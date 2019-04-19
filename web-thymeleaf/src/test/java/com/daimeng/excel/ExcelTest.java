package com.daimeng.excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;

public class ExcelTest {

	public static void main(String[] args) {
		String src = "D:/java_test/excel/Excel_Remove_Mod.xls";
		String targ = "D:/java_test/excel/new_excel_" +System.currentTimeMillis()+ ".xls";

		delRowAndColumn(src, targ);
	}
	
	public static void delRowAndColumn(String src, String targ){
		try {
			FileInputStream is = new FileInputStream(src);
            HSSFWorkbook workbook = new HSSFWorkbook(is);
            HSSFSheet sheet = workbook.getSheetAt(0);
            
            System.out.println("===delete row start!===");
            sheet = ExcelRemove.delRow(sheet, 10, 15);
            System.out.println("===delete row end!===");
            
            System.out.println("===delete column start!===");
            sheet = ExcelRemove.delColumn(sheet, 12, 13);
            System.out.println("===delete column end!===");
            
           /* System.out.println("===hide row start!===");
            sheet = ExcelHide.hideRow(sheet, 3, 78);
            System.out.println("===hide row end!===");*/
            
            /*System.out.println("===hide column start!===");
            sheet = ExcelHide.hideColumn(sheet, 3, 10);
            System.out.println("===hide column end!===");*/
            
            //执行计算公式
            HSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
            
            
            FileOutputStream os = new FileOutputStream(targ);
            workbook.write(os);
            is.close();
            os.close();
            System.out.println("===export excel success!===");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
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
