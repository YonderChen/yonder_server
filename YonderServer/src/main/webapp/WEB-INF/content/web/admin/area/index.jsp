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
		var url = "${ctx}/web/admin/area/list";
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
					getAllServerInfo();
					setInterval(getAllServerInfo, 30000);
				}
			}
		});
	}
	
	function getAllServerInfo() {
		$(".area_id").each(function() {
			getServerInfo($(this).html().trim());
		});
	}
	
	function getServerInfo(areaId) {
		var url = "${ctx}/web/admin/area/get_server_info";
		$.ajax( {
			url : url,
			type : 'post',
			data : {
				areaId: areaId
			},
			dataType : 'text',
			timeout : 60000,
			error : function(e) {
				//alert("连接服务器超时,请稍后再试.");
			},
			success : function(result) {
				if (!isOutTime(result)) {
					result = eval("("+result+")");
					$("#version_" + areaId).html(result.version);
					$("#onlinePlayerCount_" + areaId).html(result.onlinePlayerCount);
					$("#onlinePlayer5MinCount_" + areaId).html(result.onlinePlayer5MinCount);
					$("#onlinePlayer30MinCount_" + areaId).html(result.onlinePlayer30MinCount);
					$("#serverTime_" + areaId).html(result.serverTime);
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
		if($("#publishTime_e").val().trim() == ""){
			alert("开服时间不能为空");
			$("#publishTime_e").select();
			return;
		}
    	$(".btn-cancel").button('loading');
		$(".btn-primary").button('loading');
		var url = "${ctx}/web/admin/area/edit";
		$.ajax( {
			url : url,
			type : 'post',
			data : {
				areaId: $("#areaId_e").val().trim(),
				areaName: $("#areaName_e").val().trim(),
				areaDesc: $("#areaDesc_e").val().trim(),
				status: $("#status_e").val().trim(),
				publishTime: $("#publishTime_e").val().trim()
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
	
	function editInput(areaId) {
		$('#editModal').modal('show');
    	$(".btn-cancel").button('loading');
		$(".btn-primary").button('loading');
		$("#editDetail").html("<div class='no-found'>加载中...</div>");
		var url = "${ctx}/web/admin/area/edit_input";
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
					区服管理
				</li>
			</ul>
		</div>

		<div class="rightinfo">
		    	<ul class="seachform">
		    	<li>
		    	<button class="btn btn-primary" type="button" onclick="ajaxSearch();" data-toggle="button" data-loading-text="<i class='icon-refresh'></i>&nbsp;手工刷新"><i class="icon-refresh"></i>&nbsp;手工刷新</button>
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
	</body>
</html>