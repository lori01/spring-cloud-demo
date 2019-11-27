package com.daimeng.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PieLabelLinkStyle;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;


public class JFreeCharUtils {

	public static void main(String[] args) {
		String[] names = {"A选项","B选项","C选项","D选项","E选项"};
		Double[] data = {10d,20d,30d,50d,15d};
		makeBarChart(names, data, "D:/java_test/问卷调查test/柱状图1.jpg");
		makePieChart(names, data, "D:/java_test/问卷调查test/饼图1.jpg");
		Constants.println("success");
	}
	
	private static final String BAR_CHART_TITLE = "选项比例(%)柱状图";
	private static final String BAR_CHART_X_LABEL = "选项";
	private static final String BAR_CHART_Y_LABEL = "选项比例(%)";
	
	private static final String PIE_CHART_TITLE = "选项比例(%)饼图";
	private static final String NO_DATA_MSG = "没有数据";
	
	private static final String FONT_NAME = "黑体";//sans-serif  宋体
	private static final int FONT_SIZE = 40;
	private static final float CHAR_QUALITY = 0.5f;
	private static final int CHAR_WIDTH = 1200;
	private static final int CHAR_HEIGHT = 1200;
	
	static {
		setChartTheme();
	}

	public JFreeCharUtils() {
	}
	
	public static Color[] CHART_COLORS = {
		new Color(31,129,188), new Color(92,92,97), new Color(144,237,125), new Color(255,188,117),
		new Color(153,158,255), new Color(255,117,153), new Color(253,236,109), new Color(128,133,232),
		new Color(158,90,102),new Color(255, 204, 102) };// 颜色
	
