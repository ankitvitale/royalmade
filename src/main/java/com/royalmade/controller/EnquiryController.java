package com.royalmade.controller;


import com.royalmade.entity.Enquiry;
import com.royalmade.service.EnquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enquiries")
public class EnquiryController {

    @Autowired
    private EnquiryService service;

    @PostMapping
    public Enquiry create(@RequestBody Enquiry enquiry) {
        return service.create(enquiry);
    }

    @PutMapping("/{id}")
    public Enquiry update(@PathVariable Long id, @RequestBody Enquiry enquiry) {
        return service.update(id, enquiry);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/{id}")
    public Enquiry getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<Enquiry> getAll() {
        return service.getAll();
    }
}
