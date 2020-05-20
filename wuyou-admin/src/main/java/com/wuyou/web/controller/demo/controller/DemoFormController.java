package com.wuyou.web.controller.demo.controller;

import com.wuyou.common.core.domain.Result;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 表单相关
 *
 * @author wuyou
 */
@RequestMapping("/demo/form")
@Controller
public class DemoFormController {

  private static final String PREFIX = "demo/form";

  private final static List<UserFormModel> USERS = new ArrayList<>();

  {
    USERS.add(new UserFormModel(1, "1000001", "测试1", "15888888888"));
    USERS.add(new UserFormModel(2, "1000002", "测试2", "15666666666"));
    USERS.add(new UserFormModel(3, "1000003", "测试3", "15666666666"));
    USERS.add(new UserFormModel(4, "1000004", "测试4", "15666666666"));
    USERS.add(new UserFormModel(5, "1000005", "测试5", "15666666666"));
  }

  /**
   * 按钮页
   */
  @GetMapping("/button")
  public String button() {
    return PREFIX + "/button";
  }

  /**
   * 下拉框
   */
  @GetMapping("/select")
  public String select() {
    return PREFIX + "/select";
  }

  /**
   * 时间轴
   */
  @GetMapping("/timeline")
  public String timeline() {
    return PREFIX + "/timeline";
  }

  /**
   * 表单校验
   */
  @GetMapping("/validate")
  public String validate() {
    return PREFIX + "/validate";
  }

  /**
   * 功能扩展（包含文件上传）
   */
  @GetMapping("/jasny")
  public String jasny() {
    return PREFIX + "/jasny";
  }

  /**
   * 拖动排序
   */
  @GetMapping("/sortable")
  public String sortable() {
    return PREFIX + "/sortable";
  }

  /**
   * 选项卡 & 面板
   */
  @GetMapping("/tabs_panels")
  public String tabsPanels() {
    return PREFIX + "/tabs_panels";
  }

  /**
   * 栅格
   */
  @GetMapping("/grid")
  public String grid() {
    return PREFIX + "/grid";
  }

  /**
   * 表单向导
   */
  @GetMapping("/wizard")
  public String wizard() {
    return PREFIX + "/wizard";
  }

  /**
   * 文件上传
   */
  @GetMapping("/upload")
  public String upload() {
    return PREFIX + "/upload";
  }

  /**
   * 日期和时间页
   */
  @GetMapping("/datetime")
  public String datetime() {
    return PREFIX + "/datetime";
  }

  /**
   * 左右互选组件
   */
  @GetMapping("/duallistbox")
  public String duallistbox() {
    return PREFIX + "/duallistbox";
  }

  /**
   * 基本表单
   */
  @GetMapping("/basic")
  public String basic() {
    return PREFIX + "/basic";
  }

  /**
   * 卡片列表
   */
  @GetMapping("/cards")
  public String cards() {
    return PREFIX + "/cards";
  }

  /**
   * summernote 富文本编辑器
   */
  @GetMapping("/summernote")
  public String summernote() {
    return PREFIX + "/summernote";
  }

  /**
   * 搜索自动补全
   */
  @GetMapping("/autocomplete")
  public String autocomplete() {
    return PREFIX + "/autocomplete";
  }

  /**
   * 获取用户数据
   */
  @ResponseBody
  @GetMapping("/userModel")
  public Result userModel() {
    Result result = new Result();
    result.put("code", 200);
    result.put("value", USERS);
    return result;
  }

  /**
   * 获取数据集合
   */
  @ResponseBody
  @GetMapping("/collection")
  public Result collection() {
    String[] array = {"wuyou 1", "wuyou 2", "wuyou 3", "wuyou 4", "wuyou 5"};
    Result result = new Result();
    result.put("value", array);
    return result;
  }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class UserFormModel {
  /**
   * 用户ID
   */
  private int userId;
  /**
   * 用户编号
   */
  private String userCode;
  /**
   * 用户姓名
   */
  private String userName;
  /**
   * 用户手机
   */
  private String userPhone;
}
