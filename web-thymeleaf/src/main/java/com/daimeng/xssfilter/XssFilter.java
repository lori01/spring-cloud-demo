package com.daimeng.xssfilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
* @名称: XssFilter.java 
* @路径 com.daimeng.interceptor 
* @功能描述: 拦截防止xss注入 通过Jsoup过滤请求参数内的特定字符
* @作者 daimeng@tansun.com.cn
* @创建时间 2019年4月8日 下午5:00:08 
* @version V1.0
 */
public class XssFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(XssFilter.class);
	
	/**
	 * 是否过滤富文本内容
	 */
	private static boolean IS_INCLUDE_RICH_TEXT = false;
	
	public List<String> excludes = new ArrayList<>();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		if(logger.isDebugEnabled()){
			logger.debug("xss filter init ====================");
		}
		String isIncludeRichText = filterConfig.getInitParameter("isIncludeRichText");
		if(StringUtils.isNotBlank(isIncludeRichText)){
			IS_INCLUDE_RICH_TEXT = BooleanUtils.toBoolean(isIncludeRichText);
		}
		
		String temp = filterConfig.getInitParameter("excludes");
		if (temp != null) {
			String[] url = temp.split(",");
			for (int i = 0; url != null && i < url.length; i++) {
				excludes.add(url[i]);
			}
		}

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if(logger.isDebugEnabled()){
  			logger.debug("xss filter is open");
  		}
  		
  		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
  		if(handleExcludeURL(req, resp)){
  			chain.doFilter(request, response);
			return;
		}
  		
  		XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request,IS_INCLUDE_RICH_TEXT);
  		chain.doFilter(xssRequest, response);
	}
	
	private boolean handleExcludeURL(HttpServletRequest request, HttpServletResponse response) {
		if (excludes == null || excludes.isEmpty()) {
			return false;
		}
 
		String url = request.getServletPath();
		for (String pattern : excludes) {
			Pattern p = Pattern.compile("^" + pattern);
			Matcher m = p.matcher(url);
			if (m.find()) {
				return true;
			}
		}
 
		return false;
	}

	@Override
	public void destroy() {
		
		
	}

}
