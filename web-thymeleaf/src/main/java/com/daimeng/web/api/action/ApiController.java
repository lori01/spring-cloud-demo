package com.daimeng.web.api.action;

import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daimeng.util.DateUtils;
import com.daimeng.web.common.BaseController;
import com.daimeng.web.common.ResponseVo;
import com.daimeng.web.mail.service.MailService;

@RestController
@RequestMapping("/api")
public class ApiController extends BaseController{

	@Autowired
	private MailService mailService;
	
	@RequestMapping("/test/{page}")
	public ResponseVo test(@PathVariable Integer page) {
		page = getPageNum(page);
		return createSuccess("返回参数是" + String.valueOf(page));
	}
	
	@RequestMapping("/mail")
	public ResponseVo mail() {
		String to = "sephy9527@qq.com";
		String subject = "测试发送邮件！！" + DateUtils.getDateStrFormat(new Date(), DateUtils.YYYY_MM_DDHH_MM_SS);
		
		//String content = "测试发送邮件！！成功！！！！";
		//ResponseVo vo = mailService.sendSimpleMail(to, subject, content);
		
		String content = "<h1 style='color:red'>测试发送邮件！！成功！！！！<h1>";
		//ResponseVo vo = mailService.sendHtmlMail(to, subject, content);
		
		//String filePath = "D:/java_test/excel/Excel_Remove_Mod_less.xls";
		//String filePath = "D:/java_test/picture/Kaleidoscope_2536573325285588.jpg";
		//ResponseVo vo = mailService.sendAttachmentsMail(to, subject, content, filePath);
		
		//String rscPath = "D:/java_test/picture/Kaleidoscope_2536573325285588.jpg";
		//ResponseVo vo = mailService.sendInlineResourceMail(to, subject, content, rscPath);
		
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("id", "003");
		map.put("name", "张三");
		ResponseVo vo = mailService.sendTemplateMail(to, subject, "mail/emailTemplate", map);
		return vo;
	}
	
}
