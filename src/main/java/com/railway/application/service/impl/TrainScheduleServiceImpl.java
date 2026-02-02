package com.railway.application.service.impl;

import com.railway.application.dto.TrainScheduleDto;
import com.railway.application.entity.Train;
import com.railway.application.entity.TrainSchedule;
import com.railway.application.exceptions.ResourceNotFoundException;
import com.railway.application.mapper.ScheduleMapper;
import com.railway.application.repository.TrainRepo;
import com.railway.application.repository.TrainScheduleRepository;
import com.railway.application.service.TrainScheduleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainScheduleServiceImpl implements TrainScheduleService {

    private final TrainScheduleRepository trainScheduleRepository;
    private final TrainRepo trainRepo;
    private final ScheduleMapper scheduleMapper;

    public TrainScheduleServiceImpl(TrainScheduleRepository trainScheduleRepository, TrainRepo trainRepo, ScheduleMapper scheduleMapper) {
        this.trainScheduleRepository = trainScheduleRepository;
        this.trainRepo = trainRepo;
        this.scheduleMapper = scheduleMapper;
    }

    @Override
    public TrainScheduleDto createTrainSchedule(TrainScheduleDto trainScheduleDto) {
        Long trainId = trainScheduleDto.getTrainId();
        Train train = trainRepo.findById(trainId).orElseThrow(() -> new ResourceNotFoundException("Train Not Found with id" + trainId));
        TrainSchedule entity = scheduleMapper.toEntity(trainScheduleDto);
        entity.setTrain(train);
        TrainSchedule savedTrainSchedule = trainScheduleRepository.save(entity);
        return scheduleMapper.toDto(savedTrainSchedule);


    }

    @Override
    public List<TrainScheduleDto> getTrainScheduleByTrainId(Long trainId) {

        Train train = trainRepo.findById(trainId).orElseThrow(() -> new ResourceNotFoundException("Train Not Found with id" + trainId));
        List<TrainSchedule> trainSchedules = trainScheduleRepository.findScheduleByTrainId(trainId);
        List<TrainScheduleDto> trainScheduleDtoList = trainSchedules.stream().map(trainSchedule -> scheduleMapper.toDto(trainSchedule)).toList();

        return trainScheduleDtoList;
    }

    @Override
    public void deleteTrainSchedule(Long trainScheduleId) {
        TrainSchedule trainSchedule = trainScheduleRepository.findById(trainScheduleId).orElseThrow(() -> new ResourceNotFoundException("Train Schedule Not Found with trainScheduleId " + trainScheduleId));
         trainScheduleRepository.delete(trainSchedule);
    }

    @Override
    public TrainScheduleDto updateTrainSchedule(Long trainScheduleId, TrainScheduleDto trainScheduleDto) {
        TrainSchedule trainSchedule = trainScheduleRepository.findById(trainScheduleId).orElseThrow(() -> new ResourceNotFoundException("Train Schedule Not Found with trainScheduleId " + trainScheduleId));
        trainSchedule.setRunDate(trainScheduleDto.getRunDate());
        trainSchedule.setAvailableSeats(trainScheduleDto.getAvailableSeats());
          if(trainScheduleDto.getTrainId()!=null)
             {
              Train train = trainRepo.findById(trainScheduleDto.getTrainId()).orElseThrow(() -> new ResourceNotFoundException("Train not found with id : " + trainScheduleDto.getTrainId()));
              trainSchedule.setTrain(train);
             }
        TrainSchedule updatedTrainSchedule = trainScheduleRepository.save(trainSchedule);
        return scheduleMapper.toDto(updatedTrainSchedule);
    }
}
