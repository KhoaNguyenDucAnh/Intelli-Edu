package com.intelliedu.intelliedu.security.component;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * CustomAuthenticationToken
 */
public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {

	public CustomAuthenticationToken(UserDetails userDetails, Object details) {
		super(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
		super.setDetails(details);
	}	
}
