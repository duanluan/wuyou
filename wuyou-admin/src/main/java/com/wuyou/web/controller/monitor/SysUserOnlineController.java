package com.wuyou.web.controller.monitor;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuyou.common.annotation.Log;
import com.wuyou.common.core.controller.BaseController;
import com.wuyou.common.core.domain.Result;
import com.wuyou.common.enums.BusinessType;
import com.wuyou.common.enums.OnlineStatus;
import com.wuyou.framework.shiro.session.OnlineSession;
import com.wuyou.framework.shiro.session.OnlineSessionDAO;
import com.wuyou.framework.util.ShiroUtils;
import com.wuyou.system.domain.SysUserOnline;
import com.wuyou.system.service.ISysUserOnlineService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.wuyou.common.core.domain.Result.error;
import static com.wuyou.common.core.domain.Result.success;

/**
 * 在线用户监控
 *
 * @author wuyou
 */
@RequestMapping("/monitor/online")
@Controller
public class SysUserOnlineController extends BaseController {

  private static final String PREFIX = "monitor/online";

  @Autowired
  private ISysUserOnlineService userOnlineService;
  @Autowired
  private OnlineSessionDAO onlineSessionDAO;

  @RequiresPermissions("monitor:online:view")
  @GetMapping
  public String online() {
    return PREFIX + "/online";
  }

  @RequiresPermissions("monitor:online:list")
  @ResponseBody
  @PostMapping("/list")
  public Result list(Page<SysUserOnline> page, SysUserOnline userOnline) {
    return success(userOnlineService.page(page, userOnline));
  }

  /**
   * 1、删除了 forceLogout 方法
   * 2、将 batchForceLogout 和 forceLogout 的权限逻辑 改成了 OR【按需要设定】
   * 3、@RequestParam("ids[]") ==> @RequestParam("ids")
   * 4、开源拥有者 可以斟酌一下
   * @param ids
   * @return
   */
  @RequiresPermissions(value = {"monitor:online:batchForceLogout","monitor:online:forceLogout"},logical = Logical.OR)
  @Log(title = "在线用户", businessType = BusinessType.FORCE)
  @ResponseBody
  @PostMapping("/batchForceLogout")
  public Result batchForceLogout(@RequestParam("ids") String[] ids) {
    for (String sessionId : ids) {
      SysUserOnline online = userOnlineService.getById(sessionId);
      if (online == null) {
        return error("用户已下线");
      }
      OnlineSession onlineSession = (OnlineSession) onlineSessionDAO.readSession(online.getSessionId());
      if (onlineSession == null) {
        return error("用户已下线");
      }
      if (sessionId.equals(ShiroUtils.getSessionId())) {
        return error("当前登录用户无法强退");
      }
      onlineSession.setStatus(OnlineStatus.off_line);
      onlineSessionDAO.update(onlineSession);
      online.setStatus(OnlineStatus.off_line);
      userOnlineService.saveOnline(online);
    }
    return success();
  }
}
