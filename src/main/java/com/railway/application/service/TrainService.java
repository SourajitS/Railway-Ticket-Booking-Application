package com.railway.application.service;

import com.railway.application.dto.TrainDTO;
import com.railway.application.entity.Train;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TrainService {

    public TrainDTO createTrain(TrainDTO trainDTO);
    public List<TrainDTO> getAllTrains();
    public TrainDTO getTrainById(Long id);
    public TrainDTO updateTrain(Long id,TrainDTO trainDTO);
    public void deleteTrain(Long id);
}
