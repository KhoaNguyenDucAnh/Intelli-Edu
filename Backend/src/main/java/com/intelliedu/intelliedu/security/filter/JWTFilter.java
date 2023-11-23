package com.intelliedu.intelliedu.security.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.intelliedu.intelliedu.config.SecurityConfig;
import com.intelliedu.intelliedu.security.util.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter {

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
    String jwt = jwtUtil.parseJwt(request);

    if (jwt != null && jwtUtil.validateJwtToken(jwt)) {
      try {
        SecurityContextHolder.getContext().setAuthentication(
          new CustomAuthenticationToken(userDetailsService.loadUserByUsername(jwtUtil.getUserNameFromJwtToken(jwt)),
          new WebAuthenticationDetailsSource().buildDetails(request))
				);
      } catch (UsernameNotFoundException e) {

			}
    }

    filterChain.doFilter(request, response);
  }
}

class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {

	public CustomAuthenticationToken(UserDetails userDetails, Object details) {
		super(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
		super.setDetails(details);
	}	
}
