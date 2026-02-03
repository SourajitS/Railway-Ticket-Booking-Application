package com.railway.application.mapper;

import com.railway.application.dto.TrainSeatDto;
import com.railway.application.entity.CoachType;
import com.railway.application.entity.TrainSeat;
import org.springframework.stereotype.Component;

@Component
public class SeatMapper {

    public TrainSeatDto toDto(TrainSeat trainSeat)
    {
        TrainSeatDto trainSeatDto=new TrainSeatDto();
        trainSeatDto.setId(trainSeat.getId());
        trainSeatDto.setAvailableSeats(trainSeat.getAvailableSeats());
        trainSeatDto.setTrainScheduleId(trainSeat.getTrainSchedule().getId());
        trainSeatDto.setNextSeatToAssign(trainSeat.getNextSeatToAssign());
        trainSeatDto.setTrainSeatOrder(trainSeat.getTrainSeatOrder());
        trainSeatDto.setTotalSeats(trainSeat.getTotalSeats());
        trainSeatDto.setCoachType(trainSeat.getCoachType());
        trainSeatDto.setPrice(trainSeat.getPrice());
        return trainSeatDto;

    }

    public  TrainSeat toEntity(TrainSeatDto dto) {

        if (dto == null) return null;

        TrainSeat seat = new TrainSeat();
        //seat.setId(dto.getId());
        seat.setCoachType(dto.getCoachType());
        seat.setTotalSeats(dto.getTotalSeats());
        seat.setAvailableSeats(dto.getAvailableSeats());
        seat.setTrainSeatOrder(dto.getTrainSeatOrder());
        seat.setNextSeatToAssign(dto.getNextSeatToAssign());
        seat.setPrice(
                dto.getPrice()
        );

        return seat;
    }
}
