package com.railway.application.service;
import  java.util.*;
import java.util.stream.Collectors;

import com.railway.application.dto.PagedResponse;
import com.railway.application.dto.TrainDTO;
import com.railway.application.entity.Train;
import com.railway.application.exceptions.ResourceNotFoundException;
import com.railway.application.mapper.TrainMapper;
import com.railway.application.repository.TrainRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TrainService {
private final TrainRepo trainRepo;
private final TrainMapper trainMapper;


    public TrainService(TrainRepo trainRepo, TrainMapper trainMapper) {
        this.trainRepo = trainRepo;
        this.trainMapper = trainMapper;
    }
    public PagedResponse<TrainDTO> getAllTrains(int page, int size, String sortBy, String sortDir) {

        page = Math.max(page, 0);
        size = Math.min(Math.max(size, 1), 50);

        Sort sort = sortDir.trim().equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(page, size, sort);
        Page<Train> trains = trainRepo.findAll(pageable);
        Page<TrainDTO> trainDTOPage = trains.map(trainMapper::toDTO);
        return PagedResponse.fromPage(trainDTOPage);
        // return trainRepo.findAll().stream().map(trainMapper::toDTO).collect(Collectors.toList());
    }
    public TrainDTO getTrain(String trainId)
    {
      return trainRepo
              .findById(trainId)
              .map(trainMapper::toDTO)
              .orElseThrow(()->new ResourceNotFoundException("Train not found with id :"+trainId));
    }

    public void deleteTrain(String trainId) {
        Train train = trainRepo
                .findById(trainId)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with id :" + trainId));

        trainRepo.delete(train);
    }

    public TrainDTO createTrain( TrainDTO trainDTO)
    {
        Train train = trainMapper.toEntity(trainDTO);
        Train savedTrain = trainRepo.save(train);
        return trainMapper.toDTO(savedTrain);

    }


}
