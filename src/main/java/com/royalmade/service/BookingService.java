package com.royalmade.service;

import com.royalmade.dto.BookingDto;
import com.royalmade.dto.CustomerDto;
import com.royalmade.dto.ResidencyDto;
import com.royalmade.entity.Booking;
import com.royalmade.entity.Customer;
import com.royalmade.entity.Residency;
import com.royalmade.entity.enumeration.AvailabilityStatus;
import com.royalmade.repo.BookingRepository;
import com.royalmade.repo.CustomerRepository;
import com.royalmade.repo.ResidencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ResidencyRepository residencyRepository;

    @Autowired
    private CustomerRepository customerRepository;

//    public Booking createflatbooking(BookingDto bookingDto) {
//        Customer customer = new Customer();
//        customer.setName(bookingDto.getCustomerDto().getName());
//        customer.setPhoneNumber(bookingDto.getCustomerDto().getPhoneNumber());
//        customer.setEmail(bookingDto.getCustomerDto().getEmail());
//        customer.setAadharNumber(bookingDto.getCustomerDto().getAadharNumber());
//        customer.setAgentName(bookingDto.getCustomerDto().getAgentName());
//        customer.setBrokerage(bookingDto.getCustomerDto().getBrokerage());
//        customer = customerRepository.save(customer);
//
//
//        // Map BookingDto to Booking entity
//        Booking booking = new Booking();
//        booking.setDealPrice(bookingDto.getDealPrice());
//        booking.setTokenAmount(bookingDto.getTokenAmount());
//        booking.setAgreementAmount(bookingDto.getAgreementAmount());
//        booking.setStampDutyAmount(bookingDto.getStampDutyAmount());
//        booking.setRegistrationAmount(bookingDto.getRegistrationAmount());
//        booking.setGstAmount(bookingDto.getGstAmount());
//        booking.setElectricWaterAmmount(bookingDto.getElectricWaterAmmount());
//        booking.setLegalChargesAmmout(bookingDto.getLegalChargesAmmout());
//        booking.setBookedOn(bookingDto.getBookedOn());
//        booking.setBookingStatus(bookingDto.getBookingStatus());
//        booking.setCustomer(customer);
//
//        // Save the Booking entity
//        return bookingRepository.save(booking);
//    }



//    public Booking createFlatBooking(BookingDto bookingDto) {
//        if (bookingDto == null) {
//            throw new IllegalArgumentException("Booking data cannot be null.");
//        }
//
//        // Map BookingDto to Booking entity
//        Booking booking = new Booking();
//        booking.setDealPrice(bookingDto.getDealPrice());
//        booking.setTokenAmount(bookingDto.getTokenAmount());
//        booking.setAgreementAmount(bookingDto.getAgreementAmount());
//        booking.setStampDutyAmount(bookingDto.getStampDutyAmount());
//        booking.setRegistrationAmount(bookingDto.getRegistrationAmount());
//        booking.setGstAmount(bookingDto.getGstAmount());
//        booking.setElectricWaterAmmount(bookingDto.getElectricWaterAmmount());
//        booking.setLegalChargesAmmout(bookingDto.getLegalChargesAmmout());
//        booking.setBookedOn(bookingDto.getBookedOn());
//        booking.setBookingStatus(bookingDto.getBookingStatus());
//
//        // Map CustomerDto to Customer entity
//        Customer customer = mapToCustomerEntity(bookingDto.getCustomerDto());
//        booking.setCustomer(customer);
//
//        // Map ResidencyDto to Residency entity
//        Residency residency = mapToResidencyEntity(bookingDto.getResidencyDto());
//        booking.setResidency(residency);
//
//        // Save to database
//        return bookingRepository.save(booking);
//    }
//
//    private Customer mapToCustomerEntity(CustomerDto customerDto) {
//        if (customerDto == null) {
//            throw new IllegalArgumentException("Customer data cannot be null.");
//        }
//
//        // Assuming `Customer` has the fields id, name, email, phone, and address
//        Customer customer = new Customer();
//        customer.setId(customerDto.getId());
//        customer.setName(customerDto.getName());
//        customer.setEmail(customerDto.getEmail());
//        customer.setPhoneNumber(customerDto.getPhoneNumber());
//       customer.setAgentName(customerDto.getAgentName());
//       customer.setBrokerage(customerDto.getBrokerage());
//
//        // Optionally, check if the customer already exists in the database
//        return customerRepository.save(customer); // Save or update customer
//    }
//
//    private Residency mapToResidencyEntity(ResidencyDto residencyDto) {
//        if (residencyDto == null) {
//            throw new IllegalArgumentException("Residency data cannot be null.");
//        }
//
//        // Assuming `Residency` has fields id, name, address, and city
//        Residency residency = new Residency();
//        residency.setId(residencyDto.getId());
//        residency.setName(residencyDto.getName());
//        residency.setFlatType(residencyDto.getFlatType());
//        residency.setFloorNumber(residencyDto.getFloorNumber());
//        residency.setResidencyType(residencyDto.getResidencyType());
//        residency.setIdentifier(residencyDto.getIdentifier());
//        residency.setPrice(residencyDto.getPrice());
//        residency.setAvailabilityStatus(residencyDto.getAvailabilityStatus());
//      //  residency.set(residencyDto.getCity());
//
//        // Optionally, check if the residency already exists in the database
//        return residencyRepository.save(residency); // Save or update residency
//    }

    public Booking createFlatBooking(BookingDto bookingDto) {
        if (bookingDto == null) {
            throw new IllegalArgumentException("Booking data cannot be null.");
        }

        // Map BookingDto to Booking entity
        Booking booking = new Booking();
        booking.setDealPrice(bookingDto.getDealPrice());
        booking.setTokenAmount(bookingDto.getTokenAmount());
        booking.setAgreementAmount(bookingDto.getAgreementAmount());
        booking.setStampDutyAmount(bookingDto.getStampDutyAmount());
        booking.setRegistrationAmount(bookingDto.getRegistrationAmount());
        booking.setGstAmount(bookingDto.getGstAmount());
        booking.setElectricWaterAmmount(bookingDto.getElectricWaterAmmount());
        booking.setLegalChargesAmmout(bookingDto.getLegalChargesAmmout());
        booking.setBookedOn(bookingDto.getBookedOn());
        booking.setBookingStatus(bookingDto.getBookingStatus());

        // Fetch Customer entity from database if ID is provided
        if (bookingDto.getCustomerDto() != null && bookingDto.getCustomerDto().getId() != null) {
            Customer customer = customerRepository.findById(bookingDto.getCustomerDto().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Customer with ID " + bookingDto.getCustomerDto().getId() + " not found."));
            booking.setCustomer(customer);
        } else {
            throw new IllegalArgumentException("Customer ID is required.");
        }

        // Fetch Residency entity from database if ID is provided
        if (bookingDto.getResidencyDto() != null && bookingDto.getResidencyDto().getId() != null) {
            Residency residency = residencyRepository.findById(bookingDto.getResidencyDto().getId())
      //      Residency residency = residencyRepository.findByIdentifier(bookingDto.getResidencyDto().getIdentifier())

                    .orElseThrow(() -> new IllegalArgumentException("Residency with ID " + bookingDto.getResidencyDto().getIdentifier() + " not found."));
         residency.setAvailabilityStatus(AvailabilityStatus.BOOKED);
            booking.setResidency(residency);
        } else {
            throw new IllegalArgumentException("Residency ID is required.");
        }

        // Save to database
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {

        return bookingRepository.findAll();
    }
}
