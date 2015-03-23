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
    <link href="<%=basePath%>App_Themes/Global/intro.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">
        $(function () {
        });
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
                        <li><a href="home/home.jsp" class="home_subnav">首页</a></li>
                        <li><a href="home/intro.jsp" class="feature_subnav feature_subnav_sl">功能介绍</a></li>
                        <li><a href="home/guide.jsp" class="subnav">如何操作</a></li>
                        <li><a href="home/wish.jsp" class="subnav">我们的愿景</a></li>
                        <li><a href="home/help.jsp" class="help_subnav pngFix">帮助与支持</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="banner">
           <img src="<%=basePath%>App_Themes/Global/img/home/baneerPic.jpg" /></div>

        </div>
        <div class="Main_page">
        功能介绍页面
        </div>
    	<%@include file="footer.jsp" %>
    </div>
</body>
</html>
