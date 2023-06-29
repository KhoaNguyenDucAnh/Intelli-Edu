package com.intelliedu.intelliedu.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.intelliedu.intelliedu.config.AccountConfig;
import com.intelliedu.intelliedu.dto.AccountDto;
import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.mapper.AccountMapper;
import com.intelliedu.intelliedu.repository.AccountRepo;
import com.intelliedu.intelliedu.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private AccountMapper accountMapper;

  @Autowired
  private AccountRepo accountRepo;

  @Override
  public void registerAccount(AccountDto accountDto) {
    if (accountRepo.findByUsername(accountDto.getUsername()).isPresent()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, AccountConfig.CONFLICT);
    }

    Account account = accountMapper.toAccount(accountDto);
    account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
    account.setRole(Arrays.asList("USER"));

    accountRepo.save(account);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Account account = accountRepo.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(AccountConfig.NOT_FOUND));

    boolean enabled = true;
    boolean accountNonExpired = true;
    boolean credentialsNonExpired = true;
    boolean accountNonLocked = true;

    return new org.springframework.security.core.userdetails.User(
        account.getEmail(), account.getPassword(), enabled, accountNonExpired,
        credentialsNonExpired, accountNonLocked, getAuthorities(account.getRole()));
  }

  private static List<GrantedAuthority> getAuthorities(List<String> roles) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    for (String role : roles) {
      authorities.add(new SimpleGrantedAuthority(role));
    }
    return authorities;
  }

  @Override
  public void loginAccount(AccountDto accountDto) {

  }
}
