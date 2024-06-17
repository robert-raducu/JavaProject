package com.java_project.JavaProject.api.controller;

import com.java_project.JavaProject.api.dto.userDto.AddUserDto;
import com.java_project.JavaProject.api.dto.userDto.UpdateUserDto;
import com.java_project.JavaProject.domain.analytics.UserStatistics;
import com.java_project.JavaProject.domain.analytics.UserStatisticsRepository;
import com.java_project.JavaProject.domain.expense.Expense;
import com.java_project.JavaProject.domain.expense.ExpenseRepository;
import com.java_project.JavaProject.domain.user.User;
import com.java_project.JavaProject.domain.user.UserRepository;
import com.java_project.JavaProject.exception.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/UserPage")
public class UserController {

    final UserRepository userRepository;
    final ExpenseRepository expenseRepository;
    final UserStatisticsRepository userStatisticsRepository;

    public UserController(UserRepository userRepository, ExpenseRepository expenseRepository, UserStatisticsRepository userStatisticsRepository) {
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
        this.userStatisticsRepository = userStatisticsRepository;
    }

    @GetMapping
    public String userString(){
        return "Welcome to the users page!";
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    User getAllUsers(@PathVariable Integer id){
        return userRepository.findById(id).get();
    }

    @PostMapping("/addUser")
    User addUser(@RequestBody AddUserDto addDto){
        User newUser = new User();
        newUser.setName(addDto.getName());
        newUser.setAge(addDto.getAge());
        newUser.setCity(addDto.getCity());
        newUser.setJob(addDto.getJob());
        newUser.setSalary(addDto.getSalary());
        newUser.setRemainingSalary(addDto.getSalary());

        return userRepository.save(newUser);
    }

    @PatchMapping("/updateUser/{id}")
    User updateUser(
            @PathVariable Integer id,
            @RequestBody UpdateUserDto updateDto
            ){

        User updatedUser = userRepository.findById(id)
                .orElseThrow(()-> new BadRequestException("No user found with id: " + id));

        if (!Objects.equals(updateDto.getName(), "string")) {
            updatedUser.setName(updateDto.getName());
        }
        if (updateDto.getAge() != 0) {
            updatedUser.setAge(updateDto.getAge());
        }
        if (!Objects.equals(updateDto.getCity(), "string")) {
            updatedUser.setCity(updateDto.getCity());
        }
        if (!Objects.equals(updateDto.getJob(), "string")) {
            updatedUser.setJob(updateDto.getJob());
        }
        if (updateDto.getSalary() != 0) {
            updatedUser.setSalary(updateDto.getSalary());
            // Adjust remaining salary based on new salary
            // If you have specific logic to calculate remaining salary, add it here
        }
        if (updateDto.getRemainingSalary() != 0) {
            updatedUser.setRemainingSalary(updateDto.getRemainingSalary());
        }

        return userRepository.save(updatedUser);
    }

    @DeleteMapping("/deleteUser/{id}")
    ResponseEntity<String> deleteUser (@PathVariable Integer id){
        User deletedUser = userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No user found with id: " + id));

        userRepository.delete(deletedUser);
        return ResponseEntity.ok("The user was successfully deleted!");

    }

    @GetMapping("/statistics")
    public List<UserStatistics> getUsersStatistics(){
        return userStatisticsRepository.getUsersStatistics();
    }

    @GetMapping("/statistics/{userId}")
    public List<UserStatistics> getUserStatistics(@PathVariable Integer userId) {
        userRepository.findById(userId)
                .orElseThrow(()-> new BadRequestException("No user found with id: " + userId));
        return userStatisticsRepository.getUserStatisticsByUserId(userId);
    }
}
