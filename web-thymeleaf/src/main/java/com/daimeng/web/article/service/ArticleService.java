package com.daimeng.web.article.service;

import org.springframework.data.domain.Page;

import com.daimeng.web.article.entity.ArticleInfo;

public interface ArticleService {

	public Page<ArticleInfo> findAll(int page);
	public ArticleInfo findOne(int id);
	public Page<ArticleInfo> findByCreateUid(Integer uid,int page);
	
	public ArticleInfo addArticle(ArticleInfo info);
	public ArticleInfo updateArticle(ArticleInfo info);
	public ArticleInfo deleteArticle(ArticleInfo info);
	
	public Page<ArticleInfo> findAllBySpecification(ArticleInfo info,int page);
}
