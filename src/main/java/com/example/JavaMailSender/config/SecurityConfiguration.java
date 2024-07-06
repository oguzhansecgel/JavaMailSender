package com.example.JavaMailSender.config;

import com.example.JavaMailSender.service.abstracts.UserService;
import com.turkcell.tcell.core.security.BaseSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final BaseSecurityService baseSecurityService;

    private static final String[] WHITE_LIST = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-ui/index.html/**",
            "api/v1/user/**"

    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        baseSecurityService.configureCommonSecurityRules(http);
        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests.requestMatchers(WHITE_LIST).permitAll()
                        .anyRequest().authenticated()
        );
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
