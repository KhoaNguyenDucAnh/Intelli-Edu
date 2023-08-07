package com.intelliedu.intelliedu.security.util;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.intelliedu.intelliedu.config.SecurityConfig;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JWTUtil {

  private static final Logger logger = LoggerFactory.getLogger(JWTUtil.class);
	
	private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SecurityConfig.SECRET));
  }
  
	public String generateJwtToken(Authentication authentication) {

    User userPrincipal = (User) authentication.getPrincipal();

    return Jwts.builder()
        .setSubject((userPrincipal.getUsername()))
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + SecurityConfig.TOKEN_EXPIRATION_TIME))
        .signWith(key(), SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
      return true;
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }
    return false;
  }

	public String parseJwt(HttpServletRequest request) {
    String token = URLDecoder
			.decode(
				Arrays
					.stream(request.getCookies())
					.filter(cookie -> SecurityConfig.HEADER_STRING.equals(cookie.getName()))
					.findFirst()
					.orElseThrow(() -> new NoSuchElementException())
				.getValue(),
				StandardCharsets.UTF_8
			);

    if (StringUtils.hasText(token) && token.startsWith(SecurityConfig.BEARER_PREFIX)) {
      return token.substring(SecurityConfig.BEARER_PREFIX.length());
    }

    return null;
  }

	public String getUserNameFromJwtToken(String token) {
    return Jwts.parserBuilder().setSigningKey(key()).build()
               .parseClaimsJws(token).getBody().getSubject();
  }
}
