package com.royalmade.repo;

import com.royalmade.dto.PaymentDTO;
import com.royalmade.entity.Project;
import com.royalmade.entity.Vendor;
import com.royalmade.entity.VendorPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorPaymentRepository extends JpaRepository<VendorPayment, Long> {


    List<VendorPayment> findByMaterial_IdIn(List<Long> collect);

    List<VendorPayment> findByMaterial_BillNo(Double billNo);

    List<VendorPayment> findByBillNo(Double billNo);

    @Query("SELECT v FROM VendorPayment v WHERE v.vendor.id = :vendorId AND v.billNo = :billNo")
    List<VendorPayment> findByVendorIdAndBillNo(@Param("vendorId") Long vendorId, @Param("billNo") String billNo);

    List<VendorPayment> findByVendorAndMaterial_BillNo(Vendor vendor, Double billNo);

  //  List<VendorPayment> findByVendorIdAndProjectIdAndBillNo(Long vendorId, Long projectId, String billNo);
    List<VendorPayment> findByVendorIdAndProjectIdAndBillNo(Long vendorId, Long projectId, Double billNo);

    @Query("SELECT new com.royalmade.dto.PaymentDTO(p.id, p.payDate, p.amount, p.remark, p.paymentStatus) " +
            "FROM VendorPayment p WHERE p.material.id = :materialId")
    List<PaymentDTO> findPaymentsByMaterialId(@Param("materialId") Long materialId);

    List<VendorPayment> findByBillNoAndProjectId(Double aDouble, Long projectId);

    List<VendorPayment> findByVendorAndProject(Vendor vendor, Project project);


    // List<VendorPayment> findByBillNo(String billNo);
}
