package com.wuyou.web.controller.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuyou.common.annotation.Log;
import com.wuyou.common.core.controller.BaseController;
import com.wuyou.common.core.domain.Result;
import com.wuyou.common.enums.BusinessType;
import com.wuyou.framework.util.ShiroUtils;
import com.wuyou.system.domain.SysNotice;
import com.wuyou.system.service.ISysNoticeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import static com.wuyou.common.core.domain.Result.custom;

/**
 * 公告 信息操作处理
 *
 * @author wuyou
 */
@RequestMapping("/system/notice")
@Controller
public class SysNoticeController extends BaseController {

  private static final String PREFIX = "system/notice";

  @Autowired
  private ISysNoticeService noticeService;

  @RequiresPermissions("system:notice:view")
  @GetMapping
  public String notice() {
    return PREFIX + "/notice";
  }

  /**
   * 查询公告列表
   */
  @RequiresPermissions("system:notice:list")
  @ResponseBody
  @PostMapping("/list")
  public Result list(Page<SysNotice> page, SysNotice notice) {
    return Result.success(noticeService.page(page, notice));
  }

  /**
   * 新增公告
   */
  @GetMapping("/add")
  public String add() {
    return PREFIX + "/add";
  }

  /**
   * 新增保存公告
   */
  @Log(title = "通知公告", businessType = BusinessType.INSERT)
  @RequiresPermissions("system:notice:add")
  @ResponseBody
  @PostMapping("/add")
  public Result addSave(SysNotice notice) {
    notice.setCreateBy(ShiroUtils.getLoginName());
    return custom(noticeService.save(notice));
  }

  /**
   * 修改公告
   */
  @GetMapping("/edit/{noticeId}")
  public String edit(@PathVariable("noticeId") Long noticeId, ModelMap mmap) {
    mmap.put("notice", noticeService.getById(noticeId));
    return PREFIX + "/edit";
  }

  /**
   * 修改保存公告
   */
  @Log(title = "通知公告", businessType = BusinessType.UPDATE)
  @RequiresPermissions("system:notice:edit")
  @ResponseBody
  @PostMapping("/edit")
  public Result editSave(SysNotice notice) {
    notice.setUpdateBy(ShiroUtils.getLoginName());
    return custom(noticeService.updateById(notice));
  }

  /**
   * 删除公告
   */
  @Log(title = "通知公告", businessType = BusinessType.DELETE)
  @RequiresPermissions("system:notice:remove")
  @ResponseBody
  @DeleteMapping
  public Result remove(String ids) {
    return custom(noticeService.removeByIds(ids));
  }
}
