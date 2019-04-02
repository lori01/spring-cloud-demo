package com.daimeng.web.comment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.daimeng.web.comment.entity.CommentInfo;

public interface CommentRepository extends JpaRepository<CommentInfo, Integer>{

	public Page<CommentInfo> findAllByArticleIdOrderByLayerDesc(Integer articleId,Pageable pageable);
	public Page<CommentInfo> findAllByOrderByIdDesc(Pageable pageable);
	public Page<CommentInfo> findAllByCreateUidOrderByIdDesc(Integer createUid,Pageable pageable);
	
	@Query("select max(layer) from CommentInfo where articleId =:articleId")
	public Integer maxLayerByArticleId(@Param("articleId") Integer articleId);
}
