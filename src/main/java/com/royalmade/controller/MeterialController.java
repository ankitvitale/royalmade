package com.royalmade.controller;

import com.royalmade.dto.*;
import com.royalmade.dto.MeterialDto.*;
import com.royalmade.entity.*;
import com.royalmade.repo.*;
import com.royalmade.service.MeterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

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
    @GetMapping("/vendor-byProjectId/{projectId}")
    @PreAuthorize("hasAnyRole('Admin','AppUser')")
    public ResponseEntity<List<Vendor>> getVendorsByProjectId(@PathVariable Long projectId) {
        return meterialService.getVendorsByProjectId(projectId);
    }


    //    @GetMapping("/vendor-byProjectId/{projectId}")
//    @PreAuthorize("hasAnyRole('Admin','AppUser')")
//    public ResponseEntity<List<Vendor>> getVendorsByProjectId(@PathVariable Long projectId) {
//        return meterialService.getVendorsByProjectId(projectId);
//    }
    @DeleteMapping("/deleteVendor/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> deleteVendor(@PathVariable Long id) {
        meterialService.deleteVendorById(id);
        return ResponseEntity.ok("Vendor deleted successfully");
    }



    @PostMapping("/add")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<MaterialResponse> addMaterial(
            @RequestParam(required = false) Long vendorId,
            @RequestParam(required = false) Long projectId,
            @RequestBody MaterialRequest request) {
        return ResponseEntity.ok(meterialService.addMaterial(vendorId, projectId, request));
    }



    @GetMapping("/material/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<MaterialResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(meterialService.getMaterialById(id));
    }


    @GetMapping("/all")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<MaterialResponse> getAllMaterials(
            @RequestParam(required = false) Long vendorId,
            @RequestParam(required = false) Long projectId) {

        MaterialResponse response = meterialService.getAllMaterials(vendorId, projectId);
        return ResponseEntity.ok(response);
    }


@PutMapping("/update-items")
public ResponseEntity<MaterialUpdateResponse> updateMaterialItems(
        @RequestBody MaterialUpdateRequest request) {

    Material updatedMaterial = meterialService.updateMaterialItemsPartial(request);

    // Prepare response
    MaterialUpdateResponse response = new MaterialUpdateResponse();
    response.setMaterialId(updatedMaterial.getId());
    response.setTotalAmount(updatedMaterial.getTotalAmount());
    response.setAmountPaid(updatedMaterial.getAmountPaid());
    response.setBalanceAmount(updatedMaterial.getBalanceAmount());

    List<MaterialItemUpdateResponse> responseItems = updatedMaterial.getItems().stream()
            .map(i -> {
                MaterialItemUpdateResponse r = new MaterialItemUpdateResponse();
                r.setId(i.getId());
                r.setName(i.getName());
                r.setPrice(i.getPrice());
                r.setQuantity(i.getQuantity());
                r.setUnit(i.getUnit());
                double price = i.getPrice() != null ? i.getPrice() : 0;
                int qty = i.getQuantity() != null ? i.getQuantity() : 0;
                r.setTotalPrice(price * qty);
                return r;
            }).toList();

    response.setItems(responseItems);


    response.setItems(responseItems);
    return ResponseEntity.ok(response);
}
    @DeleteMapping("/delete-all-material/{materialId}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<String> deleteMaterial(@PathVariable Long materialId) {
        meterialService.deleteMaterial(materialId);
        return ResponseEntity.ok("Material deleted successfully");
    }


    @DeleteMapping("/delete-item/{itemId}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<String> deleteMaterialItem(@PathVariable Long itemId) {
        meterialService.deleteMaterialItem(itemId);
        return ResponseEntity.ok("Material item deleted and totals updated successfully");
    }


//    @PostMapping("/add-material-payment/{materialId}")
//    @PreAuthorize("hasRole('Admin')")
//    public ResponseEntity<VendorPaymentResponse> addPayment(
//            @PathVariable Long materialId,
//            @RequestBody VendorPaymentRequest request) {
//        return ResponseEntity.ok(meterialService.addPayment(materialId, request));
//    }


    @PostMapping("/add-material-payment")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<VendorPaymentResponse> addPayment(@RequestBody VendorPaymentRequest request) {
        VendorPaymentResponse saved = meterialService.addPayment(request);
        return ResponseEntity.ok(saved);
    }



    @GetMapping("/show-All-payment")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<List<VendorPaymentResponse>> searchPayments(
            @RequestParam(required = false) Long vendorId,
            @RequestParam(required = false) Long projectId) {
        return ResponseEntity.ok(meterialService.getPayments(vendorId, projectId));
    }
    @PutMapping("/update-payment/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<VendorPaymentResponse> updatePayment(
            @PathVariable Long id,
            @RequestBody VendorPaymentRequest request) {
        return ResponseEntity.ok(meterialService.updatePayment(id, request));
    }
    @DeleteMapping("/delete-payment/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<String> deletePayment(@PathVariable Long id) {
        meterialService.deletePayment(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("Payment deleted successfully");
    }
    @GetMapping("/payment/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<VendorPaymentResponse> getPaymentById(@PathVariable Long id) {
        return ResponseEntity.ok(meterialService.getPaymentById(id));
    }

}
