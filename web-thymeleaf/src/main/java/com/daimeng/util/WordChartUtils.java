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

	public static void main(String[] args) throws Exception {
		SimpleDateFormat sdf_datetime_format = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = sdf_datetime_format.format(Calendar.getInstance().getTime());
		
		//final String templateurl="D:/java_test/问卷调查test/Word_Test.docx";
		final String templateurl="D:/java_test/问卷调查test/Word_Test_picture.docx";
		
		final String returnurl="D:/java_test/问卷调查test/Word_Test_" + date + ".docx";
		InputStream is = new FileInputStream(new File(templateurl));
		XWPFDocument doc = new XWPFDocument(is);
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("title", "惠助你使用体验调查问卷");
		map.put("desc", "这是第一个调查问卷，他的功能是为了调查惠助你上线后得使用心得和使用体验。请各位参与调查得用户认真作答，您的得答案将会作为后续惠助你APP得改进依据，谢谢。");
		HashMap<String,Object> object01 = new HashMap<String,Object>();
		object01.put("user", "赵某");
		object01.put("time", "2019-10-11");
		object01.put("totalTime", "2019-10-15");
		object01.put("stDt", "2019-10-01");
		object01.put("enDt", "2019-10-31");
		object01.put("peopleCount", "150");
		map.put("object_01", object01);
		
		ArrayList<HashMap<String,Object>> table01 = new ArrayList<HashMap<String,Object>>();
		
		HashMap<String,Object> quest1 = new HashMap<String,Object>();
		quest1.put("no", "1");
		quest1.put("title", "第一个问题的答案是什么?");
		quest1.put("type", "02");
		ArrayList<HashMap<String,Object>> answerList1 = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> ans1_1 = new HashMap<String,Object>();
		ans1_1.put("no", "A");
		ans1_1.put("desc", "这个是第一个问题的第一个答案");
		ans1_1.put("per", "30");
		ans1_1.put("count", "10");
		HashMap<String,Object> ans1_2 = new HashMap<String,Object>();
		ans1_2.put("no", "B");
		ans1_2.put("desc", "这个是第一个问题的第二个答案");
		ans1_2.put("per", "60");
		ans1_2.put("count", "20");
		HashMap<String,Object> ans1_3 = new HashMap<String,Object>();
		ans1_3.put("no", "C");
		ans1_3.put("desc", "这个是第一个问题的第三个答案");
		ans1_3.put("per", "10");
		ans1_3.put("count", "15");
		HashMap<String,Object> ans1_4 = new HashMap<String,Object>();
		ans1_4.put("no", "D");
		ans1_4.put("desc", "这个是第一个问题的第四个答案");
		ans1_4.put("per", "10");
		ans1_4.put("count", "50");
		HashMap<String,Object> ans1_5 = new HashMap<String,Object>();
		ans1_5.put("no", "E");
		ans1_5.put("desc", "这个是第一个问题的第wu个答案");
		ans1_5.put("per", "9");
		ans1_5.put("count", "550");
		answerList1.add(ans1_1);
		answerList1.add(ans1_2);
		answerList1.add(ans1_3);
		answerList1.add(ans1_4);
		answerList1.add(ans1_5);
		quest1.put("table_02", answerList1);
		
		HashMap<String,Object> quest2 = new HashMap<String,Object>();
		quest2.put("no", "2");
		quest2.put("title", "第二个问题的答案是什么?");
		quest2.put("type", "02");
		ArrayList<HashMap<String,Object>> answerList2 = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> ans2_1 = new HashMap<String,Object>();
		ans2_1.put("no", "A");
		ans2_1.put("desc", "这个是第二个问题的第一个答案");
		ans2_1.put("per", "15");
		ans2_1.put("count", "60");
		HashMap<String,Object> ans2_2 = new HashMap<String,Object>();
		ans2_2.put("no", "B");
		ans2_2.put("desc", "这个是第二个问题的第二个答案");
		ans2_2.put("per", "20");
		ans2_2.put("count", "10");
		HashMap<String,Object> ans2_3 = new HashMap<String,Object>();
		ans2_3.put("no", "C");
		ans2_3.put("desc", "这个是第二个问题的第三个答案");
		ans2_3.put("per", "30");
		ans2_3.put("count", "1000");
		HashMap<String,Object> ans2_4 = new HashMap<String,Object>();
		ans2_4.put("no", "D");
		ans2_4.put("desc", "这个是第二个问题的第四个答案");
		ans2_4.put("per", "40");
		ans2_4.put("count", "0");
		answerList2.add(ans2_1);
		answerList2.add(ans2_2);
		answerList2.add(ans2_3);
		answerList2.add(ans2_4);
		quest2.put("table_02", answerList2);
		
		HashMap<String,Object> quest3 = new HashMap<String,Object>();
		quest3.put("no", "3");
		quest3.put("title", "第三个问题的答案是什么?");
		quest3.put("type", "02");
		ArrayList<HashMap<String,Object>> answerList3 = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> ans3_1 = new HashMap<String,Object>();
		ans3_1.put("no", "A");
		ans3_1.put("desc", "这个是第三个问题的第一个答案");
		ans3_1.put("per", "10");
		ans3_1.put("count", "14670");
		HashMap<String,Object> ans3_2 = new HashMap<String,Object>();
		ans3_2.put("no", "B");
		ans3_2.put("desc", "这个是第三个问题的第二个答案");
		ans3_2.put("per", "20");
		ans3_2.put("count", "340");
		HashMap<String,Object> ans3_3 = new HashMap<String,Object>();
		ans3_3.put("no", "C");
		ans3_3.put("desc", "这个是第三个问题的第三个答案");
		ans3_3.put("per", "30");
		ans3_3.put("count", "10");
		HashMap<String,Object> ans3_4 = new HashMap<String,Object>();
		ans3_4.put("no", "D");
		ans3_4.put("desc", "这个是第三个问题的第四个答案");
		ans3_4.put("per", "40");
		ans3_4.put("count", "410");
		HashMap<String,Object> ans3_5 = new HashMap<String,Object>();
		ans3_5.put("no", "E");
		ans3_5.put("desc", "这个是第三个问题的第5个答案");
		ans3_5.put("per", "50");
		ans3_5.put("count", "510");
		HashMap<String,Object> ans3_6 = new HashMap<String,Object>();
		ans3_6.put("no", "F");
		ans3_6.put("desc", "这个是第三个问题的第6个答案");
		ans3_6.put("per", "30");
		ans3_6.put("count", "60");
		HashMap<String,Object> ans3_7 = new HashMap<String,Object>();
		ans3_7.put("no", "G");
		ans3_7.put("desc", "这个是第三个问题的第7个答案");
		ans3_7.put("per", "20");
		ans3_7.put("count", "70");
		HashMap<String,Object> ans3_8 = new HashMap<String,Object>();
		ans3_8.put("no", "H");
		ans3_8.put("desc", "这个是第三个问题的第8个答案");
		ans3_8.put("per", "10");
		ans3_8.put("count", "810");
		answerList3.add(ans3_1);
		answerList3.add(ans3_2);
		answerList3.add(ans3_3);
		answerList3.add(ans3_4);
		answerList3.add(ans3_5);
		answerList3.add(ans3_6);
		answerList3.add(ans3_7);
		answerList3.add(ans3_8);
		quest3.put("table_02", answerList3);
		
		HashMap<String,Object> quest4 = new HashMap<String,Object>();
		quest4.put("no", "4");
		quest4.put("title", "第4个问题的答案是什么?");
		quest4.put("type", "01");
		ArrayList<HashMap<String,Object>> answerList4 = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> ans4_1 = new HashMap<String,Object>();
		ans4_1.put("no", "A");
		ans4_1.put("desc", "这个是第4个问题的第一个答案");
		ans4_1.put("per", "30");
		ans4_1.put("count", "10");
		HashMap<String,Object> ans4_2 = new HashMap<String,Object>();
		ans4_2.put("no", "B");
		ans4_2.put("desc", "这个是第4个问题的第二个答案");
		ans4_2.put("per", "60");
		ans4_2.put("count", "2210");
		HashMap<String,Object> ans4_3 = new HashMap<String,Object>();
		ans4_3.put("no", "C");
		ans4_3.put("desc", "这个是第4个问题的第三个答案");
		ans4_3.put("per", "10");
		ans4_3.put("count", "3310");
		HashMap<String,Object> ans4_4 = new HashMap<String,Object>();
		ans4_4.put("no", "D");
		ans4_4.put("desc", "这个是第4个问题的第四个答案");
		ans4_4.put("per", "10");
		ans4_4.put("count", "4104");
		HashMap<String,Object> ans4_5 = new HashMap<String,Object>();
		ans4_5.put("no", "E");
		ans4_5.put("desc", "这个是第4个问题的第wu个答案");
		ans4_5.put("per", "9");
		ans4_5.put("count", "1550");
		answerList4.add(ans4_1);
		answerList4.add(ans4_2);
		answerList4.add(ans4_3);
		answerList4.add(ans4_4);
		answerList4.add(ans4_5);
		quest4.put("table_02", answerList4);
		
		table01.add(quest1);
		table01.add(quest2);
		table01.add(quest3);
		table01.add(quest4);
		map.put("table_01", table01);
		
		//replaceAll(doc);
		replaceText(doc,map);
		createTable(doc,map);
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
	}
	
	public static void replaceText(XWPFDocument doc,HashMap<String,Object> map){
		Constants.println("======replaceText start======");
		List<XWPFParagraph> paragraphList = doc.getParagraphs();
		for(XWPFParagraph paragraph : paragraphList){
			String paragraphText = paragraph.getText();
			//Constants.println("替换前="+paragraphText);
			String newText = getNewText(paragraphText, map);
			/*XWPFRun insertNewRun = paragraph.insertNewRun(0);
			insertNewRun.setText(newText);*/
			replaceParagraph(paragraph, newText);
			String paragraphText2 = paragraph.getText();
			//Constants.println("替换后="+paragraphText2);
		}
		Constants.println("======replaceText end======");
	}
	
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
	
	
	public static void createTable(XWPFDocument doc,HashMap<String,Object> map){
		Constants.println("======createTable start======");
		List<XWPFTable> tableList = doc.getTables();
		List<XWPFChart> charList = doc.getCharts();
		List<POIXMLDocumentPart> realtionsList = doc.getRelations();
		for(POIXMLDocumentPart realtions : realtionsList){
			
		}
		for(XWPFTable table : tableList){
			//String tableName = table.getRow(0).getCell(0).getText().replace("${", "").replace("}", "");
			String tableName = getTableName(table,"table");
			if(tableName == null || "".equals(tableName)){
				tableName = getTableName(table,"object");
			}
			Constants.println("table name="+tableName);
			if(map.get(tableName) != null){
				if(tableName.startsWith("object")){
					HashMap<String,Object> obj = (HashMap<String,Object>) map.get(tableName);
					replaceTable(doc, table, obj,tableName, 0);
				}else if(tableName.startsWith("table")){
					List<HashMap<String,Object>> list = (List<HashMap<String,Object>>) map.get(tableName);
					List<XWPFTableRow> rowList = table.getRows();
					int rowCount = table.getNumberOfRows();
					for(HashMap<String,Object> obj : list){
						String type = (String) obj.get("type");
						for(int i = 0; i < rowCount; i ++){
							XWPFTableRow r = rowList.get(i);
							copyRow(table, r, rowList.size());
						}
						replaceTable(doc, table, obj, tableName, rowCount);
					}
					//删除模板，否则下次会继续循环
					for(int i = 0; i < rowCount; i ++){
						table.removeRow(0);
					}
				}
			}
		}
		Constants.println("======createTable end======");
	}
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
		for(int i = 0; i < runs.size(); i ++){
			if(i == 0){
				runs.get(i).setText(text, 0);
			}else{
				runs.get(i).setText("", 0);
			}
		}
	}
	
	public static void replaceTable(XWPFDocument doc,XWPFTable table,HashMap<String,Object> obj,String tableName,int rowCount){
		List<XWPFTableRow> rowList = table.getRows();
		for(int i = rowCount; i < rowList.size(); i++){
			XWPFTableRow row = rowList.get(i);
			List<XWPFTableCell> cellList = row.getTableCells();
			for(XWPFTableCell cell : cellList){
				String text = cell.getText();
				//替换表格名字
				text = text.replace(tableName + "_", "");
				//双重循环，支持最后一行
				if(text.indexOf("${") > -1 && text.indexOf("}") > -1){
					String type = (String)obj.get("type");
					Constants.println("==table01==");
					Constants.println("type="+type);
					if(text.startsWith("${table_")){
						int rowStart = i;
						int rowEnd = rowList.size();
						if(type == null || "01".equals(type)){
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
					/*else if(text.indexOf("${answerTable_") > -1 && text.indexOf("}") > -1){
						Constants.println("table name3="+text);
						String tableName3 = text.substring(text.indexOf("table_"),text.length()-1);
						Constants.println("table name3="+tableName3);
						replaceCell(cell,"");
						
						List<XWPFParagraph> paragraphList = cell.getParagraphs();
						//XmlCursor cursor = paragraphList.get(0).getCTP().newCursor();
						XmlCursor cursor = row.getCtRow().newCursor();
						Constants.println(paragraphList.get(0).getText());
                        XWPFTable tableOne = doc.insertNewTbl(cursor);// ---这个是关键

                        //设置表格宽度，第一行宽度就可以了，这个值的单位，目前我也还不清楚，还没来得及研究
                        tableOne.setWidth(8500);

                        // 表格第一行，对于每个列，必须使用createCell()，而不是getCell()，因为第一行嘛，肯定是属于创建的，没有create哪里来的get呢
                        XWPFTableRow tableOneRowOne = tableOne.getRow(0);//行
                        new WordPoiTools().setWordCellSelfStyle(tableOneRowOne.getCell(0), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 10, "序号");
                        new WordPoiTools().setWordCellSelfStyle(tableOneRowOne.createCell(), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 10, "选项");
                        new WordPoiTools().setWordCellSelfStyle(tableOneRowOne.createCell(), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 10, "占比");

                        List<HashMap<String,Object>> list = (List<HashMap<String,Object>>) obj.get(tableName3);
                        for(HashMap<String,Object> map : list){
                        	XWPFTableRow tableOneRowTwo = tableOne.createRow();//行
                            new WordPoiTools().setWordCellSelfStyle(tableOneRowTwo.getCell(0), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 10, (String)map.get("no"));
                            new WordPoiTools().setWordCellSelfStyle(tableOneRowTwo.getCell(1), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 10, (String)map.get("desc"));
                            new WordPoiTools().setWordCellSelfStyle(tableOneRowTwo.getCell(2), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 10, (String)map.get("per"));
                        }
                        // 表格第二行
                        

					}*/
					else if(text.startsWith("${img_")){
						String tableName2 = text.substring(text.indexOf("table_"),text.length()-1);
						Constants.println("==table02=="+tableName2);
						replaceCell(cell,"");
						if(type != null && ("02".equals(type) || "03".equals(type))){
							String name = "img-"+type+"-"+System.currentTimeMillis()+".jpg";
							String imgPath = "D:/java_test/问卷调查test/"+name;
							List<HashMap<String,Object>> list = (List<HashMap<String,Object>>) obj.get(tableName2);
							String[] names = new String[list.size()];
							Double[] data = new Double[list.size()];
							for(int k = 0 ; k < list.size(); k++){
								HashMap<String,Object> map = list.get(k);
								names[k] = (String) map.get("no");
								data[k] = Double.valueOf((String) map.get("per"));
							}
							if("02".equals(type)){
								JFreeCharUtils.makeBarChart(names, data, imgPath);
							}else{
								JFreeCharUtils.makePieChart(names, data, imgPath);
							}
							
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
