package com.railway.application.repository;

import com.railway.application.entity.TrainSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

public interface TrainSeatRepository extends JpaRepository<TrainSeat,Long> {

    @Query("select ts from TrainSeat ts where ts.trainSchedule.id=?1 order by ts.trainSeatOrder asc")
    List<TrainSeat> findByTrainScheduleId(Long trainScheduleId);
}
