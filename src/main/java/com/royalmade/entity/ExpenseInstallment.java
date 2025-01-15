package com.royalmade.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.royalmade.entity.enumeration.ExpensePayStatus;
import com.royalmade.entity.enumeration.InstallmentStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class ExpenseInstallment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "expense_Date")
    private LocalDate expensePayDate;

    @Column(name = "expense_amount")
    private Double  expenseAmount;
    @Enumerated(EnumType.STRING)
    @Column(name = "expense_status")
    private ExpensePayStatus expensePayStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "bookingInstallment" }, allowSetters = true)
    private Expense expense;

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

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }
}
