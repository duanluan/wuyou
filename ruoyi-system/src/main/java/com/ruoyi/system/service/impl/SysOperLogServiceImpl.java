package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.SysOperLog;
import com.ruoyi.system.mapper.SysOperLogMapper;
import com.ruoyi.system.service.ISysOperLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 操作日志 服务层处理
 *
 * @author ruoyi
 */
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements ISysOperLogService {

  @Autowired
  private SysOperLogMapper operLogMapper;

  /**
   * 新增操作日志
   *
   * @param operLog 操作日志对象
   */
  @Override
  public void insert(SysOperLog operLog) {
    super.save(operLog);
  }

  @Override
  public IPage<SysOperLog> page(Page<SysOperLog> page, SysOperLog operLog) {
    return page.setRecords(operLogMapper.page(page, operLog));
  }

  /**
   * 查询系统操作日志集合
   *
   * @param operLog 操作日志对象
   * @return 操作日志集合
   */
  @Override
  public List<SysOperLog> list(SysOperLog operLog) {
    return operLogMapper.page(null, operLog);
  }

  /**
   * 批量删除系统操作日志
   *
   * @param ids 需要删除的数据
   * @return
   */
  @Override
  public boolean removeByIds(String ids) {
    return super.removeByIds(Arrays.asList(StringUtils.split(ids, ",")));
  }

  /**
   * 清空操作日志
   */
  @Override
  public void clean() {
    operLogMapper.clean();
  }
}
