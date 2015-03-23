<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>">
    <title>主页</title>
    <%@include file="homeImport.jsp" %>
    <link href="<%=basePath%>App_Themes/Global/login_register.css" rel="stylesheet" type="text/css" />
    <!--[if IE 6]><script type="text/javascript" src="Common/Scripts/DD_belatedPNG.js"></script><![endif]-->
    <script type="text/javascript">
        $(function () {

        });
    </script>
</head>
<body>
    <div class="ft_wrap">
        <div class="header">
            <div class="headerContent clearfix">
                <div class="logo_header pngFix">
                </div>
                <div class="nav_header">
                    <ul class="nav clearfix pngFix">
                        <li ><a href="home/home.jsp" class="home_subnav home_subnav">首页</a></li>
                        <li><a href="javascript:void(0)" class="feature_subnav">功能介绍</a></li>
                        <li><a href="javascript:void(0)" class="subnav">如何操作</a></li>
                        <li><a href="javascript:void(0)" class="subnav">我们的愿景</a></li>
                        <li><a href="javascript:void(0)" class="help_subnav pngFix">帮助与支持</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="banner">
            <img src="App_Themes/Global/img/register/baneerPic.jpg" /></div>
        <div class="register_content" id="register_content">
            <div class="top_title">
                <h5>
                    填写注册信息
                </h5>
            </div>
            <table>
                <tr>
                    <td class="title">
                       <span class="remark"> *</span> <span class="font_weight"> 选择用户类型:</span>
                    </td>
                    <td class="content">
                       <select id="registerType">
		                   	<option value="0" selected="selected">车主用户</option>
		                   	<option value="1">车站用户</option>
                       </select>
                    </td>
                </tr>
                <tr>
                    <td class="title">
                       <span class="remark"> *</span> <span class="font_weight"> Email:</span>
                    </td>
                    <td class="content">
                        <input id="email" type="text" class="common_textbox" /><label id="email_msg"></label>
                    </td>
                </tr>
                <tr>
                    <td class="notice_title">
                    </td>
                    <td class="notice">
                    Email将是您的登录帐号，同时也接收系统通知或者车站/车主邮件。
                    </td>
                </tr>
                <tr>
                    <td class="title">
                       <span class="remark"> *</span><span class="font_weight"> 密码:</span>
                    </td>
                    <td class="content">
                    	<input id="loginKey" type="password" class="common_textbox" />
                    </td>
                </tr>
                 <tr>
                    <td class="title">
                       <span class="remark"> *</span> <span class="font_weight"> 重复密码:</span>
                    </td>
                    <td class="content">
                    	<input id="reLoginKey" type="password" class="common_textbox" />
                    </td>
                </tr>
                <tr id="tr_realName">
                    <td class="title">
                        <span class="remark"> *</span><span class="font_weight"> 真实姓名:</span>
                    </td>
                    <td class="content">
                    	<input id="realName" type="text" class="common_textbox" />
                    </td>
                </tr>
                <tr id="tr_stationName" style="display: none;">
                    <td class="title">
                        <span class="remark"> *</span><span class="font_weight"> 充电站名称:</span>
                    </td>
                    <td class="content">
                    	<input id="stationName" type="text" class="common_textbox" />
                    </td>
                </tr>
                <tr id="tr_phoneNumber" style="display: none;">
                    <td class="title">
                        <span class="remark"> *</span><span class="font_weight"> 电话号码:</span>
                    </td>
                    <td class="content">
                    	<input id="phoneNumber" type="text" class="common_textbox" />
                    </td>
                </tr>
                <tr id="tr_address" style="display: none;">
                    <td class="title">
                        <span class="remark"> *</span><span class="font_weight"> 地址:</span>
                    </td>
                    <td class="content">
                    	<input id="address" type="text" class="common_textbox" />
                    </td>
                </tr>
                <tr id="tr_latitude" style="display: none;">
                    <td class="title">
                        <span class="remark"> *</span><span class="font_weight"> 纬度:</span>
                    </td>
                    <td class="content">
                    	<input id="latitude" type="text" class="common_textbox" />
                    </td>
                </tr>
                <tr id="tr_longitude" style="display: none;">
                    <td class="title">
                        <span class="remark"> *</span><span class="font_weight"> 经度:</span>
                    </td>
                    <td class="content">
                    	<input id="longitude" type="text" class="common_textbox" />
                    </td>
                </tr>
              
                <tr>
                  <td colspan="2">
                    <div class="foot_line">
                        <input id="registerButton" type="button" onclick="register()" value="" class="register_btn" />
                    </div>
                  </td>
                </tr>
            </table>
        </div>
    	<%@include file="footer.jsp" %>
    </div>
