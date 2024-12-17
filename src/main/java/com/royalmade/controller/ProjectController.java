package com.royalmade.controller;

import com.royalmade.dto.ProjectRequestDto;
import com.royalmade.dto.ProjectResponseDto;
import com.royalmade.entity.Project;
import com.royalmade.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @PostMapping("/createProject")
    public ResponseEntity<Project> createLand(@RequestBody ProjectRequestDto projectRequestDto) {
        Project createdProject = projectService.createProject(projectRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
    }


    @GetMapping("/getAllProjects")
    public ResponseEntity<List<ProjectResponseDto>> getAllProjects() {
        List<ProjectResponseDto> projects = projectService.getAllProjects();
        if (!projects.isEmpty()) {
            return ResponseEntity.ok(projects);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping("/getProjectById/{id}")
    public ResponseEntity<ProjectResponseDto> getProjectById(@PathVariable Long id) {
        ProjectResponseDto project = projectService.getProjectById(id);
        if (project != null) {
            return ResponseEntity.ok(project);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PutMapping("/updateProject/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody ProjectRequestDto projectRequestDto) {

        Project updatedProject = projectService.updateProject(id, projectRequestDto);
        return ResponseEntity.ok(updatedProject);  // Return updated project as response
    }


}