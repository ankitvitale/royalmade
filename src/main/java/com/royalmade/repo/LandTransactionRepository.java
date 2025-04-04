package com.royalmade.repo;

import com.royalmade.entity.LandTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LandTransactionRepository extends JpaRepository<LandTransaction,Long> {
    List<LandTransaction> findAllByPartnerLandId(Long landId);

    List<LandTransaction> findAllByPerson_Id(Long id);

    List<LandTransaction> findAllByPartnerId(Long partnerId);
}
