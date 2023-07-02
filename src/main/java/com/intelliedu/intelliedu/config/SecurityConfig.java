package com.intelliedu.intelliedu.config;

public class SecurityConfig {
  public static final String SECRET = "SECRET";
  public static final String LOGIN_URL = "/api/v1/account/login";
  public static final String HEADER_STRING = "Authorization";
  public static final String BEARER_PREFIX = "Bearer ";
  public static final Long EXPIRATION_TIME = (long) (1000 * 60 * 60 * 24 * 365);
  public static final String BAD_REQUEST = "";
}
