package com.enjoyor.common.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

//import com.enjoyor.common.pojo.ExcelExportTest;

/**
 * 
 * @date : 2012-12-4 上午10:47:45
 *excel 导出
 **/
public class ExcelExportTools<T> {
	
	/**
	 * 导出excel
	 * 
	 * @param : @param result  需要导出的数据
	 * @param : @return 文件路径
	 * @return :
	 */
	public boolean exportExcel(List<T> list, String filePath) throws Exception{
		OutputStream out = new FileOutputStream(filePath);
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		HSSFCellStyle colStyle = getColumsStyle(workbook);
		int maxY = 0;
		this.createCellsInSheet(list, sheet, maxY, colStyle, "yyyy-MM-dd", new String[]{});
		workbook.write(out);
		if(out != null){
			out.close();
		}
		return false;
	}
	
	/**
	 * 导出excel
	 * 
	 * 
	 * @param : @param result  导出的结果集
	 * @param : @param headers  excel的表头
	 * @param : @param filePath 文件存放的绝对路径
	 * @param : @param sheetTitle  表格标题
	 * @param : @param dateFormat  日期格式
	 * @param : @param unExportedFields  不需要导出的列
	 * @param : @return
	 * @return :
	 * @throws IOException 
	 */
	public boolean exportExcel(List<T> list, CustomHSSFCell[] headers, String filePath, String sheetTitle, String dateFormat, String[] unExportedFields) throws Exception{
		OutputStream out = new FileOutputStream(filePath);
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(sheetTitle);
		HSSFCellStyle styleHeader = this.getFirstHeaderStyle(workbook);
		HSSFCellStyle colStyle = this.getColumsStyle(workbook);
		int maxY = 0;
		HSSFRow row = null;
		for(int i = 0; i < headers.length; i++){
			int rowBegin = headers[i].getRowBegin();
			row = sheet.getRow(rowBegin);
			if(row == null){
				row = sheet.createRow(rowBegin);
				row.setHeight((short)600);
			}	
			HSSFCell cell = row.createCell(headers[i].getColBegin());
			sheet.autoSizeColumn(headers[i].getColBegin());
			sheet.setColumnWidth(headers[i].getColBegin(), (headers[i].getColWidth()==0 ? 20 : headers[i].getColWidth())*256);
			cell.setCellStyle(styleHeader);
			HSSFRichTextString text = new HSSFRichTextString((String)headers[i].getCellValue());
			cell.setCellValue(text);
			if(headers[i].isMerged()){
				sheet.addMergedRegion(new CellRangeAddress( headers[i].getRowBegin(), headers[i].getRowEnd(), headers[i].getColBegin(), headers[i].getColEnd()));
			}
			if(maxY <= headers[i].getRowEnd()){
				maxY = headers[i].getRowEnd();
			}
		}
		this.createCellsInSheet(list, sheet, maxY+1, colStyle, dateFormat, unExportedFields);
		workbook.write(out);
		if(out != null){
			out.close();
		}
		return true;
	}
	
	/**
	 * Excel导出
	 * 
	 * 
	 * @param : @param list  excel导入数据
	 * @param : @param headers  表格头
	 * @param : @param filePath  文件存放绝对路径
	 * @param : @param sheetTitle  表格标题
	 * @param : @return
	 * @param : @throws Exception
	 * @return :
	 */
	public boolean exportExcel(List<T> list, CustomHSSFCell[] headers, String filePath, String sheetTitle) throws Exception{
		return exportExcel(list, headers, filePath, sheetTitle, "yyyy-MM-dd");
	}
	/**
	 * Excel导出
	 * 
	 * 
	 * @param : @param list excel导入数据
	 * @param : @param headers 表格头
	 * @param : @param filePath  文件存放绝对路径
	 * @param : @param sheetTitle 表格标题
	 * @param : @param dateFormat  日期格式
	 * @param : @return
	 * @return :
	 */
	public boolean exportExcel(List<T> list, CustomHSSFCell[] headers, String filePath, String sheetTitle, String dateFormat) throws Exception{
		return this.exportExcel(list, headers, filePath, sheetTitle, dateFormat, null);
	}
	
