package com.railway.application.service;

import com.railway.application.dto.TrainRouteDto;

import java.util.List;

public interface TrainRouteService {
    TrainRouteDto addRoute(TrainRouteDto dto);
    List<TrainRouteDto> getRoutesByTrain(Long trainId);
    TrainRouteDto updateRoute(Long id,TrainRouteDto dto);
    void deleteRoute(Long id);
}
