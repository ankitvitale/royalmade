package com.royalmade.controller;


import com.royalmade.dto.AllContractorResponseDto;
import com.royalmade.dto.ContractorDto;
import com.royalmade.dto.ContractorInstallmentDto;
import com.royalmade.dto.ContractorResponseDto;
import com.royalmade.entity.Contractor;
import com.royalmade.entity.ContractorInstallment;
//import com.royalmade.entity.Expense;
//import com.royalmade.entity.ExpenseInstallment;
import com.royalmade.service.ContractorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ContractorController {

    @Autowired
    private ContractorService contractorService;


private static final Logger logger = LoggerFactory.getLogger(ContractorController.class);


    @PreAuthorize("hasRole('Admin')")
    @PostMapping("/contractor/{projectId}")
    public ResponseEntity<Contractor> createContractor(
            @PathVariable Long projectId,
            @RequestBody ContractorDto contractorDto) {
        Contractor newContractor = contractorService.createContractor(projectId, contractorDto);
        return new ResponseEntity<>(newContractor, HttpStatus.CREATED);
    }


    @GetMapping("/contractor")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<List<Contractor>> getAllContractors() {
        List<Contractor> contractors = contractorService.getAllContractors();
        return ResponseEntity.ok(contractors);
    }

    @PostMapping("/{id}/contractorInstallment")
    @PreAuthorize("hasAnyRole('Admin','AppUser')")
    public ResponseEntity<Contractor> addContractorInstallment(
            @PathVariable Long id,
            @RequestBody List<ContractorInstallmentDto> contractorInstallments) {
        Contractor updatedContractor = contractorService.addContractorInstallment(id, contractorInstallments);
        return ResponseEntity.ok(updatedContractor);
    }


    @GetMapping("/{projectId}/Contractor")
    @PreAuthorize("hasAnyRole('Admin','AppUser')")
    public ResponseEntity<List<AllContractorResponseDto>> getContractorByProject(@PathVariable Long projectId) {
        List<AllContractorResponseDto> contractors = contractorService.getContractorByProject(projectId);
        return ResponseEntity.ok(contractors);
    }

    @GetMapping("/Contractor/{id}")
    @PreAuthorize("hasAnyRole('Admin','AppUser')")
    public ResponseEntity<ContractorResponseDto> getContractorById(@PathVariable Long id) {
        ContractorResponseDto contractorDto = contractorService.getContractorById(id);
        return ResponseEntity.ok(contractorDto);
    }
    @DeleteMapping("/deleteContractor/{id}")
    @PreAuthorize("hasAnyRole('Admin','AppUser')")
    public ResponseEntity<Contractor> deleteContractor(@PathVariable Long id){
        Contractor contractor=contractorService.deleteContractor(id);
        return ResponseEntity.ok(contractor);
    }

}
