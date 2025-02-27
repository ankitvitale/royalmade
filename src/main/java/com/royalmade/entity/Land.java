package com.royalmade.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Land.
 */
@Entity
@Table(name = "land")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Land implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "area")
    private Double area;

    @Column(name = "token_amount")
    private Double tokenAmount;

    @Column(name = "agreement_amount")
    private Double agreementAmount;

    @Column(name = "total_amount")
    private Double totalAmount;

    @JsonIgnoreProperties(value = { "land" }, allowSetters = true)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private Address address;

    @JsonIgnoreProperties(value = { "ownedLand", "purchasedLand" }, allowSetters = true)
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(unique = true)
    private Person owner;

    @JsonIgnoreProperties(value = { "ownedLand", "purchasedLand" }, allowSetters = true)
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(unique = true)
    private Person purchaser;

    @OneToMany( mappedBy = "land",cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "land" }, allowSetters = true)
    private Set<Partner> partners = new HashSet<>();

    @JsonIgnoreProperties(value = { "land", "expenses", "residencies" }, allowSetters = true)
    @OneToOne( mappedBy = "land")
    private Project project;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Land id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getArea() {
        return this.area;
    }

    public Land area(Double area) {
        this.setArea(area);
        return this;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getTokenAmount() {
        return this.tokenAmount;
    }

    public Land tokenAmount(Double tokenAmount) {
        this.setTokenAmount(tokenAmount);
        return this;
    }

    public void setTokenAmount(Double tokenAmount) {
        this.tokenAmount = tokenAmount;
    }

    public Double getAgreementAmount() {
        return this.agreementAmount;
    }

    public Land agreementAmount(Double agreementAmount) {
        this.setAgreementAmount(agreementAmount);
        return this;
    }

    public void setAgreementAmount(Double agreementAmount) {
        this.agreementAmount = agreementAmount;
    }

    public Double getTotalAmount() {
        return this.totalAmount;
    }

    public Land totalAmount(Double totalAmount) {
        this.setTotalAmount(totalAmount);
        return this;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Land address(Address address) {
        this.setAddress(address);
        return this;
    }

    public Person getOwner() {
        return this.owner;
    }

    public void setOwner(Person person) {
        this.owner = person;
    }

    public Land owner(Person person) {
        this.setOwner(person);
        return this;
    }

    public Person getPurchaser() {
        return this.purchaser;
    }

    public void setPurchaser(Person person) {
        this.purchaser = person;
    }

    public Land purchaser(Person person) {
        this.setPurchaser(person);
        return this;
    }

    public Set<Partner> getPartners() {
        return this.partners;
    }

    public void setPartners(Set<Partner> partners) {
        if (this.partners != null) {
            this.partners.forEach(i -> i.setLand(null));
        }
        if (partners != null) {
            partners.forEach(i -> i.setLand(this));
        }
        this.partners = partners;
    }

    public Land partners(Set<Partner> partners) {
        this.setPartners(partners);
        return this;
    }

    public Land addPartner(Partner partner) {
        this.partners.add(partner);
        partner.setLand(this);
        return this;
    }

    public Land removePartner(Partner partner) {
        this.partners.remove(partner);
        partner.setLand(null);
        return this;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        if (this.project != null) {
            this.project.setLand(null);
        }
        if (project != null) {
            project.setLand(this);
        }
        this.project = project;
    }

    public Land project(Project project) {
        this.setProject(project);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Land)) {
            return false;
        }
        return getId() != null && getId().equals(((Land) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Land{" +
            "id=" + getId() +
            ", area=" + getArea() +
            ", tokenAmount=" + getTokenAmount() +
            ", agreementAmount=" + getAgreementAmount() +
            ", totalAmount=" + getTotalAmount() +
            "}";
    }



}
