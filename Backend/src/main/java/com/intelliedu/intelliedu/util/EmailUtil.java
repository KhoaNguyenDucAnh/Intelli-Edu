package com.intelliedu.intelliedu.util;

/**
 * EmailUtil
 */
public class EmailUtil {

	public static void sendEmail(String template, Object... args) {
		System.out.println(String.format(template, args));
	}
}
