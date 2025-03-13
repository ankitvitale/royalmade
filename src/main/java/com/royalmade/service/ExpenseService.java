//package com.royalmade.service;
//
//
//import com.royalmade.dto.ExpenseDto;
//import com.royalmade.dto.ExpenseInstallmentDto;
//import com.royalmade.entity.*;
//import com.royalmade.exception.ResourceNotFoundException;
//import com.royalmade.mapper.ExpenseMapper;
//import com.royalmade.repo.*;
//import com.royalmade.security.JwtAuthenticationFilter;
//import jakarta.persistence.EntityNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//import org.springframework.web.multipart.MultipartFile;
//import software.amazon.awssdk.services.s3.S3Client;
//import software.amazon.awssdk.services.s3.model.PutObjectRequest;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.StandardCopyOption;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//@Service
//public class ExpenseService {
//
//
//    private final S3Client s3Client;
//    private final String bucketName = "resallingo";
//    // private final String spaceUrl = "https://studycycle.blr1.digitaloceanspaces.com/";
//    private final String spaceUrl =  "https://resallingo.sfo3.digitaloceanspaces.com/resallingo";
//    public ExpenseService(S3Client s3Client) {
//        this.s3Client = s3Client;
//    }
//    @Autowired
//    private ExpenseRepository expenseRepository;
//
//    @Autowired
//    private ExpenseMapper expenseMapper;
//
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
//    private  VendorRepository vendorRepository;
//
//    @Autowired
//    private ExpenselnstallmentRepository expenselnstallmentRepository;
//
//
//    public  List<Expense> getExpensesByProject(Long projectId) {
//        return expenseRepository.findByProjectId(projectId);
//    }
//
//    public List<Expense> getExpensesByVenodrName(String vendorName) {
//        return expenseRepository.findByVendorNameIgnoreCase(vendorName);
//    }
//
//    public Expense updateExpense(Long expenceId, ExpenseDto expenseDto) {
//
//        Expense expense=expenseRepository.findById(expenceId).orElseThrow(()->
//                 new RuntimeException("Expence ID not found"+expenceId));
//        double totalPrice=expenseDto.getQuantity()*expenseDto.getPrice();
//        expense.setName(expenseDto.getName());
//        expense.setType(expenseDto.getType());
//        expense.setVendorName(expenseDto.getVendorName());
//        expense.setPrice(expenseDto.getPrice());
//        expense.setQuantity(expenseDto.getQuantity());
//        expense.setVendorAmountPaid(expenseDto.getVendorAmountPaid());
//        expense.setReamingAmount(expenseDto.getReamingAmount());
//        expense.setTotalPrice(totalPrice);
//        expense.setAddedOn(expenseDto.getAddedOn());
//
//        return expenseRepository.save(expense);
//    }
//
//    public List<Expense> getAllExpenses() {
//        return expenseRepository.findAll();
//    }
//
//    public Expense deleteExpences(Long id) {
//        // Retrieve the expense first
//        Expense expense = expenseRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));
//
//        // Delete all associated ExpenseInstallments
//        for (ExpenseInstallment installment : expense.getExpenseInstallments()) {
//            expenselnstallmentRepository.delete(installment);
//        }
//
//        // Finally, delete the Expense
//        expenseRepository.deleteById(id);
//        // Delete the expense
//
//
//        return expense; // Return the deleted expense
//    }
//
//
//
//
//    public Expense addExpenseInstallment(Long id, List<ExpenseInstallmentDto> expenseInstallments) {
//        Expense expense = expenseRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Expense id not found"));
//
//        List<ExpenseInstallment> installments = new ArrayList<>();
//
//        for (ExpenseInstallmentDto expenseInstallmentDto : expenseInstallments) {
//            ExpenseInstallment installment = new ExpenseInstallment();
//            installment.setExpense(expense);
//            installment.setExpenseAmount(expenseInstallmentDto.getExpenseAmount());
//            installment.setExpensePayDate(expenseInstallmentDto.getExpensePayDate());
//            installment.setExpensePayStatus(expenseInstallmentDto.getExpensePayStatus());
//
//            double expenseAmount = expenseInstallmentDto.getExpenseAmount();
//            expense.setVendorAmountPaid(expense.getVendorAmountPaid() + expenseAmount);
//            expense.setReamingAmount(expense.getReamingAmount() - expenseAmount);
//
//            installments.add(installment);
//        }
//
//        expense.getExpenseInstallments().addAll(installments);
//        expenselnstallmentRepository.saveAll(installments);
//        return expenseRepository.save(expense);
//    }
//
//    public Expense getExpenseById(Long id) {
//        Expense expense=expenseRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Expense ID "+id+"Not Found"));
//        expenseRepository.findById(id);
//        return expense;
//    }
//
//    public Vendor addVendor(Vendor vendor) {
//        return vendorRepository.save(vendor);
//    }
//
//
//}
