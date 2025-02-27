package com.royalmade.repo;

import com.royalmade.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartnerRepository extends JpaRepository<Partner ,Long> {
    List<Partner> findAllByLandId(Long landId);
}
