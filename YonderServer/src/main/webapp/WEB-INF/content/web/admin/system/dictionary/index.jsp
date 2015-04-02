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
		$(".usual ul").idTabs();
	}
	
	var module;
	
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
		var url = "${ctx}/web/admin/system/dictionary/list";
		$.ajax( {
			url : url,
			type : 'post',
			data : {
				page : page,
				pageSize : pageSize,
				module: module,
				value : $("#value").val().trim(),
				index : $("#index").val().trim()
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
	
	function edit(index) {
		if($("#value_"+index).val().trim() == ""){
			alert("字典值不能为空");
			$("#value_"+index).select();
			return ;
		}
		$(".btn-link").button('loading');
		var url = "${ctx}/web/admin/system/dictionary/edit";
		$.ajax( {
			url : url,
			type : 'post',
			data : {
				index:index,
				value:$("#value_"+index).val().trim(),
				module:module
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
					if(result.success){
						ajaxSearch(null);
					} else {
						alert(result.msg);
						$("#value_"+index).select();
					}
				}
			}
		});
	}
	
	function setModule(value) {
		module = value;
		ajaxSearch(1);
	}
	
	function add(){
		 if($("#index_new").val().trim() ==""){
		 	alert("字典key不能为空");
		 	$("#index_new").select();
		 	return ;
		 }
		  if($("#value_new").val().trim() ==""){
		 	alert("字典值不能为空");
		 	$("#value_new").select();
		 	return ;
		 }
		$(".btn-link").button('loading');
		var url = "${ctx}/web/admin/system/dictionary/add";
		$.ajax( {
			url : url,
			type : 'post',
			data : {
				index:$("#index_new").val().trim(),
				value:$("#value_new").val().trim(),
				module:module
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
					if(result.success){
						ajaxSearch(1);
					} else {
						alert(result.msg);
					}
				}
			}
		});
	}
	
	function addInput() {
		$("#newTr").show();
		$("#index_new").select();
	}
	
	function cancel() {
		$("#newTr").hide();
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
					数据字典管理
				</li>
			</ul>
		</div>

	<div class="rightinfo">
		<div class="usual">
			<div class="itab">
				<ul>
					<li><a href="#tab1" class="selected" onclick="setModule(1);">操作模块</a></li>
					<li><a href="#tab2" onclick="setModule(2);">背包类型</a></li>
					<li><a href="#tab3" onclick="setModule(200);">水手信物</a></li>
				</ul>
			</div>
		</div>
		<ul class="seachform">
				<li>
					<label>
						字典KEY:
					</label>
					<input name="index" id="index" type="text" class="span2"/>
				</li>
				<li>
					<label>
						字典值:
					</label>
					<input name="value" id="value" type="text" class="span2"/>
				</li>
				<li>
					<label>
						&nbsp;
					</label>
					<button id="searchBtn" class="btn btn-info" onclick="ajaxSearch(1);" data-toggle="button" data-loading-text="<i class='icon-search'></i>&nbsp;搜索中..."><i class="icon-search"></i>&nbsp;搜索</button>
				</li>

			</ul>
		    	<ul class="seachform">
		    	<li>
		    	<button class="btn btn-primary" type="button" onclick="addInput();" data-toggle="button" data-loading-text="<i class='icon-plus'></i>&nbsp;新增字典"><i class="icon-plus"></i>&nbsp;新增字典</button>
		    	</li>
		      </ul>
		<div id="ajaxSearchId"></div>
	</div>
</body>
</html>