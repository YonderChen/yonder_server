<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<div class="account_layer">
	<div class="common_list_out_layer">
		<div class="account_content">
			<div class="inf_modular">
				<div class="titleDiv clearfix">
					<div class="title_L">充电站基本信息</div>
					<div class="title_R">
						<a class="drop_down_icon pngFix"></a>
					</div>
				</div>
				<div class="content">
					<div class="content_main">
						<div class="add_content">
							<div class="add_option">
								<table cellpadding="0" cellspacing="0" class="add_site_tb"
									style="margin: 17px 0 11px 0;">
									<tr>
										<th>充电站ID:</th>
										<td class="add_site_padding td_height">
											<input type="text" id="id" name="id" readonly="readonly" class="txtCss_site2" />
										</td>
									</tr>
									<tr>
										<th>电子邮件:</th>
										<td class="add_site_padding td_height">
											<input type="text" id="email" name="email" readonly="readonly" class="txtCss_site2" />
										</td>
									</tr>
									<tr>
										<th>充电站名称:</th>
										<td class="add_site_padding td_height">
											<input type="text" id="stationName" name="stationName" readonly="readonly" class="txtCss_site2" />
										</td>
									</tr>
									<tr>
										<th>电话号码:</th>
										<td class="add_site_padding td_height">
											<input type="text" id="phoneNumber" name="phoneNumber" readonly="readonly" class="txtCss_site2" />
										</td>
									</tr>
									<tr>
										<th>充电站地址:</th>
										<td class="add_site_padding td_height">
											<input type="text" id="address" name="address" readonly="readonly" class="txtCss_site2" />
										</td>
									</tr>
									<tr>
										<th>经度:</th>
										<td class="add_site_padding td_height">
											<input type="text" id="latitude" name="latitude" readonly="readonly" class="txtCss_site2" />
										</td>
									</tr>
									<tr>
										<th>纬度:</th>
										<td class="add_site_padding td_height">
											<input type="text" id="longitude" name="longitude" readonly="readonly" class="txtCss_site2" />
										</td>
									</tr>
									<tr>
										<th></th>
										<td class="add_site_padding td_height">
											<input id="openEditBtn" type="button" value=" 编辑模式 " onclick="openEdit()" />
											<input id="closeEditBtn" type="button" value=" 关闭编辑 " onclick="closeEdit()" />
										</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div id="button_site" class="button_site" style="display: none;">
				<input id="applicationBtn" type="button" value="" onclick="update()" class="saveBtn" />
				<input id="resetBtn" type="button" value="" onclick="reset()" class="resetBtn" />
			</div>
		</div>
	</div>
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
        loadData();
	});
	
	document.onkeydown=function(event){
		if($("#button_site").css("display")!="none"){
			var e = event || window.event || arguments.callee.caller.arguments[0];
			if(e && e.keyCode==27){ // 按 Esc 
		   		reset();
		 	}            
		 	if(e && e.keyCode==13){ // enter 键
		     	update();
		   	}
	   	}
	}; 
	
	function loadData(){
		$.ajax({
			url: "<%=basePath%>station/getStationInformation.action",
			data : "",
			async : false,
			dataType : "json",
			success : function(jsonResult) {
				//var jsonResult=eval("(" + result + ")");
				$("#id").val(jsonResult.id);
				$("#email").val(jsonResult.email);
				$("#stationName").val(jsonResult.stationName);
				$("#phoneNumber").val(jsonResult.phoneNumber);
				$("#address").val(jsonResult.address);
				$("#latitude").val(jsonResult.latitude);
				$("#longitude").val(jsonResult.longitude);
			}
		});
	}
	
	function openEdit(){
		$("#stationName").removeAttr("readonly");
		$("#phoneNumber").removeAttr("readonly");
		$("#address").removeAttr("readonly");
		$("#latitude").removeAttr("readonly");
		$("#longitude").removeAttr("readonly");
		$("#stationName").focus();
		$("#button_site").css("display","block");
 	}
 	
	function closeEdit(){
        loadData();
		$("#stationName").attr("readonly","readonly");
		$("#phoneNumber").attr("readonly","readonly");
		$("#address").attr("readonly","readonly");
		$("#latitude").attr("readonly","readonly");
		$("#longitude").attr("readonly","readonly");
		$("#button_site").css("display","none");
 	}
 	
 	function update(){
 		$.ajax({
 			type : "POST",
			url : "<%=basePath%>station/updateStationInformation.action",
			data : {id : $("#id").val(), email : $("#email").val(), stationName : $("#stationName").val(),
				phoneNumber : $("#phoneNumber").val(), address : $("#address").val(), latitude : $("#latitude").val(),
				longitude : $("#longitude").val()},
			async : false,
			dataType : "text",
			success : function(textResult) {
				if(textResult=="true"){
					alert("修改成功！");
				}
				else{
					alert("修改失败，请重试！");
				}
				closeEdit();
			}
		});
 	}
 	
	function reset(){
        loadData();
 	}
</script>
</html>

