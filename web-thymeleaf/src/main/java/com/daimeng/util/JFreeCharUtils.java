package com.daimeng.util;

import java.awt.Font;
import java.io.FileOutputStream;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class JFreeCharUtils {

	public static void main(String[] args) {
		String[] names = {"A选项","B选项","C选项","D选项","E选项"};
		Double[] data = {10d,20d,30d,50d,15d};
		makeBarChart(names, data, "D:/java_test/问卷调查test/柱状图1.jpg");
		makePieChart(names, data, "D:/java_test/问卷调查test/饼图1.jpg");
		Constants.println("success");
	}
	public static void makeBarChart(String[] names, Double[] data, String imgPath) {
		//CategoryDataset ds = getDataSet();
		DefaultCategoryDataset ds = new DefaultCategoryDataset();
		for(int i = 0; i < names.length; i ++){
			//ds.addValue(value, rowKey, columnKey);
			ds.addValue(data[i], names[i]+":"+data[i], names[i]);
		}
		JFreeChart chart = ChartFactory.createBarChart("柱状图", "选项", "占比", ds);
		CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		CategoryAxis domainAxis = categoryplot.getDomainAxis();
		/*------设置X轴坐标上的文字-----------*/
		domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 40));
		/*------设置X轴的标题文字------------*/
		domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 40));
		/*------设置Y轴坐标上的文字-----------*/
		numberaxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 40));
		/*------设置Y轴的标题文字------------*/
		numberaxis.setLabelFont(new Font("黑体", Font.PLAIN, 40));
		/*------这句代码解决了底部汉字乱码的问题-----------*/
		chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 40));
		/******* 这句代码解决了标题汉字乱码的问题 ********/
		chart.getTitle().setFont(new Font("宋体", Font.PLAIN, 40));
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(imgPath);
			ChartUtils.writeChartAsJPEG(out, 0.5f, chart, 1200, 1200, null);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} 
	}
	public static void makePieChart(String[] names, Double[] data, String imgPath) {
		//CategoryDataset ds = getDataSet();
		DefaultPieDataset ds = new DefaultPieDataset();
		for(int i = 0; i < names.length; i ++){
			ds.setValue(names[i], data[i]);
		}
		JFreeChart chart = ChartFactory.createPieChart("饼图", ds);
		//处理图表对象
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setSectionOutlinesVisible(false); //true
		//主标题乱码处理
		chart.getTitle().setFont(new Font("宋体", Font.BOLD, 40));
		//子标题乱码处理
		chart.getLegend().setItemFont(new Font("宋体", Font.BOLD, 40));
		//字体显示
		plot.setLabelFont(new Font("宋体", Font.BOLD, 40));
		//饼图详细信息显示
		String labelFormat="{0}:{1} ({2})";
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator(labelFormat));
		//plot.setlabel
		/*------这句代码解决了底部汉字乱码的问题-----------*/
		chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 40));
		/******* 这句代码解决了标题汉字乱码的问题 ********/
		chart.getTitle().setFont(new Font("宋体", Font.PLAIN, 40));
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(imgPath);
			ChartUtils.writeChartAsJPEG(out, 0.5f, chart, 1200, 1200, null);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} 
	}
}
