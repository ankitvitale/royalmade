package com.royalmade.controller;


import com.royalmade.dto.AppUserDTO;
import com.royalmade.dto.AppUserDtos;
import com.royalmade.dto.ExpenceProjectDto;
import com.royalmade.dto.ProjectResponseDto;
import com.royalmade.entity.AppUser;
import com.royalmade.entity.Employee;
import com.royalmade.entity.Project;
import com.royalmade.entity.Supervisor;
import com.royalmade.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AppUserController {

    @Autowired
    private UserService userService;
    @PostMapping("/supervisorRegister")
    public Supervisor supervisorRegister(@RequestBody Supervisor supervisor){
        return userService.supervisorRegister(supervisor);
    }


    @PostMapping("/appUserRegister")
    public AppUserDtos appUserRegister(@RequestBody AppUserDtos appUserDto) {
        return userService.appUserRegister(appUserDto);
    }

    @GetMapping("/all-supervisors")
    @PreAuthorize("hasRole('Admin')")
    public List<AppUserDTO> getAllSupervisors() {
        return userService.getAllSupervisors();
    }

    @GetMapping("/AllSupervisor")
    @PreAuthorize("hasRole('Admin')")
    public List<AppUser> getAllAppUser(){
        return userService.getAllUser();
    }
    @GetMapping("/Supervisor/{id}")
    @PreAuthorize("hasRole('Admin')")
    public String  getAppUserById(@PathVariable Long id){
        return userService.getAllUserById(id);
    }

    @GetMapping("/AllProject")
    @PreAuthorize("hasAnyRole('Admin','AppUser')")
    public ResponseEntity<List<ExpenceProjectDto>> getAllProjects() {
        List<ExpenceProjectDto> projects = userService.getAllProjects();
        if (!projects.isEmpty()) {
            return ResponseEntity.ok(projects);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }


}
