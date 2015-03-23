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
			    <li><a href="<%=basePath %>station/addPile.jsp" class="add_btn pngFix" id="add">新增</a></li>
			    <li><a onclick="delPiles()" class="delete_btn pngFix">删除选定</a></li>
			    <li><a onclick="reloadPileList()" class="reload_btn pngFix">刷新</a></li>
		    </ul>
		</div>
        <table cellpadding="0" cellspacing="0" class="common_list">
             <tr>
                 <th class="borderLeftNone" style="width: 5%;">
                     <input type="checkbox" value="checkbox" name="checkboxAll" id="ckbAll" />
                 </th>
                 <th style="width: 30%;">
                     充电桩ID
                 </th>
                 <th style="width: 15%;">
                     充电桩别名
                 </th>
                 <th style="width: 15%;">
                     充电桩状态
                 </th>
                 <th style="width: 15%;">
                     手动充电
                 </th>
                 <th class="borderRightNone" style="width: 15%;">
                     操作
                 </th>
      		</tr>
      		<!-- 数据填充 -->
       		<tbody id="pileList">
             
            </tbody>
        </table>
        <div style=""></div>
	</div>
	
    <script type="text/javascript">
        $(function () {
        	reloadPileList();
			initUI();
			var pileJsonList=null;
			setInterval(function(){
				$.ajax({
					url: "<%=basePath%>station/getChargingPileList.action",
				  	data: "",
				  	async: false,
				  	dataType: "json",
				  	success: function(jsonResult){
				  		if(pileJsonList==null){
				  			pileJsonList=jsonResult;
				  		}
			           	for(var i=0; i<jsonResult.length; i++){
			           		if(jsonResult[i].status!=pileJsonList[i].status){
			           			if(jsonResult[i].status=="0"){
			           				alert("充电桩别名："+jsonResult[i].pileName+"。充电完成！");
			           			}
			           			else{
			           				alert("充电桩别名："+jsonResult[i].pileName+"。充电开始！");
			           			}
			           		}
			           	}
			           	pileJsonList=jsonResult;
			           	loadPileListHtml(jsonResult);
				  	}
				});
			},10000);
        });
        
        function reloadPileList(){
        	$.ajax({
				url: "<%=basePath%>station/getChargingPileList.action",
			  	data: "",
			  	async: false,
			  	dataType: "json",
			  	success: function(jsonResult){
	           		//var jsonResult=eval("(" + result + ")");
		           	loadPileListHtml(jsonResult);
			  	}
			});
        }
        
        function loadPileListHtml(jsonResult){
        	$("#pileList").html("");
        	for(var i=0; i<jsonResult.length; i++){
            	var tr_html="<tr>";
            	tr_html+="<td>";
            	tr_html+="<input type=\"checkbox\" name=\"checkbox\" value=\""+jsonResult[i].id+"\" class=\"ckbcls\" />";
            	tr_html+="</td>";
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
            	tr_html+="<button onclick=\"charge('"+jsonResult[i].id+"')\""
            	if(jsonResult[i].status!="0"){
            		tr_html+=" disabled=\"disabled\"";
            	}
            	tr_html+=">充电</button>";
            	tr_html+="</td>";
            	tr_html+="<td>";
            	tr_html+="<button onclick=\"endCharge('"+jsonResult[i].id+"')\""
            	if(jsonResult[i].status=="0"){
            		tr_html+=" disabled=\"disabled\"";
            	}
            	tr_html+=">结束充电</button>";
            	tr_html+="<button onclick=\"toEditPile('"+jsonResult[i].id+"')\">编辑</button>&nbsp;<button onclick=\"delPile('"+jsonResult[i].id+"')\">删除</button>";
            	tr_html+="</td>";
            	tr_html+="</tr>";
            	$("#pileList").append(tr_html);
        	}
			
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
        
        function charge(pileId){
        	$.ajax({
				url: "<%=basePath%>station/charge.action",
			  	data: {pileId : pileId, type : $("#"+pileId+"_type option:selected").val()},
			  	async: false,
			  	dataType: "json",
			  	success: function(jsonResult){
			  		if(jsonResult.isSuccess=="true"){
			  			alert("操作成功！");
			  		}
			  		else{
			  			alert("操作失败，请重新尝试。若再次失败请联系管理员。"+
			  					"\n\n电话xxxx-xxxxxxx");
			  		}
					reloadPileList();
			  	}
        	});
		}
        
        function endCharge(pileId){
        	$.ajax({
				url: "<%=basePath%>station/endCharge.action",
			  	data: {pileId : pileId},
			  	async: false,
			  	dataType: "json",
			  	success: function(jsonResult){
			  		if(jsonResult.isSuccess=="true"){
			  			alert("操作成功！");
			  		}
			  		else{
			  			alert("操作失败，请重新尝试。若再次失败请联系管理员。"+
			  					"\n\n电话xxxx-xxxxxxx");
			  		}
					reloadPileList();
			  	}
        	});
		}
        
        function delPile(pileId){
			$.ajax({
            	type: "POST",
				url: "<%=basePath%>station/delPile.action",
			  	data: {pileIds:pileId},
			  	async: false,
			  	dataType: "text",
			  	success: function(textResult){
			  		if(textResult=="true"){
			  			alert("删除成功！");
			  		}
			  		else if(textResult=="notIdle"){
			  			alert("删除失败，选中的充电桩不全是空闲状态！");
			  		}
			  		else{
			  			alert("删除失败！");
			  		}
			  		reloadPileList();
			  	}
			});
		}
		
		function toEditPile(pileId){
			$.ajax({
            	type: "POST",
				url: "<%=basePath%>station/toEditPile.action",
			  	data: {pileId:pileId},
			  	async: false,
			  	dataType: "json",
			  	success: function(jsonResult){
			  		$("#pageContent").load(url="<%=basePath%>station/editPile.jsp",function(){
				  		$("#pile_id").val(jsonResult.id);
				  		$("#pile_name").val(jsonResult.pileName);
			  		});
			  	}
			});
		}
		
        function delPiles(){
        	var n = $("input:checked").length; 
        	if(n<1){
        		alert("没有选中的记录");
        	}
        	else{
        		var data="";
				$("input[name='checkbox']:checkbox:checked").each(function(){ 
					if(data.length>0){
						data+="&"+$(this).val()
					}else{
						data+=$(this).val();
					}
				});
				$.ajax({
	            	type: "POST",
					url: "<%=basePath%>station/delPile.action",
				  	data: {pileIds:data},
				  	async: false,
				  	dataType: "text",
				  	success: function(textResult){
				  		if(textResult=="true"){
				  			alert("删除成功！");
				  		}
				  		else if(textResult=="notIdle"){
				  			alert("删除失败，选中的充电桩不全是空闲状态！");
				  		}
				  		else{
				  			alert("删除失败！");
				  		}
			  			reloadPileList();
				  	}
				});
			}
        }
    </script>
</html>