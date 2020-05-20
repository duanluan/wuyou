package com.wuyou.framework.manager.factory;

import com.wuyou.common.constant.Constants;
import com.wuyou.common.utils.AddressUtils;
import com.wuyou.common.utils.ServletUtils;
import com.wuyou.common.utils.spring.SpringUtils;
import com.wuyou.framework.shiro.session.OnlineSession;
import com.wuyou.framework.util.LogUtils;
import com.wuyou.framework.util.ShiroUtils;
import com.wuyou.system.domain.SysLogininfor;
import com.wuyou.system.domain.SysOperLog;
import com.wuyou.system.domain.SysUserOnline;
import com.wuyou.system.service.ISysOperLogService;
import com.wuyou.system.service.ISysUserOnlineService;
import com.wuyou.system.service.impl.SysLogininforServiceImpl;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

/**
 * 异步工厂（产生任务用）
 *
 * @author liuhulu
 */
public class AsyncFactory {

  private static final Logger SYS_USER_LOGGER = LoggerFactory.getLogger("sys-user");

  /**
   * 同步session到数据库
   *
   * @param session 在线用户会话
   * @return 任务task
   */
  public static TimerTask syncSessionToDb(final OnlineSession session) {
    return new TimerTask() {
      @Override
      public void run() {
        SysUserOnline online = new SysUserOnline();
        online.setSessionId(String.valueOf(session.getId()));
        online.setDeptName(session.getDeptName());
        online.setLoginName(session.getLoginName());
        online.setStartTimestamp(session.getStartTimestamp());
        online.setLastAccessTime(session.getLastAccessTime());
        online.setExpireTime(session.getTimeout());
        online.setIpaddr(session.getHost());
        online.setLoginLocation(AddressUtils.getRealAddressByIp(session.getHost()));
        online.setBrowser(session.getBrowser());
        online.setOs(session.getOs());
        online.setStatus(session.getStatus());
        SpringUtils.getBean(ISysUserOnlineService.class).saveOnline(online);

      }
    };
  }

  /**
   * 操作日志记录
   *
   * @param operLog 操作日志信息
   * @return 任务task
   */
  public static TimerTask recordOper(final SysOperLog operLog) {
    return new TimerTask() {
      @Override
      public void run() {
        // 远程查询操作地点
        operLog.setOperLocation(AddressUtils.getRealAddressByIp(operLog.getOperIp()));
        SpringUtils.getBean(ISysOperLogService.class).insert(operLog);
      }
    };
  }

  /**
   * 记录登录信息
   *
   * @param username 用户名
   * @param status   状态
   * @param message  消息
   * @param args     列表
   * @return 任务task
   */
  public static TimerTask recordLogininfor(final String username, final String status, final String message, final Object... args) {
    final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
    final String ip = ShiroUtils.getIp();
    return new TimerTask() {
      @Override
      public void run() {
        String address = AddressUtils.getRealAddressByIp(ip);
        StringBuilder s = new StringBuilder();
        s.append(LogUtils.getBlock(ip));
        s.append(address);
        s.append(LogUtils.getBlock(username));
        s.append(LogUtils.getBlock(status));
        s.append(LogUtils.getBlock(message));
        // 打印信息到日志
        SYS_USER_LOGGER.info(s.toString(), args);
        // 获取客户端操作系统
        String os = userAgent.getOperatingSystem().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        // 封装对象
        SysLogininfor logininfor = new SysLogininfor();
        logininfor.setLoginName(username);
        logininfor.setIpaddr(ip);
        logininfor.setLoginLocation(address);
        logininfor.setBrowser(browser);
        logininfor.setOs(os);
        logininfor.setMsg(message);
        // 日志状态
        if (StringUtils.equalsAny(status, Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER)) {
          logininfor.setStatus(Constants.SUCCESS);
        } else if (Constants.LOGIN_FAIL.equals(status)) {
          logininfor.setStatus(Constants.FAIL);
        }
        // 插入数据
        SpringUtils.getBean(SysLogininforServiceImpl.class).save(logininfor);
      }
    };
  }
}
