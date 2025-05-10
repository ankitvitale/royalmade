package com.royalmade.repo;


import com.royalmade.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
    @Query("SELECT v FROM Vendor v WHERE v.project.id = :projectId")
    Vendor getVendorByProjectId(@Param("projectId") Long projectId); // ✅ Correct

    List<Vendor> findByProjectId(Long projectId);
}