package com.royalmade.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.royalmade.entity.enumeration.ProjectStatus;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProjectStatus status;

    @JsonIgnoreProperties(value = { "address", "owner", "purchaser", "partners", "project" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Land land;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "addedBy", "project" }, allowSetters = true)
    private Set<Expense> expenses = new HashSet<>();

    @OneToMany( mappedBy = "project")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "booking", "project" }, allowSetters = true)
    private Set<Residency> residencies = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "password", "email", "allowedSite", "expenses", "role" }, allowSetters = true)
    private AppUser appUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Project id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Project name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProjectStatus getStatus() {
        return this.status;
    }

    public Project status(ProjectStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public Land getLand() {
        return this.land;
    }

    public void setLand(Land land) {
        this.land = land;
    }

    public Project land(Land land) {
        this.setLand(land);
        return this;
    }

    public Set<Expense> getExpenses() {
        return this.expenses;
    }

    public void setExpenses(Set<Expense> expenses) {
        if (this.expenses != null) {
            this.expenses.forEach(i -> i.setProject(null));
        }
        if (expenses != null) {
            expenses.forEach(i -> i.setProject(this));
        }
        this.expenses = expenses;
    }

    public Project expenses(Set<Expense> expenses) {
        this.setExpenses(expenses);
        return this;
    }

    public Project addExpense(Expense expense) {
        this.expenses.add(expense);
        expense.setProject(this);
        return this;
    }

    public Project removeExpense(Expense expense) {
        this.expenses.remove(expense);
        expense.setProject(null);
        return this;
    }

    public Set<Residency> getResidencies() {
        return this.residencies;
    }

    public void setResidencies(Set<Residency> residencies) {
        if (this.residencies != null) {
            this.residencies.forEach(i -> i.setProject(null));
        }
        if (residencies != null) {
            residencies.forEach(i -> i.setProject(this));
        }
        this.residencies = residencies;
    }

    public Project residencies(Set<Residency> residencies) {
        this.setResidencies(residencies);
        return this;
    }

    public Project addResidency(Residency residency) {
        this.residencies.add(residency);
        residency.setProject(this);
        return this;
    }

    public Project removeResidency(Residency residency) {
        this.residencies.remove(residency);
        residency.setProject(null);
        return this;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
// jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        return getId() != null && getId().equals(((Project) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
