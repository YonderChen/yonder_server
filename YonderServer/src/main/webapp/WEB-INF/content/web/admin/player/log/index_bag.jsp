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
		//ajaxSearch(1);
		addBlankSelect("module", "0", "0", "===请选择===");
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
		var url = "${ctx}/web/admin/player/log/list_bag";
		$.ajax( {
			url : url,
			type : 'post',
			data : {
				page : page,
				pageSize : pageSize,
				gameProfileId: $("#gameProfileId").val().trim(),
				areaId: $("#areaId").val().trim(),
				module: $("#module").val().trim(),
				opTimeFrom: $("#opTimeFrom").val().trim(),
				opTimeTo: $("#opTimeTo").val().trim()
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
					运营日志查询
				</li>
				<li>
					背包日志查询
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
						玩家编号:
					</label>
					<input name="gameProfileId" id="gameProfileId" type="text" class="span2"/>
				</li>
				<li>
					<label>
						查询模块:
					</label>
					<s:select list="#request['moduleList']" onchange="" cssClass="span2" value="" name="module" id="module" listKey="pk.index" listValue="value" />
				</li>
				<li>
					<label>
						操作时间：
					</label>
					<input type="text" class="span2" id="opTimeFrom" name="opTimeFrom" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',lang:'zh-cn',maxDate:'#F{$dp.$D(\'opTimeTo\')}'});"/>&nbsp;-&nbsp;
					<input type="text" class="span2" id="opTimeTo" name="opTimeTo" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',lang:'zh-cn',minDate:'#F{$dp.$D(\'opTimeFrom\')}'});"/>		
				</li>
				<li>
					<label>
						&nbsp;
					</label>
					<button id="searchBtn" class="btn btn-info" onclick="ajaxSearch(1);" data-toggle="button" data-loading-text="<i class='icon-search'></i>&nbsp;搜索中..."><i class="icon-search"></i>&nbsp;搜索</button>
				</li>
		      </ul>
    
			<div id="ajaxSearchId">
			
			</div>
		</div>
		
	</body>
</html>