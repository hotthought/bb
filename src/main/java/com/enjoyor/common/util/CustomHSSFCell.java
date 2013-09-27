package com.enjoyor.common.util;

import java.util.Map;

/**
 * @author : SGF
 * @date : 2012-12-4 下午2:36:37
 * 定制的excel的单元格
 **/
public class CustomHSSFCell {
	
	private Object cellValue;		//
	
	private int colBegin;
	
	private int rowBegin;
	
	private int colEnd;
	
	private int rowEnd;
	
	private boolean isMerged;
	
	private int colWidth;
	
	private String fieldName;
	
	private Map keyValue;

	public CustomHSSFCell(Object cellValue, int colBegin, int rowBegin, int colWidth){
		this.cellValue = cellValue;
		this.colBegin = colBegin;
		this.rowBegin = rowBegin;
		this.isMerged = false;
		this.colWidth = colWidth;
	}
	
	public CustomHSSFCell(Object cellValue, int colBegin, int rowBegin, int colEnd, int rowEnd, int colWidth){
		this.cellValue = cellValue;
		this.colBegin = colBegin;
		this.rowBegin = rowBegin;
		this.colEnd = colEnd;
		this.rowEnd = rowEnd;
		this.isMerged = true;
		this.colWidth = colWidth;
	}
	
	public CustomHSSFCell(Object cellValue, int colBegin, int rowBegin, int colWidth, String fieldName){
		this.cellValue = cellValue;
		this.colBegin = colBegin;
		this.rowBegin = rowBegin;
		this.isMerged = false;
		this.colWidth = colWidth;
		this.fieldName = fieldName;
	}
	public CustomHSSFCell(Object cellValue, int colBegin, int rowBegin, int colWidth, String fieldName, Map keyValue){
		this.cellValue = cellValue;
		this.colBegin = colBegin;
		this.rowBegin = rowBegin;
		this.isMerged = false;
		this.colWidth = colWidth;
		this.fieldName = fieldName;
		this.keyValue = keyValue;
	}

	public Map getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(Map keyValue) {
		this.keyValue = keyValue;
	}
	
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public int getColWidth() {
		return colWidth;
	}

	public void setColWidth(int colWidth) {
		this.colWidth = colWidth;
	}
	
	public int getColBegin() {
		return colBegin;
	}

	public void setColBegin(int colBegin) {
		this.colBegin = colBegin;
	}

	public int getRowBegin() {
		return rowBegin;
	}

	public void setRowBegin(int rowBegin) {
		this.rowBegin = rowBegin;
	}

	public int getColEnd() {
		return colEnd;
	}

	public void setColEnd(int colEnd) {
		this.colEnd = colEnd;
	}

	public int getRowEnd() {
		return rowEnd;
	}

	public void setRowEnd(int rowEnd) {
		this.rowEnd = rowEnd;
	}

	public boolean isMerged() {
		return isMerged;
	}

	public void setMerged(boolean isMerged) {
		this.isMerged = isMerged;
	}

	public Object getCellValue() {
		return cellValue;
	}

	public void setCellValue(Object cellValue) {
		this.cellValue = cellValue;
	}
}
