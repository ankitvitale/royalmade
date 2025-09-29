package com.royalmade.dto.MeterialDto;

import java.time.LocalDate;
import java.util.List;

public class MaterialSummaryResponse {
    private Long itemId;
    private LocalDate date;
    private Double total;
    private List<MaterialItemResponse> materials;

    // getters & setters


    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<MaterialItemResponse> getMaterials() {
        return materials;
    }

    public void setMaterials(List<MaterialItemResponse> materials) {
        this.materials = materials;
    }
}
