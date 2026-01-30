package com.railway.application.repository;

import com.railway.application.entity.Train;
import com.railway.application.entity.TrainRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrainRouteRepository extends JpaRepository<TrainRoute,Long> {
  // @Query("select trainRoute from TrainRoute where train_id=? order by station_order asc")
   @Query("""
           
    SELECT tr
    FROM TrainRoute tr
    WHERE tr.train.trainId = ?1
    ORDER BY tr.stationOrder ASC
""")
    List<TrainRoute> findRouteByTrainId(Long trainId);
}
