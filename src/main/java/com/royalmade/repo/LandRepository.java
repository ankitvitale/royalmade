package com.royalmade.repo;

import com.royalmade.entity.Land;
import com.royalmade.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LandRepository extends JpaRepository<Land ,Long> {
//    @Query("SELECT l.partners FROM Land l WHERE l.id = :id")
//    List<Partner> findPartnersByLandId(@Param("id") Long id);

 //   List<Partner> findPartnersWithTransactionsByLandId(Long landId);

    @Query("SELECT DISTINCT p FROM Land l " +
            "JOIN l.partners p " +
            "LEFT JOIN FETCH p.landTransactions tx " +
            "WHERE l.id = :landId")
    List<Partner> findPartnersWithTransactionsByLandId(@Param("landId") Long landId);


//    @Query("SELECT p FROM Partner p LEFT JOIN FETCH p.landTransactions WHERE p.id = :id")
//    List<Partner> findPartnersByLandId(@Param("id") Long id);

}
