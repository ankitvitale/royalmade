package com.royalmade.controller;

import com.royalmade.dto.*;
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

    @Autowired
    private VendorPaymentRepository vendorPaymentRepository;


    @PostMapping("/addVendor")
    @PreAuthorize("hasAnyRole('Admin')")
    public Vendor addVendor(@RequestBody VendorResponseDTO vendordto){
        return meterialService.addVendors(vendordto);
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
//    @GetMapping("/vendor-byProjectId/{projectId}")
//    @PreAuthorize("hasAnyRole('Admin','AppUser')")
//    public ResponseEntity<Vendor> getVendorByprojectId(@PathVariable Long projectId){
//        return meterialService.getVendorByprojectId(projectId);
//
//    }

    @GetMapping("/vendor-byProjectId/{projectId}")
    @PreAuthorize("hasAnyRole('Admin','AppUser')")
    public ResponseEntity<List<Vendor>> getVendorsByProjectId(@PathVariable Long projectId) {
        return meterialService.getVendorsByProjectId(projectId);
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


// old get all bills
//@GetMapping("/bills/{vendorId}")
//@PreAuthorize("hasAnyRole('Admin','AppUser')")
//public ResponseEntity<List<Map<String, Object>>> getBillsByVendor(@PathVariable Long vendorId) {
//    List<Map<String, Object>> billDetails = meterialService.getBillDetailsByVendor(vendorId);
//    return ResponseEntity.ok(billDetails);
//}

  // new get All bills

    @GetMapping("/bills/{vendorId}/{projectId}")
    @PreAuthorize("hasAnyRole('Admin','AppUser')")
    public ResponseEntity<List<Map<String, Object>>> getBillsByVendor(
            @PathVariable Long vendorId, @PathVariable Long projectId) {
        List<Map<String, Object>> billDetails = meterialService.getBillDetailsByVendor(vendorId, projectId);
        return ResponseEntity.ok(billDetails);
    }

    @GetMapping("/bills/details/{billNo}")
    @PreAuthorize("hasAnyRole('Admin','AppUser')")
    public ResponseEntity<Map<String, Object>> getBillDetailsByBillNo(@PathVariable Double billNo) {
        Map<String, Object> billDetails = meterialService.getBillDetailsByBillNo(billNo);
        return ResponseEntity.ok(billDetails);
    }

  // Add payment to Material
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

    //delete Material payment
    @DeleteMapping("/VendorMeterialPayment/{id}")
    @PreAuthorize("hasAnyRole('Admin')")
    public ResponseEntity<Map<String,String>> deleteMeterialPayment(@PathVariable Long id){
        try{
            VendorPayment vendorPayment=vendorPaymentRepository.findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException("Vendor Payment ID :"+id+" Not found"));
            vendorPaymentRepository.delete(vendorPayment);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Vendor Payment deleted successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to delete material."));
        }
    }

    @PostMapping("/material/add-payment")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Map<String, Object>> addPaymentByVendorAndProject(
            @RequestParam Long vendorId,
            @RequestParam Long projectId,
            @RequestBody VendorPaymentDTO paymentDTO) {

        try {
            Map<String, Object> response = meterialService.addPaymentByVendorAndProject(vendorId, projectId, paymentDTO);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal server error"));
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

    @GetMapping("/SingleMeteralPayment/{id}")
    @PreAuthorize("hasAnyRole('Admin')")
    public ResponseEntity<VendorPayment> getVendorSinglePaymentById(@PathVariable Long id){
        VendorPayment vendorPayment= meterialService.getVendorSinglePaymentById(id);
        return ResponseEntity.ok(vendorPayment);

    }

    @PutMapping("/Material/{billNo}/payments/{paymentId}")
    @PreAuthorize("hasAnyRole('Admin')")
    public ResponseEntity<Map<String, Object>> updatePayment(
            @PathVariable Double billNo,
            @PathVariable Long paymentId,
            @RequestBody VendorPaymentDTO paymentDTO) {
        try {
            Map<String, Object> response = meterialService.updatePayment(billNo, paymentId, paymentDTO);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Internal server error"));
        }
    }

    @DeleteMapping("/Material/{billNo}/payments/{paymentId}")
    @PreAuthorize("hasAnyRole('Admin')")
    public ResponseEntity<Map<String, Object>> deletePayment(
            @PathVariable Double billNo,
            @PathVariable Long paymentId) {
        try {
            Map<String, Object> response = meterialService.deletePayment(billNo, paymentId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Internal server error"));
        }
    }

    @GetMapping("/filteredMaterials")
    @PreAuthorize("hasAnyRole('Admin')")
    public ResponseEntity<List<MaterialBillResponseDTO>> getFilteredMaterials(
            @RequestParam Long vendorId,
            @RequestParam Long projectId) {
        List<MaterialBillResponseDTO> materials = meterialService.getFilteredMaterials(vendorId, projectId);
        return ResponseEntity.ok(materials);
    }
    @GetMapping("/filteredSingleMaterials")
    @PreAuthorize("hasAnyRole('Admin')")
    public ResponseEntity<List<SingleMaterialResponseDTO>> getFilteredMaterials(
            @RequestParam Double billNo,
            @RequestParam Long projectId,
            @RequestParam Long vendorId) {

        List<SingleMaterialResponseDTO> materials = meterialService.getMaterialsByBillNoAndProjectIdAndVendorId(billNo, projectId, vendorId);
        return ResponseEntity.ok(materials);
    }


    @GetMapping("/SingleBill/{billNo}/{projectId}")
    @PreAuthorize("hasAnyRole('Admin','AppUser')")
    public ResponseEntity<Map<String, Object>> getBillByBillNo(
            @PathVariable String billNo,
            @PathVariable Long projectId) {
        Map<String, Object> billDetail = meterialService.getBillDetailByBillNo(billNo, projectId);
        return ResponseEntity.ok(billDetail);
    }

    //update materials
    @PutMapping("/UpdateMaterial/{materialId}")
    @PreAuthorize("hasAnyRole('Admin')")
    public ResponseEntity<Map<String,Object>> updateMaterials(
            @PathVariable Long materialId,
            @RequestBody MaterialDTO materialDTO){
        try {
            Map<String, Object> response = meterialService.updateMaterial(materialId, materialDTO);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Internal server error"));
        }
    }


    @DeleteMapping("/DeleteVendorMeterial/{id}")
    @PreAuthorize("hasAnyRole('Admin')")
    public ResponseEntity<Map<String,String>> deleteMeterial(@PathVariable Long id){
        try{
            Material material=materialRepository.findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException("Meterial ID :"+id+" Not found"));
            materialRepository.delete(material);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Meterial deleted successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to delete material."));
        }
    }

    @GetMapping("/Material/{id}")
    @PreAuthorize("hasAnyRole('Admin')")
    public ResponseEntity<?> getMaterialById(@PathVariable Long id) {
        try {
            Material material = materialRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Material ID " + id + " not found"));

            Map<String, Object> response = new HashMap<>();
            response.put("id", material.getId());
            response.put("name", material.getName());
            response.put("type", material.getType());
            response.put("quantity", material.getQuantity());
            response.put("price", material.getPrice());
            response.put("addedOn", material.getAddedOn());
            response.put("billNo", material.getBillNo());

            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch material details."));
        }
    }

    // delete bill

    @DeleteMapping("/projects/{projectId}/{vendorId}/delete-bill/{billNo}")
    @PreAuthorize("hasAnyRole('Admin','AppUser')")
    public ResponseEntity<String> deleteBill(
            @PathVariable Long projectId,
            @PathVariable Long vendorId,
            @PathVariable String billNo) {
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

            List<Material> materialsToDelete = materialRepository.findByProjectIdAndVendorIdAndBillNo(projectId, vendorId, Double.valueOf(billNo));

            if (materialsToDelete.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No materials found for Bill No: " + billNo);
            }

            materialRepository.deleteAll(materialsToDelete);

            return ResponseEntity.ok("Bill No: " + billNo + " and associated materials deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting bill.");
        }
    }


}
