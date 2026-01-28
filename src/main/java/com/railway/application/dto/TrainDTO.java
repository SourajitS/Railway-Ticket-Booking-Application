package com.railway.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    private String number;

//    @Schema(
//            description = "Route name in format: SOURCE - DESTINATION",
//            example = "LKO - DELHI",
//            required = true
//    )
    private Integer totalDistance;
    private StationDto sourceStation;
    private StationDto destinationStation;


}
