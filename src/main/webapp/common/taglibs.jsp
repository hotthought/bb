<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<c:set var="basePath" value="<%=basePath %>"/>

<!-- 诱导屏服务器ip -->
<c:set var="vmsIp" value="174.16.103.215"/> 
<c:set var="vmsPort" value="13445"/> 

<!-- 单兵服务器参数 begin -->
<!-- 注册服务器IP -->
<c:set var="RegistServerIP" value="174.16.103.204"/>
<!-- 注册服务器端口 -->
<c:set var="RegistServerPort" value="7660"/>
<!-- 报警服务器IP -->
<c:set var="AlarmServerIP" value="174.16.103.204"/>
<!-- 报警服务器端口 -->
<c:set var="AlarmServerPort" value="7332"/>
<!-- 流媒体服务器IP -->
<c:set var="StreamServerIP" value="174.16.103.204"/>
<!-- 流媒体服务器端口 -->
<c:set var="StreamServerPort" value="7554"/>
<!-- 单兵服务器参数 end -->

<!-- FTP参数 begin -->
<!-- FTP服务器IP -->
<c:set var="FxcFtpIP" value="174.16.103.214"/>
<!-- FTP服务器端口 -->
<c:set var="FxcFtpPort" value=""/>
<!-- FTP服务器用户 -->
<c:set var="FxcFtpUserName" value="fxc"/>
<!-- FTP服务器密码 -->
<c:set var="FxcFtpPassWord" value="fxc"/>
<!-- FTP参数 end -->

<!-- 南昌配置 -->

<c:set var="mapJSPath" value="192.168.104.102"/> 

<!-- 公司配置 -->
<%--
<c:set var="mapJSPath" value="192.168.9.170"/>
 --%>
<c:set var="gpsLayerID" value="车辆定位"/>
<c:set var="pdaLayerID" value="单兵定位"/>
<c:set var="videoLayerID" value="视频监控"/>
<c:set var="signalLayerID" value="信号控制"/>
<c:set var="vmsLayerID" value="交通诱导"/>
<c:set var="jcbkLayerID" value="缉查布控"/>
<c:set var="wfzpLayerID" value="违法抓拍"/>
<c:set var="signLayerID" value="标志标牌"/>
<c:set var="parkLayerID" value="停车泊位"/>
<c:set var="constructionLayerID" value="施工占道"/>
<c:set var="guardrailLayerID" value="交通护栏"/>
<c:set var="onewayLayerID" value="单行道路"/>
<c:set var="zdLayerID" value="交警中队"/>
<c:set var="ddLayerID" value="交警大队"/>
<!-- 公司配置 -->
<%--
<c:set var="zdAreaLayerID" value="http://192.168.9.170/ArcGIS/rest/services/AreaMap/MapServer/2"/>
<c:set var="ddAreaLayerID" value="http://192.168.9.170/ArcGIS/rest/services/AreaMap/MapServer/3"/>
 --%>
<!-- 南昌配置 -->

<c:set var="zdAreaLayerID" value="http://192.168.104.102/ArcGIS/rest/services/AreaMap/MapServer/2"/>
<c:set var="ddAreaLayerID" value="http://192.168.104.102/ArcGIS/rest/services/AreaMap/MapServer/3"/>


<c:set var="signalApplicationPath" value="file:///E:/SVN文件/南昌项目/1开发库/4源代码/EJ-ITS-CDS/EJ-ITS-CDS/bin/Debug/EJ-ITS-CDS.exe"/>
<c:set var="vmsApplicationPath" value="file:///E:/SVN文件/南昌项目/1开发库/4源代码/EJ-ITS-CDS/EJ-ITS-CDS/bin/Debug/EJ-ITS-CDS.exe"/>