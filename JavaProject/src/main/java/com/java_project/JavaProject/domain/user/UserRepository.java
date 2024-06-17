package com.java_project.JavaProject.domain.user;

import com.java_project.JavaProject.domain.expense.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

}
