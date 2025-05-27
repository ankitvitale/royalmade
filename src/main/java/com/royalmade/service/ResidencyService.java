package com.royalmade.service;

import com.royalmade.dto.ProjectDto;
import com.royalmade.dto.ResidencyDto;
import com.royalmade.entity.Project;
import com.royalmade.entity.Residency;
import com.royalmade.entity.enumeration.AvailabilityStatus;
import com.royalmade.exception.ResourceNotFoundException;
import com.royalmade.mapper.ResidencyMapper;
import com.royalmade.repo.ProjectRepository;
import com.royalmade.repo.ResidencyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
        residency.setFacing(residencyDto.getFacing());
        residency.setArea(residencyDto.getArea());
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
        existingResidency.setFacing(residencyDto.getFacing());
        existingResidency.setArea(residencyDto.getArea());

        // Update project association if projectId is provided
        if (residencyDto.getProjectId() != null) {
            Project project = projectRepository.findById(residencyDto.getProjectId())
                    .orElseThrow(() -> new EntityNotFoundException("Project not found with ID: " + residencyDto.getProjectId()));
            existingResidency.setProject(project);
        }

        // Save and return the updated residency
        return residencyRepository.save(existingResidency);
    }

    public List<ResidencyDto> getResidenciesByProjectId(Long projectId) {
        List<Residency> residencies = residencyRepository.findAllByProjectId(projectId);


        // Map Residency entities to DTOs
        return residencies.stream().map(residency -> {
            ResidencyDto dto = new ResidencyDto();
            dto.setId(residency.getId());
            dto.setName(residency.getName());
            dto.setResidencyType(residency.getResidencyType());
            dto.setFlatType(residency.getFlatType());
            dto.setAvailabilityStatus(residency.getAvailabilityStatus());
            dto.setFloorNumber(residency.getFloorNumber());
            dto.setIdentifier(residency.getIdentifier());
            dto.setPrice(residency.getPrice());
            dto.setArea(residency.getArea());
            dto.setFacing(residency.getFacing());
            dto.setProjectId(residency.getProject().getId());
            return dto;
        }).toList();
    }




    public Map<Long, Map<String, Long>> countResidenciesByProjectAndStatus() {
        List<Object[]> results = residencyRepository.countByProjectIdAndAvailabilityStatus();

        // Map to store the results by projectId and availability status
        Map<Long, Map<String, Long>> projectStatusCountMap = new HashMap<>();

        // Iterate over the query results and populate the map
        for (Object[] result : results) {
            Long projectId = (Long) result[0];
            AvailabilityStatus availabilityStatus = (AvailabilityStatus) result[1];
            Long count = (Long) result[2];

            // Get or create the map for the given projectId
            projectStatusCountMap
                    .computeIfAbsent(projectId, k -> new HashMap<>())
                    .put(availabilityStatus.name(), count);
        }

        return projectStatusCountMap;
    }

    public Residency deleteResidency(Long id) {
        Residency residency=residencyRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Residency ID Not Found"));
        residencyRepository.deleteById(id);
        return residency;
    }

    public List<Residency> getResidenciesByProject(Long projectId) {
//
        return residencyRepository.findByProjectIdAndAvailabilityStatus(projectId, AvailabilityStatus.BOOKED);

    }

}
