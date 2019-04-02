package com.daimeng.api;

import java.util.Properties;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;

@SpringBootApplication
@EnableDiscoveryClient
//声明成mybatis Dao层的Bean，也可以在DAO/Mapper类上加@Mapper注解声明
@MapperScan("com.daimeng.**.mapper")
public class ApiServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiServerApplication.class, args);
	}
}
