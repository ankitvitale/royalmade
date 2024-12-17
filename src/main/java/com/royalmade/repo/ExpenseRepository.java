package com.royalmade.repo;

import com.royalmade.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByProjectId(Long projectId);

  //  List<Expense> findByVendorName(String vendorName);
//    @Query("SELECT e FROM Expense e WHERE LOWER(e.vendor.name) = LOWER(:vendorName)")
//    List<Expense> findByVendorNameIgnoreCase(@Param("vendorName") String vendorName);

    @Query("SELECT e FROM Expense e WHERE LOWER(e.vendorName) = LOWER(:vendorName)")
    List<Expense> findByVendorNameIgnoreCase(@Param("vendorName") String vendorName);
    //List<Expense> findByVendorName(@Param("vendor_name") String vendorName);


}