package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.SysOperLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 操作日志 数据层
 *
 * @author ruoyi
 */
public interface SysOperLogMapper extends BaseMapper<SysOperLog> {
  /**
   * 分页列出操作日志
   *
   * @param page    分页对象
   * @param operLog 操作日志
   * @return
   */
  List<SysOperLog> page(Page<SysOperLog> page, @Param("operLog") SysOperLog operLog);

  /**
   * 清空操作日志
   */
  void clean();
}
