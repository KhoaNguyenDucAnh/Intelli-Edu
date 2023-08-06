package com.intelliedu.intelliedu.config;

public class SecurityConfig {
  public static final String SECRET = "ThisIsAFuckingSecretKeySoPleaseDoNotTouchAtAllCost";
  public static final String LOGIN_URL = "/api/v1/account/login";
  public static final String HEADER_STRING = "Authorization";
  public static final String BEARER_PREFIX = "Bearer ";
  public static final long ACTIVATION_EXPIRATION_TIME = (long) (1000 * 60 * 5);
  public static final long TOKEN_EXPIRATION_TIME = (long) (1000 * 60 * 60 * 24 * 365);
  public static final String BAD_REQUEST = "";
  public static final String[] PERMITALL = new String[] {"/", "/homepage.*", "/login.*", "/register.*", "/activate.*", "/api/v1/auth/.*", "/error", "/favicon.ico"};
  public static final String PERMITALL_REGEX = "^(" + String.join("|", PERMITALL) + ")$";
  public static final String[] PERMITALLWITHAUTH = new String[] {"/api/v1/mindmap/find/.*"};
  public static final String PERMITALLWITHAUTH_REGEX = "^(" + String.join("|", PERMITALLWITHAUTH) + ")$";
}
