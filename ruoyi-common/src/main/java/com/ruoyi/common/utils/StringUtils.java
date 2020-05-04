package com.ruoyi.common.utils;

import com.ruoyi.common.core.text.StrFormatter;
import org.apache.commons.collections4.CollectionUtils;

/**
 * 字符串工具类
 *
 * @author ruoyi
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
  /**
   * 下划线
   */
  private static final char SEPARATOR = '_';

  /**
   * 获取参数不为空值
   *
   * @param value defaultValue 要判断的value
   * @return value 返回值
   */
  public static <T> T nvl(T value, T defaultValue) {
    return value != null ? value : defaultValue;
  }

  /**
   * 格式化文本, {} 表示占位符<br>
   * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
   * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
   * 例：<br>
   * 通常使用：format("this is {} for {}", "a", "b") -> this is a for b<br>
   * 转义{}： format("this is \\{} for {}", "a", "b") -> this is \{} for a<br>
   * 转义\： format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
   *
   * @param template 文本模板，被替换的部分用 {} 表示
   * @param params   参数值
   * @return 格式化后的文本
   */
  public static String format(String template, Object... params) {
    if (CollectionUtils.sizeIsEmpty(params) || isEmpty(template)) {
      return template;
    }
    return StrFormatter.format(template, params);
  }

  /**
   * 下划线转驼峰命名
   */
  public static String toUnderScoreCase(String str) {
    if (str == null) {
      return null;
    }
    StringBuilder sb = new StringBuilder();
    // 前置字符是否大写
    boolean preCharIsUpperCase = true;
    // 当前字符是否大写
    boolean curreCharIsUpperCase = true;
    // 下一字符是否大写
    boolean nexteCharIsUpperCase = true;
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      if (i > 0) {
        preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
      } else {
        preCharIsUpperCase = false;
      }

      curreCharIsUpperCase = Character.isUpperCase(c);

      if (i < (str.length() - 1)) {
        nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
      }

      if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase) {
        sb.append(SEPARATOR);
      } else if (i != 0 && !preCharIsUpperCase && curreCharIsUpperCase) {
        sb.append(SEPARATOR);
      }
      sb.append(Character.toLowerCase(c));
    }

    return sb.toString();
  }

  /**
   * 是否包含字符串
   *
   * @param str  验证字符串
   * @param strs 字符串组
   * @return 包含返回true
   */
  public static boolean inStringIgnoreCase(String str, String... strs) {
    if (str != null && strs != null) {
      for (String s : strs) {
        if (str.equalsIgnoreCase(org.apache.commons.lang3.StringUtils.trim(s))) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。 例如：HELLO_WORLD->HelloWorld
   *
   * @param name 转换前的下划线大写方式命名的字符串
   * @return 转换后的驼峰式命名的字符串
   */
  public static String convertToCamelCase(String name) {
    StringBuilder result = new StringBuilder();
    // 快速检查
    if (name == null || name.isEmpty()) {
      // 没必要转换
      return "";
    } else if (!name.contains("_")) {
      // 不含下划线，仅将首字母大写
      return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
    // 用下划线将原始字符串分割
    String[] camels = name.split("_");
    for (String camel : camels) {
      // 跳过原始字符串中开头、结尾的下换线或双重下划线
      if (camel.isEmpty()) {
        continue;
      }
      // 首字母大写
      result.append(camel.substring(0, 1).toUpperCase());
      result.append(camel.substring(1).toLowerCase());
    }
    return result.toString();
  }

  /**
   * 驼峰式命名法 例如：user_name->userName
   */
  public static String toCamelCase(String s) {
    if (s == null) {
      return null;
    }
    s = s.toLowerCase();
    StringBuilder sb = new StringBuilder(s.length());
    boolean upperCase = false;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);

      if (c == SEPARATOR) {
        upperCase = true;
      } else if (upperCase) {
        sb.append(Character.toUpperCase(c));
        upperCase = false;
      } else {
        sb.append(c);
      }
    }
    return sb.toString();
  }

  @SuppressWarnings("unchecked")
  public static <T> T cast(Object obj) {
    return (T) obj;
  }
}