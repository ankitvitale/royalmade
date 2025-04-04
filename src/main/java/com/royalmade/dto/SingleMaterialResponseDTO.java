package com.royalmade.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SingleMaterialResponseDTO {
    private Long id;
    private String name;
    private String type;
    private int quantity;
    private double price;
    private LocalDate addedOn;
    private List<PaymentDTO> payments = new ArrayList<>();  // Default empty list

    public SingleMaterialResponseDTO(Long id, String name, String type, int quantity, double price, LocalDate addedOn) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
        this.addedOn = addedOn;
    }

    public void setPayments(List<PaymentDTO> payments) {
        this.payments = payments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(LocalDate addedOn) {
        this.addedOn = addedOn;
    }

    public List<PaymentDTO> getPayments() {
        return payments;
    }
}
