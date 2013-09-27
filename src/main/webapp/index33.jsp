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

.txt{ height:25px; border:1px solid #cdcdcd; width:180px;margin:2px;} 
.filebtn{ background-color:#D9DCDF; border:1px solid #CDCDCD;height:25px; width:60px;} 
.file{ position:absolute; top:14px; left:463px; height:25px; filter:alpha(opacity:0);opacity: 0;width:100px } 
</style>
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
						<a href="#">成本费用表</a>
					</div></li>
				<li><div id="lrManage">
						<a href="#" class="current">利润表</a>
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
			<div id="in_right_box">
				
				<div class="commbox">
					<div id="tools" class="toolsbar">
						<div class="file-box"> 
<form action="" method="post" enctype="multipart/form-data" style="width: 400px;height:30px; display:inline;"> 
<label>导入Excel文件</label>
<input type='text' name='textfield' id='textfield' class='txt' /> 
<input type='button' class='filebtn' value='浏览...' /> 
<input type="file" name="fileField" class="file" id="fileField" size="28" onchange="document.getElementById('textfield').value=this.value" /> 
<input type="submit" name="submit" class="filebtn" value="上传" /> 
</form> 
						<ul style="float: right; margin-right: 5px">
							<li><a id="btnAddblock" href="#" class="bttools"><span
									class="add">添加</span></a></li>
							<li><a id="btnEditblock" href="#" class="bttools"><span
									class="edit">修改</span></a></li>
							<li><a id="btnDelblock" href="#" class="bttools"><span
									class="del">删除</span></a></li>
							<li><a id="btnAddDev" href="#" class="bttoolsL"><span
									class="export">导出报告</span></a></li>
						</ul>
</div> 
					</div>
					<div class="tablecon">
						<table id="roadResultTable" keyid="ROWNUM_" style="width: 100%; text-align: center;" selecttype="radio">
								<thead>
									<tr>
										<th column="ROWNUM_">序号</th>
										<th column="ROAD_NAME">道路名称</th>
										<th column="ROAD_LENGTH">道路长度</th>
										<th column="SPEED_LIMIT">道路限速</th>
										<th column="ROAD_START">道路起点</th>
										<th column="ROAD_END">道路终点</th>
										<th column="ROAD_DIRECTION" datatype="json" jsonvalue="({'1':'由东向西','2':'由西向东','3':'由南向北','4':'由北向南','5':'上行',
'6':'下行','7':'双向','8':'其他'})">道路方向</th>
										<th column="REMARK">备注</th>
									</tr>
								</thead>
								<tbody><tr key="1" class=""><td class="">1</td><td class="">古墩路</td><td class="">2000</td><td class="">60</td><td class="">文三路</td><td class="">祥符路</td><td class="">由东向西</td><td class=""></td></tr><tr key="2" class="trDoubleRow"><td class="">2</td><td class="">测试添加1</td><td class="">22</td><td class="">244</td><td class="">文一路古墩路口</td><td class="">文三路教工路口</td><td class="">由东向西</td><td class=""></td></tr><tr key="3" class=""><td class="">3</td><td class="">文三路</td><td class="">1000</td><td class="">40</td><td class="">教工路</td><td class="">上塘路</td><td class="">由西向东</td><td class=""></td></tr></tbody>
							</table>
						<div id="blockPager" class="pager"></div>
					</div>
				</div>
			</div>


			<div id="blockContent"
				style="display: none; overflow: auto; height: 480px;">
				<form action="" id="form1" method="post">
					<div style="height: 100%">
						<input type="hidden" id="blockid" />
						<ul class="search">
							<li><label>路段名称&nbsp;：</label><input type="text" style=""
								id="blockname" /></li>
							<li><label>所在区域&nbsp;：</label><select id="zoneId"
								name="zoneId" style="height:26px;">
									<c:forEach items="${zoneList}" var="item">
										<option value="${item.zoneCode}">${item.zoneName}</option>
									</c:forEach>
							</select></li>
							<li><label>所在道路编号：</label> <input id="roadid" type="text" />
							</li>
							<li><label>上游路口编号：</label> <input id="upcrossid" type="text" />
							</li>
							<li><label>下游路口编号：</label> <input id="downcrossid"
								type="text" /></li>
							<li><label>路段方向：</label>
							<select id="blockdir" name="blockdir">
									<option value="1">由东向西</option>
									<option value="2">由西向东</option>
									<option value="3">由南向北</option>
									<option value="4">由北向南</option>
									<option value="5">上行</option>
									<option value="6">下行</option>
									<option value="7">双向</option>
									<option value="8">其他</option>
							</select>
							</li>
							<li><label>路段类型：</label> 
							<select id="blocktype"  name="blocktype">
									<option value="0">地面</option>
									<option value="1">高架</option>
							</select></li>
							<li><label>是否单行道：</label> <select id="blocksingle"
								name="blocksingle">
									<option value="2">否</option>
									<option value="1">是</option>
							</select></li>
							<li><label>路段长度：</label> <input id="blocklength" type="text" />
							</li>
							<li><label>备注：</label> <input id="remark" type="text" /></li>
						</ul>

						<ul class="search">
							<li><label>&nbsp;&nbsp;&nbsp;&nbsp;</label> <input
								id="btnInsertBlock" type="button" value="确定" class="btn" /> <input
								id="btnCancel" type="button" value="取消" class="btn" /></li>
						</ul>
					</div>
				</form>
			</div>
			<%--关联设备 --%>
			<div id="blockDevContent" style="display: none;">
				<form action="" id="form2" method="post">
					<div style="height: 480px; overflow-y: auto;">
						<input type="hidden" id="addblockid" /> <input type="hidden"
							id="addDevType" />
						<ul class="search">
							<li><span style="float: left;">设备类型：</span> <span
								onclick="devSelect(4)" class="selspan">电子卡口</span> <span
								onclick="devSelect(8)" class="selspan">视频检测</span> <span
								onclick="devSelect(3)" class="selspan">诱导屏</span> <span
								onclick="devSelect(7)" class="selspan">微波</span> <span
								onclick="devSelect(9)" class="selspan">地磁</span></li>
							<li><label>关键字：</label><input id="addKey" type="text" /></li>
							<li><input id="addbtnSearch" type="button" value="查询"
								class="btn" /></li>
						</ul>

						<div id="devZoneDiv" style="display: none; height: 30px;">
						</div>
						<div class="index_b_kuang">
							<ul id="devSelUl">
							</ul>
						</div>
						<div id="btnDiv" style="display: none;">
							<ul class="search">
								<li><label>&nbsp;&nbsp;&nbsp;&nbsp;</label> <input
									id="addbtnInsertBlock" type="button" value="确定" class="btn" />
									<input id="addbtnCancel" type="button" value="取消" class="btn" />
								</li>
							</ul>
						</div>

					</div>
				</form>
			</div>
		</div>
	</div>
</body>

</html>
