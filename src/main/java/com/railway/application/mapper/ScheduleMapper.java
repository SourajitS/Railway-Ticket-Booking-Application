package com.railway.application.mapper;

import com.railway.application.dto.TrainScheduleDto;
import com.railway.application.entity.TrainSchedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class ScheduleMapper {

    public TrainScheduleDto toDto(TrainSchedule trainSchedule)
    {
        TrainScheduleDto trainScheduleDto=new TrainScheduleDto();
        trainScheduleDto.setId(trainSchedule.getId());
        trainScheduleDto.setTrainId(trainSchedule.getTrain().getTrainId());
        trainScheduleDto.setRunDate(trainSchedule.getRunDate());
        trainScheduleDto.setAvailableSeats(trainSchedule.getAvailableSeats());
        return trainScheduleDto;
    }

    public TrainSchedule toEntity(TrainScheduleDto trainScheduleDto)
    {
        TrainSchedule trainSchedule=new TrainSchedule();

        trainSchedule.setRunDate(trainScheduleDto.getRunDate());
        trainSchedule.setAvailableSeats(trainScheduleDto.getAvailableSeats());
        return trainSchedule;
    }
}
