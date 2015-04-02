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
		var url = "${ctx}/web/admin/user/list";
		$.ajax( {
			url : url,
			type : 'post',
			data : {
				page : page,
				pageSize : pageSize,
				username : $("#username").val().trim(),
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
	
	function editInput(userId) {
		$('#editModal').modal('show');
    	$(".btn-cancel").button('loading');
		$(".btn-primary").button('loading');
		$("#editDetail").html("<div class='no-found'>加载中...</div>");
		var url = "${ctx}/web/admin/user/edit_input";
		$.ajax( {
			url : url,
			type : 'post',
			data : {
				userId: userId
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
					if ($("#isAdmin").val().trim() == "true") {
						var roleIdArray = [];
						myStringToArray(roleIdArray, $("#roleIds").val().trim());
						$("#roleTd_e").find("input").each(function() {
							if (myContain(roleIdArray, this.id)) {
								$(this).prop("checked", true);
							}
						});
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
		var url = "${ctx}/web/admin/user/add_input";
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
	
	function edit() {
		if ($("#name_e").val().trim() == "") {
			alert("姓名不能为空");
			$("#name_e").select();
			return;
		}
		if ($("#phone_e").val().trim() == "") {
			alert("联系电话不能为空");
			$("#phone_e").select();
			return;
		}
		if (!isTelephone($("#phone_e").val().trim())) {
			alert("联系电话无效");
			$("#phone_e").select();
			return;
		}
		var roleIdArray = [];
		if ($("#isAdmin").val().trim() == "true") {
			$("#roleTd_e").find("input").each(function() {
				if ($(this).prop("checked")) {
					roleIdArray.push(this.id);
				}
			});
			if (roleIdArray.length == 0) {
				alert("请勾选角色.");
				return;
			}
		}
		$(".btn-cancel").button('loading');
		$(".btn-primary").button('loading');
		var url = "${ctx}/web/admin/user/edit";
		$.ajax({
			url:url,
			type:'post',
			data:{
				userId: $("#userId").val().trim(),
				name:$("#name_e").val().trim(),
				phone:$("#phone_e").val().trim(),
				status:$("#status_e").val().trim(),
				roleIds:myArrayToString(roleIdArray)
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
						ajaxSearch(1);
					} else {
						alert(result.msg);
						$("#name_e").select();
					}
				}
			}
		});
	}
	
	function add() {
		if ($("#username_a").val().trim() == "") {
			alert("用户名不能为空");
			$("#username_a").select();
			return;
		}
		if ($("#name_a").val().trim() == "") {
			alert("姓名不能为空");
			$("#name_a").select();
			return;
		}
		var roleIdArray = [];
		$("#roleTd_a").find("input").each(function() {
			if ($(this).prop("checked")) {
				roleIdArray.push(this.id);
			}
		});
		if (roleIdArray.length == 0) {
			alert("请勾选角色.");
			return;
		}
		$(".btn-cancel").button('loading');
		$(".btn-primary").button('loading');
		var url = "${ctx}/web/admin/user/add";
		$.ajax({
			url:url,
			type:'post',
			data:{
				username: $("#username_a").val().trim(),
				name:$("#name_a").val().trim(),
				roleIds:myArrayToString(roleIdArray)
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
						$("#username_a").select();
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
					用户管理
				</li>
			</ul>
		</div>

		<div class="rightinfo">
			<ul class="seachform">

				<li>
					<label>
						用户名:
					</label>
					<input name="username" id="username" type="text" class="span2" placeholder="用户名"/>
				</li>
				<li>
					<label>
						姓名:
					</label>
					<input name="name" id="name" type="text" class="span2" placeholder="姓名"/>
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
		    	<button class="btn btn-primary" type="button" onclick="addInput();" data-toggle="button" data-loading-text="<i class='icon-plus'></i>&nbsp;新增用户"><i class="icon-plus"></i>&nbsp;新增用户</button>
		    	</li>
		      </ul>
			<div id="ajaxSearchId">
			
			</div>
		</div>
		
	<div id="editModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="editModalLabel" aria-hidden="true" style="z-index:100000;" data-backdrop="static">
		<div class="modal-header">
			<button type="button" class="close btn-cancel" data-dismiss="modal" aria-hidden="true" data-toggle="button" data-loading-text="×">×</button>
		<h3 id="editModalLabel">编辑用户</h3>
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
		<h3 id="addModalLabel">新增用户</h3>
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