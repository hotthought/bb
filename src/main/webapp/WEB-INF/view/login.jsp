<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="pageHead.jsp"></jsp:include>
<div id="breadcrumbs">
    	<p><a href="homePage.htm" title="首页">首页</a> &gt; 登录 </p>
    </div>
	
<%--{********************************************}--%>
	
	<div class="mainlistsbox">
		<%--栏目导航--%>
		<div class="bar_nav" style="border-top:0px;">
			<ul >
				<li class="on">用户登录</li>
			</ul>
		</div>
		<div class="nav_content_list">
			<%--路况微博--%>
			<div class="nav_content_item">
		
				<form action="login.htm" method="post">
				<div class="loginbox">
				<ul>
				<li><label>用户名：</label><div autocomplete="off" class="searchBoxBor" id="searchBoxBor" style="padding-left:8px;"><input name="phone" id="phone" type="text" class="inputsearh"></div></li>
		 		<li><label>密码：</label><div autocomplete="off" class="searchBoxBor" id="searchBoxBor" style="padding-left:8px;"><input name="password" id="password" type="password" class="inputsearh"></div></li>
				<li><label>&nbsp;</label><input type="submit" value="登  录" class="sbtn" onclick="return load()">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value="重   置" class="sbtn"></li>
				</ul>
				</div>
				</form>
			</div>
			
		</div>
	</div>

	<script type="text/javascript">
		//if('${msg}'=="cancel"){
		//	location.href="";
		//}
		if('${msg}'=="false"){
			alert("用户名或密码错误，请重试！");
		}
		
		
		function load(){
			var phone = document.getElementById('phone').value;
			var password = document.getElementById('password').value;
			
			if (phone.length == 0) {
				alert("请输入用户名！");
				return false;
			} else if(password.length == 0){
				alert("请输入密码！");
				return false;
			} 
			
			return true;
		}
	</script>

<jsp:include page="pageFoot.jsp"></jsp:include>	