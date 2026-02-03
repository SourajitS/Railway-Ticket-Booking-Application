package com.railway.application.service.impl;

import com.railway.application.dto.TrainSeatDto;
import com.railway.application.entity.TrainSchedule;
import com.railway.application.entity.TrainSeat;
import com.railway.application.exceptions.ResourceNotFoundException;
import com.railway.application.mapper.SeatMapper;
import com.railway.application.repository.TrainScheduleRepository;
import com.railway.application.repository.TrainSeatRepository;
import com.railway.application.service.TrainSeatService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainSeatServiceImpl implements TrainSeatService {

    private final TrainSeatRepository trainSeatRepository;
    private final TrainScheduleRepository trainScheduleRepository;
    private final SeatMapper seatMapper;


    public TrainSeatServiceImpl(TrainSeatRepository trainSeatRepository, TrainScheduleRepository trainScheduleRepository, SeatMapper seatMapper) {
        this.trainSeatRepository = trainSeatRepository;
        this.trainScheduleRepository = trainScheduleRepository;
        this.seatMapper = seatMapper;
    }

    @Override
    public TrainSeatDto createSeatInfo(TrainSeatDto trainSeatDto) {
        Long trainScheduleId = trainSeatDto.getTrainScheduleId();
        TrainSchedule trainSchedule = trainScheduleRepository.findById(trainScheduleId).orElseThrow(() -> new ResourceNotFoundException("Train Schedule not found with id :" + trainScheduleId));

        TrainSeat trainSeat=seatMapper.toEntity(trainSeatDto);
        trainSeat.setTrainSchedule(trainSchedule);
        TrainSeat savedTrainSeat = trainSeatRepository.save(trainSeat);
        return seatMapper.toDto(savedTrainSeat);
    }

    @Override
    public List<TrainSeatDto> getSeatInfoByTrainScheduleId(Long scheduleId) {

        List<TrainSeat> byTrainScheduleId = trainSeatRepository.findByTrainScheduleId(scheduleId);
        return byTrainScheduleId.stream().map(seatInfo -> seatMapper.toDto(seatInfo)).toList();
    }

    @Override
    public void deleteSeatInfo(Long seatId) {
        TrainSeat trainSeat = trainSeatRepository.findById(seatId).orElseThrow(() -> new ResourceNotFoundException("Train Seat not found with id : " + seatId));
         trainSeatRepository.delete(trainSeat);
    }

    @Override
    public TrainSeatDto updateSeatInfo(Long seatId, TrainSeatDto trainSeatDto) {
        TrainSeat trainSeat = trainSeatRepository.findById(seatId).orElseThrow(() -> new ResourceNotFoundException("Train Seat not found with id : " + seatId));
        Long trainScheduleId = trainSeatDto.getTrainScheduleId();
        TrainSchedule trainSchedule = trainScheduleRepository.findById(trainScheduleId).orElseThrow(() -> new ResourceNotFoundException("Train Schedule not found with id :" + trainScheduleId));

        trainSeat.setTrainSchedule(trainSchedule);
        trainSeat.setCoachType(trainSeatDto.getCoachType());
        trainSeat.setTotalSeats(trainSeatDto.getTotalSeats());
        trainSeat.setAvailableSeats(trainSeatDto.getAvailableSeats());
        trainSeat.setPrice(trainSeatDto.getPrice());
        trainSeat.setTrainSeatOrder(trainSeatDto.getTrainSeatOrder());
        trainSeat.setNextSeatToAssign(trainSeatDto.getNextSeatToAssign());
        TrainSeat savedTrainSeat = trainSeatRepository.save(trainSeat);
        return seatMapper.toDto(savedTrainSeat);
    }


   @Override
    public List<Integer> bookSeats(int seatToBook,Long seatId)
    {
        TrainSeat trainSeat = trainSeatRepository.findById(seatId).orElseThrow(() -> new ResourceNotFoundException("Train Seat not found with id : " + seatId));
        if(trainSeat.getAvailableSeats()<seatToBook)
        {
            throw new IllegalStateException("Not enough seat available");
        }


            List<Integer> bookSeats=new ArrayList<>();
            int nextSeat=trainSeat.getNextSeatToAssign();
            for(int i=1;i<=seatToBook;i++)
            {
                bookSeats.add(nextSeat);
                nextSeat++;
            }

            trainSeat.setAvailableSeats(trainSeat.getAvailableSeats()-seatToBook);
            trainSeat.setNextSeatToAssign(nextSeat);

            trainSeatRepository.save(trainSeat);
            return bookSeats;
        }


}
