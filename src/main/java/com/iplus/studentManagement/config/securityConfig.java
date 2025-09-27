package com.iplus.studentManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class securityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // disable CSRF for testing APIs (enable in production with tokens)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()  // allow public access to auth endpoints
                        .requestMatchers("/h2-console/**").permitAll() // if using H2 console
                        .anyRequest().authenticated()                  // all others need auth
                )
                .formLogin(form -> form.permitAll()) // basic login form
                .httpBasic(); // also allow basic auth (for testing APIs with Postman)

        // for H2 console compatibility
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}
