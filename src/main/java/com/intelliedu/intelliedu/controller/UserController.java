package com.intelliedu.intelliedu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.intelliedu.intelliedu.dto.UserDto;
import com.intelliedu.intelliedu.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
@ResponseStatus(code = HttpStatus.OK)
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping("")
  public void registerUser(@RequestBody @Valid UserDto userDto) {
    userService.registerUser(userDto);
  }
}
