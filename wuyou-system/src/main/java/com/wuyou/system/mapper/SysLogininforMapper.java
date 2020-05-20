package com.wuyou.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuyou.system.domain.SysLogininfor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统访问日志情况信息 数据层
 *
 * @author wuyou
 */
public interface SysLogininforMapper extends BaseMapper<SysLogininfor> {
  /**
   * 分页列出访问日志
   *
   * @param page       分页对象
   * @param logininfor 访问日志
   * @return
   */
  List<SysLogininfor> page(Page<SysLogininfor> page, @Param("logininfor") SysLogininfor logininfor);

  /**
   * 清空系统登录日志
   *
   * @return 结果
   */
  int cleanLogininfor();
}
