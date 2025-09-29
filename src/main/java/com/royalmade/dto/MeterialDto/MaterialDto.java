package com.royalmade.dto.MeterialDto;



import java.time.LocalDate;
import java.util.List;

public class MaterialDto {
    private Long MaterialId;
    private LocalDate date;
    private Double totalAmount;
    private List<MaterialItemDto> items;

    // getters and setters


    public Long getMaterialId() {
        return MaterialId;
    }

    public void setMaterialId(Long materialId) {
        MaterialId = materialId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<MaterialItemDto> getItems() {
        return items;
    }

    public void setItems(List<MaterialItemDto> items) {
        this.items = items;
    }
}