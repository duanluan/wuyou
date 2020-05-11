package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 字典类型表 sys_dict_type
 *
 * @author ruoyi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysDictType extends BaseEntity {

  private static final long serialVersionUID = 1L;

  /**
   * 字典主键
   */
  @Excel(name = "字典主键", cellType = ColumnType.NUMERIC)
  @TableId
  private Long dictId;
  /**
   * 字典名称
   */
  @Excel(name = "字典名称")
  @Size(max = 100, message = "字典类型名称长度不能超过100个字符")
  @NotBlank(message = "字典名称不能为空")
  private String dictName;
  /**
   * 字典类型
   */
  @Excel(name = "字典类型")
  @Size(max = 100, message = "字典类型类型长度不能超过100个字符")
  @NotBlank(message = "字典类型不能为空")
  private String dictType;
  /**
   * 状态（0正常 1停用）
   */
  @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
  private Integer status;
}
