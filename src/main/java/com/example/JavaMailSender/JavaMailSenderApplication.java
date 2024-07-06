package com.example.JavaMailSender;

import com.turkcell.tcell.core.annotations.EnableSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableSecurity
public class JavaMailSenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaMailSenderApplication.class, args);
	}

}
