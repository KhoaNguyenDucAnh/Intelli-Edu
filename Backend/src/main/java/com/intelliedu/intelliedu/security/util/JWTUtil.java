package com.intelliedu.intelliedu.security.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;

import com.intelliedu.intelliedu.config.SecurityConfig;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JWTUtil {

  public static String generateJwtToken(Authentication authentication) {
    User userPrincipal = (User) authentication.getPrincipal();
    return Jwts.builder()
      .subject(userPrincipal.getUsername())
      .issuedAt(Date.from(Instant.now())) 
      .expiration(Date.from(Instant.now().plus(SecurityConfig.TOKEN_EXPIRATION_TIME,ChronoUnit.SECONDS)))
      .signWith(SecurityConfig.SECRET_KEY)
      .compact();
  }

  public static boolean validateJwtToken(String jws) {
    try {
      Jwts.parser().verifyWith(SecurityConfig.SECRET_KEY).build().parseSignedClaims(jws);
      return true;
    } catch (JwtException e) {
      log.error(""); 
    }
    return false;
  }

	public static String parseJwt(HttpServletRequest request) {
		String token = CookieUtil.getCookie(request.getCookies(), SecurityConfig.AUTHORIZATION); 
    if (StringUtils.hasText(token) && token.startsWith(SecurityConfig.BEARER_PREFIX)) {
      return token.substring(SecurityConfig.BEARER_PREFIX.length());
    }
    return null;
  }

	public static String getUserNameFromJwtToken(String jws) {
    return Jwts.parser().verifyWith(SecurityConfig.SECRET_KEY).build().parseSignedClaims(jws).getPayload().getSubject();
  }
}
