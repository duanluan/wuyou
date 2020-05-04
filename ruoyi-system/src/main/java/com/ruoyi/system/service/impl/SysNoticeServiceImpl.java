package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.SysNotice;
import com.ruoyi.system.mapper.SysNoticeMapper;
import com.ruoyi.system.service.ISysNoticeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 公告 服务层实现
 *
 * @author ruoyi
 * @date 2018-06-25
 */
@Service
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements ISysNoticeService {

  @Autowired
  private SysNoticeMapper noticeMapper;

  @Override
  public IPage<SysNotice> page(Page<SysNotice> page, SysNotice notice) {
    return page.setRecords(noticeMapper.page(page, notice));
  }

  /**
   * 查询公告列表
   *
   * @param notice 公告信息
   * @return 公告集合
   */
  @Override
  public List<SysNotice> list(SysNotice notice) {
    return noticeMapper.page(null, notice);
  }

  /**
   * 删除公告对象
   *
   * @param ids 需要删除的数据ID
   * @return 结果
   */
  @Override
  public boolean removeByIds(String ids) {
    return super.removeByIds(Arrays.asList(StringUtils.split(ids)));
  }
}
