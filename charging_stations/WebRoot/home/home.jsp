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
    <script type="text/javascript">
        var Num = 1;
        $(function () {
            commonHover($(".loginBtns"), "loginBtns_pressed");
            commonClick($(".rightTab li a"), "a_selected");
            $(".feature_subnav").parent().hover(function () {
                $(".menu_ground").slideDown();
            }, function () {
                $(".menu_ground").hide();
            });

            $(".rightTd .expansion").click(function () {
                if ($(this).hasClass("fold")) {
                    $(this).removeClass("fold");
                    $(this).parent().prev().find("ul:eq(1)").slideDown();
                } else {
                    $(this).addClass("fold");
                    $(this).parent().prev().find("ul:eq(1)").slideUp();
                }
            });

            var intervalTime;
            $(".imgMenu li").each(function (i) {
                $(this).bind('mouseenter', function () {
                    clearInterval(intervalTime);

                    Num = i;
                    Interval();
                }).mouseleave(function () {
                    intervalTime = setInterval(Interval, 5000);
                });
            });

            intervalTime = setInterval(Interval, 5000);
        });
        function Interval() {
            if (Num > 4) Num = 0;
            $(".LiSelectd").removeClass("LiSelectd");
            $(".imgMenu li").eq(Num).addClass("LiSelectd");
            $(".imgDiv img").hide();
            $(".imgDiv img").eq(Num).fadeIn("fast");

            Num += 1;
        }
    </script>
</head>
<body class="MainBody">
    <div class="ft_wrap">
        <div class="header">
            <div class="headerContent clearfix">
                <div class="logo_header pngFix">
                </div>
                <div class="nav_header">
                    <ul class="nav clearfix pngFix">
                        <li><a href="home/home.jsp" class="home_subnav home_subnav_sl">首页</a></li>
                        <li><a href="home/intro.jsp" class="feature_subnav">功能介绍</a></li>
                        <li><a href="home/guide.jsp" class="subnav">如何操作</a></li>
                        <li><a href="home/wish.jsp" class="subnav">我们的愿景</a></li>
                        <li><a href="home/help.jsp" class="help_subnav pngFix">帮助与支持</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="Main_page">
            <div class="main_top clearfix">
                <div class="leftAdMenu">
                    <div class="imgDiv">
                        <img alt="充电站全貌" src="App_Themes/Global/img/home/flash1.jpg" class="pngFix" />
                        <img alt="奥迪充电汽车" src="App_Themes/Global/img/home/flash2.jpg" style="display: none;" class=" pngFix" />
                        <img alt="太阳能充电站" src="App_Themes/Global/img/home/flash3.jpg" style="display: none;" class=" pngFix" />
                        <img alt="给汽车充电" src="App_Themes/Global/img/home/flash4.jpg" style="display: none;" class=" pngFix" />
                        <img alt="概念充电汽车" src="App_Themes/Global/img/home/flash5.jpg" style="display: none;" class=" pngFix" />
                    </div>
                    <ul class="imgMenu pngFix">
                        <li class="LiSelectd pngFix"><a>充电站全貌</a></li>
                        <li class="pngFix"><a>奥迪充电汽车</a></li>
                        <li class="pngFix"><a>太阳能充电站</a></li>
                        <li class="pngFix"><a>给汽车充电</a></li>
                        <li class="pngFix"><a class="bgNone">概念充电汽车</a></li>
                    </ul>
                </div>
                <div class="rightLogin">
                    <div class="LoginBoxDiv">
                        <table cellpadding="0" cellspacing="0" class="Tb_Common login_Tb">
                            <tr>
                                <td class="td_right" style="width: 25%;">
                                    登录名：
                                </td>
                                <td colspan="2">
                                    <input id="userNameBtn" type="text" />
                                </td>
                            </tr>
                            <tr>
                                <td class="td_right">
                                    密&nbsp 码：
                                </td>
                                <td colspan="2">
                                    <input id="pswBtn" type="password" />
                                </td>
                            </tr>
                            <tr>
                                <td class="td_right" style="width: 24%;">
                                    类&nbsp 型：
                                </td>
                                <td>
                                    <select id="loginType">
                                    	<option value="0" selected="selected">车主用户登录</option>
                                    	<option value="1">车站用户登录</option>
                                    	<option value="2">管理员登录</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3" class="RememberMeTd">
									<a href="javascript:void(0)" class="loginLink forgetPSW">忘记密码</a>
									<a href="home/register.jsp" class="loginLink forgetPSW">新用户注册</a>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3" class="loginBtnTd">
                                    <input id="loginBtn" type="button" class="loginBtns" value="登  陆" />
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
            <div class="main_center">
               <hr>
            </div>
            <div class="main_bottom">
                <div class="title clearfix">
                    <div class="leftTitle">
                        <div class="shadowRight">
                            充电站分布
                       	</div>
                    </div>
                    <div class="rightTab ">
                        <ul class="clearfix">
                            <li><a class="a_selected">全球充电站点分布示意图</a></li>
                        </ul>
                    </div>
                </div>
                <div class="bottom_content">
                    <div class="map">
                        <img alt="" src="App_Themes/Global/img/home/map.png" /></div>
                </div>
            </div>
        </div>
        
    	<%@include file="footer.jsp" %>
    </div>
</body>

<script type="text/javascript">
     $("#loginBtn").click(function(){
     	var actionUrl;
     	var loginType=$("#loginType option:selected").val();
     	if(loginType==0){
     		actionUrl="<%=basePath%>carOwnerLogin.action";
     	}
     	else if(loginType==1){
     		actionUrl="<%=basePath%>stationLogin.action";
     	}
     	else{
     		actionUrl="<%=basePath%>sysAdminLogin.action";
     	}
     	var loginName=$("#userNameBtn").val();
     	var loginKey=$("#pswBtn").val();
     	login(actionUrl, loginName, loginKey, loginType);
     });
     
     function login(actionUrl, loginName, loginKey, loginType){
     	$.ajax({
       		type: "POST",
			url: actionUrl,
			data: {loginName : loginName, loginKey : loginKey},
			async: false,
			dataType: "text",
			success: function(textResult){
	      		//var jsonResult=eval("(" + result + ")");
	      		if(textResult=="0"){
	      	     	if(loginType==0){
		      			location.href = "<%=basePath%>carOwner/searchStation.jsp";
	      	     	}
	      	     	else if(loginType==1){
		      			location.href = "<%=basePath%>station/myStation.jsp";
	      	     	}
	      	     	else{
		      			location.href = "<%=basePath%>admin/station.jsp";
	      	     	}
	         	}
	         	else if(textResult=="1"){
	      			$("#pswBtn").val("");
	      			alert("密码错误！请重试。");
	      			$("#pswBtn").focus();
	         	}
	         	else if(textResult=="2"){
	      			$("#userNameBtn").val("");
	      			$("#pswBtn").val("");
	         		alert("用户不存在");
	      			$("#userNameBtn").focus();
	         	}
			}
		});
     }
     
     
        document.onkeydown=function(event){
	        var e = event || window.event || arguments.callee.caller.arguments[0];        
	       	if(e && e.keyCode==13){ // enter 键
	         	$("#loginBtn").click();
	     	}
		}; 
</script>
    
</html>
