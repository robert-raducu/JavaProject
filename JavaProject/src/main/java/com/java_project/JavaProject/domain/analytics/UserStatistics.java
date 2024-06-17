package com.java_project.JavaProject.domain.analytics;

public class UserStatistics {
    int userId;
    String userName;
    double userSalary;
    String categoryName;
    long numberOfExpenses;
    double sumExpenseAmount;

    public UserStatistics(String userName, double userSalary, String categoryName, long numberOfExpenses, double sumExpenseAmount) {
        this.userName = userName;
        this.userSalary = userSalary;
        this.categoryName = categoryName;
        this.numberOfExpenses = numberOfExpenses;
        this.sumExpenseAmount = sumExpenseAmount;
    }

    public UserStatistics(int userId, String userName, double userSalary, String categoryName, long numberOfExpenses, double sumExpenseAmount) {
        this.userId = userId;
        this.userName = userName;
        this.userSalary = userSalary;
        this.categoryName = categoryName;
        this.numberOfExpenses = numberOfExpenses;
        this.sumExpenseAmount = sumExpenseAmount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getUserSalary() {
        return userSalary;
    }

    public void setUserSalary(double userSalary) {
        this.userSalary = userSalary;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public long getNumberOfExpenses() {
        return numberOfExpenses;
    }

    public void setNumberOfExpenses(long numberOfExpenses) {
        this.numberOfExpenses = numberOfExpenses;
    }

    public double getSumExpenseAmount() {
        return sumExpenseAmount;
    }

    public void setSumExpenseAmount(double sumExpenseAmount) {
        this.sumExpenseAmount = sumExpenseAmount;
    }
}
