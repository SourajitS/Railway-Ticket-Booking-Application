package com.railway.application.service.impl;

import com.railway.application.dto.BookingPassengerDto;
import com.railway.application.dto.BookingRequest;
import com.railway.application.dto.BookingResponse;
import com.railway.application.dto.StationDto;
import com.railway.application.entity.*;
import com.railway.application.exceptions.ResourceNotFoundException;
import com.railway.application.repository.*;
import com.railway.application.service.BookingService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class BookingServiceImpl implements BookingService {

private BookingRepository bookingRepository;
private BookingPassengerRepository bookingPassengerRepository;
private UserRepository userRepository;
private TrainScheduleRepository trainScheduleRepository;
private TrainRepository trainRepository;
private StationRepository stationRepository;
private TrainSeatRepository trainSeatRepository;
private ModelMapper modelMapper;
    @Override
    public BookingResponse createBooking(BookingRequest bookingRequest) {

        User user = userRepository.findById(bookingRequest.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + bookingRequest.getUserId()));
        TrainSchedule trainSchedule = trainScheduleRepository.findById(bookingRequest.getTrainScheduleId()).orElseThrow(() -> new ResourceNotFoundException("TrainSchedule not found with id :" + bookingRequest.getTrainScheduleId()));

        Station sourceStation = stationRepository.findById(bookingRequest.getSourceStationId()).orElseThrow(() -> new ResourceNotFoundException("Station not found with id :" + bookingRequest.getSourceStationId()));
        Station destinationStation = stationRepository.findById(bookingRequest.getDestinationStationId()).orElseThrow(() -> new ResourceNotFoundException("Station not found with id :" + bookingRequest.getDestinationStationId()));

        List<TrainSeat> coaches = trainSchedule.getTrainSeats();
        coaches.sort((s1,s2)-> s1.getTrainSeatOrder()- s2.getTrainSeatOrder());
        List<TrainSeat> selectedCoaches = coaches.stream().filter(coach -> coach.getCoachType() == bookingRequest.getCoachType()).toList();
        int totalRequestedSeat = bookingRequest.getPassengers().size();

        TrainSeat coachToBookSeat=null;
        for(TrainSeat coach:selectedCoaches)
        {
            if(coach.isSeatAvailable(totalRequestedSeat))
            {
                coachToBookSeat=coach;
                break;
            }
        }
if(coachToBookSeat==null)
{
    throw new ResourceNotFoundException("No seats available in this coach");
}

//booking seat
Booking booking = new Booking();
booking.setBookingStatus(BookingStatus.CONFIRMED);
booking.setSourceStation(sourceStation);
booking.setDestinationStation(destinationStation);
booking.setTrainSchedule(trainSchedule);
booking.setUser(user);
booking.setCreatedAt(LocalDateTime.now());
booking.setJourneyDate(trainSchedule.getRunDate());
booking.setPnr(UUID.randomUUID().toString());

//set total fare
        booking.setTotalFare(new BigDecimal(totalRequestedSeat*coachToBookSeat.getPrice()));

        Payment payment = new Payment();

        payment.setAmount(booking.getTotalFare());
        payment.setPaymentStatus(PaymentStatus.NOT_PAID);
        payment.setBooking(booking);

        //set payment

        booking.setPayment(payment);


        List<BookingPassenger> passengers=new ArrayList<>();
        for(BookingPassengerDto passengerDto: bookingRequest.getPassengers())
        {
            BookingPassenger passenger = modelMapper.map(passengerDto, BookingPassenger.class);
            passenger.setBooking(booking);
            passenger.setTrainSeat(coachToBookSeat);
            passenger.setSeatNumber(coachToBookSeat.getNextSeatToAssign()+"");
            coachToBookSeat.setNextSeatToAssign(coachToBookSeat.getNextSeatToAssign()+1);
            coachToBookSeat.setAvailableSeats(coachToBookSeat.getAvailableSeats()-1);

            passengers.add(passenger);
        }

        booking.setPassengers(passengers);

        Booking savedBooking = bookingRepository.save(booking);
        trainSeatRepository.save(coachToBookSeat);


        BookingResponse bookingResponse=new BookingResponse();
        bookingResponse.setBookingId(savedBooking.getId());
        bookingResponse.setPnr(savedBooking.getPnr());
        bookingResponse.setTotalFare(savedBooking.getTotalFare());
        bookingResponse.setStatus(savedBooking.getBookingStatus());
        bookingResponse.setSourceStation(modelMapper.map(sourceStation, StationDto.class));
        bookingResponse.setDestinationStation(modelMapper.map(destinationStation, StationDto.class));

 bookingResponse.setJourneyDate(trainSchedule.getRunDate());
 bookingResponse.setPaymentStatus(savedBooking.getPayment().getPaymentStatus());


 bookingResponse.setPassengers(
         savedBooking.getPassengers().stream().map(
                 passenger->{
                     BookingPassengerDto bookingPassengerDto=modelMapper.map(passenger,BookingPassengerDto.class);
                     bookingPassengerDto.setCoachId(passenger.getTrainSeat().getId()+"");
                     return bookingPassengerDto;
                 }).toList()
 );

        TrainRoute sourceRoute = trainSchedule.getTrain().getTrainRoutes().stream().filter(trainRoute -> trainRoute.getStation().getId().equals(sourceStation.getId())).findFirst().get();
        bookingResponse.setDepartureTime(sourceRoute.getDepartureTime());
        bookingResponse.setArrivalTime(sourceRoute.getArrivalTime());


        return bookingResponse;
    }
}
