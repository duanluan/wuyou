package com.wuyou.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.wuyou.common.annotation.Excel;
import com.wuyou.common.annotation.Excel.ColumnType;
import com.wuyou.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 角色表 sys_role
 *
 * @author wuyou
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysRole extends BaseEntity {

  private static final long serialVersionUID = 1L;

  /**
   * 角色ID
   */
  @Excel(name = "角色序号", cellType = ColumnType.NUMERIC)
  @TableId
  private Long roleId;
  /**
   * 角色名称
   */
  @Excel(name = "角色名称")
  @Size(max = 30, message = "角色名称长度不能超过30个字符")
  @NotBlank(message = "角色名称不能为空")
  private String roleName;
  /**
   * 角色权限
   */
  @Excel(name = "角色权限")
  @Size(max = 100, message = "权限字符长度不能超过100个字符")
  @NotBlank(message = "权限字符不能为空")
  private String roleKey;
  /**
   * 角色排序
   */
  @Excel(name = "角色排序", cellType = ColumnType.NUMERIC)
  @NotBlank(message = "显示顺序不能为空")
  private String roleSort;
  /**
   * 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限）
   */
  @Excel(name = "数据范围", readConverterExp = "1=所有数据权限,2=自定义数据权限,3=本部门数据权限,4=本部门及以下数据权限")
  private Integer dataScope;
  /**
   * 角色状态（0正常 1停用）
   */
  @Excel(name = "角色状态", readConverterExp = "0=正常,1=停用")
  private Integer status;
  /**
   * 删除标志（0代表存在 2代表删除）
   */
  @TableLogic
  private Boolean delFlag;
  /**
   * 用户是否存在此角色标识 默认不存在
   */
  @TableField(exist = false)
  private boolean flag = false;
  /**
   * 菜单组
   */
  @TableField(exist = false)
  private Long[] menuIds;
  /**
   * 部门组（数据权限）
   */
  @TableField(exist = false)
  private Long[] deptIds;

  public SysRole() {
  }

  public SysRole(Long roleId) {
    this.roleId = roleId;
  }

  public boolean isAdmin() {
    return isAdmin(this.roleId);
  }

  public static boolean isAdmin(Long roleId) {
    return roleId != null && 1L == roleId;
  }
}
