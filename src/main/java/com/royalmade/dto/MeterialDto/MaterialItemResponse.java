package com.royalmade.dto.MeterialDto;

public class MaterialItemResponse {
    private Long id;

    private String name;
    private Double price;
    private String unit;
    private Integer quantity;
    private Double totaliteamPrice;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotaliteamPrice() {
        return totaliteamPrice;
    }

    public void setTotaliteamPrice(Double totaliteamPrice) {
        this.totaliteamPrice = totaliteamPrice;
    }
}