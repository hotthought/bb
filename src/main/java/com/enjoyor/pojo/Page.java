package com.enjoyor.pojo;

import java.util.List;

import com.enjoyor.common.util.ResourcesProperties;

public class Page<T> {

	private int pageSize;
	private int totalPage; // 总页数
	private int totalCount; // 总记录数
	private int currentPage; // 当前页
	private int currentCount; // 当前记录起始索引
	
	private String sortExp; 		//排序列
	
	private String sortDir;			//排序方向
	List<T> result;
	
	public String getSortExp() {
		return sortExp;
	}

	public void setSortExp(String sortExp) {
		this.sortExp = sortExp;
	}

	public String getSortDir() {
		return sortDir;
	}

	public void setSortDir(String sortDir) {
		this.sortDir = sortDir;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public int getTotalPage() {
		if(pageSize == 0){
			return 0;
		}
		if (totalCount % pageSize == 0)
			totalPage = totalCount / pageSize;
		else
			totalPage = totalCount / pageSize + 1;
		
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getCurrentPage() {
		if (currentPage <= 0)
			currentPage = 1;
		if (currentPage > getTotalPage())
			currentPage = getTotalPage();
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	
	public int getCurrentResult() {
		currentCount = (getCurrentPage() - 1) * getPageSize();
		if (currentCount < 0)
			currentCount = 0;
		return currentCount;
	}

	public void setCurrentResult(int currentCount) {
		this.currentCount = currentCount;
	}

	public int getCurrentCount() {
		return currentCount;
	}

	public void setCurrentCount(int currentCount) {
		this.currentCount = currentCount;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public void setDefaultPageSize(){
		String defaultPageSize = ResourcesProperties.getInstance().getResourcesPropreties().get("page.pageSize.default");
		this.setPageSize(Integer.valueOf(defaultPageSize));
	}
	

	
}
