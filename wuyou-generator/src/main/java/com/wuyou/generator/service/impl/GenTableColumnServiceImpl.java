package com.wuyou.generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyou.generator.domain.GenTableColumn;
import com.wuyou.generator.mapper.GenTableColumnMapper;
import com.wuyou.generator.service.IGenTableColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 业务字段 服务层实现
 *
 * @author wuyou
 */
@Service
public class GenTableColumnServiceImpl extends ServiceImpl<GenTableColumnMapper, GenTableColumn> implements IGenTableColumnService {

  @Autowired
  private GenTableColumnMapper genTableColumnMapper;
}