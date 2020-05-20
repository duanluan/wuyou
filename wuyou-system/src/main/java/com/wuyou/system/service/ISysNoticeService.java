package com.wuyou.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wuyou.system.domain.SysNotice;

import java.util.List;

/**
 * 公告 服务层
 *
 * @author wuyou
 */
public interface ISysNoticeService extends IService<SysNotice> {
  /**
   * 分页列出公告
   *
   * @param page   分页对象
   * @param notice 公告
   * @return
   */
  IPage<SysNotice> page(Page<SysNotice> page, SysNotice notice);

  /**
   * 查询公告列表
   *
   * @param notice 公告信息
   * @return 公告集合
   */
  List<SysNotice> list(SysNotice notice);

  /**
   * 删除公告信息
   *
   * @param ids 需要删除的数据ID
   * @return 结果
   */
  boolean removeByIds(String ids);
}
