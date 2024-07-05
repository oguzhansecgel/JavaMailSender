package com.example.JavaMailSender.service.concretes;

import com.example.JavaMailSender.entity.User;
import com.example.JavaMailSender.repository.UserRepository;
import com.example.JavaMailSender.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;

    public UserServiceImpl(UserRepository userRepository, JavaMailSender javaMailSender) {
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void addUser(User user) {
        userRepository.save(user);
        sendConfirmationEmail(user.getEmail());
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
