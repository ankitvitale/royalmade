package com.royalmade.controller;


import com.royalmade.entity.Employee;
import com.royalmade.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    private UserService userService;



    @PostMapping("/employeeregister")
    public Employee employeeregister(@RequestBody Employee employee){
        return userService.employeeregister(employee);
    }

    @GetMapping("/allEmployee")
    @PreAuthorize("hasRole('Admin')")
    public List<Employee> getAllEmployees() {
        return userService.getAllEmployees();
    }
    @DeleteMapping("/employeeDelete/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Map<String, String>> deleteEmployee(@PathVariable Long id) {
        try {
            boolean isUpdated = userService.deleteEmployee(id);
            if (isUpdated) {
                return ResponseEntity.ok(Map.of("message", "Employee marked as deleted successfully."));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "Employee not found or already deleted."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An error occurred while deleting the employee."));
        }
    }


}
