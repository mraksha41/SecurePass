package com.securepass.SecurePass.controller;

import com.securepass.SecurePass.domain.User;

import com.securepass.SecurePass.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user/add")
    public String addUser(@RequestParam String first, @RequestParam String last) {
        User user = new User();
        user.setFirstName(first);
        user.setLastName(last);
        userRepository.save(user);
        return "Added new user to repo!";
    }

    @GetMapping("/user/list")
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/user/find/{id}")
    public User findUserById(@PathVariable Integer id) {
        return userRepository.findUserById(id);
    }

}
