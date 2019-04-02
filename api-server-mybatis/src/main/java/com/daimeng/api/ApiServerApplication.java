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

	@Value("${datasource.dialect}")
	private String dialect;
	@Value("${pagehelper.offsetAsPageNum}")
	private String offsetAsPageNum;
	@Value("${pagehelper.rowBoundsWithCount}")
	private String rowBoundsWithCount;
	@Value("${pagehelper.reasonable}")
	private String reasonable;
	
	public static void main(String[] args) {
		SpringApplication.run(ApiServerApplication.class, args);
	}

	/**
	 * mysql 分页插件
	 * @return
	 */
	@Bean
	public PageHelper pageHelper() {
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		properties.setProperty("offsetAsPageNum", offsetAsPageNum);
		properties.setProperty("rowBoundsWithCount", rowBoundsWithCount);
		properties.setProperty("reasonable", reasonable);
		System.out.println("######################pageHelper#########################");
		System.out.println(dialect);
		System.out.println(offsetAsPageNum);
		System.out.println(rowBoundsWithCount);
		System.out.println(reasonable);
		System.out.println("######################pageHelper#########################");
		properties.setProperty("dialect", dialect); // 配置mysql数据库的方言
		pageHelper.setProperties(properties);
		return pageHelper;
	}
	/**
	 * mysql 分页插件
	 * @return
	 */
	@Bean
	public PageInterceptor pageInterceptor() {
		PageInterceptor pageInterceptor = new PageInterceptor();
		Properties properties = new Properties();
		System.out.println("######################pageInterceptor#########################");
		System.out.println(dialect);
		System.out.println("######################pageInterceptor#########################");
		properties.setProperty("helperDialect", dialect); // 配置mysql数据库的方言
		pageInterceptor.setProperties(properties);
		return pageInterceptor;
	}

}
