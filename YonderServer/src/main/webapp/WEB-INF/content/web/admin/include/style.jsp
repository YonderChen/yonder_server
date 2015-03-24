<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<link type="text/css" href="${ctx}/style/bootstrap.min.css" rel="stylesheet">
<link type="text/css" href="${ctx}/style/bootstrap-responsive.min.css" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="${ctx}/style/font-awesome.min.css">
	<!--[if IE 7]>
    <link type="text/css" rel="stylesheet" href="${ctx}/style/font-awesome-ie7.min.css">
    <![endif]-->
	<!--[if lt IE 9]><link href="${ctx}/style/ie.css" type="text/css" rel="stylesheet" /><![endif]-->
	<link type="text/css" href="${ctx}/style/style.css?7" rel="stylesheet"/>
	<link type="text/css" href="${ctx}/style/yonder.css" rel="stylesheet"/>
	<script type="text/javascript" src="${ctx}/js/base.js"></script>
	<script type="text/javascript" src="${ctx}/js/check.js"></script>
  	<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
  	<script type="text/javascript" src="${ctx}/js/swfupload.js"></script>
	<script type="text/javascript" src="${ctx}/js/swfupload.queue.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.swfupload.js"></script>
  	<script type="text/javascript" src="${ctx}/js/jquery.idTabs.min.js"></script>
   	<script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>   
   	<script type="text/javascript" src="${ctx}/js/calendar/WdatePicker.js"></script>
	<script type="text/javascript">
$('document').ready(function() {
   	var funs = preArray();
	for (var i = 0; i < funs.length; i++) {
		funs[i]();
	}
});

function isOutTime(result) {
	if (result.indexOf("win.location.href='${ctx}/web/admin/index'") > 0) {
		alert("登录超时,请重新登录.");
		var win = window;
		while (win != window.parent) {
			win = window.parent;
		}
		win.location.href='${ctx}/web/admin/index';
		return true;
	}
	return false;
}

function preArray(){
	if(typeof window.preFunArray != "function"){
		return [];
	}else{
		return window.preFunArray.call(this);
	}
}
</script>