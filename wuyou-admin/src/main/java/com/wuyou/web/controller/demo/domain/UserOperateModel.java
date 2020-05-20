package com.wuyou.web.controller.demo.domain;

import com.wuyou.common.annotation.Excel;
import com.wuyou.common.annotation.Excel.Type;
import com.wuyou.common.core.domain.BaseEntity;
import com.wuyou.common.utils.DateUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author ry
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserOperateModel extends BaseEntity {

  private static final long serialVersionUID = 1L;

  private int userId;
  /**
   * 用户编号
   */
  @Excel(name = "用户编号")
  private String userCode;
  /**
   * 用户姓名
   */
  @Excel(name = "用户姓名")
  private String userName;
  /**
   * 用户性别
   */
  @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
  private String userSex;
  /**
   * 用户手机
   */
  @Excel(name = "用户手机")
  private String userPhone;
  /**
   * 用户邮箱
   */
  @Excel(name = "用户邮箱")
  private String userEmail;
  /**
   * 用户余额
   */
  @Excel(name = "用户余额")
  private double userBalance;
  /**
   * 用户状态
   */
  @Excel(name = "用户状态", readConverterExp = "0=正常,1=停用")
  private String status;
  /**
   * 创建时间
   */
  @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
  private Date createTime;

  public UserOperateModel() {
  }

  public UserOperateModel(int userId, String userCode, String userName, String userSex, String userPhone, String userEmail, double userBalance, String status) {
    this.userId = userId;
    this.userCode = userCode;
    this.userName = userName;
    this.userSex = userSex;
    this.userPhone = userPhone;
    this.userEmail = userEmail;
    this.userBalance = userBalance;
    this.status = status;
    this.createTime = DateUtils.getNowDate();
  }
}