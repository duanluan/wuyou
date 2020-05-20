package com.wuyou.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.wuyou.common.annotation.Excel;
import com.wuyou.common.annotation.Excel.ColumnType;
import com.wuyou.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 参数配置表 sys_config
 *
 * @author wuyou
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysConfig extends BaseEntity {

  private static final long serialVersionUID = 1L;

  /**
   * 参数主键
   */
  @Excel(name = "参数主键", cellType = ColumnType.NUMERIC)
  @TableId
  private Long configId;
  /**
   * 参数名称
   */
  @Excel(name = "参数名称")
  @Size(max = 100, message = "参数名称不能超过100个字符")
  @NotBlank(message = "参数名称不能为空")
  private String configName;
  /**
   * 参数键名
   */
  @Excel(name = "参数键名")
  @NotBlank(message = "参数键名长度不能为空")
  @Size(max = 100, message = "参数键名长度不能超过100个字符")
  private String configKey;
  /**
   * 参数键值
   */
  @Excel(name = "参数键值")
  @NotBlank(message = "参数键值不能为空")
  @Size(max = 500, message = "参数键值长度不能超过500个字符")
  private String configValue;
  /**
   * 系统内置（Y是 N否）
   */
  @Excel(name = "系统内置", readConverterExp = "Y=是,N=否")
  private String configType;
}
