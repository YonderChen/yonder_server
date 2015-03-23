<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>">
    <title>预约充电验证</title>
    <%@include file="chargeImport.jsp" %>
    <link href="App_Themes/Global/login_register.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">
        $(function () {
            $(".feature_subnav").parent().hover(function () {
                $(".menu_ground").slideDown();
            }, function () {
                $(".menu_ground").hide();
            });

            commonClick($(".ft_sidebar dd a"), "warning_sl");
        });
    </script>
</head>
<body class="MainBody">
    <div class="login_outlayer">
        <div class="header">
            <div class="headerContent clearfix">
                <div class="logo_header pngFix">
                </div>
                <div class="nav_header">
                </div>
            </div>
        </div>
        <div class="login_bg clearfix">
            <div class="login_content">
                <table cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="title">
                            登录名：
                        </td>
                        <td class="content" >
                            <input id="userNameBtn" type="text" class="login_txt" />
                        </td>
                    </tr>
                    <tr>
                        <td class="title">
                            密 码：
                        </td>
                        <td class="content" >
                            <input id="pswBtn" type="password" class="login_pwd" />
                        </td>
                    </tr>
                    <tr id="checkNumber_tr">
                        <td class="title">
                            预约码：
                        </td>
                        <td class="content clearfix" >
                            <input id=checkNumber type="text" class="check_txt" />
                        </td>

                    </tr>
                    <tr>
                        <td class="title">
                        </td>
                        <td class="content" >
                            <input id="loginBtn" type="button" value="充电" class="login_btn2" />
                        </td>

                    </tr>
                </table>
            </div>
        </div>
        <div class="footer" align="center">
            <div class="footerContent" align="center">
                ChenYongduo 2013-2015 --------- 电动汽车充电网站
                <br />
                Yonder Chen(陈永铎) 集美大学毕业设计
            </div>
        </div>
    </div>
</body>
<script type="text/javascript">
     $("#loginBtn").click(function(){
     	var loginName=$("#userNameBtn").val();
     	var loginKey=$("#pswBtn").val();
     	var checkNumber=$("#checkNumber").val();
     	reservedLogin(loginName, loginKey, checkNumber);
     	$("#userNameBtn").val("");
     	$("#pswBtn").val("");
     	$("#checkNumber").val("");
     });

     function reservedLogin(loginName, loginKey, checkNumber){
     	$.ajax({
       		type: "POST",
			url: "<%=basePath%>charge/reservedLogin.action",
			data: {loginName : loginName, loginKey : loginKey, checkNumber : checkNumber},
			async: false,
			dataType: "json",
			success: function(jsonResult){
				var msg="";
				if(jsonResult){
					if(jsonResult.loginSuccess=="true"){
						if(jsonResult.checkSuccess=="true"){
							msg+="验证通过，可以开始充电";
							msg+="\n\n订单id："+jsonResult.id;
							msg+="\n\n预约时间："+jsonResult.time;
							msg+="\n\n充电开始时间："+jsonResult.chargingBegin;
							msg+="\n\n充电桩别名："+jsonResult.pileName;
							msg+="\n\n充电类型："
							msg+=jsonResult.type==0?"快充":"慢充";
						}
						else{
							msg=jsonResult.message;
						}
					}
					else{
						msg="登录失败！";
					}
				}
				alert(msg);
	      		//var jsonResult=eval("(" + result + ")");
			}
		});
     }
     $("#reserved").change(function(){
		if($("#reserved").attr("checked")){
			$("#checkNumber_tr").css("display","table-row");
		}
		else{
			$("#checkNumber_tr").css("display","none");
		}
	});
     
        document.onkeydown=function(event){
	        var e = event || window.event || arguments.callee.caller.arguments[0];        
	       	if(e && e.keyCode==13){ // enter 键
	         	$("#loginBtn").click();
	     	}
		}; 
</script>
</html>

