<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>本捷网络海神之路后台管理系统</title>
    <link rel="shortcut icon" href="${ctx }/images/favicon.ico" type="image/x-icon" />
	<link rel="icon" href="${ctx }/images/favicon.ico" type="image/x-icon" />
    <script type="text/javascript" src="${ctx}/js/jquery.js"></script>
    <script type="text/javascript">
    $('document').ready(function() {
		identifyLogin();
	});
	
	function identifyLogin() {
		var url = "${ctx}/web/admin/identify";
		$.ajax( {
			url : url,
			type : 'post',
			data : {},
			dataType : 'json',
			timeout : 60000,
			error : function(e) {
				alert("连接服务器超时,请稍后再试.");
			},
			success : function(result) {
				if (!result.success) {
					window.location.href='${ctx}/web/admin/index';
				}
			}
		});
	}
	
	function changeTab(url){
	 	document.getElementById('leftFrame').contentWindow.changeTabfunction(url); 
	}
	
    </script>
  </head>
  <frameset rows="88,*,38" cols="*" frameborder="no" border="0" framespacing="0">

      <frame src="${ctx}/web/admin/top" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" title="topFrame" />
  <frameset cols="190,*" frameborder="no" border="0" framespacing="0">
  <frame src="${ctx}/web/admin/left" name="leftFrame" scrolling="yes" noresize="noresize" id="leftFrame" title="leftFrame" />
    <frame src="${ctx}/web/admin/welcome" name="rightFrame" id="rightFrame" title="rightFrame" />
    
  </frameset>
    <frame src="${ctx}/web/admin/bottom" name="bottomFrame" scrolling="No" noresize="noresize" id="bottomFrame" title="bottomFrame" />
</frameset>
<noframes><body>
</body></noframes>
</html>