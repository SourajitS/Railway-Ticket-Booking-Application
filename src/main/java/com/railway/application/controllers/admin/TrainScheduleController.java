package com.railway.application.controllers.admin;

import com.railway.application.dto.TrainScheduleDto;
import com.railway.application.entity.TrainSchedule;
import com.railway.application.service.TrainScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/train-schedules")
public class TrainScheduleController {

   private final TrainScheduleService trainScheduleService;

    public TrainScheduleController(TrainScheduleService trainScheduleService) {
        this.trainScheduleService = trainScheduleService;
    }
    @PostMapping
    public ResponseEntity<TrainScheduleDto> createTrainSchedule(@RequestBody TrainScheduleDto trainScheduleDto)
    {
        TrainScheduleDto trainSchedule = trainScheduleService.createTrainSchedule(trainScheduleDto);
        return new ResponseEntity<>(trainSchedule, HttpStatus.CREATED);
    }
    @GetMapping("/train/{trainId}")
    public ResponseEntity<List<TrainScheduleDto>> getTrainScheduleByTrainId(@PathVariable Long trainId)
    {
        List<TrainScheduleDto> trainScheduleByTrainId = trainScheduleService.getTrainScheduleByTrainId(trainId);
        return new ResponseEntity<>(trainScheduleByTrainId,HttpStatus.OK);
    }

    @DeleteMapping("/{trainScheduleId}")
    public ResponseEntity<Void> deleteTrainSchedule(@PathVariable Long trainScheduleId)
    {
        trainScheduleService.deleteTrainSchedule(trainScheduleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{trainScheduleId}")
    public ResponseEntity<TrainScheduleDto> updateTrainSchedule(@PathVariable Long trainScheduleId,@RequestBody TrainScheduleDto trainScheduleDto)
    {
        TrainScheduleDto updatedTrainScheduleDto = trainScheduleService.updateTrainSchedule(trainScheduleId, trainScheduleDto);
        return new ResponseEntity<>(updatedTrainScheduleDto,HttpStatus.OK);
    }
}
