package com.royalmade.service;

import com.royalmade.dto.*;
import com.royalmade.dto.MeterialDto.*;
import com.royalmade.entity.*;
import com.royalmade.mapper.MaterialMapper;
import com.royalmade.mapper.VendorPaymentMapper;
import com.royalmade.repo.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
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
    private MaterialItemRepository materialItemRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private VendorPaymentMapper vendorPaymentMapper;


    @Autowired
    private MaterialMapper materialMapper;

    public Vendor addVendors(VendorResponseDTO vendordto) {
        Project project = projectRepository.findById(vendordto.getProjectId())
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        Vendor vendor = new Vendor();
        vendor.setName(vendordto.getName());
        vendor.setPhoneNo(vendordto.getPhoneNo());
        vendor.setProject(project);

        return vendorRepository.save(vendor);
    }

    public List<Vendor> getAllVendor() {
        return vendorRepository.findAll();
    }

    public void deleteVendorById(Long id) {
        if (!vendorRepository.existsById(id)) {
            throw new RuntimeException("Vendor with ID " + id + " not found");
        }
    }

    public ResponseEntity<Vendor> getVendorByprojectId(Long projectId) {
        Vendor vendor = vendorRepository.getVendorByProjectId(projectId);

        if (vendor != null) {
            return ResponseEntity.ok(vendor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Vendor> getVendorById(Long id) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor is not found " + id));
        return ResponseEntity.ok(vendor);
    }

    public ResponseEntity<List<Vendor>> getVendorsByProjectId(Long projectId) {
        List<Vendor> vendors = vendorRepository.findByProjectId(projectId);
        if (!vendors.isEmpty()) {
            return ResponseEntity.ok(vendors);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // old add material
    public MaterialResponse addMaterial(Long vendorId, Long projectId, MaterialRequest request) {
        if (vendorId == null && projectId == null) {
            throw new RuntimeException("Either vendorId or projectId must be provided");
        }

        Material material = new Material();
        MaterialDto materialDto = request.getMaterials().iterator().next();
        material.setDate(materialDto.getDate());

        if (vendorId != null) {
            Vendor vendor = vendorRepository.findById(vendorId)
                    .orElseThrow(() -> new RuntimeException("Vendor not found"));
            material.setVendor(vendor);
        }

        if (projectId != null) {
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new RuntimeException("Project not found"));
            material.setProject(project);
        }

        List<MaterialItem> items = materialDto.getItems().stream().map(i -> {
            MaterialItem item = new MaterialItem();
            item.setName(i.getName());
            item.setPrice(i.getPrice());
            item.setUnit(i.getUnit());
            item.setQuantity(i.getQuantity());
            item.setMaterial(material);
            i.setTotaliteamPrice(i.getPrice()*i.getQuantity());
            return item;
        }).collect(Collectors.toList());

        material.setItems(items);

        double totalAmount = items.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();

        material.setTotalAmount(totalAmount);
        material.setAmountPaid(0.0);
        material.setBalanceAmount(totalAmount);

        Material saved = materialRepository.save(material);

        return mapToResponse(saved); // reuse your helper
    }



    public MaterialResponse getMaterialById(Long id) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material not found"));
        return mapToResponse(material);
    }
    public MaterialResponse getAllMaterials(Long vendorId, Long projectId) {

        if (vendorId == null && projectId == null) {
            throw new RuntimeException("Either vendorId or projectId must be provided");
        }

        List<Material> materials = (vendorId != null)
                ? materialRepository.findByVendorId(vendorId)
                : materialRepository.findByProjectId(projectId);

        return mapToResponseWithGrandTotals(materials);
    }


    private MaterialResponse mapToResponseWithGrandTotals(List<Material> materials) {

        MaterialResponse response = new MaterialResponse();

        // ✅ Set grand totals across all materials
        double grandTotalAmount = materials.stream()
                .mapToDouble(Material::getTotalAmount)
                .sum();

        double grandAmountPaid = materials.stream()
                .mapToDouble(Material::getAmountPaid)
                .sum();

        double grandBalanceAmount = materials.stream()
                .mapToDouble(Material::getBalanceAmount)
                .sum();

        response.setTotalAmount(grandTotalAmount);
        response.setAmountPaid(grandAmountPaid);
        response.setBalanceAmount(grandBalanceAmount);

        // Convert each material to MaterialDto
        List<MaterialDto> materialDtos = materials.stream().map(material -> {

            // Convert MaterialItems to MaterialItemDto
            List<MaterialItemDto> itemDtos = material.getItems().stream().map(it -> {
                MaterialItemDto itemDto = new MaterialItemDto();
                itemDto.setItemId(it.getId());
                itemDto.setName(it.getName());
                itemDto.setPrice(it.getPrice());
                itemDto.setUnit(it.getUnit());
                itemDto.setQuantity(it.getQuantity());
                itemDto.setTotaliteamPrice(it.getPrice() * it.getQuantity());
                return itemDto;
            }).toList();

            // Wrap items inside MaterialDto
            MaterialDto dto = new MaterialDto();
            dto.setMaterialId(material.getId());
            dto.setDate(material.getDate());
            dto.setTotalAmount(material.getTotalAmount());
            dto.setItems(itemDtos);

            return dto;

        }).toList();

        // Add to response
        response.setMaterials(materialDtos);

        return response;
    }





    private MaterialResponse mapToResponse(Material material) {
        MaterialResponse response = new MaterialResponse();
      //  response.setId(material.getId());
      //  response.setDate(material.getDate());
        response.setTotalAmount(material.getTotalAmount());
        response.setAmountPaid(material.getAmountPaid());
        response.setBalanceAmount(material.getBalanceAmount());

        // Create one summary per material
        MaterialSummaryResponse summary = new MaterialSummaryResponse();
        summary.setItemId(material.getId());
        summary.setDate(material.getDate());
        summary.setTotal(material.getTotalAmount());

        List<MaterialItemResponse> itemResponses = new ArrayList<>();
        for (MaterialItem it : material.getItems()) {
            MaterialItemResponse ir = new MaterialItemResponse();
            ir.setId(it.getId());
            ir.setName(it.getName());
            ir.setPrice(it.getPrice());
            ir.setUnit(it.getUnit());
            ir.setQuantity(it.getQuantity());
            ir.setTotaliteamPrice(it.getPrice() * it.getQuantity());
            itemResponses.add(ir);
        }

        summary.setMaterials(itemResponses);

       // response.setItems(List.of(summary));

        return response;
    }



    // working code update material

//    public MaterialResponse updateMaterial(Long materialId, MaterialUpdateRequest request) {
//        Material material = materialRepository.findById(materialId)
//                .orElseThrow(() -> new RuntimeException("Material not found"));
//
//        // ✅ Update date
//        material.setDate(request.getDate());
//
//        // ✅ Replace items
//        material.getItems().clear();
//        for (MaterialItemUpdateRequest dto :
//                Optional.ofNullable(request.getItems()).orElse(Collections.emptyList())) {
//
//            MaterialItem item = new MaterialItem();
//            item.setId(dto.getId());
//            item.setName(dto.getName());
//            item.setPrice(dto.getPrice());
//            item.setUnit(dto.getUnit());
//            item.setQuantity(dto.getQuantity());
//            item.setMaterial(material);
//
//            material.getItems().add(item);
//        }
//
//        // ✅ Recalculate totals
//        double totalAmount = material.getItems().stream()
//                .mapToDouble(i -> i.getPrice() * i.getQuantity())
//                .sum();
//
//        material.setTotalAmount(totalAmount);
//        material.setBalanceAmount(totalAmount - material.getAmountPaid());
//
//        Material saved = materialRepository.save(material);
//
//        return mapToResponse(saved);
//    }
//

//    public MaterialResponse updateMaterial(Long materialId, MaterialUpdateRequest request) {
//        Material material = materialRepository.findById(materialId)
//                .orElseThrow(() -> new RuntimeException("Material not found"));
//
//        material.setDate(request.getDate());
//
//        List<MaterialItem> items = material.getItems();
//
//        for (MaterialItemUpdateRequest dto : Optional.ofNullable(request.getItems()).orElse(Collections.emptyList())) {
//            MaterialItem item = null;
//
//            if (dto.getId() != null) {
//                // ✅ Find existing item by ID
//                item = items.stream()
//                        .filter(i -> i.getId().equals(dto.getId()))
//                        .findFirst()
//                        .orElse(null);
//            }
//
//            if (item != null) {
//                // ✅ Update existing item
//                item.setName(dto.getName());
//                item.setPrice(dto.getPrice());
//                item.setUnit(dto.getUnit());
//                item.setQuantity(dto.getQuantity());
//            }
//        }
//
//        // ✅ Recalculate totals
//        double totalAmount = items.stream()
//                .mapToDouble(i -> i.getPrice() * i.getQuantity())
//                .sum();
//
//        material.setTotalAmount(totalAmount);
//        material.setBalanceAmount(totalAmount - material.getAmountPaid());
//
//        Material saved = materialRepository.save(material);
//        return mapToResponse(saved);
//    }



    @Transactional
    public VendorPaymentResponse addPayment(VendorPaymentRequest request) {
        VendorPayment payment = new VendorPayment();
        payment.setPayDate(request.getPayDate());
        payment.setAmount(request.getAmount());
        payment.setRemark(request.getRemark());
        payment.setPaymentStatus(request.getPaymentStatus());

        Material material = null;

        if (request.getVendorId() != null) {
            Vendor vendor = vendorRepository.findById(request.getVendorId())
                    .orElseThrow(() -> new RuntimeException("Vendor not found"));
            payment.setVendor(vendor);

            material = materialRepository.findTopByVendorIdOrderByDateDesc(vendor.getId())
                    .orElseThrow(() -> new RuntimeException("No material found for vendor"));
        }

        if (request.getProjectId() != null) {
            Project project = projectRepository.findById(request.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project not found"));
            payment.setProject(project);

            material = materialRepository.findTopByProjectIdOrderByDateDesc(project.getId())
                    .orElseThrow(() -> new RuntimeException("No material found for project"));
        }

        if (material != null) {
            payment.setMaterial(material);

            double updatedPaid = material.getAmountPaid() + request.getAmount();
            material.setAmountPaid(updatedPaid);
            material.setBalanceAmount(material.getTotalAmount() - updatedPaid);

            materialRepository.save(material);
        }

        VendorPayment saved = vendorPaymentRepository.save(payment);

        // ✅ Convert entity → DTO using mapper
        return vendorPaymentMapper.toResponse(saved);
    }


    private VendorPaymentResponse mapToResponse(VendorPayment payment) {
        VendorPaymentResponse res = new VendorPaymentResponse();
        res.setId(payment.getId());
        res.setPayDate(payment.getPayDate());
        res.setAmount(payment.getAmount());
        res.setRemark(payment.getRemark());
        res.setPaymentStatus(payment.getPaymentStatus());
        res.setMaterialId(payment.getMaterial().getId());
        res.setVendorId(payment.getVendor().getId());
        res.setProjectId(payment.getProject().getId());
        return res;
    }


    public List<VendorPaymentResponse> getPayments(Long vendorId, Long projectId) {
        List<VendorPayment> payments;

        if (vendorId != null && projectId != null) {
            payments = vendorPaymentRepository.findByVendorIdAndProjectId(vendorId, projectId);
        } else if (vendorId != null) {
            payments = vendorPaymentRepository.findByVendorId(vendorId);
        } else if (projectId != null) {
            payments = vendorPaymentRepository.findByProjectId(projectId);
        } else {
            payments = vendorPaymentRepository.findAll();
        }

        return payments.stream()
                .map(this::mapToResponse)
                .toList();
    }


    public VendorPaymentResponse updatePayment(Long paymentId, VendorPaymentRequest request) {
        VendorPayment payment = vendorPaymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        Material material = payment.getMaterial();

        // Remove old payment amount from Material
        double oldAmount = payment.getAmount();
        material.setAmountPaid(material.getAmountPaid() - oldAmount);
        material.setBalanceAmount(material.getTotalAmount() - material.getAmountPaid());

        // Update payment
        payment.setPayDate(request.getPayDate());
        payment.setAmount(request.getAmount());
        payment.setRemark(request.getRemark());
        payment.setPaymentStatus(request.getPaymentStatus());

//        if (request.getMaterialId() != null && !request.getMaterialId().equals(material.getId())) {
//            Material newMaterial = materialRepository.findById(request.getMaterialId())
//                    .orElseThrow(() -> new RuntimeException("Material not found"));
//            payment.setMaterial(newMaterial);
//            material = newMaterial;
//        }

        if (request.getVendorId() != null) {
            Vendor vendor = vendorRepository.findById(request.getVendorId())
                    .orElseThrow(() -> new RuntimeException("Vendor not found"));
            payment.setVendor(vendor);
        }

        if (request.getProjectId() != null) {
            Project project = projectRepository.findById(request.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project not found"));
            payment.setProject(project);
        }

        // Add new payment amount to Material
        material.setAmountPaid(material.getAmountPaid() + request.getAmount());
        material.setBalanceAmount(material.getTotalAmount() - material.getAmountPaid());

        vendorPaymentRepository.save(payment);
        materialRepository.save(material);

        return mapToResponse(payment);
    }


    public void deletePayment(Long id) {
        VendorPayment payment = vendorPaymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        Material material = payment.getMaterial();

        // Subtract payment from Material totals
        material.setAmountPaid(material.getAmountPaid() - payment.getAmount());
        material.setBalanceAmount(material.getTotalAmount() - material.getAmountPaid());

        materialRepository.save(material);
        vendorPaymentRepository.delete(payment);
    }

    public VendorPaymentResponse getPaymentById(Long id) {
        VendorPayment payment = vendorPaymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id " + id));
        return mapToResponse(payment);
    }

    public void deleteMaterial(Long materialId) {
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new RuntimeException("Material not found"));

        materialRepository.delete(material);
    }

    public void deleteMaterialItem(Long itemId) {
        MaterialItem item = materialItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Material item not found"));

        Material material = item.getMaterial();

        // remove item
        material.getItems().remove(item);
        materialItemRepository.delete(item);

        // recalc totals
        double totalAmount = material.getItems().stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();

        material.setTotalAmount(totalAmount);

        double balance = totalAmount - material.getAmountPaid();
        material.setBalanceAmount(balance);

        materialRepository.save(material);
    }

    public MaterialResponse updateMaterial(Long materialId, Long vendorId, Long projectId, MaterialRequest request) {
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new RuntimeException("Material not found with id: " + materialId));

        if (vendorId == null && projectId == null) {
            throw new RuntimeException("Either vendorId or projectId must be provided");
        }

        if (request.getMaterials() == null || request.getMaterials().isEmpty()) {
            throw new RuntimeException("Material data is required");
        }

        MaterialDto dto = request.getMaterials().get(0);

        // ✅ Update basic fields
        material.setDate(dto.getDate());

        // ✅ Vendor/Project mapping
        if (vendorId != null) {
            Vendor vendor = vendorRepository.findById(vendorId)
                    .orElseThrow(() -> new RuntimeException("Vendor not found with id " + vendorId));
            material.setVendor(vendor);
            material.setProject(null);
        }
        if (projectId != null) {
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new RuntimeException("Project not found with id " + projectId));
            material.setProject(project);
            material.setVendor(null);
        }

        // ✅ Update items using MapStruct
        List<MaterialItem> updatedItems = dto.getItems().stream()
                .map(materialMapper::toEntity)
                .peek(item -> item.setMaterial(material)) // set parent
                .collect(Collectors.toList());

        material.getItems().clear();
        material.getItems().addAll(updatedItems);

        // ✅ Recalculate totals
        recalculateMaterialTotals(material);

        Material updated = materialRepository.save(material);
        return materialMapper.toResponse(updated);
    }

    private void recalculateMaterialTotals(Material material) {
        double totalAmount = material.getItems().stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();
        material.setTotalAmount(totalAmount);
        material.setBalanceAmount(totalAmount - material.getAmountPaid());
    }


    @Transactional
    public Material updateMaterialItemsPartial(MaterialUpdateRequest request) {
        Material material = materialRepository.findById(request.getMaterialId())
                .orElseThrow(() -> new RuntimeException("Material not found"));

        // Map existing items by id for quick lookup
        Map<Long, MaterialItem> existingItemsMap = material.getItems().stream()
                .collect(Collectors.toMap(MaterialItem::getId, Function.identity()));

        for (MaterialItemUpdateRequest dto : request.getItems()) {
            if (dto.getItemId() != null && existingItemsMap.containsKey(dto.getItemId())) {
                // Update only the fields provided in request
                MaterialItem item = existingItemsMap.get(dto.getItemId());
                if (dto.getName() != null) item.setName(dto.getName());
                if (dto.getPrice() != null) item.setPrice(dto.getPrice());
                if (dto.getQuantity() != null) item.setQuantity(dto.getQuantity());
                if (dto.getUnit() != null) item.setUnit(dto.getUnit());
            } else {
                // Add new item if itemId is null
                MaterialItem newItem = new MaterialItem();
                newItem.setMaterial(material);
                newItem.setName(dto.getName());
                newItem.setPrice(dto.getPrice() != null ? dto.getPrice() : 0);
                newItem.setQuantity(dto.getQuantity() != null ? dto.getQuantity() : 0);
                newItem.setUnit(dto.getUnit());
                material.getItems().add(newItem);
            }
        }

        // Recalculate totals without removing any items
        double totalAmount = material.getItems().stream()
                .mapToDouble(i -> (i.getPrice() != null ? i.getPrice() : 0) *
                        (i.getQuantity() != null ? i.getQuantity() : 0))
                .sum();
        material.setTotalAmount(totalAmount);
        material.setBalanceAmount(totalAmount - (material.getAmountPaid() != null ? material.getAmountPaid() : 0));

        return materialRepository.save(material);
    }

}