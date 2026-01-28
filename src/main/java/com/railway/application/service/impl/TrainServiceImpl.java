package com.railway.application.service.impl;

import com.railway.application.dto.TrainDTO;
import com.railway.application.entity.Station;
import com.railway.application.entity.Train;
import com.railway.application.exceptions.ResourceNotFoundException;
import com.railway.application.mapper.TrainMapper;
import com.railway.application.repository.StationRepository;
import com.railway.application.repository.TrainRepo;
import com.railway.application.service.TrainService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@AllArgsConstructor
@Service
public class TrainServiceImpl implements TrainService {

    private final TrainRepo trainRepo;
    private final TrainMapper trainMapper;
    private final StationRepository stationRepository;


    @Override
    public TrainDTO createTrain(TrainDTO trainDTO) {
        Long sid=trainDTO.getSourceStation().getId();
        Long did=trainDTO.getDestinationStation().getId();
        Station sourceStation = stationRepository.findById(sid).orElseThrow(() -> new ResourceNotFoundException("Station Not Found with " + sid));
        Station destinationStation = stationRepository.findById(did).orElseThrow(() -> new ResourceNotFoundException("Station Not Found with " + did));
        Train train = trainMapper.toEntity(trainDTO);
        train.setSourceStation(sourceStation);
        train.setDestinationStation(destinationStation);
        Train savedTrain = trainRepo.save(train);
        return trainMapper.toDTO(savedTrain);
    }

    @Override
    public List<TrainDTO> getAllTrains() {
        List<Train> trains = trainRepo.findAll();
        return trains.stream().map(trainMapper::toDTO).toList();
    }

    @Override
    public TrainDTO getTrainById(Long id) {

        Train train = trainRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Train not found with id " + id));
        return trainMapper.toDTO(train);

    }

    @Override
    public TrainDTO updateTrain(Long id, TrainDTO trainDTO) {
        Train train = trainRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Train not found with id " + id));
        train.setTrainName(trainDTO.getTrainName());
        train.setNumber(trainDTO.getNumber());
        train.setTotalDistance(trainDTO.getTotalDistance());
        Long sid = trainDTO.getSourceStation().getId();
        Station sourceStation = stationRepository.findById(sid).orElseThrow(() -> new ResourceNotFoundException("Station not found with id " + sid));
        Long did=trainDTO.getDestinationStation().getId();
        Station destinationStation=stationRepository.findById(did).orElseThrow(()->new ResourceNotFoundException("Station not found with id "+did));
        train.setSourceStation(sourceStation);
        train.setDestinationStation(destinationStation);
        trainRepo.save(train);
        return trainMapper.toDTO(train);
    }

    @Override
    public void deleteTrain(Long id) {
        Train train = trainRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Train not found with id " + id));
        trainRepo.delete(train);
    }
}
