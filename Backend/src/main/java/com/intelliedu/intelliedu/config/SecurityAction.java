package com.intelliedu.intelliedu.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * SecurityAction
 */
@Getter
@AllArgsConstructor
public enum SecurityAction {

	ACTIVATE("Activate"),
	RESET_PASSWORD("Reset password");

	private final String emailContent;
}
