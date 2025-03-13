package com.royalmade.service;

import com.royalmade.dto.VendorPaymentDTO;
import com.royalmade.entity.Material;
import com.royalmade.entity.Project;
import com.royalmade.entity.Vendor;
import com.royalmade.entity.VendorPayment;
import com.royalmade.repo.MaterialRepository;
import com.royalmade.repo.ProjectRepository;
import com.royalmade.repo.VendorPaymentRepository;
import com.royalmade.repo.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MeterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private VendorPaymentRepository vendorPaymentRepository;
    @Autowired
    private VendorRepository vendorRepository;


    @Autowired
    private ProjectRepository projectRepository;

        public Vendor addVendors(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

//    public List<Map<String, Object>> getBillDetailsByVendor(Long vendorId) {
//        List<Material> expenses = materialRepository.findByVendorId(vendorId);
//        if (expenses.isEmpty()) {
//            throw new RuntimeException("No bills found for the vendor.");
//        }
//
//        Map<Double, Map<String, Object>> billDetailsMap = new HashMap<>();
//
//        for (Material exp : expenses) {
//            double billNo = exp.getBillNo();
//
//            // Fetch payments only for this bill
//            List<VendorPayment> payments = vendorPaymentRepository.findByBillNo(billNo);
//
//            // If this billNo is not yet added, initialize a new structure
//            if (!billDetailsMap.containsKey(billNo)) {
//                Vendor vendor = exp.getVendor();
//                Map<String, Object> billDetails = new HashMap<>();
//                billDetails.put("billNo", billNo);
//                billDetails.put("vendor", Map.of("name", vendor.getName(), "phoneno", vendor.getPhoneNo()));
//                billDetails.put("materials", new ArrayList<Map<String, Object>>());
//                billDetails.put("total", 0.0);
//                billDetails.put("vendorPaidAmount", 0.0);
//                billDetails.put("remainingAmount", 0.0);
//                billDetails.put("payment", new ArrayList<Map<String, Object>>());
//                billDetailsMap.put(billNo, billDetails);
//            }
//
//            Map<String, Object> billDetails = billDetailsMap.get(billNo);
//            List<Map<String, Object>> materials = (List<Map<String, Object>>) billDetails.get("materials");
//            double total = (double) billDetails.get("total");
//
//            // Add material details
//            Map<String, Object> material = new HashMap<>();
//            material.put("id", exp.getId());
//            material.put("name", exp.getName());
//            material.put("type", exp.getType());
//            material.put("quantity", exp.getQuantity());
//            material.put("price", exp.getPrice());
//            material.put("addedOn", exp.getAddedOn());
//            material.put("billNo", exp.getBillNo());
//            materials.add(material);
//
//            // Update total amount
//            total += exp.getPrice();
//            billDetails.put("total", total);
//
//            // Calculate payment details
//            double vendorPaidAmount = payments.stream().mapToDouble(VendorPayment::getAmount).sum();
//            double remainingAmount = total - vendorPaidAmount;
//
//            List<Map<String, Object>> paymentDetails = new ArrayList<>();
//            for (VendorPayment payment : payments) {
//                Map<String, Object> paymentInfo = new HashMap<>();
//                paymentInfo.put("expensePayDate", payment.getPayDate());
//                paymentInfo.put("expenseAmount", payment.getAmount());
//                paymentInfo.put("expensePayStatus", payment.getPaymentStatus().toString());
//                paymentInfo.put("remark", payment.getRemark());
//                paymentDetails.add(paymentInfo);
//            }
//
//            billDetails.put("vendorPaidAmount", vendorPaidAmount);
//            billDetails.put("remainingAmount", remainingAmount);
//            billDetails.put("payment", paymentDetails);
//        }
//
//        return new ArrayList<>(billDetailsMap.values());
//    }
//

//    public List<Map<String, Object>> getBillDetailsByVendor(Long vendorId) {
//        List<Material> expenses = materialRepository.findByVendorId(vendorId);
//        if (expenses.isEmpty()) {
//            throw new RuntimeException("No bills found for the vendor.");
//        }
//
//        Map<Double, Map<String, Object>> billDetailsMap = new HashMap<>();
//
//        for (Material exp : expenses) {
//            double billNo = exp.getBillNo();
//
//            // Fetch payments only for this specific bill
//            List<VendorPayment> payments = vendorPaymentRepository.findByBillNo(billNo);
//            if (payments.isEmpty()) {
//                continue; // Skip bills with no payments
//            }
//
//            // If this billNo is not yet added, initialize a new structure
//            if (!billDetailsMap.containsKey(billNo)) {
//                Vendor vendor = exp.getVendor();
//                Map<String, Object> billDetails = new HashMap<>();
//                billDetails.put("billNo", billNo);
//                billDetails.put("vendor", Map.of("name", vendor.getName(), "phoneno", vendor.getPhoneNo()));
//                billDetails.put("materials", new ArrayList<Map<String, Object>>());
//                billDetails.put("total", 0.0);
//                billDetails.put("vendorPaidAmount", 0.0);
//                billDetails.put("remainingAmount", 0.0);
//                billDetails.put("payment", new ArrayList<Map<String, Object>>());
//                billDetailsMap.put(billNo, billDetails);
//            }
//
//            Map<String, Object> billDetails = billDetailsMap.get(billNo);
//            List<Map<String, Object>> materials = (List<Map<String, Object>>) billDetails.get("materials");
//            double total = (double) billDetails.get("total");
//
//            // Add material details
//            Map<String, Object> material = new HashMap<>();
//            material.put("id", exp.getId());
//            material.put("name", exp.getName());
//            material.put("type", exp.getType());
//            material.put("quantity", exp.getQuantity());
//            material.put("price", exp.getPrice());
//            material.put("addedOn", exp.getAddedOn());
//            material.put("billNo", exp.getBillNo());
//            materials.add(material);
//
//            // Update total amount
//            total += exp.getPrice();
//            billDetails.put("total", total);
//
//            // Calculate payment details
//            double vendorPaidAmount = payments.stream().mapToDouble(VendorPayment::getAmount).sum();
//            double remainingAmount = total - vendorPaidAmount;
//
//            List<Map<String, Object>> paymentDetails = new ArrayList<>();
//            for (VendorPayment payment : payments) {
//                Map<String, Object> paymentInfo = new HashMap<>();
//                paymentInfo.put("expensePayDate", payment.getPayDate());
//                paymentInfo.put("expenseAmount", payment.getAmount());
//                paymentInfo.put("expensePayStatus", payment.getPaymentStatus().toString());
//                paymentInfo.put("remark", payment.getRemark());
//                paymentDetails.add(paymentInfo);
//            }
//
//            billDetails.put("vendorPaidAmount", vendorPaidAmount);
//            billDetails.put("remainingAmount", remainingAmount);
//            billDetails.put("payment", paymentDetails);
//        }
//
//        return new ArrayList<>(billDetailsMap.values());
//    }


    public Map<String, Object> getBillDetailsByBillNo(Double billNo) {
        List<Material> materials = materialRepository.findByBillNo(billNo);
        if (materials.isEmpty()) {
            throw new RuntimeException("No materials found for this bill number.");
        }

        Vendor vendor = materials.get(0).getVendor();
        double totalAmount = materials.stream().mapToDouble(Material::getPrice).sum();

        List<Map<String, Object>> materialsList = materials.stream()
                .map(mat -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", mat.getId());
                    map.put("name", mat.getName());
                    map.put("type", mat.getType());
                    map.put("quantity", mat.getQuantity());
                    map.put("price", mat.getPrice());
                    map.put("addedOn", mat.getAddedOn());
                    map.put("billNo", mat.getBillNo());
                    return map;
                })
                .collect(Collectors.toList());

        List<VendorPayment> payments = vendorPaymentRepository.findByMaterial_BillNo(billNo);
        double vendorPaidAmount = payments.stream().mapToDouble(VendorPayment::getAmount).sum();
        double remainingAmount = totalAmount - vendorPaidAmount;

        List<Map<String, Object>> paymentDetails = payments.stream()
                .map(pay -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("expensePayDate", pay.getPayDate());
                    map.put("expenseAmount", pay.getAmount());
                    map.put("expensePayStatus", pay.getPaymentStatus().toString());
                    map.put("remark", pay.getRemark());
                    return map;
                })
                .collect(Collectors.toList());

        return Map.of(
                "billNo", billNo,
                "vendor", Map.of("name", vendor.getName(), "phoneno", vendor.getPhoneNo()),
                "materials", materialsList,
                "total", totalAmount,
                "vendorPaidAmount", vendorPaidAmount,
                "remainingAmount", remainingAmount,
                "payment", paymentDetails
        );
    }


  //old get All bills

