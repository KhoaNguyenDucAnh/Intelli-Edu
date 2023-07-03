package com.intelliedu.intelliedu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

  @GetMapping("/")
  public String homepage() {
    return "homepage.html";
  }

  @GetMapping("/login")
  public String login() {
    return "login.html";
  }

  @GetMapping("/registration")
  public String registration() {
    return "registration.html";
  }

  @GetMapping("/account")
  public String account() {
    return "account.html";
  }
}
