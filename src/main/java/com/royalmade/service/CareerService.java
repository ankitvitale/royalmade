package com.royalmade.service;


import com.royalmade.entity.Career;
import com.royalmade.repo.CareerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CareerService {

    @Autowired
    private CareerRepository repository;

    public Career create(Career career) {
        return repository.save(career);
    }

    public Career update(Long id, Career career) {
        career.setId(id);
        return repository.save(career);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Career getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Career> getAll() {
        return repository.findAll();
    }
}
