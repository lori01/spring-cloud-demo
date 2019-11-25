package com.daimeng.util;

import java.awt.Color;
import java.awt.Font;
import java.io.FileOutputStream;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.StackedXYBarRenderer;
import org.jfree.chart.ui.TextAnchor;
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
	
	private static final String BAR_CHART_TITLE = "柱状图";
	private static final String BAR_CHART_X_LABEL = "选项";
	private static final String BAR_CHART_Y_LABEL = "占比(%)";
	
	private static final String PIE_CHART_TITLE = "饼图";
	
	private static final String FONT_NAME = "黑体";//sans-serif  宋体
	private static final int FONT_SIZE = 40;
	private static final float CHAR_QUALITY = 0.5f;
	private static final int CHAR_WIDTH = 1200;
	private static final int CHAR_HEIGHT = 1200;
	
	public static void makeBarChart(String[] names, Double[] data, String imgPath) {
		//CategoryDataset ds = getDataSet();
		DefaultCategoryDataset ds = new DefaultCategoryDataset();
		for(int i = 0; i < names.length; i ++){
			//ds.addValue(value, rowKey, columnKey);
			ds.addValue(data[i], names[i]+":"+data[i], names[i]);
		}
		JFreeChart chart = ChartFactory.createBarChart(BAR_CHART_TITLE, BAR_CHART_X_LABEL, BAR_CHART_Y_LABEL, ds);
		CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
		CategoryAxis domainAxis = categoryplot.getDomainAxis();
		//设置X轴坐标上的文字
		domainAxis.setTickLabelFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));//sans-serif
		//设置X轴的标题文字
		domainAxis.setLabelFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));//宋体
		
		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		//设置Y轴坐标上的文字
		numberaxis.setTickLabelFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));//sans-serif
		//设置Y轴的标题文字
		numberaxis.setLabelFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));//黑体
		numberaxis.setUpperMargin(0.14999999999999999D);    
		numberaxis.setLowerBound(0.0);
		//numberaxis.setTickUnit(new NumberTickUnit(0.5));//设置Y轴间隔  
		numberaxis.setAutoTickUnitSelection(true);//取消柱子上的渐变色    
		
		//这句代码解决了底部汉字乱码的问题
		chart.getLegend().setItemFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));//宋体
		//这句代码解决了标题汉字乱码的问题 
		chart.getTitle().setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));//宋体
		//设置总的背景颜色   
		chart.setBackgroundPaint(ChartColor.white);   
		//显示横坐标详细信息
		chart.getLegend().setVisible(true);    
		
		BarRenderer renderer = new BarRenderer(); 
		//设置条目标签生成器
		//在JFreeChart1.0.6之前可以通过renderer.setItemLabelGenerator(CategoryItemLabelGenerator generator)方法实现
		//但是从版本1.0.6开始有下面方法代替
		renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setDefaultItemLabelFont(new Font(FONT_NAME,Font.PLAIN,FONT_SIZE));
		//显示条目标签
		renderer.setDefaultItemLabelsVisible(true);
		//设置条目标签显示的位置,outline表示在条目区域外,baseline_center表示基于基线且居中
		//renderer.setDefaultPositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.CENTER_LEFT));
		renderer.setDefaultPositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.CENTER));
		renderer.setItemLabelAnchorOffset(10);
		
		renderer.setSeriesPaint(0, new Color(0, 97, 183));   
		//取消柱子的阴影效果
		renderer.setShadowVisible(false);
		//设置不显示边框线
		//renderer.setDrawBarOutline(false);
		//设置每个地区所包含的平行柱的之间距离
		renderer.setItemMargin(0.1d);
		//设置柱子宽度 
		renderer.setMaximumBarWidth(20d);
		//设置柱子高度 
		renderer.setMinimumBarLength(0.00d); 
		categoryplot.setRenderer(renderer);//渲染
		
		//设置图的背景颜色     
		//categoryplot.setBackgroundPaint(ChartColor.white);  
		//设置图的边框      
		//categoryplot.setOutlinePaint(ChartColor.white);      
		//设置x轴网格线       
		//categoryplot.setDomainGridlinePaint(ChartColor.white);  
		//设置y轴网格线      
		//categoryplot.setRangeGridlinePaint(ChartColor.white);   
		
		/*XYBarRenderer customBarRenderer = (XYBarRenderer) categoryplot.getRenderer();   
		customBarRenderer.setDefaultItemLabelGenerator(new StandardXYItemLabelGenerator());     
		customBarRenderer.setSeriesItemLabelsVisible(0, true);        
		customBarRenderer.setBarPainter(new StandardXYBarPainter());//设置柱子颜色    
		customBarRenderer.setSeriesPaint(0, new Color(0, 97, 183));    */
		
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(imgPath);
			ChartUtils.writeChartAsJPEG(out, CHAR_QUALITY, chart, CHAR_WIDTH, CHAR_HEIGHT, null);
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
		JFreeChart chart = ChartFactory.createPieChart(PIE_CHART_TITLE, ds);
		//处理图表对象
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setSectionOutlinesVisible(false); //true
		//主标题乱码处理
		chart.getTitle().setFont(new Font(FONT_NAME, Font.BOLD, FONT_SIZE));//宋体
		//子标题乱码处理
		chart.getLegend().setItemFont(new Font(FONT_NAME, Font.BOLD, FONT_SIZE));//宋体
		//字体显示
		plot.setLabelFont(new Font(FONT_NAME, Font.BOLD, FONT_SIZE));//宋体
		//饼图详细信息显示
		//String labelFormat="{0}:{1} ({2})";name:data(百分比)
		String labelFormat="{0}:{1}";
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator(labelFormat));
		//plot.setlabel
		/*------这句代码解决了底部汉字乱码的问题-----------*/
		chart.getLegend().setItemFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));//宋体
		/******* 这句代码解决了标题汉字乱码的问题 ********/
		chart.getTitle().setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));//宋体
		
		
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(imgPath);
			ChartUtils.writeChartAsJPEG(out, CHAR_QUALITY, chart, CHAR_WIDTH, CHAR_HEIGHT, null);
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
