package com.railway.application.controllers.user;

import com.railway.application.dto.BookingRequest;
import com.railway.application.dto.BookingResponse;
import com.railway.application.service.BookingService;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    //create booking

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest bookingRequest)
    {
       BookingResponse bookingResponse= this.bookingService.createBooking(bookingRequest);
        return new ResponseEntity<BookingResponse>(bookingResponse, HttpStatus.CREATED);
    }
}
