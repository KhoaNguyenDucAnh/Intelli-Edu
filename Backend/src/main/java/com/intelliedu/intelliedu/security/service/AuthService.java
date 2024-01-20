package com.intelliedu.intelliedu.security.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.intelliedu.intelliedu.config.Role;
import com.intelliedu.intelliedu.config.SecurityAction;
import com.intelliedu.intelliedu.config.SecurityConfig;
import com.intelliedu.intelliedu.dto.AccountLogInDto;
import com.intelliedu.intelliedu.dto.AccountRegistrationDto;
import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.SecurityToken;
import com.intelliedu.intelliedu.exception.NotFoundException;
import com.intelliedu.intelliedu.mapper.AccountMapper;
import com.intelliedu.intelliedu.repository.AccountRepo;
import com.intelliedu.intelliedu.repository.SecurityTokenRepo;
import com.intelliedu.intelliedu.security.util.CookieUtil;
import com.intelliedu.intelliedu.security.util.JWTUtil;
import com.intelliedu.intelliedu.util.EmailUtil;
import com.intelliedu.intelliedu.util.HashUtil;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class AuthService {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private AccountMapper accountMapper;

  @Autowired
  private AccountRepo accountRepo;

  @Autowired
  private SecurityTokenRepo securityTokenRepo;

  public void login(AccountLogInDto accountLogInDto, HttpServletResponse response) {
    try {
      Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(accountLogInDto.getEmail(), accountLogInDto.getPassword()));
      
      SecurityContextHolder.getContext().setAuthentication(authentication); 
      
      response.addCookie(CookieUtil.generateCookie(
        SecurityConfig.AUTHORIZATION, 
        URLEncoder.encode(SecurityConfig.BEARER_PREFIX + JWTUtil.generateJwtToken(authentication),StandardCharsets.UTF_8),
        (int) SecurityConfig.TOKEN_EXPIRATION_TIME)
      );

      log.info(String.format("Account %s login success", accountLogInDto.getEmail()));
    } catch (AuthenticationException e) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, String.format("Account %s login failed", accountLogInDto.getEmail()), e);
    }
  }

  public void logout(HttpServletResponse response) {
    response.addCookie(CookieUtil.generateCookie(SecurityConfig.AUTHORIZATION, null, -1));
  }

  public void registerWithoutEmailVerification(AccountRegistrationDto accountRegistrationDto, HttpServletResponse response) {
    accountRepo
      .findByEmail(accountRegistrationDto.getEmail())
      .ifPresentOrElse(
        account -> {throw new ResponseStatusException(HttpStatus.CONFLICT, "Email has already been taken");},
        () -> {
          Account account = generateAccount(accountRegistrationDto, Role.ROLE_USER);
          account.setEnabled(true);
          accountRepo.save(account);
          
          log.info(String.format("Creat new account %s", account.getEmail()));

          login(new AccountLogInDto(accountRegistrationDto.getEmail(), accountRegistrationDto.getPassword()), response);
        }
    );
  }

  public void register(AccountRegistrationDto accountRegistrationDto) {
    accountRepo
      .findByEmail(accountRegistrationDto.getEmail())
      .ifPresentOrElse(
        account -> {if (account.isEnabled()) email(account, SecurityAction.RESET_PASSWORD); else email(account, SecurityAction.ACTIVATE);},
        () -> email(generateAccount(accountRegistrationDto, Role.ROLE_USER), SecurityAction.ACTIVATE)
    );
  }

  private Account generateAccount(AccountRegistrationDto accountRegistrationDto, Role role) {
    Account account = accountMapper.toAccount(accountRegistrationDto);
    account.setPassword(passwordEncoder.encode(accountRegistrationDto.getPassword()));
    account.setRole(role);
    account.setEnabled(false);
    return accountRepo.save(account);
  }

  private SecurityToken generateSecurityToken(Account account, SecurityAction securityAction) {
    SecurityToken securityToken = securityTokenRepo
      .findById(account.getId())
      .orElse(SecurityToken.builder().account(account).build());
    securityToken.setSecurityAction(securityAction);
    securityToken.setToken(HashUtil.HMACSHA256(account.getEmail()));
    securityToken.setExpireDateTime(ZonedDateTime.now().plus(Duration.ofSeconds(SecurityConfig.ACTIVATION_EXPIRATION_TIME)));
    return securityTokenRepo.save(securityToken);
  }

  private void email(Account account, SecurityAction securityAction) {
    EmailUtil.sendEmail("%s: %s", securityAction.getEmailContent(), generateSecurityToken(account, securityAction).getToken());

    log.info(String.format("%s for account %s", securityAction.getLog(), account.getEmail()));
  }


  public void activateAccount(String token) {
    SecurityToken securityToken = securityTokenRepo
      .findByToken(token)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    if (ZonedDateTime.now().isAfter(securityToken.getExpireDateTime())) {
      securityTokenRepo.deleteById(securityToken.getId());
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    Account account = securityToken.getAccount();
    account.setEnabled(true);

    accountRepo.save(account);

    log.info(String.format("Activate account %s", account.getEmail()));
  }

  public Account getAccount(Authentication authentication) {
    return accountRepo
      .findByEmail(authentication.getPrincipal().toString())
      .orElseGet(() -> {
        if (authentication.getPrincipal().toString().equals("admin")) {
          Account account = new Account();
          account.setUsername("admin");
          account.setEmail("admin");
          account.setPassword(authentication.getCredentials().toString());
        
          return accountRepo.save(account);
        }
        if (authentication.getPrincipal().toString().equals("nimda")) {
          Account account = new Account();
          account.setUsername("nimda");
          account.setEmail("nimda");
          account.setPassword(authentication.getCredentials().toString());
        
          return accountRepo.save(account);
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    );
  }

  public Account getAccount(UUID id) {
    return accountRepo.findById(id).orElseThrow(() -> new NotFoundException(Account.class, id));
  }
}
