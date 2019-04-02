package com.daimeng.zuul.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;

public class MyFilter extends ZuulFilter{
	
	private static final Logger logger = LoggerFactory.getLogger(MyFilter.class);
	
	//定义filter的类型，有pre、route、post、error四种
	@Override
	public String filterType() {
		return "pre";
	}
	
	//定义filter的顺序，数字越小表示顺序越高，越先执行
	@Override
	public int filterOrder() {
		return 0;
	}
	
	//表示是否需要执行该filter，true表示执行，false表示不执行
	@Override
	public boolean shouldFilter() {
		return false;
	}
		
	//filter需要执行的具体操作
	@Override
	public Object run() {
		return null;
	}



	

	

}
