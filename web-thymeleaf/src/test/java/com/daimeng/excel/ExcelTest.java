package com.daimeng.excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelTest {

	public static void main(String[] args) {
		String src = "D:/java_test/excel/江西建行项目成本明细_20194.xls";
		String targ = "D:/java_test/excel/new_excel_" +System.currentTimeMillis()+ ".xls";

		delRowAndColumn(src, targ);
		System.out.println("success");
	}
	
	public static void delRowAndColumn(String src, String targ){
		try {
			FileInputStream is = new FileInputStream(src);
            HSSFWorkbook workbook = new HSSFWorkbook(is);
            HSSFSheet sheet = workbook.getSheetAt(0);
            
            //sheet = delRow(sheet, 3, 5);
            //sheet = delColumn(sheet, 3, 5);
            
            sheet = hideRow(sheet, 3, 78);
            sheet = hideColumn(sheet, 3, 10);
            
            FileOutputStream os = new FileOutputStream(targ);
            workbook.write(os);
            is.close();
            os.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static HSSFSheet delRow(HSSFSheet sheet,int start,int end){
		int rowNum = sheet.getLastRowNum();
		int rowCount = rowNum+1;
		System.out.println("rowCount="+rowCount);
		if(end > rowCount){
			end = rowCount;
		}
		//删除行内容,但会保留空行
        for(int i = start-1; i < end; i++){
        	HSSFRow row = sheet.getRow(i);
        	if(row != null){
        		sheet.removeRow(row);
        	}
        }
        //sheet.shiftRows(31, rowCount, -1);
        //删除1-5行,则是把6以下的行数往上移动5格,这样可以达到删除空行的效果  1-5-1
        if(end != rowCount){
        	sheet.shiftRows(end, rowCount, start-end-1);
        }
		return sheet;
	}
	public static HSSFSheet delColumn(HSSFSheet sheet,int start,int end){
		HSSFRow row = sheet.getRow(0);
        int columnCount = row.getPhysicalNumberOfCells();
        System.out.println("columnCount="+columnCount);
        
        int rowNum = sheet.getLastRowNum();
		int rowCount = rowNum+1;
		System.out.println("new rowCount="+rowCount);
		
        if(end > columnCount){
			end = columnCount;
		}
        
        for(int i = start-1; i < end; i++){
    		
    		for(int j = 0; j < rowCount; j++){
    			HSSFRow crow = sheet.getRow(j);
    			if(crow != null){
    				HSSFCell cell = crow.getCell(i);
    				if(cell != null){
    					crow.removeCell(cell);
    				}
    			}
            }
        }
        int moveCount = -1;
        for(int i = end; i < columnCount; i ++){
        	//for(int j = 0; j < rowCount; j++){
    			HSSFRow crow = sheet.getRow(0);
    			if(crow != null){
    				HSSFCell cell = crow.getCell(i);
    				if(cell != null){
    					row.moveCell(cell, (short) (start+moveCount));
    					
    				}
    			}
            //}
        	moveCount++;
        }
        
        /*HSSFCell cell2 = row.getCell(start);
        if(cell2 != null){
        	row.moveCell(cell2, (short) (start-1));
        }*/
        
        if(end != columnCount){
        	
        }
		return sheet;
	}
	public static HSSFSheet hideRow(HSSFSheet sheet,int start,int end){
		int rowNum = sheet.getLastRowNum();
		int rowCount = rowNum+1;
		System.out.println("rowCount="+rowCount);
		if(end > rowCount){
			end = rowCount;
		}
		//行高为0则隐藏
		for(int i = start-1; i < end; i++){
			HSSFRow row = sheet.getRow(i);
			row.setZeroHeight(true);
		}
		return sheet;
	}
	public static HSSFSheet hideColumn(HSSFSheet sheet,int start,int end){
		HSSFRow row = sheet.getRow(0);
        int columnCount = row.getPhysicalNumberOfCells();
        System.out.println("columnCount="+columnCount);
        if(end > columnCount){
			end = columnCount;
		}
        for(int i = start-1; i < end; i++){
        	sheet.setColumnWidth(i, 0); 
        }
		return sheet;
	}

}
