package com.wuyou.web.controller.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuyou.common.annotation.Log;
import com.wuyou.common.core.controller.BaseController;
import com.wuyou.common.core.domain.Result;
import com.wuyou.common.enums.BusinessType;
import com.wuyou.common.utils.poi.ExcelUtil;
import com.wuyou.framework.util.ShiroUtils;
import com.wuyou.system.domain.SysPost;
import com.wuyou.system.service.ISysPostService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.wuyou.common.core.domain.Result.custom;
import static com.wuyou.common.core.domain.Result.error;

/**
 * 岗位信息操作处理
 *
 * @author wuyou
 */
@RequestMapping("/system/post")
@Controller
public class SysPostController extends BaseController {

  private static final String PREFIX = "system/post";

  @Autowired
  private ISysPostService postService;

  @RequiresPermissions("system:post:view")
  @GetMapping
  public String operlog() {
    return PREFIX + "/post";
  }

  @RequiresPermissions("system:post:list")
  @ResponseBody
  @PostMapping("/list")
  public Result list(Page<SysPost> page, SysPost post) {
    return Result.success(postService.page(page, post));
  }

  @Log(title = "岗位管理", businessType = BusinessType.EXPORT)
  @RequiresPermissions("system:post:export")
  @ResponseBody
  @PostMapping("/export")
  public Result export(SysPost post) {
    List<SysPost> list = postService.list(post);
    ExcelUtil<SysPost> util = new ExcelUtil<>(SysPost.class);
    return util.exportExcel(list, "岗位数据");
  }

  @Log(title = "岗位管理", businessType = BusinessType.DELETE)
  @RequiresPermissions("system:post:remove")
  @ResponseBody
  @DeleteMapping
  public Result remove(String ids) {
    try {
      return custom(postService.removeByIds(ids));
    } catch (Exception e) {
      return error(e.getMessage());
    }
  }

  /**
   * 新增岗位
   */
  @GetMapping("/add")
  public String add() {
    return PREFIX + "/add";
  }

  /**
   * 新增保存岗位
   */
  @Log(title = "岗位管理", businessType = BusinessType.INSERT)
  @RequiresPermissions("system:post:add")
  @ResponseBody
  @PostMapping("/add")
  public Result addSave(@Validated SysPost post) {
    if (postService.checkNameUnique(post)) {
      return error("新增岗位'" + post.getPostName() + "'失败，岗位名称已存在");
    } else if (postService.checkCodeUnique(post)) {
      return error("新增岗位'" + post.getPostName() + "'失败，岗位编码已存在");
    }
    post.setCreateBy(ShiroUtils.getLoginName());
    return custom(postService.save(post));
  }

  /**
   * 修改岗位
   */
  @GetMapping("/edit/{postId}")
  public String edit(@PathVariable("postId") Long postId, ModelMap mmap) {
    mmap.put("post", postService.getById(postId));
    return PREFIX + "/edit";
  }

  /**
   * 修改保存岗位
   */
  @Log(title = "岗位管理", businessType = BusinessType.UPDATE)
  @RequiresPermissions("system:post:edit")
  @ResponseBody
  @PostMapping("/edit")
  public Result editSave(@Validated SysPost post) {
    if (postService.checkNameUnique(post)) {
      return error("修改岗位'" + post.getPostName() + "'失败，岗位名称已存在");
    } else if (postService.checkCodeUnique(post)) {
      return error("修改岗位'" + post.getPostName() + "'失败，岗位编码已存在");
    }
    post.setUpdateBy(ShiroUtils.getLoginName());
    return custom(postService.updateById(post));
  }

  /**
   * 校验岗位名称
   * @return
   */
  @ResponseBody
  @PostMapping("/checkPostNameUnique")
  public boolean checkPostNameUnique(SysPost post) {
    return postService.checkNameUnique(post);
  }

  /**
   * 校验岗位编码
   * @return
   */
  @ResponseBody
  @PostMapping("/checkPostCodeUnique")
  public boolean checkPostCodeUnique(SysPost post) {
    return postService.checkCodeUnique(post);
  }
}
