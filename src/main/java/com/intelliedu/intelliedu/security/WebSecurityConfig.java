package com.intelliedu.intelliedu.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf((csrf) -> csrf.disable())
        .formLogin((login) -> login
            .loginPage("/login.html")
            .loginProcessingUrl("/api/v1/use/login")
            .defaultSuccessUrl("/", false))
        .authorizeHttpRequests((request) -> request
            .anyRequest().permitAll());
    return http.build();
  }
}
