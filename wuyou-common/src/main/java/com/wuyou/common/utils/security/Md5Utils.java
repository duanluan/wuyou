package com.wuyou.common.utils.security;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Md5加密方法
 *
 * @author wuyou
 */
@Slf4j
public class Md5Utils {

  private static final String CHARSET_NAME = StandardCharsets.UTF_8.name();
  
  private static byte[] md5(String s) {
    MessageDigest algorithm;
    try {
      algorithm = MessageDigest.getInstance("MD5");
      algorithm.reset();
      algorithm.update(s.getBytes(CHARSET_NAME));
      return algorithm.digest();
    } catch (Exception e) {
      log.error("MD5 Error...", e);
    }
    return null;
  }

  private static final String toHex(byte[] hash) {
    if (hash == null) {
      return null;
    }
    StringBuilder buf = new StringBuilder(hash.length * 2);
    int i;

    for (i = 0; i < hash.length; i++) {
      if ((hash[i] & 0xff) < 0x10) {
        buf.append("0");
      }
      buf.append(Long.toString(hash[i] & 0xff, 16));
    }
    return buf.toString();
  }

  public static String hash(String s) {
    try {
      return new String(toHex(md5(s)).getBytes(CHARSET_NAME), CHARSET_NAME);
    } catch (Exception e) {
      log.error("not supported charset...{}", e);
      return s;
    }
  }
}
