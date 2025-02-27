package com.royalmade.service;

import com.royalmade.dto.*;
import com.royalmade.entity.*;
import com.royalmade.entity.enumeration.UserType;
import com.royalmade.exception.ResourceNotFoundException;
import com.royalmade.mapper.ProjectMapper;
import com.royalmade.repo.AppUserRepository;
import com.royalmade.repo.LandRepository;
import com.royalmade.repo.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private LandRepository landRepository;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private AppUserRepository appUserRepository;


    public Project createProject(ProjectRequestDto projectRequestDto) {
        // Create a new Project entity
        Project project = projectMapper.toProject( projectRequestDto);

        // Fetch the existing Land entity by ID
        Land land = landRepository.findById(projectRequestDto.getLandId())
                .orElseThrow(() -> new EntityNotFoundException("Land not found with ID: " + projectRequestDto.getLandId()));

        project.setLand(land); // Link the existing Land to the Project

        // Save the Project
        return projectRepository.save(project);
    }

//    public List<ProjectResponseDto> getAllProjects() {
//        List<Project> projects = projectRepository.findAll();
//        // Map entities to DTOs
//        return projects.stream()
//                .map(projectMapper::toProjectResponseDto)
//                .collect(Collectors.toList());
//    }


    public List<ProjectResponseDto> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream()
                .map(projectMapper::toProjectResponseDto)
                .collect(Collectors.toList());
    }

    public ProjectResponseDto getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElse(null); // You can throw an exception here if you prefer
        if (project == null) {
            return null; // Or you could throw a custom exception if the project is not found
        }
        // Map entity to DTO
        return projectMapper.toProjectResponseDto(project);
    }


    public Project updateProject(Long id, ProjectRequestDto projectRequestDto) {
        // Retrieve the existing project by ID
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + id));

        // Map the fields from the DTO to the Project entity
        project.setName(projectRequestDto.getName());
        project.setStatus(projectRequestDto.getStatus());
        // Save the updated project back to the repository
        Project updatedProject = projectRepository.save(project);

        return updatedProject;
    }


    public void deleteProject(Long id) {
        // Check if the project exists
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with ID: " + id));
        // Delete related entries from residency
        projectRepository.deleteResidenciesByProjectId(id);

        // Delete related entries from expense_installment
        projectRepository.deleteExpenseInstallmentsByProjectId(id);
        projectRepository.deleteExpensesByProjectId(id);
        // Delete related entries in the app_user_allowed_site table
        projectRepository.deleteAppUserAllowedSiteByProjectId(id);

        // Delete the project itself
        projectRepository.deleteProjectById(id);
    }

    public Project allowedSiteSupervisor(Long userId, Long projectId) {
        // Fetch the supervisor (AppUser) details
        AppUser supervisor = appUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Supervisor with ID " + userId + " not found"));

        // Validate the supervisor's role
        if (!supervisor.getUserType().equals(UserType.AppUser)) {
            throw new IllegalArgumentException("User with ID " + userId + " is not a valid site supervisor");
        }

        // Fetch the project
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project with ID " + projectId + " not found"));

        // Associate the supervisor with the project
        project.setAppUser(supervisor); // Assuming `setAppUser` links the supervisor to the project

        // Optionally, add the project to the supervisor's list of allowed sites
        supervisor.getAllowedSite().add(project);
        appUserRepository.save(supervisor);

        // Save and return the updated project
        return projectRepository.save(project);
    }


    public Project releaseSiteSupervisor(Long userId, Long projectId) {
        // Fetch the supervisor (AppUser) details
        AppUser supervisor = appUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Supervisor with ID " + userId + " not found"));

        // Fetch the project
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project with ID " + projectId + " not found"));

        // Check if the supervisor is currently assigned to the project
        if (!project.getAppUser().equals(supervisor)) {
            throw new IllegalArgumentException("Supervisor with ID " + userId + " is not assigned to this project");
        }

        // Remove the supervisor from the project
        project.setAppUser(null); // Assuming `setAppUser` links the supervisor to the project, we set it to null

        // Optionally, remove the project from the supervisor's list of allowed sites
        supervisor.getAllowedSite().remove(project);
        appUserRepository.save(supervisor);

        // Save and return the updated project
        return projectRepository.save(project);
    }



}
