package com.royalmade.controller;

import com.royalmade.dto.*;
import com.royalmade.entity.Land;
import com.royalmade.entity.Partner;
import com.royalmade.mapper.LandMapper;
import com.royalmade.service.LandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LandController {

    @Autowired
    LandService landService;


    @Autowired
    private LandMapper landMapper;
    @PostMapping("/create")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Land> createLand(@RequestBody LandRequestDto landRequestDto) {
        Land createdLand = landService.createLand(landRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLand);
    }
    @PostMapping("/partnerpayment/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Partner> addPartnerPayment(@RequestBody PartnerpaymnetDto partnerpaymnetDto,@PathVariable Long  id){
        Partner addpayment = landService.addPartnerPayment(partnerpaymnetDto,id);
        return ResponseEntity.status(HttpStatus.CREATED).body(addpayment);
    }
    @GetMapping("/getAllland")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<List<Land>> getAllLands() {
        List<Land> lands = landService.getAllLands();
        return ResponseEntity.ok(lands);
    }

    @GetMapping("/land/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Land> getLandById(@PathVariable Long id) {
        Land lands = landService.getLandById(id);;
        return ResponseEntity.ok(lands);
    }


    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Land> updateLand(@PathVariable Long id, @RequestBody LandRequestDto landRequestDto) {
        Land updatedLand = landService.updateLand(id, landRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedLand);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<String> deleteLand(@PathVariable Long id) {
        landService.deleteLand(id);
        return ResponseEntity.status(HttpStatus.OK).body("Land with ID " + id + " has been deleted successfully.");
    }


}
