<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>账户</title>
    
    <%@include file="stationImport.jsp" %>
</head>
    <body>
    <div id="north" class="default_head" style="display: block;">
		<%@include file="_data/_north.jsp" %>
    </div>
    <div style="width: 100%">
        <table style="padding: 0; margin: 0; vertical-align: top; width: 100%">
            <tr>
                <td style="width: 201px; vertical-align: top;">
                    <div id="nav" class="easyui-accordion" fit="false" border="false" style="width: 199px;"
                        enabled="false">
                        <!--  导航内容 -->
                    </div>
                </td>
                <td  class="space">
                       <!--当前页面路径-->
                    <div class="location_layer clearfix">
                        <div id="pageTitle" class="pageTitle">
                            <span></span>
                        </div>
                        <div id="pagePath" class="pagePath">
                            
                        </div>
                    </div>
                    <div class="account_layer">
                        <div class="common_list_out_layer">
                            <div class="account_content">
                                <div class="inf_modular">
                                    <div class="titleDiv clearfix">
                                        <div class="title_L">
                                            账户基本信息
                                        </div>
                                        <div class="title_R">
                                            <a class="drop_down_icon pngFix"></a>
                                        </div>
                                    </div>
                                    <div class="content">
                                        <div class="content_main">
                                            <div class="content_L">
                                                <span>充电站ID:</span><span id="id"></span><br />
                                                <span>电子邮件:</span><span id="email"></span><br />
                                                <span>电话号码:</span><span id="phoneNumber"></span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class=""></div>
                                <div class="inf_modular">
                                    <div class="titleDiv clearfix">
                                        <div class="title_L">
                                            账户余额
                                        </div>
                                        <div class="title_R">
                                            <a class="drop_down_icon pngFix"></a>
                                        </div>
                                    </div>
                                    <div class="content">
                                        <div class="content_main">
                                            <div class="content_L">
                                                <span>帐户余额:</span><span id="balance"></span><br />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
        </table>
    </div>
        <script type="text/javascript">
            $(function () {
                $(".drop_down_icon").click(function () {
                    if ($(this).parent().parent().next().is(":hidden"))
                        $(this).removeClass("contraction_icon");
                    else
                        $(this).addClass("contraction_icon");
                    $(this).parent().parent().next().slideToggle();
                });
            	//框架高度调整
                if (window.parent.adjustHeiht) {
                    var height = $(document).height() + 20;
                    window.parent.adjustHeiht(height);
                }
                $.ajax({
					url: "<%=basePath%>station/getStationInformation.action",
				  	data: "",
				  	async: false,
				  	dataType: "json",
				  	success: function(jsonResult){
		           		//var jsonResult=eval("(" + result + ")");
		           		$("#id").html(jsonResult.id);
		           		$("#email").html(jsonResult.email);
		           		$("#phoneNumber").html(jsonResult.phoneNumber);
		           		$("#balance").html(jsonResult.balance);
					}
				});
            });
        </script>
    </body>
</html>

