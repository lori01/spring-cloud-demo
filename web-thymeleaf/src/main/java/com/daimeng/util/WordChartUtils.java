package com.daimeng.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFChart;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRelation;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTAxDataSource;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTBarChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTBarSer;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTNumData;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTNumDataSource;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTNumVal;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPlotArea;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTStrData;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTStrVal;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTColor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHpsMeasure;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTOnOff;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTParaRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVerticalJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STOnOff;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;

public class WordChartUtils {
	
	//调查问卷基本信息存放对象名字
	private static final String RSK_BSC_OBJ_NAME = "object_01";
	//问卷题目列表
	private static final String RSK_TABLE_NAME = "table_01";
	//题目内选项列表
	private static final String DEFAULT_TABLE_NAME = "table_02";
	//排序题每个排序排行的列表
	private static final String OPT_SORT_TABLE_NAME = "table_03";
	//排序题每个排序排行的列表
	private static final String OPT_SORT_TABLE_DTL_NAME = "table_03_DTL";
	//排序题每个排序统计的展示形式
	private static final String OPT_SORT_SHOW_TYPE_NAME = "table_04";
	//图表图片生成地址
	private static String CHART_IMG_PATH = "D:/java_test/问卷调查test/";
	
	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		SimpleDateFormat sdf_datetime_format = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = sdf_datetime_format.format(Calendar.getInstance().getTime());
		
		//final String templateurl="D:/java_test/问卷调查test/Word_Test.docx";
		final String templateurl="D:/java_test/问卷调查test/Word_Test_2.docx";
		
		final String returnurl="D:/java_test/问卷调查test/Word_Test_" + date + ".docx";
		InputStream is = new FileInputStream(new File(templateurl));
		XWPFDocument doc = new XWPFDocument(is);
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		//标题 描述
		map.put("title", "惠助你使用体验调查问卷");
		map.put("desc", "这是第一个调查问卷，他的功能是为了调查惠助你上线后得使用心得和使用体验。请各位参与调查得用户认真作答，您的得答案将会作为后续惠助你APP得改进依据，谢谢。");
		HashMap<String,Object> bascInf = new HashMap<String,Object>();
		bascInf.put("stDt", "2019-10-01");//生效时间
		bascInf.put("enDt", "2019-10-31");//失效时间
		bascInf.put("peopleCount", "150");//人数统计
		bascInf.put("type", "惠助你");//发布渠道
		
		map.put(RSK_BSC_OBJ_NAME, bascInf);
		
