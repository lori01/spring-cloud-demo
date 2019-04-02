package com.daimeng.web.comment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.daimeng.web.comment.entity.CommentInfo;

public interface CommentService {

	public Page<CommentInfo> findAllByArticleIdOrderByLayerDesc(Integer articleId,int page);
	public Page<CommentInfo> findByCreateUid(Integer uid,int page);
	public Page<CommentInfo> findAllByOrderByIdDesc(int page);
	public CommentInfo addCommentInfo(CommentInfo info);
}
