package com.royalmade.dto;

import com.royalmade.entity.Address;
import com.royalmade.entity.Partner;
import com.royalmade.entity.Person;

import java.util.List;
import java.util.Set;


public class LandRequestDto {
  //  private Long id;
    private Double area;
    private Double tokenAmount;
    private Double agreementAmount;
    private Double totalAmount;
    private Double soldAmount;
    private Address address;
    private Person owner;
    private Person purchaser;


    private Set<Partner> partners;

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getTokenAmount() {
        return tokenAmount;
    }

    public void setTokenAmount(Double tokenAmount) {
        this.tokenAmount = tokenAmount;
    }

    public Double getAgreementAmount() {
        return agreementAmount;
    }

    public void setAgreementAmount(Double agreementAmount) {
        this.agreementAmount = agreementAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getSoldAmount() {
        return soldAmount;
    }

    public void setSoldAmount(Double soldAmount) {
        this.soldAmount = soldAmount;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Person getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(Person purchaser) {
        this.purchaser = purchaser;
    }

    public Set<Partner> getPartners() {
        return partners;
    }

    public void setPartners(Set<Partner> partners) {
        this.partners = partners;
    }

}



