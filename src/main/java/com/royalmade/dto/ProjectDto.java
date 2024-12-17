package com.royalmade.dto;

import com.royalmade.entity.enumeration.ProjectStatus;

import java.util.Set;

public class ProjectDto {
    public String name;
    public ProjectStatus status;
    public Set<ExpenseDto> expenses;

    public ProjectDto(String name, ProjectStatus status, Set<ExpenseDto> expenses) {
        this.name = name;
        this.status = status;
        this.expenses = expenses;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public Set<ExpenseDto> getExpenses() {
        return expenses;
    }

    public void setExpenses(Set<ExpenseDto> expenses) {
        this.expenses = expenses;
    }
}
