package com.enjoyor.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.enjoyor.common.util.MD5;
import com.enjoyor.pojo.AcctUser;
import com.enjoyor.service.AcctUserService;
import com.enjoyor.common.util.StringUtil;;

@Controller
public class LoginController {
	@Autowired
	private AcctUserService uService;
	
	public AcctUserService getuService() {
		return uService;
	}

	public void setuService(AcctUserService uService) {
		this.uService = uService;
	}

	@RequestMapping(value="login.htm",produces="text/html;charset=UTF-8")
	public String login(Model model,HttpServletRequest request,HttpServletResponse res){
		MD5 md5 = new MD5();
		String name = request.getParameter("phone");
		if(name == null) return "login";
		name = StringUtil.ReplaceSQLChar(name);
		String password = request.getParameter("password");
		Map map = uService.selectAcctUserByName(name);
		if(name!=null && !"".equals(name) && !"".equals(password) && password!=null){
			AcctUser user=uService.selectAcctUserByLoginName(name);
			if(user!=null&&user.getPasswd()!=null){
				//String ps = md5.getMD5ofStr(password);
				//System.out.println(ps);
				if(md5.getMD5ofStr(password).equals(user.getPasswd())){
					HttpSession sessionOld = request.getSession(false);
					if(sessionOld != null){
						sessionOld.invalidate();//清空session
					}
					Cookie cookie = request.getCookies()[0];//获取cookie
					cookie.setMaxAge(0);//让cookie过期
					HttpSession session = request.getSession(true);
					if(session!=null){
						session.setAttribute("CURRENTUSER", user);
					}
					model.addAttribute("msg", "success");
					return "homepage";
					
				}else{
					model.addAttribute("msg", "false");
					return "login";
				}
			}else{
				model.addAttribute("msg", "false");
				return "login";
			}
			
		}
		return "login";
	}

	@RequestMapping(value="cancel.htm",produces="text/html;charset=UTF-8")
	public String cancel(Model model,HttpServletRequest request,HttpServletResponse res){
		HttpSession session = request.getSession();
		session.removeAttribute("CURRENTUSER");
		
		model.addAttribute("msg", "cancel");
		return "login";
	}
	
	@ResponseBody
	@RequestMapping(value="test.htm",produces="text/html;charset=UTF-8")
	public String test(Model model,HttpServletRequest request,HttpServletResponse res){
//		HttpSession session = request.getSession();
//		session.removeAttribute("CURRENTUSER");
//		model.addAttribute("msg", "cancel");
		//uService.selectAcctUserByLoginName("sgf");
		String name = request.getParameter("username");
		name = StringUtil.ReplaceSQLChar(name);
		AcctUser user=uService.selectAcctUserByLoginName(name);
		
		return user.toString();
	}
}
