package com.daimeng.api.cst.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daimeng.api.cst.entity.CstEntity;
import com.daimeng.api.cst.service.CstService;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/cst")
public class CstController {

	@Autowired
	private CstService cstService;
	
	@RequestMapping("/getCstList/{pageNum}")
	public PageInfo<CstEntity> getCstList(@PathVariable("pageNum") Integer pageNum) {
		PageInfo<CstEntity> users = cstService.getAll(pageNum);
		return users;
	}
	
}
