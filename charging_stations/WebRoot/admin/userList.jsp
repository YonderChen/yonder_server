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
			    <li><a onclick="verifyCarOwners()" style="width: 80px">审核选定</a></li>
			    <li><a onclick="reloadCarOwnerList()" class="reload_btn pngFix">刷新</a></li>
			    <li><a onclick="reloadUnverifyCarOwnerList()" class="search_btn pngFix" style="width: 80px">未审核</a></li>
		    </ul>
		</div>
        <table cellpadding="0" cellspacing="0" class="common_list">
             <tr>
                 <th class="borderLeftNone" style="width: 2%;">
                     <input type="checkbox" value="checkbox" name="checkboxAll" id="ckbAll" />
                 </th>
                 <th style="width: 30%;">
                     用户ID
                 </th>
                 <th style="width: 30%;">
                     电子邮箱
                 </th>
                 <th style="width: 25%;">
                     用户姓名
                 </th>
                 <th class="borderRightNone" style="width: 5%;">
                     操作
                 </th>
      		</tr>
      		<!-- 数据填充 -->
       		<tbody id="userList">
             
            </tbody>
        </table>
        <div style=""></div>
	</div>
	
    <script type="text/javascript">
        $(function () {
        	reloadCarOwnerList();
        });
        
        function reloadCarOwnerList(){
        	$.ajax({
				url: "<%=basePath%>admin/getCarOwnerList.action",
			  	data: "",
			  	async: false,
			  	dataType: "json",
			  	success: function(jsonResult){
		        	$("#userList").html("");
	           		//var jsonResult=eval("(" + result + ")");
		           	for(var i=0; i<jsonResult.length; i++){
		            	var tr_html="<tr>";
		            	tr_html+="<td>";
		            	tr_html+="<input type=\"checkbox\" name=\"checkbox\" value=\""+jsonResult[i].id+"\" class=\"ckbcls\" />";
		            	tr_html+="</td>"
		            	tr_html+="<td>";
		            	tr_html+=jsonResult[i].id;
		            	tr_html+="</td>";
		            	tr_html+="<td>";
		            	tr_html+=jsonResult[i].email;
		            	tr_html+="</td>";
		            	tr_html+="<td>";
		            	tr_html+=jsonResult[i].realName;
		            	tr_html+="</td>";
		            	tr_html+="<td>";
	                	if(jsonResult[i].status=="0"){
			            	tr_html+="<button onclick=\"verifyCarOwner('"+jsonResult[i].id+"')\">审核";
	                	}
	                	else{
	                		tr_html+="已审核";
	                	}
		            	tr_html+="</td>";
		            	tr_html+="</tr>";
		            	$("#userList").append(tr_html);
	            	}
			  	}
			});
			
			initUI();
        }
        
        function reloadUnverifyCarOwnerList(){
        	$.ajax({
				url: "<%=basePath%>admin/getUnverifyCarOwnerList.action",
			  	data: "",
			  	async: false,
			  	dataType: "json",
			  	success: function(jsonResult){
		        	$("#userList").html("");
	           		//var jsonResult=eval("(" + result + ")");
		           	for(var i=0; i<jsonResult.length; i++){
		            	var tr_html="<tr>";
		            	tr_html+="<td>";
		            	tr_html+="<input type=\"checkbox\" name=\"checkbox\" value=\""+jsonResult[i].id+"\" class=\"ckbcls\" />";
		            	tr_html+="</td>"
		            	tr_html+="<td>";
		            	tr_html+=jsonResult[i].id;
		            	tr_html+="</td>";
		            	tr_html+="<td>";
		            	tr_html+=jsonResult[i].email;
		            	tr_html+="</td>";
		            	tr_html+="<td>";
		            	tr_html+=jsonResult[i].realName;
		            	tr_html+="</td>";
		            	tr_html+="<td>";
	                	if(jsonResult[i].status=="0"){
			            	tr_html+="<button onclick=\"verifyCarOwner('"+jsonResult[i].id+"')\">审核";
	                	}
	                	else{
	                		tr_html+="已审核";
	                	}
		            	tr_html+="</td>";
		            	tr_html+="</tr>";
		            	$("#userList").append(tr_html);
	            	}
			  	}
			});
			
			initUI();
        }
        
        function verifyCarOwner(ownerId){
        	$.ajax({
				url: "<%=basePath%>admin/verifyCarOwner.action",
			  	data: {ownerIds:ownerId},
			  	async: false,
			  	dataType: "text",
			  	success: function(textResult){
			  		alert(textResult);
			  		if(textResult=="true"){
			  			alert("操作成功！");
			  		}
			  		else{
			  			alert("操作失败！");
			  		}
		        	reloadCarOwnerList();
			  	}
			});
		}
        
        function verifyCarOwners(){
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
					url: "<%=basePath%>admin/verifyCarOwner.action",
				  	data: {ownerIds:data},
				  	async: false,
				  	dataType: "text",
				  	success: function(textResult){
				  		if(textResult=="true"){
				  			alert("操作成功！");
				  		}
				  		else{
				  			alert("操作失败！");
				  		}
				  		reloadCarOwnerList();
				  	}
	        	});
        	}
        }
    </script>
</html>