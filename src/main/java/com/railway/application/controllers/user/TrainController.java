package com.railway.application.controllers.user;

import com.railway.application.dto.AvailableTrainResponse;
import com.railway.application.dto.TrainSearchDto;
import com.railway.application.service.TrainService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/user/train")
public class TrainController {

    private final TrainService trainService;

    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }
    @PostMapping("/search")
public ResponseEntity<List<AvailableTrainResponse>> searchTrain(@RequestBody TrainSearchDto trainSearchDto)
{
    List<AvailableTrainResponse> trainResponses = trainService.userTrainSearch(trainSearchDto);

    return new ResponseEntity<>(trainResponses, HttpStatus.OK);
}
}
