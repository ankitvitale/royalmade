package com.royalmade.dto;

import com.royalmade.entity.AppUser;
import com.royalmade.entity.enumeration.ProjectStatus;

public class ExpenceProjectDto {
    private Long id;
    private String name;
    private ProjectStatus status;
    private AppUser appUser;

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

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
}
