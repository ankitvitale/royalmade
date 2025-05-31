package com.royalmade.service;


import com.royalmade.entity.LoanEnquiry;
import com.royalmade.repo.LoanEnquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanEnquiryService {

    @Autowired
    private LoanEnquiryRepository repository;

    public LoanEnquiry create(LoanEnquiry enquiry) {
        return repository.save(enquiry);
    }

    public LoanEnquiry update(Long id, LoanEnquiry enquiry) {
        enquiry.setId(id);
        return repository.save(enquiry);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public LoanEnquiry getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<LoanEnquiry> getAll() {
        return repository.findAll();
    }
}
