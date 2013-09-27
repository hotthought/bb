<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!--页尾信息-->
 <div class="stockBox" style="display:none;height:0;">
					<p class='stock_text'></p><p class='stock_text hidden'></p>
</div>  
	<div class="footbox">
		<p class="font14">&#169;南昌交管局 版权所有&nbsp;&nbsp;技术支持：银江股份有限公司</p>
		<p class="font14">电话：0791-86268399 &nbsp;&nbsp; 官方微信：ncjjbmw</p>
    </div><!-- footbox end -->
</div><!-- mainpage end -->

<!--alert push-->
<script src="js/mvc_20120803.js"></script>
<script src="js/24609.js"></script>
<script type="text/javascript">
	function theStory(){
		window.history.length<=1?window.location.href="default.html":window.history.go(-1)
		}
	function b(){
			goTop=document.getElementById("goTop");
			if(goTop!=null){
				goTop.onclick=function(){window.scrollTo(0,0)}
				}
		}
	window.addEventListener("load",function(){b()},false)
</script>
</body>
</html>
	



