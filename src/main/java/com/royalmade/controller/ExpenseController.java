package com.royalmade.controller;

import com.royalmade.dto.ExpenseDto;
import com.royalmade.dto.ExpenseInstallmentDto;
import com.royalmade.entity.*;
import com.royalmade.entity.enumeration.ExpenseType;
import com.royalmade.exception.ResourceNotFoundException;
import com.royalmade.repo.*;
import com.royalmade.security.JwtAuthenticationFilter;
import com.royalmade.service.ExpenseService;
import com.royalmade.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
public class ExpenseController {

    private final S3Client s3Client;
    private final String bucketName = "resallingo";
    // private final String spaceUrl = "https://studycycle.blr1.digitaloceanspaces.com/";
    private final String spaceUrl =  "https://resallingo.sfo3.digitaloceanspaces.com/resallingo";
    public ExpenseController(S3Client s3Client) {
        this.s3Client = s3Client;
    }
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ExpenseService expenseService;


    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private SupervisorRepository supervisorRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

//
//    @PostMapping("/projects/{projectId}/add-expense")
//    @PreAuthorize("hasAnyRole('Admin','AppUser')")
//    public ResponseEntity<Expense> addExpenseToProject(
//            @PathVariable Long projectId,
//            @RequestParam String name,
//            @RequestParam ExpenseType type,
//            @RequestParam Integer quantity,
//            @RequestParam String vendorName,
//            @RequestParam Double price,
//            @RequestParam Double vendorAmountPaid,
//            @RequestParam LocalDate addedOn,
//            @RequestPart("billImg") MultipartFile billImg) {
//
//        try {
//            // Retrieve the authenticated user
//            String email = JwtAuthenticationFilter.CURRENT_USER;
//            AppUser appUser = appUserRepository.findByEmail(email);
//            Admin admin = adminRepository.findByEmail(email);
//            if (appUser == null && admin == null) {
//                throw new RuntimeException("User not found");
//            }
//
//            // Retrieve the project by its ID
//            Project project = projectRepository.findById(projectId)
//                    .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));
//
//            // Upload the bill image to storage and get the URL
//             String billImg1=uploadFileToSpace(billImg);
//
//            // Calculate total price and remaining amount
//            double totalPrice = quantity * price;
//            double remainingAmount = totalPrice - vendorAmountPaid;
//
//            // Create and save the expense
//            Expense expense = new Expense();
//            expense.setName(name);
//            expense.setType(type);
//            expense.setQuantity(quantity);
//            expense.setVendorName(vendorName);
//            expense.setPrice(price);
//            expense.setTotalPrice(totalPrice);
//            expense.setVendorAmountPaid(vendorAmountPaid);
//            expense.setReamingAmount(remainingAmount);
//            expense.setAddedOn(addedOn);
//            expense.setBillImg(billImg1);
//            expense.setProject(project);
//            expense.setAddedBy(appUser);
//
//            Expense savedExpense = expenseRepository.save(expense);
//            return ResponseEntity.status(HttpStatus.CREATED).body(savedExpense);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }


    @PostMapping("/projects/{projectId}/add-expense")
    @PreAuthorize("hasAnyRole('Admin','AppUser')")
    public ResponseEntity<Expense> addExpenseToProject(
            @PathVariable Long projectId,
            @RequestParam String name,
            @RequestParam ExpenseType type,
            @RequestParam Integer quantity,
            @RequestParam String vendorName,
            @RequestParam Double price,
            @RequestParam Double vendorAmountPaid,
            @RequestParam LocalDate addedOn) {
        try {
            // Retrieve the authenticated user
            String email = JwtAuthenticationFilter.CURRENT_USER;
            AppUser appUser = appUserRepository.findByEmail(email);
            Admin admin = adminRepository.findByEmail(email);
            if (appUser == null && admin == null) {
                throw new RuntimeException("User not found");
            }

            // Retrieve the project by its ID
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

            // Calculate total price and remaining amount
            double totalPrice = quantity * price;
            double remainingAmount = totalPrice - vendorAmountPaid;

            // Create and save the expense
            Expense expense = new Expense();
            expense.setName(name);
            expense.setType(type);
            expense.setQuantity(quantity);
            expense.setVendorName(vendorName);
            expense.setPrice(price);
            expense.setTotalPrice(totalPrice);
            expense.setVendorAmountPaid(vendorAmountPaid);
            expense.setReamingAmount(remainingAmount);
            expense.setAddedOn(addedOn);
            expense.setProject(project);
            expense.setAddedBy(appUser);

            Expense savedExpense = expenseRepository.save(expense);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedExpense);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private String uploadFileToSpace(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "-" + StringUtils.cleanPath(file.getOriginalFilename());

        // Validate file
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        Path tempFile = Files.createTempFile("upload-", fileName);

        try (var inputStream = file.getInputStream()) {
            Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
        }

        // Upload to DigitalOcean Spaces
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .acl("public-read")
                .build();

        s3Client.putObject(putObjectRequest, tempFile);

        // Clean up temporary file
        Files.deleteIfExists(tempFile);

        // Correct URL format
        return spaceUrl + "/" + fileName; // Add "/" between spaceUrl and fileName
    }



        @PostMapping("/{id}/expenseInstallment")
        @PreAuthorize("hasAnyRole('Admin','AppUser')")
        public ResponseEntity<Expense> addExpenseinstallment(@PathVariable Long id, @RequestBody List<ExpenseInstallmentDto> expenseInstallments){
            Expense addExpenseinstallment=expenseService.addExpenseInstallment(id,expenseInstallments);
            return ResponseEntity.ok(addExpenseinstallment);
        }



    @GetMapping("/Allexpenses")
    @PreAuthorize("hasAnyRole('Admin','AppUser')")
    public  ResponseEntity<List<Expense>> getAllExpenses(){
        List <Expense> expenses=expenseService.getAllExpenses();
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/ExpenseById/{id}")
    @PreAuthorize("hasAnyRole('Admin','AppUser')")
    public ResponseEntity<Expense> getExpenseBYId(@PathVariable Long id){
        Expense expense=expenseService.getExpenseById(id);
        return ResponseEntity.ok(expense);
    }
    @GetMapping("/{projectId}/expenses")
    @PreAuthorize("hasAnyRole('Admin','AppUser')")
    public ResponseEntity<List<Expense>> getExpensesByProject(@PathVariable Long projectId) {
        List<Expense> expenses = expenseService.getExpensesByProject(projectId);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/expenses/by-vendor")
    @PreAuthorize("hasAnyRole('Admin','AppUser')")
    public ResponseEntity<List <Expense>>getExpencesByVendor(@RequestParam String vendorName){
        List<Expense> expenses=expenseService.getExpensesByVenodrName(vendorName);
        return ResponseEntity.ok(expenses);
    }

    @PutMapping("/updateExpense/{expenceId}")
    @PreAuthorize("hasAnyRole('Admin','AppUser')")
    public ResponseEntity<Expense> updateExpences(@PathVariable Long expenceId ,@RequestBody ExpenseDto expenseDto){
        expenseDto.setId(expenceId);
        Expense updateExpence=expenseService.updateExpense(expenceId,expenseDto);
        return ResponseEntity.ok(updateExpence);
    }

    @DeleteMapping("/deleteExpense/{id}")
    @PreAuthorize("hasAnyRole('Admin','AppUser')")
    public ResponseEntity<Expense> deleteExpences(@PathVariable Long id){
        Expense expense=expenseService.deleteExpences(id);
        return ResponseEntity.ok(expense);
    }

}
