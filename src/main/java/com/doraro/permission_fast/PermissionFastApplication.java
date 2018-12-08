package com.doraro.permission_fast;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.doraro.permission_fast.mapper")
public class PermissionFastApplication {

	public static void main(String[] args) {
		SpringApplication.run(PermissionFastApplication.class, args);
	}
}
