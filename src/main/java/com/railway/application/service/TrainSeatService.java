package com.railway.application.service;

import com.railway.application.dto.TrainSeatDto;
import java.util.*;

public interface TrainSeatService {

    TrainSeatDto createSeatInfo(TrainSeatDto trainSeatDto);
    List<TrainSeatDto> getSeatInfoByTrainScheduleId(Long scheduleId);
    void deleteSeatInfo(Long seatId);
    TrainSeatDto updateSeatInfo(Long seatId,TrainSeatDto trainSeatDto);
    public List<Integer> bookSeats(int seatToBook,Long seatId);
}
