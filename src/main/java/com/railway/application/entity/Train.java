package com.railway.application.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
    @JoinColumn(name = "source_stations_id")
    private Station sourceStation;
    @ManyToOne
    @JoinColumn(name="destination_stations_id")
    private Station destinationStation;
    @OneToMany(mappedBy = "train")
    private List<TrainRoute> trainRoutes;
    @OneToMany(mappedBy ="train")
    private List<TrainSchedule> trainSchedules;
    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    private TrainImage trainImage;


}
