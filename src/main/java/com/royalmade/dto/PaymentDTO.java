package com.royalmade.dto;

import com.royalmade.entity.enumeration.ExpensePayStatus;

import java.time.LocalDate;

public class PaymentDTO {
    private Long id;
    private LocalDate payDate;
    private double amount;
    private String remark;
    private ExpensePayStatus paymentStatus;
    public PaymentDTO(){}
    public PaymentDTO(Long id, LocalDate payDate, double amount, String remark, ExpensePayStatus paymentStatus) {
        this.id = id;
        this.payDate = payDate;
        this.amount = amount;
        this.remark = remark;
        this.paymentStatus = paymentStatus;
    }

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
}
