package com.royalmade.repo;

import com.royalmade.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    Employee findByEmail(String email);
}
