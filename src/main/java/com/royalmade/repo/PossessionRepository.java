package com.royalmade.repo;

import com.royalmade.entity.PossessionLetter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PossessionRepository extends JpaRepository< PossessionLetter,Long> {
}
