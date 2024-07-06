package com.example.JavaMailSender.controller;

import com.example.JavaMailSender.dto.request.RegisterRequest;
import com.example.JavaMailSender.entity.User;
import com.example.JavaMailSender.service.abstracts.AuthService;
import com.example.JavaMailSender.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthService authService;


    @PostMapping("register")
    public void register( @RequestBody RegisterRequest request)
    {
        authService.register(request);
    }
}
