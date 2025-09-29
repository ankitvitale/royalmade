package com.royalmade.dto.MeterialDto;

import java.time.LocalDate;
import java.util.List;

public class MaterialUpdateRequest {
    private Long materialId;

    private LocalDate date;
    private List<MaterialItemUpdateRequest> items;

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<MaterialItemUpdateRequest> getItems() {
        return items;
    }

    public void setItems(List<MaterialItemUpdateRequest> items) {
        this.items = items;
    }
}
