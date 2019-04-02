package com.daimeng.api.cst.service;

import com.daimeng.api.cst.entity.CstEntity;
import com.github.pagehelper.PageInfo;

public interface CstService {

	public PageInfo<CstEntity> getAll(Integer pageNum);
	
}
