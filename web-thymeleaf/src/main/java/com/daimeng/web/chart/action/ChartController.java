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
	//折线图
	@RequestMapping("/line")
	public String line(Model model) {
		return "chart/line";
	}
	//柱状图
	@RequestMapping("/bar")
	public String bar(Model model) {
		return "chart/bar";
	}
	//饼图
	@RequestMapping("/pie")
	public String pie(Model model) {
		return "chart/pie";
	}
	//雷达图
	@RequestMapping("/radar")
	public String radar(Model model) {
		return "chart/radar";
	}
	//关系图
	@RequestMapping("/graph")
	public String graph(Model model) {
		return "chart/graph";
	}
	//仪表盘图
	@RequestMapping("/gauge")
	public String gauge(Model model) {
		return "chart/gauge";
	}
	//漏斗图
	@RequestMapping("/funnel")
	public String funnel(Model model) {
		return "chart/funnel";
	}
}
