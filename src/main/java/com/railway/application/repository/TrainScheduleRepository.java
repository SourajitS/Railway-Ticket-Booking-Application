package com.railway.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.railway.application.entity.TrainSchedule;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TrainScheduleRepository extends JpaRepository<TrainSchedule,Long> {

//    @Query("Select ts from TrainSchedule ts where ts.train.id=?1")
@Query("SELECT ts FROM TrainSchedule ts JOIN FETCH ts.train t WHERE ts.train.id = ?1")
List<TrainSchedule> findScheduleByTrainId(Long trainId);

    //@Query("SELECT ts FROM TrainSchedule ts WHERE ts.train.id=?1 AND ts.runDate=?2")
    @Query("SELECT ts FROM TrainSchedule ts JOIN FETCH ts.train t LEFT JOIN FETCH ts.trainSeats WHERE ts.train.id = ?1")
    Optional<TrainSchedule> findByTrainIdAndRunDate(Long trainId, LocalDate runDate);
}
