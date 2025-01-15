package com.royalmade.service;

import com.royalmade.dto.*;
import com.royalmade.entity.*;
import com.royalmade.entity.enumeration.AvailabilityStatus;
import com.royalmade.entity.enumeration.BookingStatus;
import com.royalmade.repo.BookingInstallmentRepository;
import com.royalmade.repo.BookingRepository;
import com.royalmade.repo.CustomerRepository;
import com.royalmade.repo.ResidencyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ResidencyRepository residencyRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookingInstallmentRepository bookingInstallmentRepository;

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
//        customer.setAadharNumber(customerDto.getAadharNumber());
//        customer.setPanCard(customerDto.getPanCard());
//        customer.setAddress(customerDto.getAddress());
//        customer.setPhoneNumber(customerDto.getPhoneNumber());
//        customer.setAgentName(customerDto.getAgentName());
//        customer.setBrokerage(customerDto.getBrokerage());
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
//        // Fetch Customer entity from database if ID is provided
//        if (bookingDto.getCustomerDto() != null && bookingDto.getCustomerDto().getId() != null) {
//            Customer customer = customerRepository.findById(bookingDto.getCustomerDto().getId())
//                    .orElseThrow(() -> new IllegalArgumentException("Customer with ID " + bookingDto.getCustomerDto().getId() + " not found."));
//            booking.setCustomer(customer);
//        } else {
//            throw new IllegalArgumentException("Customer ID is required.");
//        }
//
//        // Fetch Residency entity from database if ID is provided
//        if (bookingDto.getResidencyDto() != null && bookingDto.getResidencyDto().getId() != null) {
//            Residency residency = residencyRepository.findById(bookingDto.getResidencyDto().getId())
//      //      Residency residency = residencyRepository.findByIdentifier(bookingDto.getResidencyDto().getIdentifier())
//                    .orElseThrow(() -> new IllegalArgumentException("Residency with ID " + bookingDto.getResidencyDto().getIdentifier() + " not found."));
//         residency.setAvailabilityStatus(AvailabilityStatus.BOOKED);
//            booking.setResidency(residency);
//        } else {
//            throw new IllegalArgumentException("Residency ID is required.");
//        }
//        // Save to database
//        return bookingRepository.save(booking);
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

        if (bookingDto.getCustomerDto() != null) {
            CustomerDto customerDto = bookingDto.getCustomerDto();

            // Check if customer exists, or create a new one if the ID is null
            Customer customer;
            if (customerDto.getId() != null) {
                customer = customerRepository.findById(customerDto.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Customer with ID " + customerDto.getId() + " not found."));
            } else {
                // If ID is not provided, create a new customer
                customer = new Customer();
                customer.setName(customerDto.getName());
                customer.setEmail(customerDto.getEmail());
                customer.setAadharNumber(customerDto.getAadharNumber());
                customer.setPanCard(customerDto.getPanCard());
                customer.setAddress(customerDto.getAddress());
                customer.setPhoneNumber(customerDto.getPhoneNumber());
                customer.setAgentName(customerDto.getAgentName());
                customer.setBrokerage(customerDto.getBrokerage());
                customer.setLoan(customerDto.getLoan());
                customer.setBankName(customerDto.getBankName());
                customer.setLoanAmount(customerDto.getLoanAmount());

                customer = customerRepository.save(customer); // Save the new customer
            }

            booking.setCustomer(customer); // Set the customer on the booking entity
        }

        // Handling Residency entity
        if (bookingDto.getResidencyDto() != null && bookingDto.getResidencyDto().getId() != null) {
            Residency residency = residencyRepository.findById(bookingDto.getResidencyDto().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Residency with ID " + bookingDto.getResidencyDto().getId() + " not found."));
            residency.setAvailabilityStatus(AvailabilityStatus.BOOKED);
            booking.setResidency(residency);
        } else {
            throw new IllegalArgumentException("Residency ID is required.");
        }

        // Save the booking to the database
        return bookingRepository.save(booking);
    }


    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public BookingSummaryDTO getBookingSummary(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking with ID " + id + " not found"));

        BookingSummaryDTO dto = new BookingSummaryDTO();
        dto.setCustomerName(booking.getCustomer().getName());
        dto.setResidencyName(booking.getResidency().getName());
        dto.setIdentifier(booking.getResidency().getIdentifier());
        dto.setDealPrice(booking.getDealPrice());
        dto.setTokenAmount(booking.getTokenAmount());
        dto.setAgreementAmount(booking.getAgreementAmount());

        List<BookingInstallmentDTO> installmentDTOs = new ArrayList<>();

        for (BookingInstallment installment : booking.getBookingInstallments()) {
            BookingInstallmentDTO installmentDTO = new BookingInstallmentDTO();
            installmentDTO.setId(installment.getId());
            installmentDTO.setInstallmentDate(installment.getInstallmentDate().toString());
            installmentDTO.setInstallmentAmount(installment.getInstallmentAmount());
            installmentDTO.setInstallmentStatus(installment.getInstallmentStatus().toString());
            installmentDTOs.add(installmentDTO);
        }

        dto.setBookingInstallments(installmentDTOs);

        // Calculate remaining amount
        double totalInstallmentsPaid = installmentDTOs.stream()
                .mapToDouble(BookingInstallmentDTO::getInstallmentAmount)
                .sum();
        dto.setRemainingAmount(dto.getDealPrice() - dto.getTokenAmount()-dto.getAgreementAmount()-totalInstallmentsPaid);

        return dto;
    }
    public List<Booking> getAllCompleteBookings() {
        return bookingRepository.findByBookingStatusAndResidency_AvailabilityStatus(BookingStatus.COMPLETE, AvailabilityStatus.BOOKED);
//        return bookingRepository.findByBookingStatus(BookingStatus.COMPLETE);
    }


    public List<Booking> getAllCancelleBookings() {
        return bookingRepository.findByBookingStatus(BookingStatus.CANCELLED);
    }
    public Booking getBookingByID(Long id) {
        return bookingRepository.findById(id).orElseThrow(()-> new RuntimeException("Booking ID is not present"));
    }


    public Booking updateFlatBooking(Long bookingId, BookingDto bookingDto) {
        if (bookingDto == null) {
            throw new IllegalArgumentException("Booking data cannot be null.");
        }

        // Find the existing booking by ID
        Booking existingBooking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking with ID " + bookingId + " not found."));

        // Update Booking details
        existingBooking.setDealPrice(bookingDto.getDealPrice());
        existingBooking.setTokenAmount(bookingDto.getTokenAmount());
        existingBooking.setAgreementAmount(bookingDto.getAgreementAmount());
        existingBooking.setStampDutyAmount(bookingDto.getStampDutyAmount());
        existingBooking.setRegistrationAmount(bookingDto.getRegistrationAmount());
        existingBooking.setGstAmount(bookingDto.getGstAmount());
        existingBooking.setElectricWaterAmmount(bookingDto.getElectricWaterAmmount());
        existingBooking.setLegalChargesAmmout(bookingDto.getLegalChargesAmmout());
        existingBooking.setBookedOn(bookingDto.getBookedOn());
        existingBooking.setBookingStatus(bookingDto.getBookingStatus());

        // Update Customer details
        if (bookingDto.getCustomerDto() != null) {
            CustomerDto customerDto = bookingDto.getCustomerDto();
            Customer customer;

            if (customerDto.getId() != null) {
                // Update existing customer
                customer = customerRepository.findById(customerDto.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Customer with ID " + customerDto.getId() + " not found."));
            } else {
                // Create a new customer if ID is not provided
                customer = new Customer();
            }

            customer.setName(customerDto.getName());
            customer.setEmail(customerDto.getEmail());
            customer.setAadharNumber(customerDto.getAadharNumber());
            customer.setPanCard(customerDto.getPanCard());
            customer.setAddress(customerDto.getAddress());
            customer.setPhoneNumber(customerDto.getPhoneNumber());
            customer.setAgentName(customerDto.getAgentName());
            customer.setBrokerage(customerDto.getBrokerage());
            customer = customerRepository.save(customer); // Save customer changes
            existingBooking.setCustomer(customer); // Set the updated customer
        }

        // Update Residency details

//            Residency residency = residencyRepository.findById(bookingDto.getResidencyDto().getId())
//                    .orElseThrow(() -> new IllegalArgumentException("Residency with ID " + bookingDto.getResidencyDto().getId() + " not found."));
//            residency.setAvailabilityStatus(AvailabilityStatus.BOOKED);
//            existingBooking.setResidency(residency);
//

        // Save updated booking to the database
        return bookingRepository.save(existingBooking);
    }

    public Booking cancelFlatBooking(Long bookingId) {
        if (bookingId == null) {
            throw new IllegalArgumentException("Booking ID cannot be null.");
        }

        // Retrieve the existing booking by ID
        Booking existingBooking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking with ID " + bookingId + " not found."));

        // Update booking status to CANCELLED
        existingBooking.setBookingStatus(BookingStatus.CANCELLED);

        // Update residency status to AVAILABLE if linked residency exists
        Residency residency = existingBooking.getResidency();
        if (residency != null) {
            residency.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
            residency.setBooking(null);

            residencyRepository.save(residency);
        }

        // Save the updated booking to the database
        return bookingRepository.save(existingBooking);
    }


    public Booking addInstallment(Long id, List<BookingInstallment> bookingInstallments) {
        Booking booking=bookingRepository.findById(id).orElseThrow(()-> new RuntimeException("Booking "+id+" is not found"));

        for(BookingInstallment  installment:bookingInstallments){
            installment.setBooking(booking);
        }

        booking.getBookingInstallments().addAll(bookingInstallments);

         bookingInstallmentRepository.saveAll(bookingInstallments);
         return booking;

    }
}
