package com.daimeng.zuul.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class TokenFilter extends ZuulFilter{
	
	private static final Logger logger = LoggerFactory.getLogger(TokenFilter.class);
	
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
		return true;
	}
		
	//filter需要执行的具体操作
	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        logger.info("--->>> TokenFilter {},{}", request.getMethod(), request.getRequestURL().toString());

        String token = request.getParameter("token");// 获取请求的参数

        if (token !=null && !"".equals(token)) {
            ctx.setSendZuulResponse(true); //对请求进行路由
            ctx.setResponseStatusCode(200);
            ctx.set("isSuccess", true);
            return null;
        } else {
            ctx.setSendZuulResponse(false); //不对其进行路由
            ctx.setResponseStatusCode(400);
            ctx.setResponseBody("token is empty");
            ctx.set("isSuccess", false);
            return null;
        }
	}



	

	

}
