package com.railway.application.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trainId;
    private String number;
    private String trainName;
    private Integer totalDistance;
    @ManyToOne
    private Stations sourceStations;
    @ManyToOne
    private Stations destinationStations;
    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    private TrainImage trainImage;


}
