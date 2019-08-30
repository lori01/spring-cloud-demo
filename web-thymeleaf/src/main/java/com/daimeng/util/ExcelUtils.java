package com.daimeng.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.eval.FunctionEval;
import org.apache.poi.ss.formula.eval.NumberEval;
import org.apache.poi.ss.formula.eval.OperandResolver;
import org.apache.poi.ss.formula.eval.ValueEval;
import org.apache.poi.ss.formula.functions.Function;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;

/**
 * 
* @功能描述: excel操作,主要为excel的删除行/删除列/隐藏行/隐藏列
* @名称: ExcelUtils.java 
* @路径 com.daimeng.util 
* @作者 daimeng@tansun.com.cn
* @创建时间 2019年4月20日 下午2:51:22 
* @version V1.0
 */
public class ExcelUtils {
	
	{
		initFormula();
	}
	
	
	public static void main(String[] args) {
		//删除等
		/*String src = "D:/java_test/excel/Excel_Remove_Mod_less.xls";
		SimpleDateFormat sdf_datetime_format = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = sdf_datetime_format.format(Calendar.getInstance().getTime());
		System.out.println(date);
		String targ = "D:/java_test/excel/new_excel_" +date+ ".xls";
		delRowAndColumn(src, targ);
		System.out.println(targ);*/
		
		//自定义公式
		/*initFormula();
		try {
			String src = "D:/java_test/excel/新公式.xls";
			FileInputStream is = new FileInputStream(src);
            HSSFWorkbook workbook = new HSSFWorkbook(is);
            HSSFSheet sheet = getSheet(workbook, 0, "");
            HSSFRow row = sheet.getRow(0);
            HSSFCell cell = row.getCell(1);
            cell.setCellValue(270d);
            evaluate(workbook, 0, null);
            System.out.println(row.getCell(0).getNumericCellValue());
            FileOutputStream os = new FileOutputStream(src);
            workbook.write(os);
            is.close();
            os.close();
		}catch (Exception e){
			
		}*/
		
		//图
		/*String src = "D:/java_test/excel/新设法人房地产模板.xls";
		SimpleDateFormat sdf_datetime_format = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = sdf_datetime_format.format(Calendar.getInstance().getTime());
		System.out.println(date);
		String targ = "D:/java_test/excel/新设法人房地产模板_new_excel_" +date+ ".xls";
		drawPic(src, targ);
		System.out.println(targ);*/
		
		//System.out.println(getSumFormulaString("F10:B810+SUM(A10:S10)+B8+SUM(SS14+YY84)"));
		//System.out.println(getFormulaPositionFromFormula("=INDEX(C34:BL34,MATCH(0,C29:BL29,1))-INDEX(C29:BL29,MATCH(0,C29:BL29,1))/INDEX(C28:BL28,MATCH(0,C29:BL29,1)+1)-1+fzb1!$C$4/12"));
		//System.out.println(getFormulaPositionFromFormula("=IF(E17=0,0,(D47*E18+SUMPRODUCT(D28:D32,E28:E32)+SUMPRODUCT(D34:D38,E34:E38)+SUMPRODUCT(D42:D43,E42:E43))/E17)"));
	
		//System.out.println(getFormulaAllCoordinate("A1+B1+c3+SUM(A1:B1)+(AA44*BB55+SS66)/C78+PGB1!A1+$C$3+$C4+C$5"));
		
		String formula = "K2+H1+J2+M1+O2+PGB11!K8+$A$1+B$2+$C3+$K$2+$K2+K$2+SUM(H1:M1)+SUM($H$1:$M1)+SUM(A10:D10)+SUM(M5:O5)+SUM(M$5:O5)";
		//System.out.println(getFormulaAllSection(formula));
		//System.out.println(reWriteFormulaWithSection(formula, 5, 10));
		//System.out.println(reWriteFormulaWithoutSection(formula, 5, 10));
		System.out.println(reWriteFormulaWithoutSection(reWriteFormulaWithSection(formula, 5, 10), 5, 10));
		//System.out.println(reWriteFormulaWithRelation("PGB2!$K$2+PGB2!$K2+PGB2!K$2+PGB2!K2", "PGB2", 5, 10));
	
	}
	//测试修改数据后图会不会自动生成-答案是会的
	private static void drawPic(String src, String targ){
		try {
			long start = System.currentTimeMillis();
			FileInputStream is = new FileInputStream(src);
            HSSFWorkbook workbook = new HSSFWorkbook(is);
            
            int sheetNum = workbook.getNumberOfSheets();
            long t1 = System.currentTimeMillis();
            System.out.println(t1);
        	HSSFSheet sheet = getSheet(workbook, null, "fzb7.1");
        	long t2 = System.currentTimeMillis();
        	System.out.println(t2);
        	System.out.println("获取sheet消耗的时间" + (t2-t1));
        	System.out.println(sheet.getSheetName());
    		System.out.println("=====start=====");
    		for(int j = 5; j <= 13; j++ ){
    			HSSFRow row = sheet.getRow(j); 
    			//excel可能从中间开始才有数据，row.getPhysicalNumberOfCells()获取不为空的列个数
        		//getLastCellNum()是获取最后一个不为空的列是第几个
    			System.out.println("row.getPhysicalNumberOfCells()" + row.getPhysicalNumberOfCells());
    			System.out.println("row.getLastCellNum()" + row.getLastCellNum());
    			for(int k = 1; k <= 6; k++){
    				HSSFCell cell = row.getCell(k);
    				cell.setCellValue(k*10 + j);
    			}
    		}
           //evaluate(workbook, i, "");
            
            //执行计算公式
            //HSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
            //循环所有,执行计算公式
            //evaluate(workbook);
            
            FileOutputStream os = new FileOutputStream(targ);
            workbook.write(os);
            is.close();
            os.close();
            
            System.out.println("===export excel success!===");
            long end = System.currentTimeMillis();
            System.out.println("===整个过程消耗了"+(end-start)+"ms===");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	//测试删除行列
	private static void delRowAndColumn(String src, String targ){
		try {
			long start = System.currentTimeMillis();
			FileInputStream is = new FileInputStream(src);
            HSSFWorkbook workbook = new HSSFWorkbook(is);
            HSSFSheet sheet = workbook.getSheetAt(0);
            
            //同一类型的操作,删除和隐藏不要同时用,否则会有错乱!!
            //要么都用删除,要么都用隐藏
            //可以row用删除,column用隐藏
            
            /*System.out.println("===delete row start!===");
            sheet = delRow(sheet, 60, 145);
            sheet = delRow(sheet, 20, 45);
            System.out.println("===delete row end!===");
            
            System.out.println("===delete column start!===");
            sheet = delColumn(sheet, 30, 40);
            sheet = delColumn(sheet, 15, 20);
            System.out.println("===delete column end!===");*/
            
            /*System.out.println("===hide row start!===");
            sheet = hideRow(sheet, 170, 180, false);
            sheet = hideRow(sheet, 50, 160, true);
            System.out.println("===hide row end!===");
            
            System.out.println("===hide column start!===");
            sheet = hideColumn(sheet, 35, 130, true);
            sheet = hideColumn(sheet, 30, 34, false);
            sheet = hideColumn(sheet, 12, 26, true);
            System.out.println("===hide column end!===");*/
            
            /*System.out.println("===hide row start!===");
            sheet = hideRow(sheet, 27, 33,false);
            sheet = hideRow(sheet, 6, 10,false);
            System.out.println("===hide row end!===");*/
            
            /*System.out.println("===hide column start!===");
            sheet = hideColumn(sheet, 35, 130, true);
            sheet = hideColumn(sheet, 30, 34, false);
            sheet = hideColumn(sheet, 12, 26, true);
            System.out.println("===hide column end!===");*/
            System.out.println("===delete column start!===");
            sheet = delColumn(sheet, 57, 141);//27-141
            sheet = delColumn(sheet, 16, 26);//6-26
            System.out.println("===delete column end!===");
            
            reWriteFormulaForDelEndColumn(sheet, 57, 141);//27-141
            reWriteFormulaForDelEndColumn(sheet, 16, 26);//6-26
            
            //执行计算公式 
            //HSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
            //循环所有,执行计算公式
            evaluate(workbook, 0, "");
            
            
            System.out.println("隐藏sheet");
            HSSFSheet sheet1 = getSheet(workbook, null, "no_sheet");
            HSSFCell cell1 = getCell(sheet1, 0, 0);
            cell1.setCellValue("no_123456_ss22");
            System.out.println(cell1.getStringCellValue());
            
            
            
            FileOutputStream os = new FileOutputStream(targ);
            workbook.write(os);
            is.close();
            os.close();
            
            System.out.println("===export excel success!===");
            long end = System.currentTimeMillis();
            System.out.println("===整个过程消耗了"+(end-start)+"ms===");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	/**
	 * 
	* @功能描述: 处理公式的公共方法type=1(删除末尾列，重写当前sheet公式) type=2(删除中间列，重写当前sheet公式) type=3(删除中间列，重写关联sheet公式)
	* @方法名称: reWriteFormulaCommon 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年6月30日 下午2:36:00 
	* @version V1.0   
	* @param sheet
	* @param currSheetName
	* @param delStartIndex
	* @param delEndIndex
	* @param type 
	* @return void
	 */
	private static void reWriteFormulaCommon(HSSFSheet sheet,String currSheetName, int delStartIndex,int delEndIndex,int type){
		for(int rowid = 0; rowid <= sheet.getLastRowNum(); rowid++) {
        	HSSFRow row = sheet.getRow(rowid);
        	if(row != null){
        		for(int cid = 0; cid < row.getLastCellNum(); cid++) {
                	HSSFCell cell = row.getCell(cid);
                	if(cell != null){
                		if(cell.getCellTypeEnum() == CellType.FORMULA) {
							String formula = "";
							boolean formulaError = false;
                			try {
								//原始公式
								formula = cell.getCellFormula();
							}catch (Exception e){
                				System.out.println("error");
								System.out.println(e.getMessage());
								formulaError = true;
								formula = cell.getStringCellValue();
							}
                			//删除末尾列
                			if(type == 1){
                				setFormulaForDelEndColumn(cell, formula, delStartIndex, delEndIndex, formulaError);
                			}
                			//删除中间列
                			else if(type == 2){
                				setFormulaForDelMiddleColumn(cell, formula, delStartIndex, delEndIndex, formulaError);
                			}
                			//更新关联表
                			else if(type == 3){
                				setFormulaForRelationColumn(cell, currSheetName, formula, delStartIndex, delEndIndex, formulaError);
                			}
                            System.out.println(formula);
                            System.out.println(cell.getNumericCellValue());
                        }
                	}
                }
        	}
        }
	}
	
	private static void setFormulaForDelEndColumn(HSSFCell cell,String formula,int delStartIndex,int delEndIndex,boolean formulaError){
		//判断公式
        if(formula != null && !"".equals(formula) && formula.indexOf(":") > -1){
        	System.out.println("+++create new formal start 1+++");
    		System.out.println("旧公式="+formula);
    		//处理区间公式
    		formula = reWriteFormulaWithSection(formula, delStartIndex, delEndIndex);
    		cell.setCellFormula(formula);
        	System.out.println("新公式="+formula);
        	System.out.println(cell.getNumericCellValue());
        	System.out.println("+++create new formal end 1+++");
        }else{
        	if(formulaError){
				System.out.println("rewrite forlmula");
				cell.setCellFormula(formula);
				System.out.println("+++create new formal end+++");
			}
		}
	}
	
	private static void setFormulaForDelMiddleColumn(HSSFCell cell,String formula,int delStartIndex,int delEndIndex,boolean formulaError){
		//判断公式
        if(formula != null && !"".equals(formula) && formula.indexOf(":") > -1){
        	System.out.println("+++create new formal start 1+++");
    		System.out.println("旧公式="+formula);
    		//处理区间公式
    		formula = reWriteFormulaWithSection(formula, delStartIndex, delEndIndex);
    		//处理非区间的公式
    		formula = reWriteFormulaWithoutSection(formula, delStartIndex, delEndIndex);
    		cell.setCellFormula(formula);
        	System.out.println("新公式="+formula);
        	System.out.println(cell.getNumericCellValue());
        	System.out.println("+++create new formal end 1+++");
        }
        else if(formula != null && !"".equals(formula) && formula.indexOf(":") == -1){
        	System.out.println("+++create new formal start 2+++");
    		System.out.println("旧公式="+formula);
    		//处理非区间的公式
    		formula = reWriteFormulaWithoutSection(formula, delStartIndex, delEndIndex);
        	cell.setCellFormula(formula);
        	System.out.println("新公式="+formula);
        	System.out.println(cell.getNumericCellValue());
        	System.out.println("+++create new formal end 2+++");
        }
        else{
        	if(formulaError){
				System.out.println("rewrite forlmula");
				cell.setCellFormula(formula);
				System.out.println("+++create new formal end+++");
			}
		}
	}
	
	private static void setFormulaForRelationColumn(HSSFCell cell,String currSheetName, String formula,int delStartIndex,int delEndIndex,boolean formulaError){
		if(formula != null && !"".equals(formula) && formula.indexOf(":") > -1){
        	System.out.println("+++create new formal start 1+++");
    		System.out.println("旧公式="+formula);
    		//处理区间公式
    		formula = reWriteFormulaWithRelation(formula, currSheetName, delStartIndex, delEndIndex);
    		cell.setCellFormula(formula);
        	System.out.println("新公式="+formula);
        	System.out.println(cell.getNumericCellValue());
        	System.out.println("+++create new formal end 1+++");
        }else{
        	if(formulaError){
				System.out.println("rewrite forlmula");
				cell.setCellFormula(formula);
				System.out.println("+++create new formal end+++");
			}
		}
	}
	
	/**
	 * 
	* @功能描述: 当删除末尾列时，修改区间公式
	* @方法名称: reWriteFormula 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月30日 下午12:03:48 
	* @version V1.0   
	* @param sheet
	* @param delColumnStartIndex
	* @param delColumnEndIndex 
	* @return void
	 */
	public static void reWriteFormulaForDelEndColumn(HSSFSheet sheet,int delStartIndex,int delEndIndex){
		reWriteFormulaCommon(sheet, "", delStartIndex, delEndIndex, 1);
	}
	
	/**
	 * 
	* @功能描述: 当删除中间列时，修改区间公式和非区间公式
	* @方法名称: reWriteFormulaForDelMiddleColumn 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年6月20日 下午8:13:48 
	* @version V1.0   
	* @param sheet
	* @param delStartIndex
	* @param delEndIndex 
	* @return void
	 */
	public static void reWriteFormulaForDelMiddleColumn(HSSFSheet sheet,int delStartIndex,int delEndIndex){
		reWriteFormulaCommon(sheet, "", delStartIndex, delEndIndex, 2);
	}
	
	/**
	 * 
	* @功能描述: 删除关联其他表格的公式，如PGB2!K2+PBG2!$K$2+PBG2!K$2+PBG2!$K2
	* @方法名称: reWriteFormulaForRelationColumn 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年6月30日 下午2:15:55 
	* @version V1.0   
	* @param sheet
	* @param delStartIndex
	* @param delEndIndex 
	* @return void
	 */
	public static void reWriteFormulaForRelationColumn(HSSFSheet sheet,String currSheetName, int delStartIndex,int delEndIndex){
		reWriteFormulaCommon(sheet, currSheetName, delStartIndex, delEndIndex, 3);
	}
	
	/**
	 * 
	* @功能描述: 处理区间公式
	* @方法名称: reWriteFormulaWithSection 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年6月20日 下午10:31:56 
	* @version V1.0   
	* @param formula
	* @param delStartIndex
	* @param delEndIndex
	* @return 
	* @return String
	 */
	private static String reWriteFormulaWithSection(String formula,int delStartIndex,int delEndIndex){
		//获取所有公式内部的计算单元格区间
		//ArrayList<String> formualPositionList = getFormulaSection(formula);
		ArrayList<String> formualPositionList = getFormulaSpecifiedReference(formula,"$!:.");
		System.out.println(formualPositionList);
		String newFormula = "";
		System.out.println(formualPositionList);
		for(String oldFormualPosition : formualPositionList){
			if(oldFormualPosition.indexOf(":") > -1 && oldFormualPosition.indexOf("!") == -1){
				System.out.println("旧公式区间="+oldFormualPosition);
	        	String newFormalPosition = reWriteFormulaPosition(oldFormualPosition, delStartIndex, delEndIndex);
	        	newFormula += newFormalPosition;
	        	System.out.println("新公式区间="+newFormalPosition);
			}else{
				newFormula += oldFormualPosition;
			}
			
		}
		return newFormula;
	}
	
	/**
	 * 
	* @功能描述: 处理非区间的公式
	* @方法名称: reWriteFormulaWithoutSection 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年6月20日 下午10:31:32 
	* @version V1.0   
	* @param formula
	* @param delStartIndex
	* @param delEndIndex
	* @return 
	* @return String
	 */
	private static String reWriteFormulaWithoutSection(String formula,int delStartIndex,int delEndIndex){
		//获取所有公式内部的计算单元格区间
		ArrayList<String> formualPositionList = getFormulaSpecifiedReference(formula,"$!:.");
		System.out.println(formualPositionList);
		String newFormula = "";
		for(String oldFormualPosition : formualPositionList){
			char array[] = oldFormualPosition.toCharArray();
			if((Character.isLetter(array[0]) || Character.isLetter(array.length > 1 ? array[1] : array[0])) 
					&& Character.isDigit(array[array.length-1]) 
					&& oldFormualPosition.indexOf("!") == -1
					&& oldFormualPosition.indexOf(":") == -1){
				System.out.println("旧坐标="+oldFormualPosition);
				//获取字母中的行数字
		    	String rowNum = getNumberFromString(oldFormualPosition);
		    	String columnLetterCur = oldFormualPosition.replace(rowNum, "");
		    	String columnLetter = columnLetterCur.replace("$", "");
		    	int columnIndex = letterToNumber(columnLetter);
		    	int newColumnIndex = columnIndex - (delEndIndex - delStartIndex + 1);
		    	if(columnIndex > delStartIndex && newColumnIndex > 0){
		    		String newColumnLetter = numberToLetter(newColumnIndex);
		    		//String newFormalPosition = newColumnLetter + rowNum;
		    		String newFormalPosition = oldFormualPosition.replace(columnLetter, newColumnLetter);
		    		newFormula += newFormalPosition;
		    		System.out.println("新坐标="+newFormalPosition);
		    	}else{
		    		System.out.println("新坐标="+oldFormualPosition);
		    		newFormula += oldFormualPosition;
		    	}
			}else{
				newFormula += oldFormualPosition;
			}
			
		}
		return newFormula;
		
	}
	
	/**
	 * 
	* @功能描述: 处理关联其他表格的公式
	* @方法名称: reWriteFormulaWithRelation 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年6月30日 下午2:19:02 
	* @version V1.0   
	* @param formula
	* @param currSheetName
	* @param delStartIndex
	* @param delEndIndex
	* @return 
	* @return String
	 */
	private static String reWriteFormulaWithRelation(String formula,String currSheetName, int delStartIndex,int delEndIndex){
		//获取所有公式内部的计算单元格区间
		ArrayList<String> formualPositionList = getFormulaSpecifiedReference(formula,"$!:.");
		System.out.println(formualPositionList);
		String newFormula = "";
		for(String oldFormualPosition : formualPositionList){
			if(oldFormualPosition.indexOf("!") > -1 && formula.startsWith(currSheetName)){
				System.out.println("旧坐标="+oldFormualPosition);
				//获取字母中的行数字
				String rowNum = getNumberFromString(oldFormualPosition.split("!")[1]);
				String columnLetterCur = oldFormualPosition.split("!")[1].replace(rowNum, "");
				String columnLetter = columnLetterCur.replace("$", "");
				int columnIndex = letterToNumber(columnLetter);
				int newColumnIndex = columnIndex - (delEndIndex - delStartIndex + 1);
				if(columnIndex > delStartIndex && newColumnIndex > 0){
					String newColumnLetter = numberToLetter(newColumnIndex);
					//String newFormalPosition = newColumnLetter + rowNum;
					String newFormalPosition = oldFormualPosition.replace(columnLetter, newColumnLetter);
					newFormula += newFormalPosition;
					System.out.println("新坐标="+newFormalPosition);
				}else{
					System.out.println("新坐标="+oldFormualPosition);
					newFormula += oldFormualPosition;
				}
			}else{
				newFormula += oldFormualPosition;
			}
			
		}
		return newFormula;
		
	}
	
	
	/**
	 * 
	* @功能描述: 重写公式区间
	* @方法名称: reWriteFormulaPosition 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年5月13日 下午2:57:53 
	* @version V1.0   
	* @param formula
	* @param delStartIndex
	* @param delEndIndex
	* @return 
	* @return String
	 */
	private static String reWriteFormulaPosition(String position,int delStartIndex,int delEndIndex){
    	String oriStartLetter = position.split(":")[0];
    	String oriEndLetter = position.split(":")[1];
    	
    	String newStartLetter = getNewStartLetter(oriStartLetter, delStartIndex, delEndIndex);
    	String newEndLetter = getNewEndLetter(oriEndLetter, delStartIndex, delEndIndex);
    	
    	String newFormal = newStartLetter + ":" + newEndLetter;
    	return newFormal;
	}
	
	/**
	 * 
	* @功能描述: 根据删除的列，重新定位公式的起始列数
	* @方法名称: getNewStartLetter 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年5月1日 下午1:43:03 
	* @version V1.0   
	* @param oriStartLetter
	* @param delStartIndex
	* @param delEndIndex
	* @return 
	* @return String
	 */
	private static String getNewStartLetter(String oriStartLetter,int delStartIndex,int delEndIndex){
		String newStartLetter = "";
		//获取字母中的行数字
    	String currRowNum = getNumberFromString(oriStartLetter);
    	String currColumnLetter = oriStartLetter.replace(currRowNum, "");
    	String columnLetter = currColumnLetter.replace("$", "");
    	int oriStartIndex = letterToNumber(columnLetter);
		//删除段在公式的前面，公式前移。删除多少列，前移多少列
    	if(oriStartIndex > delEndIndex){
    		newStartLetter = currColumnLetter.replace(columnLetter, numberToLetter(oriStartIndex-(delEndIndex-delStartIndex+1)) )
    				+ currRowNum;
    	}
    	//删除段包含公式的前部分，则公式从删除段的开始坐标开始
    	else if(oriStartIndex >= delStartIndex && oriStartIndex <= delEndIndex){
    		newStartLetter = currColumnLetter.replace(columnLetter, numberToLetter(delStartIndex) )
    				+ currRowNum;
    	}
    	//删除段在公式开始的后面，公式不变
    	else{
    		newStartLetter = oriStartLetter;
    	}
    	return newStartLetter;
	}
	
	/**
	 * 
	* @功能描述: 根据删除的列，重新定位公式的截至列数
	* @方法名称: getNewEndLetter 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年5月1日 下午1:43:31 
	* @version V1.0   
	* @param oriEndLetter
	* @param delStartIndex
	* @param delEndIndex
	* @return 
	* @return String
	 */
	private static String getNewEndLetter(String oriEndLetter,int delStartIndex,int delEndIndex){
		String newEndLetter = "";
		//获取字母中的行数字
    	String currRowNum = getNumberFromString(oriEndLetter);
    	String currColumnLetter = oriEndLetter.replace(currRowNum, "");
    	String columnLetter = currColumnLetter.replace("$", "");
		int oriEndIndex = letterToNumber(columnLetter);
		//删除段在公式的前面，公式前移。删除多少列，前移多少列
		if(oriEndIndex > delEndIndex){
    		newEndLetter = currColumnLetter.replace(columnLetter, numberToLetter(oriEndIndex-(delEndIndex-delStartIndex+1)) )
    				+ currRowNum;
    	}
		//删除段包含公式的后半部分，则公式的截止坐标在删除段开始坐标的前面一列
    	else if(oriEndIndex >= delStartIndex && oriEndIndex <= delEndIndex){
    		newEndLetter = currColumnLetter.replace(columnLetter, numberToLetter(delStartIndex-1) )
    				+ currRowNum;
    	}
		//删除段在公式截止的后面，公式不变
    	else{
    		newEndLetter = oriEndLetter;
    	}
		return newEndLetter;
	}

	/**
	 * 
	* @功能描述: 删除行,然后将下面未删除的行移动上来,index从1开始算
	* @方法名称: delRow 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年5月13日 下午3:11:48 
	* @version V1.0   
	* @param sheet
	* @param start
	* @param end
	* @return 
	* @return HSSFSheet
	 */
	public static HSSFSheet delRow(HSSFSheet sheet,int start,int end){
		int rowNum = sheet.getLastRowNum();
		int rowCount = rowNum+1;
		System.out.println("rowCount="+rowCount);
		if(end > rowCount){
			end = rowCount;
		}
		
		sheet = delRowData(sheet, start, end);
        //删除1-5行,则是把6以下的行数往上移动5格,这样可以达到删除空行的效果  1-5-1
        if(end != rowCount){
        	sheet.shiftRows(end, rowCount, start-end-1);
        }
		return sheet;
	}
	
	/**
	 * 
	* @功能描述: 删除row的数据,但会保留空的row,index从1开始算
	* @方法名称: delRowData 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月24日 下午3:19:09 
	* @version V1.0   
	* @param sheet
	* @param start
	* @param end
	* @return 
	* @return HSSFSheet
	 */
	public static HSSFSheet delRowData(HSSFSheet sheet,int start,int end){
		//删除行内容,但会保留空行
        for(int i = start-1; i < end; i++){
        	HSSFRow row = sheet.getRow(i);
        	if(row != null){
        		sheet.removeRow(row);
        	}
        }
        return sheet;
	}
	
	/**
	 * 
	* @功能描述: 删除列,然后将后面未删除的列复制到当前删除的列上,然后删除后面所有的列,index从1开始算
	* @方法名称: delColumn 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年5月13日 下午3:13:24 
	* @version V1.0   
	* @param sheet
	* @param start
	* @param end
	* @return 
	* @return HSSFSheet
	 */
	public static HSSFSheet delColumn(HSSFSheet sheet,int start,int end){
		HSSFRow firstrow = sheet.getRow(0);
        int columnCount = firstrow.getLastCellNum();
        System.out.println("columnCount="+columnCount);
        
        int rowNum = sheet.getLastRowNum();
		int rowCount = rowNum+1;
		System.out.println("new rowCount="+rowCount);
		
        if(end > columnCount){
			end = columnCount;
		}
        
        for(int rid = 0; rid < rowCount; rid++){
        	HSSFRow row = sheet.getRow(rid);
        	if(row != null){
        		//然后复制不删除的column到前面,所以原本不删除的column会重复,所以要删除所有
        		for(int cid = start-1; cid < columnCount; cid++){
            		HSSFCell cell = row.getCell(cid);
        			if(cell != null){
        				row.removeCell(cell);
        				//复制后面保留行的cell到当前行
        				int clonid = cid + end - start + 1;
        				if(clonid < columnCount){
        					HSSFCell cNext = row.getCell(clonid);
                			if (cNext != null) {
                				HSSFCell cNew = row.createCell(cid, cNext.getCellTypeEnum());
                				cloneCellFromEnum(cNew, cNext);
                				//Set the column width only on the first row.
                                //Other wise the second row will overwrite the original column width set previously.
                                if(rid == 0) {
                                	sheet.setColumnWidth(cid, sheet.getColumnWidth(clonid));
                                }
                	        }
        				}
        			}
                }
        	}
        }
		return sheet;
	}
	
	/**
	 * 
	* @功能描述: 复制单元格内容-仅支持新版本的poi
	* @方法名称: cloneCellFromEnum 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年5月13日 下午3:14:09 
	* @version V1.0   
	* @param cNew
	* @param cOld 
	* @return void
	 */
	public static void cloneCellFromEnum(HSSFCell cNew, HSSFCell cOld) {
		cNew.setCellComment(cOld.getCellComment());
		cNew.setCellStyle(cOld.getCellStyle());
		if (CellType.BOOLEAN == cNew.getCellTypeEnum()) {
			cNew.setCellValue(cOld.getBooleanCellValue());
		} else if (CellType.NUMERIC == cNew.getCellTypeEnum()) {
			cNew.setCellValue(cOld.getNumericCellValue());
		} else if (CellType.STRING == cNew.getCellTypeEnum()) {
			cNew.setCellValue(cOld.getStringCellValue());
		} else if (CellType.ERROR == cNew.getCellTypeEnum()) {
			cNew.setCellValue(cOld.getErrorCellValue());
		} else if (CellType.FORMULA == cNew.getCellTypeEnum()) {
			cNew.setCellValue(cOld.getCellFormula());
		}
	}
	
	/**
	 * 
	* @功能描述: 复制单元格内容-旧版本poi的方法
	* @方法名称: cloneCellFromType 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年5月13日 下午3:14:24 
	* @version V1.0   
	* @param cNew
	* @param cOld 
	* @return void
	 */
	public static void cloneCellFromType(HSSFCell cNew, HSSFCell cOld) {
		cNew.setCellComment(cOld.getCellComment());
		cNew.setCellStyle(cOld.getCellStyle());
		switch (cNew.getCellType()) {
			case HSSFCell.CELL_TYPE_BOOLEAN: {
				cNew.setCellValue(cOld.getBooleanCellValue());
				break;
			}
			case HSSFCell.CELL_TYPE_NUMERIC: {
				cNew.setCellValue(cOld.getNumericCellValue());
				break;
			}
			case HSSFCell.CELL_TYPE_STRING: {
				cNew.setCellValue(cOld.getStringCellValue());
				break;
			}
			case HSSFCell.CELL_TYPE_ERROR: {
				cNew.setCellValue(cOld.getErrorCellValue());
				break;
			}
			case HSSFCell.CELL_TYPE_FORMULA: {
				cNew.setCellFormula(cOld.getCellFormula());
				break;
			}
		}
	}
	
	/**
	 * 
	* @功能描述: 隐藏行,可选是否删除数据,index从1开始算
	* @方法名称: hideRow 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年5月13日 下午3:14:55 
	* @version V1.0   
	* @param sheet
	* @param start
	* @param end
	* @param delData
	* @return 
	* @return HSSFSheet
	 */
	public static HSSFSheet hideRow(HSSFSheet sheet,int start,int end,boolean delData){
		int rowNum = sheet.getLastRowNum();
		int rowCount = rowNum+1;
		System.out.println("rowCount="+rowCount);
		if(end > rowCount){
			end = rowCount;
		}
		
		if(delData){
			sheet = delCellDataForRow(sheet, start, end);
		}
		//行高为0则隐藏
		for(int i = start-1; i < end; i++){
			HSSFRow row = sheet.getRow(i);
			if(row != null){
				row.setZeroHeight(true);
        	}
		}
		return sheet;
	}
	
	/**
	 * 
	* @功能描述: 一格一格删除cell,为的是不把row变为null,index从1开始算
	* @方法名称: delCellDataForRow 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月25日 上午10:05:12 
	* @version V1.0   
	* @param sheet
	* @param start
	* @param end
	* @return 
	* @return HSSFSheet
	 */
	public static HSSFSheet delCellDataForRow(HSSFSheet sheet,int start,int end){
		//删除行内容,但会保留空行
        for(int i = start-1; i < end; i++){
        	HSSFRow row = sheet.getRow(i);
        	if(row != null){
        		int columnCount = row.getLastCellNum();
        		for(int j = 0 ; j < columnCount; j++){
        			HSSFCell cell = row.getCell(j);
        			if(cell != null){
        				row.removeCell(cell);
        			}
        		}
        	}
        }
        return sheet;
	}
	
	/**
	 * 
	* @方法名称: hideColumn ,index从1开始算
	* @路径 com.daimeng.util 
	* @功能描述: 隐藏列,并且清空隐藏列的数据
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月19日 下午3:30:31 
	* @version V1.0   
	* @param sheet
	* @param start
	* @param end
	* @return 
	* HSSFSheet
	 */
	public static HSSFSheet hideColumn(HSSFSheet sheet,int start,int end,boolean delData){
		HSSFRow row = sheet.getRow(0);
        int columnCount = row.getLastCellNum();
        System.out.println("columnCount="+columnCount);
        
        int rowNum = sheet.getLastRowNum();
		int rowCount = rowNum+1;
		System.out.println("rowCount="+rowCount);
		
        if(end > columnCount){
			end = columnCount;
		}
        if(delData){
        	sheet = delCellDataForColumn(sheet, start, end);
        }
        for(int i = start-1; i < end; i++){
        	sheet.setColumnWidth(i, 0); 
        }
		return sheet;
	}
	
	/**
	 * 
	* @功能描述: 删除column的内容,index从1开始算
	* @方法名称: removeCellDataForColumn 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月24日 下午3:44:24 
	* @version V1.0   
	* @param sheet
	* @param start
	* @param end
	* @return 
	* @return HSSFSheet
	 */
	public static HSSFSheet delCellDataForColumn(HSSFSheet sheet,int start,int end){
		int rowNum = sheet.getLastRowNum();
		for(int i = start-1; i < end; i++){
			for(int j = 0; j <= rowNum; j++){
				HSSFRow row = sheet.getRow(j);
				if(row != null){
					HSSFCell cell = row.getCell(i);
	        		if(cell != null) row.removeCell(cell);
	        	}
			}
        	
        }
        return sheet;
	}
	
	/**
	 * 
	* @功能描述: 根据index或name获取sheet
	* @方法名称: getSheet 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月30日 上午10:35:31 
	* @version V1.0   
	* @param wb
	* @param sheetId
	* @param sheetName
	* @return 
	* @return HSSFSheet
	 */
	public static HSSFSheet getSheet(HSSFWorkbook wb,Integer sheetIndex, String sheetName){
		if(sheetIndex != null){
			return wb.getSheetAt(sheetIndex);
		}else if(sheetName != null && !"".equals(sheetName)){
			for(int sheetNum = 0; sheetNum < wb.getNumberOfSheets(); sheetNum++) {
            	HSSFSheet sheet = wb.getSheetAt(sheetNum);
        		if(sheetName.equals(sheet.getSheetName())){
        			return sheet; 
        		}
            }
		}
		return null;
	}
	
	/**
	 * 
	* @功能描述: 执行workbook的公式
	* @方法名称: evaluate 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月25日 下午3:25:21 
	* @version V1.0   
	* @param wb
	* @param sheetId 只执行指定sheet的公式
	* @param sheetName 只执行指定sheet的公式
	* @return void
	 */
	public static void evaluate(HSSFWorkbook wb,Integer sheetIndex, String sheetName){
        FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
        HSSFSheet sheet = getSheet(wb, sheetIndex, sheetName);
        if(sheet != null){
        	evalSheet(evaluator, sheet);
        }
        else{
        	for(int sheetNum = 0; sheetNum < wb.getNumberOfSheets(); sheetNum++) {
        		evalSheet(evaluator, wb.getSheetAt(sheetNum));
            }
        }
	}
	
	/**
	 * 
	* @功能描述: 执行sheet的计算公式
	* @方法名称: evalSheet 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月25日 下午3:25:03 
	* @version V1.0   
	* @param evaluator
	* @param sheet 
	* @return void 
	 */
	private static void evalSheet(FormulaEvaluator evaluator, HSSFSheet sheet){
		for(int rowid = 0; rowid <= sheet.getLastRowNum(); rowid++) {
        	HSSFRow row = sheet.getRow(rowid);
        	if(row != null){
        		//excel可能从中间开始才有数据，row.getPhysicalNumberOfCells()获取不为空的列个数
        		//getLastCellNum()是获取最后一个不为空的列是第几个
        		for(int cid = 0; cid < row.getLastCellNum(); cid++) {
                	HSSFCell cell = row.getCell(cid);
                	if(cell != null){
                		if(cell.getCellTypeEnum() == CellType.FORMULA) {
                            evaluator.evaluateFormulaCell(cell);
                            String formular = cell.getCellFormula();
                            if(formular.indexOf("SLN") > -1){
                            	String newformular = reWriteSln(formular);
                            	cell.setCellFormula(newformular);
                            	cell.getNumericCellValue();
                            }
                        }
                	}
                }
        	}
        }
	}
	
	/**
	 * 
	* @功能描述: 获取对应右括号的index
	* @方法名称: getRightBrackerIndex 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年8月29日 下午9:08:01 
	* @version V1.0   
	* @param formular
	* @return 
	* @return int
	 */
	public static int getRightBrackerIndex(String formular){
		char[] letters = formular.toCharArray();
		int leftBrack = 0;
		for(int i=0; i < letters.length; i ++){
			if(letters[i] == '('){
				leftBrack ++;
			}else if(letters[i] == ')'){
				leftBrack --;
			}
			if(leftBrack == 0){
				return i;
			}
		}
		return 0;
	}
	
	/**
	 * 
	* @功能描述: 重写SLN公式，替换到cell中
	* @方法名称: reWriteSln 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年8月29日 下午9:16:53 
	* @version V1.0   
	* @param formular
	* @return 
	* @return String
	 */
	public static String reWriteSln(String formular){
		//(d1-d2)/d3
		formular = formular.replace(" ", "");
		String[] slns = formular.split("SLN");
		if(slns.length == 2){
			String sln = slns[1].substring(1, getRightBrackerIndex(slns[1]));
			String[] forms = sln.split(",");
			if(forms.length == 3){
				String d1 = forms[0];
				String d2 = forms[1];
				String d3 = forms[2];
				String newformul = "(("+d1+"-"+d2+")/"+d3+")";
				return formular.replace("SLN("+sln+")", newformul);
			}
		}
		return formular;
	}
	
	/**
	 * 
	* @功能描述: 初始化poi未实现的公式
	* @方法名称: initFormula 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年6月18日 下午8:10:25 
	* @version V1.0    
	* @return void
	 */
	public static void initFormula(){
		Function slnf = new Function() {
			@Override
			public ValueEval evaluate(ValueEval[] arg0, int arg1, int arg2) {
				try {
					if(arg0.length != 3){
						return null;
					}
					ValueEval v1 = OperandResolver.getSingleValue(arg0[0], arg1, arg2);
					ValueEval v2 = OperandResolver.getSingleValue(arg0[1], arg1, arg2);
					ValueEval v3 = OperandResolver.getSingleValue(arg0[2], arg1, arg2);
					Double d1 = OperandResolver.coerceValueToDouble(v1);
					Double d2 = OperandResolver.coerceValueToDouble(v2);
					Double d3 = OperandResolver.coerceValueToDouble(v3);
					Double result = (d1-d2)/d3;
					return new NumberEval(result);
				} catch (Exception e) {
					// TODO: handle exception
				}
				return null;
			}
		};
		FunctionEval.registerFunction("SLN", slnf);
	}
	
	/**
	 * 
	* @功能描述: 将EXCEL列字母转成数字
	* @方法名称: letterToNumber 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月30日 上午10:51:54 
	* @version V1.0   
	* @param letter
	* @return 
	* @return int
	 */
	public static int letterToNumber(String letter) {
	    // 检查字符串是否为空
	    if (letter == null || letter.isEmpty()) {
	        return -1;
	    }
	    String upperLetter = letter.toUpperCase(); // 转为大写字符串
	    if (!upperLetter.matches("[A-Z]+")) { // 检查是否符合，不能包含非字母字符
	        return -1;
	    }
	    long num = 0; // 存放结果数值
	    long base = 1;
	    // 从字符串尾部开始向头部转换
	    for (int i = upperLetter.length() - 1; i >= 0; i--) {
	        char ch = upperLetter.charAt(i);
	        num += (ch - 'A' + 1) * base;
	        base *= 26;
	        if (num > Integer.MAX_VALUE) { // 防止内存溢出
	            return -1;
	        }
	    }
	    return (int) num;
	}
	
	/**
	 * 
	* @功能描述: 将EXCEL数字转成列字母
	* @方法名称: numberToLetter 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月30日 上午10:52:18 
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
	
	/**
	 * 
	* @功能描述: 获取cell单元格公式表达式英文里面的数字，如F10里面的10
	* @方法名称: getNumberFromString 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月30日 上午11:19:53 
	* @version V1.0   
	* @param letter
	* @return 
	* @return String
	 */
	public static String getNumberFromString(String letter){
		String reg = "[^0-9]";
		Pattern p = Pattern.compile(reg);  
		Matcher m = p.matcher(letter);  
		if(m.find()){  
			System.out.println(letter+"->"+m.replaceAll("").trim());
		    return m.replaceAll("").trim();
		}else {
			System.out.println(letter+"->null");
			return "";
		}
	}
	
	/**
	 * 
	* @功能描述: 将一个公式按照单元格切割
	* @方法名称: getFormulaSpecifiedReference 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年6月30日 下午1:44:46 
	* @version V1.0   
	* @param formula
	* @return 
	* @return ArrayList<String>
	 */
	private static ArrayList<String> getFormulaSpecifiedReference(String formula, String prex){
		ArrayList<String> formulaList = new ArrayList<String>();
		char array[] = formula.toCharArray();
		boolean hasStartLetter = false;
		int startIndex = 0;
		int endIndex = 0;
		
		for(int i = 0; i < array.length; i++){
			if(Character.isLetterOrDigit(array[i]) || prex.indexOf(array[i]) > -1){
				if(!hasStartLetter){
					startIndex = i;
					hasStartLetter = true;
				}else if(i == (array.length - 1)){
					String coordinate = formula.substring(startIndex, i+1);
					formulaList.add(coordinate);
				}
				
			}else{
				if(hasStartLetter){
					endIndex = i;
					String coordinate = formula.substring(startIndex, endIndex);
					formulaList.add(coordinate);
					hasStartLetter = false;
					
				}
				String symbol = formula.substring(i, i+1);
				formulaList.add(symbol);
			}
		}
		
		
		return formulaList;
	}
	
	/**
	 * 
	* @功能描述: 去除list中重复的元素
	* @方法名称: removeRepeat 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年5月13日 下午2:46:21 
	* @version V1.0   
	* @param list
	* @return 
	* @return ArrayList<String>
	 */
	private static ArrayList<String> removeRepeat(ArrayList<String> list){
		for(int i = list.size() -1; i >= 0; i--){
			for(int j = 0; j < list.size(); j++){
				if(i != j && list.get(i).equals(list.get(j))){
					list.remove(i);
					break;
				}
			}
		}
		return list;
	}
	
	/**
	 * 
	* @功能描述: 通过row编号和column编号获取单元格，row和column从0开始计数
	* @方法名称: getHSSFCell 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年5月17日 上午10:20:02 
	* @version V1.0   
	* @param sheet
	* @param rowId
	* @param columnId
	* @return 
	* @return HSSFCell
	 */
	public static HSSFCell getCell(HSSFSheet sheet, int rowId, int columnId){
		if(sheet != null){
			HSSFRow row = sheet.getRow(rowId);
			if(row == null){
				sheet.createRow(rowId);
				row = sheet.getRow(rowId);
			}
			HSSFCell cell = row.getCell(columnId);
			if(cell == null){
				row.createCell(columnId);
				cell = row.getCell(columnId);
			}
			return cell;
		}
		return null;
	}
	
	
	
	
	
	/*
	 * 以下为不建议使用的方法
	 */
	
	/**
	 * 
	* @功能描述: 根据是删除的列，重写sum公式
	* @方法名称: reWriteFormulaForSum 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年5月1日 下午1:42:39 
	* @version V1.0   
	* @param formula
	* @param delStartIndex
	* @param delEndIndex
	* @return 
	* @return String
	 */
	@Deprecated
	private static String reWriteFormulaForSum(String formula,int delStartIndex,int delEndIndex){
		String letters = formula.substring(formula.indexOf("(")+1, formula.indexOf(")"));
    	String oriStartLetter = letters.split(":")[0];
    	String oriEndLetter = letters.split(":")[1];
    	
    	String newStartLetter = getNewStartLetter(oriStartLetter, delStartIndex, delEndIndex);
    	String newEndLetter = getNewEndLetter(oriEndLetter, delStartIndex, delEndIndex);
    	
    	String newFormal = "SUM(" + newStartLetter + ":" + newEndLetter + ")";
    	return newFormal;
	}
	/**
	 * 
	* @功能描述: 获取公式表格中的所有SUM公式
	* @方法名称: getSumFormulaString 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年5月13日 上午10:11:21 
	* @version V1.0   
	* @param formula
	* @return 
	* @return ArrayList<String>
	 */
	@Deprecated
	private static ArrayList<String> getSumFormulaString(String formula){
		ArrayList<String> newFormulaList = new ArrayList<String>();
		if(formula.indexOf("SUM(") > -1){
			String[] arr = formula.split("SUM");
			for(int i = 1; i < arr.length; i ++){
				String rightStr = arr[i];
				int rightBracketsPlace = rightStr.indexOf(")")+1;
				String newFormula = rightStr.substring(0,rightBracketsPlace);
				newFormulaList.add("SUM"+newFormula);
			}
			return newFormulaList;
		}else return null;
		
	}
	/**
	 * 
	* @功能描述: 获取一个公式中，所有的单元格区间表达式
	* 如=INDEX(C34:BL34,MATCH(0,C29:BL29,1))-INDEX(C29:BL29,MATCH(0,C29:BL29,1))/INDEX(C28:BL28,MATCH(0,C29:BL29,1)+1)-1+fzb1!$C$4/12
	* 取出[C34:BL34, C29:BL29, C29:BL29, C29:BL29, C28:BL28, C29:BL29]
	* @方法名称: getFormulaSection 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年5月13日 下午2:21:30 
	* @version V1.0   
	* @param formula
	* @return 
	* @return ArrayList<String>
	 */
	@Deprecated
	private static ArrayList<String> getFormulaSection(String formula){
		ArrayList<String> formulaList = new ArrayList<String>();
		String[] array = formula.split(":");
		for(int i = 1; i < array.length; i++){
			int firstIndex = 0;
			int secondIndex = 0;
			
			//获取冒号前最近的一个非英文或数字的字符，以此判断冒号前的单元格
			//将字符串分割为一个个字符
			char first[] = array[i-1].toCharArray();
			for(int j = (first.length-1); j >= 0; j--){
				//判断字符是否为字母或数字
				if(!Character.isLetterOrDigit(first[j])){
					firstIndex = j;
					break;
				}
			}
			//获取冒号后最近的一个非英文或数字的字符，以此判断冒号后的单元格
			char second[] = array[i].toCharArray();
			for(int j = 0; j < second.length; j++){
				if(!Character.isLetterOrDigit(second[j])){
					secondIndex = j;
					break;
				}
			}
			
			String firstLeter = array[i-1].substring(firstIndex + 1, array[i-1].length());
			String secondLeter = array[i].substring(0, secondIndex);
			formulaList.add(firstLeter + ":" + secondLeter);
		}
		
		return removeRepeat(formulaList);
	}
	/**
	 * 
	* @功能描述: 获取一个公式中，所有的区间表达式
	* * 如=INDEX(C34:BL34,MATCH(0,C29:BL29,1))-INDEX(C29:BL29,MATCH(0,C29:BL29,1))/INDEX(C28:BL28,MATCH(0,C29:BL29,1)+1)-1+fzb1!$C$4/12
	* 取出[C34:BL34, C29:BL29, C29:BL29, C29:BL29, C28:BL28, C29:BL29]
	* @方法名称: getFormulaAllSection 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年6月20日 下午10:47:14 
	* @version V1.0   
	* @param formula
	* @return 
	* @return ArrayList<String>
	 */
	@Deprecated
	private static ArrayList<String> getFormulaAllSection(String formula){
		ArrayList<String> formulaList = new ArrayList<String>();
		char array[] = formula.toCharArray();
		boolean hasStartLetter = false;
		int startIndex = 0;
		int endIndex = 0;
		
		for(int i = 0; i < array.length; i++){
			if(Character.isLetterOrDigit(array[i]) || array[i] == ':'){
				if(!hasStartLetter){
					startIndex = i;
					hasStartLetter = true;
				}else if(i == (array.length - 1)){
					String coordinate = formula.substring(startIndex, i+1);
					formulaList.add(coordinate);
				}
				
			}else{
				if(hasStartLetter){
					endIndex = i;
					String coordinate = formula.substring(startIndex, endIndex);
					formulaList.add(coordinate);
					hasStartLetter = false;
					
				}
				String symbol = formula.substring(i, i+1);
				formulaList.add(symbol);
			}
		}
		
		
		return formulaList;
	}
	
	/**
	 * 
	* @功能描述: 获取一个公式中，所有的单元格坐标表达式
	* 如=A1+B1+c3+(AA44*BB55+SS66)/C78+PGB1!A1+$C$3+$C4+C$5
	* 取出[C34:BL34, C29:BL29, C29:BL29, C29:BL29, C28:BL28, C29:BL29]
	* @方法名称: getFormulaAllCoordinate 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年5月13日 下午2:21:30 
	* @version V1.0   
	* @param formula
	* @return 
	* @return ArrayList<String>
	 */
	@Deprecated
	private static ArrayList<String> getFormulaAllCoordinate(String formula){
		ArrayList<String> formulaList = new ArrayList<String>();
		char array[] = formula.toCharArray();
		boolean hasStartLetter = false;
		int startIndex = 0;
		int endIndex = 0;
		
		for(int i = 0; i < array.length; i++){
			if(Character.isLetterOrDigit(array[i]) || array[i] == '!' || array[i] == '$' || array[i] == ':'){
				if(!hasStartLetter){
					startIndex = i;
					hasStartLetter = true;
				}else if(i == (array.length - 1)){
					String coordinate = formula.substring(startIndex, i+1);
					formulaList.add(coordinate);
				}
				
			}else{
				if(hasStartLetter){
					endIndex = i;
					String coordinate = formula.substring(startIndex, endIndex);
					formulaList.add(coordinate);
					hasStartLetter = false;
					
				}
				String symbol = formula.substring(i, i+1);
				formulaList.add(symbol);
			}
		}
		
		
		return formulaList;
	}
	
}