</body>
<script type="text/javascript">
    $("#registerType").change(function(){
     	var loginType=$("#registerType option:selected").val();
     	if(loginType==0){
     		$("#tr_realName").css("display", "table-row");
     		$("#tr_stationName").css("display", "none");
     		$("#tr_phoneNumber").css("display", "none");
     		$("#tr_address").css("display", "none");
     		$("#tr_latitude").css("display", "none");
     		$("#tr_longitude").css("display", "none");
     		$("#register_content").css("height", "450px");
     	}
     	else{
     		$("#tr_realName").css("display", "none");
     		$("#tr_stationName").css("display", "table-row");
     		$("#tr_phoneNumber").css("display", "table-row");
     		$("#tr_address").css("display", "table-row");
     		$("#tr_latitude").css("display", "table-row");
     		$("#tr_longitude").css("display", "table-row");
     		$("#register_content").css("height", "650px");
     	}     	
    });
    
    $("#email").blur(function(){
   		var email=$("#email").val();
   		var loginType=$("#registerType option:selected").val()
   		$.ajax({
	       		type: "POST",
				url: "<%=basePath%>checkEmail.action",
				data: {email : email, loginType : loginType},
				async: false,
				dataType: "text",
				success: function(textResult){
					if(textResult=="true"){
						$("#email_msg").html("可用的邮件地址。");
						$("#email_msg").css("color","green");
					}
					else{
						alert("邮件地址已注册！");
						$("#email_msg").html("该邮件地址已被注册！");
						$("#email_msg").css("color","red");
						$("#email").focus();
					}
				}
   		});
    });
    
   	function register(){
       	var loginType=$("#registerType option:selected").val();
       	if(loginType==0){
       		var email=$("#email").val();
       		var loginKey=$("#loginKey").val();
       		var realName=$("#realName").val();
       		$.ajax({
  	       		type: "POST",
  				url: "<%=basePath%>registerCarOwner.action",
  				data: {email : email, loginKey : loginKey, realName : realName},
  				async: false,
  				dataType: "text",
  				success: function(textResult){
  		      		//var jsonResult=eval("(" + result + ")");
  		      		if(textResult=="true"){
  		      			alert("注册成功！");
  		      			location.href = "<%=basePath%>home/home.jsp";
  		         	}
  		         	else{
  		      			alert("注册失败！");
  		         	}
  				}
  			});
       	}
       	else{
       		var email=$("#email").val();
       		var loginKey=$("#loginKey").val();
       		var stationName=$("#stationName").val();
       		var phoneNumber=$("#phoneNumber").val();
       		var address=$("#address").val();
       		var latitude=$("#latitude").val();
       		var longitude=$("#longitude").val();
       		$.ajax({
  	       		type: "POST",
  				url: "<%=basePath%>registerStation.action",
  				data: {email : email, loginKey : loginKey, stationName : stationName, 
  					phoneNumber : phoneNumber, address : address, latitude : latitude, longitude : longitude},
  				async: false,
  				dataType: "text",
  				success: function(textResult){
  		      		//var jsonResult=eval("(" + result + ")");
  		      		if(textResult=="true"){
  		      			alert("注册成功！");
  		      			location.href = "<%=basePath%>home/home.jsp";
  		         	}
  		         	else{
  		      			alert("注册失败！");
  		         	}
  				}
  			});
       	}
   	}
   	
   	document.onkeydown=function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];        
       	if(e && e.keyCode==13){ // enter 键
         	$("#registerButton").click();
     	}
	};
    
</script>
</html>
