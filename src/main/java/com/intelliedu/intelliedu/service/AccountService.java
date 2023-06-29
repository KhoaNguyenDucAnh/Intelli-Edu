package com.intelliedu.intelliedu.service;

import com.intelliedu.intelliedu.dto.AccountDto;

public interface AccountService {

  public void registerAccount(AccountDto accountDto);

  public void loginAccount(AccountDto accountDto);
}
