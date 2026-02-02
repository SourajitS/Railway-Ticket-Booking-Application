package com.railway.application.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime runDate;
    @ManyToOne
    @JoinColumn(name = "train_id")
    private Train train;

    private Integer availableSeats;

    @OneToMany(mappedBy = "trainSchedule")
    private List<TrainSeat> trainSeats;
    @OneToMany(mappedBy = "trainSchedule")
    private List<Booking> bookings;
}
