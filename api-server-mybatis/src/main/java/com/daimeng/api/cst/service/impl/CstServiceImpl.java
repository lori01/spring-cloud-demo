package com.daimeng.api.cst.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daimeng.api.cst.entity.CstEntity;
import com.daimeng.api.cst.mapper.CstMapper;
import com.daimeng.api.cst.service.CstService;
import com.daimeng.api.utils.Constants;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class CstServiceImpl implements CstService{

	@Autowired
	private CstMapper cstMapper;
	
	@Override
	public PageInfo<CstEntity> getAll(Integer pageNum) {
		//设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper/dao接口中的方法执行之前设置该分页信息】
		PageHelper.startPage(pageNum, Constants.PAGE_SIZE);
		ArrayList<CstEntity> list = cstMapper.getAll();
		PageInfo<CstEntity> pageInfo = new PageInfo<CstEntity>(list);
		System.out.println(pageInfo.getTotal());
		return pageInfo;
	}

}
