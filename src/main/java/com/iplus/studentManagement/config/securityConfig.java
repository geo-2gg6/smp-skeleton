package com.iplus.studentManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class securityConfig {

    // Bean to encode passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean to create in-memory users for testing
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN", "USER")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    // Main security configuration
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Allow public access to static resources, the home page, login, and API
                .requestMatchers("/", "/css/**", "/js/**", "/api/**", "/login", "/signup").permitAll()
                // Only allow users with the 'ADMIN' role to access delete URLs
                .requestMatchers("/students/delete/**", "/courses/delete/**").hasRole("ADMIN")
                // All other requests require the user to be authenticated
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                // Specify the custom login page URL
                .loginPage("/login")
                // Redirect to the home page on successful login
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout -> logout
                // Redirect to the login page after logging out
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );
        return http.build();
    }
}