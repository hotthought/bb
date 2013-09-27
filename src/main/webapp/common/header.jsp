<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<!--头部:Start-->
<div id="header">
	<div id="top">
          	<h1><span>杭州市交警支队指挥平台</span></h1>
              <div id="topinfo">
              	<ul class="quick-menu">
                  <!--设置:Start-->
                      <li class="setting menu-item">
                          <div class="menu">
                          <a class="menu-hd" href="javascript:void(0)" rel=nofollow><span class="ico-set"></span><B></B></a> 
                          <div class="menu-bd">
                              <div class="menu-bd-panel">
                                <dl>
                                   <dt><span>基本设置</span>
                                   <dd>
                                     <a href="javascript:void(0)" title="个人信息">个人信息</a>
                                     <a id="moduleSetA" href="javascript:void(0)" title="模块设置">模块设置</a>
                                   </dd>
                              </dl>
                              
                              <dl class="last">
                                   <dt><span>安全设置</span>
                                   <dd>
                                  <a id="uPasswordA" href="javascript:void(0)" title="修改密码">修改密码</a>
                                  </dd>
                              </dl>
                              </div>
                          </div>
                          </div>
                      </li>
                <!--退出:Start-->
                      <li class="quit menu-item">
                          <div class="menu">
                              <a id="logoutA" class="menu-hd" href="javascript:void(0);" rel=nofollow><span class="ico-quit"></span></a>
                          </div>
                      </li>
                      <!--全屏:Start-->
                        <li class="fullscreen menu-item">
                            <div class="menu">
                                <a class="menu-hd" href="#" rel=nofollow title="全屏"><span class="ico-fullscreen"></span></a>
                            </div>
                        </li>
                  </ul>
              </div>
          </div>
	<!--导航:Start-->
	<div id="navbar">
		<div id="user">
              	<div id="photo">
                  	<img src="${basePath }/images/photo.jpg" alt="照片" />
                  </div>
                  <div id="info">
                  	<p>${LoginUser.name}</p>
                      <em>${LoginUser.pno}</em>
                  </div>
              </div>
              <div id="nav">
              	<ul id="headerMenu">
              	
              	
           
                  </ul>
              </div>
              <div id="date">
              	<ul>
                  	<li class="year"></li>
                      <li class="time"></li>
                      <li class="week"></li>
                  </ul>
              </div>
	</div>
	<!--导航:End-->

</div>
<!--头部:End-->
<script type="text/javascript">
<!--
function splitDate(){   
    var d=new Date(); 
    var yyyy,MM,dd,hh,mm,day;    
    yyyy=d.getFullYear();    
    day = d.getDay();
    MM=(d.getMonth()+1)<10?"0"+(d.getMonth()+1):d.getMonth()+1;
    dd=d.getDate()<10?"0"+d.getDate():d.getDate();
    hh=d.getHours()<10?"0"+d.getHours():d.getHours();
    mm=d.getMinutes()<10?"0"+d.getMinutes():d.getMinutes();
    var yearStr = yyyy+"年" + MM +"月" + dd + "日";
    var timeStr = hh + ":" +mm;
    var weekStr = weekDay(day);
    $(".year").html(yearStr); 
    $(".time").html(timeStr); 
    $(".week").html(weekStr); 
}

function weekDay(day){
	var weekDay = "";
	switch(day)
	   {
	   case 0:
		   weekDay = "星期日";
	       break;
	   case 1:
		   weekDay = "星期一";
	     break;
	   case 2:
		   weekDay = "星期二";
	       break;
	   case 3:
		   weekDay = "星期三";
	       break;
	   case 4:
		   weekDay = "星期四";
	       break;
	   case 5:
		   weekDay = "星期五";
	       break;
	   case 6:
		   weekDay = "星期六";
	       break;
	   }
	return weekDay;
}
$(document).ready(function(){
	splitDate();
    setInterval("splitDate();",1000);
    $("#logoutA").click(function(){
    	if(confirm("确定要退出吗？")){
    		window.location.href = "${basePath}/login/logout.htm";
    	} else {
    		return false;
    	}
    });
});
function showDialogView(url,dialogID,initFun,closeFun){
	if($("#"+dialogID).length > 0){
		$("#"+dialogID).dialog({
			height: 500,
			width : 800,
	        modal: true, 
	        close:function(event, ui){
	        	if(typeof closeFun == 'function'){
	        		closeFun();
	        	}
	        	$("#photoFile").uploadify("destroy");
	        }
		});
		
		if(typeof initFun == 'function'){
			initFun(url, dialogID);
		}
		$("#photoFile").uploadify({
	        height        : 20,
	        swf           : '${basePath}/uploadify/uploadify.swf',
	        uploader      : '${basePath}/upload/fileUpload.htm?jsessionid=<%=request.getSession().getId()%>',
	        width         : 90,
	        buttonText    :"选择文件",
	        fileSizeLimit : "1Mb",
	        formData:{fileType:'image', width : 16, height:16, fileRootPathKey:'avatar.thumbnails.photo.rootpath'},
	        auto : true,
	        fileTypeExts : '*.gif; *.jpg; *.png, *.bmp',
	        onUploadStart : function(file){
	        	if($("#photoPath").val() != ""){
	        		alert("上传已达到上限！");
	        		$("#photoPath").uploadify('cancel');
	        	}
	        },
	        onUploadSuccess : function(file, data, response){
	        	var json = eval("("+data+")");
	        	var fileInfo = json.result.fileInfo;
	        	if(fileInfo != null){
	        		$("#photoPath").val(fileInfo.filePath);
	        	}
	        }
		});
		
	}
}
//-->
</script>   