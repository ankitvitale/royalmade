package com.royalmade.controller;

import com.royalmade.dto.ExpenseDto;
import com.royalmade.entity.Expense;
import com.royalmade.service.ExpenseService;
import com.royalmade.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExpenseController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ExpenseService expenseService;



    @PostMapping("/projects/{projectId}/add-expense")
    @PreAuthorize("hasRole('AppUser')")
    public ResponseEntity<Expense> addExpenseToProject(
            @PathVariable Long projectId,
            @RequestBody ExpenseDto expenseDto) {
        Expense createdExpense = expenseService.addExpenseToProject(projectId, expenseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExpense);
    }

    @GetMapping("/{projectId}/expenses")
    public ResponseEntity<List<Expense>> getExpensesByProject(@PathVariable Long projectId) {
        List<Expense> expenses = expenseService.getExpensesByProject(projectId);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/expenses/by-vendor")
    public ResponseEntity<List <Expense>>getExpencesByVendor(@RequestParam String vendorName){
        List<Expense> expenses=expenseService.getExpensesByVenodrName(vendorName);
        return ResponseEntity.ok(expenses);
    }

    @PutMapping("/updateExpense/{expenceId}")
    public ResponseEntity<Expense> updateExpences(@PathVariable Long expenceId ,@RequestBody ExpenseDto expenseDto){
        expenseDto.setId(expenceId);
        Expense updateExpence=expenseService.updateExpense(expenceId,expenseDto);
        return ResponseEntity.ok(updateExpence);
    }


}
