package com.daimeng.web.article.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.daimeng.web.article.entity.ArticleInfo;


public interface ArticleRepository extends JpaRepository<ArticleInfo, Integer>{

	public Page<ArticleInfo> findByCreateUidAndStatusCdOrderByIdDesc(Integer createUid,Integer statusCd,Pageable pageable);
	public Page<ArticleInfo> findByStatusCdOrderByIdDesc(Integer statusCd,Pageable pageable);
}
