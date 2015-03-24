<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title></title>
		<jsp:include page="../../include/style.jsp" flush="true"></jsp:include>
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
	$("#ajaxSearchId").html("<div class='no-found'>加载中...</div>");
	var url = "${ctx}/web/admin/system/param/list";
	$.ajax( {
		url : url,
		type : 'post',
		data : {},
		dataType : 'text',
		timeout : 60000,
		error : function(e) {
			$("#ajaxSearchId").html(
					"<div class='no-found'>连接服务器超时,请稍后再试.</div>");
		},
		success : function(result) {
			if (!isOutTime(result)) {
				$("#ajaxSearchId").html(result);
			}
		}
	});
}

function edit(paramId) {
	if ($("#value_" + paramId).val().trim() == "") {
		alert("参数值不能为空");
		$("#value_" + paramId).select();
		return;
	}
	$(".btn-link").button('loading');
	var url = "${ctx}/web/admin/system/param/edit";
	$.ajax( {
		url : url,
		type : 'post',
		data : {
			paramId : paramId,
			value : $("#value_" + paramId).val().trim()
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
				result = eval("(" + result + ")");
				ajaxSearch();
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
					系统管理
				</li>
				<li>
					系统参数设置
				</li>
			</ul>
		</div>

		<div class="rightinfo">
			<div id="ajaxSearchId">

			</div>
		</div>
	</body>
</html>