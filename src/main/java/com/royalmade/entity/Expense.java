package com.royalmade.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.royalmade.entity.enumeration.ExpenseType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Expense.
 */
@Entity
@Table(name = "expense")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Expense implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ExpenseType type;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "vendor_name")
    private String vendorName;

    @Column(name = "price")
    private Double price;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "vendorAmountPaid")
    private Double vendorAmountPaid;
    @Column(name = "reamingAmount")
    private Double reamingAmount;

    @Column(name = "added_on")
    private LocalDate addedOn;
    @Column(name = "billImg")
    private String billImg;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "expense")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "expense" }, allowSetters = true)
    private List<ExpenseInstallment> expenseInstallments = new ArrayList<>();
    @JsonIgnoreProperties(value = { "expense" }, allowSetters = true)
    @ManyToOne
  //  @JoinColumn(unique = true)
    private AppUser addedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = { "land", "expenses", "residencies" }, allowSetters = true)
    private Project project;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Expense id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Expense name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExpenseType getType() {
        return this.type;
    }

    public Expense type(ExpenseType type) {
        this.setType(type);
        return this;
    }

    public void setType(ExpenseType type) {
        this.type = type;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public Expense quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getVendorName() {
        return this.vendorName;
    }

    public Expense vendorName(String vendorName) {
        this.setVendorName(vendorName);
        return this;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Double getPrice() {
        return this.price;
    }

    public Expense price(Double price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getVendorAmountPaid() {
        return vendorAmountPaid;
    }

    public void setVendorAmountPaid(Double vendorAmountPaid) {
        this.vendorAmountPaid = vendorAmountPaid;
    }

    public Double getReamingAmount() {
        return reamingAmount;
    }

    public void setReamingAmount(Double reamingAmount) {
        this.reamingAmount = reamingAmount;
    }

    public List<ExpenseInstallment> getExpenseInstallments() {
        return expenseInstallments;
    }

    public void setExpenseInstallments(List<ExpenseInstallment> expenseInstallments) {
        this.expenseInstallments = expenseInstallments;
    }

    public LocalDate getAddedOn() {
        return this.addedOn;
    }

    public Expense addedOn(LocalDate addedOn) {
        this.setAddedOn(addedOn);
        return this;
    }

    public void setAddedOn(LocalDate addedOn) {
        this.addedOn = addedOn;
    }


    public String getBillImg() {
        return billImg;
    }

    public void setBillImg(String billImg) {
        this.billImg = billImg;
    }

    public AppUser getAddedBy() {
        return this.addedBy;
    }

    public void setAddedBy(AppUser appUser) {
        this.addedBy = appUser;
    }

    public Expense addedBy(AppUser appUser) {
        this.setAddedBy(appUser);
        return this;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Expense project(Project project) {
        this.setProject(project);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Expense)) {
            return false;
        }
        return getId() != null && getId().equals(((Expense) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Expense{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", quantity=" + getQuantity() +
            ", vendorName='" + getVendorName() + "'" +
            ", price=" + getPrice() +
            ", addedOn='" + getAddedOn() + "'" +
            "}";
    }
}
