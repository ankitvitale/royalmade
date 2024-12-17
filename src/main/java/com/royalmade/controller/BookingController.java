package com.royalmade.controller;

import com.royalmade.dto.BookingDto;
import com.royalmade.entity.Booking;
import com.royalmade.entity.Land;
import com.royalmade.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("/createBooking")
    public ResponseEntity<Booking> createflatbooking(@RequestBody BookingDto bookingDto){
        Booking flatbooking=bookingService.createFlatBooking(bookingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(flatbooking);

    }

    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

}
