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
		ajaxSearch(1);
	}
	
	function ajaxSearch(page) {
		if (page == null) {
			page = $("#currentPage").val().trim();
		}
		var pageSize = 10;
		if ($("#pageSize").val() != null) {
			pageSize = $("#pageSize").val().trim();
		}
		$("#searchBtn").button('loading');
		$("#ajaxSearchId").html("<div class='no-found'>加载中...</div>");
		var url = "${ctx}/web/admin/refresh/list";
		$.ajax( {
			url : url,
			type : 'post',
			data : {
				page : page,
				pageSize : pageSize
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
	
	function refreshGameConfig(areaId) {
		if (areaId == 0) {
			areaId = $("#areaId").val().trim();
		} else if (areaId == -3){
			$("#batchResult").html("");
			$('#detailModal').modal('show');
			return;
		}
    	$(".btn-cancel").button('loading');
		$(".btn-primary").button('loading');
		var url = "${ctx}/web/admin/refresh/refresh_game_config";
		$.ajax( {
			url : url,
			type : 'post',
			data : {
				areaId: areaId
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
					alert(result.msg);
					if (result.success) {
						ajaxSearch(1);
					}
				}
			}
		});
	}
	
	function refreshBatch() {
		var areaIdArray = [];
		$("#areaTd").find("input").each(function() {
			if ($(this).prop("checked")) {
				areaIdArray.push(this.id);
			}
		});
		if (areaIdArray.length == 0) {
			alert("请勾选区服.");
			return;
		}
		$(".btn-cancel").button('loading');
		$(".btn-primary").button('loading');
		var url = "${ctx}/web/admin/refresh/refresh_batch";
		$.ajax({
			url:url,
			type:'post',
			data:{
				areaIds:myArrayToString(areaIdArray)
			},
			dataType:'text',
			timeout:60000,
			error: function(e) {
				$(".btn-cancel").button('reset');
				$(".btn-primary").button('reset');
				alert("连接服务器超时,请稍后再试.");
			},
			success: function(result){
				if (!isOutTime(result)) {
					$(".btn-cancel").button('reset');
					$(".btn-primary").button('reset');
					result = eval("("+result+")");
					$("#batchResult").html(result.msg);
					if (result.success) {
						ajaxSearch(1);
					}
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
					缓存刷新管理
				</li>
			</ul>
		</div>

		<div class="rightinfo">
		    	<ul class="seachform">
		    	<li>
					<label>
						区服:
					</label>
					<s:select list="#request['areaList']" onchange="" cssClass="span2" value="" name="areaId" id="areaId" listKey="areaId" listValue="areaName" />
				</li>
		    	<li>
		    		<label>
						&nbsp;
					</label>
		    	<button class="btn btn-primary" type="button" onclick="refreshGameConfig(0);" data-toggle="button" data-loading-text="<i class='icon-refresh'></i>&nbsp;刷新游戏服缓存"><i class="icon-refresh"></i>&nbsp;刷新游戏服缓存</button>
		    	<button class="btn btn-primary" type="button" onclick="refreshGameConfig(-1);" data-toggle="button" data-loading-text="<i class='icon-refresh'></i>&nbsp;刷新负载服缓存"><i class="icon-refresh"></i>&nbsp;刷新负载服缓存</button>
		    	<button class="btn btn-primary" type="button" onclick="refreshGameConfig(-2);" data-toggle="button" data-loading-text="<i class='icon-refresh'></i>&nbsp;刷新世界服缓存"><i class="icon-refresh"></i>&nbsp;刷新世界服缓存</button>
		    	<button class="btn btn-primary" type="button" onclick="refreshGameConfig(-3);" data-toggle="button" data-loading-text="<i class='icon-eye-open'></i>&nbsp;批量刷新游戏服缓存"><i class="icon-eye-open"></i>&nbsp;批量刷新游戏服缓存</button>
		    	</li>
		      </ul>
    
			<div id="ajaxSearchId">
			
			</div>
		</div>
		
		<div id="detailModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="detailModalLabel" aria-hidden="true" style="z-index:100000;" data-backdrop="static">
		<div class="modal-header">
			<button type="button" class="close btn-cancel" data-dismiss="modal" aria-hidden="true" data-toggle="button" data-loading-text="×">×</button>
		<h3 id="detailModalLabel">选择区服</h3>
		</div>
		<div class="modal-body">
		<table width="100%" border="1">
  <tr>
    <td align="right" style="padding-top:5px;">区服：</td>
    <td id="areaTd">
    <s:iterator value="#request.areaList" id="area">
		<label class="checkbox"><input type="checkbox" id="${area.areaId }" checked/>${area.areaName }</label>
	</s:iterator>
    </td>
    <td id="batchResult">
    	
    </td>
  </tr>
</table>
		</div>
		<div class="modal-footer">
		<button class="btn btn-primary" data-toggle="button" data-loading-text="刷新" onclick="refreshBatch();">刷新</button>
		<button class="btn btn-cancel" data-dismiss="modal" aria-hidden="true" data-toggle="button" data-loading-text="关闭">关闭</button>
		</div>
	</div>
	</body>
</html>