package com.intelliedu.intelliedu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intelliedu.intelliedu.dto.AccountResponseDto;
import com.intelliedu.intelliedu.service.AccountService;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

  @Autowired
	private AccountService accountService;

  @GetMapping("")
  public AccountResponseDto getAccountInfo(Authentication authentication) {
    return accountService.getAccountInfo(authentication);
  } 
}