		//题目列表
		ArrayList<HashMap<String,Object>> questionList = new ArrayList<HashMap<String,Object>>();
		//题目
		for(int i = 0; i < 20; i++){
			HashMap<String,Object> question = new HashMap<String,Object>();
			//序号，自动生成
			int no = i + 1;
			//展示类型 01-表格 02-柱状图 03-饼图 ,需要传参
			String showType = "0"+(i%3+1);
			//题目类型 01-单选题 02-多选题 03-问答题 04-排序题 ,需要传参
			String selType = "0"+(i%4+1);
			//题目,需要传参
			String title = "第" + no + "个问题的答案是什么?";
			if(selType == null || "".equals(selType) || "01".equals(selType)){
				title += "【单选题】";
			}else if("02".equals(selType)){
				title += "【多选题】";
			}else if("03".equals(selType)){
				title += "【问答题】";
			}else if("04".equals(selType)){
				title += "【排序题】";
			}
			//题目展示类容数据填充
			question.put("no", String.valueOf(no));
			question.put("title", title);
			question.put("showType", showType); 
			question.put("selType", selType); 
			//单选题 多选题 选项赋值
			if(selType == null || "".equals(selType) || "01".equals(selType) || "02".equals(selType)){
				ArrayList<HashMap<String,Object>> answerList = new ArrayList<HashMap<String,Object>>();
				for(int j = 0 ; j < 4; j ++){
					HashMap<String,Object> answer = new HashMap<String,Object>();
					answer.put("no", numberToLetter(j+1));//数字index转成ABCD的字母
					answer.put("title", "这个是第"+(i+1)+"个问题的第"+(j+1)+"个答案。");
					double random = Math.random();
					answer.put("count", String.valueOf(10000*random));//数量
					answer.put("ratio", String.valueOf(100*random));//比例
					answerList.add(answer);
				}
				question.put(DEFAULT_TABLE_NAME, answerList);
			}
			//排序题选项赋值
			else if("04".equals(selType)){
				//综合评分数据
				ArrayList<HashMap<String,Object>> answerList = new ArrayList<HashMap<String,Object>>();
				for(int j = 0 ; j < 4; j ++){
					HashMap<String,Object> answer = new HashMap<String,Object>();
					answer.put("no", numberToLetter(j+1));//数字index转成ABCD的字母
					answer.put("title", "这个是第"+(i+1)+"个问题的第"+(j+1)+"个答案。");
					double random = Math.random();
					answer.put("count", String.valueOf(10000*random));//综合评分
					answer.put("ratio", String.valueOf(10000*random));//综合评分
					answerList.add(answer);
				}
				question.put(DEFAULT_TABLE_NAME, answerList);
				
				//每题的排序数据
				ArrayList<HashMap<String,Object>> answerTotleList = new ArrayList<HashMap<String,Object>>();
				//选项题目
				for(int j = 0 ; j < 4; j ++){
					HashMap<String,Object> answer = new HashMap<String,Object>();
					answer.put("no", numberToLetter(j+1));//数字index转成ABCD的字母
					answer.put("title", "这个是第"+(i+1)+"个问题的第"+(j+1)+"个答案。");
					//选项排序名次列表
					ArrayList<HashMap<String,Object>> answerTotleDtlList = new ArrayList<HashMap<String,Object>>();
					for(int k = 0; k < 4; k++){
						HashMap<String,Object> dtl = new HashMap<String,Object>();
						double random = Math.random();
						dtl.put("count", String.valueOf(10000*random));//数量
						dtl.put("ratio", String.valueOf(100*random));//比例
						answerTotleDtlList.add(dtl);
					}
					answer.put(OPT_SORT_TABLE_DTL_NAME, answerTotleDtlList);
					answerTotleList.add(answer);
				}
				question.put(OPT_SORT_TABLE_NAME, answerTotleList);
				
				//每题的排序数据的展示方式
				ArrayList<HashMap<String,Object>> showTypeList = new ArrayList<HashMap<String,Object>>();
				for(int j = 0 ; j < 4; j ++){
					HashMap<String,Object> showTypeOjb = new HashMap<String,Object>();
					showTypeOjb.put("showType", "0"+(i%3+1));//数字index转成ABCD的字母
					showTypeList.add(showTypeOjb);
				}
				question.put(OPT_SORT_SHOW_TYPE_NAME, showTypeList);
			}
			questionList.add(question);
		}
		map.put(RSK_TABLE_NAME, questionList);
		
		replaceAllRskWord(doc,map);
		//文件存在删除
		try {
			File file = new File(returnurl);
			if (file.exists()) {
				file.delete();
			}
		FileOutputStream fos = new FileOutputStream(returnurl);
		doc.write(fos);
		fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("end,消耗="+(end-start));
		System.out.println("url="+returnurl);
	}
	/**
	 * 
	* @功能描述: 数字转字母
	* @方法名称: numberToLetter 
	* @路径 com.daimeng.util 
	* @作者 daimeng.fun
	* @E-Mail sephy9527@qq.com
	* @创建时间 2019年11月29日 下午2:28:22 
	* @version V1.0   
	* @param num
	* @return 
	* @return String
	 */
	public static String numberToLetter(int num) {
	    if (num <= 0) { // 检测列数是否正确
	        return null;
	    }
	    StringBuffer letter = new StringBuffer();
	    do {
	        --num;
	        int mod = num % 26; // 取余
	        letter.append((char) (mod + 'A')); // 组装字符串
	        num = (num - mod) / 26; // 计算剩下值
	    } while (num > 0);
	    return letter.reverse().toString(); // 返回反转后的字符串
	}
	
	public static void replaceAllRskWord(XWPFDocument doc, HashMap<String,Object> map){
		replaceText(doc,map);
		replaceTable(doc,map);
		//获取所有题目的列表
		ArrayList<HashMap<String,Object>> rskList = (ArrayList<HashMap<String,Object>>)map.get(RSK_TABLE_NAME);
		for(HashMap<String,Object> rsk : rskList){
			String title = "";
			if(rsk.get("no") != null){
				title += (String) rsk.get("no") + ".";
			}
			if(rsk.get("title") != null){
				title +=  (String) rsk.get("title");
			}
			String peopleCount = "0";
			if(map.get(RSK_BSC_OBJ_NAME) != null && ((HashMap<String,Object>)map.get(RSK_BSC_OBJ_NAME)).get("peopleCount") != null){
				peopleCount = (String) ((HashMap<String,Object>)map.get(RSK_BSC_OBJ_NAME)).get("peopleCount");
			}
			try {
				createTable(doc, title,peopleCount, rsk);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println(e.getLocalizedMessage());
			}
			
		}
		//List<XWPFChart> charList = doc.getCharts();
		//List<POIXMLDocumentPart> realtionsList = doc.getRelations();
		
	}
	/**
	 * 
	* @功能描述: 获取document最下面的坐标
	* @方法名称: getBottomCurrsor 
	* @路径 com.daimeng.util 
	* @作者 daimeng.fun
	* @E-Mail sephy9527@qq.com
	* @创建时间 2019年11月26日 下午4:54:04 
	* @version V1.0   
	* @param doc
	* @return 
	* @return XmlCursor
	 */
	public static XmlCursor getBottomCurrsor(XWPFDocument doc){
		List<XWPFParagraph> paragraphList = doc.getParagraphs();
		return paragraphList.get(paragraphList.size()-1).getCTP().newCursor();
	}
	public static void createDefaultText(XWPFDocument doc,HashMap<String,Object> map){
		XWPFParagraph paragraph = doc.insertNewParagraph(getBottomCurrsor(doc));
		replaceParagraph(paragraph, "");
		//return paragraph.getCTP().newCursor();
	}
	
