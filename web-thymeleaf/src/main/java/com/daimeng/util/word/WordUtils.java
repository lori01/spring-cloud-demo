package com.daimeng.util.word;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.daimeng.util.Constants;

/**
 * 
* @功能描述: word模板解析,将数据填充入模板,生成新的word并导出
* @名称: WordUtils.java 
* @路径 com.daimeng.util 
* @作者 daimeng@tansun.com.cn
* @创建时间 2019年4月20日 下午2:51:49 
* @version V1.0
 */
public class WordUtils {

	/**
	 * 
	* @功能描述: 将数据写入模板总并导出成新的excel
	* @方法名称: downDocument 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月19日 下午3:54:05 
	* @version V1.0   
	* @param templatePath
	* @param newDocumentPath
	* @param dataMap 
	* @return void
	 */
	public static void downDocument(String templatePath, String newDocumentPath, HashMap<String,Object> dataMap){
		try {
			ZipFile zipTemplatefile = new ZipFile(templatePath);
			//判断生成文件的文件夹是否存在，否则生成文件夹
			File file = new File(newDocumentPath.substring(0, newDocumentPath.lastIndexOf("/")));
			if(!file.exists()){
				file.mkdir();
			}
			ZipOutputStream zipOutput = new ZipOutputStream(new FileOutputStream(newDocumentPath));
			ZipEntry documentXml = zipTemplatefile.getEntry("word/document.xml");
			InputStream documentXmlIs = zipTemplatefile.getInputStream(documentXml);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			Document doc = dbf.newDocumentBuilder().parse(documentXmlIs);
			//解析列表
			Map<String,Element> totalMap = WordUtils.getAllTableElement(doc,"total");
			//列表赋值
			for(Map.Entry<String, Element> entry : totalMap.entrySet()){
				long start = System.currentTimeMillis();
				Constants.println("##################create " +entry.getKey()+ " start###################");
				Element ele = entry.getValue();
				ArrayList<HashMap<String,Object>> tableList = new ArrayList<HashMap<String,Object>>();
				if(dataMap.get(entry.getKey()) != null){
					tableList = (ArrayList<HashMap<String,Object>>) dataMap.get(entry.getKey());
				}
				if("".equals(entry.getKey())){
					
				}
				else{
					//参数 (table元素，table数据，表名)
					createTableCycleTwice(ele,tableList ,entry.getKey());
				}
				long end = System.currentTimeMillis();
				Constants.println("##################create " +entry.getKey()+ " end,cost " +(end-start)+ "ms###################");
			}
			Map<String,Element> tableMap = getAllTableElement(doc,"table");
			for(Map.Entry<String, Element> entry : tableMap.entrySet()){
				long start = System.currentTimeMillis();
				Constants.println("##################create " +entry.getKey()+ " start###################");
				Element ele = entry.getValue();
				ArrayList<HashMap<String,Object>> tableList = new ArrayList<HashMap<String,Object>>();
				if(dataMap.get(entry.getKey()) != null){
					tableList = (ArrayList<HashMap<String,Object>>) dataMap.get(entry.getKey());
				}
				
				if("".equals(entry.getKey())){
					
				}
				//循环表头在第一行
				else if("table_03".equals(entry.getKey()) || 
						"table_04".equals(entry.getKey()) ){
					createTableCycleOnce(ele,tableList ,0,entry.getKey());
				}
				//循环表头在第二行
				else{
					createTableCycleOnce(ele,tableList ,1,entry.getKey());
				}
				long end = System.currentTimeMillis();
				Constants.println("##################create " +entry.getKey()+ " end,cost " +(end-start)+ " ms###################");
			}
			//替换非table类型的w:p数据
			Constants.println("##################replace field start###################");
			long start = System.currentTimeMillis();
			replaceFiled4Document(doc, dataMap);
			long end = System.currentTimeMillis();
			Constants.println("##################replace field end,cost " +(end-start)+ " ms###################");
			//解析所有非列表的w:p内容
			//Map<String,Element> fieldMarkEle = WordUtils.getFiledElementByPrex(doc, "w:p");
			//根据field字段填入value
			//WordUtils.fillFieldParam(fieldMarkEle, dataMap);
			
			
			createNewDocFile(doc,zipTemplatefile,zipOutput);
			Constants.println("##################word导出成功###################");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	* @功能描述: 替换整个document所有w：p元素里面包含${}的内容
	* @方法名称: replaceFiledElement 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月19日 下午3:54:18 
	* @version V1.0   
	* @param doc
	* @param dataMap 
	* @return void
	 */
	public static void replaceFiled4Document(Document doc,HashMap<String,Object> dataMap){
		NodeList wpNodelist = doc.getElementsByTagName("w:p");
		replaceFiled4NodeList(wpNodelist, dataMap, "");
	}
	/**
	 * 
	* @功能描述: 替换指定Element里面所有w：p元素里面包含${}的内容,如果是表格,这要修改${}里面的字段名称
	* @方法名称: replaceFiledElementForElement 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月19日 下午3:55:21 
	* @version V1.0   
	* @param element
	* @param dataMap
	* @param tableName 
	* @return void
	 */
	public static void replaceFiled4Element(Element element,HashMap<String,Object> dataMap, String tableName){
		NodeList wpNodelist = element.getElementsByTagName("w:p");
		replaceFiled4NodeList(wpNodelist, dataMap, tableName);
	}
	/**
	 * 
	* @功能描述: 替换指定NodeList里面所有w：p元素里面包含${}的内容,如果是表格,这要修改${}里面的字段名称
	* @功能描述: 如果tableName不为空,则要把${}里面的tableName标识替换掉
	* @方法名称: replaceFiled4NodeList 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月19日 下午4:03:17 
	* @version V1.0   
	* @param wpNodelist
	* @param dataMap
	* @param tableName 
	* @return void
	 */
	public static void replaceFiled4NodeList(NodeList wpNodelist,HashMap<String,Object> dataMap, String tableName){
		for(int i = 0; i < wpNodelist.getLength(); i ++){
			Element wpElement = (Element) wpNodelist.item(i);
			String value = getWpElementValue(wpElement);
			if(value.contains("${") && value.contains("}")){
				//替换${table_01_name}字段中的table_01_，使其仅保留map中的数据字段
				if(tableName != null && !"".equals(tableName)){
					value = value.replace(tableName+"_", "");
				}
				
				//获取所有${}字段
				ArrayList<String> pars = getParamFromDollar(value);
				for(String parameter : pars){
					//替换${}内的数据
					setWpElementValueByParam(wpElement, parameter, dataMap, tableName);
					if(dataMap.get(parameter) != null){
						Constants.println("##replace field##" + value + " --> " + (String)dataMap.get(parameter));
					}
				}
			}
		}
	}
	
	/**
	 * 
	* @功能描述: 从w:p获取元素值  ${name}可能被拆分到不同的w:t中，所以w:p内所有w:t合并起来才是完整的表达式
	* @方法名称: getWpElementValue 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月19日 下午4:38:13 
	* @version V1.0   
	* @param element
	* @return 
	* @return String
	 */
	public static String getWpElementValue(Element element){
		String value = "";
		//Constants.println(element.getTextContent());
		NodeList wrNodeList = element.getElementsByTagName("w:r");
		for(int i = 0; i < wrNodeList.getLength(); i ++){
			Element ele = (Element) wrNodeList.item(i);
			NodeList wtNodeList = ele.getElementsByTagName("w:t");
			for(int j = 0; j < wtNodeList.getLength(); j ++){
				value += wtNodeList.item(j).getTextContent();
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
	* @功能描述: 清楚w:t内的所有数据,并往w:t中重新set值
	* @方法名称: setWpElementValueByParam 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月19日 下午4:38:43 
	* @version V1.0   
	* @param element
	* @param param
	* @param map
	* @param tableName 
	* @return void
	 */
	public static void setWpElementValueByParam(Element element,String param,HashMap<String,Object> map, String tableName){
		NodeList wtNodeList = element.getElementsByTagName("w:t");
		String nodeValue = "";
		//循环所有w:t标签，拼成一个字符串，因为${}等数据会被拆分成多个w:t，无法正常替换
		for (int m = 0; m < wtNodeList.getLength(); m++) {
			nodeValue += wtNodeList.item(m).getTextContent();
		}
		for (int m = 0; m < wtNodeList.getLength(); m++) {
			wtNodeList.item(m).setTextContent("");
		}
		if(tableName != null && !"".equals(tableName)){
			nodeValue = nodeValue.replace(tableName+"_", "");
		}
		nodeValue = nodeValue.replaceAll("\\$|\\{|\\}", "")
				.replace(param, map.containsKey(param)&&map.get(param)!=null ? (String)map.get(param) : "");
		wtNodeList.item(0).setTextContent(nodeValue);
	}
	
	
	/**
	 * 
	* @功能描述: 获取Document所有table,并以Map<表格名称,表格元素>形式返回
	* @方法名称: getAllTableElement 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月19日 下午4:03:44 
	* @version V1.0   
	* @param document
	* @param parfix 前缀 [table,total] table为单层表头 total双层表头
	* @return 
	* @return Map<String,Element>  Map<表格名称,表格元素>
	 */
	public static Map<String,Element> getAllTableElement(Document document,String parfix){
		NodeList nodeList = document.getElementsByTagName("w:tbl");
		Map<String,Element> map = new HashMap<String,Element>();
		for(int i = 0; i < nodeList.getLength(); i++){
			Element table = (Element) nodeList.item(i);
			String tableName = getTableNameForElement(table,parfix);
			if(tableName != null && !"".equals(tableName)){
				if(map.get(tableName) == null){
					map.put(tableName, table);
				}
			}
		}
		return map;
	}
	/**
	 * 
	* @功能描述: 查询表格元素总的表格名字,一般是w:p里面${table_01_xxxx}的table_01
	* @方法名称: getTableNameForElement 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月19日 下午4:06:56 
	* @version V1.0   
	* @param table
	* @param parfix
	* @return 
	* @return String
	 */
	public static String getTableNameForElement(Element table,String parfix){
		String tableName = "";
		parfix = parfix + "_";
		NodeList trList = table.getElementsByTagName("w:tr");
		
		OUT:
		for(int j = 0; j < trList.getLength(); j ++){
			Element trElement = (Element) trList.item(j);
			NodeList tcList = trElement.getElementsByTagName("w:tc");
			for(int k = 0; k < tcList.getLength(); k ++){
				Element tcElement = (Element) tcList.item(k);
				NodeList wpList = tcElement.getElementsByTagName("w:p");
				for(int l = 0; l < wpList.getLength(); l ++){
					Element wpElement = (Element) wpList.item(l);
					String value = getWpElementValue(wpElement);
					//Constants.println("列表w:tbl->w:tr->w:tc->w:p->"+value);
					if(value.contains("${") && value.contains("}") && value.contains(parfix)){
						int startIndex = value.indexOf("${");
						int endIndex = value.indexOf("}");
						String parameter = value.substring(startIndex+2, endIndex);
						tableName = parameter.substring(parameter.indexOf(parfix),parameter.indexOf(parfix)+(parfix.length()+2));//+3表示后面的 _01
						Constants.println(tableName);
						break OUT;
					}
				}
			}
		}
		return tableName;
	}
	
	
	/**
	 * 
	* @功能描述: 普通的单表头列表.只有一个表头,即只循环一次 
	* @功能描述: 根据所要插入的数据自动增加行数，并且替换掉预先设置的字符串
	* @方法名称: createTableCycleOnce 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月19日 下午4:16:05 
	* @version V1.0   
	* @param tblElement:table的元素
	* @param dataList:数据
	* @param headerStatIndex:循环表头位置 0 1 2(第一行可以是写实标题,第二行才是开始循环的表头)
	* @param tableKey:表格名称  table_01
	* @throws Exception 
	* @return void
	 */
	public static void createTableCycleOnce(Element tblElement, ArrayList<HashMap<String,Object>> dataList, int headerStatIndex, String tableKey) throws Exception {
		//w:tr->w:tc->w:p->w:r->w:t
		//w:tr(table row)是table的一行
		//w:tc(table column cell)是tr中的一个单元格
		//w:p是文字内容，内部会被拆分成多个w:r和w:t
		Element elementTr = ((Element) (tblElement.getElementsByTagName("w:tr").item(headerStatIndex)));
		Node elementTrCloneTemp = elementTr.cloneNode(true);
		tblElement.removeChild(tblElement.getElementsByTagName("w:tr").item(headerStatIndex));
		cycleTableTcContext(tblElement, elementTrCloneTemp, dataList, tableKey);
		
	}
	/**
	 * 
	* @功能描述: 循环生成表格的数据
	* @方法名称: cycleTableTcContext 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月19日 下午6:11:38 
	* @version V1.0   
	* @param parent
	* @param tmp
	* @param dataList
	* @param tableKey 
	* @return void
	 */
	public static void cycleTableTcContext(Element parent,Node tmp, ArrayList<HashMap<String,Object>> dataList,String tableKey){
		//循环数据,有几条数据则生成几行表格的row
		for (int i = 0; i < dataList.size(); i++) {
			Node elementTrClone = tmp.cloneNode(true);
			NodeList noli = ((Element) elementTrClone).getElementsByTagName("w:tc");
			//循环w：tc标签，每个w：tc是一个单元格格子
			for (int k = 0; k < noli.getLength(); k++) {
				Element elementTc = (Element) noli.item(k);
				replaceFiled4Element(elementTc, dataList.get(i), tableKey);
			}
			parent.appendChild(elementTrClone);
			elementTrClone = null;
		}
	}
	/**
	 * 
	* @功能描述: 双表头列表.有2个表头,循环里面嵌套循环,循环2吃
	* @方法名称: createTableCycleTwice 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月19日 下午4:25:59 
	* @version V1.0   
	* @param document
	* @param tblElement:table的元素
	* @param dataList:数据
	* @param tableKey:表格名称  total_01
	* @throws Exception 
	* @return void
	 */
	public static void createTableCycleTwice(Element tblElement, ArrayList<HashMap<String,Object>> dataList,  String tableKey) throws Exception{
		//获取第二个循环的table name   最外层的table name=参数tableKey
		String tableName = getTableNameForElement(tblElement,"table");
		//复制第一个表头和第二个表头
		Element elementTr1 = ((Element) (tblElement.getElementsByTagName("w:tr").item(0)));
		Constants.println(elementTr1.getTextContent());
		Element elementTr2 = ((Element) (tblElement.getElementsByTagName("w:tr").item(1)));
		Constants.println(elementTr2.getTextContent());
		Node elementTrCloneTemp1 = elementTr1.cloneNode(true);
		Node elementTrCloneTemp2 = elementTr2.cloneNode(true);
		//当删除一个表头后,下一行表头即变成了第一个,所以此处删除2个item(0)
		tblElement.removeChild(tblElement.getElementsByTagName("w:tr").item(0));
		tblElement.removeChild(tblElement.getElementsByTagName("w:tr").item(0));
		Constants.println(tblElement.getTextContent());
		//循环第一层数据
		for (int i = 0; i < dataList.size(); i ++) {
			HashMap<String,Object> totalData = dataList.get(i);
			Node elementTrClone1 = elementTrCloneTemp1.cloneNode(true);
			
			//替换第一层表头里面单个字段${}的数据
			//目前第一层只有一个cell,可以当成普通文本替换,否则需要按照一个表格的方式替换
			replaceFiled4Element((Element) elementTrClone1, totalData,tableKey);
			
			tblElement.appendChild(elementTrClone1);
			Constants.println(tblElement.getTextContent());
			
			elementTrClone1 = null;
			ArrayList<HashMap<String,Object>> tableList = (ArrayList<HashMap<String,Object>>) totalData.get(tableName);
			//循环第二层表头
			if (tableList.size() > 0) {
				cycleTableTcContext(tblElement, elementTrCloneTemp2, tableList, tableName);
			}
		}
	}
	
	
	
	
	/**
	 * 
	* @功能描述: 生成新的文件
	* @方法名称: createNewDocFile 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月19日 下午4:39:04 
	* @version V1.0   
	* @param doc
	* @param zipTemplatefile
	* @param zipoutput 
	* @return void
	 */
	public static void createNewDocFile(Document doc,ZipFile zipTemplatefile,ZipOutputStream zipoutput){
		try {
			ByteArrayOutputStream byteoutput = new ByteArrayOutputStream();
			Transformer trans = TransformerFactory.newInstance().newTransformer();
			//将document转换成byte
			trans.transform(new DOMSource(doc), new StreamResult(byteoutput));
			/*
			 * 循环模板文件里面的元素
			 * [Content_Types].xml
			 * _rels/.rels
			 * word/_rels/document.xml.rels
			 * word/document.xml
			 * word/footnotes.xml
			 * word/endnotes.xml
			 * word/theme/theme1.xml
			 * word/settings.xml
			 * word/webSettings.xml
			 * docProps/core.xml
			 * word/styles.xml
			 * word/fontTable.xml
			 * docProps/app.xml
			 */
			Enumeration enumera = zipTemplatefile.entries();
			while (enumera.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) enumera.nextElement();
				Constants.println(entry.getName());
				//如果格式是word/document.xml，将根据模板生成的有数据的document替换模板的内容
				if (entry.getName().equals("word/document.xml")) {
					byte[] data = byteoutput.toByteArray();
					zipoutput.putNextEntry(new ZipEntry(entry.getName()));
					zipoutput.write(data, 0, data.length);
					zipoutput.closeEntry();
				} 
				//其他格式则使用模板中的文件
				else {
					InputStream incoming = zipTemplatefile.getInputStream(entry);
					byte[] data = new byte[incoming.available()];
					int readCount = incoming.read(data, 0, data.length);
					zipoutput.putNextEntry(new ZipEntry(entry.getName()));
					zipoutput.write(data, 0, readCount);
					zipoutput.closeEntry();
				}
			}
			zipoutput.flush();
			zipoutput.close();
		} catch (Exception e) {
			e.printStackTrace();
			Constants.println(e.getMessage());
		} 
	}
	
	
}
