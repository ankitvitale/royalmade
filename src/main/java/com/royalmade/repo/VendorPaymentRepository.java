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
    List<VendorPayment> findByVendorIdAndProjectId(Long vendorId, Long projectId);

    List<VendorPayment> findByVendorId(Long vendorId);

    List<VendorPayment> findByProjectId(Long projectId);
}
