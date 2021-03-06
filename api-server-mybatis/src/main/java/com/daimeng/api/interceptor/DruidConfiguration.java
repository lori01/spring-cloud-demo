package com.daimeng.api.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/**
 * druid有一个Servlet和Filter,这里采用编程注入的方式
 * @author daimeng
 *
 */
@Configuration
public class DruidConfiguration {

	private static final Logger log = LoggerFactory.getLogger(DruidConfiguration.class);
	
	@Value("${druid.login.username}")
	private String userName;
	@Value("${druid.login.password}")
	private String password;
	@Value("${druid.reset.enable}")
	private String resetEnable;

	@Bean
	public ServletRegistrationBean druidServlet() {
		log.info("init Druid Servlet Configuration ");
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
		servletRegistrationBean.setServlet(new StatViewServlet());
		servletRegistrationBean.addUrlMappings("/druid/*");
		Map<String, String> initParameters = new HashMap<String, String>();
		System.out.println("######################druidServlet#########################");
		System.out.println("#############druid-name="+userName);
		System.out.println("#############druid-password="+password);
		System.out.println("#############druid-resetEnable="+resetEnable);
		initParameters.put("loginUsername", userName);// 用户名
		initParameters.put("loginPassword", password);// 密码
		initParameters.put("resetEnable", resetEnable);// 禁用HTML页面上的“Reset All”功能
		initParameters.put("allow", ""); // IP白名单 (没有配置或者为空，则允许所有访问)
		// initParameters.put("deny", "192.168.20.38");// IP黑名单
		// (存在共同时，deny优先于allow)
		servletRegistrationBean.setInitParameters(initParameters);
		return servletRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new WebStatFilter());

		// 添加过滤规则
		filterRegistrationBean.addUrlPatterns("/*");

		// 添加不需要忽略的格式信息.
		filterRegistrationBean.addInitParameter("exclusions",
				"*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
		return filterRegistrationBean;
	}

	//监控到SQL语句
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource druidDataSource() {
		return new DruidDataSource();
	}

}
