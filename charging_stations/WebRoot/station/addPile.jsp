<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>添加充电桩</title>
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
                                            充电桩信息
                                        </div>
                                        <div class="title_R">
                                            <a class="drop_down_icon pngFix "></a>
                                        </div>
                                    </div>
                                    <div class="content">
                                        <div class="content_main">
                                            <div class="add_content">
                                                <div class="add_option">
                                                    <table cellpadding="0" cellspacing="0" class="add_site_tb" style="margin: 17px 0 11px 0;">
                                                        <tr>
                                                            <th>
                                                                充电桩别名:
                                                            </th>
                                                            <td class="add_site_padding td_height">
                                                                <input type="text" id="pile_name" name="pile_name" class="txtCss_site2" />
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <th>
                                                            </th>
                                                        </tr>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="button_site">
                                    <input id="applicationBtn" type="button" value="" onclick="save()" class="saveBtn" />&nbsp;
                                    <input id="resetBtn" type="button" value="" onclick="reset()" class="resetBtn" />
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
                
                document.onkeydown=function(event){
		            var e = event || window.event || arguments.callee.caller.arguments[0];
		            if(e && e.keyCode==27){ // 按 Esc 
		            	reset();
		           	}            
		           	if(e && e.keyCode==13){ // enter 键
		             	save();
		         	}
		        }; 
            });
            
            
	        
            function save(){
            	$.ajax({
            		type: "POST",
					url: "<%=basePath%>station/addPile.action",
				  	data: {pile_name : $("#pile_name").val()},
				  	async: false,
				  	dataType: "json",
				  	success: function(jsonResult){
		           		//var jsonResult=eval("(" + result + ")");
		           		alert(jsonResult.message);
		           		if(jsonResult.isSuccess=="true"){
		           			location.href = "<%=basePath%>station/myStation.jsp";
		           		}
					}
				});
            }
            
            function reset(){
            	$("#pile_id").val("");
            	$("#pile_name").val("");
            }
        </script>
    </body>
</html>
