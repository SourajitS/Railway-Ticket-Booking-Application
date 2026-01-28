package com.railway.application.controllers.admin;

import com.railway.application.dto.TrainDTO;
import com.railway.application.service.TrainService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/trains")
public class TrainAdminController {

    private final TrainService trainService;

    public TrainAdminController(TrainService trainService) {
        this.trainService = trainService;
    }
    @PostMapping
    public ResponseEntity<TrainDTO> createTrain(@Valid @RequestBody TrainDTO trainDTO)
{
     return new ResponseEntity<>(trainService.createTrain(trainDTO), HttpStatus.CREATED);
}

@GetMapping
public List<TrainDTO> getAllTrains()
{
    return trainService.getAllTrains();
}

@GetMapping("/{id}")
public ResponseEntity<TrainDTO> getTrainById(@PathVariable ("id") Long id)
{
    return new ResponseEntity<>(trainService.getTrainById(id),HttpStatus.OK);
}

@PutMapping("/{id}")
    public ResponseEntity<TrainDTO> updateTrain(@PathVariable Long id,@RequestBody TrainDTO trainDTO)
{
    return new ResponseEntity<>(trainService.updateTrain(id,trainDTO),HttpStatus.OK);
}

@DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrain(@PathVariable("id") Long id)
{
    trainService.deleteTrain(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}



}


