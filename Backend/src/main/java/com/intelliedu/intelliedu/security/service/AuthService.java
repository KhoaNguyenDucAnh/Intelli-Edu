package com.intelliedu.intelliedu.security.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Optional;

import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
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
import com.intelliedu.intelliedu.mapper.AccountMapper;
import com.intelliedu.intelliedu.repository.AccountRepo;
import com.intelliedu.intelliedu.repository.SecurityTokenRepo;
import com.intelliedu.intelliedu.security.util.JWTUtil;
import com.intelliedu.intelliedu.util.EmailUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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
	private SecurityTokenRepo securityTokenRepo;

  @Autowired
	private AccountMapper accountMapper;

  public void authenticateAccount(AccountLogInDto accountLogInDto, HttpServletResponse response) {
    try {
      Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(accountLogInDto.getEmail(), accountLogInDto.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(authentication);

      Cookie cookie = new Cookie(
        SecurityConfig.AUTHORIZATION,
        URLEncoder.encode(SecurityConfig.BEARER_PREFIX + jwtUtil.generateJwtToken(authentication),StandardCharsets.UTF_8)
			);

      cookie.setMaxAge((int) SecurityConfig.TOKEN_EXPIRATION_TIME);
      cookie.setPath("/");
      cookie.setHttpOnly(false);
      cookie.setSecure(false);

      response.addCookie(cookie);

      log.info(String.format("Account %s | Login successful", accountLogInDto.getEmail()));
    } catch (AuthenticationException e) {
      log.error(String.format("Account %s | Login failed", accountLogInDto.getEmail()));
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
  }

  public void registerAccount(AccountRegistrationDto accountRegistrationDto) {
    if (!EmailUtil.validateEmail(accountRegistrationDto.getEmail())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

		if (!accountRegistrationDto.getPassword().equals(accountRegistrationDto.getConfirmPassword())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

		accountRepo
			.findByEmail(accountRegistrationDto.getEmail())
			.ifPresentOrElse((account) -> {
					if (account.isEnabled()) {
						email(account, SecurityAction.RESET_PASSWORD);
					} else {
						email(account, SecurityAction.ACTIVATE);
					}
				},
				() -> {
					Account account = generateAccount(accountRegistrationDto, Role.ROLE_USER);

					log.info(String.format("Account %s | Register", account.getEmail()));

					email(account, SecurityAction.ACTIVATE);
				}
			);
	}

	private Account generateAccount(AccountRegistrationDto accountRegistrationDto, Role role) {
		Account account = accountMapper.toAccount(accountRegistrationDto);

		account.setPassword(passwordEncoder.encode(accountRegistrationDto.getPassword()));
		account.setRole(role);
		account.setIsEnabled(true);

		return accountRepo.save(account);
	}

	private void email(Account account, SecurityAction securityAction) {
		account.setSecurityToken(generateSecurityToken(account, securityAction));
		accountRepo.save(account);

		EmailUtil.sendEmail("%s: %s", securityAction.getEmailContent(), account.getSecurityToken());

		log.info(String.format("Account %s | %s", account.getEmail(), securityAction.toString()));
	}

	private SecurityToken generateSecurityToken(Account account, SecurityAction securityAction) {
    SecurityToken securityToken = Optional
			.ofNullable(account.getSecurityToken())
      .orElse(SecurityToken.builder().account(account).build());

		securityToken.setSecurityAction(securityAction);
    securityToken.setToken(new HmacUtils(HmacAlgorithms.HMAC_SHA_256, ZonedDateTime.now().toString()).hmacHex(account.getEmail()));
    securityToken.setExpireDateTime(ZonedDateTime.now().plus(Duration.ofMillis(SecurityConfig.ACTIVATION_EXPIRATION_TIME)));

    return securityToken;
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

		account.setIsEnabled(true);

    accountRepo.save(account);

    log.info(String.format("Account %s | Activate", account.getEmail()));
  }

  public Account getAccount(Authentication authentication) {
    return accountRepo
      .findByEmail(authentication.getPrincipal().toString())
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
  }
}
