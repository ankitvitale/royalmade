//package com.royalmade.controller;
//
//import com.royalmade.dto.ExpenseDto;
//import com.royalmade.dto.ExpenseInstallmentDto;
//import com.royalmade.dto.ExpenseRequest;
//import com.royalmade.entity.*;
//import com.royalmade.entity.enumeration.ExpenseType;
//import com.royalmade.exception.ResourceNotFoundException;
//import com.royalmade.repo.*;
//import com.royalmade.security.JwtAuthenticationFilter;
//import com.royalmade.service.ExpenseService;
//import com.royalmade.service.ProjectService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import software.amazon.awssdk.services.s3.S3Client;
//import software.amazon.awssdk.services.s3.model.PutObjectRequest;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.StandardCopyOption;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//public class ExpenseController {
//
//
//    @Autowired
//    private ProjectService projectService;
//    @Autowired
//    private ExpenseService expenseService;
//
//    @Autowired
//    private ProjectRepository projectRepository;
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//    @Autowired
//    private AppUserRepository appUserRepository;
//    @Autowired
//    private SupervisorRepository supervisorRepository;
//
//    @Autowired
//    private AdminRepository adminRepository;
//
//    @Autowired
//    private ExpenseRepository expenseRepository;
//
//
//
//    @PostMapping("/addVendor")
//    public Vendor addVendor(@RequestBody Vendor vendor){
//        return expenseService.addVendor(vendor);
//    }
//
//
//
//    @PostMapping("/projects/{projectId}/add-expense")
//    @PreAuthorize("hasAnyRole('Admin','AppUser')")
//    public ResponseEntity<Expense> addExpenseToProject(
//            @PathVariable Long projectId,
//            @RequestBody ExpenseRequest expenseRequest) {
//        try {
//            String email = JwtAuthenticationFilter.CURRENT_USER;
//            AppUser appUser = appUserRepository.findByEmail(email);
//            Admin admin = adminRepository.findByEmail(email);
//            if (appUser == null && admin == null) {
//                throw new RuntimeException("User not found");
//            }
//
//            Project project = projectRepository.findById(projectId)
//                    .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));
//
//            double totalPrice = expenseRequest.getQuantity() * expenseRequest.getPrice();
//            double remainingAmount = totalPrice - expenseRequest.getVendorAmountPaid();
//
//            Expense expense = new Expense();
//            expense.setName(expenseRequest.getName());
//            expense.setType(expenseRequest.getType());
//            expense.setQuantity(expenseRequest.getQuantity());
//            expense.setVendorName(expenseRequest.getVendorName());
//            expense.setPrice(expenseRequest.getPrice());
//            expense.setTotalPrice(totalPrice);
//            expense.setVendorAmountPaid(expenseRequest.getVendorAmountPaid());
//            expense.setReamingAmount(remainingAmount);
//            expense.setAddedOn(expenseRequest.getAddedOn());
//            expense.setProject(project);
//            expense.setAddedBy(appUser);
//
//            Expense savedExpense = expenseRepository.save(expense);
//            return ResponseEntity.status(HttpStatus.CREATED).body(savedExpense);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//
//
//        @PostMapping("/{id}/expenseInstallment")
//        @PreAuthorize("hasAnyRole('Admin','AppUser')")
//        public ResponseEntity<Expense> addExpenseinstallment(@PathVariable Long id, @RequestBody List<ExpenseInstallmentDto> expenseInstallments){
//            Expense addExpenseinstallment=expenseService.addExpenseInstallment(id,expenseInstallments);
//            return ResponseEntity.ok(addExpenseinstallment);
//        }
//
//
//
//    @GetMapping("/Allexpenses")
//    @PreAuthorize("hasAnyRole('Admin','AppUser')")
//    public  ResponseEntity<List<Expense>> getAllExpenses(){
//        List <Expense> expenses=expenseService.getAllExpenses();
//        return ResponseEntity.ok(expenses);
//    }
//
//    @GetMapping("/ExpenseById/{id}")
//    @PreAuthorize("hasAnyRole('Admin','AppUser')")
//    public ResponseEntity<Expense> getExpenseBYId(@PathVariable Long id){
//        Expense expense=expenseService.getExpenseById(id);
//        return ResponseEntity.ok(expense);
//    }
//    @GetMapping("/{projectId}/expenses")
//    @PreAuthorize("hasAnyRole('Admin','AppUser')")
//    public ResponseEntity<List<Expense>> getExpensesByProject(@PathVariable Long projectId) {
//        List<Expense> expenses = expenseService.getExpensesByProject(projectId);
//        return ResponseEntity.ok(expenses);
//    }
//
//    @GetMapping("/expenses/by-vendor")
//    @PreAuthorize("hasAnyRole('Admin','AppUser')")
//    public ResponseEntity<List <Expense>>getExpencesByVendor(@RequestParam String vendorName){
//        List<Expense> expenses=expenseService.getExpensesByVenodrName(vendorName);
//        return ResponseEntity.ok(expenses);
//    }
//
//    @PutMapping("/updateExpense/{expenceId}")
//    @PreAuthorize("hasAnyRole('Admin','AppUser')")
//    public ResponseEntity<Expense> updateExpences(@PathVariable Long expenceId ,@RequestBody ExpenseDto expenseDto){
//        expenseDto.setId(expenceId);
//        Expense updateExpence=expenseService.updateExpense(expenceId,expenseDto);
//        return ResponseEntity.ok(updateExpence);
//    }
//
//    @DeleteMapping("/deleteExpense/{id}")
//    @PreAuthorize("hasAnyRole('Admin','AppUser')")
//    public ResponseEntity<Expense> deleteExpences(@PathVariable Long id){
//        Expense expense=expenseService.deleteExpences(id);
//        return ResponseEntity.ok(expense);
//    }
//
//}
