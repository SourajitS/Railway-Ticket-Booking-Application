package com.railway.application.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainScheduleDto {

    private Long id;
    private Long trainId;
    private LocalDateTime runDate;
    private Integer availableSeats;
}
