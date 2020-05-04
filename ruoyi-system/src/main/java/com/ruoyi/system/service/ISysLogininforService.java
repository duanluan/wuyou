package com.ruoyi.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.SysLogininfor;

import java.util.List;

/**
 * 系统访问日志情况信息 服务层
 *
 * @author ruoyi
 */
public interface ISysLogininforService extends IService<SysLogininfor> {
  /**
   * 分页列出登录日志
   *
   * @param page       分页对象
   * @param logininfor 登录日志
   * @return
   */
  IPage<SysLogininfor> page(Page<SysLogininfor> page, SysLogininfor logininfor);

  /**
   * 查询系统登录日志集合
   *
   * @param logininfor 访问日志对象
   * @return 登录记录集合
   */
  List<SysLogininfor> list(SysLogininfor logininfor);

  /**
   * 批量删除系统登录日志
   *
   * @param ids 需要删除的数据
   * @return
   */
  boolean removeByIds(String ids);

  /**
   * 清空系统登录日志
   */
  void cleanLogininfor();
}
