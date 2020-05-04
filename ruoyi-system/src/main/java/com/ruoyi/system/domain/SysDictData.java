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
 * 字典数据表 sys_dict_data
 *
 * @author ruoyi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysDictData extends BaseEntity {

  private static final long serialVersionUID = 1L;

  /**
   * 字典编码
   */
  @Excel(name = "字典编码", cellType = ColumnType.NUMERIC)
  @TableId
  private Long dictCode;
  /**
   * 字典排序
   */
  @Excel(name = "字典排序", cellType = ColumnType.NUMERIC)
  private Long dictSort;
  /**
   * 字典标签
   */
  @Excel(name = "字典标签")
  @Size(min = 0, max = 100, message = "字典标签长度不能超过100个字符")
  @NotBlank(message = "字典标签不能为空")
  private String dictLabel;
  /**
   * 字典键值
   */
  @Excel(name = "字典键值")
  @Size(min = 0, max = 100, message = "字典键值长度不能超过100个字符")
  @NotBlank(message = "字典键值不能为空")
  private String dictValue;
  /**
   * 字典类型
   */
  @Excel(name = "字典类型")
  @Size(min = 0, max = 100, message = "字典类型长度不能超过100个字符")
  @NotBlank(message = "字典类型不能为空")
  private String dictType;
  /**
   * 样式属性（其他样式扩展）
   */
  @Excel(name = "字典样式")
  @Size(min = 0, max = 100, message = "样式属性长度不能超过100个字符")
  private String cssClass;
  /**
   * 表格字典样式
   */
  private String listClass;
  /**
   * 是否默认（Y是 N否）
   */
  @Excel(name = "是否默认", readConverterExp = "Y=是,N=否")
  private String isDefault;
  /**
   * 状态（0正常 1停用）
   */
  @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
  private Integer status;
}
