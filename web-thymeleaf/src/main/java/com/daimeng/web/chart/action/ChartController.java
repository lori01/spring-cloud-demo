package com.daimeng.web.chart.action;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.daimeng.web.article.entity.ArticleInfo;
import com.daimeng.web.common.BaseController;

@Controller
@RequestMapping("/chart")
public class ChartController extends BaseController {
	@RequestMapping("/index")
	public String index(Model model) {
		setCurrentUser(model);
		return "chart/index";
	}
}
