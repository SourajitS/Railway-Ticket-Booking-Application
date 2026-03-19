package com.railway.application.dto;

import com.railway.application.entity.BookingStatus;
import com.railway.application.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {

    private Long bookingId;
    private LocalDate journeyDate;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private StationDto sourceStation;
    private StationDto destinationStation;
    private String pnr;
    private BigDecimal totalFare;
    private BookingStatus status;
    private PaymentStatus paymentStatus;
    private List<BookingPassengerDto> passengers;
}
