package com.royalmade.dto;

import com.royalmade.entity.Address;
import com.royalmade.entity.Partner;

import java.util.Set;

public class LandDto {
    private Long id;
    private Double area;
    private Double tokenAmount;
    private Double agreementAmount;
    private Double totalAmount;
    private Double soldAmount;
    private AddressResponseDto address;
    private PurchaserDto purchaser; // Replaced "person" with "purchaser"
    private OwnerDto owner;
    private Set<PartnerDto> partners;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getArea() { return area; }
    public void setArea(Double area) { this.area = area; }

    public Double getTokenAmount() { return tokenAmount; }
    public void setTokenAmount(Double tokenAmount) { this.tokenAmount = tokenAmount; }

    public Double getAgreementAmount() { return agreementAmount; }
    public void setAgreementAmount(Double agreementAmount) { this.agreementAmount = agreementAmount; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public Double getSoldAmount() { return soldAmount; }
    public void setSoldAmount(Double soldAmount) { this.soldAmount = soldAmount; }

    public AddressResponseDto getAddress() { return address; }
    public void setAddress(AddressResponseDto address) { this.address = address; }

    public PurchaserDto getPurchaser() { return purchaser; }
    public void setPurchaser(PurchaserDto purchaser) { this.purchaser = purchaser; }

    public Set<PartnerDto> getPartners() { return partners; }
    public void setPartners(Set<PartnerDto> partners) { this.partners = partners; }

    public OwnerDto getOwner() {
        return owner;
    }

    public void setOwner(OwnerDto owner) {
        this.owner = owner;
    }
}