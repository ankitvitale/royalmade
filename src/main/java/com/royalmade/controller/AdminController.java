package com.royalmade.controller;



import com.royalmade.entity.Admin;
import com.royalmade.entity.JwtRequest;
import com.royalmade.entity.JwtResponse;
import com.royalmade.entity.Project;
import com.royalmade.service.JwtService;
import com.royalmade.service.ProjectService;
import com.royalmade.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AdminController {

    @Autowired
   private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ProjectService projectService;


    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }
    @PostMapping({"/registerAdmin"})
    public Admin registerNewAdmin(@RequestBody Admin admin) {
        return userService.registerAdmin(admin);
    }

    //Login
    @PostMapping("/auth/login")
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {

        return jwtService.createJwtToken(jwtRequest);
    }

    @PutMapping("/allowedSiteSupervisor/{userId}/{projectId}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Project> allowedSiteSupervisor(@PathVariable Long userId, @PathVariable Long projectId) {
        Project allowedProject = projectService.allowedSiteSupervisor(userId, projectId);
        return ResponseEntity.ok(allowedProject);
    }


    @PutMapping("/releaseSiteSupervisor/{userId}/{projectId}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Project> releaseSiteSupervisor(@PathVariable Long userId, @PathVariable Long projectId) {
        Project updatedProject = projectService.releaseSiteSupervisor(userId, projectId);
        return ResponseEntity.ok(updatedProject);
    }


}