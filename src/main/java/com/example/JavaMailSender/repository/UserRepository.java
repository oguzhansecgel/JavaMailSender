package com.example.JavaMailSender.repository;

import com.example.JavaMailSender.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(int id);
}
