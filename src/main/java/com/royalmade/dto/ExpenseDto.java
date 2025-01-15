package com.royalmade.dto;

import com.royalmade.entity.AppUser;
import com.royalmade.entity.enumeration.ExpenseType;
import jakarta.persistence.Column;

import java.time.LocalDate;

public class ExpenseDto {
    private Long id;
    private String name;
    private ExpenseType type;
    private Integer quantity;
    private String vendorName;
    private Double price;
    private Double totalprice;
    private Double vendorAmountPaid;
    private Double reamingAmount;
    private LocalDate addedOn;
    private String billImg;
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

    public Double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Double totalprice) {
        this.totalprice = totalprice;
    }

    public Double getVendorAmountPaid() {
        return vendorAmountPaid;
    }

    public void setVendorAmountPaid(Double vendorAmountPaid) {
        this.vendorAmountPaid = vendorAmountPaid;
    }

    public Double getReamingAmount() {
        return reamingAmount;
    }

    public void setReamingAmount(Double reamingAmount) {
        this.reamingAmount = reamingAmount;
    }

    public LocalDate getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(LocalDate addedOn) {
        this.addedOn = addedOn;
    }

    public String getBillImg() {
        return billImg;
    }

    public void setBillImg(String billImg) {
        this.billImg = billImg;
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
