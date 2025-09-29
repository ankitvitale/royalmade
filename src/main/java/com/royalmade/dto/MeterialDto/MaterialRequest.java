package com.royalmade.dto.MeterialDto;

import java.util.ArrayList;
import java.util.List;

public class MaterialRequest {

     private  Long id;

    private List<MaterialDto> materials = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Double getBillAmount() {
//        return billAmount;
//    }
//
//    public void setBillAmount(Double billAmount) {
//        this.billAmount = billAmount;
//    }
//
//    public Double getPaidAmount() {
//        return paidAmount;
//    }
//
//    public void setPaidAmount(Double paidAmount) {
//        this.paidAmount = paidAmount;
//    }
//
//    public Double getRemainingAmount() {
//        return remainingAmount;
//    }
//
//    public void setRemainingAmount(Double remainingAmount) {
//        this.remainingAmount = remainingAmount;
//    }

    public List<MaterialDto> getMaterials() {
        return materials;
    }
    public void setMaterials(List<MaterialDto> materials) {
        this.materials = materials;
    }
}
