package com.daimeng.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
	
	public static void main(String[] args) {
		String src = "D:/java_test/excel/Excel_Remove_Mod.xls";
		SimpleDateFormat sdf_datetime_format = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = sdf_datetime_format.format(Calendar.getInstance().getTime());
		System.out.println(date);
		String targ = "D:/java_test/excel/new_excel_" +date+ ".xls";
		delRowAndColumn(src, targ);
	}
	
	/**
	 * 
	* @方法名称: delRowAndColumn 
	* @路径 com.daimeng.util 
	* @功能描述: 执行删除excel的行和列
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月19日 下午3:27:41 
	* @version V1.0   
	* @param src
	* @param targ 
	* void
	 */
	public static void delRowAndColumn(String src, String targ){
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
            
            System.out.println("===delete row start!===");
            sheet = delRow(sheet, 60, 145);
            sheet = delRow(sheet, 20, 45);
            System.out.println("===delete row end!===");
            
            System.out.println("===hide column start!===");
            sheet = hideColumn(sheet, 35, 130, true);
            sheet = hideColumn(sheet, 30, 34, false);
            sheet = hideColumn(sheet, 12, 26, true);
            System.out.println("===hide column end!===");
            
            //执行计算公式
            HSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
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

	/**
	 * 
	* @方法名称: delRow 
	* @路径 com.daimeng.util 
	* @功能描述: 删除行,然后将下面未删除的行移动上来
	* @功能描述: 但不会更改公式的内容
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月19日 下午3:28:22 
	* @version V1.0   
	* @param sheet
	* @param start
	* @param end
	* @return 
	* HSSFSheet
	 */
	public static HSSFSheet delRow(HSSFSheet sheet,int start,int end){
		int rowNum = sheet.getLastRowNum();
		int rowCount = rowNum+1;
		System.out.println("rowCount="+rowCount);
		if(end > rowCount){
			end = rowCount;
		}
		
		sheet = removeRowData(sheet, start, end);
        //sheet.shiftRows(31, rowCount, -1);
        //删除1-5行,则是把6以下的行数往上移动5格,这样可以达到删除空行的效果  1-5-1
        if(end != rowCount){
        	sheet.shiftRows(end, rowCount, start-end-1);
        }
		return sheet;
	}
	/**
	 * 
	* @功能描述: 删除row的数据,但会保留空的row
	* @方法名称: removeRowData 
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
	public static HSSFSheet removeRowData(HSSFSheet sheet,int start,int end){
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
	* @方法名称: delColumn 
	* @路径 com.daimeng.util 
	* @功能描述: 删除列,然后将后面未删除的列复制到当前删除的列上,然后删除后面所有的列.
	* @功能描述: 但不会更改公式的内容
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月19日 下午3:28:49 
	* @version V1.0   
	* @param sheet
	* @param start
	* @param end
	* @return 
	* HSSFSheet
	 */
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
	/**
	 * 
	* @方法名称: cloneCellFromEnum 
	* @路径 com.daimeng.util 
	* @功能描述: 复制单元格内容
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月19日 下午3:29:25 
	* @version V1.0   
	* @param cNew
	* @param cOld 
	* void
	 */
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
	/**
	 * 
	* @方法名称: cloneCellFromType 
	* @路径 com.daimeng.util 
	* @功能描述: 复制单元格内容-旧版本
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月19日 下午3:29:45 
	* @version V1.0   
	* @param cNew
	* @param cOld 
	* void
	 */
	@Deprecated
	private static void cloneCellFromType(HSSFCell cNew, HSSFCell cOld) {
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
	* @方法名称: hideRow 
	* @路径 com.daimeng.util 
	* @功能描述: 纯粹隐藏行
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月19日 下午3:30:18 
	* @version V1.0   
	* @param sheet
	* @param start
	* @param end
	* @return 
	* HSSFSheet
	 */
	public static HSSFSheet hideRow(HSSFSheet sheet,int start,int end,boolean delData){
		int rowNum = sheet.getLastRowNum();
		int rowCount = rowNum+1;
		System.out.println("rowCount="+rowCount);
		if(end > rowCount){
			end = rowCount;
		}
		
		if(delData){
			sheet = removeCellDataForRow(sheet, start, end);
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
	* @功能描述: 一格一格删除cell,为的是不把row变为null
	* @方法名称: removeCellDataForRow 
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
	public static HSSFSheet removeCellDataForRow(HSSFSheet sheet,int start,int end){
		//删除行内容,但会保留空行
        for(int i = start-1; i < end; i++){
        	HSSFRow row = sheet.getRow(i);
        	if(row != null){
        		int columnCount = row.getPhysicalNumberOfCells();
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
	* @方法名称: hideColumn 
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
        int columnCount = row.getPhysicalNumberOfCells();
        System.out.println("columnCount="+columnCount);
        
        int rowNum = sheet.getLastRowNum();
		int rowCount = rowNum+1;
		System.out.println("rowCount="+rowCount);
		
        if(end > columnCount){
			end = columnCount;
		}
        if(delData){
        	sheet = removeCellDataForColumn(sheet, start, end);
        }
        for(int i = start-1; i < end; i++){
        	sheet.setColumnWidth(i, 0); 
        }
		return sheet;
	}
	/**
	 * 
	* @功能描述: 删除column的内容
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
	public static HSSFSheet removeCellDataForColumn(HSSFSheet sheet,int start,int end){
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
	* @方法名称: evaluate 
	* @路径 com.daimeng.excel 
	* @功能描述: 循环excel,执行所有的公式表格
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月19日 下午3:23:10 
	* @version V1.0   
	* @param wb 
	* void
	 */
	public static void evaluate(HSSFWorkbook wb){
        FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
        for(int sheetNum = 0; sheetNum < wb.getNumberOfSheets(); sheetNum++) {
        	HSSFSheet sheet = wb.getSheetAt(sheetNum);
            for(int rowid = 0; rowid <= sheet.getLastRowNum(); rowid++) {
            	HSSFRow row = sheet.getRow(rowid);
                for(int cid = 0; cid < row.getPhysicalNumberOfCells(); cid++) {
                	HSSFCell cell = row.getCell(cid);
                    if(cell.getCellTypeEnum() == CellType.FORMULA) {
                        evaluator.evaluateFormulaCell(cell);
                    }
                }
            }
        }
	}
}
