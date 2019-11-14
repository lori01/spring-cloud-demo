package com.daimeng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.daimeng.**.mapper")
//@ImportResource(locations = { "classpath:druid-bean.xml" })
public class WebServerApplication  {

	public static void main(String[] args) {
		SpringApplication.run(WebServerApplication.class, args);
	}


}
