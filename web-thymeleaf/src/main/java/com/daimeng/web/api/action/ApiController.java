package com.daimeng.web.api.action;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daimeng.web.article.entity.ArticleInfo;
import com.daimeng.web.common.BaseController;
import com.daimeng.web.common.ResponseVo;

@RestController
@RequestMapping("/api")
public class ApiController extends BaseController{

	@RequestMapping("/test/{page}")
	public ResponseVo test(@PathVariable Integer page) {
		page = getPageNum(page);
		return createSuccess("返回参数是" + String.valueOf(page));
	}
}
