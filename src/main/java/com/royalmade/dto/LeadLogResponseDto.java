package com.royalmade.dto;

import com.royalmade.entity.enumeration.LeadStatus;

import java.time.LocalDate;

public class LeadLogResponseDto {
    private Long id;
    private LocalDate logDate;
    private String status;

//    public LeadLogResponseDto(Long id, LocalDate logDate, LeadStatus status) {
//        this.id = id;
//        this.logDate = logDate;
//        this.status = status;
//    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getLogDate() {
        return logDate;
    }

    public void setLogDate(LocalDate logDate) {
        this.logDate = logDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
