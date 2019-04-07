package com.daimeng.web.comment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.daimeng.web.comment.entity.CommentInfo;

public interface CommentRepository extends JpaRepository<CommentInfo, Integer>,JpaSpecificationExecutor<CommentInfo>{

	public Page<CommentInfo> findAllByArticleIdAndStatusCdOrderByLayerDesc(Integer articleId,Integer statusCd, Pageable pageable);
	public Page<CommentInfo> findAllByStatusCdOrderByIdDesc(Integer statusCd,Pageable pageable);
	public Page<CommentInfo> findAllByCreateUidAndStatusCdOrderByIdDesc(Integer createUid,Integer statusCd,Pageable pageable);
	//public Page<CommentInfo> findAll(CommentInfo var,Pageable pageable);
	
	@Query("select max(layer) from CommentInfo where articleId =:articleId")
	public Integer maxLayerByArticleId(@Param("articleId") Integer articleId);
}
