package com.royalmade.controller;


import com.royalmade.entity.LoanEnquiry;
import com.royalmade.service.LoanEnquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan-enquiries")
public class LoanEnquiryController {

    @Autowired
    private LoanEnquiryService service;

    @PostMapping
    public LoanEnquiry create(@RequestBody LoanEnquiry enquiry) {
        return service.create(enquiry);
    }

    @PutMapping("/{id}")
    public LoanEnquiry update(@PathVariable Long id, @RequestBody LoanEnquiry enquiry) {
        return service.update(id, enquiry);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/{id}")
    public LoanEnquiry getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<LoanEnquiry> getAll() {
        return service.getAll();
    }
}
