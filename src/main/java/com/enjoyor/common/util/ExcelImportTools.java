package com.enjoyor.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


/**
 * @author : SGF
 * @date : 2012-12-4 下午11:22:20
 * 
 * excel导入
 *
 **/
public class ExcelImportTools<T> {
	
	/**
	 * excel导入
	 * 
	 * @author : SGF
	 * @param : @param file  导入的excel文件
	 * @param : @return
	 * @return :
	 * @throws Exception 
	 */
	public List<Object[]> importExcel(File file) throws Exception{
		return this.importExcel(file, 0);
	}
	
	/**
	 * excel 导入
	 * 
	 * @author : SGF
	 * @param : @param file 导入的excel文件
	 * @param : @param rowIndex  文件从哪一行开始导入
	 * @param : @return
	 * @return :
	 */
	public List<Object[]> importExcel(File file, int startRow) throws Exception{
		InputStream is = new FileInputStream(file);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		List<Object[]> list = new ArrayList<Object[]>();
		HSSFSheet sheet = null;
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			sheet = hssfWorkbook.getSheetAt(numSheet);
			if(sheet != null){
				break;
			}
		}
		HSSFRow row = null;
		List listCellTemp =null;
		Iterator iterator = null;
		HSSFCell cell = null;
		Object[] obj = null;
		if(startRow > sheet.getLastRowNum()){
			throw new Exception("导入开始行大于表格总行数！");
		}
		for (int rowNum = startRow; rowNum <= sheet.getLastRowNum(); rowNum++) {
			row = sheet.getRow(rowNum);
			if (row == null) {
                continue;
            }
			iterator = row.cellIterator();
			listCellTemp = new ArrayList();
			while(iterator.hasNext()){
				cell = (HSSFCell)iterator.next();
				//listCellTemp.add(cell.get)
				switch(cell.getCellType()){
					case HSSFCell.CELL_TYPE_BLANK : 
						//blank
						listCellTemp.add("");
						break;
					case HSSFCell.CELL_TYPE_NUMERIC : 
						if(HSSFDateUtil.isCellDateFormatted(cell)){
							listCellTemp.add(cell.getDateCellValue());
						} else {
							listCellTemp.add(cell.getNumericCellValue());
						}
						break;
					default :
						listCellTemp.add(cell.getStringCellValue());
				}
			}
			if(listCellTemp != null && listCellTemp.size() > 0){
				obj = new Object[listCellTemp.size()];
				for(int i = 0; i < listCellTemp.size(); i++){
					obj[i] = listCellTemp.get(i);
				}
				list.add(obj);
			}
		}
		return list;
	}
	
	/**
	 * excel 导入
	 * 
	 * @author : SGF
	 * @param : @param file  导入文件
	 * @param : @param clazz 导入的数值存放的对象
	 * @param : @return
	 * @return :
	 */
	public List<T> importExcel(File file, Class<T> clazz) throws Exception{
		return this.importExcel(file, clazz, 0);
	}
	
	/**
	 * excel 导入
	 * 
	 * @author : SGF
	 * @param : @param file  导入的excel文件
	 * @param : @param clazz 导入的数值存放的对象
	 * @param : @param rowIndex 文件从哪一行开始导入
	 * @param : @return
	 * @return :
	 */
	public List<T> importExcel(File file, Class<T> clazz, int startRow) throws Exception{
		return this.importExcel(file, clazz, startRow, null);
	}
	
	/**
	 * excel 导入
	 * 
	 * @author : SGF
	 * @param : @param file  导入的excel文件
	 * @param : @param clazz  导入的数值存放的对象
	 * @param : @param rowIndex  文件从哪一行开始导入
	 * @param : @param importFields  需要导入的对象属性
	 * @param : @return
	 * @return :
	 */
	public List<T> importExcel(File file, Class<T> clazz, int startRow, String[] importFields) throws Exception{
		InputStream is = new FileInputStream(file);
		return this.importExcel(is, clazz, startRow, importFields);
	}
	
	public List<T> importExcel(InputStream in, Class<T> clazz, int startRow, String[] importFields) throws Exception{
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(in);
		List<T> list = new ArrayList<T>();
		T obj = null;
		HSSFSheet sheet = null;
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			sheet = hssfWorkbook.getSheetAt(numSheet);
			if(sheet != null){
				break;
			}
		}
		HSSFRow row = null;
		HSSFCell cell = null;
		String fieldName = null;
		Field[] fields = null;
		Object fieldValue = null;
		if(startRow > sheet.getLastRowNum()){
			throw new Exception("导入开始行大于表格总行数！");
		}
		for (int rowNum = startRow; rowNum <= sheet.getLastRowNum(); rowNum++) {
			if(rowNum == 43){
				int dd = 22;
				dd ++;
			}
			obj = clazz.newInstance();
			row = sheet.getRow(rowNum);
			if (row == null) {
                continue;
            }
			fields = importFields == null ? clazz.getDeclaredFields() : getClassFieldByFieldName(clazz, importFields);
			for(int i = 0; i < row.getLastCellNum(); i++){
				if(i>=fields.length){
					break;
				}
				cell = (HSSFCell)row.getCell(i);
				fieldName = fields[i].getName();
				if(cell != null){
					switch(cell.getCellType()){
					case HSSFCell.CELL_TYPE_BLANK : 
						//blank
						fieldValue = null;
						break;
					case HSSFCell.CELL_TYPE_NUMERIC : 
						if(HSSFDateUtil.isCellDateFormatted(cell)){
							if(cell.getDateCellValue() != null){
								fieldValue = cell.getDateCellValue();
							}
						} else {
							if(Double.valueOf(cell.getNumericCellValue()).toString().compareTo("") != 0){
								if(fields[i].getType().getName().equals("java.lang.String")){
									fieldValue = new BigDecimal(cell.getNumericCellValue()).toString();
								} else if(fields[i].getType().getName().equals("int")){
									fieldValue = (int)cell.getNumericCellValue();
								} else if(fields[i].getType().getName().equals("long")){
									fieldValue = (long)cell.getNumericCellValue();
								}
							}
						}
						break;
					default :
						fieldValue = cell.getStringCellValue();
					}
				} else {
					fieldValue = null;
				}
				String setName = "set"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1);
				Method setMethod = clazz.getMethod(setName, new Class[]{fields[i].getType()});
				setMethod.invoke(obj,new Object[]{fieldValue});
			}
			list.add(obj);
		}
		return list;
	}
	
	public Field[] getClassFieldByFieldName(Class clazz, String[] importFields) throws SecurityException, NoSuchFieldException {
		Field[] fields = new Field[importFields.length];
		for(int i = 0; i < importFields.length; i++){
			fields[i] = clazz.getDeclaredField(importFields[i]);
		}
		return fields;
	}

	public String[] getFieldNames(Class<T> clazz) {
		Field[] fields=clazz.getDeclaredFields();   
		if(fields != null && fields.length > 0){
			String[] fieldNames = new String[fields.length];
			for(int i = 0; i < fields.length; i++){
				fieldNames[i] = fields[i].getName();
			}
			return fieldNames;
		}
		return null;
	}

	/*
	 * public static void main(String[] args){
		File file = new File("D:\\test9.xls");
		POIExcelImportTools poiit = new POIExcelImportTools();
		try {
			List<Object[]> list = poiit.importExcel(file, 1);
			List<ExcelExportTest> listPOJO = poiit.importExcel(file, ExcelExportTest.class, 13, new String[]{"field1", "field2", "field4"});
			if(listPOJO != null){
				
			}
			Object[] obj= null;
			if(list != null){
				for(int i = 0 ;i < list.size(); i++){
					obj = list.get(i);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
	

}
