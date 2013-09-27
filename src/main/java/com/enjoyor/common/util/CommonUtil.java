package com.enjoyor.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.enjoyor.pojo.Page;



public class CommonUtil {
	
	public static Page getPage(HttpServletRequest request){
		Page page = new Page();
		String currentPage = request.getParameter("pageIndex") == null ? "" : request.getParameter("pageIndex");
		String pageSize = request.getParameter("pageSize") == null ? "" : request.getParameter("pageSize");
		if(currentPage==null || currentPage.compareTo("") == 0){
			page.setCurrentPage(1);
		} else {
			page.setCurrentPage(Integer.valueOf(currentPage));
		}
		if(pageSize.compareTo("") == 0){
			page.setPageSize(10);
		} else {
			page.setPageSize(Integer.valueOf(pageSize));
		}
		return page;
	}
	

	/**
	 * 获取访问者IP
	 */
	public static String getRemortIP(HttpServletRequest request) {  
	    if (request.getHeader("x-forwarded-for") == null) {  
	        return request.getRemoteAddr();  
	    }  
	    return request.getHeader("x-forwarded-for");  
	}  
	
	/**
	 * 获取访问者Name
	 */
	public static String getUserName(HttpServletRequest request) {  
		HttpSession session = request.getSession();
		if(session != null){
			return session.getAttribute("CURRENT_USER_NAME").toString();
		}
		return "";
	}
	
	public static String getProperties(String properties,String key) {
		String value=null;
		InputStream inputStream = CommonUtil.class.getClassLoader().getResourceAsStream(properties);
		Properties p = new Properties();
		try {
			p.load(inputStream);
			value=p.getProperty(key);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return value;
	}

}
