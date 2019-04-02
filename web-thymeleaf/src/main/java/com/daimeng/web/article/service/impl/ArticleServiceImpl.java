package com.daimeng.web.article.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.daimeng.util.Constants;
import com.daimeng.web.article.entity.ArticleInfo;
import com.daimeng.web.article.repository.ArticleRepository;
import com.daimeng.web.article.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService{

	@Autowired
	private ArticleRepository articleRepository;

	@Override
	public Page<ArticleInfo> findAll(int page) {
		Pageable pageable = new PageRequest(page, Constants.PAGE_SIZE_10);
		Page<ArticleInfo> pagelist = articleRepository.findAllByOrderByIdDesc(pageable);
		return pagelist;
	}

	@Override
	public ArticleInfo findOne(int id) {
		return articleRepository.findOne(id);
	}

	@Override
	public Page<ArticleInfo> findByCreateUid(int page, int uid) {
		Pageable pageable = new PageRequest(page, Constants.PAGE_SIZE_10);
		Page<ArticleInfo> pagelist = articleRepository.findByCreateUidOrderByIdDesc(uid, pageable);
		return pagelist;
	}

	@Override
	public void add(ArticleInfo info) {
		articleRepository.save(info);
		
	}
}
