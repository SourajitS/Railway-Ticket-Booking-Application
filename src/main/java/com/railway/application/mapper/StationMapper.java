package com.railway.application.mapper;

import com.railway.application.dto.StationDto;
import com.railway.application.entity.Station;
import org.springframework.stereotype.Component;

@Component
public class StationMapper {

    public StationDto toDto(Station station)
    {
        StationDto stationDto=new StationDto();
        stationDto.setCode(station.getCode());
        stationDto.setName(station.getName());
        stationDto.setCity(station.getCity());
        stationDto.setState(station.getState());
        return stationDto;
    }

    public Station toEntity(StationDto stationDto)
    {
        Station station=new Station();
        station.setCode(stationDto.getCode());
        station.setName(stationDto.getName());
        station.setCity(stationDto.getCity());
        station.setState(stationDto.getState());
        return station;
    }
}
