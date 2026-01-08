package com.railway.application.dto;

import com.railway.application.entity.TrainImage;
import org.springframework.core.io.Resource;

public record TrainImageDataWithResource(
        TrainImage trainImage,
        Resource resource
) {
}
