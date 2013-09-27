<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>路段管理</title>
<style type="text/css">
html,body {
	width: 100%;
	height: 100%;
	overflow: hidden;
	border-left-width: 0px
}

body {
	margin: 0;
	padding: 0;
	text-align: center;
	background-color: #ffffff;
	height: 100%;
}

.DialogTitle {
	height: 24px;
	color: #fff;
}

.DialogTitleContent {
	float: left;
	color: #fff;
	padding: 0px 0px 0px 5px;
	line-height: 24px;
}

#topContent {
	float: left;
	width: 100%;
}

#centerContent {
	float: left;
	width: 100%;
}

#bottomContent {
	float: left;
	width: 100%;
}

#tablediv {
	float: right;
	width: 100%;
}

#road_left {
	float: left;
	width: 15%;
}
.selspan {
	float: left;
	border: #c1c2c3 1px solid;
	margin: 3px;
	white-space: nowrap;
	line-height: 18px;
	cursor: pointer;
}

.bgSpan {
	float: left;
	border: #c1c2c3 1px solid;
	margin: 3px;
	white-space: nowrap;
	line-height: 18px;
	cursor: pointer;
	background-color: #6495ED;
}

.search li label {
	float: left;
	width: 90px;
	text-align: right;
	margin-top: 4px;
	height: 20px;
	line-height: 20px;
}

.index_b_kuang {
	width: 620px;
	padding-left: 10px;
}

.index_b_kuang ul {
	width: 620px;
	padding: 5px 0 5px 0;
	margin: 0;
	list-style-type: none;
}

