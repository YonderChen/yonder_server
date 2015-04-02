<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title></title>
		<jsp:include page="../include/style.jsp" flush="true"></jsp:include>
	<script type="text/javascript">
	function preFunArray() {
		var funs = new Array();
		funs.push(loadData);
		return funs;
	}
	
	function loadData() {
		ajaxSearch();
	}
	
	function ajaxSearch() {
		$("#searchBtn").button('loading');
		$("#ajaxSearchId").html("<div class='no-found'>加载中...</div>");
		var url = "${ctx}/web/admin/area/list_advanced";
		$.ajax( {
			url : url,
			type : 'post',
			data : {
				
			},
			dataType : 'text',
			timeout : 60000,
			error : function(e) {
				$("#ajaxSearchId").html("<div class='no-found'>连接服务器超时,请稍后再试.</div>");
				$("#searchBtn").button('reset');
			},
			success : function(result) {
				if (!isOutTime(result)) {
					$("#searchBtn").button('reset');
					$("#ajaxSearchId").html(result);
				}
			}
		});
	}
	
	function edit() {
		// 用js判空，验证数据的合法性
		if($("#areaName_e").val().trim() == "") {
			alert("区服名称不能为空");
			$("#areaName_e").select();
			return;
		}
		if($("#areaDesc_e").val().trim().length > 490) {
			alert("区服描述过长");
			$("#areaDesc_e").select();
			return;
		}
		if($("#host_e").val().trim() == ""){
			alert("MinaIP不能为空");
			$("#host_e").select();
			return;
		}
		if($("#port_e").val().trim() == ""){
			alert("端口号不能为空");
			$("#port_e").select();
			return;
		}
		if(!isPositiveInteger($("#port_e").val().trim())){
			alert("端口号不规范");
			$("#port_e").select();
			return;
		}
		if($("#hostLan_e").val().trim() == ""){
			alert("AkkaIP不能为空");
			$("#hostLan_e").select();
			return;
		}
		if($("#portLan_e").val().trim() == ""){
			alert("端口号不能为空");
			$("#portLan_e").select();
			return;
		}
		if(!isPositiveInteger($("#portLan_e").val().trim())){
			alert("端口号不规范");
			$("#portLan_e").select();
			return;
		}
		if($("#publishTime_e").val().trim() == ""){
			alert("开服时间不能为空");
			$("#publishTime_e").select();
			return;
		}
		if($("#version_e").val().trim() == ""){
			alert("版本号不能为空");
			$("#version_e").select();
			return;
		}
		if(!isPositiveInteger($("#version_e").val().trim())){
			alert("版本号不规范");
			$("#version_e").select();
			return;
		}
		if($("#groupBy_e").val().trim() == ""){
			alert("合服编号不能为空");
			$("#groupBy_e").select();
			return;
		}
		if(!isPositiveInteger($("#groupBy_e").val().trim())){
			alert("合服编号不规范");
			$("#groupBy_e").select();
			return;
		}
    	$(".btn-cancel").button('loading');
		$(".btn-primary").button('loading');
		var url = "${ctx}/web/admin/area/edit_advanced";
		$.ajax( {
			url : url,
			type : 'post',
			data : {
				areaId: $("#areaId_e").val().trim(),
				areaName: $("#areaName_e").val().trim(),
				areaDesc: $("#areaDesc_e").val().trim(),
				status: $("#status_e").val().trim(),
				host: $("#host_e").val().trim(),
				port: $("#port_e").val().trim(),
				hostLan: $("#hostLan_e").val().trim(),
				portLan: $("#portLan_e").val().trim(),
				publishTime: $("#publishTime_e").val().trim(),
				version: $("#version_e").val().trim(),
				groupBy: $("#groupBy_e").val().trim(),
				isQa:$("input[name='isQa_e']:checked").val().trim()
			},
			dataType : 'text',
			timeout : 60000,
			error : function(e) {
				$(".btn-cancel").button('reset');
				$(".btn-primary").button('reset');
				alert("连接服务器超时,请稍后再试.");
			},
			success : function(result) {
				if (!isOutTime(result)) {
					$(".btn-cancel").button('reset');
					$(".btn-primary").button('reset');
					result = eval("("+result+")");
					if (result.success) {
						$('#editModal').modal('hide');
						ajaxSearch();
					} else {
						alert(result.msg);
					}
				}
			}
		});
	}
	
	function add() {
		// 用js判空，验证数据的合法性
		if($("#areaId_a").val().trim() == ""){
			alert("区服编号不能为空");
			$("#areaId_a").select();
			return;
		}
		if(!isPositiveInteger($("#areaId_a").val().trim())){
			alert("区服编号不规范");
			$("#areaId_a").select();
			return;
		}
			if($("#areaName_a").val().trim() == "") {
			alert("区服名称不能为空");
			$("#areaName_a").select();
			return;
		}
		if($("#areaDesc_a").val().trim().length > 490) {
			alert("区服描述过长");
			$("#areaDesc_a").select();
			return;
		}
		if($("#host_a").val().trim() == ""){
			alert("MinaIP不能为空");
			$("#host_a").select();
			return;
		}
		if($("#port_a").val().trim() == ""){
			alert("端口号不能为空");
			$("#port_a").select();
			return;
		}
		if(!isPositiveInteger($("#port_a").val().trim())){
			alert("端口号不规范");
			$("#port_a").select();
			return;
		}
		if($("#hostLan_a").val().trim() == ""){
			alert("AkkaIP不能为空");
			$("#hostLan_a").select();
			return;
		}
		if($("#portLan_a").val().trim() == ""){
			alert("端口号不能为空");
			$("#portLan_a").select();
			return;
		}
		if(!isPositiveInteger($("#portLan_a").val().trim())){
			alert("端口号不规范");
			$("#portLan_a").select();
			return;
		}
		if($("#publishTime_a").val().trim() == ""){
			alert("开服时间不能为空");
			$("#publishTime_a").select();
			return;
		}
		if($("#version_a").val().trim() == ""){
			alert("版本号不能为空");
			$("#version_a").select();
			return;
		}
		if(!isPositiveInteger($("#version_a").val().trim())){
			alert("版本号不规范");
			$("#version_a").select();
			return;
		}
		if($("#groupBy_a").val().trim() == ""){
			alert("合服编号不能为空");
			$("#groupBy_a").select();
			return;
		}
		if(!isPositiveInteger($("#groupBy_a").val().trim())){
			alert("合服编号不规范");
			$("#groupBy_a").select();
			return;
		}
    	$(".btn-cancel").button('loading');
		$(".btn-primary").button('loading');
		var url = "${ctx}/web/admin/area/add_advanced";
		$.ajax( {
			url : url,
			type : 'post',
			data : {
				areaId: $("#areaId_a").val().trim(),
				areaName: $("#areaName_a").val().trim(),
				areaDesc: $("#areaDesc_a").val().trim(),
				status: $("#status_a").val().trim(),
				host: $("#host_a").val().trim(),
				port: $("#port_a").val().trim(),
				hostLan: $("#hostLan_a").val().trim(),
				portLan: $("#portLan_a").val().trim(),
				publishTime: $("#publishTime_a").val().trim(),
				version: $("#version_a").val().trim(),
				groupBy: $("#groupBy_a").val().trim(),
				isQa:$("input[name='isQa_a']:checked").val().trim()
			},
			dataType : 'text',
			timeout : 60000,
			error : function(e) {
				$(".btn-cancel").button('reset');
				$(".btn-primary").button('reset');
				alert("连接服务器超时,请稍后再试.");
			},
			success : function(result) {
				if (!isOutTime(result)) {
					$(".btn-cancel").button('reset');
					$(".btn-primary").button('reset');
					result = eval("("+result+")");
					if (result.success) {
						$('#addModal').modal('hide');
						ajaxSearch();
					} else {
						alert(result.msg);
					}
				}
			}
		});
	}
	
	function editInput(areaId) {
		$('#editModal').modal('show');
    	$(".btn-cancel").button('loading');
		$(".btn-primary").button('loading');
		$("#editDetail").html("<div class='no-found'>加载中...</div>");
		var url = "${ctx}/web/admin/area/edit_advanced_input";
		$.ajax( {
			url : url,
			type : 'post',
			data : {
				areaId: areaId
			},
			dataType : 'text',
			timeout : 60000,
			error : function(e) {
				$("#editDetail").html("<div class='no-found'>连接服务器超时,请稍后再试.</div>");
				$(".btn-cancel").button('reset');
				$(".btn-primary").button('reset');
			},
			success : function(result) {
				if (!isOutTime(result)) {
					$(".btn-cancel").button('reset');
					$(".btn-primary").button('reset');
					$("#editDetail").html(result);
				}
			}
		});
	}
	
	function addInput() {
		$('#addModal').modal('show');
    	$(".btn-cancel").button('loading');
		$(".btn-primary").button('loading');
		$("#addDetail").html("<div class='no-found'>加载中...</div>");
		var url = "${ctx}/web/admin/area/add_advanced_input";
		$.ajax( {
			url : url,
			type : 'post',
			data : {
			},
			dataType : 'text',
			timeout : 60000,
			error : function(e) {
				$("#addDetail").html("<div class='no-found'>连接服务器超时,请稍后再试.</div>");
				$(".btn-cancel").button('reset');
				$(".btn-primary").button('reset');
			},
			success : function(result) {
				if (!isOutTime(result)) {
					$(".btn-cancel").button('reset');
					$(".btn-primary").button('reset');
					$("#addDetail").html(result);
				}
			}
		});
	}
	
	function clearMac(areaId) {
		if (confirm("确定清空该区服所有玩家的Mac?") == 0) {
			return;
		}
		$(".btn-link").button('loading');
		var url = "${ctx}/web/admin/area/clear_mac";
		$.ajax( {
			url : url,
			type : 'post',
			data : {
				areaId: areaId
			},
			dataType : 'text',
			timeout : 60000,
			error : function(e) {
				$(".btn-link").button('reset');
				alert("连接服务器超时,请稍后再试.");
			},
			success : function(result) {
				if (!isOutTime(result)) {
					$(".btn-link").button('reset');
					result = eval("("+result+")");
					alert(result.msg);
				}
			}
		});
	}
