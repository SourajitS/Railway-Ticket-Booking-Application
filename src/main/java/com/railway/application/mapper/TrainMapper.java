package com.railway.application.mapper;

import com.railway.application.dto.StationDto;
import com.railway.application.dto.TrainDTO;
import com.railway.application.entity.Train;
import org.springframework.stereotype.Component;

@Component
public class TrainMapper {

    public TrainDTO toDTO(Train train)
    {
        if(train==null)return null;
        TrainDTO trainDTO=new TrainDTO();
        trainDTO.setTrainId(train.getTrainId());
        trainDTO.setTrainName(train.getTrainName());
        trainDTO.setNumber(train.getNumber());
        trainDTO.setTotalDistance(train.getTotalDistance());
        trainDTO.setSourceStation(
                new StationDto(train.getSourceStation().getId(),train.getSourceStation().getName(),
                        train.getSourceStation().getCode(),train.getSourceStation().getCity(),train.getSourceStation().getState())
        );
        trainDTO.setDestinationStation(
                new StationDto(train.getDestinationStation().getId(),train.getDestinationStation().getName(),
                train.getDestinationStation().getCode(),train.getDestinationStation().getCity(),train.getDestinationStation().getState()
        ));

        return trainDTO;
    }

    public Train toEntity(TrainDTO trainDTO) {
        if (trainDTO == null) return null;

        Train train = new Train();
        train.setTrainId(trainDTO.getTrainId());
        train.setTrainName(trainDTO.getTrainName());
        train.setNumber(trainDTO.getNumber());
        train.setTotalDistance(trainDTO.getTotalDistance());

        return train;
    }
}
