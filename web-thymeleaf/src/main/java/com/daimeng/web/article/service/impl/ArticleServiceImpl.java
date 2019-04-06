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
		Page<ArticleInfo> pagelist = articleRepository.findByStatusCdOrderByIdDesc(1,pageable);
		return pagelist;
	}

	@Override
	public ArticleInfo findOne(int id) {
		return articleRepository.findOne(id);
	}

	@Override
	public Page<ArticleInfo> findByCreateUid(int page, int uid) {
		Pageable pageable = new PageRequest(page, Constants.PAGE_SIZE_10);
		Page<ArticleInfo> pagelist = articleRepository.findByCreateUidAndStatusCdOrderByIdDesc(uid,1, pageable);
		return pagelist;
	}

	@Override
	public ArticleInfo addArticle(ArticleInfo info) {
		if(info.getId() != null){
			return updateArticle(info);
		}else{
			info = articleRepository.save(info);
			return info;
		}
	}

	@Override
	public ArticleInfo updateArticle(ArticleInfo info) {
		ArticleInfo cur = articleRepository.findOne(info.getId());
		if(cur != null){
			cur.setTitle(info.getTitle());
			cur.setShortContext(info.getShortContext());
			cur.setContext(info.getContext());
			cur.setUpdateTm(info.getUpdateTm());
			cur.setUpdateUid(info.getUpdateUid());
			cur.setUpdateUser(info.getUpdateUser());
			info = articleRepository.save(cur);
			return info;
		}else return null;
		
	}

	@Override
	public ArticleInfo deleteArticle(ArticleInfo info) {
		info = articleRepository.findOne(info.getId());
		if(info != null){
			info.setStatusCd(0);
			info =  articleRepository.save(info);
			return info;
		}else return null;
		
	}
}
