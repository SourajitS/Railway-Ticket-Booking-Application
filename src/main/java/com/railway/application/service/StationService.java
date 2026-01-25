package com.railway.application.service;

import com.railway.application.dto.PagedResponse;
import com.railway.application.dto.StationDto;
import org.springframework.stereotype.Service;


public interface StationService {

      StationDto findById(Long id) ;

     StationDto createStation(StationDto stationDto);
     public PagedResponse<StationDto> listStations(int page, int size, String sortBy, String sortDir);

    StationDto update(Long id,StationDto dto);

     void delete(Long id);
}