	/**
	 * 将list里面的数据导入到表格中
	 * 
	 * 
	 * @param : @param list
	 * @param : @param sheet
	 * @param : @param rowIndex
	 * @param : @param colStyle
	 * @param : @param dateFormat
	 * @param : @param unExportedFields
	 * @param : @throws Exception
	 * @return :
	 */
	public void createCellsInSheet(List<T> list, HSSFSheet sheet, int rowIndex, HSSFCellStyle colStyle, String dateFormat, String[] unExportedFields) throws Exception{
		Iterator<T> iterator = list.iterator();
		HSSFRow row = null;
		Field[] fields = null;
		Field field = null;
		HSSFCell cell = null;
		while(iterator.hasNext()){
			row = sheet.createRow(rowIndex);
			row.setHeight((short)500);
			T t = iterator.next();
			fields = t.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				cell = row.createCell(i);
				cell.setCellStyle(colStyle);
				field = fields[i];
				String fieldName = field.getName();
				if(unExportedFields != null && containStr(fieldName, unExportedFields)){
					break;
				}
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				Class tCls = t.getClass();
				Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
				Object value = getMethod.invoke(t, new Object[]{});
				if(value != null){
					if (value instanceof Integer) {
						int intValue = (Integer) value;
						cell.setCellValue(intValue);
					} else if (value instanceof Float) {
						float fValue = (Float) value;
						cell.setCellValue(fValue);
					} else if (value instanceof Double) {
						double dValue = (Double) value;
						//textValue = new HSSFRichTextString(String.valueOf(dValue));
						//cell.setCellValue(textValue);
						cell.setCellValue(dValue);
					} else if (value instanceof Long) {
						long longValue = (Long) value;
						cell.setCellValue(longValue);
					} else if (value instanceof Date) {
						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
						cell.setCellValue(sdf.format(date));
					} else{
						cell.setCellValue(value.toString());
					}
				} else {
					cell.setCellValue("");
				}
				
				
			}
			rowIndex++;
		}
	}
	
	public boolean exportExcelOfFieldName(List<T> list, CustomHSSFCell[] topheaders,CustomHSSFCell[] midheaders,CustomHSSFCell[] botheaders, String filePath, String sheetTitle, String dateFormat)throws Exception{
		OutputStream out = new FileOutputStream(filePath);
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(sheetTitle);
		HSSFCellStyle styleHeader = this.getFirstHeaderStyle(workbook);
		HSSFCellStyle colStyle = this.getColumsStyle(workbook);
		int maxY = 0;
		HSSFRow row = null;
		//表头
		if(topheaders != null){
			for(int i = 0; i < topheaders.length; i++){
				int rowBegin = topheaders[i].getRowBegin();
				row = sheet.getRow(rowBegin);
				if(row == null){
					row = sheet.createRow(rowBegin);
					row.setHeight((short)600);
				}	
				HSSFCell cell = row.createCell(topheaders[i].getColBegin());
				sheet.autoSizeColumn(topheaders[i].getColBegin());
				sheet.setColumnWidth(topheaders[i].getColBegin(), (topheaders[i].getColWidth()==0 ? 20 : topheaders[i].getColWidth())*256);
				cell.setCellStyle(styleHeader);
				HSSFRichTextString text = new HSSFRichTextString((String)topheaders[i].getCellValue());
				cell.setCellValue(text);
				if(topheaders[i].isMerged()){
					sheet.addMergedRegion(new CellRangeAddress( topheaders[i].getRowBegin(), topheaders[i].getRowEnd(), topheaders[i].getColBegin(), topheaders[i].getColEnd()));
				}
				if(maxY <= topheaders[i].getRowEnd()){
					maxY = topheaders[i].getRowEnd();
				}
			}
		}
		
		//表内容填充
		for(int i = 0; i < midheaders.length; i++){
			int rowBegin = midheaders[i].getRowBegin();
			row = sheet.getRow(rowBegin);
			if(row == null){
				row = sheet.createRow(rowBegin);
				row.setHeight((short)600);
			}	
			HSSFCell cell = row.createCell(midheaders[i].getColBegin());
			sheet.autoSizeColumn(midheaders[i].getColBegin());
			sheet.setColumnWidth(midheaders[i].getColBegin(), (midheaders[i].getColWidth()==0 ? 20 : midheaders[i].getColWidth())*256);
			cell.setCellStyle(styleHeader);
			HSSFRichTextString text = new HSSFRichTextString((String)midheaders[i].getCellValue());
			cell.setCellValue(text);
			if(midheaders[i].isMerged()){
				sheet.addMergedRegion(new CellRangeAddress( midheaders[i].getRowBegin(), midheaders[i].getRowEnd(), midheaders[i].getColBegin(), midheaders[i].getColEnd()));
			}
			if(maxY <= midheaders[i].getRowEnd()){
				maxY = midheaders[i].getRowEnd();
			}
		}
		this.createCellsInSheet(list, sheet, maxY+2, colStyle, dateFormat, midheaders);
		
		//表尾
		if(botheaders != null){
					for(int i = 0; i < botheaders.length; i++){
						int rowBegin = botheaders[i].getRowBegin();
						row = sheet.getRow(rowBegin);
						if(row == null){
							row = sheet.createRow(rowBegin);
							row.setHeight((short)600);
						}	
						HSSFCell cell = row.createCell(botheaders[i].getColBegin());
						sheet.autoSizeColumn(botheaders[i].getColBegin());
						sheet.setColumnWidth(botheaders[i].getColBegin(), (botheaders[i].getColWidth()==0 ? 20 : botheaders[i].getColWidth())*256);
						cell.setCellStyle(styleHeader);
						HSSFRichTextString text = new HSSFRichTextString((String)botheaders[i].getCellValue());
						cell.setCellValue(text);
						if(botheaders[i].isMerged()){
							sheet.addMergedRegion(new CellRangeAddress( botheaders[i].getRowBegin(), botheaders[i].getRowEnd(), botheaders[i].getColBegin(), botheaders[i].getColEnd()));
						}
						if(maxY <= botheaders[i].getRowEnd()){
							maxY = botheaders[i].getRowEnd();
						}
					}
				}
		
		workbook.write(out);
		if(out != null){
			out.close();
		}
		return true;
	}
	
	private void createCellsInSheet(List<T> list, HSSFSheet sheet, int rowIndex, HSSFCellStyle colStyle, String dateFormat, CustomHSSFCell[] headers)throws Exception {
		Iterator<T> iterator = list.iterator();
		HSSFRow row = null;
		Field[] fields = null;
		HSSFCell cell = null;
		String fieldName = null;
		while(iterator.hasNext()){
			row = sheet.createRow(rowIndex);
			row.setHeight((short)500);
			T t = iterator.next();
			fields = t.getClass().getDeclaredFields();
			for(int i = 0; i < headers.length; i++){
				cell = row.createCell(i);
				cell.setCellStyle(colStyle);
				fieldName = headers[i].getFieldName();
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				Class tCls = t.getClass();
				Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
				Object value = getMethod.invoke(t, new Object[]{});
				if(value != null){
					if (value instanceof Integer) {
						int intValue = (Integer) value;
						
						cell.setCellValue(intValue);
					} else if (value instanceof Float) {
						float fValue = (Float) value;
						cell.setCellValue(fValue);
					} else if (value instanceof Double) {
						double dValue = (Double) value;
						//textValue = new HSSFRichTextString(String.valueOf(dValue));
						//cell.setCellValue(textValue);
						cell.setCellValue(dValue);
					} else if (value instanceof Long) {
						long longValue = (Long) value;
						cell.setCellValue(longValue);
					} else if (value instanceof Date) {
						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
						cell.setCellValue(sdf.format(date));
					} else{
						cell.setCellValue(value.toString());
					}
				} else {
					cell.setCellValue("");
				}
				if(headers[i].getKeyValue() != null){
					cell.setCellValue((String)headers[i].getKeyValue().get(value.toString()));
				}
			}
			rowIndex++;
		}
	
	}

	public boolean containStr(String fieldName, String[] unExportedFields) {
		for(int i = 0; i <unExportedFields.length; i++){
			if(unExportedFields[i].compareTo(fieldName) == 0){
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取excel数据单元格的样式
	 * 
	 * 
	 * @param : @param workbook
	 * @param : @return
	 * @return :
	 */
	public HSSFCellStyle getColumsStyle(HSSFWorkbook workbook){
		HSSFCellStyle style = workbook.createCellStyle();
	    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	    HSSFFont font = workbook.createFont();
	    font.setColor(HSSFColor.BLACK.index);
	    font.setFontHeightInPoints((short) 12);
	    //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    style.setFont(font);
	    return style;
	}
	
	/**
	 * 获取excel头部样式
	 * 
	 * 
	 * @param : @param workbook
	 * @param : @return
	 * @return :
	 */
	public HSSFCellStyle getFirstHeaderStyle(HSSFWorkbook workbook){
		HSSFCellStyle style = workbook.createCellStyle();
		//style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    //style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	    //style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    //style.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    //style.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	    HSSFFont font = workbook.createFont();
	    font.setColor(HSSFColor.BLACK.index);
	    font.setFontHeightInPoints((short) 12);
	    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    style.setFont(font);
	    return style;
	}
	
	
	/*public static void main(String[] args){
		CustomHSSFCell[] headers = new CustomHSSFCell[5];
		headers[0] = new CustomHSSFCell("单元格A", 0, 0, 30);
		headers[1] = new CustomHSSFCell("单元格B", 1, 0, 20);
		headers[2] = new CustomHSSFCell("单元格C", 2, 0, 20);
		headers[3] = new CustomHSSFCell("单元格D", 0, 1, 1, 1, 50);
		headers[4] = new CustomHSSFCell("单元格E", 2, 1, 20);
		List list = new ArrayList();
		ExcelExportTest eet = new ExcelExportTest();
		eet.setField1("eee");
		eet.setField2("rrrr");
		eet.setField3("ttttt");
		eet.setField4("yyyy");
		list.add(eet);
		list.add(eet);
		list.add(eet);
		list.add(eet);
		list.add(eet);
		list.add(eet);
		list.add(eet);
		list.add(eet);
		list.add(eet);
		list.add(eet);
		try {
			POIExcelExportTools et = new POIExcelExportTools();
			et.exportExcel(list, headers, "D:\\test9.xls", "测试", "yyyy-MM-dd", new String[]{"field4"});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

}
