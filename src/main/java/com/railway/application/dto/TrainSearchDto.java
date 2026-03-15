package com.railway.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainSearchDto {

    private Long sourceStationId;
    private Long destinationStationId;
    private LocalDate journeyDate;
}
