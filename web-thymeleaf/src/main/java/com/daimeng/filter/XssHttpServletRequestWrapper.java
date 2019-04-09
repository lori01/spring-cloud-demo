package com.daimeng.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringUtils;

import com.daimeng.util.JsoupUtil;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

	HttpServletRequest orgRequest = null;  
    private boolean isIncludeRichText = false;
    
	public XssHttpServletRequestWrapper(HttpServletRequest request, boolean isIncludeRichText) {
		super(request);  
        orgRequest = request;
        this.isIncludeRichText = isIncludeRichText;
	}
	
	/** 
	    * 覆盖getParameter方法，将参数名和参数值都做xss过滤。<br/> 
	    * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取<br/> 
	    * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖 
	    */  
	    @Override  
	    public String getParameter(String name) {
	        Boolean flag = ("content".equals(name) || name.endsWith("WithHtml"));
	        if( flag && !isIncludeRichText){
	            return super.getParameter(name);
	        }
	        name = JsoupUtil.clean(name,true);
	        String value = super.getParameter(name);  
	        if (StringUtils.isNotBlank(value)) {
	            value = JsoupUtil.clean(value,false);
	        }
	        return value;  
	    }  
	    
	    /**
	     * 
	    * @see javax.servlet.ServletRequestWrapper#getParameterValues(java.lang.String)
	    * @方法名称: getParameterValues 
	    * @路径 com.daimeng.filter 
	    * @功能描述: post方法 
	    * @作者 daimeng@tansun.com.cn
	    * @创建时间 2019年4月8日 下午5:37:32 
	    * @version V1.0   
	    * @param name
	    * @return
	     */
	    @Override
	    public String[] getParameterValues(String name) {
	    	String[] arr = super.getParameterValues(name);
	    	if(arr != null){
	    		for (int i=0;i<arr.length;i++) {
	    			arr[i] = JsoupUtil.clean(arr[i],false);
	    		}
	    	}
	    	return arr;
	    }
	    
	  
	    /** 
	    * 覆盖getHeader方法，将参数名和参数值都做xss过滤。<br/> 
	    * 如果需要获得原始的值，则通过super.getHeaders(name)来获取<br/> 
	    * getHeaderNames 也可能需要覆盖 
	    */  
	    @Override  
	    public String getHeader(String name) {  
	        name = JsoupUtil.clean(name,true);
	        String value = super.getHeader(name);  
	        if (StringUtils.isNotBlank(value)) {  
	            value = JsoupUtil.clean(value,true); 
	        }  
	        return value;  
	    }  
	  
	    /** 
	    * 获取最原始的request 
	    * 
	    * @return 
	    */  
	    public HttpServletRequest getOrgRequest() {  
	        return orgRequest;  
	    }  
	  
	    /** 
	    * 获取最原始的request的静态方法 
	    * 
	    * @return 
	    */  
	    public static HttpServletRequest getOrgRequest(HttpServletRequest req) {  
	        if (req instanceof XssHttpServletRequestWrapper) {  
	            return ((XssHttpServletRequestWrapper) req).getOrgRequest();  
	        }  
	  
	        return req;  
	    }  

}
