package com.railway.application.dto;

import com.railway.application.entity.TrainImage;

import java.time.LocalDateTime;

public record TrainImageResponse(
        Long id,
        String fileName,
        String fileType,
        String url,
        Long size,
        LocalDateTime uploadTime

) {

    public static TrainImageResponse from(TrainImage image,String baseUrl,String trainId)
    {
        return new TrainImageResponse(
                image.getId(),
                image.getFileName(),
                image.getFileType(),
                baseUrl+"/trains/"+trainId+"/image",
                image.getSize(),
                image.getUploadTime()
                );
    }
}
