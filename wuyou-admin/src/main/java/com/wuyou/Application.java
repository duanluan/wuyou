package com.wuyou;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动程序
 *
 * @author wuyou
 */
@EnableTransactionManagement
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class})
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
    // http://patorjk.com/software/taag/#p=display&f=Slant&t=WuYou
    System.out.println(
      "   _       __    __  __            \n" +
        "  | |     / /_  _\\ \\/ /___  __  __ \n" +
        "  | | /| / / / / /\\  / __ \\/ / / / \n" +
        "  | |/ |/ / /_/ / / / /_/ / /_/ /  \n" +
        "  |__/|__/\\__,_/ /_/\\____/\\__,_/   \n" +
        "  -----------------------------    \n" +
        "  无   尤   启    动    成    功    \n");
  }
}