package com.royalmade.controller;

import com.royalmade.entity.Salaryslip;
import com.royalmade.service.SalaryslipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SalaryslipController {

    @Autowired
    private SalaryslipService salaryslipService;

    @PostMapping("/createsalarySlip")
    @PreAuthorize("hasRole('Admin')")
    public Salaryslip createsalarySlip(@RequestBody Salaryslip salaryslip){
        return salaryslipService.createsalarySlip(salaryslip);
    }

    @GetMapping("/getAllsalarySlip")
    @PreAuthorize("hasRole('Admin')")
    public List<Salaryslip> getAllsalarySlip(){
        return salaryslipService.getAllsalarySlip();
    }
    @GetMapping("/getsalarySlip/{id}")
    @PreAuthorize("hasRole('Admin')")
    public Salaryslip getsalarySlip(@PathVariable Long id){
        return salaryslipService.getlsalarySlip(id);
    }
    @DeleteMapping("/deleteSalarySlip/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<String> deleteSalarySlip(@PathVariable Long id) {
        salaryslipService.deleteSalarySlip(id);
        return ResponseEntity.ok("Salary slip deleted successfully");
    }
    @PutMapping("/updatesalarySlip/{id}")
    @PreAuthorize("hasRole('Admin')")
    public Salaryslip updateSalaryslip(@PathVariable Long id,@RequestBody Salaryslip salaryslip){
        salaryslip.setId(id);
        return salaryslipService.updateSalaryslip(salaryslip);
    }



}
