package com.royalmade.dto;

public class VendorResponseDTO {
    private Long id;
    private String name;
    private String phoneNo;

    public VendorResponseDTO(Long id, String name, String phoneNo) {
        this.id = id;
        this.name = name;
        this.phoneNo = phoneNo;
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
}
