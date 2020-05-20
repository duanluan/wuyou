package com.wuyou.common.core.text;


import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 字符集工具类
 *
 * @author wuyou
 */
public class CharsetKit {
  /**
   * GBK
   */
  private static final String GBK_STRING = "GBK";
  /**
   * GBK
   */
  public static final Charset GBK = Charset.forName(GBK_STRING);

  /**
   * 转换为Charset对象
   *
   * @param charset 字符集，为空则返回默认字符集
   * @return Charset
   */
  public static Charset charset(String charset) {
    return StringUtils.isEmpty(charset) ? Charset.defaultCharset() : Charset.forName(charset);
  }

  /**
   * 转换字符串的字符集编码
   *
   * @param source      字符串
   * @param srcCharset  源字符集，默认ISO-8859-1
   * @param destCharset 目标字符集，默认UTF-8
   * @return 转换后的字符集
   */
  public static String convert(String source, String srcCharset, String destCharset) {
    return convert(source, Charset.forName(srcCharset), Charset.forName(destCharset));
  }

  /**
   * 转换字符串的字符集编码
   *
   * @param source      字符串
   * @param srcCharset  源字符集，默认ISO-8859-1
   * @param destCharset 目标字符集，默认UTF-8
   * @return 转换后的字符集
   */
  public static String convert(String source, Charset srcCharset, Charset destCharset) {
    if (null == srcCharset) {
      srcCharset = StandardCharsets.ISO_8859_1;
    }

    if (null == destCharset) {
      srcCharset = StandardCharsets.UTF_8;
    }

    if (StringUtils.isEmpty(source) || srcCharset.equals(destCharset)) {
      return source;
    }
    return new String(source.getBytes(srcCharset), destCharset);
  }

  /**
   * @return 系统字符集编码
   */
  public static String systemCharset() {
    return Charset.defaultCharset().name();
  }
}
