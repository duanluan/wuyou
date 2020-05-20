package com.wuyou.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyou.system.domain.SysRoleMenu;
import com.wuyou.system.mapper.SysRoleMenuMapper;
import com.wuyou.system.service.ISysRoleMenuService;
import org.springframework.stereotype.Service;

/**
 * 角色和部门业务层处理
 *
 * @author duanluan
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {
}
