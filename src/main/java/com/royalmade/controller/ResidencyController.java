package com.royalmade.controller;

import com.royalmade.dto.ResidencyDto;
import com.royalmade.entity.Residency;
import com.royalmade.service.ResidencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ResidencyController {

    @Autowired
    private ResidencyService residencyService;


    @PostMapping("/createResidency")
    public ResponseEntity<Residency> createResidency(@RequestBody ResidencyDto residencyDto){
        Residency createResidency= residencyService.createResidency(residencyDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createResidency);

    }

    @GetMapping("/allResidency")
    public ResponseEntity<List<Residency>> getAllResidencies() {
        List<Residency> residencies = residencyService.getAllResidencies();
        return ResponseEntity.ok(residencies);
    }

    // Get residency by ID
    @GetMapping("/allResidencybyid/{id}")
    public ResponseEntity<Residency> getResidencyById(@PathVariable Long id) {
        Residency residency = residencyService.getResidencyById(id);
        return ResponseEntity.ok(residency);
    }

    //upade residency by ID
    @PutMapping("/updateresidency/{id}")
    public ResponseEntity<Residency> updateResidency(@PathVariable Long id, @RequestBody ResidencyDto residencyDto) {
        Residency updatedResidency = residencyService.updateResidency(id, residencyDto);
        return ResponseEntity.ok(updatedResidency);
    }
}
