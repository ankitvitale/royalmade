package com.royalmade.controller;

import com.royalmade.dto.BookingDto;
import com.royalmade.dto.BookingInstallmentDTO;
import com.royalmade.dto.BookingPaymentDto;
import com.royalmade.dto.BookingSummaryDTO;
import com.royalmade.entity.Booking;
import com.royalmade.entity.BookingInstallment;
import com.royalmade.entity.Land;
import com.royalmade.entity.enumeration.AvailabilityStatus;
import com.royalmade.entity.enumeration.BookingStatus;
import com.royalmade.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("/createBooking")
    public ResponseEntity<Booking> createflatbooking(@RequestBody BookingDto bookingDto){
        Booking flatbooking=bookingService.createFlatBooking(bookingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(flatbooking);

    }
//    @PostMapping("/{id}/addInstallment")
//    @PreAuthorize("hasRole('Admin')")
//    public ResponseEntity<Booking> addinstallment(@PathVariable Long id, @RequestBody List<BookingInstallment> bookingInstallments){
//        Booking addInstallment=bookingService.addInstallment(id,bookingInstallments);
//        return ResponseEntity.ok(addInstallment);
//    }

//    @PostMapping("/{id}/addInstallment")
//    @PreAuthorize("hasRole('Admin')")
//    public ResponseEntity<BookingPaymentDto> addinstallment(@PathVariable Long id, @RequestBody BookingInstallment[] bookingInstallments) {
//        Booking addInstallment = bookingService.addInstallment(id, Arrays.asList(bookingInstallments));
//        return ResponseEntity.ok(addInstallment);
//    }
@PostMapping("/{id}/addInstallment")
@PreAuthorize("hasRole('Admin')")
public ResponseEntity<BookingPaymentDto> addinstallment(
        @PathVariable Long id,
        @RequestBody List<BookingInstallmentDTO> bookingInstallmentDtos) {

    BookingPaymentDto addInstallment = bookingService.addInstallment(id, bookingInstallmentDtos);
    return ResponseEntity.ok(addInstallment);
}


    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }
    @GetMapping("/BookingSummary/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<BookingSummaryDTO> getBookingSummary(@PathVariable Long id) {
        BookingSummaryDTO bookingSummary = bookingService.getBookingSummary(id);
        return ResponseEntity.ok(bookingSummary);
    }

    @GetMapping("/bookings/complete")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<List<Booking>> getAllCompleteBookings() {
        List<Booking> completeBookings = bookingService.getAllCompleteBookings();
        return ResponseEntity.ok(completeBookings);
    }


    @GetMapping("/bookings/cancelle")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<List<Booking>> getAllCancelleBookings(){
        List<Booking> cancelleBookings=bookingService.getAllCancelleBookings();
        return ResponseEntity.ok(cancelleBookings);
    }
    @GetMapping("/booking/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id){
        Booking booking=bookingService.getBookingByID(id);
        return ResponseEntity.ok(booking);
    }
    @PutMapping("/updateBooking/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Booking> updateFlatBooking(@PathVariable Long id, @RequestBody BookingDto bookingDto) {
        Booking updatedBooking = bookingService.updateFlatBooking(id, bookingDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedBooking);
    }

    @PutMapping("/cancelBooking/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Booking> cancelFlatBooking(@PathVariable Long id) {
        Booking cancelledBooking = bookingService.cancelFlatBooking(id);
        return ResponseEntity.ok(cancelledBooking);
    }


}
