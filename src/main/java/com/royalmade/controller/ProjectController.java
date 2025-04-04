package com.royalmade.controller;

import com.royalmade.dto.ProjectRequestDto;
import com.royalmade.dto.ProjectResponseDto;
import com.royalmade.entity.Project;
import com.royalmade.service.ProjectService;
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
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @Autowired
    private UserService userService;

    @PostMapping("/createProject")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Project> createLand(@RequestBody ProjectRequestDto projectRequestDto) {
        Project createdProject = projectService.createProject(projectRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
    }


//    @GetMapping("/getAllProjects")
//    @PreAuthorize("hasRole('Admin')")
//    public ResponseEntity<List<ProjectResponseDto>> getAllProjects() {
//        List<ProjectResponseDto> projects = projectService.getAllProjects();
//        if (!projects.isEmpty()) {
//            return ResponseEntity.ok(projects);
//        } else {
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//        }
//    }

    @GetMapping("/getAllProjects")
    @PreAuthorize("hasAnyRole('Admin','AppUser')")
    public ResponseEntity<List<?>> getProjects(Authentication authentication) {
        String email = authentication.getName();
        List<?> projects;

        // Check if the logged-in user is an Admin
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_Admin"));

        if (isAdmin) {
            // Admin gets all projects
            projects = projectService.getAllProjects();
        } else {
            // AppUser gets only allowed sites
            projects = userService.getAllowedSitesForUser(email);
        }

        if (!projects.isEmpty()) {
            return ResponseEntity.ok(projects);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    // new Api forgetAll project show
    @GetMapping("/getAllProjectShow")
    @PreAuthorize("hasAnyRole('Admin','AppUser')")
    public ResponseEntity<List<?>> getProjectShow(Authentication authentication) {
        String email = authentication.getName();
        List<?> projects;

        // Check if the logged-in user is an Admin
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_Admin"));

        if (isAdmin) {
            // Admin gets all projects
            projects = projectService.getAllProjectShow();
        } else {
            // AppUser gets only allowed sites
            projects = userService.getAllowedSitesForUserShow(email);
        }

        if (!projects.isEmpty()) {
            return ResponseEntity.ok(projects);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }



    @GetMapping("/getProjectById/{id}")
    @PreAuthorize("hasAnyRole('Admin','AppUser')")
    public ResponseEntity<ProjectResponseDto> getProjectById(@PathVariable Long id) {
        ProjectResponseDto project = projectService.getProjectById(id);
        if (project != null) {
            return ResponseEntity.ok(project);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PutMapping("/updateProject/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody ProjectRequestDto projectRequestDto) {

        Project updatedProject = projectService.updateProject(id, projectRequestDto);
        return ResponseEntity.ok(updatedProject);  // Return updated project as response
    }

    @DeleteMapping("/deleteProject/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }


}