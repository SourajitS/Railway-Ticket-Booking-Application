package com.railway.application.controllers.admin;

import com.railway.application.dto.TrainRouteDto;
import com.railway.application.repository.TrainRepo;
import com.railway.application.service.TrainRouteService;
import com.railway.application.service.impl.TrainServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/train-routes")
public class TrainRouteController {

    private final TrainRouteService trainRouteService;

    public TrainRouteController(TrainRouteService trainRouteService) {
        this.trainRouteService = trainRouteService;
    }

    @PostMapping
    public ResponseEntity<TrainRouteDto> createTrainRoute(@RequestBody TrainRouteDto trainRouteDto)
    {
        TrainRouteDto routeDto = trainRouteService.addRoute(trainRouteDto);
        return new ResponseEntity<>(routeDto, HttpStatus.CREATED);
    }
    @PutMapping("/{routeId}")
    public ResponseEntity<TrainRouteDto> updateTrainRoute(@PathVariable Long routeId,@RequestBody TrainRouteDto trainRouteDto)
    {
        TrainRouteDto updatedRoute = trainRouteService.updateRoute(routeId, trainRouteDto);
        return new ResponseEntity<>(updatedRoute,HttpStatus.OK);
    }
    @DeleteMapping("/{routeId}")
    public ResponseEntity<Void> deleteTrainRoute(@PathVariable Long routeId)
    {
        trainRouteService.deleteRoute(routeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/train/{trainId}")
    public ResponseEntity<List<TrainRouteDto>> findTrainRoutebyTrainId(@PathVariable Long trainId)
    {
        List<TrainRouteDto> routesByTrain = trainRouteService.getRoutesByTrain(trainId);
        return new ResponseEntity<>(routesByTrain,HttpStatus.OK);

    }
}
