package com.intelliedu.intelliedu.security.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import com.intelliedu.intelliedu.config.SecurityConfig;
import com.intelliedu.intelliedu.security.util.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

  @Autowired 
  private JWTUtil jwtUtil;

  @Autowired 
  private UserDetailsService userDetailsService;

  private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    return request.getRequestURI().matches(SecurityConfig.PERMITALL_REGEX);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    try {
      String jwt = parseJwt(request);
      if (jwt != null && jwtUtil.validateJwtToken(jwt)) {
        String username = jwtUtil.getUserNameFromJwtToken(jwt);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {

    }
    filterChain.doFilter(request, response);
  }

  private String parseJwt(HttpServletRequest request) {
    String undecodedAuth = "", auth = "";
    Cookie[] cookies = request.getCookies();
    
    if (cookies == null) {
      return null;
    }
    
    try {
      for (Cookie cookie : cookies) {
        if (SecurityConfig.HEADER_STRING.equals(cookie.getName())) {
          undecodedAuth = cookie.getValue();
          auth = URLDecoder.decode(undecodedAuth, "UTF-8");
          break;
        }
      }
    } catch (UnsupportedEncodingException e) {
      logger.error("Failed to decode authentication token with value %s: UnsupportedEncodingException occurred.", undecodedAuth);
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error decoding authentication token on the server side.");
    }

    if (StringUtils.hasText(auth) && auth.startsWith(SecurityConfig.BEARER_PREFIX)) {
      return auth.substring(SecurityConfig.BEARER_PREFIX.length());
    }

    return null;
  }
}
