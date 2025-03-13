package com.royalmade.dto;

import com.royalmade.entity.enumeration.ExpenseType;
import java.time.LocalDate;

public class ExpenseRequest {
    private String name;
    private ExpenseType type;
    private Integer quantity;
    private String vendorName;
    private Double price;
    private Double vendorAmountPaid;
    private LocalDate addedOn;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ExpenseType getType() { return type; }
    public void setType(ExpenseType type) { this.type = type; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getVendorName() { return vendorName; }
    public void setVendorName(String vendorName) { this.vendorName = vendorName; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Double getVendorAmountPaid() { return vendorAmountPaid; }
    public void setVendorAmountPaid(Double vendorAmountPaid) { this.vendorAmountPaid = vendorAmountPaid; }

    public LocalDate getAddedOn() { return addedOn; }
    public void setAddedOn(LocalDate addedOn) { this.addedOn = addedOn; }
}
