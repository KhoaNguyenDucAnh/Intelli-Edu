package com.intelliedu.intelliedu.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.intelliedu.intelliedu.config.SecurityConfig;
import com.intelliedu.intelliedu.dto.AccountLogInDto;
import com.intelliedu.intelliedu.dto.AccountRegistrationDto;
import com.intelliedu.intelliedu.security.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = SecurityConfig.EXPIRATION_TIME)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  @Autowired
  private AuthService authService;

  @PostMapping("/authenticate")
  public void authenticateUser(@Valid @RequestBody AccountLogInDto accountLogInDto, HttpServletResponse response) {
    authService.authenticateAccount(accountLogInDto, response);
  }

  @PostMapping("/register")
  public void registerAccount(@Valid @RequestBody AccountRegistrationDto accountRegistrationDto) {
    authService.registerAccount(accountRegistrationDto);
  }
}
