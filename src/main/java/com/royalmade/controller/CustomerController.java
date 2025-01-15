package com.royalmade.controller;

import com.royalmade.dto.CustomerDto;
import com.royalmade.dto.LoanDto;
import com.royalmade.dto.RemarkRequest;
import com.royalmade.entity.Customer;
import com.royalmade.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/addCustomer")
    public ResponseEntity<Customer> AddCustomer(@RequestBody CustomerDto customerDto){
        Customer addcustomer=customerService.AddCustomer(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addcustomer);
    }
    @GetMapping("/getAllcustomer")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/getAllcustomer/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/customerdelete/{id}")
    public ResponseEntity<String> deleteCustomerById(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.ok("Customer deleted successfully");
    }
    @PutMapping("/customerupdate/{id}")
    public ResponseEntity<Customer> updateCustomerById(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
        Customer updatedCustomer = customerService.updateCustomerById(id, customerDto);
        return ResponseEntity.ok(updatedCustomer);
    }

    @PostMapping("/addLoanDetails/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Customer> addBankDetails(@PathVariable Long id, @RequestBody LoanDto loanDto){
        Customer customer=customerService.addBankDetails(id,loanDto);
        return ResponseEntity.ok(customer);
    }

}
