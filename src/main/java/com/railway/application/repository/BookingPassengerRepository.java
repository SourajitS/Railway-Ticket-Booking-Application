package com.railway.application.repository;

import com.railway.application.entity.BookingPassenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingPassengerRepository extends JpaRepository<BookingPassenger,Long> {
}
