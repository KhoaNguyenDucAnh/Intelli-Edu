package com.intelliedu.intelliedu.util;

import java.time.ZonedDateTime;
import java.util.UUID;

import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

/**
 * Hash
 */
public class HashUtil {

  public static String HMACSHA256(String content) {
    return new HmacUtils(HmacAlgorithms.HMAC_SHA_256, ZonedDateTime.now().toString()).hmacHex(content);
  }

  public static String HMACSHA256() {
    return HMACSHA256("");
  }

  public static String UUID() {
    return UUID.randomUUID().toString();
  }
}
