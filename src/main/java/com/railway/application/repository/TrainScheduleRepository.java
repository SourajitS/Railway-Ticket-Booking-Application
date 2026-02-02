package com.railway.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.railway.application.entity.TrainSchedule;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrainScheduleRepository extends JpaRepository<TrainSchedule,Long> {

    @Query("Select ts from TrainSchedule ts where ts.train.id=?1")
    List<TrainSchedule> findScheduleByTrainId(Long trainId);
}
