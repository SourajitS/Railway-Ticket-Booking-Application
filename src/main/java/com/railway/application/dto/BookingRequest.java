package com.railway.application.dto;


import com.railway.application.entity.BookingPassenger;
import com.railway.application.entity.CoachType;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class BookingRequest {

    private Long userId;
    private Long trainScheduleId;
    private Long trainId;
    private Long sourceStationId;
    private Long destinationStationId;
    private LocalDate journeyDate;
    private CoachType coachType;
    private List<BookingPassengerDto> passengers;
}
