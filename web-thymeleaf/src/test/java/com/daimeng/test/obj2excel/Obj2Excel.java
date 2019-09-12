package com.daimeng.test.obj2excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.daimeng.util.ExcelUtils;

public class Obj2Excel {

	public static void main(String[] args) {
		String src = "D:/java_test/obj2excel/excel_mapping.xls";
		try {
			FileInputStream is = new FileInputStream(src);
            HSSFWorkbook workbook = new HSSFWorkbook(is);
            HSSFSheet sheet = ExcelUtils.getSheet(workbook, 0, "");
            setSheetData(sheet, com.daimeng.test.obj2excel.Company.class, 0, 0);
            
            FileOutputStream os = new FileOutputStream(src);
            workbook.write(os);
            is.close();
            os.close();
		}catch (Exception e){
			
		}
		System.out.println("success");
	}
	
	public static int setSheetData(HSSFSheet sheet,Class<?> clz,int rowNum,int level){
		//Class<?> clz = com.daimeng.test.obj2excel.Company.class;
		//int rowNum = 0;
		Field[] fields = clz.getDeclaredFields(); 
		for (Field field : fields) {
			String fieldName = field.getName();
			//System.out.println(fieldName);
			//System.out.println(field.getType());
			if(field.getType().getName().indexOf("java.lang.") > -1 || field.getType().getName().indexOf(".") == -1){
				int fieldType = 1;
				if(field.getType().getName().indexOf("java.lang.String") > -1){
					fieldType = 0;
				}
				setCell(sheet, rowNum ++, fieldName,level,fieldType,0);
			}else if(field.getType().getName().indexOf("com.daimeng.") > -1){
				setCell(sheet, rowNum ++, fieldName,level,2,1);
				rowNum = setSheetData(sheet, field.getType(), rowNum, level+1);
			}else if(field.getType().getName().indexOf("java.util.List") > -1 || field.getType().getName().indexOf("java.util.ArrayList") > -1){
				setCell(sheet, rowNum++, fieldName,level,2,2);
				
				Type type = field.getGenericType();
				if(type instanceof ParameterizedType){
					ParameterizedType pt = (ParameterizedType) type;
					//System.out.println(pt.getActualTypeArguments()[0]);
					rowNum = setSheetData(sheet, (Class<?>) pt.getActualTypeArguments()[0], rowNum, level+1);
				}
			}
		}
        return rowNum;
	}
	
	public static void setCell(HSSFSheet sheet,int rowNum,String fieldName,int level,int fieldType,int length){
		String prex = "";
		for(int i = 0; i < level; i ++){
			prex += "..";
		}
		sheet.createRow(rowNum);
		HSSFRow row = sheet.getRow(rowNum);
		row.createCell(0);
		HSSFCell cell = row.getCell(0);
		cell.setCellValue(prex+fieldName);
		
		row.createCell(1);
		HSSFCell cell1 = row.getCell(1);
		if(fieldType == 0){
			cell1.setCellValue("C");
		}else if(fieldType == 1){
			cell1.setCellValue("N");
		}else{
			cell1.setCellValue("GROUP");
		}
		
		
		row.createCell(2);
		HSSFCell cell2 = row.getCell(2);
		if(length == 0){
			cell2.setCellValue("");
		}else if(length == 1){
			cell2.setCellValue("*1");
		}else{
			cell2.setCellValue("*n");
		}
		
	}
	
	
	
	
}
