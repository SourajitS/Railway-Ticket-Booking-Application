package com.railway.application.repository;

import com.railway.application.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainRepo extends JpaRepository<Train, Long> {
}
