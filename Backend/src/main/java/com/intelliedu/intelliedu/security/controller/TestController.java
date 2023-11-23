package com.intelliedu.intelliedu.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intelliedu.intelliedu.dto.AccountLogInDto;
import com.intelliedu.intelliedu.security.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;

/**
 * TestController
 */
@RestController
@Profile("test")
public class TestController {

  @Autowired
  private AuthService authService;

  @GetMapping("/api/v1/auth/admin")
  public void admin(HttpServletResponse response) {
    authService.login(new AccountLogInDto("admin", "admin"), response);
  }

  @GetMapping("/api/v1/auth/nimda")
  public void nimda(HttpServletResponse response) {
    authService.login(new AccountLogInDto("nimda", "nimda"), response);
  }
}
