package com.intelliedu.intelliedu.security.service;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.intelliedu.intelliedu.config.Role;
import com.intelliedu.intelliedu.config.SecurityConfig;
import com.intelliedu.intelliedu.dto.AccountLogInDto;
import com.intelliedu.intelliedu.dto.AccountRegistrationDto;
import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.ActivationToken;
import com.intelliedu.intelliedu.mapper.AccountMapper;
import com.intelliedu.intelliedu.repository.AccountRepo;
import com.intelliedu.intelliedu.repository.ActivationTokenRepo;
import com.intelliedu.intelliedu.security.util.JWTUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthService {

  @Autowired
  private JWTUtil jwtUtil;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired 
  private AuthenticationManager authenticationManager;

  @Autowired 
  private AccountRepo accountRepo;

  @Autowired
  private ActivationTokenRepo activationTokenRepo;

  @Autowired
  private AccountMapper accountMapper;

  private Logger logger = LoggerFactory.getLogger(AuthService.class);

  public void authenticateAccount(AccountLogInDto accountLogInDto, HttpServletResponse response) {

    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(accountLogInDto.getEmail(), accountLogInDto.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtil.generateJwtToken(authentication);
    
    try {
      Cookie cookie = new Cookie(SecurityConfig.HEADER_STRING, URLEncoder.encode(SecurityConfig.BEARER_PREFIX + jwt, "UTF-8"));
      
      cookie.setMaxAge((int) SecurityConfig.TOKEN_EXPIRATION_TIME);
      cookie.setPath("/");
      cookie.setHttpOnly(false);
      cookie.setSecure(false);

      response.addCookie(cookie);
      
      logger.info(String.format("User with email %s logged in successfully.", accountLogInDto.getEmail()));
    } catch (UnsupportedEncodingException e) {
      logger.error(e.getMessage());
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error encoding authentication token on the server side.");
    }
  }

  public String registerAccount(AccountRegistrationDto accountRegistrationDto) {
    if (accountRepo.findByEmail(accountRegistrationDto.getEmail()).isPresent()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT);
    }

    if (!accountRegistrationDto.getPassword().equals(accountRegistrationDto.getConfirmPassword())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    Account account = accountMapper.toAccount(accountRegistrationDto);
    
    account.setPassword(passwordEncoder.encode(accountRegistrationDto.getPassword()));
    account.setRole(Role.ROLE_USER);
    account.setIsEnabled(false);

    accountRepo.save(account);

    ActivationToken activationToken = new ActivationToken();

    String token = activationToken.setToken();
    activationToken.setAccount(account);
    activationToken.setExpireDateTime(Timestamp.from(Instant.now().plusMillis(SecurityConfig.TOKEN_EXPIRATION_TIME)));

    activationTokenRepo.save(activationToken);

    return "http://localhost:8080/activate/" + token;
  }

  public void activateAccount(String token) {
    System.out.println(token);

    ActivationToken activationToken = activationTokenRepo
      .findById(token)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    if (activationToken.getExpireDateTime().before(Timestamp.from(Instant.now()))) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    Account account = activationToken.getAccount();

    account.setIsEnabled(true);

    accountRepo.save(account);

    logger.info(String.format("User with email %s registered successfully.", account.getEmail()));
  }

  public Account getAccount(Authentication authentication) {
    return accountRepo
        .findByEmail(((UserDetails) authentication.getPrincipal()).getUsername())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
  }
}
