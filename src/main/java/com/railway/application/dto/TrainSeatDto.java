package com.railway.application.dto;

import com.railway.application.entity.CoachType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainSeatDto {

    private Long id;
    private Long trainScheduleId;
    private CoachType coachType;
    private Integer totalSeats;
    private int availableSeats;
    private Integer trainSeatOrder;
    private Double price;
    private Integer nextSeatToAssign;
}
