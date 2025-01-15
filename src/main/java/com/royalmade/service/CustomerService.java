package com.royalmade.service;

import com.royalmade.dto.CustomerDto;
import com.royalmade.dto.LoanDto;
import com.royalmade.entity.Customer;
import com.royalmade.repo.BookingRepository;
import com.royalmade.repo.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        customer.setAddress(customerDto.getAddress());
        customer.setPanCard(customerDto.getPanCard());
        customer.setAgentName(customerDto.getAgentName());
        customer.setBrokerage(customerDto.getBrokerage());
        customer.setLoan(customerDto.getLoan());
        customer.setBankName(customerDto.getBankName());
        customer.setLoanAmount(customerDto.getLoanAmount());

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

    //@Transactional
    public Customer updateCustomerById(Long id, CustomerDto customerDto) {
        // Fetch the existing customer by ID
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

        // Update fields from the DTO
        existingCustomer.setName(customerDto.getName());
        existingCustomer.setPhoneNumber(customerDto.getPhoneNumber());
        existingCustomer.setEmail(customerDto.getEmail());
        existingCustomer.setAadharNumber(customerDto.getAadharNumber());
        existingCustomer.setAddress(customerDto.getAddress());
        existingCustomer.setPanCard(customerDto.getPanCard());
        existingCustomer.setAgentName(customerDto.getAgentName());
        existingCustomer.setBrokerage(customerDto.getBrokerage());
        existingCustomer.setLoan(customerDto.getLoan());
        existingCustomer.setBankName(customerDto.getBankName());
        existingCustomer.setLoanAmount(customerDto.getLoanAmount());

        // Save the updated customer
        return customerRepository.save(existingCustomer);
    }

    public Customer addBankDetails(Long id, LoanDto loanDto) {
        // Find customer by ID
        Optional<Customer> customerOptional = customerRepository.findById(id);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();

            // Update customer details from LoanDto
            customer.setBankName(loanDto.getBankName());
            customer.setLoanAmount(loanDto.getLoanAmount());

            // Save the updated customer back to the database
            return customerRepository.save(customer);

        } else {
            throw new RuntimeException("Customer not found with id: " + id);
        }

    }
}
