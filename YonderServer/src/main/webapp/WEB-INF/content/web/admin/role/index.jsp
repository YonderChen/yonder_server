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
		var url = "${ctx}/web/admin/role/list";
		$.ajax( {
			url : url,
			type : 'post',
			data : {
				page : page,
				pageSize : pageSize,
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
				}
			}
		});
	}
	
	function addInput() {
		$('#addModal').modal('show');
    	$(".btn-cancel").button('loading');
		$(".btn-primary").button('loading');
		$("#userDetail").html("<div class='no-found'>加载中...</div>");
		var url = "${ctx}/web/admin/user/add_input";
		$.ajax( {
			url : url,
			type : 'post',
			data : {
				
			},
			dataType : 'text',
			timeout : 60000,
			error : function(e) {
				$("#userDetail").html("<div class='no-found'>连接服务器超时,请稍后再试.</div>");
				$(".btn-cancel").button('reset');
				$(".btn-primary").button('reset');
			},
			success : function(result) {
				if (!isOutTime(result)) {
					$(".btn-cancel").button('reset');
					$(".btn-primary").button('reset');
					$("#userDetail").html(result);
				}
			}
		});
	}
	
	function editInput(roleId) {
		$('#editModal').modal('show');
    	$(".btn-cancel").button('loading');
		$(".btn-primary").button('loading');
		$("#editDetail").html("<div class='no-found'>加载中...</div>");
		var url = "${ctx}/web/admin/role/edit_input";
		$.ajax( {
			url : url,
			type : 'post',
			data : {
				roleId: roleId
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
	
	function selectOne(checkbox) {
		var parent = null;
		$(checkbox).parent().parent().parent().prev().each(function(){
			$(this).find("input").each(function() {
				parent = this;
			});
		});
		if (checkbox.checked) {
			$(parent).prop("checked", true);
		} else {
			var checked = false;
			$(checkbox).parent().parent().parent().each(function(){
				$(this).find("input").each(function() {
					if ($(this).prop("checked")) {
						checked = true;
						return;
					};
				});
			});
			$(parent).prop("checked", checked);
		}
	}
	
	function selectAll(checkbox) {
		$(checkbox).parent().parent().nextAll().each(function(){
			$(this).find("input").each(function() {
				$(this).prop("checked", checkbox.checked);
			});
		});
	}
	
	function edit() {
		if($("#name_e").val().trim() == ""){
			alert("角色名不能为空");
			$("#name_e").select();
			return;
		}
		if($("#description_e").val().trim().length > 100){
			alert("角色描述过长");
			$("#description_e").select();
			return;
		}
		var menuIdArray = [];
		$(".leftmenu_e").find("input").each(function() {
			if ($(this).prop("checked")) {
				menuIdArray.push(this.id);
			}
		});
		if (menuIdArray.length == 0) {
			alert("请勾选菜单.");
			return;
		}
		$(".btn-cancel").button('loading');
		$(".btn-primary").button('loading');
		var url = "${ctx}/web/admin/role/edit";
		$.ajax({
			url:url,
			type:'post',
			data:{
				roleId: $("#roleId").val().trim(),
				name:$("#name_e").val().trim(),
				description:$("#description_e").val().trim(),
				menuIds:myArrayToString(menuIdArray)
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
					if (result.success) {
						$('#editModal').modal('hide');
						ajaxSearch(null);
					} else {
						alert(result.msg);
						$("#name_e").select();
					}
				}
			}
		});
	}
	
	function addInput() {
		$('#addModal').modal('show');
    	$(".btn-cancel").button('loading');
		$(".btn-primary").button('loading');
		$("#addDetail").html("<div class='no-found'>加载中...</div>");
		var url = "${ctx}/web/admin/role/add_input";
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
	
	function add() {
		if($("#name_a").val().trim() == ""){
			alert("角色名不能为空");
			$("#name_a").select();
			return;
		}
		if($("#description_a").val().trim().length > 100){
			alert("角色描述过长");
			$("#description_a").select();
			return;
		}
		var menuIdArray = [];
		$(".leftmenu_a").find("input").each(function() {
			if ($(this).prop("checked")) {
				menuIdArray.push(this.id);
			}
		});
		if (menuIdArray.length == 0) {
			alert("请勾选菜单.");
			return;
		}
		$(".btn-cancel").button('loading');
		$(".btn-primary").button('loading');
		var url = "${ctx}/web/admin/role/add";
		$.ajax({
			url:url,
			type:'post',
			data:{
				name:$("#name_a").val().trim(),
				description:$("#description_a").val().trim(),
				menuIds:myArrayToString(menuIdArray)
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
					if (result.success) {
						$('#addModal').modal('hide');
						ajaxSearch(1);
					} else {
						alert(result.msg);
						$("#name_a").select();
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
					权限管理
				</li>
				<li>
					角色管理
				</li>
			</ul>
		</div>

		<div class="rightinfo">
			<ul class="seachform">
				<li>
					<label>
						角色名称:
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
				<div class="tools">
		    	<ul class="seachform">
		    	<li>
		    	<button class="btn btn-primary" type="button" onclick="addInput();" data-toggle="button" data-loading-text="<i class='icon-plus'></i>&nbsp;新增角色"><i class="icon-plus"></i>&nbsp;新增角色</button>
		    	</li>
		      </ul>
		    </div>
    
			<div id="ajaxSearchId">
			
			</div>
		</div>
		
	<div id="editModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="editModalLabel" aria-hidden="true" style="z-index:100000;" data-backdrop="static">
		<div class="modal-header">
			<button type="button" class="close btn-cancel" data-dismiss="modal" aria-hidden="true" data-toggle="button" data-loading-text="×">×</button>
		<h3 id="editModalLabel">编辑角色</h3>
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
		<h3 id="addModalLabel">新增角色</h3>
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