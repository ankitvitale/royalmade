package com.royalmade.repo;


import com.royalmade.entity.LoanEnquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanEnquiryRepository extends JpaRepository<LoanEnquiry, Long> {
}