.index_b_kuang ul li {
	float: left;
	width: 182px;
	height: 23px;
	line-height: 22px !important line-height:23px;
	display: block;
	text-align: left;
	overflow: auto;
}
</style>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						var blockSearch = new AjaxResult2Table(
								{
									pagerId : "blockPager",
									resultTableId : "blockResultTable",
									searchUrl : "${basePath}/servicemanage/queryRoad.htm"
								});

						$("#btnSearch").click(
								function() {
									blockSearch.search({
										key : $("#txtName").val(),
										type : 2
									}, null, function() {
										$("#blockResultTable tbody tr").each(
												function() {

												});
									});
								});

						$("#btnAddblock").click(function() {
							$('#form1')[0].reset();
							$("#blockContent").dialog({
								resizable : true,
								width : 500,
								modal : false,
								title : '添加路段'
							});
							$("#blockid").val("");
						});

						$("#btnDelblock")
								.click(
										function() {

											if ($("#blockResultTable tr.trSelect")[0] == undefined) {
												jAlert("请选择需要删除的行！");
											} else {
												selctIndex = $(
														"#blockResultTable tbody tr")
														.index(
																$("#blockResultTable tr.trSelect")[0]);
												var blockObjSelect = blockSearch.page.result[selctIndex];
												var id = blockObjSelect.BLOCK_ID;
												jConfirm("确定删除？", function() {
													delblock(id);
												});
											}
										});

						$("#btnEditblock")
								.click(
										function() {

											if ($("#blockResultTable tr.trSelect")[0] == undefined) {
												jAlert("请选择需要修改的行！");
											} else {
												selctIndex = $(
														"#blockResultTable tbody tr")
														.index(
																$("#blockResultTable tr.trSelect")[0]);
												var blockObjSelect = blockSearch.page.result[selctIndex];
												var id = blockObjSelect.BLOCK_ID;
												var blockname = blockObjSelect.BLOCK_NAME;
												var zoneId = blockObjSelect.ZONE_ID;
												var roadid = blockObjSelect.ROAD_ID;
												var upcrossid = blockObjSelect.UP_CROSS_ID;
												var downcrossid = blockObjSelect.DOWN_CROSS_ID;
												var blockdir = blockObjSelect.BLOCK_DIRECTION;
												var blocktype = blockObjSelect.BLOCK_TYPE;
												var blocksingle = blockObjSelect.BLOCK_SINGLE;
												var blocklength = blockObjSelect.BLOCK_LENGTH;
												var remark = blockObjSelect.REMARK;
												$("#blockContent").dialog({
													resizable : true,
													width : 500,
													modal : false,
													title : '修改'
												});
												$("#blockid").val(id);
												$("#blockname").val(blockname);
												$("#zoneId").val(zoneId);
												$("#roadid").val(roadid);
												$("#upcrossid").val(upcrossid);
												$("#downcrossid").val(
														downcrossid);
												$("#blockdir").val(blockdir);
												$("#blocktype").val(blocktype);
												$("#blocksingle").val(
														blocksingle);
												$("#blocklength").val(
														blocklength);
												$("#remark").val(remark);

											}
										});

						$("#addbtnSearch")
								.click(
										function() {
											$
													.ajax({
														type : "GET",
														url : "${basePath}/servicemanage/queryDevRel.htm",
														data : {
															type : $(
																	"#addDevType")
																	.val(),
															key : $("#addKey")
																	.val()
														},
														success : function(data) {
															$("#devSelUl")
																	.empty();
															$("#btnDiv").hide();
															var result = data.result;
															if (result != null) {
																var str = "";
																for ( var i = 0; i < result.length; i++) {
																	//var devid = result[i].NUMID;
																	//var name = result[i].NAME;
																	str += "<li><input type='checkbox' name='addDevSel' value='"+result[i].NUMID+"' />"
																			+ result[i].NAME
																			+ "</li>";
																	// $("#devSelUl").append("<li><input type='checkbox' name='addDevSel' value='"+result[i].NUMID+"' />"+result[i].NAME+"</li>"); 
																}
																$("#devSelUl")
																		.append(
																				str);
															}
															$("#devSelUl")
																	.show();
															$("#btnDiv").show();

														}
													});
										});

						$("#btnInsertBlock")
								.click(
										function() {
											if (!validateForm()) {
												return false;
											}
											CallWebMethod(
													"${basePath}/servicemanage/insertBlock.htm",
													{
														id : $("#blockid")
																.val(),
														blockname : $(
																"#blockname")
																.val(),
														zoneId : $("#zoneId")
																.val(),
														roadid : $("#roadid")
																.val(),
														upcrossid : $(
																"#upcrossid")
																.val(),
														downcrossid : $(
																"#downcrossid")
																.val(),
														blockdir : $(
																"#blockdir")
																.val(),
														blocktype : $(
																"#blocktype")
																.val(),
														blocksingle : $(
																"#blocksingle")
																.val(),
														blocklength : $(
																"#blocklength")
																.val(),
														remark : $("#remark")
																.val()
													},
													function(data) {
														if (data.success == true) {
															jAlert("成功！");
															$('#form1')[0]
																	.reset();
															$("#blockContent")
																	.dialog(
																			"close");
															$("#btnSearch")
																	.click();
														} else {
															jAlert("失败！");
														}
													}, null);
										});

						$("#btnCancel").click(function() {
							$('#form1')[0].reset();
							$("#blockContent").dialog("close");
						});

						//关联设备
						$("#btnAddDev")
								.click(
										function() {
											$("#addblockid").val("");
											if ($("#blockResultTable tr.trSelect")[0] == undefined) {
												jAlert("请选择需要的行！");
											} else {
												selctIndex = $(
														"#blockResultTable tbody tr")
														.index(
																$("#blockResultTable tr.trSelect")[0]);
												var blockObjSelect = blockSearch.page.result[selctIndex];
												var id = blockObjSelect.BLOCK_ID;
												var blockname = blockObjSelect.BLOCK_NAME;
												$("#addblockid").val(id);
												$('#form2')[0].reset();
												$("#devSelUl").empty();
												$("#btnDiv").hide();
												$("#blockDevContent").dialog({
													resizable : true,
													width : 650,
													modal : false,
													title : blockname
												});
											}

										});

						$("#addbtnInsertBlock")
								.click(
										function() {
											var r = $("input[name='addDevSel']");
											var s = "";
											for ( var i = 0; i < r.length; i++) {
												if (r[i].checked) {
													s += r[i].value + ",";
												}
											}
											if (s == "") {
												jAlert("请选择设备");
												return;
											}
											CallWebMethod(
													"${basePath}/servicemanage/updateBlockDev.htm",
													{
														id : $("#addblockid")
																.val(),
														addDevNo : s,
														devType : $(
																"#addDevType")
																.val()
													},
													function(data) {
														if (data.success == true) {
															jAlert("成功！");
															$('#form2')[0]
																	.reset();
															$(
																	"#blockDevContent")
																	.dialog(
																			"close");
														} else {
															jAlert("失败！");
														}
													}, null);
										});
						$("#addbtnCancel").click(function() {
							$('#form2')[0].reset();
							$("#blockDevContent").dialog("close");
						});

						$("ul span").click(function() {
							$(this).css({
								"background-color" : "#6495ED"
							}).siblings().css({
								"background-color" : ""
							});
						});

						$("#btnSearch").click();

					});
