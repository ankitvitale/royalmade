package com.royalmade.service;

import com.royalmade.dto.ResidencyDto;
import com.royalmade.entity.Land;
import com.royalmade.entity.Project;
import com.royalmade.entity.Residency;
import com.royalmade.mapper.ResidencyMapper;
import com.royalmade.repo.ProjectRepository;
import com.royalmade.repo.ResidencyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResidencyService {

    @Autowired
    private ResidencyMapper residencyMapper;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ResidencyRepository residencyRepository;


    public Residency createResidency(ResidencyDto residencyDto) {
        // Manually map fields from ResidencyDto to Residency
        Residency residency = new Residency();
        residency.setId(residencyDto.getId());
        residency.setName(residencyDto.getName());
        residency.setResidencyType(residencyDto.getResidencyType());
        residency.setFlatType(residencyDto.getFlatType());
        residency.setAvailabilityStatus(residencyDto.getAvailabilityStatus());
        residency.setFloorNumber(residencyDto.getFloorNumber());
        residency.setIdentifier(residencyDto.getIdentifier());
        residency.setPrice(residencyDto.getPrice());

        // Fetch the Project entity using projectId
        Project project = projectRepository.findById(residencyDto.getProjectId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Project not found with ID: " + residencyDto.getProjectId()));

        // Set the project in the residency entity
        residency.setProject(project);

        // Save and return the residency entity
        return residencyRepository.save(residency);
    }

    // Fetch all residencies
    public List<Residency> getAllResidencies() {
        return residencyRepository.findAll();
    }

    // Fetch a residency by ID
    public Residency getResidencyById(Long id) {
        return residencyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Residency not found with ID: " + id));
    }
    // Update an existing residency
    public Residency updateResidency(Long id, ResidencyDto residencyDto) {
        // Fetch the existing residency entity
        Residency existingResidency = residencyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Residency not found with ID: " + id));

        // Update residency fields
        existingResidency.setName(residencyDto.getName());
        existingResidency.setResidencyType(residencyDto.getResidencyType());
        existingResidency.setFlatType(residencyDto.getFlatType());
        existingResidency.setAvailabilityStatus(residencyDto.getAvailabilityStatus());
        existingResidency.setFloorNumber(residencyDto.getFloorNumber());
        existingResidency.setIdentifier(residencyDto.getIdentifier());
        existingResidency.setPrice(residencyDto.getPrice());

        // Update project association if projectId is provided
        if (residencyDto.getProjectId() != null) {
            Project project = projectRepository.findById(residencyDto.getProjectId())
                    .orElseThrow(() -> new EntityNotFoundException("Project not found with ID: " + residencyDto.getProjectId()));
            existingResidency.setProject(project);
        }

        // Save and return the updated residency
        return residencyRepository.save(existingResidency);
    }
}
