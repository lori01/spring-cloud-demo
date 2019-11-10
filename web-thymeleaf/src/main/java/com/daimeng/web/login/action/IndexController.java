package com.daimeng.web.login.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.daimeng.shiro.exception.NullAccountException;
import com.daimeng.shiro.exception.NullCredentialsException;
import com.daimeng.util.Constants;
import com.daimeng.web.article.entity.ArticleInfo;
import com.daimeng.web.article.service.ArticleService;
import com.daimeng.web.comment.entity.CommentInfo;
import com.daimeng.web.comment.service.CommentService;
import com.daimeng.web.common.BaseController;
import com.daimeng.web.user.entity.SysUser;
import com.daimeng.web.user.service.UserService;

@Controller
@RequestMapping("/index")
public class IndexController extends BaseController{
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping({"/info"})
	public String info(Model model){
		return "index";
	}

	@RequestMapping({"/list/{page}"})
	public String index(Model model,@PathVariable Integer page){
		page = getPageNum(page);
		ArticleInfo inf = new ArticleInfo();
		inf.setContextType("02");
		inf.setStatusCd(1);
		Page<ArticleInfo> list = articleService.findAllBySpecification(inf,page);
		setPageToModel(model, list, page);
		return "index/list";
	}
	
	@RequestMapping("/user/{uid}/{page}")
	public String user(Model model,@PathVariable Integer uid,@PathVariable Integer page) {
		page = getPageNum(page);
		ArticleInfo inf = new ArticleInfo();
		inf.setContextType("02");
		inf.setCreateUid(uid);
		inf.setStatusCd(1);
		Page<ArticleInfo> list = articleService.findAllBySpecification(inf,page);
		setPageToModel(model, list, page);
		
		model.addAttribute("qid",uid);
		SysUser quser = userService.findSysUser(uid);
		model.addAttribute("quser",quser);
		
		return "index/list";
	}
	
	@RequestMapping("/detail/{id}/{page}")
	public String detail(Model model,@PathVariable Integer id,@PathVariable Integer page) {
		ArticleInfo info = new ArticleInfo();
		if(id != null && id >0){
			info = articleService.findOne(id);
		}
		model.addAttribute("info",info);
		if(info == null){
			//return"redirect:/500";
		}
		
		page = getPageNum(page);
		Page<CommentInfo> list = commentService.findAllByArticleIdOrderByLayerDesc(info.getId(), page);
		
		setPageToModel(model, list, page);
		
		model.addAttribute("qid",id);
		return "index/detail";
	}
}
