package com.daimeng.web.article.action;

import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.daimeng.util.Constants;
import com.daimeng.util.DateUtils;
import com.daimeng.web.article.entity.ArticleInfo;
import com.daimeng.web.article.service.ArticleService;
import com.daimeng.web.comment.entity.CommentInfo;
import com.daimeng.web.comment.service.CommentService;
import com.daimeng.web.common.ResponseVo;
import com.daimeng.web.user.entity.SysUser;
import com.daimeng.web.user.service.UserService;

@Controller
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/list/{page}")
	public String list(Model model,@PathVariable Integer page) {
		if(page != null && page >0){
			page--;
		}else page = 0;
		Page<ArticleInfo> list = articleService.findAll(page);
		model.addAttribute("list",list);
		model.addAttribute("cpage",page+1);
		if(list.getTotalPages() > 0){
			model.addAttribute("pages",Integer.valueOf(list.getTotalPages()));
		}else model.addAttribute("pages",1);
		
		SysUser cuser = (SysUser)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER2);
		if(cuser != null){
			model.addAttribute("myname",cuser.getRealname());
			model.addAttribute("cuser",cuser);
		}
		return "article/list";
	}
	
	@RequestMapping("/detail/{id}/{page}")
	public String detail(Model model,@PathVariable Integer id,@PathVariable Integer page) {
		SysUser cuser = (SysUser)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER2);
		if(cuser != null){
			model.addAttribute("myname",cuser.getRealname());
			model.addAttribute("cuser",cuser);
		}
		
		ArticleInfo info = new ArticleInfo();
		if(id != null && id >0){
			info = articleService.findOne(id);
		}
		model.addAttribute("info",info);
		
		
		if(page != null && page >0){
			page--;
		}else page = 0;
		Page<CommentInfo> list = commentService.findAllByArticleIdOrderByLayerDesc(id, page);
		
		model.addAttribute("list",list);
		model.addAttribute("qid",id);
		model.addAttribute("cpage",page+1);
		
		if(list.getTotalPages() > 0){
			model.addAttribute("pages",Integer.valueOf(list.getTotalPages()));
		}else model.addAttribute("pages",1);
		
		return "article/detail";
	}
	
	@RequestMapping("/user/{uid}/{page}")
	public String user(Model model,@PathVariable Integer uid,@PathVariable Integer page) {
		SysUser cuser = (SysUser)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER2);
		if(cuser != null){
			model.addAttribute("myname",cuser.getRealname());
			model.addAttribute("cuser",cuser);
		}
		
		if(page != null && page >0){
			page--;
		}else page = 0;
		Page<ArticleInfo> list = articleService.findByCreateUid(uid,page);
		
		model.addAttribute("list",list);
		model.addAttribute("cpage",page+1);
		model.addAttribute("quid",uid);
		if(list.getTotalPages() > 0){
			model.addAttribute("pages",Integer.valueOf(list.getTotalPages()));
		}else model.addAttribute("pages",1);
		
		SysUser quser = userService.findSysUser(uid);
		model.addAttribute("quser",quser);
		
		return "article/user";
	}
	
	@RequestMapping(value="/add",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseVo add(ArticleInfo info) {
		SysUser cuser = (SysUser)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER2);
		if(info.getId() == null){
			info.setCreateUser(cuser);
			info.setCreateTm(new Date());
			info.setStatusCd(1);
		}
		info.setUpdateTm(new Date());
		info.setUpdateUser(cuser);
		ResponseVo vo = new ResponseVo();
		try {
			info = articleService.addArticle(info);
			vo.setStatus(100);
			vo.setDesc(DateUtils.getDateStrFormat(info.getUpdateTm(), "yyyy年MM月dd日 HH:mm"));
			vo.setObj(info);
			return vo;
		} catch (Exception e) {
			vo.setStatus(200);
			vo.setDesc(e.getMessage());
			return vo;
		}
		
	}
	
	@RequestMapping(value="/findOne",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseVo findOne(ArticleInfo info) {
		SysUser cuser = (SysUser)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER2);
		ResponseVo vo = new ResponseVo();
		try {
			info = articleService.findOne(info.getId());
			vo.setStatus(100);
			vo.setDesc(DateUtils.getDateStrFormat(info.getCreateTm(), "yyyy年MM月dd日 HH:mm"));
			vo.setObj(info);
			return vo;
		} catch (Exception e) {
			vo.setStatus(200);
			vo.setDesc(e.getMessage());
			return vo;
		}
		
	}
	
	@RequestMapping(value="/delete",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseVo delete(ArticleInfo info) {
		SysUser cuser = (SysUser)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER2);
		ResponseVo vo = new ResponseVo();
		try {
			info = articleService.deleteArticle(info);
			if(info != null){
				vo.setStatus(100);
				vo.setDesc(DateUtils.getDateStrFormat(new Date(), "yyyy年MM月dd日 HH:mm"));
				vo.setObj(info);
			}else{
				vo.setStatus(200);
				vo.setDesc("删除失败,找不到对象!");
			}
			return vo;
		} catch (Exception e) {
			vo.setStatus(200);
			vo.setDesc(e.getMessage());
			return vo;
		}
		
	}
	
}
