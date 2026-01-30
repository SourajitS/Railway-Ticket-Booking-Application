package com.railway.application.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class TrainRouteDto {

    private Long id;
    private Long trainId;
    private StationDto station;
    private Integer stationOrder;
    private LocalTime arrivalTime;
    private LocalTime departureTime;
    private Integer haltMinutes;
    private Integer distanceFromSource;
}


