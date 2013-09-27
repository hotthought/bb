<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--底部状态栏:Start-->
<div id="footer">
	<span class="fl">系统版本：1.1.0 beta</span>
	<a href="javascript:void(0)" onclick="showSignalDialog()" style="display: none">信号机</a>
	<a href="javascript:void(0)" onclick="showVms('1018','曙光-保俶北口(面北)','1')" style="display: none">复合屏</a>
	<a href="javascript:void(0)" onclick="showVms('141', '潮王下城体育馆', '0')" style="display: none">文字屏</a>
	<a href="javascript:void(0)" onclick="openMainrightbottom('#bottom_vehicle')" style="display: none">警车分布</a>
	<a href="javascript:void(0)" onclick="openMainrightbottom('#bottom_police')" style="display: none">警员分布</a>
	<a href="${basePath}/trafficguidance/realblockstatus.htm"  style="display: none">实时路况</a>
	<p class="fr">技术支持：银江股份有限公司</p>
</div>
<!--底部状态栏:End-->
