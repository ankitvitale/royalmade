package com.royalmade.dto;

import com.royalmade.entity.Address;
import com.royalmade.entity.Land;
import com.royalmade.entity.enumeration.ProjectStatus;

public class ProjectResponseDto {

    private Long id;
    private String name;
    private ProjectStatus status;

    private String totalflat;

    private String buildingSize;

    private LandResponseDto land;


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

    public LandResponseDto getLand() {
        return land;
    }

    public void setLand(LandResponseDto land) {
        this.land = land;
    }
}
