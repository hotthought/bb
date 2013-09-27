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
<link type="text/css" rel="stylesheet" href="css/jquery-ui/jquery-ui-1.9.1.custom.css" />
<style type="text/css">

.txt{ height:25px; border:1px solid #cdcdcd; width:180px;margin:2px;} 
.filebtn{ background-color:#D9DCDF; border:1px solid #CDCDCD;height:25px; width:50px;} 
.file{ position:absolute; top:103px; left:268px; height:25px; filter:alpha(opacity:0);opacity:0; width:50px } 
</style>
<script language="javascript" type="text/javascript" charset="utf-8" src="js/jquery.min.1.8.2.js"></script>
<script language="javascript" type="text/javascript" charset="utf-8" src="js/jquery-ui-1.9.1.custom.min.js"></script>

<script type="text/javascript" src="js/common.js" ></script>
<script type="text/javascript" src="js/resulttable.js" ></script>
<script type="text/javascript" src="js/ajaxresult2tableV1.0.js" ></script>
<script language="javascript" type="text/javascript">
$(document)
 .ready(
		function() {
			
			$("#btnimportEx").click(function(){
				$('#form1')[0].reset();
				$("#importContent").dialog({
					resizable : true,
					width : 400,
					modal : false,
					title : '导入Excel数据'
				});
				
			});
		});
</script>
<script type="text/javascript">

function validate_required(field,alerttxt)
{
with (field)
  {
  if (value==null||value=="")
    {alert(alerttxt);return false}
  else {return true}
  }
}

function validate_form(){
	alert(11111);
return false;
}
</script>
</head>
<body class="Dialogbody">
	<div id="main">
		<%--左侧菜单 --%>
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
					<li><div id="csmbManage">
						<a href="#">定义参数模板</a>
					</div></li>
					<li><div id="scbgManage">
						<a href="#">生成报告</a>
					</div></li>
			</ul>
		</div>

		<div id="right_box">
			<div id="in_right_box">
				
				<div class="commbox">
					<div id="tools" class="toolsbar">

						<ul style="float: right; margin-right: 5px">
							<li><a id="btnimportEx" href="#" class="bttoolsL">
							<span class="import">导入数据</span></a></li>
							<li><a id="btnAddblock" href="#" class="bttools"><span
									class="add">添加</span></a></li>
							<li><a id="btnEditblock" href="#" class="bttools"><span
									class="edit">修改</span></a></li>
							<li><a id="btnDelblock" href="#" class="bttools"><span
									class="del">删除</span></a></li>
						</ul>
					</div>
					<div class="tablecon">
						<table id="roadResultTable" keyid="ROWNUM_" style="width: 100%; text-align: center;" selecttype="radio">
								<thead>
									<tr>
										<th column="ROWNUM_">成本项目</th>
										<th column="ROAD_NAME">行次</th>
										<th column="ROAD_LENGTH">发电成本</th>
										<th column="SPEED_LIMIT">购电成本</th>
										<th column="ROAD_START">输配电成本</th>
										<th column="ROAD_END">管理费用</th>
										<th column="ROAD_DIRECTION">营业费用</th>
										<th column="REMARK">业务及管理费</th>
									</tr>
								</thead>
								<tbody>
								<tr key="1" class=""><td class="">电力费</td><td class="">1</td><td class="">2000</td><td class="">60</td><td class="">34</td><td class="">44</td><td class="">44</td><td class="">45</td></tr>
								<tr key="2" class="trDoubleRow"><td class="">电力设施保护费</td><td class="">2</td><td class="">22</td><td class="">244</td><td class="">55</td><td class="">555</td><td class="">44</td><td class="">66</td></tr>
								<tr key="3" class=""><td class="">取暖费</td><td class="">3</td><td class="">1000</td><td class="">40</td><td class="">44</td><td class="">77</td><td class="">55</td><td class="">66</td></tr>
								</tbody>
							</table>
						<div id="blockPager" class="pager"></div>
					</div>
				</div>
			</div>


			<div id="importContent" style="display: none;  height: 280px;">
				<form action="impcbfy.htm" id="form1" method="post" enctype="multipart/form-data" onsubmit="return validate_form();">
				<div style="height: 100%"> 
				<ul class="inputdia">
				<li><label style="width: 68px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年：</label><input type='text' name='exyear' id='exyear' class='txt'/>
				</li> 
				<li><label style="width: 68px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月：</label><input type='text' name='exmonth' id='exmonth' class='txt'/> 
				</li>
				<li><label style="width: 68px;">&nbsp;表格编号：&nbsp;</label><input type='text' name='exbianhao' id='exbianhao' class='txt'/>
				</li>
				<li><label style="width: 68px;">Excel文件：</label> 
				<input type='text' name='textfield' id='textfield' class='txt' /> 
				<input type='button' class='filebtn' value='浏览...' /> 
				<input type="file" name="myfile" class="file" id="fileField" size="28" onchange="document.getElementById('textfield').value=this.value" /> 
				</li>
			</ul> 
									<input type="submit" name="submit" class="filebtn" value="确定" /> 
									<input  type="reset" value="取消" class="filebtn" />
					
				</div>
				</form> 
			</div>
			
		
			
		</div>
	</div>
</body>

</html>
