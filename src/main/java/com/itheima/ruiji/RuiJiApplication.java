package com.itheima.ruiji;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author tyb
 * @version 1.0
 * @ClassName: RuiJiApplication
 * @description:
 * @date 2022/12/15 16:00
 */

@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
public class RuiJiApplication {
    public static void main(String[] args) {
        SpringApplication.run(RuiJiApplication.class, args);
    }
}
