package com.royalmade.dto;

import com.royalmade.entity.enumeration.BookingStatus;

import java.time.LocalDate;

public class BookingDto {
    private Long id;
    private Double dealPrice;
    private Double tokenAmount;
    private Double agreementAmount;
    private Double stampDutyAmount;
    private Double registrationAmount;
    private Double gstAmount;
    private Double electricWaterAmmount;

    private Double legalChargesAmmout;
    private LocalDate bookedOn;
    private BookingStatus bookingStatus;
    private CustomerDto customerDto;
    private ResidencyDto residencyDto;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(Double dealPrice) {
        this.dealPrice = dealPrice;
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

    public Double getStampDutyAmount() {
        return stampDutyAmount;
    }

    public void setStampDutyAmount(Double stampDutyAmount) {
        this.stampDutyAmount = stampDutyAmount;
    }

    public Double getRegistrationAmount() {
        return registrationAmount;
    }

    public void setRegistrationAmount(Double registrationAmount) {
        this.registrationAmount = registrationAmount;
    }

    public Double getGstAmount() {
        return gstAmount;
    }

    public void setGstAmount(Double gstAmount) {
        this.gstAmount = gstAmount;
    }

    public Double getElectricWaterAmmount() {
        return electricWaterAmmount;
    }

    public void setElectricWaterAmmount(Double electricWaterAmmount) {
        this.electricWaterAmmount = electricWaterAmmount;
    }

    public Double getLegalChargesAmmout() {
        return legalChargesAmmout;
    }

    public void setLegalChargesAmmout(Double legalChargesAmmout) {
        this.legalChargesAmmout = legalChargesAmmout;
    }

    public LocalDate getBookedOn() {
        return bookedOn;
    }

    public void setBookedOn(LocalDate bookedOn) {
        this.bookedOn = bookedOn;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public CustomerDto getCustomerDto() {
        return customerDto;
    }

    public void setCustomerDto(CustomerDto customerDto) {
        this.customerDto = customerDto;
    }

    public ResidencyDto getResidencyDto() {
        return residencyDto;
    }

    public void setResidencyDto(ResidencyDto residencyDto) {
        this.residencyDto = residencyDto;
    }
}
