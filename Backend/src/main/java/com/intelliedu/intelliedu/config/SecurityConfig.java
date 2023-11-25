package com.intelliedu.intelliedu.config;

import javax.crypto.SecretKey;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class SecurityConfig {
  public static final String SECRET = "ThisIsAFuckingSecretKeySoPleaseDoNotTouchAtAllCost";
  public static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
  public static final String AUTHORIZATION = "Authorization";
  public static final String BEARER_PREFIX = "Bearer ";
	public static final String HMAC_ALGORITHM = "HmacSHA256";
  public static final long ACTIVATION_EXPIRATION_TIME = (long) (1000 * 60 * 5);
  public static final long TOKEN_EXPIRATION_TIME = (long) (1000 * 60 * 60 * 24 * 5);
  private static final String[] PERMITALL = new String[] {
    "/favicon.ico",
    "/", "/homepage/.*", "/static/.*", "/manifest.json", "/logo192.png",
    "/login",
    "/register",
    "/error",
    "/activate/.*", "/api/v1/auth/.*",
  };
  private static final String[] PERMITALLWITHAUTH = new String[] {
    "/search",
    "/api/v1/(comment|document|mindmap|question)(\\?[^/\\s]*)?"
  };
  public static final String PERMITALL_REGEX = "^(" + String.join("|", PERMITALL) + ")$";
  public static final String PERMITALLWITHAUTH_REGEX = "^(" + String.join("|", PERMITALLWITHAUTH) + ")$";
}
