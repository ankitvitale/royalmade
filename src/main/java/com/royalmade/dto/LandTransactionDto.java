package com.royalmade.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.royalmade.entity.Partner;
import com.royalmade.entity.Person;
import com.royalmade.entity.enumeration.InstallmentStatus;
import com.royalmade.entity.enumeration.TransactionChange;
import com.royalmade.entity.enumeration.TransactionMadeBy;
import jakarta.persistence.*;

import java.time.LocalDate;

public class LandTransactionDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private LocalDate transactionDate;
    private Double transactionAmount;
    private String note;
    @Enumerated(EnumType.STRING)
    private TransactionChange change;

    @Enumerated(EnumType.STRING)
    private TransactionMadeBy madeBy;

    @Enumerated(EnumType.STRING)
    private InstallmentStatus status;

    public LandTransactionDto(){}
    public LandTransactionDto(Long id, LocalDate transactionDate, Double transactionAmount, String note, TransactionChange change, TransactionMadeBy madeBy, InstallmentStatus status, Partner partnerId, Person personId) {
        this.id = id;
        this.transactionDate = transactionDate;
        this.transactionAmount = transactionAmount;
        this.note = note;
        this.change = change;
        this.madeBy = madeBy;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public TransactionChange getChange() {
        return change;
    }

    public void setChange(TransactionChange change) {
        this.change = change;
    }

    public TransactionMadeBy getMadeBy() {
        return madeBy;
    }

    public void setMadeBy(TransactionMadeBy madeBy) {
        this.madeBy = madeBy;
    }

    public InstallmentStatus getStatus() {
        return status;
    }

    public void setStatus(InstallmentStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LandTransactionDto{" +
                "id=" + id +
                ", transactionDate=" + transactionDate +
                ", transactionAmount=" + transactionAmount +
                ", note='" + note + '\'' +
                ", change=" + change +
                ", madeBy=" + madeBy +
                ", status=" + status +
                '}';
    }
}