</script>
<script type="text/javascript">
	function validateForm() {
		if ($("#blockContent input[id=blockname]").val() == "") {
			jAlert("请填写路段名称");
			return false;
		} else if (!/^\d+$/
				.test($("#blockContent input[id=blocklength]").val())) {
			jAlert("请正确填写路段长度");
			return false;
		} else if ($("#blockContent input[id=blockdir]").val().length > 5) {
			jAlert("请正确填写路段方向");
			return false;
		}else if (!/^\d+$/
				.test($("#blockContent input[id=roadid]").val())) {
			jAlert("请正确填写所在道路编号");
			return false;
		}else if (!/^\d+$/
				.test($("#blockContent input[id=upcrossid]").val())) {
			jAlert("请正确填写上游路段编号");
			return false;
		}else if (!/^\d+$/
				.test($("#blockContent input[id=downcrossid]").val())) {
			jAlert("请正确填写下游路段编号");
			return false;
		}
		return true;
	}

	function delblock(id) {
		if (id == "")
			jAlert("请选择删除的数据");
		CallWebMethod("${basePath}/servicemanage/delRoad.htm", {
			id : id,
			type : "2"
		}, function(data) {
			if (data.success == true) {
				jAlert("删除成功！");
				$("#btnSearch").click();
			} else {
				jAlert("删除失败！");
			}
		}, null);
	}

	function devSelect(ty) {
		$("#addDevType").val(ty);
		$.ajax({
			type : "GET",
			url : "${basePath}/servicemanage/queryDevZone.htm",
			data : {
				type : ty
			},
			success : function(result) {
				//	var result =data.result;
				$("#devZoneDiv").empty();
				$("#devSelUl").empty();
				$("#btnDiv").hide();
				if (result != null) {
					var str = "";
					for ( var i = 0; i < result.length; i++) {
						str += "<span onclick='zoneSelect(" + result[i].ID
								+ "," + ty + ")' class='selspan'>"
								+ result[i].NAME + "</span>";
					}
					$("#devZoneDiv").append(str);
				}
				$("#devZoneDiv").show();
				$("#devZoneDiv span").click(function() {
					$(this).css({
						"background-color" : "#6495ED"
					}).siblings().css({
						"background-color" : ""
					});
				});
			}
		});

	}

	function zoneSelect(no, ty) {
		$
				.ajax({
					type : "GET",
					url : "${basePath}/servicemanage/queryDevRel.htm",
					data : {
						type : ty,
						zoneId : no
					},
					success : function(data) {
						$("#devSelUl").empty();
						$("#btnDiv").hide();
						var result = data.result;
						if (result != null) {
							var str = "";
							for ( var i = 0; i < result.length; i++) {
								//var devid = result[i].NUMID;
								//var name = result[i].NAME;
								str += "<li><input type='checkbox' name='addDevSel' value='"+result[i].NUMID+"' />"
										+ result[i].NAME + "</li>";
								// $("#devSelUl").append("<li><input type='checkbox' name='addDevSel' value='"+result[i].NUMID+"' />"+result[i].NAME+"</li>"); 
							}
							$("#devSelUl").append(str);
						}
						$("#devSelUl").show();
						$("#btnDiv").show();
					}
				});
	}
