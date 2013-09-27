package com.enjoyor.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.enjoyor.pojo.SysUser;
import com.enjoyor.common.util.CustomSessionContext;



public class SercurityFilter implements Filter {
	private Set restrictedResources;
	public void destroy() {
	}
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain)
			throws IOException, ServletException {
		SysUser sysUser =(SysUser)((HttpServletRequest)request).getSession().getAttribute("CURRENTUSER");
		String path=((HttpServletRequest)request).getServletPath();
		//String sessionId =  ((HttpServletRequest)request).getSession().getId();
		if(sysUser == null){
			String sessionId = ((HttpServletRequest)request).getParameter("jsessionid");
			HttpSession session = CustomSessionContext.getInstance().getSession(sessionId);
			if(session != null){
				sysUser = (SysUser)session.getAttribute("CURRENTUSER");
			}
		}
		if(contains(path)){
			filterChain.doFilter(request, response);
		} else {
			if(sysUser==null){
				if (((HttpServletRequest)request).getHeader("x-requested-with") != null && ((HttpServletRequest)request).getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {   
					//((HttpServletResponse)response).setHeader("sessionstatus","timeout"); 
					//((HttpServletResponse)response).setStatus(HttpServletResponse.SC_FORBIDDEN); 
					PrintWriter printWriter = response.getWriter(); 
					printWriter.print("{sessionState:0}"); 
					printWriter.flush(); 
					printWriter.close(); 
				} else {
					((HttpServletResponse)response).sendRedirect(((HttpServletRequest)request).getContextPath());
				}
			    
			} else {
				filterChain.doFilter(request, response);
			}
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		this.restrictedResources = new HashSet();
		this.restrictedResources.add("/login/login.htm");
		this.restrictedResources.add("/login/loginform.htm");
		this.restrictedResources.add("/login/logout.htm");
		this.restrictedResources.add("/login/timeout.jsp");
		this.restrictedResources.add("/index.jsp");
		this.restrictedResources.add("//WEB-INF/view/login/login.jsp");
		this.restrictedResources.add("/upload/imageUpload.htm");
		
	}
	
	private boolean contains(String value) {
		Iterator ite = this.restrictedResources.iterator();
		while (ite.hasNext()) {
			String restrictedResource = (String) ite.next();
			if (( restrictedResource).equalsIgnoreCase(value)) {
				return true;
			}
		}
		return false;
	}
}
