package com.daimeng.api.cst.action;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daimeng.api.cst.entity.CstInf;
import com.daimeng.api.cst.service.CstService;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/cst")
public class CstController {

	@Autowired
	private CstService cstService;
	
	@RequestMapping("/getCstList/{pageNum}")
	public ArrayList<CstInf> findAll(@PathVariable("pageNum") Integer pageNum) {
		ArrayList<CstInf> list = cstService.findAll();
		return list;
	}
	
}
