package com.intelliedu.intelliedu.security.service;

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
import com.intelliedu.intelliedu.config.AccountConfig;
import com.intelliedu.intelliedu.config.Role;
import com.intelliedu.intelliedu.config.SecurityConfig;
import com.intelliedu.intelliedu.dto.AccountLogInDto;
import com.intelliedu.intelliedu.dto.AccountRegistrationDto;
import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.mapper.AccountMapper;
import com.intelliedu.intelliedu.repository.AccountRepo;
import com.intelliedu.intelliedu.security.TokenUtil.JWTUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthService implements UserDetailsService {
  
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JWTUtil jwtUtil;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private AccountMapper accountMapper;

  @Autowired
  private AccountRepo accountRepo;

  public void authenticateAccount(AccountLogInDto accountLogInDto, HttpServletResponse response) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(accountLogInDto.getUsername(), accountLogInDto.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtil.generateJwtToken(authentication);
    
    // Account userDetails = (Account) authentication.getPrincipal();    
    // List<String> roles = userDetails.getAuthorities().stream()
    //     .map(GrantedAuthority::getAuthority)
    //     .collect(Collectors.toList());

    Cookie cookie = new Cookie(SecurityConfig.HEADER_STRING, jwt);
    cookie.setHttpOnly(false);
    cookie.setSecure(false);

    response.addCookie(cookie);
  }

  public void registerAccount(AccountRegistrationDto accountRegistrationDto) {
    if (accountRepo.findByUsername(accountRegistrationDto.getUsername()).isPresent()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, AccountConfig.CONFLICT);
    }

    Account account = accountMapper.toAccount(accountRegistrationDto);
    account.setPassword(passwordEncoder.encode(accountRegistrationDto.getPassword()));
    account.setRole(Role.ROLE_USER);

    accountRepo.save(account);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    System.out.println(username);

    Account account = accountRepo.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(AccountConfig.NOT_FOUND));

    boolean enabled = true;
    boolean accountNonExpired = true;
    boolean credentialsNonExpired = true;
    boolean accountNonLocked = true;

    return new org.springframework.security.core.userdetails.User(
        account.getUsername(), account.getPassword(), enabled, accountNonExpired,
        credentialsNonExpired, accountNonLocked,
        account.getAuthorities());
  }
}
