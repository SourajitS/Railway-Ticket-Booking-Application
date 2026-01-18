package com.railway.application.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainRoute {

    @ManyToOne
    @JoinColumn(name = "train_id")
    private Train train;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;
    private Integer stationOrder;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
    private Integer haltMinutes;
    private Integer distanceFromSource;

}
