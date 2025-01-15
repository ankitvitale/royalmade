package com.royalmade.dto;

import com.royalmade.entity.Address;
import com.royalmade.entity.Land;
import com.royalmade.entity.enumeration.ProjectStatus;

public class ProjectResponseDto {

    private Long id;
    private String name;
    private ProjectStatus status;
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

    public LandResponseDto getLand() {
        return land;
    }

    public void setLand(LandResponseDto land) {
        this.land = land;
    }
}
