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
		if ("${addProductId}" != "") {
			productIdArray.push("${addProductId}");
			addInput();
		}
		$(".usual ul").idTabs();
	}
	
	function handleRemark(remark) {
		while (remark.indexOf("#") != -1 || remark.indexOf(",") != -1) {
			remark = remark.replace("#","");
			remark = remark.replace(",","");
		}
		return remark;
	}
	
	var shopId;
	
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
		var url = "${ctx}/web/admin/product/list";
		$.ajax( {
			url : url,
			type : 'post',
			data : {
				page : page,
				pageSize : pageSize,
				shopId: shopId,
				name : $("#name").val().trim()
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
					for (var i = 0; i < productIdArray.length; i++) {
						$("#"+productIdArray[i]).prop("checked", true);
					}
				}
			}
		});
	}
	
	function setShopId(value) {
		shopId = value;
		ajaxSearch(1);
	}
	
	var productIdArray = [];
    
    function selectOne(checkbox) {
    	if (checkbox.checked) {
			if (!myContain(productIdArray, checkbox.id)) {
				productIdArray.push(checkbox.id);
			}
		} else {
			myRemove(productIdArray, checkbox.id);
		}
    }
	
	function addInput() {
		if (productIdArray.length == 0) {
			alert("你勾选你要吃的菜品.");
			return;
		}
		$('#detailModal').modal('show');
		$(".btn-cancel").button('loading');
		$(".btn-primary").button('loading');
		$("#ajaxDetailId").html("<div class='no-found'>加载中...</div>");
		var url = "${ctx}/web/admin/product/pick_list";
		$.ajax( {
			url : url,
			type : 'post',
			data : {
				productIds : myArrayToString(productIdArray)
			},
			dataType : 'text',
			timeout : 60000,
			error : function(e) {
				$("#ajaxDetailId").html("<div class='no-found'>连接服务器超时,请稍后再试.</div>");
				$(".btn-cancel").button('reset');
				$(".btn-primary").button('reset');
			},
			success : function(result) {
				if (!isOutTime(result)) {
					$(".btn-cancel").button('reset');
					$(".btn-primary").button('reset');
					$("#ajaxDetailId").html(result);
				}
			}
		});
	}
	
	function vertifyCount1(obj) {
		var val = $(obj).val().trim();
		if (val != "" && !isPositiveInteger(val)) {
			$(obj).val("1");
		}
	}
	
	function vertifyCount2(obj) {
		var val = $(obj).val().trim();
		if (!isPositiveInteger(val)) {
			$(obj).val(1);
		}
	}
	
	function plusOne(productId) {
		var val = $("#"+productId+"_count").val().trim();
		val++;
		$("#"+productId+"_count").val(val);
	}
	
	function minusOne(productId) {
		var val = $("#"+productId+"_count").val().trim();
		if (val > 1) {
			val--;
			$("#"+productId+"_count").val(val);
		} else {
			if (confirm("友情提示:确定移除该菜品?") == 0) {
				return;
			}
			$("#"+productId).prop("checked", false);
			myRemove(productIdArray, productId);
			$("#"+productId+"_tr1").remove();
			$("#"+productId+"_tr2").remove();
			if (productIdArray.length == 0) {
				$('#detailModal').modal('hide');
			}
		}
	}
	
	function add() {
		if (confirm("友情提示:一旦提交就不能更改哦...确定吃这些?") == 0) {
			return;
		}
		var productIdCountArray = [];
		for (var i = 0; i < productIdArray.length; i++) {
			productIdCountArray.push(productIdArray[i]+"#"+$("#"+productIdArray[i]+"_count").val().trim()+"#"+
					handleRemark($("#"+productIdArray[i]+"_remark").val().trim()));
		}
		$(".btn-cancel").button('loading');
		$(".btn-primary").button('loading');
		var url = "${ctx}/web/admin/product/add";
		$.ajax( {
			url : url,
			type : 'post',
			data : {
				productIdCounts : myArrayToString(productIdCountArray)
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
						$('#detailModal').modal('hide');
						window.location.reload();
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
					点餐管理
				</li>
				<li>
					我要点餐
				</li>
			</ul>
		</div>

	<div class="rightinfo">
		<div class="usual">
			<div class="itab">
				<ul>
					<s:iterator value="#request.shopList" id="shop" status="line">
					<li><a href="#tab${line.index + 1}" <s:if test="#line.index == 0">class="selected"</s:if> onclick="setShopId('${shop.shopId}');">${shop.name}</a></li>
					</s:iterator>
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
						&nbsp;
					</label>
					<button id="searchBtn" class="btn btn-info" onclick="ajaxSearch(1);" data-toggle="button" data-loading-text="<i class='icon-search'></i>&nbsp;搜索中..."><i class="icon-search"></i>&nbsp;搜索</button>
				</li>
			</ul>
			<s:if test="#request.status">
			<ul class="seachform">
			<li>
				<button class="btn btn-primary" type="button" onclick="addInput();" data-toggle="button" data-loading-text="<i class='icon-shopping-cart'></i>&nbsp;就吃这些"><i class="icon-shopping-cart"></i>&nbsp;就吃这些</button>
			</li>
			</ul>
			</s:if>
			<s:else>
			<div class="alert alert-error">
				<div>${msg}</div>
			</div>
			</s:else>
		<div id="ajaxSearchId"></div>
	</div>
	
	<div id="detailModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="detailModalLabel" aria-hidden="true" style="z-index:100000;width:650px;" data-backdrop="static">
		<div class="modal-header">
			<button type="button" class="close btn-cancel" data-dismiss="modal" aria-hidden="true" data-toggle="button" data-loading-text="×">×</button>
			<h3 id="detailModalLabel">确认订单</h3>
		</div>
		<div class="modal-body">
			<div id="ajaxDetailId"></div>
		</div>
		<div class="modal-footer">
		<button class="btn btn-primary" data-toggle="button" data-loading-text="提交订单" onclick="add();">提交订单</button>
		<button class="btn btn-cancel" data-dismiss="modal" aria-hidden="true" data-toggle="button" data-loading-text="再逛逛">再逛逛</button>
		</div>
	</div>
	
</body>
</html>