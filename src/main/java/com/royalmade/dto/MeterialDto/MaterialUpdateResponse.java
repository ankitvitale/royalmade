package com.royalmade.dto.MeterialDto;

import java.util.List;

public class MaterialUpdateResponse {
    private Long materialId;
    private Double totalAmount;
    private Double amountPaid;
    private Double balanceAmount;
    private List<MaterialItemUpdateResponse> items;

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(Double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public List<MaterialItemUpdateResponse> getItems() {
        return items;
    }

    public void setItems(List<MaterialItemUpdateResponse> items) {
        this.items = items;
    }
}
