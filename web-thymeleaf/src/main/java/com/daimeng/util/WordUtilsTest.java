package com.daimeng.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class WordUtilsTest {

	public static void main(String[] args) {
		/*ArrayList<String> result = getParamFromDollar("(${no})是一个${name}");
		for(String str : result){
			Constants.println(str);
		}*/
		
		long start = System.currentTimeMillis();
		String templatePath = "D:/java_test/word/Word_Export_Test2.docx";
		SimpleDateFormat sdf_datetime_format = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = sdf_datetime_format.format(Calendar.getInstance().getTime());
		String newDocumentPath = "D:/java_test/word/" + date +System.currentTimeMillis()+ ".docx";
		WordUtils.downDocument(templatePath, newDocumentPath, dataMap2);
		
		long end = System.currentTimeMillis();
		Constants.println(newDocumentPath);
        Constants.println("===整个过程消耗了"+(end-start)+"ms===");
	}
	
	private static HashMap<String,Object> dataMap2 = new HashMap<String,Object>();
	static{
		dataMap2.put("name", "惠懂你调查问卷测试模板");
		dataMap2.put("user", "孙某");
		dataMap2.put("from", "惠懂你");
		dataMap2.put("time", "2019-10-10");
		
		ArrayList<HashMap<String,Object>> quests = new ArrayList<HashMap<String,Object>>();
		
		HashMap<String,Object> quest1 = new HashMap<String,Object>();
		quest1.put("no", "1");
		quest1.put("title", "第一个问题的答案是什么?");
		quest1.put("ans", "A");
		ArrayList<HashMap<String,Object>> answerList1 = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> ans1_1 = new HashMap<String,Object>();
		ans1_1.put("no", "A");
		ans1_1.put("desc", "这个是第一个问题的第一个答案");
		HashMap<String,Object> ans1_2 = new HashMap<String,Object>();
		ans1_2.put("no", "B");
		ans1_2.put("desc", "这个是第一个问题的第二个答案");
		HashMap<String,Object> ans1_3 = new HashMap<String,Object>();
		ans1_3.put("no", "C");
		ans1_3.put("desc", "这个是第一个问题的第三个答案");
		HashMap<String,Object> ans1_4 = new HashMap<String,Object>();
		ans1_4.put("no", "D");
		ans1_4.put("desc", "这个是第一个问题的第四个答案");
		answerList1.add(ans1_1);
		answerList1.add(ans1_2);
		answerList1.add(ans1_3);
		answerList1.add(ans1_4);
		quest1.put("table_01", answerList1);
		
		HashMap<String,Object> quest2 = new HashMap<String,Object>();
		quest2.put("no", "2");
		quest2.put("title", "第二个问题的答案是什么?");
		quest2.put("ans", "D-这里是其他答案的描述");
		ArrayList<HashMap<String,Object>> answerList2 = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> ans2_1 = new HashMap<String,Object>();
		ans2_1.put("no", "A");
		ans2_1.put("desc", "这个是第二个问题的第一个答案");
		HashMap<String,Object> ans2_2 = new HashMap<String,Object>();
		ans2_2.put("no", "B");
		ans2_2.put("desc", "这个是第二个问题的第二个答案");
		HashMap<String,Object> ans2_3 = new HashMap<String,Object>();
		ans2_3.put("no", "C");
		ans2_3.put("desc", "这个是第二个问题的第三个答案");
		HashMap<String,Object> ans2_4 = new HashMap<String,Object>();
		ans2_4.put("no", "D");
		ans2_4.put("desc", "其他");
		answerList2.add(ans2_1);
		answerList2.add(ans2_2);
		answerList2.add(ans2_3);
		answerList2.add(ans2_4);
		quest2.put("table_01", answerList2);
		
		HashMap<String,Object> quest3 = new HashMap<String,Object>();
		quest3.put("no", "3");
		quest3.put("title", "第三个问题的答案是什么?");
		quest3.put("ans", "AC");
		ArrayList<HashMap<String,Object>> answerList3 = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> ans3_1 = new HashMap<String,Object>();
		ans3_1.put("no", "A");
		ans3_1.put("desc", "这个是第三个问题的第一个答案");
		HashMap<String,Object> ans3_2 = new HashMap<String,Object>();
		ans3_2.put("no", "B");
		ans3_2.put("desc", "这个是第三个问题的第二个答案");
		HashMap<String,Object> ans3_3 = new HashMap<String,Object>();
		ans3_3.put("no", "C");
		ans3_3.put("desc", "这个是第三个问题的第三个答案");
		HashMap<String,Object> ans3_4 = new HashMap<String,Object>();
		ans3_4.put("no", "D");
		ans3_4.put("desc", "这个是第三个问题的第四个答案");
		answerList3.add(ans3_1);
		answerList3.add(ans3_2);
		answerList3.add(ans3_3);
		answerList3.add(ans3_4);
		quest3.put("table_01", answerList3);
		
		HashMap<String,Object> quest4 = new HashMap<String,Object>();
		quest4.put("no", "4");
		quest4.put("title", "第四个问题是排序题，答案是什么?");
		quest4.put("ans", "ADCB");
		ArrayList<HashMap<String,Object>> answerList4 = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> ans4_1 = new HashMap<String,Object>();
		ans4_1.put("no", "A");
		ans4_1.put("desc", "这个是第三个问题的第一个答案");
		HashMap<String,Object> ans4_2 = new HashMap<String,Object>();
		ans4_2.put("no", "B");
		ans4_2.put("desc", "这个是第三个问题的第二个答案");
		HashMap<String,Object> ans4_3 = new HashMap<String,Object>();
		ans4_3.put("no", "C");
		ans4_3.put("desc", "这个是第三个问题的第三个答案");
		HashMap<String,Object> ans4_4 = new HashMap<String,Object>();
		ans4_4.put("no", "D");
		ans4_4.put("desc", "这个是第三个问题的第四个答案");
		answerList4.add(ans4_1);
		answerList4.add(ans4_2);
		answerList4.add(ans4_3);
		answerList4.add(ans4_4);
		quest4.put("table_01", answerList4);
		
		HashMap<String,Object> quest5 = new HashMap<String,Object>();
		quest5.put("no", "4");
		quest5.put("title", "第五个问题是问答题，答案是什么?");
		quest5.put("ans", "这里是问答题的答案");
		ArrayList<HashMap<String,Object>> answerList5 = new ArrayList<HashMap<String,Object>>();
		quest5.put("table_01", answerList5);
		
		HashMap<String,Object> quest6 = new HashMap<String,Object>();
		quest6.put("no", "6");
		quest6.put("title", "第6个问题的答案是什么?");
		quest6.put("ans", "E-这里是其他答案的描述");
		ArrayList<HashMap<String,Object>> answerList6 = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> ans6_1 = new HashMap<String,Object>();
		ans6_1.put("no", "A");
		ans6_1.put("desc", "这个是第6个问题的第一个答案");
		HashMap<String,Object> ans6_2 = new HashMap<String,Object>();
		ans6_2.put("no", "B");
		ans6_2.put("desc", "这个是第6个问题的第二个答案");
		HashMap<String,Object> ans6_3 = new HashMap<String,Object>();
		ans6_3.put("no", "C");
		ans6_3.put("desc", "这个是第6个问题的第三个答案");
		HashMap<String,Object> ans6_4 = new HashMap<String,Object>();
		ans6_4.put("no", "D");
		ans6_4.put("desc", "这个是第6个问题的第4个答案");
		HashMap<String,Object> ans6_5 = new HashMap<String,Object>();
		ans6_5.put("no", "E");
		ans6_5.put("desc", "其他");
		answerList6.add(ans6_1);
		answerList6.add(ans6_2);
		answerList6.add(ans6_3);
		answerList6.add(ans6_4);
		answerList6.add(ans6_5);
		quest6.put("table_01", answerList6);
		
		quests.add(quest1);
		quests.add(quest2);
		quests.add(quest3);
		quests.add(quest4);
		quests.add(quest5);
		quests.add(quest6);
		dataMap2.put("total_01", quests);
	}
	
	
	
	
	
	
	private static HashMap<String,Object> dataMap = new HashMap<String,Object>();
	static{
		dataMap.put("project_name", "厦门公司金融服务方案");
		dataMap.put("cst_name", "厦门海洋运输集团");
		dataMap.put("inst_name", "中国建设银行厦门分行");
		dataMap.put("date", "2018年10月15号");
		dataMap.put("other_pro", "其他章节信息其他章节信息其他章节信息其他章节信息其他章节信息其他章节信息其他章节信息其他章节信息");
		//双循环
		ArrayList<HashMap<String,Object>> total01 = new ArrayList<HashMap<String,Object>>();
		for(int i = 0; i < 10 ; i++){
			HashMap<String,Object> pt = new HashMap<String,Object>();
			pt.put("ch_no", "大类产品"+i);
			pt.put("ch_tp", "大类产品类型:类型"+i);
			ArrayList<HashMap<String,String>> table01 = new ArrayList<HashMap<String,String>>();
			for(int j = 0; j < 10; j++){
				HashMap<String,String> ptt = new HashMap<String,String>();
				ptt.put("pro_no", ""+j);
				ptt.put("pro_name", "小类产品"+i+j);
				ptt.put("pro_action", "产品的功能是"+i+j);
				ptt.put("pro_object", "产品的服务对象是"+i+j);
				ptt.put("pro_spot", "产品的优点是"+i+j);
				ptt.put("pro_struct", "产品的交易结构为结构"+i+j);
				ptt.put("pro_stuff", "客户需要提供身份证件、营业执照等相关信息.");
				table01.add(ptt);
			}
			pt.put("table_01", table01);
			total01.add(pt);
		}
		dataMap.put("total_01", total01);
		
		//单循环
		for(int i = 2; i < 10; i++){
			ArrayList<HashMap<String,String>> table = new ArrayList<HashMap<String,String>>();
			for(int j = 0; j < 10; j++){
				HashMap<String,String> ptt = new HashMap<String,String>();
				ptt.put("index", ""+j);
				ptt.put("demand", "需求方案"+j);
				ptt.put("mod_name", "产品名字"+j);
				table.add(ptt);
			}
			dataMap.put("table_0"+i, table);
		}
		
	}

}
