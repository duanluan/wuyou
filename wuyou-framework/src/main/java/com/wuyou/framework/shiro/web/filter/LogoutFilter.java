package com.wuyou.framework.shiro.web.filter;

import com.wuyou.common.constant.Constants;
import com.wuyou.common.constant.ShiroConstants;
import com.wuyou.common.utils.MessageUtils;
import com.wuyou.framework.manager.AsyncManager;
import com.wuyou.framework.manager.factory.AsyncFactory;
import com.wuyou.framework.util.ShiroUtils;
import com.wuyou.system.domain.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Deque;

/**
 * 退出过滤器
 *
 * @author wuyou
 */
@Slf4j
public class LogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter {
  /**
   * 退出后重定向的地址
   */
  private String loginUrl;
  private Cache<String, Deque<Serializable>> cache;

  public String getLoginUrl() {
    return loginUrl;
  }

  public void setLoginUrl(String loginUrl) {
    this.loginUrl = loginUrl;
  }

  @Override
  protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
    try {
      Subject subject = getSubject(request, response);
      String redirectUrl = getRedirectUrl(request, response, subject);
      try {
        SysUser user = ShiroUtils.getSysUser();
        if (user != null) {
          String loginName = user.getLoginName();
          // 记录用户退出日志
          AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGOUT, MessageUtils.message("user.logout.success")));
          // 清理缓存
          cache.remove(loginName);
        }
        // 退出登录
        subject.logout();
      } catch (SessionException ise) {
        log.error("logout fail.", ise);
      }
      issueRedirect(request, response, redirectUrl);
    } catch (Exception e) {
      log.error("Encountered session exception during logout.  This can generally safely be ignored.", e);
    }
    return false;
  }

  /**
   * 退出跳转URL
   */
  @Override
  protected String getRedirectUrl(ServletRequest request, ServletResponse response, Subject subject) {
    String url = getLoginUrl();
    if (StringUtils.isNotEmpty(url)) {
      return url;
    }
    return super.getRedirectUrl(request, response, subject);
  }

  /**
   * 设置Cache的key的前缀
   *
   * @param cacheManager
   */
  public void setCacheManager(CacheManager cacheManager) {
    // 必须和ehcache缓存配置中的缓存name一致
    this.cache = cacheManager.getCache(ShiroConstants.SYS_USERCACHE);
  }
}