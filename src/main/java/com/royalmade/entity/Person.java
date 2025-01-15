package com.royalmade.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "aadhar_number")
    private String aadharNumber;

    @JsonIgnoreProperties(value = { "address", "owner", "purchaser", "partners", "project" }, allowSetters = true)
    @OneToOne( mappedBy = "owner")
    private Land ownedLand;

    @JsonIgnoreProperties(value = { "address", "owner", "purchaser", "partners", "project" }, allowSetters = true)
    @OneToOne( mappedBy = "purchaser")
    private Land purchasedLand;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Person id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Person name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Person phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return this.address;
    }

    public Person address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAadharNumber() {
        return this.aadharNumber;
    }

    public Person aadharNumber(String aadharNumber) {
        this.setAadharNumber(aadharNumber);
        return this;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public Land getOwnedLand() {
        return this.ownedLand;
    }

    public void setOwnedLand(Land land) {
        if (this.ownedLand != null) {
            this.ownedLand.setOwner(null);
        }
        if (land != null) {
            land.setOwner(this);
        }
        this.ownedLand = land;
    }

    public Person ownedLand(Land land) {
        this.setOwnedLand(land);
        return this;
    }

    public Land getPurchasedLand() {
        return this.purchasedLand;
    }

    public void setPurchasedLand(Land land) {
        if (this.purchasedLand != null) {
            this.purchasedLand.setPurchaser(null);
        }
        if (land != null) {
            land.setPurchaser(this);
        }
        this.purchasedLand = land;
    }

    public Person purchasedLand(Land land) {
        this.setPurchasedLand(land);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        return getId() != null && getId().equals(((Person) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", address='" + getAddress() + "'" +
            ", aadharNumber='" + getAadharNumber() + "'" +
            "}";
    }
}
