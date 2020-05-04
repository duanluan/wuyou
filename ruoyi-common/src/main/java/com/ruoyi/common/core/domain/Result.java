package com.ruoyi.common.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;

/**
 * 响应结果
 *
 * @author duanluan
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Result extends HashMap<String, Object> {

  private static final long serialVersionUID = 1L;

  /**
   * 编码
   */
  private int code;
  /**
   * 消息
   */
  private String msg;
  /**
   * 内容
   */
  private Object data;


  public static final String CODE = "code";
  public static final String MSG = "msg";
  public static final String DATA = "data";

  /**
   * 获取响应结果
   *
   * @param resultEnum 响应枚举
   * @return 响应结果
   */
  private static Result getResult(ResultEnum resultEnum) {
    Result result = new Result();
    result.put(CODE, resultEnum.getCode());
    result.put(MSG, resultEnum.getMsg());
    return result;
  }

  /**
   * 获取响应结果
   *
   * @param resultEnum 响应枚举
   * @param data       内容
   * @return 响应结果
   */
  private static Result getResult(ResultEnum resultEnum, Object data) {
    Result result = new Result();
    result.put(CODE, resultEnum.getCode());
    result.put(MSG, resultEnum.getMsg());
    result.put(DATA, data);
    return result;
  }

  /**
   * 成功
   *
   * @return 响应结果
   */
  public static Result success() {
    return getResult(ResultEnum.SUCCESS);
  }

  /**
   * 成功
   *
   * @param data 内容
   * @return 响应结果
   */
  public static Result success(Object data) {
    return getResult(ResultEnum.SUCCESS, data);
  }

  /**
   * 错误
   *
   * @return 响应结果
   */
  public static Result error() {
    return getResult(ResultEnum.ERROR);
  }

  /**
   * 错误
   *
   * @param msg 消息
   * @return 响应结果
   */
  public static Result error(String msg) {
    return getResult(ResultEnum.ERROR.customMsg(msg));
  }

  /**
   * 错误
   *
   * @param e 异常
   * @return 响应结果
   */
  public static Result error(Exception e) {
    return Result.error(e.getMessage());
  }

  /**
   * 参数错误
   *
   * @return 响应结果
   */
  public static Result paramError() {
    return getResult(ResultEnum.PARAM_ERROR);
  }

  /**
   * 自定义
   *
   * @param result 结果
   * @return 响应结果
   */
  public static Result custom(Boolean result) {
    return result ? success() : error();
  }

  /**
   * 自定义
   *
   * @param result 结果
   * @param data 内容
   * @return 响应结果
   */
  public static Result custom(Boolean result, Object data) {
    return result ? success(data) : error();
  }

  /**
   * 自定义
   *
   * @param rows 受影响行数
   * @return 响应结果
   */
  public static Result custom(int rows) {
    return rows > 0 ? success() : error();
  }

  /**
   * 自定义
   *
   * @param rows 受影响行数
   * @param data 内容
   * @return 响应结果
   */
  public static Result custom(int rows, Object data) {
    return rows > 0 ? success(data) : error();
  }

  /**
   * 自定义
   *
   * @param code 编码
   * @param msg  消息
   * @param data 内容
   * @return 响应结果
   */
  public static Result custom(int code, String msg, Object data) {
    Result result = new Result();
    result.put(CODE, code);
    result.put(MSG, msg);
    result.put(DATA, data);
    return result;
  }

  /**
   * 自定义
   *
   * @param resultEnum 响应枚举
   * @return 响应结果
   */
  public static Result custom(ResultEnum resultEnum) {
    return getResult(resultEnum);
  }

  /**
   * 自定义前后缀
   *
   * @param resultEnum 响应枚举
   * @return 响应结果
   */
  public static Result custom(ResultEnum resultEnum, String prefix, String suffix) {
    resultEnum.customMsg(prefix + resultEnum.getMsg() + suffix);
    return getResult(resultEnum);
  }

  /**
   * 自定义前缀
   *
   * @param resultEnum 响应枚举
   * @param prefix     前缀
   * @return 响应结果
   */
  public static Result customPrefix(ResultEnum resultEnum, String prefix) {
    resultEnum.customMsg(prefix + resultEnum.getMsg());
    return getResult(resultEnum);
  }

  /**
   * 自定义后缀
   *
   * @param resultEnum 响应枚举
   * @param suffix     后缀
   * @return 响应结果
   */
  public static Result customSuffix(ResultEnum resultEnum, String suffix) {
    resultEnum.customMsg(resultEnum.getMsg() + suffix);
    return getResult(resultEnum);
  }

  /**
   * 响应枚举
   */
  public enum ResultEnum {
    /**
     * 防止警告
     */
    SUCCESS(0x000000, "成功"),
    ERROR(0x000001, "系统错误"),
    PARAM_ERROR(0x000101, "参数错误"),

    ;

    /**
     * 编码
     */
    private int code;
    /**
     * 消息
     */
    private String msg;

    ResultEnum(int code, String msg) {
      this.code = code;
      this.msg = msg;
    }

    public int getCode() {
      return code;
    }

    public void setCode(int code) {
      this.code = code;
    }

    public String getMsg() {
      return msg;
    }

    public void setMsg(String msg) {
      this.msg = msg;
    }

    /**
     * 自定义消息
     *
     * @param msg 消息
     * @return 响应枚举
     */
    protected ResultEnum customMsg(String msg) {
      setMsg(msg);
      return this;
    }

    /**
     * 自定义
     *
     * @param code 编码
     * @param msg  消息
     * @return 响应枚举
     */
    protected ResultEnum custom(int code, String msg) {
      setCode(code);
      setMsg(msg);
      return this;
    }
  }
}
