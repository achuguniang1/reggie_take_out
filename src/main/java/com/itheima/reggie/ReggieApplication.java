package com.itheima.reggie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author ：sean
 * @date ：Created in 2022/4/16
 * @description ：
 * @version: 1.0
 */
@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
public class ReggieApplication {
	public static void main(String args[]){
		log.info("[项目启动...]");
		SpringApplication.run(ReggieApplication.class);
	}
}
