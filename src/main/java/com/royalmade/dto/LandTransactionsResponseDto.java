package com.royalmade.dto;

import java.util.List;

public class LandTransactionsResponseDto {
    private List<PartnerWithTransactionsDto> partners;
    private PurchaserWithTransactionsDto purchaser;

    public List<PartnerWithTransactionsDto> getPartners() {
        return partners;
    }

    public void setPartners(List<PartnerWithTransactionsDto> partners) {
        this.partners = partners;
    }

    public PurchaserWithTransactionsDto getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(PurchaserWithTransactionsDto purchaser) {
        this.purchaser = purchaser;
    }
}
