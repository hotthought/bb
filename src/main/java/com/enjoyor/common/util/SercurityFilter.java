package com.enjoyor.common.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class SercurityFilter implements Filter {
	public void destroy() {
	}
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain)
			throws IOException, ServletException {
		//httponly是微软对cookie做的扩展,该值指定 Cookie 是否可通过客户端脚本访问,   
		//解决用户的cookie可能被盗用的问题,减少跨站脚本攻击  
		HttpSession session = ((HttpServletRequest)request).getSession();
		String sessionid = "";
		if(session != null){
			sessionid = session.getId();
		}
		
		
		//((HttpServletResponse)response).setHeader( "SET-COOKIE", "Path=/ccrl;secure;HttpOnly"); 
		String path=((HttpServletRequest)request).getServletPath();
		filterChain.doFilter(request, response);
		/*
		if(path.compareTo("/update.jsp") == 0){
			filterChain.doFilter(request, response);
		} else {
			((HttpServletResponse)response).sendRedirect("update.jsp"); 
		}
		*/
	}
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
