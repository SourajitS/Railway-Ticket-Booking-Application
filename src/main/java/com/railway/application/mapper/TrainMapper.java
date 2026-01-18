package com.railway.application.mapper;

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

        return trainDTO;
    }

    public Train toEntity(TrainDTO trainDTO) {
        if (trainDTO == null) return null;

        Train train = new Train();
        train.setTrainId(trainDTO.getTrainId());
        train.setTrainName(trainDTO.getTrainName());

        return train;
    }
}
