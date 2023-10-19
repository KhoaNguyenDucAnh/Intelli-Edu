package com.intelliedu.intelliedu.util;

import java.time.ZonedDateTime;

import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

/**
 * Hash
 */
public class HashUtil {

  public static String timeBasedHash(String content) {
    return new HmacUtils(HmacAlgorithms.HMAC_SHA_256, ZonedDateTime.now().toString()).hmacHex(content);
  }

  public static String timeBasedHash() {
    return timeBasedHash("");
  }
}
