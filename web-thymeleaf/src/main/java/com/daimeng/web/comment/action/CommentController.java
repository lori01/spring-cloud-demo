package com.daimeng.web.comment.action;

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
import com.daimeng.web.comment.entity.CommentInfo;
import com.daimeng.web.comment.service.CommentService;
import com.daimeng.web.common.ResponseVo;
import com.daimeng.web.user.entity.SysUser;
import com.daimeng.web.user.service.UserService;

@Controller
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/list/{page}")
	public String list(Model model,@PathVariable Integer page) {
		if(page != null && page >0){
			page--;
		}else page = 0;
		Page<CommentInfo> list = commentService.findAllByOrderByIdDesc(page);
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
		return "comment/list";
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
		Page<CommentInfo> list = commentService.findByCreateUid(uid,page);
		
		model.addAttribute("list",list);
		model.addAttribute("cpage",page+1);
		model.addAttribute("quid",uid);
		if(list.getTotalPages() > 0){
			model.addAttribute("pages",Integer.valueOf(list.getTotalPages()));
		}else model.addAttribute("pages",1);
		
		SysUser quser = userService.findSysUser(uid);
		model.addAttribute("quser",quser);
		
		return "comment/user";
	}
	
	
	@RequestMapping(value="/add",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseVo add(CommentInfo info) {
		SysUser cuser = (SysUser)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER2);
		/*info.setCreateUid(cuser.getId());
		info.setUpdateUid(cuser.getId());*/
		info.setCreateUser(cuser);
		info.setUpdateUser(cuser);
		info.setCreateTm(new Date());
		info.setUpdateTm(new Date());
		info.setStatusCd(1);
		ResponseVo vo = new ResponseVo();
		try {
			info = commentService.addCommentInfo(info);
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
	
	@RequestMapping(value="/update",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseVo update(CommentInfo info) {
		SysUser cuser = (SysUser)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER2);
		ResponseVo vo = new ResponseVo();
		info.setUpdateUser(cuser);
		info.setUpdateTm(new Date());
		try {
			info = commentService.updateCommentInfo(info);
			if(info != null){
				vo.setStatus(100);
				vo.setDesc(DateUtils.getDateStrFormat(new Date(), "yyyy年MM月dd日 HH:mm"));
				//vo.setObj(info);
			}else{
				vo.setStatus(200);
				vo.setDesc("更新失败,找不到对象!");
			}
			return vo;
		} catch (Exception e) {
			vo.setStatus(200);
			vo.setDesc(e.getMessage());
			return vo;
		}
	}
	
	@RequestMapping(value="/delete",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseVo delete(CommentInfo info) {
		SysUser cuser = (SysUser)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER2);
		ResponseVo vo = new ResponseVo();
		try {
			info = commentService.deleteCommentInfo(info);
			if(info != null){
				vo.setStatus(100);
				vo.setDesc(DateUtils.getDateStrFormat(new Date(), "yyyy年MM月dd日 HH:mm"));
				//vo.setObj(info);
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
