package com.wuyou.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyou.system.domain.SysUserPost;
import com.wuyou.system.mapper.SysUserPostMapper;
import com.wuyou.system.service.ISysUserPostService;
import org.springframework.stereotype.Service;

/**
 * 角色和部门业务层处理
 *
 * @author duanluan
 */
@Service
public class SysUserPostServiceImpl extends ServiceImpl<SysUserPostMapper, SysUserPost> implements ISysUserPostService {
}
