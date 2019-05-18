package com.daimeng.web.mail.service;

import java.util.HashMap;

import com.daimeng.web.common.ResponseVo;

public interface MailService {
	//发送简单邮件
	public ResponseVo sendSimpleMail(String to, String subject, String content);
	//发送 html 格式邮件
	public ResponseVo sendHtmlMail(String to, String subject, String content);
	//发送带附件的邮件
	public ResponseVo sendAttachmentsMail(String to, String subject, String content, String filePath);
	//发送带静态资源的邮件
	public ResponseVo sendInlineResourceMail(String to, String subject, String content, String rscPath);
	//发送模板html邮件
	public ResponseVo sendTemplateMail(String to, String subject, String templateName, HashMap<String,String> map);
	
}
