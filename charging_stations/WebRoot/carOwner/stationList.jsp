<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<div id="pageContent" class="common_list_out_layer">
		<div class="operate_layer">
			<ul class="left">
			    <li><a onclick="reloadStationList()" class="reload_btn pngFix">刷新</a></li>
			    <li><a onclick="reloadIdlePileStationList()" class="search_btn pngFix" style="width: 80px">筛选空闲</a></li>
		    </ul>
		</div>
        <table cellpadding="0" cellspacing="0" class="common_list">
             <tr>
                 <th style="width: 20%;">
                     充电站ID
                 </th>
                 <th style="width: 25%;">
                     充电站名称
                 </th>
                 <th style="width: 20%;">
                     充电站地址
                 </th>
                 <th style="width: 10%;">
                     充电站电话
                 </th>
                 <th style="width: 10%;">
                     充电桩数
                 </th>
                 <th style="width: 10%;">
                     空闲充电桩数
                 </th>
                 <th class="borderRightNone" style="width: 5%;">
                     操作
                 </th>
      		</tr>
      		<!-- 数据填充 -->
       		<tbody id="stationList">
             
            </tbody>
        </table>
        <div style=""></div>
	</div>
	
    <script type="text/javascript">
        $(function () {
        	reloadStationList();
        });
        
        function reloadStationList(){
        	$.ajax({
				url: "<%=basePath%>carOwner/getStationShow.action",
			  	data: "",
			  	async: false,
			  	dataType: "json",
			  	success: function(jsonResult){
		        	$("#stationList").html("");
	           		//var jsonResult=eval("(" + result + ")");
		           	for(var i=0; i<jsonResult.length; i++){
		            	var tr_html="<tr>";
		            	tr_html+="<td>";
		            	tr_html+=jsonResult[i].id;
		            	tr_html+="</td>";
		            	tr_html+="<td>";
		            	tr_html+=jsonResult[i].stationName;
		            	tr_html+="</td>";
		            	tr_html+="<td>";
		            	tr_html+=jsonResult[i].address;
		            	tr_html+="</td>";
		            	tr_html+="<td>";
		            	tr_html+=jsonResult[i].phoneNumber;
		            	tr_html+="</td>";
		            	tr_html+="<td>";
		            	tr_html+=jsonResult[i].pileCount;
		            	tr_html+="</td>";
		            	tr_html+="<td>";
		            	tr_html+=jsonResult[i].idlePileCount;
		            	tr_html+="</td>";
		            	tr_html+="<td>";
		            	tr_html+="<button onclick=\"toDetail('"+jsonResult[i].id+"')\">进入</button>";
		            	tr_html+="</td>";
		            	tr_html+="</tr>";
		            	$("#stationList").append(tr_html);
	            	}
			  	}
			});
			
			initUI();
        }
        
        function reloadIdlePileStationList(){
        	$.ajax({
				url: "<%=basePath%>carOwner/getIdlePileStationShow.action",
			  	data: "",
			  	async: false,
			  	dataType: "json",
			  	success: function(jsonResult){
		        	$("#stationList").html("");
	           		//var jsonResult=eval("(" + result + ")");
		           	for(var i=0; i<jsonResult.length; i++){
		            	var tr_html="<tr>";
		            	tr_html+="<td>";
		            	tr_html+=jsonResult[i].id;
		            	tr_html+="</td>";
		            	tr_html+="<td>";
		            	tr_html+=jsonResult[i].stationName;
		            	tr_html+="</td>";
		            	tr_html+="<td>";
		            	tr_html+=jsonResult[i].address;
		            	tr_html+="</td>";
		            	tr_html+="<td>";
		            	tr_html+=jsonResult[i].phoneNumber;
		            	tr_html+="</td>";
		            	tr_html+="<td>";
		            	tr_html+=jsonResult[i].pileCount;
		            	tr_html+="</td>";
		            	tr_html+="<td>";
		            	tr_html+=jsonResult[i].idlePileCount;
		            	tr_html+="</td>";
		            	tr_html+="<td>";
		            	tr_html+="<button onclick=\"toDetail('"+jsonResult[i].id+"')\">进入</button>";
		            	tr_html+="</td>";
		            	tr_html+="</tr>";
		            	$("#stationList").append(tr_html);
	            	}
			  	}
			});
			
			initUI();
        }
        
		function toDetail(stationId){
			$.ajax({
				url: "<%=basePath%>carOwner/chargingPileList.jsp",
			  	data: "",
			  	async: false,
			  	dataType: "html",
			  	success: function(htmlPage){
			  		$("#pageContent").html(htmlPage);
			  		$("#stationId").val(stationId);
			  		reloadPileList();
			  	}
			});
        }
    </script>
</html>