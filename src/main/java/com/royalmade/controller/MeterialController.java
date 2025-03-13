package com.royalmade.controller;

import com.royalmade.dto.MaterialRequest;
import com.royalmade.dto.VendorPaymentDTO;
import com.royalmade.entity.*;
import com.royalmade.exception.ResourceNotFoundException;
import com.royalmade.repo.*;
import com.royalmade.security.JwtAuthenticationFilter;
import com.royalmade.service.MeterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MeterialController {


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

    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private  MaterialRepository materialRepository;


    @Autowired
    private MeterialService meterialService;


    @PostMapping("/addVendor")
    @PreAuthorize("hasAnyRole('Admin')")
    public Vendor addVendor(@RequestBody Vendor vendor){
        return meterialService.addVendors(vendor);
    }

    @GetMapping("/AllVendor")
    @PreAuthorize("hasAnyRole('Admin','AppUser')")
    public List<Vendor> getAllVendor(){
        return meterialService.getAllVendor();
    }

    @GetMapping("/vendor/{id}")
    @PreAuthorize("hasAnyRole('Admin','AppUser')")
    public ResponseEntity<Vendor> getVendorByID(@PathVariable Long id){
        return meterialService.getVendorById(id);
    }
    @DeleteMapping("/deleteVendor/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> deleteVendor(@PathVariable Long id) {
        meterialService.deleteVendorById(id);
        return ResponseEntity.ok("Vendor deleted successfully");
    }


    @PostMapping("/projects/{projectId}/{vendorId}/add-expense")
    @PreAuthorize("hasAnyRole('Admin','AppUser')")
    public ResponseEntity<List<Material>> addExpenses(
            @PathVariable Long projectId,
            @PathVariable Long vendorId,
            @RequestBody List<MaterialRequest> materialRequests) {
        try {
            String email = JwtAuthenticationFilter.CURRENT_USER;
            AppUser appUser = appUserRepository.findByEmail(email);
            Admin admin = adminRepository.findByEmail(email);

            if (appUser == null && admin == null) {
                throw new RuntimeException("User not found");
            }

            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

            Vendor vendor = vendorRepository.findById(vendorId)
                    .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with ID: " + vendorId));

            List<Material> materials = new ArrayList<>();

            for (MaterialRequest materialRequest : materialRequests) {
                Material material = new Material();
                material.setName(materialRequest.getName());
                material.setType(materialRequest.getType());
                material.setQuantity(materialRequest.getQuantity());
                material.setBillNo(materialRequest.getBillNo());
                material.setPrice(materialRequest.getPrice());

                double totalPrice = materialRequest.getQuantity() * materialRequest.getPrice();
                material.setAddedOn(LocalDate.now());
                material.setVendor(vendor);
                material.setProject(project);  // ✅ Setting the project
                material.setAddedBy(appUser);

                materials.add(material);
            }

            List<Material> savedMaterials = materialRepository.saveAll(materials);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedMaterials);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



@GetMapping("/bills/{vendorId}")
@PreAuthorize("hasAnyRole('Admin','AppUser')")
public ResponseEntity<List<Map<String, Object>>> getBillsByVendor(@PathVariable Long vendorId) {
    List<Map<String, Object>> billDetails = meterialService.getBillDetailsByVendor(vendorId);
    return ResponseEntity.ok(billDetails);
}

    @GetMapping("/bills/details/{billNo}")
    @PreAuthorize("hasAnyRole('Admin','AppUser')")
    public ResponseEntity<Map<String, Object>> getBillDetailsByBillNo(@PathVariable Double billNo) {
        Map<String, Object> billDetails = meterialService.getBillDetailsByBillNo(billNo);
        return ResponseEntity.ok(billDetails);
    }


    @PostMapping("/Material/{billNo}/payments")
    @PreAuthorize("hasAnyRole('Admin')")
    public ResponseEntity<Map<String, Object>> addPayment(
            @PathVariable Double billNo,
            @RequestBody VendorPaymentDTO paymentDTO) {
        try {
            Map<String, Object> response = meterialService.addPayment(billNo, paymentDTO);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST) // 🔴 Change 404 -> 400
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Internal server error"));
        }
    }

    @DeleteMapping("/material/{materialId}")
    @PreAuthorize("hasAnyRole('Admin')")
    public ResponseEntity<Map<String, String>> deleteMaterial(@PathVariable Long materialId) {
        try {
            Material material = materialRepository.findById(materialId)
                    .orElseThrow(() -> new ResourceNotFoundException("Material not found with ID: " + materialId));

            materialRepository.delete(material);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Material deleted successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to delete material."));
        }
    }

}
