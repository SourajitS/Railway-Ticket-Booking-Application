package com.railway.application.service;

import com.railway.application.dto.TrainScheduleDto;
import com.railway.application.entity.Train;
import com.railway.application.entity.TrainSchedule;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TrainScheduleService {

    TrainScheduleDto createTrainSchedule(TrainScheduleDto trainScheduleDto);
    List<TrainScheduleDto> getTrainScheduleByTrainId(Long trainId);
    void deleteTrainSchedule(Long trainScheduleId);

    TrainScheduleDto updateTrainSchedule(Long trainScheduleId,TrainScheduleDto trainScheduleDto);
}
