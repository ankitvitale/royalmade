package com.royalmade.dto;

import com.royalmade.entity.Land;
import com.royalmade.entity.enumeration.ProjectStatus;
import jakarta.persistence.Column;

public class ProjectRequestDto {
     private Long id;
    private String name;


    private String totalflat;

    private String buildingSize;

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


    public String getTotalflat() {
        return totalflat;
    }

    public void setTotalflat(String totalflat) {
        this.totalflat = totalflat;
    }

    public String getBuildingSize() {
        return buildingSize;
    }

    public void setBuildingSize(String buildingSize) {
        this.buildingSize = buildingSize;
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
