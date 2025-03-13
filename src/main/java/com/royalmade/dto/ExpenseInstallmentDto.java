package com.royalmade.dto;

import com.royalmade.entity.enumeration.ExpensePayStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

public class ExpenseInstallmentDto {


    private Long id;

    private LocalDate expensePayDate;
    private Double  expenseAmount;
    private ExpensePayStatus expensePayStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getExpensePayDate() {
        return expensePayDate;
    }

    public void setExpensePayDate(LocalDate expensePayDate) {
        this.expensePayDate = expensePayDate;
    }

    public Double getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(Double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public ExpensePayStatus getExpensePayStatus() {
        return expensePayStatus;
    }

    public void setExpensePayStatus(ExpensePayStatus expensePayStatus) {
        this.expensePayStatus = expensePayStatus;
    }
}
