package com.royalmade.service;

import com.royalmade.dto.CustomerDto;
import com.royalmade.entity.Customer;
import com.royalmade.repo.BookingRepository;
import com.royalmade.repo.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;


    @Autowired
    private BookingRepository bookingRepository;
    public Customer AddCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setEmail(customerDto.getEmail());
        customer.setAadharNumber(customerDto.getAadharNumber());
        customer.setAgentName(customerDto.getAgentName());
        customer.setBrokerage(customerDto.getBrokerage());

        // Save the customer entity
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));
    }

    @Transactional
    public void deleteCustomerById(Long id) {
        // Check if the customer exists
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

        // Delete related booking if it exists
        if (customer.getBooking() != null) {
            bookingRepository.delete(customer.getBooking());
        }

        // Delete the customer
        customerRepository.delete(customer);
    }

    @Transactional
    public Customer updateCustomerById(Long id, CustomerDto customerDto) {
        // Fetch the existing customer by ID
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

        // Update fields from the DTO
        existingCustomer.setName(customerDto.getName());
        existingCustomer.setPhoneNumber(customerDto.getPhoneNumber());
        existingCustomer.setEmail(customerDto.getEmail());
        existingCustomer.setAadharNumber(customerDto.getAadharNumber());
        existingCustomer.setAgentName(customerDto.getAgentName());
        existingCustomer.setBrokerage(customerDto.getBrokerage());

        // Save the updated customer
        return customerRepository.save(existingCustomer);
    }
}
