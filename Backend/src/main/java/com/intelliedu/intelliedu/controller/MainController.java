package com.intelliedu.intelliedu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

  @GetMapping("/")
  public String homepage() {
    return "forward:homepage.html";
  }

  @GetMapping("/login")
  public String login() {
    return "forward:login.html";
  }

  @GetMapping("/registration")
  public String registration() {
    return "forward:registration.html";
  }

  @GetMapping("/account")
  public String account() {
    return "forward:account.html";
  }
}
