package com.royalmade.repo;

import com.royalmade.entity.ContractorInstallment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface ContractorInstallmentRepository extends JpaRepository<ContractorInstallment,Long> {
    List<ContractorInstallment> findByContractorId(Long id);
}
