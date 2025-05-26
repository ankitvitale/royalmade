package com.royalmade.dto;

import com.royalmade.entity.enumeration.AvailabilityStatus;
import com.royalmade.entity.enumeration.FlatType;
import com.royalmade.entity.enumeration.ResidencyType;
import jakarta.persistence.Column;

public class ResidencyDto {
    private Long id;
    private String name;
    private ResidencyType residencyType;
    private FlatType flatType;

    private AvailabilityStatus availabilityStatus;
    private String floorNumber;

    private String identifier;
    private Double price;

    private String area;

    private String facing;
    private Long projectId; // Link project via ID, not entire object


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

    public ResidencyType getResidencyType() {
        return residencyType;
    }

    public void setResidencyType(ResidencyType residencyType) {
        this.residencyType = residencyType;
    }

    public FlatType getFlatType() {
        return flatType;
    }

    public void setFlatType(FlatType flatType) {
        this.flatType = flatType;
    }

    public AvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public String getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(String floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getFacing() {
        return facing;
    }

    public void setFacing(String facing) {
        this.facing = facing;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
