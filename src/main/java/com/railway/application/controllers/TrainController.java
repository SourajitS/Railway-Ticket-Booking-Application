package com.railway.application.controllers;

import com.railway.application.dto.*;
import com.railway.application.entity.TrainImage;
import com.railway.application.service.TrainImageService;
import com.railway.application.service.TrainService;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

@RestController
@RequestMapping("/trains")
public class TrainController {


    private final TrainService trainService;
    private final TrainImageService trainImageService;

    public TrainController(TrainService trainService, TrainImageService trainImageService) {
        this.trainService = trainService;
        this.trainImageService = trainImageService;
    }

    @PostMapping("/photo")
    public String trainPhotoUpload(@RequestParam("file") MultipartFile multipartFile)
    {
        String originalFilename = multipartFile.getOriginalFilename();
        System.out.println(originalFilename);


        return originalFilename;
    }

    @GetMapping
    public ResponseEntity<PagedResponse<TrainDTO>> getTrains(
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size,
            @RequestParam (defaultValue = "trainName") String sortBy,
            @RequestParam (defaultValue = "asc") String sortDir
    ) {


        PagedResponse<TrainDTO> allTrains = trainService.getAllTrains(page, size, sortBy, sortDir);
        return new ResponseEntity<>(allTrains, HttpStatus.OK);
    }

    @GetMapping("/{trainId}")
    public ResponseEntity<TrainDTO> getTrainById(@PathVariable String trainId) {
        return new ResponseEntity<>(trainService.getTrain(trainId),HttpStatus.OK);
    }

  @DeleteMapping("/{trainId}")
  public ResponseEntity<String> deleteTrain(@PathVariable String trainId) {
    trainService.deleteTrain(trainId);
    return ResponseEntity.ok("Train deleted successfully");
  }


  @PostMapping
  public ResponseEntity<TrainDTO> createTrain(@Valid @RequestBody TrainDTO trainDTO) {
    return new ResponseEntity<>(trainService.createTrain(trainDTO), HttpStatus.CREATED);
  }



    @PostMapping("/{trainId}/upload")
    public ResponseEntity<?> uploadImage(
            @PathVariable String trainId,
            @RequestParam("file") MultipartFile file) throws IOException {

        // 1️⃣ Validate file presence
        if (file.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("File is empty", "400", false));
        }

        // 2️⃣ Validate content type
        String contentType = file.getContentType();

        if (!contentType.startsWith("image/")) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Only image files are allowed", "400", false));
        }

        // 3️⃣ Call service
        TrainImageResponse response = trainImageService.upload(file, trainId);

        // 4️⃣ Return success
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/{trainId}/image")
    public ResponseEntity<Resource> serveTrainImage(@PathVariable String trainId) throws MalformedURLException {
        TrainImageDataWithResource trainImageDataWithResource = trainImageService.loadImageByTrainId(trainId);
        TrainImage trainImage = trainImageDataWithResource.trainImage();
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(trainImage.getFileType())).body(trainImageDataWithResource.resource());
    }

//  @ExceptionHandler(NoSuchElementException.class)
//  public ErrorResponse handleNoSuchException(NoSuchElementException ex)
//  {
//      return new ErrorResponse("No Train found "+ ex.getMessage(),"404",false);
//  }

}
