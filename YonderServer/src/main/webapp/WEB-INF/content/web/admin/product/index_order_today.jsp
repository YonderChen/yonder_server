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
		$(".usual ul").idTabs();
	}
	
	function ajaxSearch(page) {
		if (page == null) {
			page = $("#currentPage").val().trim();
		}
		var pageSize = 50;
		if ($("#pageSize").val() != null) {
			pageSize = $("#pageSize").val().trim();
		}
		$("#searchBtn").button('loading');
		$("#ajaxSearchId").html("<div class='no-found'>加载中...</div>");
		var url = "${ctx}/web/admin/product/list_order_today";
		$.ajax( {
			url : url,
			type : 'post',
			data : {
				page : page,
				pageSize : pageSize,
				type: type,
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
					var status = $("#status").val().trim();
					if (status == "true") {
						logId = $("#msg").val().trim();
						$(".btn-primary").button('reset');
						$(".btn-primary").html("我要下单，不能再点餐");
						$(".btn-primary").attr("data-loading-text", "我要下单，不能再点餐");
					} else {
						$(".btn-primary").button('loading');
						$(".btn-primary").html($("#msg").val().trim());
						$(".btn-primary").attr("data-loading-text", $("#msg").val().trim());
					}
				}
			}
		});
	}
	
	var logId;
	
	function addLog(obj) {
		if (confirm("友情提示:确定要下单，不能再点餐?") == 0) {
			return;
		}
		$(".btn-primary").button('loading');
		var url = "${ctx}/web/admin/product/add_log";
		$.ajax( {
			url : url,
			type : 'post',
			data : {
				logId : logId
			},
			dataType : 'text',
			timeout : 60000,
			error : function(e) {
				$(".btn-primary").button('reset');
				alert("连接服务器超时,请稍后再试.");
			},
			success : function(result) {
				if (!isOutTime(result)) {
					result = eval("("+result+")");
					$(obj).html(result.msg);
					$(obj).attr("data-loading-text", result.msg);
				}
			}
		});
	}
	var type;
	function setType(value) {
		type = value;
		ajaxSearch(1);
	}
	
</script>
	</head>
	<body>
		<div class="place">
			<span>位置：</span>
			<ul class="placeul">
				<li>
					点餐管理
				</li>
				<li>
					今日订单
				</li>
			</ul>
		</div>

	<div class="rightinfo">
	<div class="usual">
			<div class="itab">
				<ul>
					<li><a href="#tab1" <s:if test="#request.type == 1">class="selected"</s:if> onclick="setType(1);">午餐汇总订单</a></li>
					<li><a href="#tab2" <s:if test="#request.type == 2">class="selected"</s:if> onclick="setType(2);">晚餐汇总订单</a></li>
				</ul>
			</div>
		</div>
		<ul class="seachform">
				<li>
					<button id="searchBtn" class="btn btn-info" onclick="ajaxSearch(1);" data-toggle="button" data-loading-text="<i class='icon-refresh'></i>&nbsp;刷新中..."><i class="icon-refresh"></i>&nbsp;刷新</button>
					<button class="btn btn-primary" type="button" onclick="addLog(this)" data-toggle="button" data-loading-text="我要下单，不能再点餐">我要下单，不能再点餐</button>
				</li>
			</ul>
		<div id="ajaxSearchId"></div>
	</div>
</body>
</html>