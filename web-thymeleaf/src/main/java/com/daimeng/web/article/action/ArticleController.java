package com.daimeng.web.article.action;

import java.util.Date;
import java.util.HashMap;

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
import com.daimeng.web.common.BaseController;
import com.daimeng.web.common.ResponseVo;
import com.daimeng.web.mail.service.MailService;
import com.daimeng.web.user.entity.SysUser;
import com.daimeng.web.user.service.UserService;

@Controller
@RequestMapping("/article")
public class ArticleController extends BaseController {

	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MailService mailService;
	
	@RequestMapping("/list/{page}")
	public String list(Model model,@PathVariable Integer page) {
		page = getPageNum(page);
		Page<ArticleInfo> list = articleService.findAll(page);
		
		setPageToModel(model, list, page);
		
		return "article/list";
	}
	
	@RequestMapping("/detail/{id}/{page}")
	public String detail(Model model,@PathVariable Integer id,@PathVariable Integer page) {
		ArticleInfo info = new ArticleInfo();
		if(id != null && id >0){
			info = articleService.findOne(id);
			/*String newContext = CommonUtils.replaceEnter(info.getContext());
			info.setContext(newContext);
			System.out.println(newContext);*/
		}
		model.addAttribute("info",info);
		if(info == null){
			return"redirect:/500";
		}
		
		page = getPageNum(page);
		Page<CommentInfo> list = commentService.findAllByArticleIdOrderByLayerDesc(id, page);
		
		setPageToModel(model, list, page);
		
		model.addAttribute("qid",id);
		return "article/detail";
	}
	
	@RequestMapping("/user/{uid}/{page}")
	public String user(Model model,@PathVariable Integer uid,@PathVariable Integer page) {
		page = getPageNum(page);
		Page<ArticleInfo> list = articleService.findByCreateUid(uid,page);
		
		setPageToModel(model, list, page);
		
		model.addAttribute("qid",uid);
		SysUser quser = userService.findSysUser(uid);
		model.addAttribute("quser",quser);
		
		return "article/user";
	}
	
	@RequestMapping(value="/add",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseVo add(ArticleInfo info) {
		SysUser cuser = (SysUser)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
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
			if(info.getIsSendMail() != null && "1".equals(info.getIsSendMail()) 
					&& cuser.getEmail() != null && !"".equals(cuser.getEmail())){
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("title", info.getTitle());
				map.put("createTm", DateUtils.getDateStrFormat(new Date(), DateUtils.YYYY_MM_DDHH_MM_SS));
				map.put("realname", cuser.getRealname());
				map.put("id", String.valueOf(info.getId()));
				map.put("uid", String.valueOf(cuser.getId()));
				map.put("img", cuser.getImg());
				map.put("context", info.getContext());
				map.put("title", info.getTitle());
				
				mailService.sendTemplateMail(cuser.getEmail(), info.getTitle(), "mail/articleTemplate", map);
			}
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
		SysUser cuser = (SysUser)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
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
		SysUser cuser = (SysUser)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
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
