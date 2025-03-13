package com.royalmade.dto;

import java.time.LocalDate;
import java.util.List;

public class MaterialDTO {
    private Long id;
    private String name;
    private String type;
    private int quantity;
    private double price;
    private LocalDate addedOn;
    private List<PaymentDTO> payments;

    public MaterialDTO(Long id, String name, String type, int quantity, double price, LocalDate addedOn, List<PaymentDTO> payments) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
        this.addedOn = addedOn;
        this.payments = payments;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public LocalDate getAddedOn() { return addedOn; }
    public List<PaymentDTO> getPayments() { return payments; }
}
