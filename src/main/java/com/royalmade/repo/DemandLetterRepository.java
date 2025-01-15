package com.royalmade.repo;

import com.royalmade.entity.DemandLetter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandLetterRepository extends JpaRepository<DemandLetter, Long> {
}
