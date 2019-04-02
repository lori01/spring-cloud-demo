package com.daimeng.web.article.service;

import org.springframework.data.domain.Page;

import com.daimeng.web.article.entity.ArticleInfo;

public interface ArticleService {

	public Page<ArticleInfo> findAll(int page);
	public ArticleInfo findOne(int id);
	public Page<ArticleInfo> findByCreateUid(int page,int uid);
	
	public void add(ArticleInfo info);
}
