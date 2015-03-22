<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title></title>
		<link type="text/css" href="${ctx}/style/style.css" rel="stylesheet" />
	</head>
	<body >

	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">首页</a></li>
    </ul>
    </div>
<div class="mainindex">
    
    
    <div class="welinfo">
    <span><img src="${ctx}/images/sun.png" alt="天气" /></span>
    <b>${sessionServerUserInfo.name }(${sessionServerUserInfo.username })早上好，欢迎使用本捷网络海神之路后台管理系统</b>
    </div>
    
  <div class="welinfo">
    <span><img src="${ctx}/images/time.png" alt="时间" /></span>
    <i>您上次登录的时间：<s:date name="#session.loginLast" format="yyyy-MM-dd HH:mm:ss"/>  </i>
  </div>
  <div class="xline"></div>
    
    <div class="uimakerinfo"><b>本捷网络海神之路后台管理系统说明</b></div>
    
  <ul class="umlist">
    <li><a href="#">说明1</a></li>
    <li><a href="#">说明2</a></li>
  </ul>
    
  </div>

</body>
</html>