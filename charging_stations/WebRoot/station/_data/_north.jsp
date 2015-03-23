<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
		<div class="header_bg">
            <div class="head_logo"><a href="#"></a></div>
            <div class="head_right pngFix">
                <ul class="clearfix">
                    <li><a href="javascript:logout()" class="exit_btn pngFix">退 出</a></li>
                    <li ><a class="user_info pngFix">亲爱的<span id="_north_username"></span>用户,欢迎您。</a></li>
                </ul>
            </div>
        </div>	
	<div class="system_header_menu clearfix ">
            <ul class="system_menu clearfix">
                <li><a class="home_submenu" href="station/myStation.jsp">我的充电站</a></li>
                <li><a class="form_submenu" href="station/record.jsp">充电记录</a> </li>
                <li><a class="submenu" href="station/user.jsp">用户记录</a></li>
                <li><a class="submenu" href="station/account.jsp">账 户</a></li>
            </ul>
           
            <a href="station/addPile.jsp" class="create_site_btn pngFix" >创建充电桩</a>
            
           <div class="system_searchDiv">
               <div class="system_search">
                   <input id="searchtxt" type="text" class="system_searchTxt" value="搜索所有内容" />
                   <input id="searchbtn" type="button" value="" class="system_searchBtn" /></div>
           </div>
    </div>
		<script type="text/javascript">
            $(function () {
                $.ajax({
					url: "<%=basePath%>station/getStationInformation.action",
				  	data: "",
				  	async: false,
				  	dataType: "json",
				  	success: function(jsonResult){
		           		//var jsonResult=eval("(" + result + ")");
		           		$("#_north_username").html(jsonResult.email);
					}
				});
            });
            
            function logout(){
            	$.ajax({
					url: "<%=basePath%>logout.action",
				  	data: "",
				  	async: false,
				  	dataType: "text",
				  	success: function(textResult){
		      			location.href = "<%=basePath%>home/home.jsp";
					}
				});
            }
        </script>