package com.royalmade.controller;

import com.royalmade.dto.ResidencyDto;
import com.royalmade.entity.Residency;
import com.royalmade.entity.enumeration.AvailabilityStatus;
import com.royalmade.service.ResidencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ResidencyController {

    @Autowired
    private ResidencyService residencyService;


    @PostMapping("/createResidency")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Residency> createResidency(@RequestBody ResidencyDto residencyDto){
        Residency createResidency= residencyService.createResidency(residencyDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createResidency);

    }

    @GetMapping("/allResidency")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<List<Residency>> getAllResidencies() {
        List<Residency> residencies = residencyService.getAllResidencies();
        return ResponseEntity.ok(residencies);
    }

    // Get residency by ID
    @GetMapping("/allResidencybyid/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Residency> getResidencyById(@PathVariable Long id) {
        Residency residency = residencyService.getResidencyById(id);
        return ResponseEntity.ok(residency);
    }


    // API to get residencies by project_id as a query parameter
    @GetMapping("/project/{projectId}")
//@PreAuthorize("hasRole('Admin')")
    public ResponseEntity<List<ResidencyDto>> getResidenciesByProjectId(@PathVariable Long projectId) {
        List<ResidencyDto> residencies = (List<ResidencyDto>) residencyService.getResidenciesByProjectId(projectId);
        return ResponseEntity.ok(residencies);
    }
    //upade residency by ID
    @PutMapping("/updateresidency/{id}")
    public ResponseEntity<Residency> updateResidency(@PathVariable Long id, @RequestBody ResidencyDto residencyDto) {
        Residency updatedResidency = residencyService.updateResidency(id, residencyDto);
        return ResponseEntity.ok(updatedResidency);
    }

    @GetMapping("/count")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Map<Long, Map<String, Long>>> countResidenciesByProjectAndStatus() {
        Map<Long, Map<String, Long>> counts = residencyService.countResidenciesByProjectAndStatus();
        return ResponseEntity.ok(counts);
    }

    @DeleteMapping("/deleteResidency/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Residency> deleteResidency(@PathVariable  Long id){
        Residency residency=residencyService.deleteResidency(id);
        return ResponseEntity.ok(residency);

    }
    @GetMapping("/residenciesByProject/{projectId}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<List<Residency>> getResidenciesByProject(@PathVariable Long projectId) {
        List<Residency> residencies = residencyService.getResidenciesByProject(projectId);
        return ResponseEntity.ok(residencies);
    }

}
