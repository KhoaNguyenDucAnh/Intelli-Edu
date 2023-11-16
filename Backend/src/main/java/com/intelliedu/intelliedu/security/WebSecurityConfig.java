package com.intelliedu.intelliedu.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.intelliedu.intelliedu.config.SecurityConfig;
import com.intelliedu.intelliedu.security.exception.CustomAuthenticationEntryPoint;
import com.intelliedu.intelliedu.security.filter.JWTFilter;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  @Autowired
  private DaoAuthenticationProvider authenticationProvider;

  @Autowired
  private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
  
  @Autowired
  private JWTFilter jwtFilter;

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf(CsrfConfigurer::disable)
      .authenticationProvider(authenticationProvider)
      .exceptionHandling(exception -> exception.authenticationEntryPoint(customAuthenticationEntryPoint))
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests(auth -> auth
        .requestMatchers(new RequestMatcher() {
          @Override
          public boolean matches(HttpServletRequest request) {
            return request.getRequestURI().matches(SecurityConfig.PERMITALL_REGEX)
                || (request.getMethod().equals("GET") && request.getRequestURI().matches(SecurityConfig.PERMITALLWITHAUTH_REGEX)); 
          }
        }).permitAll()
        .anyRequest().authenticated())
      .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
