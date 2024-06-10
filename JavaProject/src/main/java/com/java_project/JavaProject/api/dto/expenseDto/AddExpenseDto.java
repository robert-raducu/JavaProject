package com.java_project.JavaProject.api.dto.expenseDto;


import java.time.LocalDateTime;

public class AddExpenseDto {
    //@NotNull(message = "Este necesar sa completezi suma!")
    double amount;
    String description;
    LocalDateTime date;
    int categoryId;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
