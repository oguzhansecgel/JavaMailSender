package com.example.JavaMailSender.service.abstracts;

import com.example.JavaMailSender.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    void add(User user);

    Optional<User> getUserById(int id);
}