package com.royalmade.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.royalmade.entity.enumeration.InstallmentStatus;
import com.royalmade.entity.enumeration.TransactionChange;
import com.royalmade.entity.enumeration.TransactionMadeBy;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class LandTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private LocalDate transactionDate;
    private Double transactionAmount;
    private String note;
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_change")
    private TransactionChange change;

    @Enumerated(EnumType.STRING)
    private TransactionMadeBy madeBy;

    @Enumerated(EnumType.STRING)
    private InstallmentStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties

    @JoinColumn(name = "partner_id")
    @JsonBackReference
    private Partner partner;


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "person_id")
    private Person person;


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

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }


//    public Person getPurchaser() {
//        return purchaser;
//    }
//
//    public void setPurchaser(Person purchaser) {
//        this.purchaser = purchaser;
//    }
}
