package com.intelliedu.intelliedu.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.intelliedu.intelliedu.dto.AccountLogInDto;
import com.intelliedu.intelliedu.dto.AccountRegistrationDto;
import com.intelliedu.intelliedu.dto.AccountResponseDto;
import com.intelliedu.intelliedu.security.service.AuthService;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthService authService;

  @PostMapping("/signin")
  public AccountResponseDto authenticateUser(@Valid @RequestBody AccountLogInDto accountLogInDto) {
    return authService.authenticateAccount(accountLogInDto);
  }

  @PostMapping("/register")
  public void registerAccount(@RequestBody @Valid AccountRegistrationDto accountRegistrationDto) {
    authService.registerAccount(accountRegistrationDto);
  }
}
