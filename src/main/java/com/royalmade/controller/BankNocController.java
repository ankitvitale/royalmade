package com.royalmade.controller;

import com.royalmade.entity.BankNoc;
import com.royalmade.service.BankNocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BankNocController {

    @Autowired
    private BankNocService bankNocService;

    @PostMapping("/createBankNoc")
    @PreAuthorize("hasRole('Admin')")
    public BankNoc createBankNoc(@RequestBody BankNoc bankNoc){
        return bankNocService.createBankNoc(bankNoc);
    }

    @GetMapping("/bankNoc/{id}")
    @PreAuthorize("hasRole('Admin')")
    public BankNoc getBankNocByid(@PathVariable long id){
        return bankNocService.getBankNocByid(id);
    }

    @GetMapping("/bankNoc")
    @PreAuthorize("hasRole('Admin')")
    public List<BankNoc> getAlllBankNoc(){
        return bankNocService.getAllBankNoc();
    }

    @DeleteMapping("/bankNoc/{id}")
    @PreAuthorize("hasRole('Admin')")
    public void deleteBankNoc(@PathVariable Long id) {
        bankNocService.deleteBankNocById(id);
    }


    @PutMapping("/bankNoc/{id}")
    @PreAuthorize("hasRole('Admin')")
    public BankNoc updateBankNoc(@PathVariable Long id, @RequestBody BankNoc bankNoc) {
        return bankNocService.updateBankNoc(id, bankNoc);
    }

}
