package com.railway.application.service.impl;

import com.railway.application.dto.AvailableTrainResponse;
import com.railway.application.dto.TrainDTO;
import com.railway.application.dto.TrainSearchDto;
import com.railway.application.entity.*;
import com.railway.application.exceptions.ResourceNotFoundException;
import com.railway.application.mapper.TrainMapper;
import com.railway.application.repository.StationRepository;
import com.railway.application.repository.TrainRepository;
import com.railway.application.repository.TrainScheduleRepository;
import com.railway.application.service.TrainService;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class TrainServiceImpl implements TrainService {

    private final TrainRepository trainRepository;
    private final TrainMapper trainMapper;
    private final StationRepository stationRepository;
    private final TrainScheduleRepository trainScheduleRepository;




    @Override
    public TrainDTO createTrain(TrainDTO trainDTO) {
        Long sid=trainDTO.getSourceStation().getId();
        Long did=trainDTO.getDestinationStation().getId();
        Station sourceStation = stationRepository.findById(sid).orElseThrow(() -> new ResourceNotFoundException("Station Not Found with " + sid));
        Station destinationStation = stationRepository.findById(did).orElseThrow(() -> new ResourceNotFoundException("Station Not Found with " + did));
        Train train = trainMapper.toEntity(trainDTO);
        train.setSourceStation(sourceStation);
        train.setDestinationStation(destinationStation);
        Train savedTrain = trainRepository.save(train);
        return trainMapper.toDTO(savedTrain);
    }

    @Override
    public List<TrainDTO> getAllTrains() {
        List<Train> trains = trainRepository.findAll();
        return trains.stream().map(trainMapper::toDTO).toList();
    }

    @Override
    public TrainDTO getTrainById(Long id) {

        Train train = trainRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Train not found with id " + id));
        return trainMapper.toDTO(train);

    }

    @Override
    public TrainDTO updateTrain(Long id, TrainDTO trainDTO) {
        Train train = trainRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Train not found with id " + id));
        train.setTrainName(trainDTO.getTrainName());
        train.setNumber(trainDTO.getNumber());
        train.setTotalDistance(trainDTO.getTotalDistance());
        Long sid = trainDTO.getSourceStation().getId();
        Station sourceStation = stationRepository.findById(sid).orElseThrow(() -> new ResourceNotFoundException("Station not found with id " + sid));
        Long did=trainDTO.getDestinationStation().getId();
        Station destinationStation=stationRepository.findById(did).orElseThrow(()->new ResourceNotFoundException("Station not found with id "+did));
        train.setSourceStation(sourceStation);
        train.setDestinationStation(destinationStation);
        trainRepository.save(train);
        return trainMapper.toDTO(train);
    }

    @Override
    public void deleteTrain(Long id) {
        Train train = trainRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Train not found with id " + id));
        trainRepository.delete(train);
    }



    // HELPER METHOD: Find schedule for specific date
    private TrainSchedule findTrainScheduleForDate(Train train, LocalDate journeyDate) {
        return train.getTrainSchedules().stream()
                .filter(schedule -> schedule.getRunDate().equals(journeyDate))
                .findFirst()
                .orElse(null); // Returns null if not found
    }




    @Override
    public List<AvailableTrainResponse> userTrainSearch(TrainSearchDto request) {
        // STEP 1: Database query - gets trains that stop at both stations
        List<Train> matchedTrains = trainRepository.findTrainBySourceAndDestination(
                request.getSourceStationId(),
                request.getDestinationStationId()
        );

        // STEP 2: Stream processing pipeline
        List<AvailableTrainResponse> responseList = matchedTrains.stream()
                .peek(train -> System.out.println("Before filter - Train: " + train.getTrainName()))
                // PHASE 1: FILTER - Which trains are valid for booking?


                .filter(train -> {

                    System.out.println("Processing train: " + train.getTrainName());
                    // SECTION A: Route Order Validation
                    Integer sourceStationOrder = null;
                    Integer destinationStationOrder = null;
                    System.out.println(train.getTrainRoutes().size());
                    // Loop through all stations in train's route
                    for (TrainRoute trainRoute : train.getTrainRoutes()) {
                        // Check if current station is the source station
                        if (trainRoute.getStation().getId().equals(request.getSourceStationId())) {
                            sourceStationOrder = trainRoute.getStationOrder(); // e.g., order 1, 2, 3...
                        }
                        // Check if current station is the destination station
                        if (trainRoute.getStation().getId().equals(request.getDestinationStationId())) {
                            destinationStationOrder = trainRoute.getStationOrder();
                        }
                        // Optimization: Exit early if both stations found
                        if (sourceStationOrder != null && destinationStationOrder != null) {
                            break; // No need to check remaining stations
                        }
                    }

                    // Validate that source comes BEFORE destination in route
                    boolean validOrder = sourceStationOrder != null &&
                            destinationStationOrder != null &&
                            sourceStationOrder < destinationStationOrder;
                    // Example: Source=Station2(order=2), Destination=Station4(order=4) → valid
                    // Invalid if: Source=Station4(order=4), Destination=Station2(order=2)

                    // SECTION B: Schedule Date Validation
                    boolean runOnThatDay = train.getTrainSchedules().stream()
                            .anyMatch(schedule -> schedule.getRunDate().equals(request.getJourneyDate()));
                    // Checks if train operates on the requested date

                    // Final filter condition: Must have valid route AND run on requested date
                    return validOrder && runOnThatDay;
                })

                // PHASE 2: MAP - Transform valid trains into booking responses
                .map(train -> {
//                    // Initialize collections
//                    Hibernate.initialize(train.getTrainSchedules());
//                    Hibernate.initialize(train.getTrainRoutes());
                    // SECTION C: Find specific schedule for journey date
                    TrainSchedule trainSchedule = train.getTrainSchedules().stream()
                            .filter(sch -> sch.getRunDate().equals(request.getJourneyDate()))
                            .findFirst().orElse(null); // Get first matching schedule or null

                    // Defense check: Shouldn't happen due to filter, but safe guard
                    if (trainSchedule == null) {
                        return null; // Skip this train
                    }

                    // SECTION D: Find source station details
                    TrainRoute sourceRoute = train.getTrainRoutes().stream()
                            .filter(route -> route.getStation().getId().equals(request.getSourceStationId()))
                            .findFirst().orElse(null); // Get source station route details

                    // Defense check: Also shouldn't happen due to filter
                    if (sourceRoute == null) {
                        return null; // Skip this train
                    }

                    // SECTION E: Aggregate seat availability by coach type
                    Map<CoachType, Integer> seatMap = new HashMap<>();
                    Map<CoachType, Double> priceMap = new HashMap<>();

                    // Process all seats for this schedule
                    trainSchedule.getTrainSeats().forEach(trainSeat -> {
                        // MERGE: Sum available seats for same coach type
                        // If SLEEPER already has 5 seats, and new SLEEPER has 3 → becomes 8
                        seatMap.merge(trainSeat.getCoachType(),
                                trainSeat.getAvailableSeats(),
                                Integer::sum);

                        // PUT IF ABSENT: Set price only once per coach type
                        // Prevents overwriting prices if multiple seat records exist
                        priceMap.putIfAbsent(trainSeat.getCoachType(), trainSeat.getPrice());
                    });

                    // SECTION F: Build final response object
                    return AvailableTrainResponse.builder()
                            .trainId(train.getTrainId())           // Internal ID
                            .trainNumber(train.getNumber())        // Public number "12678"
                            .trainName(train.getTrainName())       // "Rajdhani Express"
                            .departureTime(sourceRoute.getDepartureTime()) // When train leaves source
                            .arrivalTime(sourceRoute.getArrivalTime())     // When arrives at destination
                            .seatAvailable(seatMap)               // {SLEEPER: 25, AC3: 12}
                            .price(priceMap)                      // {SLEEPER: 450.0, AC3: 750.0}
                            .scheduledDate(trainSchedule.getRunDate()) // Actual service date
                            .build();
                })

                // PHASE 3: Cleanup - Remove any null responses
                .filter(Objects::nonNull)  // Filters out trains that returned null in map phase

                // PHASE 4: Collect results
                .collect(Collectors.toList());  // Convert stream to List

        return responseList;
    }


//    @Override
//    public List<AvailableTrainResponse> userTrainSearch(TrainSearchDto request) {
//
//       List<TrainRoute> matchedTrain= trainRepository.findTrainBySourceAndDestinationInOrder(request.getSourceStationId(),request.getDestinationStationId());
//
//        List<AvailableTrainResponse> availableTrainResponses = matchedTrain.stream().map(trainRoute -> {
//            TrainSchedule trainSchedule =
//                    trainScheduleRepository.findByTrainIdAndRunDate(trainRoute.getTrain().getTrainId(), request.getJourneyDate()).orElse(null);
//
//            if (trainSchedule == null) {
//                return null;
//            }
//
//            Map<CoachType, Integer> seatMap = new HashMap<>();
//            Map<CoachType, Double> priceMap = new HashMap<>();
//
//            //Train is scheduled for given date
//            for (TrainSeat trainSeat : trainSchedule.getTrainSeats()) {
//                seatMap.merge(trainSeat.getCoachType(), trainSeat.getAvailableSeats(), Integer::sum);
//                priceMap.putIfAbsent(trainSeat.getCoachType(), trainSeat.getPrice());
//            }
//
//            return AvailableTrainResponse.builder()
//                    .trainId(trainRoute.getTrain().getTrainId())
//                    .trainName(trainRoute.getTrain().getTrainName())
//                    .trainNumber(trainRoute.getTrain().getNumber())
//                    .departureTime(trainRoute.getDepartureTime())
//                    .arrivalTime(trainRoute.getArrivalTime())
//                    .seatAvailable(seatMap)
//                    .price(priceMap)
//                    .build();
//        }).filter(Objects::nonNull).toList();
//
//        return availableTrainResponses;
//    }


}
