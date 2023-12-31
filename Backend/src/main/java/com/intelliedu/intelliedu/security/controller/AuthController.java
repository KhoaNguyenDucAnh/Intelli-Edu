package com.intelliedu.intelliedu.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intelliedu.intelliedu.dto.AccountLogInDto;
import com.intelliedu.intelliedu.dto.AccountRegistrationDto;
import com.intelliedu.intelliedu.security.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
public class AuthController {

  @Autowired
  private AuthService authService;

  @PostMapping("/api/v1/auth/login")
  public void login(@RequestBody @Valid AccountLogInDto accountLogInDto, HttpServletResponse response) {
    authService.login(accountLogInDto, response);
  }

  @GetMapping("/api/v1/auth/logout")
  public void logout(HttpServletResponse response) {
    authService.logout(response);
  }

  //@PostMapping("/api/v1/auth/register")
  public void register(@RequestBody @Valid AccountRegistrationDto accountRegistrationDto) {
    authService.register(accountRegistrationDto);
  }

  @PostMapping("/api/v1/auth/register")
  public void registerWithoutEmailVerification(@RequestBody @Valid AccountRegistrationDto accountRegistrationDto, HttpServletResponse response) {
    authService.registerWithoutEmailVerification(accountRegistrationDto, response);
  }

  @RequestMapping("/activate/{token}")
  public void activate(@PathVariable(value = "token") String token) {
    authService.activateAccount(token);
  }
}
