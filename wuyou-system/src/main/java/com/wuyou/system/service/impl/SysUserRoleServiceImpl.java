package com.wuyou.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyou.system.domain.SysUserRole;
import com.wuyou.system.mapper.SysUserRoleMapper;
import com.wuyou.system.service.ISysUserRoleService;
import org.springframework.stereotype.Service;

/**
 * 角色和部门业务层处理
 *
 * @author duanluan
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {
}
