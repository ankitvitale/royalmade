package com.royalmade.repo;

import com.royalmade.dto.ResidencyDto;
import com.royalmade.entity.Residency;
import com.royalmade.entity.enumeration.AvailabilityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResidencyRepository extends JpaRepository<Residency,Long> {
    Optional<Residency> findByProjectId(Long projectId);

    @Query(value = "SELECT * FROM residency WHERE identifier = :identifier", nativeQuery = true)
    Optional<Residency> findByIdentifier(@Param("identifier") String identifier);


//   @Query("SELECT r FROM Residency r WHERE r.project.id = :projectId")
//   List<Residency> findAllByProjectId(@Param("projectId") Long projectId);

    List<Residency> findAllByProjectId(Long projectId);


    @Query("SELECT r.project.id, r.availabilityStatus, COUNT(r) FROM Residency r GROUP BY r.project.id, r.availabilityStatus")
    List<Object[]> countByProjectIdAndAvailabilityStatus();

//    List<Residency> findByprojectId(Long projectId);

    List<Residency> findByProjectIdAndAvailabilityStatus(Long projectId, AvailabilityStatus availabilityStatus);


}
