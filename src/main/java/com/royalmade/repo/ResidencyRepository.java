package com.royalmade.repo;

import com.royalmade.entity.Residency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ResidencyRepository extends JpaRepository<Residency,Long> {
    Optional<Residency> findByProjectId(Long projectId);

    @Query(value = "SELECT * FROM residency WHERE identifier = :identifier", nativeQuery = true)
    Optional<Residency> findByIdentifier(@Param("identifier") String identifier);

}
