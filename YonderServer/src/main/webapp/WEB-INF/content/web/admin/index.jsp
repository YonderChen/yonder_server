<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>本捷网络海神之路后台管理系统</title>
	<link rel="shortcut icon" href="${ctx }/images/favicon.ico" type="image/x-icon" />
	<link rel="icon" href="${ctx }/images/favicon.ico" type="image/x-icon" />
	<link type="text/css" href="${ctx}/style/style.css" rel="stylesheet"/>
	<script type="text/javascript" src="${ctx}/js/base.js"></script>
  	<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
   	<script type="text/javascript" src="${ctx}/js/jquery.cookie.js"></script>
   	<script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>   
   	<script type="text/javascript" src="${ctx}/js/cloud.js"></script>   
	<script type="text/javascript">
	function login() {
		if ($("#username").val().trim() == "") {
			alert("用户名不能为空.");
			$("#username").select();
			return;
		}
		if ($("#password").val().trim() == "") {
			alert("密码不能为空.");
			$("#password").select();
			return;
		}
		if ($("#code").val().trim() == "") {
			alert("验证码不能为空.");
			$("#code").select();
			return;
		}
		$("#loginBtn").button('loading');
		var url = "${ctx}/web/admin/login";
		$.ajax({
			url:url,
			type:'post',
			data:{
				username:$("#username").val().trim(),
				password:$("#password").val().trim(),
				code:$("#code").val().trim()
			},
			dataType:'json',
			timeout:60000,
			error: function(e) {
				$("#loginBtn").button('reset');
				alert("连接服务器超时,请稍后再试.");
			},
			success: function(result){
				if (result.success) {
					if ($("#rememberMe").prop("checked") == true) {
						var username = $("#username").val().trim();
        				var password = $("#password").val().trim();
        				$.cookie("rmbUser", "true", { expires: 7 }); // 存储一个带7天期限的 cookie
       	 				$.cookie("username", username, { expires: 7 }); // 存储一个带7天期限的 cookie
        				$.cookie("password", password, { expires: 7 }); // 存储一个带7天期限的 cookie
    				} else {
    					$.cookie("rmbUser", "false", { expires: -1 });
        				$.cookie("username", null, { expires: -1 });
        				$.cookie("password", null, { expires: -1 });
    				}
					window.location.href = result.redirectUrl;
				} else {
					$("#loginBtn").button('reset');
					changeCode();
					alert(result.msg);
				}
			}
		});
	}
	
	function identifyLogin() {
		var url = "${ctx}/web/admin/identify";
		$.ajax({
			url:url,
			type:'post',
			data:{
				
			},
			dataType:'json',
			timeout:60000,
			error: function(e) {alertError("连接服务器超时,请稍后再试.");},
			success: function(result){
				if (result.success) {
					window.location.href=result.redirectUrl;
				}
			}
		});
	}
	
	$('document').ready(function() {
		$('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
		$(window).resize(function(){
    		$('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
    	}); 
		identifyLogin();
		if ($.cookie("rmbUser") == "true") {
        	$("#rememberMe").prop("checked", true);
       	 	$("#username").val($.cookie("username"));
        	$("#password").val($.cookie("password"));
    	}
	});
	
	function changeCode() {
		document.getElementById("img").src="${ctx}/web/admin/code?num="+Math.random();
	}
	
	function cancelAdd() {
		$("#addDiv").fadeOut(100);
	}
	
	function addInput() {
		$("#addDiv").fadeIn(100);
	}
	
	</script>
  </head>
  <body style="background-color:#1c77ac; background-image:url(${ctx }/images/light.png); background-repeat:no-repeat; background-position:center top; overflow:hidden;">
  
	<div id="mainBody">
      <div id="cloud1" class="cloud"></div>
      <div id="cloud2" class="cloud"></div>
    </div>  


<div class="logintop">    
    <span>欢迎登录本捷网络海神之路后台管理系统
</span>    
    <ul>
    <li><a href="#">回首页</a></li>
    <li><a href="#">帮助</a></li>
    <li><a href="#">关于</a></li>
    </ul>    
    </div>
    
    <div class="loginbody">
    
    <span class="systemlogo"></span> 
       
    <div class="loginbox">
    <form action="" method="post" onkeydown="if(event.keyCode==13) {login();}">
    <ul>
    <li><input id="username" name="username" type="text" class="loginuser" value="" placeholder="用户名" maxlength="50"/></li>
    <li><input id="password" name="password" type="password" class="loginpwd" value="" placeholder="密码" maxlength="50" autocomplete="off"/></li>
    <li><input id="code" name="code" type="text" class="loginpwd1" value="" placeholder="验证码" maxlength="5"/>
    	<img src="${ctx}/web/admin/code" style="vertical-align: middle;padding-left:10px;cursor:pointer;" id="img" onclick="changeCode();" title="换一张"/>
    	</li>
    <li><input id="loginBtn" type="button" class="loginbtn" value="登录"  onclick="login();"  data-toggle="button" data-loading-text="登录"/><label><input name="rememberMe" id="rememberMe" type="checkbox"/>记住密码</label>
    	<label><a href="javascript:addInput();">忘记密码？</a></label></li>
    </ul>
    </form>
    
    </div>
    
    <div class="tip1" id="addDiv">
    	<div class="tiptop1"><span>忘记密码</span><a onclick="cancelAdd();"></a></div>
        
      <div class="tipinfo1">
      
      <table width="100%" border="1">
  <tr>
    <td>
    请联系本捷网络科技有限公司技术人员，电话：5930820
    </td>
    </tr>
</table>
      </div>
        
        <div class="tipbtn1">
        <input id="cancelBtn" type="button"  class="cancel" value="关闭" onclick="cancelAdd();" data-toggle="button" data-loading-text="关闭"/>
        </div>
    
    </div>
    
    </div>
    
    
    
    <div class="loginbm">Copyright©2014本捷网络科技有限公司版权所有 </div>
    
</body>
</html>