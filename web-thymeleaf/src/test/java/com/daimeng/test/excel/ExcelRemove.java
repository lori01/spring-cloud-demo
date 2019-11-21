package com.daimeng.test.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.CellType;

public class ExcelRemove {

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
		HSSFRow firstrow = sheet.getRow(0);
        int columnCount = firstrow.getPhysicalNumberOfCells();
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
	
	private static void cloneCellFromEnum(HSSFCell cNew, HSSFCell cOld) {
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
	
	/*private static void cloneCellFromType(HSSFCell cNew, HSSFCell cOld) {
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
	}*/
	
}
