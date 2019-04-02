package com.daimeng.api.cst.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daimeng.api.cst.entity.CstInf;
import com.daimeng.api.cst.repository.CstInfRepository;
import com.daimeng.api.cst.service.CstService;

@Service
public class CstServiceImpl implements CstService{
	
	@Autowired
	private CstInfRepository cstRepository;
	
	@Override
	public ArrayList<CstInf> findAll() {
		ArrayList<CstInf> list = cstRepository.findAll();
		return list;
	}

}
