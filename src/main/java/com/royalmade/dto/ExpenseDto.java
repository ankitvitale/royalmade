package com.royalmade.dto;

import com.royalmade.entity.AppUser;
import com.royalmade.entity.enumeration.ExpenseType;

import java.time.LocalDate;

public class ExpenseDto {
    private Long id;
    private String name;
    private ExpenseType type;
    private Integer quantity;
    private String vendorName;
    private Double price;
    private LocalDate addedOn;
    private AddedByDto addedByDto;
    private ProjectDto project;

    // Add a constructor to handle object creation


    // Add getters and setters
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

    public ExpenseType getType() {
        return type;
    }

    public void setType(ExpenseType type) {
        this.type = type;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(LocalDate addedOn) {
        this.addedOn = addedOn;
    }

    public AddedByDto getAddedByDto() {
        return addedByDto;
    }

    public void setAddedByDto(AddedByDto addedByDto) {
        this.addedByDto = addedByDto;
    }

    public ProjectDto getProject() {
        return project;
    }

    public void setProject(ProjectDto project) {
        this.project = project;
    }
}
