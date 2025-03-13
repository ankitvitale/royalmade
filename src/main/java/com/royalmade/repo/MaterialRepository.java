package com.royalmade.repo;

import com.royalmade.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MaterialRepository extends JpaRepository<Material,Long> {
    List<Material> findByVendorIdAndBillNo(Long vendorId, Double billNo);

    List<Material> findByBillNo(Double billNo);


//    @Query("SELECT DISTINCT m.billNo FROM Material m WHERE m.vendor.id = :vendorId")
//    List<Double> findDistinctBillNosByVendorId(@Param("vendorId") Long vendorId);

    @Query("SELECT m FROM Material m WHERE m.vendor.id = :vendorId")
    List<Material> findByVendorId(@Param("vendorId") Long vendorId);
}