</script>
	</head>
	<body>
		<div class="place">
			<span>位置：</span>
			<ul class="placeul">
				<li>
					功能目录
				</li>
				<li>
					游戏管理
				</li>
				<li>
					区服高级管理
				</li>
			</ul>
		</div>

		<div class="rightinfo">
		    	<ul class="seachform">
		    	<li>
		    	<button class="btn btn-primary" type="button" onclick="addInput();" data-toggle="button" data-loading-text="<i class='icon-plus'></i>&nbsp;新增区服"><i class="icon-plus"></i>&nbsp;新增区服</button>
		    	</li>
		      </ul>
    
			<div id="ajaxSearchId">
			
			</div>
		</div>
		
		<div id="editModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="editModalLabel" aria-hidden="true" style="z-index:100000;" data-backdrop="static">
		<div class="modal-header">
			<button type="button" class="close btn-cancel" data-dismiss="modal" aria-hidden="true" data-toggle="button" data-loading-text="×">×</button>
		<h3 id="editModalLabel">编辑区服</h3>
		</div>
		<div class="modal-body" id="editDetail">
		
		</div>
		<div class="modal-footer">
		<button class="btn btn-primary" data-toggle="button" data-loading-text="保存" onclick="edit();">保存</button>
		<button class="btn btn-cancel" data-dismiss="modal" aria-hidden="true" data-toggle="button" data-loading-text="关闭">关闭</button>
		</div>
		</div>
		
			<div id="addModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="addModalLabel" aria-hidden="true" style="z-index:100000;" data-backdrop="static">
		<div class="modal-header">
			<button type="button" class="close btn-cancel" data-dismiss="modal" aria-hidden="true" data-toggle="button" data-loading-text="×">×</button>
		<h3 id="addModalLabel">新增区服</h3>
		</div>
		<div class="modal-body" id="addDetail">
		
		</div>
		<div class="modal-footer">
		<button class="btn btn-primary" data-toggle="button" data-loading-text="保存" onclick="add();">保存</button>
		<button class="btn btn-cancel" data-dismiss="modal" aria-hidden="true" data-toggle="button" data-loading-text="关闭">关闭</button>
		</div>
		</div>
	</body>
</html>