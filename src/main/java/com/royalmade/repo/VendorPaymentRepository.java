package com.royalmade.repo;

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
}
