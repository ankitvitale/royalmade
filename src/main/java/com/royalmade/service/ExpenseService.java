package com.royalmade.service;


import com.royalmade.dto.ExpenseDto;
import com.royalmade.dto.ExpenseInstallmentDto;
import com.royalmade.entity.*;
import com.royalmade.exception.ResourceNotFoundException;
import com.royalmade.mapper.ExpenseMapper;
import com.royalmade.repo.*;
import com.royalmade.security.JwtAuthenticationFilter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ExpenseService {


    private final S3Client s3Client;
    private final String bucketName = "resallingo";
    // private final String spaceUrl = "https://studycycle.blr1.digitaloceanspaces.com/";
    private final String spaceUrl =  "https://resallingo.sfo3.digitaloceanspaces.com/resallingo";
    public ExpenseService(S3Client s3Client) {
        this.s3Client = s3Client;
    }
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

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ExpenselnstallmentRepository expenselnstallmentRepository;

//
//    public Expense addExpenseToProject(Long projectId, ExpenseDto expenseDto) {
//        // Retrieve the project by its ID
//        Project project = projectRepository.findById(projectId)
//                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));
//        String email = JwtAuthenticationFilter.CURRENT_USER;
//        AppUser appUser = appUserRepository.findByEmail(email);
//        Admin admin =adminRepository.findByEmail(email);
//        if (appUser == null && admin==null) {
//            throw new RuntimeException("User not found");
//        }
//
//        double totalPrice=expenseDto.getQuantity()*expenseDto.getPrice();
//        double reamingAmount=totalPrice-expenseDto.getVendorAmountPaid();
//        // Create a new Expense and map fields from the DTO
//        Expense expense = new Expense();
//        expense.setId(null);
//        expense.setName(expenseDto.getName());
//        expense.setType(expenseDto.getType());
//        expense.setQuantity(expenseDto.getQuantity());
//        expense.setVendorName(expenseDto.getVendorName());
//        expense.setPrice(expenseDto.getPrice());
//        expense.setAddedOn(expenseDto.getAddedOn());
//        expense.setReamingAmount(reamingAmount);
//        expense.setVendorAmountPaid(expenseDto.getVendorAmountPaid());
//        expense.setTotalPrice(totalPrice);
//        expense.setBillImg(expenseDto.getBillImg()); // Set the billImg field
//        expense.setProject(project); // Link the expense to the project
//        expense.setAddedBy(appUser);
//        // Save the expense to the database (assuming cascading is not used here)
//        return expenseRepository.save(expense);
//
//    }

//        public Expense addExpenseToProject(Long projectId, ExpenseDto expenseDto, MultipartFile billImgFile) throws IOException {
//            // Retrieve the project by its ID
//            Project project = projectRepository.findById(projectId)
//                    .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));
//
//            // Get the current user's email
//            String email = JwtAuthenticationFilter.CURRENT_USER;
//            AppUser appUser = appUserRepository.findByEmail(email);
//            Admin admin = adminRepository.findByEmail(email);
//
//            // Validate if user exists
//            if (appUser == null && admin == null) {
//                throw new RuntimeException("User not found");
//            }
//
//            // Calculate total price and remaining amount
//            double totalPrice = expenseDto.getQuantity() * expenseDto.getPrice();
//            double remainingAmount = totalPrice - expenseDto.getVendorAmountPaid();
//
//            // Upload the bill image and get the URL
//            String billImgUrl = null;
//            if (billImgFile != null && !billImgFile.isEmpty()) {
//                billImgUrl = uploadFileToSpace(billImgFile); // Method to upload file and return URL
//            }
//
//            // Create a new Expense and map fields from the DTO
//            Expense expense = new Expense();
//            expense.setName(expenseDto.getName());
//            expense.setType(expenseDto.getType());
//            expense.setQuantity(expenseDto.getQuantity());
//            expense.setVendorName(expenseDto.getVendorName());
//            expense.setPrice(expenseDto.getPrice());
//            expense.setAddedOn(expenseDto.getAddedOn());
//            expense.setReamingAmount(remainingAmount);
//            expense.setVendorAmountPaid(expenseDto.getVendorAmountPaid());
//            expense.setTotalPrice(totalPrice);
//            expense.setBillImg(billImgUrl); // Set the uploaded bill image URL
//            expense.setProject(project); // Link the expense to the project
//            expense.setAddedBy(appUser); // Set the addedBy field
//
//            // Save the expense to the database
//            return expenseRepository.save(expense);
//        }
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


    public  List<Expense> getExpensesByProject(Long projectId) {
        return expenseRepository.findByProjectId(projectId);
    }

    public List<Expense> getExpensesByVenodrName(String vendorName) {
        return expenseRepository.findByVendorNameIgnoreCase(vendorName);
    }

    public Expense updateExpense(Long expenceId, ExpenseDto expenseDto) {

        Expense expense=expenseRepository.findById(expenceId).orElseThrow(()->
                 new RuntimeException("Expence ID not found"+expenceId));
        double totalPrice=expenseDto.getQuantity()*expenseDto.getPrice();
        expense.setName(expenseDto.getName());
        expense.setType(expenseDto.getType());
        expense.setVendorName(expenseDto.getVendorName());
        expense.setPrice(expenseDto.getPrice());
        expense.setQuantity(expenseDto.getQuantity());
        expense.setVendorAmountPaid(expenseDto.getVendorAmountPaid());
        expense.setReamingAmount(expenseDto.getReamingAmount());
        expense.setTotalPrice(totalPrice);
        expense.setAddedOn(expenseDto.getAddedOn());

        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense deleteExpences(Long id) {
        // Retrieve the expense first
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));

        // Delete all associated ExpenseInstallments
        for (ExpenseInstallment installment : expense.getExpenseInstallments()) {
            expenselnstallmentRepository.delete(installment);
        }

        // Finally, delete the Expense
        expenseRepository.deleteById(id);
        // Delete the expense


        return expense; // Return the deleted expense
    }




    public Expense addExpenseInstallment(Long id, List<ExpenseInstallmentDto> expenseInstallments) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Expense id not found"));

        List<ExpenseInstallment> installments = new ArrayList<>();

        for (ExpenseInstallmentDto expenseInstallmentDto : expenseInstallments) {
            ExpenseInstallment installment = new ExpenseInstallment();
            installment.setExpense(expense);
            installment.setExpenseAmount(expenseInstallmentDto.getExpenseAmount());
            installment.setExpensePayDate(expenseInstallmentDto.getExpensePayDate());
            installment.setExpensePayStatus(expenseInstallmentDto.getExpensePayStatus());

            double expenseAmount = expenseInstallmentDto.getExpenseAmount();
            expense.setVendorAmountPaid(expense.getVendorAmountPaid() + expenseAmount);
            expense.setReamingAmount(expense.getReamingAmount() - expenseAmount);

            installments.add(installment);
        }

        expense.getExpenseInstallments().addAll(installments);
        expenselnstallmentRepository.saveAll(installments);
        return expenseRepository.save(expense);
    }

    public Expense getExpenseById(Long id) {
        Expense expense=expenseRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Expense ID "+id+"Not Found"));
        expenseRepository.findById(id);
        return expense;
    }
}
