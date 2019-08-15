package com.daimeng;

import java.util.Properties;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.daimeng.**.mapper")
public class WebServerApplication  {

	public static void main(String[] args) {
		SpringApplication.run(WebServerApplication.class, args);
	}


}
