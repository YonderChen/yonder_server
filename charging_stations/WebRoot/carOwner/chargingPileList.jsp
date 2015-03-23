<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
		<input type="hidden" id="stationId" value="" />
		<div class="operate_layer">
			<ul class="left">
			    <li><a onclick="back()" class="back_btn pngFix">返回</a></li>
			    <li><a onclick="reloadPileList()" class="reload_btn pngFix">刷新</a></li>
		    </ul>
		</div>
        <table cellpadding="0" cellspacing="0" class="common_list">
             <tr>
                 <th style="width: 30%;">
                     充电桩ID
                 </th>
                 <th style="width: 35%;">
                     充电桩别名
                 </th>
                 <th style="width: 15%;">
                     充电桩状态
                 </th>
                 <th class="borderRightNone" style="width: 15%;">
                     操作
                 </th>
      		</tr>
      		<!-- 数据填充 -->
       		<tbody id="pileList">
             
            </tbody>
        </table>
	
    <script type="text/javascript">
        $(function () {
        	reloadPileList();
        });
        
        function reloadPileList(){
        	$.ajax({
				url: "<%=basePath%>carOwner/getChargingPileList.action",
			  	data: {stationId : $("#stationId").val()},
			  	async: false,
			  	dataType: "json",
			  	success: function(jsonResult){
		        	$("#pileList").html("");
	           		//var jsonResult=eval("(" + result + ")");
		           	for(var i=0; i<jsonResult.length; i++){
		            	var tr_html="<tr>";
		            	tr_html+="<td>";
		            	tr_html+=jsonResult[i].id;
		            	tr_html+="</td>";
		            	tr_html+="<td>";
		            	tr_html+=jsonResult[i].pileName;
		            	tr_html+="</td>";
		            	tr_html+="<td>";
		            	if(jsonResult[i].status=="0"){
		            		tr_html+="空闲";
		            	}
		            	else if(jsonResult[i].status=="1"){
		            		tr_html+="<a href=\"javascript:void(0)\" onclick=\"chargingDetial('"+jsonResult[i].id+"')\">预约中</a>";
		            	}
		            	else if(jsonResult[i].status=="2"){
		            		tr_html+="<a href=\"javascript:void(0)\" onclick=\"chargingDetial('"+jsonResult[i].id+"')\">充电中</a>";
		            	}
		            	else{
		            		tr_html+="故障";
		            	}
		            	tr_html+="</td>";
		            	tr_html+="<td>";
		            	tr_html+="<select id='"+jsonResult[i].id+"_type'>";
		            	tr_html+="<option value='0'>快充</option>";
		            	tr_html+="<option value='1'>慢充</option>";
		            	tr_html+="</select>";
		            	tr_html+="<button onclick=\"reserve('"+jsonResult[i].id+"')\""
		            	if(jsonResult[i].status!="0"){
		            		tr_html+=" disabled=\"disabled\"";
		            	}
		            	tr_html+=">预约</button>";
		            	tr_html+="</td>";
		            	tr_html+="</tr>";
		            	$("#pileList").append(tr_html);
	            	}
			  	}
			});
			
			initUI();
        }
        
        function chargingDetial(id){
        	$.ajax({
				url: "<%=basePath%>carOwner/chargingDetial.action",
			  	data: {pileId : id},
			  	async: false,
			  	dataType: "json",
			  	success: function(jsonResult){
			  		var message="";
			  		if(jsonResult==null||jsonResult=="null"){
			  			message="结果异常，请刷新后重试！";
			  		}
			  		else{
				  		message+="充电类型：";
				  		message+=jsonResult.type;
				  		message+="\n\n预约时间：";
				  		message+=jsonResult.beginTime;
				  		message+="\n\n预计结束时间：";
				  		message+=jsonResult.endTime;
			  		}
			  		alert(message);
			  	}
        	});
        }
        
		function reserve(pileId){
        	$.ajax({
				url: "<%=basePath%>carOwner/reserve.action",
			  	data: {pileId : pileId, type : $("#"+pileId+"_type option:selected").val()},
			  	async: false,
			  	dataType: "json",
			  	success: function(jsonResult){
			  		if(jsonResult.result=="true"){
			  			alert("预定成功！" + 
			  					"\n\n请记住您的15位预约数字编码:" + jsonResult.checkNumber + 
			  					"\n\n该预约在"　+ jsonResult.expirationTime + "前有效,请尽快前往充电站充电,逾期无效!");
			  		}
			  		else if(jsonResult.result=="undone"){
			  			alert("您还有未完成的预约或者正在充电的订单，请处理完后再执行该操作！");
			  		}
			  		else{
			  			alert("操作失败，请重新尝试。若再次失败请联系管理员。"+
	  					"\n\n电话xxxx-xxxxxxx");
			  		}
					reloadPileList();
			  	}
        	});
		}
		
		function back(){
		    var url = location.href;
		    var regStationListSearch = /stationList/;
		    if(regStationListSearch.test(url)){
				loadPageContent("carOwner", "stationList");
		    }
		    else{
				loadPageContent("carOwner", "mapSearch");
		    }
		}
    </script>
</html>