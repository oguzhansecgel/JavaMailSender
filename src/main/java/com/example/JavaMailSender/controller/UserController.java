package com.example.JavaMailSender.controller;

import com.example.JavaMailSender.entity.User;
import com.example.JavaMailSender.service.abstracts.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("create")
    public void createUser(@RequestBody User user)
    {
        userService.addUser(user);
    }
}
