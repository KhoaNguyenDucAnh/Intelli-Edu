package com.intelliedu.intelliedu.security.util;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import jakarta.servlet.http.Cookie;

/**
 * CookieUtil
 */
public class CookieUtil {

	public static String getCookie(Cookie[] cookies, String name) {
		if (cookies == null) {
			return null;
		}

		return URLDecoder
			.decode(
				Arrays
					.stream(cookies)
					.filter(cookie -> name.equals(cookie.getName()))
					.findFirst()
				.orElse(new Cookie(name, ""))
				.getValue(),
				StandardCharsets.UTF_8
		);
	}

  public static Cookie generateCookie(String key, String value, int age) {
    Cookie cookie = new Cookie(key, value);
    cookie.setMaxAge(age);
    cookie.setPath("/");
    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    return cookie;
  }
}
