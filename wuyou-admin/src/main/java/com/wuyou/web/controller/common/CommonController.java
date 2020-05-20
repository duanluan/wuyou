package com.wuyou.web.controller.common;

import com.wuyou.common.config.Global;
import com.wuyou.common.config.ServerConfig;
import com.wuyou.common.constant.Constants;
import com.wuyou.common.core.domain.Result;
import com.wuyou.common.utils.StringUtils;
import com.wuyou.common.utils.file.FileUploadUtils;
import com.wuyou.common.utils.file.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * 通用请求处理
 *
 * @author wuyou
 */
@Slf4j
@Controller
public class CommonController {

  private static final String CHARACTER_ENCODING = StandardCharsets.UTF_8.name();

  @Autowired
  private ServerConfig serverConfig;

  /**
   * 通用下载请求
   *
   * @param fileName 文件名称
   * @param delete   是否删除
   */
  @GetMapping("common/download")
  public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
    try {
      if (!FileUtils.isValidFilename(fileName)) {
        throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
      }
      String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
      String filePath = Global.getDownloadPath() + fileName;

      response.setCharacterEncoding(CHARACTER_ENCODING);
      response.setContentType("multipart/form-data");
      response.setHeader("Content-Disposition", "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, realFileName));
      FileUtils.writeBytes(filePath, response.getOutputStream());
      if (delete) {
        FileUtils.deleteFile(filePath);
      }
    } catch (Exception e) {
      log.error("下载文件失败", e);
    }
  }

  /**
   * 通用上传请求
   */
  @ResponseBody
  @PostMapping("/common/upload")
  public Result uploadFile(MultipartFile file) {
    try {
      // 上传文件路径
      String filePath = Global.getUploadPath();
      // 上传并返回新文件名称
      String fileName = FileUploadUtils.upload(filePath, file);
      String url = serverConfig.getUrl() + fileName;
      Result result = Result.success();
      result.put("fileName", fileName);
      result.put("url", url);
      return result;
    } catch (Exception e) {
      return Result.error(e.getMessage());
    }
  }

  /**
   * 本地资源通用下载
   */
  @GetMapping("/common/download/resource")
  public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    // 本地资源路径
    String localPath = Global.getProfile();
    // 数据库资源地址
    String downloadPath = localPath + StringUtils.substringAfter(resource, Constants.RESOURCE_PREFIX);
    // 下载名称
    String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
    response.setCharacterEncoding(CHARACTER_ENCODING);
    response.setContentType("multipart/form-data");
    response.setHeader("Content-Disposition",
      "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, downloadName));
    FileUtils.writeBytes(downloadPath, response.getOutputStream());
  }
}
