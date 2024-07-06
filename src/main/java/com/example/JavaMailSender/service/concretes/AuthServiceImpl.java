package com.example.JavaMailSender.service.concretes;

import com.example.JavaMailSender.dto.request.RegisterRequest;
import com.example.JavaMailSender.entity.User;
import com.example.JavaMailSender.mapper.AuthMapper;
import com.example.JavaMailSender.service.abstracts.AuthService;
import com.example.JavaMailSender.service.abstracts.UserService;
import com.turkcell.tcell.core.security.BaseJwtService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final BaseJwtService baseJwtService;
    private final JavaMailSender javaMailSender;

    @Override
    public void register(RegisterRequest request) {
        User user = AuthMapper.INSTANCE.userFromRequest(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        sendConfirmationEmail(request.getEmail());
        userService.add(user);

    }
    private void sendConfirmationEmail(String userEmail) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(userEmail);
            helper.setSubject("Merhabalar");
            String htmlContent = "<!DOCTYPE html>"
                    + "<html lang='en'>"
                    + "<head>"
                    + "<meta charset='UTF-8'>"
                    + "<meta name='viewport' content='width=device-width, initial-scale=1.0'>"
                    + "<title>Account Confirmation</title>"
                    + "<style>"
                    + ".email-container { font-family: Arial, sans-serif; margin: 0 auto; padding: 20px; width: 60%; border: 1px solid #ddd; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); text-align: center; }"
                    + ".header { background-color: #f4f4f4; padding: 10px; font-size: 24px; font-weight: bold; }"
                    + ".user-email { margin: 20px 0; font-size: 16px; color: #333; }"
                    + ".divider { border-bottom: 1px solid #ddd; margin: 20px 0; }"
                    + ".content { margin: 20px 0; font-size: 16px; color: #555; text-align: center;}"
                    + ".footer { background-color: #f4f4f4; padding: 10px; font-size: 12px; color: #777; }"
                    + "</style>"
                    + "</head>"
                    + "<body>"
                    + "<div class='email-container'>"
                    + "<div class='header'>Merhabalar</div>"
                    + "<div class='user-email'>" + userEmail + "</div>"
                    + "<div class='divider'></div>"
                    + "<div class='content'>Merhaba, hesabınız başarıyla oluşturulmuştur. Sorularınız ve destek için bizlerle iletişime geçmekten sakın çekinmeyin.</div>"
                    + "<div class='footer'>© 2024 Your Company. All rights reserved.</div>"
                    + "</div>"
                    + "</body>"
                    + "</html>";
            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
/*
    @Override
    public String login(LoginRequest loginRequest) {
        validateEmailService.validateEmail(loginRequest.getEmail());
        // TODO: Handle Exception.
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        if(!authentication.isAuthenticated())
            throw new UnauthorizedException("E-posta ya da şifre yanlış");

        UserDetails user = userService.loadUserByUsername(loginRequest.getEmail());
        // TODO: Refactor
        Map<String,Object> claims = new HashMap<>();
        List<String> roles = user
                .getAuthorities()
                .stream()
                .map((role) -> role.getAuthority())
                .toList();
        claims.put("roles", roles);
        return baseJwtService.generateToken(loginRequest.getEmail(), claims);
    }*/
}
