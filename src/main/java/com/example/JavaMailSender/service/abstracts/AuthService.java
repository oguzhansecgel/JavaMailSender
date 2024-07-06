package com.example.JavaMailSender.service.abstracts;

import com.example.JavaMailSender.dto.request.RegisterRequest;
import com.example.JavaMailSender.entity.User;

public interface AuthService {

    void register(RegisterRequest request);

    //String login(LoginRequest loginRequest);
}