package com.intelliedu.intelliedu.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * SecurityAction
 */
@Getter
@AllArgsConstructor
public enum SecurityAction {

	ACTIVATE("Send activation email", "Activate"),
	RESET_PASSWORD("Send reset password email", "Reset password");

  private final String log;

	private final String emailContent;
}
