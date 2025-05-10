package com.royalmade.dto;

public class MaterialBillResponseDTO {
    private Double billNo;
    private VendorResponseDTO vendor;
    public MaterialBillResponseDTO(){}
    public MaterialBillResponseDTO(Double billNo, VendorResponseDTO vendor) {
        this.billNo = billNo;
        this.vendor = vendor;
    }

    public Double getBillNo() {
        return billNo;
    }

    public VendorResponseDTO getVendor() {
        return vendor;
    }
}
