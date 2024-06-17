package com.java_project.JavaProject.domain.expense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

//    @Query("""
//            SELECT e FROM expenses e
//            """)
//    List<Expense>findAllExpenses();
    @Query("""
            SELECT e FROM Expense e WHERE e.categoryId = :categoryId
            """)
    List<Expense> findAllByCategoryId(Integer categoryId);

    @Query("""
            SELECT e FROM Expense e WHERE e.userId = :userId
            """)
    List<Expense> findAllByUserId(Integer userId);
}
