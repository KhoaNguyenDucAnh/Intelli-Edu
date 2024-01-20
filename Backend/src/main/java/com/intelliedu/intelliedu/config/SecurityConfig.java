package com.intelliedu.intelliedu.config;

import javax.crypto.SecretKey;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class SecurityConfig {
  public static final String SECRET = "ThisIsAFuckingSecretKeySoPleaseDoNotTouchAtAllCost";
  public static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
  public static final String AUTHORIZATION = "authorization";
  public static final String BEARER_PREFIX = "Bearer ";
	public static final String HMAC_ALGORITHM = "HmacSHA256";
  public static final int ACTIVATION_EXPIRATION_TIME = 60 * 5;
  public static final int TOKEN_EXPIRATION_TIME = 60 * 60 * 24 * 365;
  private static final String[] PERMITALL = new String[] {
    "/favicon.ico",
    "/", "/homepage/.*", "/static/.*", "/manifest.json", "/logo192.png",
    "/login",
    "/register",
    "/assets/.*",
    "/error",
    "/activate/.*", "/api/v1/auth/.*",
    "/api/v1/event/notification/.*"
    //"/.*"
  };
  private static final String[] PERMITALLWITHAUTH = new String[] {
    "/search",
    "/api/v1/(comment|document|mindmap|question)(\\?[^/\\s]*)?",
    "/api/v1/file/check-mindmap/.*"
  };
  public static final String PERMITALL_REGEX = "^(" + String.join("|", PERMITALL) + ")$";
  public static final String PERMITALLWITHAUTH_REGEX = "^(" + String.join("|", PERMITALLWITHAUTH) + ")$";
}
