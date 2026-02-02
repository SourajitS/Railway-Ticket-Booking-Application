package com.railway.application.mapper;

import com.railway.application.dto.StationDto;
import com.railway.application.dto.TrainDTO;
import com.railway.application.dto.TrainRouteDto;
import com.railway.application.entity.TrainRoute;
import org.springframework.stereotype.Component;

@Component
public class RouteMapper {

    public TrainRouteDto toRouteDto(TrainRoute trainRoute)
    {
        TrainRouteDto trainRouteDto=new TrainRouteDto();
        trainRouteDto.setId(trainRoute.getId());
        trainRouteDto.setTrainId(trainRoute.getTrain().getTrainId());
        trainRouteDto.setArrivalTime(trainRoute.getArrivalTime());
        trainRouteDto.setHaltMinutes(trainRoute.getHaltMinutes());
        trainRouteDto.setStationOrder(trainRoute.getStationOrder());
        trainRouteDto.setDepartureTime(trainRoute.getDepartureTime());
        trainRouteDto.setDistanceFromSource(trainRoute.getDistanceFromSource());
        trainRouteDto.setStation(new StationDto(
                trainRoute.getStation().getId(),
                trainRoute.getStation().getName(),
                trainRoute.getStation().getCode(),
                trainRoute.getStation().getCity(),
                trainRoute.getStation().getState()));
        return trainRouteDto;
    }

    public TrainRoute toRouteEntity(TrainRouteDto trainRouteDto)
    {
        TrainRoute route = new TrainRoute();
        //route.setId(trainRouteDto.getId());

        route.setArrivalTime(trainRouteDto.getArrivalTime());
        route.setDepartureTime(trainRouteDto.getDepartureTime());
        route.setStationOrder(trainRouteDto.getStationOrder());
        route.setHaltMinutes(trainRouteDto.getHaltMinutes());
        route.setDistanceFromSource(trainRouteDto.getDistanceFromSource());
        return route;
    }
}
