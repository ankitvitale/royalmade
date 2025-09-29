package com.royalmade.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.royalmade.entity.enumeration.ExpensePayStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class VendorPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate payDate;
    private double amount;
    private String remark;


    @Enumerated(EnumType.STRING)
    private ExpensePayStatus paymentStatus;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    @JsonBackReference
    private Material material;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @JsonIgnoreProperties(value = { "materials", "expenses", "residencies", "land" }, allowSetters = true)
    private Project project;  // Added Project reference

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getPayDate() {
        return payDate;
    }

    public void setPayDate(LocalDate payDate) {
        this.payDate = payDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }



    public ExpensePayStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(ExpensePayStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
