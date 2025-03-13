package com.royalmade.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;


@Entity
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double billNo;
    private String name;
    private String type;
    private int quantity;
    private double price;
    private LocalDate addedOn;

    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    @JsonBackReference
    private Vendor vendor;

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<VendorPayment> payments;

    @JsonIgnoreProperties(value = { "material" }, allowSetters = true)
    @ManyToOne
    @JsonIgnore
    private AppUser addedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = { "land", "material", "residencies" }, allowSetters = true)
    private Project project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBillNo() {
        return billNo;
    }

    public void setBillNo(Double billNo) {
        this.billNo = billNo;
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

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public List<VendorPayment> getPayments() {
        return payments;
    }

    public void setPayments(List<VendorPayment> payments) {
        this.payments = payments;
    }

    public AppUser getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(AppUser addedBy) {
        this.addedBy = addedBy;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
