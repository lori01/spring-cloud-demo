package com.daimeng.util;


import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
 
import java.io.IOException;

public class JsoupUtil {

	/**
	 * 使用自带的basicWithImages 白名单
	 * 允许的便签有a,b,blockquote,br,cite,code,dd,dl,dt,em,i,li,ol,p,pre,q,small,span,
	 * strike,strong,sub,sup,u,ul,img
	 * 以及a标签的href,img标签的src,align,alt,height,width,title属性
	 */
	private static final Whitelist whitelist = Whitelist.basicWithImages();
	/** 配置过滤化参数,不对代码进行格式化 */
	private static final Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);
	static {
		// 富文本编辑时一些样式是使用style来进行实现的
		// 比如红色字体 style="color:red;"
		// 所以需要给所有标签添加style属性
		whitelist.addAttributes(":all", "style");
	}
 
	public static String clean(String content, boolean doTrim) {
	    if(doTrim && StringUtils.isNotBlank(content)){
	    	//去掉两端的空格
	        content = content.trim();
        }
	    content = xssEncode(content);
	    content = Jsoup.clean(content, "", whitelist, outputSettings);
        return content;
	}
	
	private static String xssEncode(String s) {  
        if (s == null || s.isEmpty()) {  
            return s;  
        }else{  
        	StringBuilder sb = new StringBuilder(s.length() + 16);  
            for (int i = 0; i < s.length(); i++) {  
                char c = s.charAt(i);  
                switch (c) {  
                case '>':  
                    sb.append("＞");// 转义大于号  
                    //System.out.println("转义大于号");
                    break;  
                case '<':  
                    sb.append("＜");// 转义小于号  
                    //System.out.println("转义小于号");
                    break;  
                case '\'':  
                    sb.append("＇");// 转义单引号  
                    //System.out.println("转义单引号");
                    break;  
                case '\"':  
                    sb.append("＂");// 转义双引号  
                    //System.out.println("转义双引号  ");
                    break;  
                /*case '&':  
                    sb.append("＆");// 转义&  
                    System.out.println("转义&");
                    break;*/
                case '#':  
                    sb.append("＃");// 转义#  
                    //System.out.println("转义# ");
                    break;  
                case '(':  
                    sb.append("（");// 转义(  
                    //System.out.println("转义( ");
                    break;
                case ')':  
                    sb.append("）");// 转义)  
                    //System.out.println("转义) ");
                    break;
                default:  
                    sb.append(c);  
                    break;  
                }  
            }  
            return sb.toString();  
        }  
    }  
	
	public static void main(String[] args) throws IOException {
		String text = "   <a href=\"http://www.baidu.com/a\" onclick=\"alert(1);\">sss</a><script>alert(0);</script>sss   ";
		System.out.println(clean(text,true));
	}
	
}
