package com.intelliedu.intelliedu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.intelliedu.intelliedu.dto.AccountResponseDto;
import com.intelliedu.intelliedu.mapper.AccountMapper;
import com.intelliedu.intelliedu.repository.AccountRepo;
import com.intelliedu.intelliedu.security.service.AuthService;
import com.intelliedu.intelliedu.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

  @Autowired
  private AccountRepo accountRepo;

  @Autowired
  private AccountMapper accountMapper;

  @Autowired
  private AuthService authService;

  public AccountResponseDto getAccountInfo(Authentication authentication) {
    return accountMapper.toAccountResponseDto(authService.getAccount(authentication));
  }
}
