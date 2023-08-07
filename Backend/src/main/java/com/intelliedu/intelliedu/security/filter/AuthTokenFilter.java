package com.intelliedu.intelliedu.security.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

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

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    return request.getRequestURI().matches(SecurityConfig.PERMITALL_REGEX);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String jwt = parseJwt(request);
      
		if (jwt != null && jwtUtil.validateJwtToken(jwt)) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtil.getUserNameFromJwtToken(jwt));
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
    } 

    filterChain.doFilter(request, response);
  }

  private String parseJwt(HttpServletRequest request) {
    String token = "";
    Cookie[] cookies = request.getCookies();
    
    if (cookies == null) {
      return null;
    }
    
    for (Cookie cookie : cookies) {
			if (SecurityConfig.HEADER_STRING.equals(cookie.getName())) {
        token = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
        break;
      }
    }

    if (StringUtils.hasText(token) && token.startsWith(SecurityConfig.BEARER_PREFIX)) {
      return token.substring(SecurityConfig.BEARER_PREFIX.length());
    }

    return null;
  }
}
