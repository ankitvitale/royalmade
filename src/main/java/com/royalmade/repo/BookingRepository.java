package com.royalmade.repo;

import com.royalmade.entity.Booking;
import com.royalmade.entity.enumeration.AvailabilityStatus;
import com.royalmade.entity.enumeration.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    List<Booking> findByBookingStatus(BookingStatus bookingStatus);
    List<Booking> findByBookingStatusAndResidency_AvailabilityStatus(
            BookingStatus bookingStatus,
            AvailabilityStatus availabilityStatus
    );



    Booking findBycustomer_id(Long id);
}
