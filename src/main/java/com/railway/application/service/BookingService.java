package com.railway.application.service;


import com.railway.application.dto.BookingRequest;
import com.railway.application.dto.BookingResponse;

public interface BookingService {


    BookingResponse createBooking(BookingRequest bookingRequest);
}