	public static void createTable(XWPFDocument doc,String title,String peopleCount,HashMap<String,Object> map){
		//创建标题
		if(title != null && !"".equals(title)){
			XWPFParagraph paragraph = doc.insertNewParagraph(getBottomCurrsor(doc));
			replaceParagraph(paragraph, title);
        }
		String selType = "01";
		if(map.get("selType") != null){
			selType = (String)map.get("selType");
		}
		String showType = "01";
		if(map.get("showType") != null){
			showType = (String)map.get("showType");
		}
		
		//单选题和多选题的数据格式
		if(selType == null || "".equals(selType) || "01".equals(selType) || "02".equals(selType)){
			//table_02:[{no,count,per},{no,count,per},{no,count,per}...]
			//获取对象中的选项统计列表
			List<HashMap<String,Object>> answerList = (List<HashMap<String,Object>>) map.get(DEFAULT_TABLE_NAME);
			if(showType == null || "01".equals(showType)){
				createDefaultTable(doc, answerList,peopleCount);
			}else if(showType != null && ("02".equals(showType) || "03".equals(showType))){
				drawChartsToWord(doc, showType, answerList);
			}
		}
		//排序题
		else if(selType != null && "04".equals(selType)){
			//获取排序题的加权分数列表
			//table_02:[{no,count},{no,count},{no,count}...]
			List<HashMap<String,Object>> answerList = (List<HashMap<String,Object>>) map.get(DEFAULT_TABLE_NAME);
			if(showType == null || "01".equals(showType)){
				createSortTable(doc, answerList,peopleCount);
			}else if(showType != null && ("02".equals(showType) || "03".equals(showType))){
				drawChartsToWord(doc, showType, answerList);
			}
			//排序题内部每个排位的列表展示
			List<HashMap<String,Object>> sortList = (List<HashMap<String,Object>>) map.get(OPT_SORT_TABLE_NAME);
			List<HashMap<String,Object>> showTypeList = (List<HashMap<String,Object>>) map.get(OPT_SORT_SHOW_TYPE_NAME);
			int optCount = answerList.size();//选项数量
			//排序题数量要等于选项数量等于showtype数量，否则数据对不上
			if(optCount == sortList.size() && optCount == showTypeList.size()){
				//排序
				for(int i = 0; i < optCount; i ++){
					String newtitle = "选项排在第"+(i+1)+"位的统计";
					List<HashMap<String,Object>> tmpList = new ArrayList<HashMap<String,Object>>();
					//选项
					/*
					 * [
{"no":"1","title":"第一个选项","OPT_SORT_TABLE_DTL_NAME":[{"sort":"1","count":"100","ratio":"10"},{"sort":"2","count":"100","ratio":"10"},{"sort":"3","count":"100","ratio":"10"},{"sort":"4","count":"100","ratio":"10"}]},
{"no":"2","title":"第二个选项","OPT_SORT_TABLE_DTL_NAME":[{"sort":"1","count":"100","ratio":"10"},{"sort":"2","count":"100","ratio":"10"},{"sort":"3","count":"100","ratio":"10"},{"sort":"4","count":"100","ratio":"10"}]},
{"no":"3","title":"第三个选项","OPT_SORT_TABLE_DTL_NAME":[{"sort":"1","count":"100","ratio":"10"},{"sort":"2","count":"100","ratio":"10"},{"sort":"3","count":"100","ratio":"10"},{"sort":"4","count":"100","ratio":"10"}]},
{"no":"4","title":"第四个选项","OPT_SORT_TABLE_DTL_NAME":[{"sort":"1","count":"100","ratio":"10"},{"sort":"2","count":"100","ratio":"10"},{"sort":"3","count":"100","ratio":"10"},{"sort":"4","count":"100","ratio":"10"}]}
]
					 */
					for(int j = 0; j < optCount; j ++){
						List<HashMap<String,Object>> sortOptList = new ArrayList<HashMap<String,Object>>();
						if(sortList.get(j).get(OPT_SORT_TABLE_DTL_NAME) != null){
							sortOptList = (List<HashMap<String,Object>>) sortList.get(j).get(OPT_SORT_TABLE_DTL_NAME);
						}
						if(sortOptList.size() == optCount){
							HashMap<String,Object> dtl = new HashMap<String,Object>();
							if(sortOptList.get(i) != null){
								dtl = (HashMap<String,Object>) sortOptList.get(i);
								dtl.put("no", sortList.get(j).get("no"));
								dtl.put("title", sortList.get(j).get("title"));
								tmpList.add(dtl);
							}
						}
					}
					XWPFParagraph paragraph = doc.insertNewParagraph(getBottomCurrsor(doc));
					replaceParagraph(paragraph, newtitle);
					String showTypeSort = "";
					if(showTypeList.get(i) != null && showTypeList.get(i).get("showType") != null){
						showTypeSort = (String) showTypeList.get(i).get("showType");
					}
					if(showTypeSort == null || "01".equals(showTypeSort)){
						createDefaultTable(doc, tmpList,peopleCount);
					}else if(showTypeSort != null && ("02".equals(showTypeSort) || "03".equals(showTypeSort))){
						drawChartsToWord(doc, showTypeSort, tmpList);
					}
				}
			}
		}
	}
	
