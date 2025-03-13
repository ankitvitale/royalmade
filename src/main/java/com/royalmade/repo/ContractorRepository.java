package com.royalmade.repo;

import com.royalmade.entity.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractorRepository extends JpaRepository<Contractor,Long> {
    List<Contractor> findByProjectId(Long projectId);
}
