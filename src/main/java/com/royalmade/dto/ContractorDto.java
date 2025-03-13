package com.royalmade.dto;


import com.royalmade.entity.Project;
import jakarta.persistence.*;

import java.time.LocalDate;

public class ContractorDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String contractorName;
    private String sideName;
    private  String type;
    private LocalDate addedOn;
    private  Double total;
    private  Double contractorPaidAmount;
    private Double  reamingAmount;

    private Long projectId;
    public ContractorDto(){}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContractorName() {
        return contractorName;
    }

    public void setContractorName(String contractorName) {
        this.contractorName = contractorName;
    }

    public String getSideName() {
        return sideName;
    }

    public void setSideName(String sideName) {
        this.sideName = sideName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(LocalDate addedOn) {
        this.addedOn = addedOn;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getContractorPaidAmount() {
        return contractorPaidAmount;
    }

    public void setContractorPaidAmount(Double contractorPaidAmount) {
        this.contractorPaidAmount = contractorPaidAmount;
    }

    public Double getReamingAmount() {
        return reamingAmount;
    }

    public void setReamingAmount(Double reamingAmount) {
        this.reamingAmount = reamingAmount;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
