package com.royalmade.controller;


import com.royalmade.entity.Career;
import com.royalmade.service.CareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/careers")
public class CareerController {

    @Autowired
    private CareerService service;

    @PostMapping
    public Career create(@RequestBody Career career) {
        return service.create(career);
    }

    @PutMapping("/{id}")
    public Career update(@PathVariable Long id, @RequestBody Career career) {
        return service.update(id, career);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/{id}")
    public Career getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<Career> getAll() {
        return service.getAll();
    }
}
