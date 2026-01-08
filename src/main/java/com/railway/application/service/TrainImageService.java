package com.railway.application.service;

import com.railway.application.dto.TrainImageDataWithResource;
import com.railway.application.dto.TrainImageResponse;
import com.railway.application.entity.Train;
import com.railway.application.entity.TrainImage;
import com.railway.application.exceptions.ResourceNotFoundException;
import com.railway.application.repository.TrainImageRepository;
import com.railway.application.repository.TrainRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
public class TrainImageService {

    @Value("${train.image.folder.path}")
    private String folderPath;
    @Value("${app.base-url}")
    private String baseUrl;

    private final TrainRepo trainRepo;
    private final TrainImageRepository trainImageRepository;

    public TrainImageService(TrainRepo trainRepo, TrainImageRepository trainImageRepository) {
        this.trainRepo = trainRepo;
        this.trainImageRepository = trainImageRepository;
    }

    public TrainImageResponse upload(MultipartFile file, String trainId) throws IOException {

        // 1️⃣ Validate file
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        // 2️⃣ Fetch train
        Train train = trainRepo.findById(trainId)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with id: " + trainId));

        // 3️⃣ Create folder if not exists
        Path uploadPath = Paths.get(folderPath);
        Files.createDirectories(uploadPath);

        // 4️⃣ Generate safe filename
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
        Path fullPath = uploadPath.resolve(fileName);

        // 5️⃣ Save file
        Files.copy(file.getInputStream(), fullPath, StandardCopyOption.REPLACE_EXISTING);
        System.out.println(fullPath);
        // 6️⃣ Save metadata
        TrainImage image = new TrainImage();
        image.setFileName(fileName);
        image.setFileType(file.getContentType());
        image.setSize(file.getSize());
        image.setTrain(train);

        train.setTrainImage(image);
       // TrainImage savedImage = trainImageRepository.save(image);
        Train saved = trainRepo.save(train);
        TrainImage trainImage = saved.getTrainImage();

        // 7️⃣ Return response
        return TrainImageResponse.from(trainImage, baseUrl , trainId);
    }

    public TrainImageDataWithResource loadImageByTrainId(String trainId) throws MalformedURLException {
        Train train = trainRepo.findById(trainId).orElseThrow(() -> new ResourceNotFoundException("Train not found with id:" + trainId));
        TrainImage trainImage = train.getTrainImage();
        if(trainImage==null)
        {
            throw new ResourceNotFoundException("TrainImage not found with trainId:" + trainId);
        }
         Path path = Paths.get(folderPath).resolve(trainImage.getFileName());
        //Path path=Paths.get(trainImage.getFileName());
        System.out.println(trainImage.getFileName());
        System.out.println(path);
        if(!Files.exists(path))

        {
            System.out.println(Files.exists(path));
            throw new ResourceNotFoundException("Train Image not found!!!");
        }
        UrlResource urlResource = new UrlResource(path.toUri());
        return new TrainImageDataWithResource(trainImage,urlResource);
    }
}