	public static void drawChartsToWord(XWPFDocument doc,String showType,List<HashMap<String,Object>> list){
		String name = "img-"+showType+"-"+System.currentTimeMillis()+".jpg";
		String imgPath = CHART_IMG_PATH +name;
		drawCharts(imgPath, showType, list);
		XWPFParagraph paragraph = doc.insertNewParagraph(getBottomCurrsor(doc));
		XWPFRun run = paragraph.createRun();
		//(pictureData, pictureType, filename, width, height)
		try {
			//title
			/*List<String> titleArr = new ArrayList<String>();// 标题
			titleArr.add("选项内容");
			titleArr.add("选项比例(%)");
			List<String> fldNameArr = new ArrayList<String>();// 字段名
			fldNameArr.add("no");
			fldNameArr.add("per");
			List<Map<String, String>> listItemsByType = new ArrayList<Map<String, String>>();
			for(HashMap<String,Object> map : list){
				Map<String, String> base = new HashMap<String, String>();
				base.put("no", (String)map.get("no"));
				base.put("per", (String)map.get("per"));
				listItemsByType.add(base);
			}
			
			// 获取word模板中的所有图表元素，用数组存放
	        List<POIXMLDocumentPart> chartsList = new ArrayList<POIXMLDocumentPart>();
	        //动态刷新图表
	        List<POIXMLDocumentPart> relations = doc.getRelations();
	        for (POIXMLDocumentPart poixmlDocumentPart : relations) {
	            if (poixmlDocumentPart instanceof XWPFChart) {  // 如果是图表元素
	                chartsList.add(poixmlDocumentPart);
	            }
	        }
	        XWPFChart chart = WordPoiTools.replaceBarCharts(chartsList.get(0), titleArr, fldNameArr, listItemsByType);
	        //doc.addRelation("1", XWPFRelation.CHART, chart);
*/	        
			run.addPicture(new FileInputStream(imgPath), 
					Document.PICTURE_TYPE_JPEG, 
					name, 
					Units.toEMU(400), Units.toEMU(400));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 
	* @功能描述: 使用JFreeCharts生成图表图片
	* @方法名称: drawCharts 
	* @路径 com.daimeng.util 
	* @作者 daimeng.fun
	* @E-Mail sephy9527@qq.com
	* @创建时间 2019年11月26日 下午6:05:52 
	* @version V1.0   
	* @param imgPath
	* @param showType
	* @param list 
	* @return void
	 */
	public static void drawCharts(String imgPath,String showType,List<HashMap<String,Object>> list){
		if(list != null && list.size() > 0){
			String[] names = new String[list.size()];
			Double[] data = new Double[list.size()];
			for(int k = 0 ; k < list.size(); k++){
				HashMap<String,Object> map = list.get(k);
				names[k] = (String) map.get("title");
				data[k] = Double.valueOf((String) map.get("ratio"));
			}
			if("02".equals(showType)){
				JFreeCharUtils.makeBarChart(names, data, imgPath);
			}else{
				JFreeCharUtils.makePieChart(names, data, imgPath);
			}
		}
	}
	/**
	 * 
	* @功能描述: 创建默认表格，即选项、数量、占比
	* @方法名称: createDefaultTable 
	* @路径 com.daimeng.util 
	* @作者 daimeng.fun
	* @E-Mail sephy9527@qq.com
	* @创建时间 2019年11月26日 下午5:27:26 
	* @version V1.0   
	* @param doc
	* @param title
	* @param map
	* @param tableName 
	* @return void
	 */
	public static void createDefaultTable(XWPFDocument doc,List<HashMap<String,Object>> list,String peopleCount){
		XWPFTable table = doc.insertNewTbl(getBottomCurrsor(doc));// ---这个是关键
		//设置表格宽度，第一行宽度就可以了，这个值的单位，目前我也还不清楚，还没来得及研究
        table.setWidth(8500);
        //表格第一行，对于每个列，必须使用createCell()，而不是getCell()，因为第一行嘛，肯定是属于创建的，没有create哪里来的get呢
        XWPFTableRow row1 = table.getRow(0);
        WordPoiTools.setDefaultWordCellSelfStyle(row1.getCell(0), 100, "选项");
        WordPoiTools.setDefaultWordCellSelfStyle(row1.createCell(), 100, "选项内容");
        WordPoiTools.setDefaultWordCellSelfStyle(row1.createCell(), 100, "数量");
        WordPoiTools.setDefaultWordCellSelfStyle(row1.createCell(), 100, "比例");

        for(HashMap<String,Object> obj : list){
        	String no = "";
        	String title = "";
        	String count = "";
        	String ratio = "";
        	if(obj.get("no") != null){
        		no = (String)obj.get("no");
        	}
        	if(obj.get("title") != null){
        		title = (String)obj.get("title");
        	}
        	if(obj.get("count") != null){
        		count = (String)obj.get("count");
        	}
        	if(obj.get("ratio") != null){
        		ratio = (String)obj.get("ratio");
        	}
        	XWPFTableRow tmpRow = table.createRow();//行
        	WordPoiTools.setDefaultWordCellSelfStyle(tmpRow.getCell(0), 100, no);
        	WordPoiTools.setDefaultWordCellSelfStyle(tmpRow.getCell(1), 100, title);
        	WordPoiTools.setDefaultWordCellSelfStyle(tmpRow.getCell(2), 100, count);
        	WordPoiTools.setDefaultWordCellSelfStyle(tmpRow.getCell(3), 100, ratio);
        }
        XWPFTableRow tmpRow = table.createRow();//行
    	WordPoiTools.setDefaultWordCellSelfStyle(tmpRow.getCell(0), 100, "");
    	WordPoiTools.setDefaultWordCellSelfStyle(tmpRow.getCell(1), 100, "<有效填写人数>");
    	WordPoiTools.setDefaultWordCellSelfStyle(tmpRow.getCell(2), 100, peopleCount);
    	WordPoiTools.setDefaultWordCellSelfStyle(tmpRow.getCell(3), 100, "");
        //return table.getCTTbl().newCursor();
	}
	/**
	 * 
	* @功能描述: 创建排序表格，即选项、加权得分
	* @方法名称: createSortTable 
	* @路径 com.daimeng.util 
	* @作者 daimeng.fun
	* @E-Mail sephy9527@qq.com
	* @创建时间 2019年11月26日 下午5:27:59 
	* @version V1.0   
	* @param doc
	* @param cursor
	* @param title
	* @param map
	* @param tableName 
	* @return void
	 */
	public static void createSortTable(XWPFDocument doc,List<HashMap<String,Object>> list,String peopleCount){
		XWPFTable table = doc.insertNewTbl(getBottomCurrsor(doc));// ---这个是关键
		//设置表格宽度，第一行宽度就可以了，这个值的单位，目前我也还不清楚，还没来得及研究
        table.setWidth(8500);
        //表格第一行，对于每个列，必须使用createCell()，而不是getCell()，因为第一行嘛，肯定是属于创建的，没有create哪里来的get呢
        XWPFTableRow row1 = table.getRow(0);
        WordPoiTools.setDefaultWordCellSelfStyle(row1.getCell(0), 100, "选项");
        WordPoiTools.setDefaultWordCellSelfStyle(row1.createCell(), 100, "选项内容");
        WordPoiTools.setDefaultWordCellSelfStyle(row1.createCell(), 100, "综合得分");

        for(HashMap<String,Object> obj : list){
        	String no = "";
        	String title = "";
        	String count = "";
        	if(obj.get("no") != null){
        		no = (String)obj.get("no");
        	}
        	if(obj.get("title") != null){
        		title = (String)obj.get("title");
        	}
        	if(obj.get("count") != null){
        		count = (String)obj.get("count");
        	}
        	XWPFTableRow tmpRow = table.createRow();//行
        	WordPoiTools.setDefaultWordCellSelfStyle(tmpRow.getCell(0), 100, no);
        	WordPoiTools.setDefaultWordCellSelfStyle(tmpRow.getCell(1), 100, title);
        	WordPoiTools.setDefaultWordCellSelfStyle(tmpRow.getCell(2), 100, count);
        }
        XWPFTableRow tmpRow = table.createRow();//行
    	WordPoiTools.setDefaultWordCellSelfStyle(tmpRow.getCell(0), 100, "");
    	WordPoiTools.setDefaultWordCellSelfStyle(tmpRow.getCell(1), 100, "<有效填写人数>");
    	WordPoiTools.setDefaultWordCellSelfStyle(tmpRow.getCell(2), 100, peopleCount);
        //return table.getCTTbl().newCursor();
	}
	/**
	 * 
	* @功能描述: 替换word中${}的数据位map中对应的字段
	* @方法名称: replaceText 
	* @路径 com.daimeng.util 
	* @作者 daimeng.fun
	* @E-Mail sephy9527@qq.com
	* @创建时间 2019年11月26日 下午6:01:03 
	* @version V1.0   
	* @param doc
	* @param map 
	* @return void
	 */
	public static void replaceText(XWPFDocument doc,HashMap<String,Object> map){
		Constants.println("======replaceText start======");
		List<XWPFParagraph> paragraphList = doc.getParagraphs();
		for(XWPFParagraph paragraph : paragraphList){
			String paragraphText = paragraph.getText();
			String newText = getNewText(paragraphText, map);
			replaceParagraph(paragraph, newText);
		}
		Constants.println("======replaceText end======");
	}
	/**
	 * 
	* @功能描述: 获取替换后新的text
	* @方法名称: getNewText 
	* @路径 com.daimeng.util 
	* @作者 daimeng.fun
	* @E-Mail sephy9527@qq.com
	* @创建时间 2019年11月26日 下午6:01:32 
	* @version V1.0   
	* @param value
	* @param dataMap
	* @return 
	* @return String
	 */
	public static String getNewText(String value,HashMap<String,Object> dataMap){
		if(value.contains("${") && value.contains("}")){
			//获取所有${}字段
			ArrayList<String> pars = getParamFromDollar(value);
			for(String parameter : pars){
				if(dataMap.get(parameter) != null){
					value = value.replace("${"+parameter+"}",(String)dataMap.get(parameter));
					//Constants.println("将"+parameter+"字段替换为"+(String)dataMap.get(parameter));
				}
			}
		}
		return value;
	}
	
	/**
	 * 
	* @功能描述: 获取${name}字符串里面的name  一个context里面可能有多个
	* @方法名称: getParamFromDollar 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月19日 下午4:20:04 
	* @version V1.0   
	* @param context
	* @return 
	* @return ArrayList<String>
	 */
	public static ArrayList<String> getParamFromDollar(String context){
		Pattern p = Pattern.compile("\\$\\{(.*?)\\}");
		Matcher m = p.matcher(context);
		ArrayList<String> result=new ArrayList<String>();
		while(m.find()){
			result.add(m.group(1));
		}
		return result;
	}
	
	/**
	 * 
	* @功能描述: 替换table中的${}数据，如果是对象则替换一次，如果是列表，则循环增加行数
	* @方法名称: replaceTable 
	* @路径 com.daimeng.util 
	* @作者 daimeng.fun
	* @E-Mail sephy9527@qq.com
	* @创建时间 2019年11月26日 下午6:02:10 
	* @version V1.0   
	* @param doc
	* @param map 
	* @return void
	 */
	public static void replaceTable(XWPFDocument doc,HashMap<String,Object> map){
		Constants.println("======createTable start======");
		List<XWPFTable> tableList = doc.getTables();
		for(XWPFTable table : tableList){
			//String tableName = table.getRow(0).getCell(0).getText().replace("${", "").replace("}", "");
			String tableName = getTableName(table,"table");
			if(tableName == null || "".equals(tableName)){
				tableName = getTableName(table,"object");
			}
			Constants.println("table name="+tableName);
			if(map.get(tableName) != null){
				//如果是object，替换一次
				if(tableName.startsWith("object")){
					HashMap<String,Object> obj = (HashMap<String,Object>) map.get(tableName);
					replaceTable(doc, table, obj,tableName, 0);
				}
				//如果是table，则循环list加载table的行
				else if(tableName.startsWith("table")){
					List<HashMap<String,Object>> list = (List<HashMap<String,Object>>) map.get(tableName);
					List<XWPFTableRow> rowList = table.getRows();
					int rowCount = table.getNumberOfRows();
					for(HashMap<String,Object> obj : list){
						for(int i = 0; i < rowCount; i ++){
							XWPFTableRow r = rowList.get(i);
							copyRow(table, r, rowList.size());
						}
						replaceTable(doc, table, obj, tableName, rowCount);
					}
					//删除最开始的模板，否则下次会继续循环
					for(int i = 0; i < rowCount; i ++){
						table.removeRow(0);
					}
				}
			}
		}
		Constants.println("======createTable end======");
	}
	/**
	 * 
	* @功能描述: 从table中获取table name
	* @方法名称: getTableName 
	* @路径 com.daimeng.util 
	* @作者 daimeng.fun
	* @E-Mail sephy9527@qq.com
	* @创建时间 2019年11月26日 下午6:05:01 
	* @version V1.0   
	* @param table
	* @param key
	* @return 
	* @return String
	 */
	public static String getTableName(XWPFTable table, String key){
		List<XWPFTableRow> rowList = table.getRows();
		for(XWPFTableRow row : rowList){
			List<XWPFTableCell> cellList = row.getTableCells();
			for(int i = 0; i < cellList.size(); i ++){
				String text = cellList.get(i).getText();
				if(text.startsWith("${") && text.indexOf("${"+key+"_") > -1){
					return text.substring(text.indexOf("${"+key+"_")+2,text.indexOf("${"+key+"_")+key.length()+5);
				}
			}
		}
		return "";
	}
	/**
	 * 
	* @功能描述: 替换表格中cell的内容
	* @方法名称: replaceCell 
	* @路径 com.daimeng.util 
	* @作者 daimeng.fun
	* @E-Mail sephy9527@qq.com
	* @创建时间 2019年11月24日 下午4:02:10 
	* @version V1.0   
	* @param cell
	* @param text 
	* @return void
	 */
	public static void replaceCell(XWPFTableCell cell,String text){
		List<XWPFParagraph> paragraphList = cell.getParagraphs();
		for(int i = 0; i < paragraphList.size(); i++){
			XWPFParagraph paragraph = paragraphList.get(i);
			if(i == 0){
				replaceParagraph(paragraph, text);
			}else{
				replaceParagraph(paragraph, "");
			}
		}
	}
	/**
	 * 
	* @功能描述: 替换文本内容
	* @方法名称: replaceParagraph 
	* @路径 com.daimeng.util 
	* @作者 daimeng.fun
	* @E-Mail sephy9527@qq.com
	* @创建时间 2019年11月24日 下午4:02:27 
	* @version V1.0   
	* @param paragraph
	* @param text 
	* @return void
	 */
	public static void replaceParagraph(XWPFParagraph paragraph,String text){
		List<XWPFRun> runs = paragraph.getRuns();
		if(runs != null && runs.size() > 1){
			for(int i = 0; i < runs.size(); i ++){
				if(i == 0){
					runs.get(i).setText(text, 0);
				}else{
					runs.get(i).setText("", 0);
				}
			}
		}
		else{
			XWPFRun run = paragraph.createRun();
			run.setText(text, 0);
		}
	}
	/**
	 * 
	* @功能描述: 替换、循环生成table的数据
	* @方法名称: replaceTable 
	* @路径 com.daimeng.util 
	* @作者 daimeng.fun
	* @E-Mail sephy9527@qq.com
	* @创建时间 2019年11月26日 下午6:06:15 
	* @version V1.0   
	* @param doc
	* @param table
	* @param obj
	* @param tableName
	* @param rowCount 从第几行开始
	* @return void
	 */
	public static void replaceTable(XWPFDocument doc,XWPFTable table,HashMap<String,Object> obj,String tableName,int rowCount){
		List<XWPFTableRow> rowList = table.getRows();
		for(int i = rowCount; i < rowList.size(); i++){
			XWPFTableRow row = rowList.get(i);
			List<XWPFTableCell> cellList = row.getTableCells();
			for(XWPFTableCell cell : cellList){
				String text = cell.getText();
				text = text.replace(tableName + "_", "");
				//双重循环，支持最后一行
				if(text.indexOf("${") > -1 && text.indexOf("}") > -1){
					String showType = (String)obj.get("showType");
					Constants.println("==table01==");
					Constants.println("showType="+showType);
					
					//替换表格，双循环，仅支持后几行
					if(text.startsWith("${table_")){
						int rowStart = i;
						int rowEnd = rowList.size();
						if(showType == null || "01".equals(showType)){
							String tableName2 = text.substring(text.indexOf("${table_")+2,text.indexOf("${table_")+10);
							Constants.println("table name2="+tableName2);
							List<HashMap<String,Object>> list = (List<HashMap<String,Object>>) obj.get(tableName2);
							
							for(HashMap<String,Object> obj2 : list){
								for(int j = rowStart; j <rowEnd ; j ++){
									XWPFTableRow r = rowList.get(j);
									copyRow(table, r, rowList.size());
								}
								replaceTable(doc, table, obj2,tableName2, rowStart+1);
							}
							//删除模板，否则下次会继续循环
							for(int j = rowStart; j < rowEnd; j ++){
								//Constants.println("remove==rowStart:"+rowStart+",rowEnd:"+rowEnd+",j="+j+":"+table.getRow(j).getCell(0).getText());
								table.removeRow(rowStart);
							}
							break;
						}else{
							//删除表格的2行
							for(int j = rowStart-1; j < rowEnd; j ++){
								Constants.println("remove==rowStart:"+rowStart+",rowEnd:"+rowEnd+",j="+j+":");
								table.removeRow(rowStart-1);
							}
							break;
						}
						
					}
					//替换图片
					else if(text.startsWith("${img_")){
						String tableName2 = text.substring(text.indexOf("table_"),text.length()-1);
						Constants.println("==table02=="+tableName2);
						replaceCell(cell,"");
						if(showType != null && ("02".equals(showType) || "03".equals(showType))){
							String name = "img-"+showType+"-"+System.currentTimeMillis()+".jpg";
							String imgPath = "D:/java_test/问卷调查test/"+name;
							List<HashMap<String,Object>> list = (List<HashMap<String,Object>>) obj.get(tableName2);
							drawCharts(imgPath, showType, list);
							List<XWPFParagraph> paragraphList = cell.getParagraphs();
							List<XWPFRun> runList = paragraphList.get(0).getRuns();
							//(pictureData, pictureType, filename, width, height)
							try {
								runList.get(0).addPicture(new FileInputStream(imgPath), 
										Document.PICTURE_TYPE_PNG, 
										name, 
										Units.toEMU(400), Units.toEMU(400));
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else{
							//删除图片的行
							table.removeRow(i);
						}
					}
					//替换文本
					else{
						String newText = getNewText(text, obj);
						replaceCell(cell,newText);
					}
				}
			}
		}
	}
	/**
	 * 
	* @功能描述: 复制row
	* addRow中的row是浅拷贝，所以复制出来的行其实都是传入的row对象
	* @方法名称: copyRow 
	* @路径 com.daimeng.util 
	* @作者 daimeng.fun
	* @E-Mail sephy9527@qq.com
	* @创建时间 2019年11月24日 下午5:13:56 
	* @version V1.0   
	* @param table
	* @param sourceRow
	* @param rowIndex 
	* @return void
	 */
	public static void copyRow(XWPFTable table,XWPFTableRow sourceRow,int rowIndex){
	    //在表格指定位置新增一行
		XWPFTableRow targetRow = table.insertNewTableRow(rowIndex);
		//复制行属性
		targetRow.getCtRow().setTrPr(sourceRow.getCtRow().getTrPr());
		List<XWPFTableCell> cellList = sourceRow.getTableCells();
		if (null == cellList) {
		    return;
		}
		//复制列及其属性和内容
		XWPFTableCell targetCell = null;
		for (XWPFTableCell sourceCell : cellList) {
		    targetCell = targetRow.addNewTableCell();
		    //列属性
		    targetCell.getCTTc().setTcPr(sourceCell.getCTTc().getTcPr());
		    //段落属性
		    if(sourceCell.getParagraphs()!=null&&sourceCell.getParagraphs().size()>0){                     
		    	targetCell.getParagraphs().get(0).getCTP().setPPr(sourceCell.getParagraphs().get(0).getCTP().getPPr());
	            if(sourceCell.getParagraphs().get(0).getRuns()!=null&&sourceCell.getParagraphs().get(0).getRuns().size()>0){
	            	XWPFRun cellR = targetCell.getParagraphs().get(0).createRun();
	    	        cellR.setText(sourceCell.getText());
	    	        cellR.setBold(sourceCell.getParagraphs().get(0).getRuns().get(0).isBold());
	            }else{
	            	targetCell.setText(sourceCell.getText());
	            }
	        }else{
	        	targetCell.setText(sourceCell.getText());
	        }
	    }
	}
	
	
	
	
	
	
	

	
}
