<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title></title>
		<link type="text/css" href="${ctx}/style/style.css" rel="stylesheet" />
	</head>
	<body style="background:url(${ctx}/images/topbg.gif) repeat-x;">

		<div class="topleft">
			<a href="${ctx}/web/admin/main" target="_parent"><img
					src="${ctx}/images/logo.png" title="系统首页" />
			</a>
		</div>



		<div class="topright">
			<div class="user" style="width: 100px;">
				<i><a href="${ctx }/web/admin/logout" style="padding-left: 35px;" target="_parent" onclick="return confirm('确定注销本次登录？');">注销</a></i>
			</div>
		</div>

		<div class="topright">
			<div class="user" style="width: 100px;">
				<i><a href="${ctx}/web/admin/main" style="padding-left: 35px;" target="_parent">首页</a></i>
			</div>
		</div>

	</body>
</html>