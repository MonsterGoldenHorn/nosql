package com.tango.nosql.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @description
 * @date 2022/7/28 15:04
 */
@SpringBootApplication
@EnableTransactionManagement
public class Boot {
    public static void main(String[] args) {
        SpringApplication.run(Boot.class);
    }
}
