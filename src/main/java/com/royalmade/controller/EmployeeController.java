package com.royalmade.controller;


import com.royalmade.entity.Employee;
import com.royalmade.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @Autowired
    private UserService userService;



    @PostMapping("/employeeregister")
    public Employee employeeregister(@RequestBody Employee employee){
        return userService.employeeregister(employee);
    }



}
