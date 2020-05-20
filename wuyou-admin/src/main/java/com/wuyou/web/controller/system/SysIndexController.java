package com.wuyou.web.controller.system;

import com.wuyou.common.config.Global;
import com.wuyou.common.core.controller.BaseController;
import com.wuyou.framework.util.ShiroUtils;
import com.wuyou.system.domain.SysMenu;
import com.wuyou.system.domain.SysUser;
import com.wuyou.system.service.ISysConfigService;
import com.wuyou.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 首页 业务处理
 *
 * @author wuyou
 */
@Controller
public class SysIndexController extends BaseController {

  @Autowired
  private ISysMenuService menuService;
  @Autowired
  private ISysConfigService configService;

  /**
   * 系统首页
   *
   * @param mmap
   * @return
   */
  @GetMapping("/index")
  public String index(ModelMap mmap) {
    // 取身份信息
    SysUser user = ShiroUtils.getSysUser();
    // 根据用户id取出菜单
    List<SysMenu> menus = menuService.listByUser(user);
    mmap.put("menus", menus);
    mmap.put("user", user);
    mmap.put("sideTheme", configService.getValueByKey("sys.index.sideTheme"));
    mmap.put("skinName", configService.getValueByKey("sys.index.skinName"));
    mmap.put("copyrightYear", Global.getCopyrightYear());
    mmap.put("demoEnabled", Global.isDemoEnabled());
    return "index";
  }

  /**
   * 切换主题
   *
   * @param mmap
   * @return
   */
  @GetMapping("/system/switchSkin")
  public String switchSkin(ModelMap mmap) {
    return "skin";
  }

  /**
   * 系统介绍
   *
   * @param mmap
   * @return
   */
  @GetMapping("/system/main")
  public String main(ModelMap mmap) {
    mmap.put("version", Global.getVersion());
    mmap.put("user", ShiroUtils.getSysUser());
    return "main";
  }
}
