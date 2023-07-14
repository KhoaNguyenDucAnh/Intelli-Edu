package com.intelliedu.intelliedu.service;

import com.intelliedu.intelliedu.dto.AccountResponseDto;
import org.springframework.security.core.Authentication;

public interface AccountService {

  public AccountResponseDto getAccountInfo(Authentication authentication);
}
