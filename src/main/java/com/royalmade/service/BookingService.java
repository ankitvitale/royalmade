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
import jakarta.transaction.Transactional;
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
            installmentDTO.setInstallmentDate(installment.getInstallmentDate());
            installmentDTO.setInstallmentAmount(installment.getInstallmentAmount());
            installmentDTO.setInstallmentStatus(installment.getInstallmentStatus());
            installmentDTO.setRemark(installment.getRemark());

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

    @Transactional
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

        if (bookingDto.getCustomerDto() != null) {
            CustomerDto customerDto = bookingDto.getCustomerDto();
            Customer customer;

            if (customerDto.getId() != null) {
                customer = customerRepository.findById(customerDto.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Customer with ID " + customerDto.getId() + " not found."));
            } else {
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

            Customer savedCustomer = customerRepository.save(customer);
            existingBooking.setCustomer(savedCustomer);
        }


        // Update Residency details

//            Residency residency = residencyRepository.findById(bookingDto.getResidencyDto().getId())
//                    .orElseThrow(() -> new IllegalArgumentException("Residency with ID " + bookingDto.getResidencyDto().getId() + " not found."));
//            residency.setAvailabilityStatus(AvailabilityStatus.BOOKED);
//            existingBooking.setResidency(residency);


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


//    public BookingPaymentDto addInstallment(Long id, List<BookingInstallment> bookingInstallments) {
//        Booking booking=bookingRepository.findById(id).orElseThrow(()-> new RuntimeException("Booking "+id+" is not found"));
//
//        for(BookingInstallment  installment:bookingInstallments){
//            installment.setBooking(booking);
//        }
//
//        booking.getBookingInstallments().addAll(bookingInstallments);
//
//         bookingInstallmentRepository.saveAll(bookingInstallments);
//         return booking;
//
//    }

    public BookingPaymentDto addInstallment(Long id, List<BookingInstallmentDTO> bookingInstallmentDtos) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking " + id + " is not found"));

        List<BookingInstallment> bookingInstallments = bookingInstallmentDtos.stream().map(dto -> {
            BookingInstallment installment = new BookingInstallment();
            installment.setInstallmentDate(dto.getInstallmentDate());
            installment.setInstallmentAmount(dto.getInstallmentAmount());
            installment.setRemark(dto.getRemark());
            installment.setInstallmentStatus(dto.getInstallmentStatus());
            installment.setBooking(booking);
            return installment;
        }).collect(Collectors.toList());

        booking.getBookingInstallments().addAll(bookingInstallments);
        bookingInstallmentRepository.saveAll(bookingInstallments);

        return new BookingPaymentDto(booking);
    }

    public BookingInstallment updateBookingInstallment(Long id, BookingInstallmentDTO bookingInstallmentDTO) {
        BookingInstallment bookingInstallment=bookingInstallmentRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("BookingInstallment"+id+"Id Not Found"));

            bookingInstallment.setInstallmentDate(bookingInstallmentDTO.getInstallmentDate());
            bookingInstallment.setInstallmentAmount(bookingInstallmentDTO.getInstallmentAmount());
            bookingInstallment.setRemark(bookingInstallmentDTO.getRemark());
            bookingInstallment.setInstallmentStatus(bookingInstallmentDTO.getInstallmentStatus());
            return bookingInstallmentRepository.save(bookingInstallment);
    }

    public void deleteBookingInstallment(Long id) {
        BookingInstallment bookingInstallment=bookingInstallmentRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("BookingInstallment"+id+"Id Not Found"));
        bookingInstallmentRepository.delete(bookingInstallment);
    }

    public BookingInstallmentDTO getBookingInstallmentById(Long id) {
        BookingInstallment bookingInstallment = bookingInstallmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("This Booking ID is not found"));

        BookingInstallmentDTO dto = new BookingInstallmentDTO();
        dto.setId(bookingInstallment.getId());
        dto.setInstallmentAmount(bookingInstallment.getInstallmentAmount());
        dto.setInstallmentDate(bookingInstallment.getInstallmentDate());
        dto.setInstallmentStatus(bookingInstallment.getInstallmentStatus());
        dto.setRemark(bookingInstallment.getRemark());

        return dto;
    }

    public Booking getBookingByCoustomerId(Long id) {
        return bookingRepository.findBycustomer_id(id);
    }
}
