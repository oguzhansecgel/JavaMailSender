package com.example.JavaMailSender.service.concretes;

import com.example.JavaMailSender.entity.User;
import com.example.JavaMailSender.repository.UserRepository;
import com.example.JavaMailSender.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;

    public UserServiceImpl(UserRepository userRepository, JavaMailSender javaMailSender) {
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void add(User user) {
        Optional<User> hasEmail = userRepository.findByEmail(user.getEmail());
        if (hasEmail.isPresent())
        {
            throw new RuntimeException("User already exists");
        }
        userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email or password "));
    }

    private void sendConfirmationEmail(String userEmail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userEmail);
        mailMessage.setSubject("Merhabalar");
        mailMessage.setText("Merhaba, hesabınız başarıyla oluşturulmuştur. Sorularınız ve destek için bizlerle iletişime geçmekten sakın çekinmeyin.");

        try {
            javaMailSender.send(mailMessage);
        } catch (MailException e) {
            e.printStackTrace();
        }
    }
}
