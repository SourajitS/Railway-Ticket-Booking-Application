package com.railway.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class TrainDTO {


    private Long trainId;
//    @Schema(
//            description = "Train name",
//            example = "LKO - DELHI Intercity",
//            required = true
//    )
    @NotBlank(message = "Train name is required")
    @Pattern(
            regexp = "^[A-Za-z]+(?:[ -][A-Za-z]+)*$",
            message = "Invalid train name. Only letters, spaces and hyphens are allowed"
    )
    private String trainName;

//    @Schema(
//            description = "Route name in format: SOURCE - DESTINATION",
//            example = "LKO - DELHI",
//            required = true
//    )
    @NotBlank(message = "Route name is required")
    @Pattern(
            regexp = "^[A-Z]{2,5}\\s-\\s[A-Z]{2,20}$",
            message = "Route name must be in format 'LKO - DELHI'"
    )
    private String routeName;

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

}
