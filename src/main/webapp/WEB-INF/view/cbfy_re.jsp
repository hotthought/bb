<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">  
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=2.0"/>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
<title>报表辅助系统</title>
<link type="text/css" rel="stylesheet" href="css/global.css" />
<link type="text/css" rel="stylesheet" href="css/layout.css" />
<link type="text/css" rel="stylesheet" href="css/theme/blue/skin.css" />
<style type="text/css">
</style>
</head>
<body class="Dialogbody">
<div id="main">
<div id="left_box">
			<div class="DialogTitle">
				<div class="DialogTitleContent" >Excel文件列表</div>
			</div>
			<ul class="road_left">
				<li><div id="cbfyManage">
						<a href="#" class="current">成本费用表</a>
					</div></li>
				<li><div id="lrManage">
						<a href="#">利润表</a>
					</div></li>
				<li><div id="xjllManage">
						<a href="#">现金流量表</a>
					</div></li>
				<li><div id="zcfzManage">
						<a href="#">资产负债表</a>
					</div></li>
				<li><div id="qtzbManage">
						<a href="#">其他指标表</a>
					</div></li>
			</ul>
		</div>
	<div id="right_box">
		<div id="in_right_box" style="text-align: center; margin-top: 200px;">
		<div>
		<span style="font-size:18px;">
		${msg}<br/>
		${importmsg}<br/>
		</span>
		<br/>
		
		<a href="cbfy.htm" style="color:#3B76F5;font-size:21px;">返回&lt;&lt;&lt;</a>
		
		
		</div>
		</div>
	</div>
	</div>
</body>

</html>
