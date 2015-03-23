<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>充电站</title>
    <%@include file="adminImport.jsp" %>
</head>
<body onunload="GUnload()">
    <div id="north" class="default_head" style="display: block;">
		<%@include file="_data/_north.jsp" %>
    </div>
    <div class="page_out_layer">
        <table style="padding: 0; margin: 0; vertical-align: top; width: 100%">
            <tr>
                <td style="width: 201px; vertical-align: top;" >
                    <div id="nav" class="easyui-accordion" fit="false" border="false" style="width: 199px;"
                        enabled="false">
                        <!--  导航内容 -->
                    </div>
                </td>
                <td class="space">
                    <!--当前页面路径-->
                    <div class="location_layer clearfix">
                        <div id="pageTitle" class="pageTitle">
                            <span></span>
                        </div>
                        <div id="pagePath" class="pagePath">
                            
                        </div>
                    </div>
                    <div id="pageContent"></div>
                </td>
            </tr>
        </table>
    </div>
    <script type="text/javascript">
        $(function () {
			var menuid = "<%=request.getParameter("menuid")%>";
			if (menuid == null || menuid == "null" || menuid.length <= 0) {
				menuid = "stationList";
			}
			loadPageContent("admin", menuid);
        });
    </script>
</body>
</html>
