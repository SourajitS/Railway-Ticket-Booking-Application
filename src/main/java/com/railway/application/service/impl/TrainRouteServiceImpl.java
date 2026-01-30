package com.railway.application.service.impl;

import com.railway.application.dto.TrainRouteDto;
import com.railway.application.entity.Station;
import com.railway.application.entity.Train;
import com.railway.application.entity.TrainRoute;
import com.railway.application.exceptions.ResourceNotFoundException;
import com.railway.application.mapper.RouteMapper;
import com.railway.application.repository.StationRepository;
import com.railway.application.repository.TrainRepo;
import com.railway.application.repository.TrainRouteRepository;
import com.railway.application.service.TrainRouteService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TrainRouteServiceImpl implements TrainRouteService {

    private final TrainRepo trainRepo;
    private final StationRepository stationRepository;
    private final TrainRouteRepository trainRouteRepository;
    private final RouteMapper routeMapper;

    public TrainRouteServiceImpl(TrainRepo trainRepo, StationRepository stationRepository, TrainRouteRepository trainRouteRepository, RouteMapper routeMapper) {
        this.trainRepo = trainRepo;
        this.stationRepository = stationRepository;
        this.trainRouteRepository = trainRouteRepository;
        this.routeMapper = routeMapper;
    }

    @Override
    public TrainRouteDto addRoute(TrainRouteDto dto) {

        Long trainId = dto.getTrainId();
        Train train = trainRepo.findById(trainId).orElseThrow(() -> new ResourceNotFoundException("Train not found with id " + trainId));

        Long stationId = dto.getStation().getId();
        Station station = stationRepository.findById(stationId).orElseThrow(() -> new ResourceNotFoundException("Station not found with id " + stationId));
        TrainRoute routeEntity = routeMapper.toRouteEntity(dto);
         routeEntity.setTrain(train);
         routeEntity.setStation(station);
        TrainRoute savedTrainRoute = trainRouteRepository.save(routeEntity);
        return routeMapper.toRouteDto(savedTrainRoute);
    }

    @Override
    public List<TrainRouteDto> getRoutesByTrain(Long trainId) {

        Train train = trainRepo.findById(trainId).orElseThrow(() -> new ResourceNotFoundException("Train not found with id " + trainId));
        List<TrainRoute> trainRoutes = trainRouteRepository.findRouteByTrainId(trainId);
        List<TrainRouteDto> trainRouteDtos = trainRoutes.stream().map(trainRoute -> routeMapper.toRouteDto(trainRoute)).toList();
        return trainRouteDtos;
    }

    @Override
    public TrainRouteDto updateRoute(Long id, TrainRouteDto dto) {

        // 1️⃣ Fetch existing route
        TrainRoute route = trainRouteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Train Route not found " + id));

        // 2️⃣ Update simple fields
        route.setArrivalTime(dto.getArrivalTime());
        route.setDepartureTime(dto.getDepartureTime());
        route.setDistanceFromSource(dto.getDistanceFromSource());
        route.setHaltMinutes(dto.getHaltMinutes());
        route.setStationOrder(dto.getStationOrder());

        // 3️⃣ Update train if changed
        if (dto.getTrainId() != null) {
            Train train = trainRepo.findById(dto.getTrainId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Train not found with id " + dto.getTrainId()));
            route.setTrain(train);
        }

        // 4️⃣ Update station if changed
        if (dto.getStation() != null && dto.getStation().getId() != null) {
            Station station = stationRepository.findById(dto.getStation().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Station not found with id " + dto.getStation().getId()));
            route.setStation(station);
        }

        // 5️⃣ Save updated entity
        TrainRoute saved = trainRouteRepository.save(route);

        return routeMapper.toRouteDto(saved);
    }


    @Override
    public void deleteRoute(Long id) {
        TrainRoute route = trainRouteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Train Route not found " + id));
        trainRouteRepository.delete(route);
    }
}
