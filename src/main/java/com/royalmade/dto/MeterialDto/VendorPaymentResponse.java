package com.royalmade.dto.MeterialDto;

import com.royalmade.entity.enumeration.ExpensePayStatus;

import java.time.LocalDate;

public class VendorPaymentResponse {
    private Long id;
    private LocalDate payDate;
    private double amount;
    private String remark;
    private ExpensePayStatus paymentStatus;
    private Long materialId;
    private Long vendorId;
    private Long projectId;

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

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
