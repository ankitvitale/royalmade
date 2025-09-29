package com.royalmade.repo;

import com.royalmade.entity.AppUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AppUserRepository extends JpaRepository<AppUser ,Long> {
    AppUser findByEmail(String email);
    AppUser findByUsername(String username);
//    @Modifying
//    @Transactional
//    @Query(value = "DELETE FROM app_user_allowed_site WHERE allowed_site_id = :projectId", nativeQuery = true)
//    void deleteAppUserAllowedSiteByProjectId(@Param("projectId") Long projectId);


}
