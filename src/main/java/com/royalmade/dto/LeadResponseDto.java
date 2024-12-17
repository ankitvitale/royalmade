package com.royalmade.dto;

import com.royalmade.entity.enumeration.LeadStatus;

import java.time.LocalDate;
import java.util.List;

public class LeadResponseDto {
    private Long id;
    private String name;
    private String jobTitle;
    private String companyName;
    private String email;
    private String phoneNumber;
    private LocalDate foundOn;
    private String status;
    private List<LeadLogResponseDto> leadLogs;

//    public LeadResponseDto(Long id, String name, String jobTitle, String companyName, String email,
//                           String phoneNumber, LocalDate foundOn, LeadStatus status,
//                           List<LeadLogResponseDto> leadLogs) {
//        this.id = id;
//        this.name = name;
//        this.jobTitle = jobTitle;
//        this.companyName = companyName;
//        this.email = email;
//        this.phoneNumber = phoneNumber;
//        this.foundOn = foundOn;
//        this.status = status;
//        this.leadLogs = leadLogs;
//    }

    // Getters and Setters

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

    public List<LeadLogResponseDto> getLeadLogs() {
        return leadLogs;
    }

    public void setLeadLogs(List<LeadLogResponseDto> leadLogs) {
        this.leadLogs = leadLogs;
    }
}
