package com.daimeng.web.comment.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
	public Page<CommentInfo> findAllBySpecification(final CommentInfo info, int page) {
		Pageable pageable = new PageRequest(page, Constants.PAGE_SIZE_10);
		Specification specification = new Specification<CommentInfo>() {
            @Override
            public Predicate toPredicate(Root<CommentInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
            	List<Predicate> list = new ArrayList<>();
            	if(info.getCreateUid() != null && info.getCreateUid() > 0){
            		list.add(criteriaBuilder.equal(root.get("createUid"), info.getCreateUid()));
            	}
            	if(info.getStatusCd() != null){
            		list.add(criteriaBuilder.equal(root.get("statusCd"), info.getStatusCd()));
            	}
            	if(info.getContext() != null && !"".equals(info.getContext())){
            		list.add(criteriaBuilder.like((Expression) root.get("context"), "%" +info.getContext()+ "%"));
            	}
            	if(info.getArticleId() != null && info.getArticleId() > 0){
            		list.add(criteriaBuilder.equal(root.get("articleId"), info.getArticleId()));
            	}
            	//return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            	criteriaQuery.where(list.toArray(new Predicate[list.size()]));
            	criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
                return criteriaQuery.getRestriction();
            }
        };
		return commentRepository.findAll(specification, pageable);
	}
	
	@Override
	public Page<CommentInfo> findAllByArticleIdOrderByLayerDesc(
			Integer articleId, int page) {
		/*Pageable pageable = new PageRequest(page, Constants.PAGE_SIZE_10);
		return commentRepository.findAllByArticleIdAndStatusCdOrderByLayerDesc(articleId,1, pageable);*/
		CommentInfo info = new CommentInfo();
		info.setStatusCd(1);
		info.setArticleId(articleId);
		return findAllBySpecification(info, page);
	}

	@Override
	public Page<CommentInfo> findAllByOrderByIdDesc(int page) {
		/*Pageable pageable = new PageRequest(page, Constants.PAGE_SIZE_10);
		return commentRepository.findAllByStatusCdOrderByIdDesc(1,pageable);*/
		CommentInfo info = new CommentInfo();
		info.setStatusCd(1);
		return findAllBySpecification(info, page);
	}
	
	@Override
	public Page<CommentInfo> findByCreateUid(Integer uid, int page) {
		/*Pageable pageable = new PageRequest(page, Constants.PAGE_SIZE_10);
		return commentRepository.findAllByCreateUidAndStatusCdOrderByIdDesc(uid,1,pageable);*/
		CommentInfo info = new CommentInfo();
		info.setStatusCd(1);
		info.setCreateUid(uid);
		return findAllBySpecification(info, page);
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
	public CommentInfo updateCommentInfo(CommentInfo info) {
		CommentInfo cur = commentRepository.findOne(info.getId());
		if(cur != null){
			cur.setContext(info.getContext());
			cur.setUpdateTm(info.getUpdateTm());
			cur.setUpdateUid(info.getUpdateUid());
			cur.setUpdateUser(info.getUpdateUser());
			cur = commentRepository.save(cur);
			return cur;
		}else return null;
		
	}

	@Override
	public CommentInfo deleteCommentInfo(CommentInfo info) {
		info = commentRepository.findOne(info.getId());
		if(info != null){
			info.setStatusCd(0);
			info = commentRepository.save(info);
			return info;
		}else return null;
		
	}

}
