package com.royalmade.dto;

import java.time.LocalDate;

public class LeadRequestDto {
    private String name;
    private String jobTitle;
    private String companyName;
    private String email;
    private String phoneNumber;
    private LocalDate foundOn;
    private String status;

    private LeadLogResponseDto leadLogResponseDto;

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getFoundOn() {
        return foundOn;
    }

    public void setFoundOn(LocalDate foundOn) {
        this.foundOn = foundOn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LeadLogResponseDto getLeadLogResponseDto() {
        return leadLogResponseDto;
    }

    public void setLeadLogResponseDto(LeadLogResponseDto leadLogResponseDto) {
        this.leadLogResponseDto = leadLogResponseDto;
    }
}
