package com.royalmade.service;

import com.royalmade.dto.*;
import com.royalmade.entity.Material;
import com.royalmade.entity.Project;
import com.royalmade.entity.Vendor;
import com.royalmade.entity.VendorPayment;
import com.royalmade.repo.MaterialRepository;
import com.royalmade.repo.ProjectRepository;
import com.royalmade.repo.VendorPaymentRepository;
import com.royalmade.repo.VendorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

        public Vendor addVendors(VendorResponseDTO vendordto) {
            Project project = projectRepository.findById(vendordto.getProjectId())
                    .orElseThrow(() -> new EntityNotFoundException("Project not found"));

            Vendor vendor = new Vendor();
            vendor.setName(vendordto.getName());
            vendor.setPhoneNo(vendordto.getPhoneNo());
            vendor.setProject(project);

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
// old code http://localhost:8080/api/bills/1/34 this api

//    public List<Map<String, Object>> getBillDetailsByVendor(Long vendorId, Long projectId) {
//        List<Material> expenses = materialRepository.findByVendorIdAndProjectId(vendorId, projectId);
//        if (expenses.isEmpty()) {
//            throw new RuntimeException("No bills found for the vendor in this project.");
//        }
//
//        Map<String, Map<String, Object>> billDetailsMap = new HashMap<>();
//
//        for (Material exp : expenses) {
//            String billNo = String.valueOf(exp.getBillNo());
//
//            // Fetch vendor payments based on vendorId, projectId, and billNo
////            List<VendorPayment> payments = vendorPaymentRepository.findByVendorIdAndProjectIdAndBillNo(vendorId, projectId, billNo);
//            List<VendorPayment> payments = vendorPaymentRepository.findByVendorIdAndProjectIdAndBillNo(vendorId, projectId, exp.getBillNo());
//
//            billDetailsMap.putIfAbsent(billNo, new HashMap<>());
//            Map<String, Object> billDetails = billDetailsMap.get(billNo);
//
//            if (!billDetails.containsKey("vendor")) {
//                Vendor vendor = exp.getVendor();
//                billDetails.put("billNo", billNo);
//                billDetails.put("vendor", Map.of("name", vendor.getName(), "phoneno", vendor.getPhoneNo()));
//                billDetails.put("materials", new ArrayList<Map<String, Object>>());
//                billDetails.put("total", 0.0);
//                billDetails.put("vendorPaidAmount", 0.0);
//                billDetails.put("remainingAmount", 0.0);
//                billDetails.put("payment", new ArrayList<Map<String, Object>>());
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
//                paymentInfo.put("id", payment.getId());
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

    public List<Map<String, Object>> getBillDetailsByVendor(Long vendorId, Long projectId) {
        List<Material> expenses = materialRepository.findByVendorIdAndProjectId(vendorId, projectId);
        if (expenses.isEmpty()) {
            throw new RuntimeException("No bills found for the vendor in this project.");
        }

        Map<String, Map<String, Object>> billDetailsMap = new HashMap<>();

        for (Material exp : expenses) {
            String billNo = String.valueOf(exp.getBillNo());
            List<VendorPayment> payments = vendorPaymentRepository.findByVendorIdAndProjectIdAndBillNo(vendorId, projectId, exp.getBillNo());

            billDetailsMap.putIfAbsent(billNo, new HashMap<>());
            Map<String, Object> billDetails = billDetailsMap.get(billNo);

            if (!billDetails.containsKey("vendor")) {
                Vendor vendor = exp.getVendor();
                Project project = vendor.getProject();
                String projectName = project != null ? project.getName() : "N/A";

                billDetails.put("billNo", billNo);
                billDetails.put("vendor", Map.of(
                        "name", vendor.getName(),
                        "phoneno", vendor.getPhoneNo(),
                        "projectName", projectName
                ));
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
                paymentInfo.put("id", payment.getId());
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


    public VendorPayment getVendorSinglePaymentById(Long id) {
            return vendorPaymentRepository.findById(id)
                    .orElseThrow(()-> new EntityNotFoundException("Id is Not Found"));

    }

    public Map<String, Object> updatePayment(Double billNo, Long paymentId, VendorPaymentDTO paymentDTO) {
        VendorPayment payment = vendorPaymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + paymentId));

        if (!payment.getBillNo().equals(billNo)) {
            throw new RuntimeException("Payment does not match the provided bill number.");
        }

        Vendor vendor = vendorRepository.findById(paymentDTO.getVendorId())
                .orElseThrow(() -> new RuntimeException("Vendor not found with ID: " + paymentDTO.getVendorId()));

        List<Material> materials = materialRepository.findByBillNo(billNo);
        List<Material> vendorMaterials = materials.stream()
                .filter(mat -> mat.getVendor().getId().equals(paymentDTO.getVendorId()))
                .collect(Collectors.toList());

        if (vendorMaterials.isEmpty()) {
            throw new RuntimeException("No materials found for this vendor and bill number.");
        }

        double totalAmount = vendorMaterials.stream().mapToDouble(Material::getPrice).sum();

        Project project = projectRepository.findById(paymentDTO.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + paymentDTO.getProjectId()));

        payment.setPayDate(paymentDTO.getPayDate() != null ? paymentDTO.getPayDate() : LocalDate.now());
        payment.setAmount(paymentDTO.getAmount());
        payment.setRemark(paymentDTO.getRemark());
        payment.setPaymentStatus(paymentDTO.getPaymentStatus());
        payment.setProject(project);

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


    public Map<String, Object> deletePayment(Double billNo, Long paymentId) {
        VendorPayment payment = vendorPaymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + paymentId));

        if (!payment.getBillNo().equals(billNo)) {
            throw new RuntimeException("Payment does not match the provided bill number.");
        }

        Vendor vendor = payment.getVendor();

        vendorPaymentRepository.delete(payment);

        List<Material> materials = materialRepository.findByBillNo(billNo);
        List<Material> vendorMaterials = materials.stream()
                .filter(mat -> mat.getVendor().getId().equals(vendor.getId()))
                .collect(Collectors.toList());

        double totalAmount = vendorMaterials.stream().mapToDouble(Material::getPrice).sum();
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
                "message", "Payment with ID " + paymentId + " has been deleted successfully.",
                "vendor", Map.of("id", vendor.getId(), "name", vendor.getName(), "phoneno", vendor.getPhoneNo()),
                "materials", materialsList,
                "total", totalAmount,
                "vendorPaidAmount", vendorPaidAmount,
                "remainingAmount", remainingAmount,
                "payment", paymentDetails
        );
    }





    public List<MaterialBillResponseDTO> getFilteredMaterials(Long vendorId, Long projectId) {
        return materialRepository.findUniqueBillNosByVendorAndProject(vendorId, projectId);
    }


    public List<SingleMaterialResponseDTO> getMaterialsByBillNoAndProjectIdAndVendorId(Double billNo, Long projectId, Long vendorId) {
        List<SingleMaterialResponseDTO> materials = materialRepository.findByBillNoAndProjectIdAndVendorId(billNo, projectId, vendorId);

        for (SingleMaterialResponseDTO material : materials) {
            List<PaymentDTO> payments = vendorPaymentRepository.findPaymentsByMaterialId(material.getId()); // Fetch separately
            material.setPayments(payments);
        }
        return materials;
    }


    public Map<String, Object> getBillDetailByBillNo(String billNo, Long projectId) {
        List<Material> expenses = materialRepository.findByBillNoAndProjectId(Double.valueOf(billNo), projectId);

        if (expenses.isEmpty()) {
            throw new RuntimeException("No bill found for the given bill number and project.");
        }

        Material firstExpense = expenses.get(0);
        Vendor vendor = firstExpense.getVendor();

        List<VendorPayment> payments = vendorPaymentRepository.findByBillNoAndProjectId(Double.valueOf(billNo), projectId);

        Map<String, Object> billDetails = new HashMap<>();
        billDetails.put("billNo", billNo);
        billDetails.put("vendor", Map.of("name", vendor.getName(), "phoneno", vendor.getPhoneNo()));
        billDetails.put("materials", new ArrayList<Map<String, Object>>());
        billDetails.put("total", 0.0);
        billDetails.put("vendorPaidAmount", 0.0);
        billDetails.put("remainingAmount", 0.0);
        billDetails.put("payment", new ArrayList<Map<String, Object>>());

        double total = 0.0;
        List<Map<String, Object>> materials = (List<Map<String, Object>>) billDetails.get("materials");

        for (Material exp : expenses) {
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
        }

        billDetails.put("total", total);
        double vendorPaidAmount = payments.stream().mapToDouble(VendorPayment::getAmount).sum();
        double remainingAmount = total - vendorPaidAmount;

        List<Map<String, Object>> paymentDetails = new ArrayList<>();
        for (VendorPayment payment : payments) {
            Map<String, Object> paymentInfo = new HashMap<>();
            paymentInfo.put("id",payment.getId());
            paymentInfo.put("expensePayDate", payment.getPayDate());
            paymentInfo.put("expenseAmount", payment.getAmount());
            paymentInfo.put("expensePayStatus", payment.getPaymentStatus().toString());
            paymentInfo.put("remark", payment.getRemark());
            paymentDetails.add(paymentInfo);
        }

        billDetails.put("vendorPaidAmount", vendorPaidAmount);
        billDetails.put("remainingAmount", remainingAmount);
        billDetails.put("payment", paymentDetails);

        return billDetails;
    }

    //update materials
    public Map<String, Object> updateMaterial(Long materialId, MaterialDTO materialDTO) {
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new RuntimeException("Material not found with ID: " + materialId));

        material.setName(materialDTO.getName());
        material.setType(materialDTO.getType());
        material.setQuantity(materialDTO.getQuantity());
        material.setPrice(materialDTO.getPrice());
        material.setAddedOn(materialDTO.getAddedOn());

        materialRepository.save(material);

        return Map.of(
                "message", "Material updated successfully",
                "material", Map.of(
                        "id", material.getId(),
                        "name", material.getName(),
                        "type", material.getType(),
                        "quantity", material.getQuantity(),
                        "price", material.getPrice(),
                        "addedOn", material.getAddedOn()
                )
        );
    }

    public ResponseEntity<Vendor> getVendorByprojectId(Long projectId) {
        Vendor vendor = vendorRepository.getVendorByProjectId(projectId);

        if (vendor != null) {
            return ResponseEntity.ok(vendor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public Map<String, Object> addPaymentByVendorAndProject(Long vendorId, Long projectId, VendorPaymentDTO paymentDTO) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        List<Material> materials = materialRepository.findByVendorAndProject(vendor, project);
        if (materials.isEmpty()) {
            throw new RuntimeException("No materials found for given vendor and project.");
        }

        double totalAmount = materials.stream().mapToDouble(Material::getPrice).sum();
        Material material = materials.get(0);

        VendorPayment payment = new VendorPayment();
        payment.setMaterial(material);
        payment.setVendor(vendor);
        payment.setProject(project);
        payment.setAmount(paymentDTO.getAmount());
        payment.setPayDate(paymentDTO.getPayDate() != null ? paymentDTO.getPayDate() : LocalDate.now());
        payment.setRemark(paymentDTO.getRemark());
        payment.setPaymentStatus(paymentDTO.getPaymentStatus());
        payment.setBillNo(material.getBillNo());

        vendorPaymentRepository.save(payment);

        List<VendorPayment> payments = vendorPaymentRepository.findByVendorAndProject(vendor, project);
        double vendorPaidAmount = payments.stream().mapToDouble(VendorPayment::getAmount).sum();
        double remainingAmount = totalAmount - vendorPaidAmount;

        List<Map<String, Object>> materialList = materials.stream().map(mat -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", mat.getId());
            map.put("name", mat.getName());
            map.put("type", mat.getType());
            map.put("quantity", mat.getQuantity());
            map.put("price", mat.getPrice());
            map.put("addedOn", mat.getAddedOn());
            map.put("billNo", mat.getBillNo());
            return map;
        }).collect(Collectors.toList());

        List<Map<String, Object>> paymentList = payments.stream().map(pay -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", pay.getId());
            map.put("amount", pay.getAmount());
            map.put("payDate", pay.getPayDate());
            map.put("status", pay.getPaymentStatus());
            map.put("remark", pay.getRemark());
            return map;
        }).collect(Collectors.toList());


        return Map.of(
                "vendor", Map.of("id", vendor.getId(), "name", vendor.getName(), "phoneno", vendor.getPhoneNo()),
                "materials", materialList,
                "total", totalAmount,
                "vendorPaidAmount", vendorPaidAmount,
                "remainingAmount", remainingAmount,
                "payment", paymentList
        );
    }

    public ResponseEntity<List<Vendor>> getVendorsByProjectId(Long projectId) {
        List<Vendor> vendors = vendorRepository.findByProjectId(projectId);
        if (!vendors.isEmpty()) {
            return ResponseEntity.ok(vendors);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}