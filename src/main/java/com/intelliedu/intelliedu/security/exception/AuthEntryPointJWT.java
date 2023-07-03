package com.intelliedu.intelliedu.security.exception;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthEntryPointJWT implements AuthenticationEntryPoint {

  private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJWT.class);

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {
    logger.error(String.format("Unauthorized access attempted. Request URL: %s. IP Address: %s.", request.getRequestURL(), request.getRemoteAddr()));
    // response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
    response.sendRedirect("/login");
  }
}
