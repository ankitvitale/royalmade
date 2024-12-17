package com.royalmade.repo;

import com.royalmade.entity.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupervisorRepository extends JpaRepository<Supervisor ,Long> {
    Supervisor findByEmail(String email);
}
