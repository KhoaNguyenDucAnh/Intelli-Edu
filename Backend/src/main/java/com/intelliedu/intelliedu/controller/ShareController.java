package com.intelliedu.intelliedu.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.intelliedu.intelliedu.service.EventService;

/**
 * ShareController
 */
@Controller
@RequestMapping("/share")
public class ShareController {

  @Autowired
  private EventService eventService;

  @GetMapping("/event/{id}")
  public String addSharedEvent(@PathVariable UUID id, Authentication authentication) {
    eventService.addSharedEvent(id, authentication);
    return "forward:/todo-list/dist/index.html";
  }
}
