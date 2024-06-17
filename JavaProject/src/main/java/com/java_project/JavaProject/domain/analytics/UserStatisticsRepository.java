package com.java_project.JavaProject.domain.analytics;

import com.java_project.JavaProject.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserStatisticsRepository extends JpaRepository<User,Integer> {
    @Query("SELECT new com.java_project.JavaProject.domain.analytics.UserStatistics( " +
            "u.name, u.salary, c.name, COUNT(e.id) as numberOfExpenses, SUM(e.amount) as spent) " +
            "FROM User u " +
            "JOIN Expense e ON u.id = e.userId " +
            "JOIN Category c ON c.id = e.categoryId " +
            "GROUP BY u.name, u.salary, c.name " +
            "ORDER BY u.name")
    List<UserStatistics> getUsersStatistics();

    @Query("SELECT new com.java_project.JavaProject.domain.analytics.UserStatistics( " +
            "u.id, u.name, u.salary, c.name, COUNT(e.id) as numberOfExpenses, SUM(e.amount) as spent) " +
            "FROM User u " +
            "JOIN Expense e ON u.id = e.userId " +
            "JOIN Category c ON c.id = e.categoryId " +
            "WHERE u.id = :userId " +
            "GROUP BY u.id, u.name, u.salary, c.name " +
            "ORDER BY u.name")
    List<UserStatistics> getUserStatisticsByUserId(int userId);
}
