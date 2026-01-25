package com.railway.application.service.impl;

import com.railway.application.dto.PagedResponse;
import com.railway.application.dto.StationDto;
import com.railway.application.entity.Station;
import com.railway.application.exceptions.ResourceNotFoundException;
import com.railway.application.mapper.StationMapper;
import com.railway.application.repository.StationRepository;
import com.railway.application.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;
    private final StationMapper stationMapper;

    public StationServiceImpl(StationRepository stationRepository, StationMapper stationMapper) {
        this.stationRepository = stationRepository;
        this.stationMapper = stationMapper;
    }

    @Override
    public StationDto findById(Long id) {
        Station station = stationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("station not found with id : " + id));
        return stationMapper.toDto(station);
    }

    @Override
    public StationDto createStation(StationDto stationDto) {

        Station station = stationMapper.toEntity(stationDto);
        Station savedStation = stationRepository.save(station);
        return stationMapper.toDto(savedStation);
    }

    public PagedResponse<StationDto> listStations(int page, int size, String sortBy, String sortDir)
    {
        Sort sort = sortDir.trim().equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(page, size, sort);
        Page<Station> stations = stationRepository.findAll(pageable);
        Page<StationDto> stationDtos = stations.map(stationMapper::toDto);
        return PagedResponse.fromPage(stationDtos);
    }

   public StationDto update(Long id,StationDto dto)
   {
       Station station = stationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("station not found with id : " + id));
        station.setCode(dto.getCode());
        station.setName(dto.getName());
        station.setCity(dto.getCity());
        station.setState(dto.getState());
       Station updatedStation = stationRepository.save(station);
       return stationMapper.toDto(updatedStation);
   }

    @Override
    public void delete(Long id) {
        Station station = stationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(("station not found with id : " + id)));
        stationRepository.delete(station);

    }
}
