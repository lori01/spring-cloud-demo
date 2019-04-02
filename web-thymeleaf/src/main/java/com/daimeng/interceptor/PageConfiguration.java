package com.daimeng.interceptor;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;

@Configuration
public class PageConfiguration {

	@Value("${datasource.dialect}")
	private String dialect;
	@Value("${pagehelper.offsetAsPageNum}")
	private String offsetAsPageNum;
	@Value("${pagehelper.rowBoundsWithCount}")
	private String rowBoundsWithCount;
	@Value("${pagehelper.reasonable}")
	private String reasonable;
	@Value("${pagehelper.supportMethodsArguments}")
	private String supportMethodsArguments;
	
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
		properties.setProperty("supportMethodsArguments", supportMethodsArguments);
		properties.setProperty("dialect", dialect); // 配置mysql数据库的方言
		System.out.println("######################pageHelper#########################");
		System.out.println("dialect="+dialect);
		System.out.println("offsetAsPageNum="+offsetAsPageNum);
		System.out.println("rowBoundsWithCount="+rowBoundsWithCount);
		System.out.println("reasonable="+reasonable);
		System.out.println("supportMethodsArguments="+supportMethodsArguments);
		System.out.println("######################pageHelper#########################");
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
		System.out.println("dialect="+dialect);
		System.out.println("######################pageInterceptor#########################");
		properties.setProperty("helperDialect", dialect); // 配置mysql数据库的方言
		pageInterceptor.setProperties(properties);
		return pageInterceptor;
	}
}
