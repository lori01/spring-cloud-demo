package com.daimeng.web.mail.service.impl;

import java.io.File;
import java.util.HashMap;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.daimeng.web.common.ResponseVo;
import com.daimeng.web.mail.service.MailService;

@Service
public class MailServiceImpl implements MailService {

	private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
	
	@Autowired
    private JavaMailSender mailSender;
	@Autowired
	private TemplateEngine templateEngine;

    @Value("${mail.fromMail.addr}")
    private String from;
    
	@Override
	public ResponseVo sendSimpleMail(String to, String subject, String content) {
		SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        ResponseVo vo = new ResponseVo();
        try {
            mailSender.send(message);
            logger.info("简单邮件已经发送！");
            vo.setDesc("简单邮件已经发送！");
            vo.setStatus(100);
            return vo;
        } catch (Exception e) {
            logger.error("发送简单邮件时发生异常！", e);
            vo.setDesc("发送简单邮件时发生异常！" + e.getMessage());
        }
        vo.setStatus(200);
        return vo;
	}

	@Override
	public ResponseVo sendHtmlMail(String to, String subject, String content) {
		MimeMessage message = mailSender.createMimeMessage();
		ResponseVo vo = new ResponseVo();
	    try {
	        //true表示需要创建一个multipart message
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        helper.setFrom(from);
	        helper.setTo(to);
	        helper.setSubject(subject);
	        helper.setText(content, true);

	        mailSender.send(message);
	        logger.info("html邮件发送成功！");
	        vo.setDesc("html邮件发送成功！");
            vo.setStatus(100);
            return vo;
	    } catch (MessagingException e) {
	        logger.error("发送html邮件时发生异常！", e);
	        vo.setDesc("发送html邮件时发生异常！" + e.getMessage());
	    }
        vo.setStatus(200);
        return vo;
	}

	@Override
	public ResponseVo sendAttachmentsMail(String to, String subject,
			String content, String filePath) {
		MimeMessage message = mailSender.createMimeMessage();
		ResponseVo vo = new ResponseVo();
	    try {
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        helper.setFrom(from);
	        helper.setTo(to);
	        helper.setSubject(subject);
	        helper.setText(content, true);

	        FileSystemResource file = new FileSystemResource(new File(filePath));
	        String fileName = filePath.substring(filePath.lastIndexOf("/"/*File.separator*/)+1);
	        helper.addAttachment(fileName, file);
	        //添加多个附件可以使用多条 helper.addAttachment(fileName, file)

	        mailSender.send(message);
	        logger.info("带附件的邮件已经发送！");
	        vo.setDesc("带附件的邮件已经发送！");
            vo.setStatus(100);
            return vo;
	    } catch (MessagingException e) {
	        logger.error("发送带附件的邮件时发生异常！", e);
	        vo.setDesc("发送带附件的邮件时发生异常！" + e.getMessage());
	    }
	    vo.setStatus(200);
        return vo;
	}

	@Override
	public ResponseVo sendInlineResourceMail(String to, String subject,
			String content, String rscPath) {
		MimeMessage message = mailSender.createMimeMessage();
		ResponseVo vo = new ResponseVo();
	    try {
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        helper.setFrom(from);
	        helper.setTo(to);
	        helper.setSubject(subject);
	        helper.setText(content, true);

	        FileSystemResource res = new FileSystemResource(new File(rscPath));
	        String rscName = rscPath.substring(rscPath.lastIndexOf("/"/*File.separator*/)+1);
	        helper.addInline(rscName, res);

	        mailSender.send(message);
	        logger.info("嵌入静态资源的邮件已经发送！");
	        vo.setDesc("嵌入静态资源的邮件已经发送！");
            vo.setStatus(100);
            return vo;
	    } catch (MessagingException e) {
	        logger.error("发送嵌入静态资源的邮件时发生异常！", e);
	        vo.setDesc("发送嵌入静态资源的邮件时发生异常！" + e.getMessage());
	    }
	    vo.setStatus(200);
        return vo;
	}

	@Override
	public ResponseVo sendTemplateMail(String to, String subject,
			String templateName, HashMap<String,String> map) {
		Context context = new Context();
		for(String key : map.keySet()){
			context.setVariable(key, map.get(key));
		}
	    String emailContent = templateEngine.process(templateName, context);
	    
	    ResponseVo vo = new ResponseVo();
	    try {
	    	return sendHtmlMail(to,subject,emailContent);
		} catch (Exception e) {
			logger.error("发送模板邮件时发生异常！", e);
	        vo.setDesc("发送模板邮件时发生异常！" + e.getMessage());
		}
	    vo.setStatus(200);
        return vo;
	}

	
}
