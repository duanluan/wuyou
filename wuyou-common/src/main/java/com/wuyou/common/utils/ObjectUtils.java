package com.wuyou.common.utils;

/**
 * Object 工具类
 *
 * @author duanluan
 */
public class ObjectUtils extends org.apache.commons.lang3.ObjectUtils {

  private ObjectUtils() {
  }

  /**
   * 大于 0（Integer、Long、Double、Float、Short）
   *
   * @param obj 值
   * @return 是否大于 0
   */
  public static boolean greaterThanZero(Object obj) {
    if (obj instanceof Integer) {
      return (Integer) obj > 0;
    } else if (obj instanceof Long) {
      return (Long) obj > 0;
    } else if (obj instanceof Double) {
      return (Double) obj > 0;
    } else if (obj instanceof Float) {
      return (Float) obj > 0;
    } else if (obj instanceof Short) {
      return (Short) obj > 0;
    }
    return false;
  }
}
