package com.royalmade.dto;

public class VendorResponseDTO {
    private Long id;
    private String name;
    private String phoneNo;
    private Long projectId;
    public VendorResponseDTO() {}


    public VendorResponseDTO(Long id, String name, String phoneNo) {
        this.id = id;
        this.name = name;
        this.phoneNo = phoneNo;
    }

    public VendorResponseDTO(Long id, String name, String phoneNo, Long projectId) {
        this.id = id;
        this.name = name;
        this.phoneNo = phoneNo;
        this.projectId = projectId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
