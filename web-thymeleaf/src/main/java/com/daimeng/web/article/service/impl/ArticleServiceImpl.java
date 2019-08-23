package com.daimeng.web.article.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
	public ArticleInfo findOne(int id) {
		return articleRepository.findOne(id);
	}
	
	@Override
	public Page<ArticleInfo> findAllBySpecification(final ArticleInfo info, int page) {
		Pageable pageable = new PageRequest(page, Constants.PAGE_SIZE_10);
		Specification specification = new Specification<ArticleInfo>() {
			@Override
			public Predicate toPredicate(Root<ArticleInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb){
				List<Predicate> list = new ArrayList<Predicate>();
				if(info.getCreateUid() != null && info.getCreateUid() > 0){
					list.add(cb.equal(root.get("createUid"), info.getCreateUid()));
				}
				if(info.getId() != null && info.getId() > 0){
					list.add(cb.equal(root.get("id"), info.getId()));
				}
				if(info.getStatusCd() != null){
					list.add(cb.equal(root.get("statusCd"), info.getStatusCd()));
				}
				if(info.getContext() != null && !"".equals(info.getContext())){
					list.add(cb.like((Expression) root.get("context"), "%" +info.getContext()+ "%"));
				}
				if(info.getContextType() != null && !"".equals(info.getContextType())){
					list.add(cb.equal(root.get("contextType"), info.getContextType()));
				}
				if(info.getTitle() != null && !"".equals(info.getTitle())){
					list.add(cb.like((Expression) root.get("title"), "%" +info.getTitle()+ "%"));
				}
				if(info.getShortContext() != null && !"".equals(info.getShortContext())){
					list.add(cb.like((Expression) root.get("shortContext"), "%" +info.getShortContext()+ "%"));
				}
				query.where(list.toArray(new Predicate[list.size()]));
				query.orderBy(cb.desc(root.get("id")));
				return query.getRestriction();
			}
		};
		return articleRepository.findAll(specification, pageable);
	}

	@Override
	public Page<ArticleInfo> findAll(int page) {
		/*Pageable pageable = new PageRequest(page, Constants.PAGE_SIZE_10);
		Page<ArticleInfo> pagelist = articleRepository.findByStatusCdOrderByIdDesc(1,pageable);
		return pagelist;*/
		ArticleInfo info = new ArticleInfo();
		info.setStatusCd(1);
		return findAllBySpecification(info, page);
	}

	@Override
	public Page<ArticleInfo> findByCreateUid(Integer uid,int page) {
		/*Pageable pageable = new PageRequest(page, Constants.PAGE_SIZE_10);
		Page<ArticleInfo> pagelist = articleRepository.findByCreateUidAndStatusCdOrderByIdDesc(uid,1, pageable);
		return pagelist;*/
		ArticleInfo info = new ArticleInfo();
		info.setStatusCd(1);
		info.setCreateUid(uid);
		return findAllBySpecification(info, page);
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
			cur.setContextType(info.getContextType());
			cur.setUpdateTm(info.getUpdateTm());
			cur.setUpdateUid(info.getUpdateUid());
			cur.setUpdateUser(info.getUpdateUser());
			cur = articleRepository.save(cur);
			
			return cur;
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
