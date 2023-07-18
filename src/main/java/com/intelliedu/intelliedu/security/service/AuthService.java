package com.intelliedu.intelliedu.security.service;

import com.intelliedu.intelliedu.config.AccountConfig;
import com.intelliedu.intelliedu.config.Role;
import com.intelliedu.intelliedu.config.SecurityConfig;
import com.intelliedu.intelliedu.dto.AccountLogInDto;
import com.intelliedu.intelliedu.dto.AccountRegistrationDto;
import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.mapper.AccountMapper;
import com.intelliedu.intelliedu.repository.AccountRepo;
import com.intelliedu.intelliedu.security.util.JWTUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService implements UserDetailsService {

  @Autowired private AuthenticationManager authenticationManager;

  @Autowired private JWTUtil jwtUtil;

  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired private AccountMapper accountMapper;

  @Autowired private AccountRepo accountRepo;

  private Logger logger = LoggerFactory.getLogger(AuthService.class);

  public void authenticateAccount(AccountLogInDto accountLogInDto, HttpServletResponse response) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                accountLogInDto.getEmail(), accountLogInDto.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtil.generateJwtToken(authentication);
    try {
      Cookie cookie =
          new Cookie(
              SecurityConfig.HEADER_STRING,
              URLEncoder.encode(SecurityConfig.BEARER_PREFIX + jwt, "UTF-8"));
      cookie.setMaxAge((int) SecurityConfig.EXPIRATION_TIME);
      cookie.setPath("/");
      cookie.setHttpOnly(false);
      cookie.setSecure(false);

      response.addCookie(cookie);
      response.sendRedirect("/account");

      logger.info(
          String.format("User with email %s logged in successfully.", accountLogInDto.getEmail()));
    } catch (UnsupportedEncodingException e) {
      logger.error(e.getMessage());
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Error encoding authentication token on the server side.");
    } catch (IOException e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public void registerAccount(AccountRegistrationDto accountRegistrationDto) {
    if (accountRepo.findByEmail(accountRegistrationDto.getEmail()).isPresent()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, AccountConfig.CONFLICT);
    }

    Account account = accountMapper.toAccount(accountRegistrationDto);
    account.setPassword(passwordEncoder.encode(accountRegistrationDto.getPassword()));
    account.setRole(Role.ROLE_USER);

    accountRepo.save(account);

    logger.info(
        String.format(
            "User with email %s registered successfully.", accountRegistrationDto.getEmail()));
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Account account =
        accountRepo
            .findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(AccountConfig.NOT_FOUND));

    boolean enabled = true;
    boolean accountNonExpired = true;
    boolean credentialsNonExpired = true;
    boolean accountNonLocked = true;

    return new org.springframework.security.core.userdetails.User(
        account.getEmail(),
        account.getPassword(),
        enabled,
        accountNonExpired,
        credentialsNonExpired,
        accountNonLocked,
        account.getAuthorities());
  }

  public Account getAccount(Authentication authentication) {
    return accountRepo
        .findByEmail(((UserDetails) authentication.getPrincipal()).getUsername())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
  }
}