//    public List<Map<String, Object>> getBillDetailsByVendor(Long vendorId) {
//        List<Material> expenses = materialRepository.findByVendorId(vendorId);
//        if (expenses.isEmpty()) {
//            throw new RuntimeException("No bills found for the vendor.");
//        }
//
//        Map<String, Map<String, Object>> billDetailsMap = new HashMap<>();
//
//        for (Material exp : expenses) {
//            String billNo = String.valueOf(exp.getBillNo()); // Convert billNo to String properly
//
//            List<VendorPayment> payments = vendorPaymentRepository.findByVendorIdAndBillNo(vendorId, billNo);
//
//            if (!billDetailsMap.containsKey(billNo)) {
//                Vendor vendor = exp.getVendor();
//                Map<String, Object> billDetails = new HashMap<>();
//                billDetails.put("billNo", billNo);
//                billDetails.put("vendor", Map.of("name", vendor.getName(), "phoneno", vendor.getPhoneNo()));
//                billDetails.put("materials", new ArrayList<Map<String, Object>>());
//                billDetails.put("total", 0.0);
//                billDetails.put("vendorPaidAmount", 0.0);
//                billDetails.put("remainingAmount", 0.0);
//                billDetails.put("payment", new ArrayList<Map<String, Object>>());
//                billDetailsMap.put(billNo, billDetails);
//            }
//
//            Map<String, Object> billDetails = billDetailsMap.get(billNo); // Correct key lookup
//            if (billDetails == null) {
//                throw new RuntimeException("Bill details not found for billNo: " + billNo);
//            }
//
//            List<Map<String, Object>> materials = (List<Map<String, Object>>) billDetails.get("materials");
//            double total = (double) billDetails.get("total");
//
//            Map<String, Object> material = new HashMap<>();
//            material.put("id", exp.getId());
//            material.put("name", exp.getName());
//            material.put("type", exp.getType());
//            material.put("quantity", exp.getQuantity());
//            material.put("price", exp.getPrice());
//            material.put("addedOn", exp.getAddedOn());
//            material.put("billNo", billNo);
//            materials.add(material);
//
//            total += exp.getPrice();
//            billDetails.put("total", total);
//
//            double vendorPaidAmount = payments.stream().mapToDouble(VendorPayment::getAmount).sum();
//            double remainingAmount = total - vendorPaidAmount;
//
//            List<Map<String, Object>> paymentDetails = new ArrayList<>();
//            for (VendorPayment payment : payments) {
//                Map<String, Object> paymentInfo = new HashMap<>();
//                paymentInfo.put("expensePayDate", payment.getPayDate());
//                paymentInfo.put("expenseAmount", payment.getAmount());
//                paymentInfo.put("expensePayStatus", payment.getPaymentStatus().toString());
//                paymentInfo.put("remark", payment.getRemark());
//                paymentDetails.add(paymentInfo);
//            }
//
//            billDetails.put("vendorPaidAmount", vendorPaidAmount);
//            billDetails.put("remainingAmount", remainingAmount);
//            billDetails.put("payment", paymentDetails);
//        }
//
//        return new ArrayList<>(billDetailsMap.values());
//    }
//

