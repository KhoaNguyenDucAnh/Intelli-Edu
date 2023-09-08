package com.intelliedu.intelliedu.security.service;

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
import com.intelliedu.intelliedu.util.EmailUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Optional;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

      logger.info(String.format("Account %s | Login successful", accountLogInDto.getEmail()));
    } catch (AuthenticationException e) {
      logger.error(String.format("Account %s | Login failed", accountLogInDto.getEmail()));
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
					EmailUtil.sendEmail("Reset password");
				} else {
					verifyEmail(account);
				}
			}, () -> {
				Account account = accountMapper.toAccount(accountRegistrationDto);

				account.setPassword(passwordEncoder.encode(accountRegistrationDto.getPassword()));
				account.setRole(Role.ROLE_USER);
				account.setIsEnabled(false);

				logger.info(String.format("Account %s | Register"));

				verifyEmail(account);
			}
		);
	}

	private ActivationToken generateActivationToken(Account account) {
    ActivationToken activationToken = Optional
			.ofNullable(account.getActivationToken())
      .orElse(ActivationToken.builder().account(account).build());

    activationToken.setToken(new HmacUtils(HmacAlgorithms.HMAC_SHA_256, ZonedDateTime.now().toString()).hmacHex(account.getEmail()));
    activationToken.setExpireDateTime(ZonedDateTime.now().plus(Duration.ofMillis(SecurityConfig.ACTIVATION_EXPIRATION_TIME)));

    return activationToken;
	}

	private void verifyEmail(Account account) {
		account.setActivationToken(generateActivationToken(account));
		accountRepo.save(account);

		EmailUtil.sendEmail("Activate: %s", account.getActivationToken().getToken());

		logger.info(String.format("Account %s | Verify email", account.getEmail()));
	}

  public void activateAccount(String token) {
    ActivationToken activationToken = activationTokenRepo
      .findByToken(token)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    if (ZonedDateTime.now().isAfter(activationToken.getExpireDateTime())) {
			activationTokenRepo.deleteById(activationToken.getId());
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

		Account account = activationToken.getAccount();

		account.setIsEnabled(true);

    accountRepo.save(account);

    logger.info(String.format("Account %s | Activate", account.getEmail()));
  }

  public Account getAccount(Authentication authentication) {
    return accountRepo
      .findByEmail(authentication.getPrincipal().toString())
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
  }
}
