package com.royalmade.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private LocalDate date;
    private Double totalAmount;
    private Double amountPaid;
    private Double balanceAmount;

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<MaterialItem> items;

    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    @JsonBackReference
    private Vendor vendor;


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


    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public List<MaterialItem> getItems() {
        return items;
    }

    public void setItems(List<MaterialItem> items) {
        this.items = items;
    }
}
