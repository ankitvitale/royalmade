package com.royalmade.service;


import com.royalmade.dto.ExpenseDto;
import com.royalmade.entity.*;
import com.royalmade.exception.ResourceNotFoundException;
import com.royalmade.mapper.ExpenseMapper;
import com.royalmade.repo.*;
import com.royalmade.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseMapper expenseMapper;


    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private SupervisorRepository supervisorRepository;


    public Expense addExpenseToProject(Long projectId, ExpenseDto expenseDto) {
        // Retrieve the project by its ID
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));
        String email = JwtAuthenticationFilter.CURRENT_USER;
        AppUser appUser = appUserRepository.findByEmail(email);
        if (appUser == null) {
            throw new RuntimeException("User not found");
        }
        // Create a new Expense and map fields from the DTO
        Expense expense = new Expense();
        expense.setName(expenseDto.getName());
        expense.setType(expenseDto.getType());
        expense.setQuantity(expenseDto.getQuantity());
        expense.setVendorName(expenseDto.getVendorName());
        expense.setPrice(expenseDto.getPrice());
        expense.setAddedOn(expenseDto.getAddedOn());
        expense.setProject(project); // Link the expense to the project
        expense.setAddedBy(appUser);
        // Save the expense to the database (assuming cascading is not used here)
        return expenseRepository.save(expense);

    }

    public  List<Expense> getExpensesByProject(Long projectId) {
        return expenseRepository.findByProjectId(projectId);
    }

    public List<Expense> getExpensesByVenodrName(String vendorName) {
        return expenseRepository.findByVendorNameIgnoreCase(vendorName);
    }

    public Expense updateExpense(Long expenceId, ExpenseDto expenseDto) {

        Expense expense=expenseRepository.findById(expenceId).orElseThrow(()->
                 new RuntimeException("Expence ID not found"+expenceId));

        expense.setName(expenseDto.getName());
        expense.setVendorName(expenseDto.getVendorName());
        expense.setPrice(expenseDto.getPrice());
        expense.setQuantity(expenseDto.getQuantity());
        expense.setAddedOn(expenseDto.getAddedOn());
        return expenseRepository.save(expense);
    }
}
