package com.royalmade.dto;

import com.royalmade.entity.Land;
import com.royalmade.entity.enumeration.ProjectStatus;

public class ProjectRequestDto {
     private Long id;
    private String name;
    private ProjectStatus status;
   private Long landId; // Reference to Land by ID


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public Long getLandId() {
        return landId;
    }

    public void setLandId(Long landId) {
        this.landId = landId;
    }
}
