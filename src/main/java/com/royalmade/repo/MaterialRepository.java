package com.royalmade.repo;

import com.royalmade.dto.MaterialBillResponseDTO;
import com.royalmade.dto.MaterialDTO;
import com.royalmade.dto.SingleMaterialResponseDTO;
import com.royalmade.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MaterialRepository extends JpaRepository<Material,Long> {
    List<Material> findByVendorIdAndBillNo(Long vendorId, Double billNo);

    List<Material> findByBillNo(Double billNo);
    



    @Query("SELECT m FROM Material m WHERE m.vendor.id = :vendorId")
    List<Material> findByVendorId(@Param("vendorId") Long vendorId);

    List<Material> findByVendorIdAndProjectId(Long vendorId, Long projectId);


    @Query("SELECT DISTINCT new com.royalmade.dto.MaterialBillResponseDTO(m.billNo, " +
            "new com.royalmade.dto.VendorResponseDTO(m.vendor.id, m.vendor.name, m.vendor.phoneNo)) " +
            "FROM Material m WHERE m.vendor.id = :vendorId AND m.project.id = :projectId")
    List<MaterialBillResponseDTO> findUniqueBillNosByVendorAndProject(@Param("vendorId") Long vendorId, @Param("projectId") Long projectId);

    @Query("SELECT new com.royalmade.dto.SingleMaterialResponseDTO(m.id, m.name, m.type, m.quantity, m.price, m.addedOn) " +
            "FROM Material m WHERE m.billNo = :billNo AND m.project.id = :projectId AND m.vendor.id = :vendorId")
    List<SingleMaterialResponseDTO> findByBillNoAndProjectIdAndVendorId(@Param("billNo") Double billNo,
                                                                        @Param("projectId") Long projectId,
                                                                        @Param("vendorId") Long vendorId);

    List<Material> findByBillNoAndProjectId(Double aDouble, Long projectId);

    List<Material> findByProjectIdAndVendorIdAndBillNo(Long projectId, Long vendorId, Double billNo);



}
