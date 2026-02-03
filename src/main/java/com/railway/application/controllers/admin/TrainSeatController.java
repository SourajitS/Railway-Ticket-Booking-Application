package com.railway.application.controllers.admin;

import com.railway.application.dto.TrainSeatDto;
import com.railway.application.service.TrainSeatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/train-seats")
public class TrainSeatController {

   private final TrainSeatService trainSeatService;

    public TrainSeatController(TrainSeatService trainSeatService) {
        this.trainSeatService = trainSeatService;
    }

    @PostMapping
    public ResponseEntity<TrainSeatDto> createTrainSeat(@RequestBody TrainSeatDto trainSeatDto)
    {
        TrainSeatDto seatInfo = trainSeatService.createSeatInfo(trainSeatDto);
        return  new ResponseEntity<>(seatInfo, HttpStatus.CREATED);
    }

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<List<TrainSeatDto>> getTrainSeatInfo(@PathVariable Long scheduleId)
    {
        List<TrainSeatDto> seatInfoByTrainScheduleId = trainSeatService.getSeatInfoByTrainScheduleId(scheduleId);
        return new ResponseEntity<>(seatInfoByTrainScheduleId,HttpStatus.OK);
    }
    @DeleteMapping("/{seatId}")
    public ResponseEntity<Void> deleteSeatInfo(@PathVariable Long seatId)
    {
        trainSeatService.deleteSeatInfo(seatId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{seatId}")
    public ResponseEntity<TrainSeatDto> updateSeatInfo(@PathVariable Long seatId, @RequestBody TrainSeatDto trainSeatDto)
    {
        TrainSeatDto updateSeatInfo = trainSeatService.updateSeatInfo(seatId, trainSeatDto);
        return new ResponseEntity<>(updateSeatInfo,HttpStatus.OK);
    }
}
