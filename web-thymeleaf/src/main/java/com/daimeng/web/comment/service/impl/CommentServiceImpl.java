package com.daimeng.web.comment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.daimeng.util.Constants;
import com.daimeng.web.article.entity.ArticleInfo;
import com.daimeng.web.article.repository.ArticleRepository;
import com.daimeng.web.comment.entity.CommentInfo;
import com.daimeng.web.comment.repository.CommentRepository;
import com.daimeng.web.comment.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Override
	public Page<CommentInfo> findAllByArticleIdOrderByLayerDesc(
			Integer articleId, int page) {
		Pageable pageable = new PageRequest(page, Constants.PAGE_SIZE_5);
		return commentRepository.findAllByArticleIdOrderByLayerDesc(articleId, pageable);
	}

	@Override
	public Page<CommentInfo> findAllByOrderByIdDesc(int page) {
		Pageable pageable = new PageRequest(page, Constants.PAGE_SIZE_5);
		return commentRepository.findAllByOrderByIdDesc(pageable);
	}

	@Override
	public CommentInfo addCommentInfo(CommentInfo info) {
		Integer layer = commentRepository.maxLayerByArticleId(info.getArticleId());
		ArticleInfo ainfo = articleRepository.findOne(info.getArticleId());
		if(layer == null){
			layer = 0;
		}
		info.setLayer(layer+1);
		info.setArticleInfo(ainfo);
		return commentRepository.save(info);
	}

	@Override
	public Page<CommentInfo> findByCreateUid(Integer uid, int page) {
		Pageable pageable = new PageRequest(page, Constants.PAGE_SIZE_5);
		return commentRepository.findAllByCreateUidOrderByIdDesc(uid,pageable);
	}

}
