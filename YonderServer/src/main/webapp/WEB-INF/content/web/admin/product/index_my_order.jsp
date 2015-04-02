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
		var url = "${ctx}/web/admin/product/list_my_order";
		$.ajax( {
			url : url,
			type : 'post',
			data : {
				page : page,
				pageSize : pageSize,
				type : type,
				name : $("#name").val().trim(),
				submitTimeFrom : $("#submitTimeFrom").val().trim(),
				submitTimeTo : $("#submitTimeTo").val().trim()
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
	
	var type;
	function setType(value) {
		type = value;
		ajaxSearch(1);
	}
	
	function cancel(orderId) {
		if (confirm("友情提示:一旦取消不能恢复哦...确定取消订单?") == 0) {
			return;
		}
		$(".btn-link").button('loading');
		var url = "${ctx}/web/admin/product/cancel_order";
		$.ajax( {
			url : url,
			type : 'post',
			data : {
				orderId: orderId
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
					if (result.success) {
						ajaxSearch(null);
					}
				}
			}
		});
	}
	
	function eat(productId) {
		window.location.href="${ctx}/web/admin/product/index?productId="+productId;
		window.parent.changeTab("${ctx}/web/admin/product/index");
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
					我的订单
				</li>
			</ul>
		</div>

	<div class="rightinfo">
		<div class="usual">
			<div class="itab">
				<ul>
					<li><a href="#tab1" <s:if test="#request.type == 1">class="selected"</s:if> onclick="setType(1);">午餐订单</a></li>
					<li><a href="#tab2" <s:if test="#request.type == 2">class="selected"</s:if> onclick="setType(2);">晚餐订单</a></li>
				</ul>
			</div>
		</div>
		<ul class="seachform">
				<li>
					<label>
						菜名:
					</label>
					<input name="name" id="name" type="text" class="span2"/>
				</li>
				<li>
					<label>
						下单时间：
					</label>
					<input type="text" class=span2 id="submitTimeFrom" value="${today}" name="submitTimeFrom" readonly onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',lang:'zh-cn',maxDate:'#F{$dp.$D(\'submitTimeTo\')}'});"/>&nbsp;-&nbsp;
					<input type="text" class="span2" id="submitTimeTo" value="${today}" name="submitTimeTo" readonly onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',lang:'zh-cn',minDate:'#F{$dp.$D(\'submitTimeFrom\')}'});"/>		
				</li>
				<li>
					<label>
						&nbsp;
					</label>
					<button id="searchBtn" class="btn btn-info" onclick="ajaxSearch(1);" data-toggle="button" data-loading-text="<i class='icon-search'></i>&nbsp;搜索中..."><i class="icon-search"></i>&nbsp;搜索</button>
				</li>
			</ul>
		<div id="ajaxSearchId"></div>
	</div>
</body>
</html>