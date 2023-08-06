package com.intelliedu.intelliedu.controller;

import com.intelliedu.intelliedu.dto.AccountResponseDto;
import com.intelliedu.intelliedu.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account")
@ResponseStatus(code = HttpStatus.OK)
public class AccountController {

  @Autowired private AccountService accountService;

  @GetMapping("")
  public AccountResponseDto getAccountInfo(Authentication authentication) {
    return accountService.getAccountInfo(authentication);
  } 
}
