package com.ruoyi.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.SysOperLog;

import java.util.List;

/**
 * 操作日志 服务层
 *
 * @author ruoyi
 */
public interface ISysOperLogService extends IService<SysOperLog> {
  /**
   * 新增操作日志
   *
   * @param operLog 操作日志对象
   */
  void insert(SysOperLog operLog);

  IPage<SysOperLog> page(Page<SysOperLog> page, SysOperLog operLog);

  /**
   * 查询系统操作日志集合
   *
   * @param operLog 操作日志对象
   * @return 操作日志集合
   */
  List<SysOperLog> list(SysOperLog operLog);

  /**
   * 批量删除系统操作日志
   *
   * @param ids 需要删除的数据
   * @return 结果
   */
  boolean removeByIds(String ids);

  /**
   * 清空操作日志
   */
  void clean();
}