//    public Map<String, Object> addPayment(Double billNo, VendorPaymentDTO paymentDTO) {
//        List<Material> materials = materialRepository.findByBillNo(billNo);
//        if (materials.isEmpty()) {
//            throw new RuntimeException("No materials found for this bill number.");
//        }
//
//        Vendor vendor = materials.get(0).getVendor();
//        double totalAmount = materials.stream().mapToDouble(Material::getPrice).sum();
//
//        Material material = materials.get(0); // Use the first material for payment association
////        VendorPayment payment = new VendorPayment();
////        payment.setMaterial(material);
////        payment.setPayDate(LocalDate.now());
////        payment.setAmount(paymentDTO.getAmount());
////        payment.setRemark(paymentDTO.getRemark());
////        payment.setPaymentStatus(paymentDTO.getPaymentStatus());
////
////        vendorPaymentRepository.save(payment);
//        for (Material mat : materials) {
//            VendorPayment payment = new VendorPayment();
//            payment.setMaterial(mat);  // Associate each material with the payment
//            payment.setPayDate(LocalDate.now());
//            payment.setAmount(paymentDTO.getAmount() / materials.size());  // Split payment if needed
//            payment.setRemark(paymentDTO.getRemark());
//            payment.setPaymentStatus(paymentDTO.getPaymentStatus());
//
//            vendorPaymentRepository.save(payment);
//        }
//
//
//        List<VendorPayment> payments = vendorPaymentRepository.findByMaterial_BillNo(billNo);
//        double vendorPaidAmount = payments.stream().mapToDouble(VendorPayment::getAmount).sum();
//        double remainingAmount = totalAmount - vendorPaidAmount;
//
//        List<Map<String, Object>> materialsList = materials.stream()
//                .map(mat -> new HashMap<String, Object>(Map.of(
//                        "id", mat.getId(),
//                        "name", mat.getName(),
//                        "type", mat.getType(),
//                        "quantity", mat.getQuantity(),
//                        "price", mat.getPrice(),
//                        "addedOn", mat.getAddedOn(),
//                        "billNo", mat.getBillNo()
//                )))
//                .collect(Collectors.toList());
//
//        List<Map<String, Object>> paymentDetails = payments.stream()
//                .map(pay -> new HashMap<String, Object>(Map.of(
//                        "payDate", pay.getPayDate(),
//                        "amount", pay.getAmount(),
//                        "status", pay.getPaymentStatus().toString(),
//                        "remark", pay.getRemark()
//                )))
//                .collect(Collectors.toList());
//
//
//        return Map.of(
//                "vendor", Map.of("name", vendor.getName(), "phoneno", vendor.getPhoneNo()),
//                "materials", materialsList,
//                "total", totalAmount,
//                "vendorPaidAmount", vendorPaidAmount,
//                "remainingAmount", remainingAmount,
//                "payment", paymentDetails
//        );
//    }
//



    public Map<String, Object> addPayment(Double billNo, VendorPaymentDTO paymentDTO) {
        List<Material> materials = materialRepository.findByBillNo(billNo);
        if (materials.isEmpty()) {
            throw new RuntimeException("No materials found for this bill number.");
        }

        Vendor vendor = vendorRepository.findById(paymentDTO.getVendorId())
                .orElseThrow(() -> new RuntimeException("Vendor not found with ID: " + paymentDTO.getVendorId()));

        List<Material> vendorMaterials = materials.stream()
                .filter(mat -> mat.getVendor().getId().equals(paymentDTO.getVendorId()))
                .collect(Collectors.toList());

        if (vendorMaterials.isEmpty()) {
            throw new RuntimeException("No materials found for this vendor and bill number.");
        }

        double totalAmount = vendorMaterials.stream().mapToDouble(Material::getPrice).sum();
        Material material = vendorMaterials.get(0);


        Project project = projectRepository.findById(paymentDTO.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + paymentDTO.getProjectId()));

        VendorPayment payment = new VendorPayment();
        payment.setMaterial(material);
        payment.setBillNo(billNo);
        payment.setVendor(vendor);
        payment.setPayDate(paymentDTO.getPayDate() != null ? paymentDTO.getPayDate() : LocalDate.now());
        payment.setAmount(paymentDTO.getAmount());
        payment.setRemark(paymentDTO.getRemark());
        payment.setPaymentStatus(paymentDTO.getPaymentStatus());
        payment.setProject(project); // ✅ Ensure this line uses the correct Project entity

        vendorPaymentRepository.save(payment);

        List<VendorPayment> payments = vendorPaymentRepository.findByVendorAndMaterial_BillNo(vendor, billNo);
        double vendorPaidAmount = payments.stream().mapToDouble(VendorPayment::getAmount).sum();
        double remainingAmount = totalAmount - vendorPaidAmount;

        List<Map<String, Object>> materialsList = vendorMaterials.stream()
                .map(mat -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", mat.getId());
                    map.put("name", mat.getName());
                    map.put("type", mat.getType());
                    map.put("quantity", mat.getQuantity());
                    map.put("price", mat.getPrice());
                    map.put("addedOn", mat.getAddedOn());
                    map.put("billNo", mat.getBillNo());
                    return map;
                })
                .collect(Collectors.toList());

        List<Map<String, Object>> paymentDetails = payments.stream()
                .map(pay -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("payDate", pay.getPayDate());
                    map.put("amount", pay.getAmount());
                    map.put("status", pay.getPaymentStatus().toString());
                    map.put("remark", pay.getRemark());
                    return map;
                })
                .collect(Collectors.toList());

        return Map.of(
                "vendor", Map.of("id", vendor.getId(), "name", vendor.getName(), "phoneno", vendor.getPhoneNo()),
                "materials", materialsList,
                "total", totalAmount,
                "vendorPaidAmount", vendorPaidAmount,
                "remainingAmount", remainingAmount,
                "payment", paymentDetails
        );
    }



    public List<Vendor> getAllVendor() {
            return vendorRepository.findAll();
    }

    public ResponseEntity<Vendor> getVendorById(Long id) {
            Vendor vendor = vendorRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Vendor is not found " + id));
            return ResponseEntity.ok(vendor);
        }

    public void deleteVendorById(Long id) {
        if (!vendorRepository.existsById(id)) {
            throw new RuntimeException("Vendor with ID " + id + " not found");
        }
    }

    public List<Map<String, Object>> getBillDetailsByVendor(Long vendorId, Long projectId) {
        List<Material> expenses = materialRepository.findByVendorIdAndProjectId(vendorId, projectId);
        if (expenses.isEmpty()) {
            throw new RuntimeException("No bills found for the vendor in this project.");
        }

        Map<String, Map<String, Object>> billDetailsMap = new HashMap<>();

        for (Material exp : expenses) {
            String billNo = String.valueOf(exp.getBillNo());

            // Fetch vendor payments based on vendorId, projectId, and billNo
//            List<VendorPayment> payments = vendorPaymentRepository.findByVendorIdAndProjectIdAndBillNo(vendorId, projectId, billNo);
            List<VendorPayment> payments = vendorPaymentRepository.findByVendorIdAndProjectIdAndBillNo(vendorId, projectId, exp.getBillNo());

            billDetailsMap.putIfAbsent(billNo, new HashMap<>());
            Map<String, Object> billDetails = billDetailsMap.get(billNo);

            if (!billDetails.containsKey("vendor")) {
                Vendor vendor = exp.getVendor();
                billDetails.put("billNo", billNo);
                billDetails.put("vendor", Map.of("name", vendor.getName(), "phoneno", vendor.getPhoneNo()));
                billDetails.put("materials", new ArrayList<Map<String, Object>>());
                billDetails.put("total", 0.0);
                billDetails.put("vendorPaidAmount", 0.0);
                billDetails.put("remainingAmount", 0.0);
                billDetails.put("payment", new ArrayList<Map<String, Object>>());
            }

            List<Map<String, Object>> materials = (List<Map<String, Object>>) billDetails.get("materials");
            double total = (double) billDetails.get("total");

            Map<String, Object> material = new HashMap<>();
            material.put("id", exp.getId());
            material.put("name", exp.getName());
            material.put("type", exp.getType());
            material.put("quantity", exp.getQuantity());
            material.put("price", exp.getPrice());
            material.put("addedOn", exp.getAddedOn());
            material.put("billNo", billNo);
            materials.add(material);

            total += exp.getPrice();
            billDetails.put("total", total);

            double vendorPaidAmount = payments.stream().mapToDouble(VendorPayment::getAmount).sum();
            double remainingAmount = total - vendorPaidAmount;

            List<Map<String, Object>> paymentDetails = new ArrayList<>();
            for (VendorPayment payment : payments) {
                Map<String, Object> paymentInfo = new HashMap<>();
                paymentInfo.put("expensePayDate", payment.getPayDate());
                paymentInfo.put("expenseAmount", payment.getAmount());
                paymentInfo.put("expensePayStatus", payment.getPaymentStatus().toString());
                paymentInfo.put("remark", payment.getRemark());
                paymentDetails.add(paymentInfo);
            }

            billDetails.put("vendorPaidAmount", vendorPaidAmount);
            billDetails.put("remainingAmount", remainingAmount);
            billDetails.put("payment", paymentDetails);
        }

        return new ArrayList<>(billDetailsMap.values());
    }

}