	public static void makeBarChart(String[] names, Double[] data, String imgPath) {
		setChartTheme();
		//CategoryDataset ds = getDataSet();
		DefaultCategoryDataset ds = new DefaultCategoryDataset();
		for(int i = 0; i < names.length; i ++){
			//ds.addValue(value, rowKey, columnKey);
			ds.addValue(data[i], names[i], names[i]);
		}
		JFreeChart chart = ChartFactory.createBarChart(
				BAR_CHART_TITLE, //图表标题  
				BAR_CHART_X_LABEL, //目录轴的显示标签
				BAR_CHART_Y_LABEL, //数值轴的显示标签  
				ds,//数据集  
				PlotOrientation.VERTICAL, //图表方向  
				true, //是否显示图例，对于简单的柱状图必须为false  
				false, //是否生成提示工具  
				false //是否生成url链接  
				);
		CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
		setBarRenderer(categoryplot, true);
		
		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		//y轴精度 最大值和最小值
		numberaxis.setUpperBound(100);
		numberaxis.setLowerBound(0);
		
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(imgPath);
			ChartUtils.writeChartAsJPEG(out, CHAR_QUALITY, chart, CHAR_WIDTH, CHAR_HEIGHT, null);
			//ChartUtils.saveChartAsJPEG(new File("D:/java_test/问卷调查test/img-test-"+System.currentTimeMillis()+".jpg"), chart, CHAR_WIDTH, CHAR_HEIGHT);
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
		setChartTheme();
		DefaultPieDataset ds = new DefaultPieDataset();
		for(int i = 0; i < names.length; i ++){
			ds.setValue(names[i], data[i]);
		}
		JFreeChart chart = ChartFactory.createPieChart(PIE_CHART_TITLE, ds);
		//处理图表对象
		PiePlot plot = (PiePlot) chart.getPlot();
		setPieRender(plot);
		
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
	
	public static void makeBarChart2(String[] names, Double[] data, String imgPath) {
		//CategoryDataset ds = getDataSet();
		DefaultCategoryDataset ds = new DefaultCategoryDataset();
		for(int i = 0; i < names.length; i ++){
			//ds.addValue(value, rowKey, columnKey);
			ds.addValue(data[i], names[i]+":"+data[i], names[i]);
		}
		JFreeChart chart = ChartFactory.createBarChart(
				BAR_CHART_TITLE, //图表标题  
				BAR_CHART_X_LABEL, //目录轴的显示标签
				BAR_CHART_Y_LABEL, //数值轴的显示标签  
				ds,//数据集  
				PlotOrientation.VERTICAL, //图表方向  
				true, //是否显示图例，对于简单的柱状图必须为false  
				false, //是否生成提示工具  
				false //是否生成url链接  
				);
		CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
		setChartTheme();
		setBarRenderer(categoryplot, true);
		//X
		CategoryAxis domainAxis = categoryplot.getDomainAxis();
		//设置X轴坐标上的文字
		domainAxis.setTickLabelFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));//sans-serif
		//设置X轴的标题文字
		domainAxis.setLabelFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));//宋体
		
		//Y
		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		//设置Y轴坐标上的文字
		numberaxis.setTickLabelFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));//sans-serif
		//设置Y轴的标题文字
		numberaxis.setLabelFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));//黑体
		numberaxis.setUpperMargin(0.14999999999999999D);    
		numberaxis.setLowerBound(0.0);
		//numberaxis.setTickUnit(new NumberTickUnit(0.5));//设置Y轴间隔  
		numberaxis.setAutoTickUnitSelection(true);//取消柱子上的渐变色    
		// y轴精度 最大值和最小值
		numberaxis.setUpperBound(100);
		numberaxis.setLowerBound(0);
		//numberaxis.setNumberFormatOverride(new DecimalFormat("#0"));
		
		//这句代码解决了底部汉字乱码的问题
		chart.getLegend().setItemFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));//宋体
		//这句代码解决了标题汉字乱码的问题 
		chart.getTitle().setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));//宋体
		//设置总的背景颜色   
		chart.setBackgroundPaint(ChartColor.white);   
		//显示横坐标详细信息true展示 false不展示
		chart.getLegend().setVisible(false);    
		
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
		//柱状图上文字偏离度
		renderer.setItemLabelAnchorOffset(30D);
		// 设置柱的边框颜色
		renderer.setDefaultOutlinePaint(Color.BLACK);
		// 设置柱的边框可见
		renderer.setDrawBarOutline(true);
		//renderer.setSeriesPaint(0, ChartColor.BLACK);
		//renderer.setSeriesPaint(0, new Color(0, 97, 183));   
		/*renderer.setSeriesPaint(0, new Color(204, 255, 204));
		renderer.setSeriesPaint(1, new Color(255, 204, 153));*/
		
		//取消柱子的阴影效果
		renderer.setShadowVisible(false);
		//设置每个地区所包含的平行柱的之间距离
		renderer.setItemMargin(1/(names.length+1));
		//设置柱子宽度 
		renderer.setMaximumBarWidth(1);
		//设置柱子高度 
		renderer.setMinimumBarLength(0.00d); 
		
		//renderer.setAutoPopulateSeriesFillPaint(false);
		//renderer.setAutoPopulateSeriesOutlinePaint(false);
		//renderer.setAutoPopulateSeriesOutlineStroke(false);
		//柱状图颜色等属性默认
		//renderer.setAutoPopulateSeriesPaint(false);
		//renderer.setAutoPopulateSeriesStroke(false);
		//renderer.setAutoPopulateSeriesShape(false);
		
		
		categoryplot.setRenderer(renderer);//渲染
		
		//设置图的背景颜色     
		categoryplot.setBackgroundPaint(ChartColor.LIGHT_GRAY);  
		//设置图的边框      
		categoryplot.setOutlinePaint(ChartColor.LIGHT_GRAY);      
		//设置x轴网格线       
		categoryplot.setDomainGridlinePaint(ChartColor.BLACK);  
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
	public static void makePieChart2(String[] names, Double[] data, String imgPath) {
		//CategoryDataset ds = getDataSet();
		DefaultPieDataset ds = new DefaultPieDataset();
		for(int i = 0; i < names.length; i ++){
			ds.setValue(names[i], data[i]);
		}
		JFreeChart chart = ChartFactory.createPieChart(PIE_CHART_TITLE, ds);
		setChartTheme();
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
		//String labelFormat="{0}:{1} ({2})";//name:data(百分比)
		String labelFormat="{0}:{1}(%)";
		//设置图的背景颜色     
		plot.setBackgroundPaint(ChartColor.LIGHT_GRAY);  
		//设置图的边框      
		plot.setOutlinePaint(ChartColor.LIGHT_GRAY);      
		
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
	
	/**
	 * 中文主题样式 解决乱码
	 */
	public static void setChartTheme() {
		// 设置中文主题样式 解决乱码
		StandardChartTheme chartTheme = new StandardChartTheme("CN");
		// 设置标题字体
		chartTheme.setExtraLargeFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		// 设置图例的字体
		chartTheme.setRegularFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		// 设置轴向的字体
		chartTheme.setLargeFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		chartTheme.setSmallFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
		chartTheme.setTitlePaint(new Color(51, 51, 51));
		chartTheme.setSubtitlePaint(new Color(85, 85, 85));

		chartTheme.setLegendBackgroundPaint(Color.WHITE);// 设置标注
		chartTheme.setLegendItemPaint(Color.BLACK);//
		chartTheme.setChartBackgroundPaint(Color.WHITE);
		// 绘制颜色绘制颜色.轮廓供应商
		// paintSequence,outlinePaintSequence,strokeSequence,outlineStrokeSequence,shapeSequence

		Paint[] OUTLINE_PAINT_SEQUENCE = new Paint[] { Color.WHITE };
		// 绘制器颜色源
		DefaultDrawingSupplier drawingSupplier = new DefaultDrawingSupplier(CHART_COLORS, CHART_COLORS, OUTLINE_PAINT_SEQUENCE,
				DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE,
				DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE);
		chartTheme.setDrawingSupplier(drawingSupplier);

		chartTheme.setPlotBackgroundPaint(Color.WHITE);// 绘制区域
		chartTheme.setPlotOutlinePaint(Color.WHITE);// 绘制区域外边框
		chartTheme.setLabelLinkPaint(new Color(8, 55, 114));// 链接标签颜色
		chartTheme.setLabelLinkStyle(PieLabelLinkStyle.CUBIC_CURVE);

		chartTheme.setAxisOffset(new RectangleInsets(5, 12, 5, 12));
		chartTheme.setDomainGridlinePaint(new Color(192, 208, 224));// X坐标轴垂直网格颜色
		chartTheme.setRangeGridlinePaint(new Color(192, 192, 192));// Y坐标轴水平网格颜色

		chartTheme.setBaselinePaint(Color.WHITE);
		chartTheme.setCrosshairPaint(Color.BLUE);// 不确定含义
		chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
		chartTheme.setTickLabelPaint(new Color(67, 67, 72));// 刻度数字
		chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
		chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar 渲染

		chartTheme.setItemLabelPaint(Color.black);
		chartTheme.setThermometerPaint(Color.white);// 温度计

		ChartFactory.setChartTheme(chartTheme);
	}

	/**
	 * 必须设置文本抗锯齿
	 */
	public static void setAntiAlias(JFreeChart chart) {
		chart.setTextAntiAlias(false);

	}

	/**
	 * 设置图例无边框，默认黑色边框
	 */
	public static void setLegendEmptyBorder(JFreeChart chart) {
		chart.getLegend().setFrame(new BlockBorder(Color.WHITE));

	}
	
	/**
	 * 创建类别数据集合
	 */
	public static DefaultCategoryDataset createDefaultCategoryDataset(Vector<JFreeSerie> series, String[] categories) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (JFreeSerie serie : series) {
			String name = serie.getName();
			Vector<Object> data = serie.getData();
			if (data != null && categories != null && data.size() == categories.length) {
				for (int index = 0; index < data.size(); index++) {
					String value = data.get(index) == null ? "" : data.get(index).toString();
					if (isPercent(value)) {
						value = value.substring(0, value.length() - 1);
					}
					if (isNumber(value)) {
						dataset.setValue(Double.parseDouble(value), name, categories[index]);
					}
				}
			}

		}
		return dataset;

	}

	/**
	 * 创建饼图数据集合
	 */
	public static DefaultPieDataset createDefaultPieDataset(String[] categories, Object[] datas) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (int i = 0; i < categories.length && categories != null; i++) {
			String value = datas[i].toString();
			if (isPercent(value)) {
				value = value.substring(0, value.length() - 1);
			}
			if (isNumber(value)) {
				dataset.setValue(categories[i], Double.valueOf(value));
			}
		}
		return dataset;

	}

	/**
	 * 创建时间序列数据
	 * 
	 * @param category
	 *            类别
	 * @param dateValues
	 *            日期-值 数组
	 * @param xAxisTitle
	 *            X坐标轴标题
	 * @return
	 */
	public static TimeSeries createTimeseries(String category, Vector<Object[]> dateValues) {
		TimeSeries timeseries = new TimeSeries(category);

		if (dateValues != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			for (Object[] objects : dateValues) {
				Date date = null;
				try {
					date = dateFormat.parse(objects[0].toString());
				} catch (ParseException e) {
				}
				String sValue = objects[1].toString();
				double dValue = 0;
				if (date != null && isNumber(sValue)) {
					dValue = Double.parseDouble(sValue);
					timeseries.add(new Day(date), dValue);
				}
			}
		}

		return timeseries;
	}
	
	/**
	 * 设置 折线图样式
	 * 
	 * @param plot
	 * @param isShowDataLabels
	 *            是否显示数据标签 默认不显示节点形状
	 */
	public static void setLineRender(CategoryPlot plot, boolean isShowDataLabels) {
		setLineRender(plot, isShowDataLabels, false);
	}

	/**
	 * 设置折线图样式
	 * 
	 * @param plot
	 * @param isShowDataLabels
	 *            是否显示数据标签
	 */
	public static void setLineRender(CategoryPlot plot, boolean isShowDataLabels, boolean isShapesVisible) {
		plot.setNoDataMessage(NO_DATA_MSG);
		plot.setInsets(new RectangleInsets(10, 10, 0, 10), false);
		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();

		//renderer.setStroke(new BasicStroke(1.5F));
		if (isShowDataLabels) {
			renderer.setDefaultItemLabelsVisible(true);
			renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator(StandardCategoryItemLabelGenerator.DEFAULT_LABEL_FORMAT_STRING,
					NumberFormat.getInstance()));
			renderer.setDefaultPositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE1, TextAnchor.BOTTOM_CENTER));// weizhi
		}
		renderer.setDefaultShapesVisible(isShapesVisible);// 数据点绘制形状
		setXAixs(plot);
		setYAixs(plot);

	}

	/**
	 * 设置时间序列图样式
	 * 
	 * @param plot
	 * @param isShowData
	 *            是否显示数据
	 * @param isShapesVisible
	 *            是否显示数据节点形状
	 */
	public static void setTimeSeriesRender(Plot plot, boolean isShowData, boolean isShapesVisible) {

		XYPlot xyplot = (XYPlot) plot;
		xyplot.setNoDataMessage(NO_DATA_MSG);
		xyplot.setInsets(new RectangleInsets(10, 10, 5, 10));

		XYLineAndShapeRenderer xyRenderer = (XYLineAndShapeRenderer) xyplot.getRenderer();

		xyRenderer.setDefaultItemLabelGenerator(new StandardXYItemLabelGenerator());
		xyRenderer.setDefaultShapesVisible(false);
		if (isShowData) {
			xyRenderer.setDefaultItemLabelsVisible(true);
			xyRenderer.setDefaultItemLabelGenerator(new StandardXYItemLabelGenerator());
			xyRenderer.setDefaultPositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE1, TextAnchor.BOTTOM_CENTER));// weizhi
		}
		xyRenderer.setDefaultShapesVisible(isShapesVisible);// 数据点绘制形状

		DateAxis domainAxis = (DateAxis) xyplot.getDomainAxis();
		domainAxis.setAutoTickUnitSelection(false);
		DateTickUnit dateTickUnit = new DateTickUnit(DateTickUnitType.YEAR, 1, new SimpleDateFormat("yyyy-MM")); // 第二个参数是时间轴间距
		domainAxis.setTickUnit(dateTickUnit);

		StandardXYToolTipGenerator xyTooltipGenerator = new StandardXYToolTipGenerator("{1}:{2}", new SimpleDateFormat("yyyy-MM-dd"), new DecimalFormat("0"));
		xyRenderer.setDefaultToolTipGenerator(xyTooltipGenerator);

		setXY_XAixs(xyplot);
		setXY_YAixs(xyplot);

	}

	/**
	 * 设置时间序列图样式 -默认不显示数据节点形状
	 * 
	 * @param plot
	 * @param isShowData
	 *            是否显示数据
	 */

	public static void setTimeSeriesRender(Plot plot, boolean isShowData) {
		setTimeSeriesRender(plot, isShowData, false);
	}

	/**
	 * 设置时间序列图渲染：但是存在一个问题：如果timeseries里面的日期是按照天组织， 那么柱子的宽度会非常小，和直线一样粗细
	 * 
	 * @param plot
	 * @param isShowDataLabels
	 */

	public static void setTimeSeriesBarRender(Plot plot, boolean isShowDataLabels) {

		XYPlot xyplot = (XYPlot) plot;
		xyplot.setNoDataMessage(NO_DATA_MSG);

		XYBarRenderer xyRenderer = new XYBarRenderer(0.1D);
		xyRenderer.setDefaultItemLabelGenerator(new StandardXYItemLabelGenerator());

		if (isShowDataLabels) {
			xyRenderer.setDefaultItemLabelsVisible(true);
			xyRenderer.setDefaultItemLabelGenerator(new StandardXYItemLabelGenerator());
		}

		StandardXYToolTipGenerator xyTooltipGenerator = new StandardXYToolTipGenerator("{1}:{2}", new SimpleDateFormat("yyyy-MM-dd"), new DecimalFormat("0"));
		xyRenderer.setDefaultToolTipGenerator(xyTooltipGenerator);
		setXY_XAixs(xyplot);
		setXY_YAixs(xyplot);

	}

	/**
	 * 设置柱状图渲染
	 * 
	 * @param plot
	 * @param isShowDataLabels
	 */
	public static void setBarRenderer(CategoryPlot plot, boolean isShowDataLabels) {

		plot.setNoDataMessage(NO_DATA_MSG);
		plot.setInsets(new RectangleInsets(10, 10, 5, 10));
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setMaximumBarWidth(0.2);// 设置柱子最大宽度

		if (isShowDataLabels) {
			renderer.setDefaultItemLabelsVisible(true);
		}

		setXAixs(plot);
		setYAixs(plot);
	}

	/**
	 * 设置堆积柱状图渲染
	 * 
	 * @param plot
	 */

	public static void setStackBarRender(CategoryPlot plot) {
		plot.setNoDataMessage(NO_DATA_MSG);
		plot.setInsets(new RectangleInsets(10, 10, 5, 10));
		StackedBarRenderer renderer = (StackedBarRenderer) plot.getRenderer();
		renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		plot.setRenderer(renderer);
		setXAixs(plot);
		setYAixs(plot);
	}

	/**
	 * 设置类别图表(CategoryPlot) X坐标轴线条颜色和样式
	 * 
	 * @param axis
	 */
	public static void setXAixs(CategoryPlot plot) {
		Color lineColor = new Color(31, 121, 170);
		plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
		plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色

	}

	/**
	 * 设置类别图表(CategoryPlot) Y坐标轴线条颜色和样式 同时防止数据无法显示
	 * 
	 * @param axis
	 */
	public static void setYAixs(CategoryPlot plot) {
		Color lineColor = new Color(192, 208, 224);
		ValueAxis axis = plot.getRangeAxis();
		axis.setAxisLinePaint(lineColor);// Y坐标轴颜色
		axis.setTickMarkPaint(lineColor);// Y坐标轴标记|竖线颜色
		// 隐藏Y刻度
		axis.setAxisLineVisible(false);
		axis.setTickMarksVisible(false);
		// Y轴网格线条
		plot.setRangeGridlinePaint(new Color(192, 192, 192));
		plot.setRangeGridlineStroke(new BasicStroke(1));

		plot.getRangeAxis().setUpperMargin(0.1);// 设置顶部Y坐标轴间距,防止数据无法显示
		plot.getRangeAxis().setLowerMargin(0.1);// 设置底部Y坐标轴间距

	}

	/**
	 * 设置XY图表(XYPlot) X坐标轴线条颜色和样式
	 * 
	 * @param axis
	 */
	public static void setXY_XAixs(XYPlot plot) {
		Color lineColor = new Color(31, 121, 170);
		plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
		plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色

	}

	/**
	 * 设置XY图表(XYPlot) Y坐标轴线条颜色和样式 同时防止数据无法显示
	 * 
	 * @param axis
	 */
	public static void setXY_YAixs(XYPlot plot) {
		Color lineColor = new Color(192, 208, 224);
		ValueAxis axis = plot.getRangeAxis();
		axis.setAxisLinePaint(lineColor);// X坐标轴颜色
		axis.setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色
		// 隐藏Y刻度
		axis.setAxisLineVisible(false);
		axis.setTickMarksVisible(false);
		// Y轴网格线条
		plot.setRangeGridlinePaint(new Color(192, 192, 192));
		plot.setRangeGridlineStroke(new BasicStroke(1));
		plot.setDomainGridlinesVisible(false);

		plot.getRangeAxis().setUpperMargin(0.12);// 设置顶部Y坐标轴间距,防止数据无法显示
		plot.getRangeAxis().setLowerMargin(0.12);// 设置底部Y坐标轴间距

	}

	/**
	 * 设置饼状图渲染
	 */
	public static void setPieRender(Plot plot) {

		plot.setNoDataMessage(NO_DATA_MSG);
		plot.setInsets(new RectangleInsets(10, 10, 5, 10));
		PiePlot piePlot = (PiePlot) plot;
		piePlot.setInsets(new RectangleInsets(0, 0, 0, 0));
		piePlot.setCircular(true);// 圆形

		// piePlot.setSimpleLabels(true);// 简单标签
		piePlot.setLabelGap(0.01);
		piePlot.setInteriorGap(0.05D);
		piePlot.setLegendItemShape(new Rectangle(10, 10));// 图例形状
		piePlot.setIgnoreNullValues(true);
		piePlot.setLabelBackgroundPaint(null);// 去掉背景色
		piePlot.setLabelShadowPaint(null);// 去掉阴影
		piePlot.setLabelOutlinePaint(null);// 去掉边框
		piePlot.setShadowPaint(null);
		// 0:category 1:value:2 :percentage
		piePlot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{2}"));// 显示标签数据
	}

	/**
	 * 是不是一个%形式的百分比
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isPercent(String str) {
		return str != null ? str.endsWith("%") && isNumber(str.substring(0, str.length() - 1)) : false;
	}

	/**
	 * 是不是一个数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		return str != null ? str.matches("^[-+]?(([0-9]+)((([.]{0})([0-9]*))|(([.]{1})([0-9]+))))$") : false;
	}
}
