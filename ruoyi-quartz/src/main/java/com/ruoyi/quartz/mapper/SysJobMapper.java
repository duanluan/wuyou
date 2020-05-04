package com.ruoyi.quartz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.quartz.domain.SysJob;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 调度任务信息 数据层
 *
 * @author ruoyi
 */
public interface SysJobMapper extends BaseMapper<SysJob> {

  /**
   * 分页查询调度任务日志集合
   *
   * @param page 分页对象
   * @param job 调度信息
   * @return 操作日志集合
   */
  List<SysJob> page(Page<SysJob> page, @Param("job") SysJob job);
}
