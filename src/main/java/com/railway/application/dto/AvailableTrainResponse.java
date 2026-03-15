package com.railway.application.dto;

import com.railway.application.entity.CoachType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvailableTrainResponse {

    private Long trainId;
    private String trainNumber;
    private String trainName;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private Map<CoachType,Integer> seatAvailable;
    private Map<CoachType,Double> price;
    private LocalDate scheduledDate;

}
