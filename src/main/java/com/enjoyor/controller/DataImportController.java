package com.enjoyor.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.enjoyor.common.util.ExcelImportTools;
import com.enjoyor.common.util.MD5;
import com.enjoyor.pojo.AcctUser;
import com.enjoyor.service.AcctUserService;
import com.enjoyor.common.util.StringUtil;;

@Controller
public class DataImportController {
	@Autowired
	private AcctUserService uService;

	
	@RequestMapping(value="cbfy.htm",produces="text/html;charset=UTF-8")
	public String cbfy(Model model,HttpServletRequest request,HttpServletResponse res){
		
		return "cbfy";
	}
	
	@RequestMapping(value="impcbfy.htm",produces="text/html;charset=UTF-8",method=RequestMethod.POST)
	public String impcbfy(Model model, MultipartFile myfile,HttpServletRequest request,HttpServletResponse res){
		 //如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解 
        //如果想上传多个文件，那么这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解 
        //并且上传多个文件时，前台表单中的所有<input type="file"/>的name都应该是myfile，否则参数里的myfile无法获取到所有上传的文件 
	
	            if(myfile==null || myfile.isEmpty()){ 
	              //  System.out.println("文件未上传"); 
	                model.addAttribute("msg", "未上传文件");
	            }else{ 
//	                System.out.println("文件长度: " + myfile.getSize()); 
//	                System.out.println("文件类型: " + myfile.getContentType()); 
//	                System.out.println("文件名称: " + myfile.getName()); 
//	                System.out.println("文件原名: " + myfile.getOriginalFilename()); 
	            	List<Object[]> datalist = null;
	                String realPath = request.getSession().getServletContext().getRealPath("/upload"); 
	                File file = new File(realPath);
	                if(! file.exists()){
	                	file.mkdirs();
	                }
	                //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的 
	                try {
						FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, myfile.getOriginalFilename()));
						File exfile = new File(realPath, myfile.getOriginalFilename());
						int startRow = 8;
						if(exfile.exists()){
							ExcelImportTools<Object> et = new ExcelImportTools<Object>();
							try {
								datalist = et.importExcel(exfile, startRow);
								model.addAttribute("importmsg", "数据导入成功");
							} catch (Exception e) {
								model.addAttribute("importmsg", "数据导入失败");
								e.printStackTrace();
							}
						}
						model.addAttribute("msg", "文件上传成功");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						model.addAttribute("msg", "文件上传失败");
						e.printStackTrace();
					}
	            } 
		
		return "cbfy_re";
	}
	
	@ResponseBody
	@RequestMapping(value="test2.htm",produces="text/html;charset=UTF-8")
	public String test(Model model,HttpServletRequest request,HttpServletResponse res){

		String name = request.getParameter("username");
		name = StringUtil.ReplaceSQLChar(name);
		AcctUser user=uService.selectAcctUserByLoginName(name);
		
		return user.toString();
	}
}
