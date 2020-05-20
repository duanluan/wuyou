package com.wuyou.web.controller.tool;

import com.wuyou.common.core.controller.BaseController;
import com.wuyou.common.core.domain.Result;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.wuyou.common.core.domain.Result.error;
import static com.wuyou.common.core.domain.Result.success;

/**
 * swagger 用户测试方法
 *
 * @author wuyou
 */
@Api("用户信息管理")
@RequestMapping("/test/user")
@RestController
public class TestController extends BaseController {

  private final static Map<Integer, UserEntity> USERS = new LinkedHashMap<Integer, UserEntity>();

  {
    USERS.put(1, new UserEntity(1, "admin", "admin123", "15888888888"));
    USERS.put(2, new UserEntity(2, "ry", "admin123", "15666666666"));
  }

  @ApiOperation("获取用户列表")
  @GetMapping("/list")
  public Result userList() {
    List<UserEntity> userList = new ArrayList<>(USERS.values());
    return success(userList);
  }

  @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "path")
  @ApiOperation("获取用户详细")
  @GetMapping("/{userId}")
  public Result getUser(@PathVariable Integer userId) {
    if (!USERS.isEmpty() && USERS.containsKey(userId)) {
      return success(USERS.get(userId));
    } else {
      return error("用户不存在");
    }
  }

  @ApiImplicitParam(name = "userEntity", value = "新增用户信息", dataType = "UserEntity")
  @ApiOperation("新增用户")
  @PostMapping("/save")
  public Result save(UserEntity user) {
    if (user == null || user.getUserId() == null) {
      return error("用户ID不能为空");
    }
    return success(USERS.put(user.getUserId(), user));
  }

  @ApiImplicitParam(name = "userEntity", value = "新增用户信息", dataType = "UserEntity")
  @ApiOperation("更新用户")
  @PutMapping("/update")
  public Result update(UserEntity user) {
    if (user == null || user.getUserId() == null) {
      return error("用户ID不能为空");
    }
    if (USERS.isEmpty() || !USERS.containsKey(user.getUserId())) {
      return error("用户不存在");
    }
    USERS.remove(user.getUserId());
    return success(USERS.put(user.getUserId(), user));
  }

  @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "path")
  @ApiOperation("删除用户信息")
  @DeleteMapping("/{userId}")
  public Result delete(@PathVariable Integer userId) {
    if (!USERS.isEmpty() && USERS.containsKey(userId)) {
      USERS.remove(userId);
      return success();
    } else {
      return error("用户不存在");
    }
  }
}

@ApiModel("用户实体")
@AllArgsConstructor
@NoArgsConstructor
@Data
class UserEntity {
  @ApiModelProperty("用户ID")
  private Integer userId;

  @ApiModelProperty("用户名称")
  private String username;

  @ApiModelProperty("用户密码")
  private String password;

  @ApiModelProperty("用户手机")
  private String mobile;
}
