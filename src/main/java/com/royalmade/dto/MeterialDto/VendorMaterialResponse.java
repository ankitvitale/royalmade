package com.royalmade.dto.MeterialDto;

import java.util.List;

public class VendorMaterialResponse {
    private Double amountPaid;
    private List<MaterialItemDto> items;

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public List<MaterialItemDto> getItems() {
        return items;
    }

    public void setItems(List<MaterialItemDto> items) {
        this.items = items;
    }
}