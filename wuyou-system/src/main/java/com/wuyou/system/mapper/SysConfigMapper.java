package com.wuyou.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuyou.system.domain.SysConfig;
import org.apache.ibatis.annotations.Param;

/**
 * 参数配置 数据层
 *
 * @author wuyou
 */
public interface SysConfigMapper extends BaseMapper<SysConfig> {
  /**
   * 分页列出配置
   *
   * @param page   分页对象
   * @param config 配置
   * @return
   */
  IPage<SysConfig> page(Page<SysConfig> page, @Param("config") SysConfig config);
}