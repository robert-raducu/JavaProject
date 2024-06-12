package com.java_project.JavaProject.api.controller;

import com.java_project.JavaProject.api.dto.userDto.AddUserDto;
import com.java_project.JavaProject.api.dto.userDto.UpdateUserDto;
import com.java_project.JavaProject.domain.user.User;
import com.java_project.JavaProject.domain.user.UserRepository;
import com.java_project.JavaProject.exception.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String userString(){
        return "User example";
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
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

        return userRepository.save(newUser);
    }

    @PatchMapping("/updateUser/{id}")
    User updateUser(
            @PathVariable Integer id,
            @RequestBody UpdateUserDto updateDto
            ){

        User updatedUser = userRepository.findById(id)
                .orElseThrow(()-> new BadRequestException("No user found with id: " + id));

        updatedUser.setName(updateDto.getName());
        updatedUser.setAge(updateDto.getAge());
        updatedUser.setCity(updateDto.getCity());
        updatedUser.setJob(updateDto.getJob());
        updatedUser.setSalary(updateDto.getSalary());

        return userRepository.save(updatedUser);
    }

    @DeleteMapping("/deleteUser/{id}")
    ResponseEntity<String> deleteUser (@PathVariable Integer id){
        User deletedUser = userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Nu " +
                "este urmatoarul user aferent id-ului: " + id));

        userRepository.delete(deletedUser);
        return ResponseEntity.ok("Utilizatorul a fost sters");

    }


}
