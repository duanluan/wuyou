package com.ruoyi;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 *
 * @author ruoyi
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class})
public class RuoYiApplication {

  public static void main(String[] args) {
    // System.setProperty("spring.devtools.restart.enabled", "false");
    SpringApplication.run(RuoYiApplication.class, args);
    System.out.println(
      "      ____          __  ___  \n" +
        "     / __ \\__  _____\\ \\/ (_) \n" +
        "    / /_/ / / / / __ \\  / /  \n" +
        "   / _, _/ /_/ / /_/ / / /   \n" +
        "  /_/ |_|\\__,_/\\____/_/_/    \n" +
        " -----------------------     \n" +
        " 若 一  启   动   成   功     \n");
  }
}