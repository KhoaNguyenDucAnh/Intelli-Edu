package com.intelliedu.intelliedu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

  @GetMapping({"/", "/login", "/register", "/search"})
  public String homepage() {
    return "forward:/homepage/build/index.html";
  }

  @GetMapping("/mindmap")
  public String mindmap() {
    return "forward:/mindmap/dist/index.html";
  }
}
