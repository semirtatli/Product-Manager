package com.product_manager.ws.config;

import com.product_manager.ws.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF as we are using JWT (stateless)
                .csrf(csrf -> csrf.disable())

                // Set session management to stateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/1.0/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/1.0/users").permitAll()
                        .anyRequest().authenticated()
                )



                // Add the JWT filter before the default authentication filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);



        return http.build();
    }
}
