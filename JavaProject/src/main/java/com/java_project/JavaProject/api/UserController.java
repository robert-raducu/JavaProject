package com.java_project.JavaProject.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @GetMapping("/test")
    public String expenseTest(){
        return "test";
    }

    @GetMapping
    public String userString(){
        return "User example";
    }
}
