<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
function gotoPage(p){
	location.href=changeQuery("pageIndex",p);
	return false;
}

function changeQuery(name,value) {
	var url = location.href;
	var reg = new RegExp("(^|)"+ name +"=([^&]*)(|$)");
	var tmp = name + "=" + value;
	if(url.match(reg) != null) {
		return url.replace(eval(reg),tmp);
	} else if(url.match("[\?]")) {
		return url + "&" + tmp;
	} else {
		return url + "?" + tmp;
	}
}
</script>
<c:if test='${page.totalPage > 1}'>
  <div class="page" style="margin:10px 0;">
	<a class="a1">第<span style="color:#0066CC;">${page.currentPage}</span>页/共${page.totalPage}页</a>
	<c:if test='${page.currentPage > 1}'>
		<a href="#" onclick='gotoPage(${page.currentPage - 1})' class="a1">上一页</a>
	</c:if>
	
	<c:if test='${page.currentPage < page.totalPage}'>
		<a href="#" onclick='gotoPage(${page.currentPage + 1})' class="a1">下一页</a>
	</c:if>
  </div>
</c:if>