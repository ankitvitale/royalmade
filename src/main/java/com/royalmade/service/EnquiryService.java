package com.royalmade.service;

import com.royalmade.entity.Enquiry;
import com.royalmade.repo.EnquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnquiryService {

    @Autowired
    private EnquiryRepository repository;

    public Enquiry create(Enquiry enquiry) {
        return repository.save(enquiry);
    }

    public Enquiry update(Long id, Enquiry enquiry) {
        enquiry.setId(id);
        return repository.save(enquiry);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Enquiry getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Enquiry> getAll() {
        return repository.findAll();
    }
}
