package com.royalmade.repo;

import com.royalmade.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser ,Long> {
    AppUser findByEmail(String email);
}