</script>
</head>
<body class="Dialogbody">
	<div id="main">
		<%--左侧菜单 --%>
		<div id="left_box">
			<div class="DialogTitle">
				<div class="DialogTitleContent" >道路列表</div>
			</div>
			<ul class="road_left">
				<li><div id="roadManage">
						<a href="roadManage.htm">道路管理</a>
					</div></li>
				<li><div id="blockManage">
						<a href="blockManage.htm" class="current">路段管理</a>
					</div></li>
				<li><div id="crossManage">
						<a href="crossManage.htm">路口管理</a>
					</div></li>
				<li><div id="vmsManage">
						<a href="vmsManage.htm">诱导屏管理</a>
					</div></li>
				<li><div id="diciManage">
						<a href="#">地磁管理</a>
					</div></li>
			</ul>
		</div>

		<div id="right_box">
			<div id="in_right_box">
				<div id="tablediv">
					<div id="topContent">
						<div class="commbox">
							<h3>
								<span>查询条件</span>
							</h3>
							<div class="searchbox" style="text-align: left">
								<ul class="search">
									<li><label>名称：</label><input id="txtName" type="text" /></li>
									<li><input id="btnSearch" type="button" value="查询"
										class="btn" /></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<div class="commbox">
					<div id="bottomContent">
						<div id="tools" class="toolsbar commbox">
							<ul style="float: right; margin-right: 5px">
								<li><a id="btnAddblock" href="#" class="bttools"><span
										class="add">添加</span></a></li>
								<li><a id="btnEditblock" href="#" class="bttools"><span
										class="edit">修改</span></a></li>
								<li><a id="btnDelblock" href="#" class="bttools"><span
										class="del">删除</span></a></li>
								<li><a id="btnAddDev" href="#" class="bttoolsL"><span
										class="add">关联设备</span></a></li>
							</ul>
						</div>
					</div>
					<div class="tablecon">
						<table id="blockResultTable" keyid="ROWNUM_"
							style="width: 100%; text-align: center;" selectType="radio">
							<thead>
								<tr>
									<th column="ROWNUM_">序号</th>
									<th column="BLOCK_NAME">路段名称</th>
									<th column="BLOCK_LENGTH">路段长度</th>
									<th column="BLOCK_DIRECTION">路段方向</th>
									<th column="BLOCK_TYPE" dataType="json"
										jsonValue="({'0':'地面','1':'高架'})">路段类型</th>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
						<div id="blockPager"></div>
					</div>
				</div>
			</div>


			<div id="blockContent"
				style="display: none; overflow: auto; height: 480px;">
				<form action="" id="form1" method="post">
					<div style="height: 100%">
						<input type="hidden" id="blockid" />
						<ul class="search">
							<li><label>路段名称&nbsp;：</label> <input type="text" style=""
								id="blockname" /></li>
							<li><label>所在区域&nbsp;：</label> <select id="zoneId"
								name="zoneId">
									<c:forEach items="${daduiList}" var="item">
										<option value="${item.ID}">${item.NAME}</option>
									</c:forEach>
							</select></li>
							<li><label>所在道路编号：</label> <input id="roadid" type="text" />
							</li>
							<li><label>上游路口编号：</label> <input id="upcrossid" type="text" />
							</li>
							<li><label>下游路口编号：</label> <input id="downcrossid"
								type="text" /></li>
							<li><label>路段方向：</label> <input id="blockdir" type="text" />
							</li>
							<li><label>路段类型：</label> <select id="blocktype"
								name="blocktype">
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