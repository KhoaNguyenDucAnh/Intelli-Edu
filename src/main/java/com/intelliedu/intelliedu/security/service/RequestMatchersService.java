package com.intelliedu.intelliedu.security.service;

import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import com.intelliedu.intelliedu.config.SecurityConfig;

import jakarta.servlet.http.HttpServletRequest;

/**
 * RequestMatchersService
 */
@Service
public class RequestMatchersService implements RequestMatcher {

  @Override
  public boolean matches(HttpServletRequest request) {
     return request.getRequestURI().matches(SecurityConfig.PERMITALL_REGEX)
        ||  request.getRequestURI().matches(SecurityConfig.PERMITALLWITHAUTH_REGEX); 
  } 
}
