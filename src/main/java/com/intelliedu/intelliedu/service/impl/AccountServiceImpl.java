package com.intelliedu.intelliedu.service.impl;

import com.intelliedu.intelliedu.dto.AccountResponseDto;
import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.mapper.AccountMapper;
import com.intelliedu.intelliedu.repository.AccountRepo;
import com.intelliedu.intelliedu.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AccountServiceImpl implements AccountService {

  @Autowired private AccountRepo accountRepo;

  @Autowired private AccountMapper accountMapper;

  public AccountResponseDto getAccountInfo(Authentication authentication) {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    Account account =
        accountRepo
            .findByEmail(userDetails.getUsername())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    return accountMapper.toAccountDResponseDto(account);
  }
}
