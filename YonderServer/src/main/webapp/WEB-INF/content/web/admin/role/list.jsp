<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:if test="#request.pageBean.allRow == 0">
	<div class='no-found'>
		无结果.
	</div>
</s:if>
<s:else>
	<table width="95%" class="tablelist">
		<thead>
			<tr>
				<th width="10%">
					角色名称
				</th>
				<th width="30%">
					角色描述
				</th>
				<th width="20%">
					创建时间
				</th>
				<th width="20%">
					最近修改时间
				</th>
				<th width="20%">
					操作
				</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="#request.pageBean.list" id="role">
				<tr>
					<td>
						${role.name }
					</td>
					<td>
						${role.description}
					</td>
					<td>
						<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" />
					</td>
					<td>
						<s:date name="modifyTime" format="yyyy-MM-dd HH:mm:ss" />
					</td>
					<td>
						<button class="btn btn-link" type="button" onclick="editInput('${role.roleId }');" data-toggle="button" data-loading-text="<i class='icon-pencil'></i>&nbsp;编辑">
							<i class="icon-pencil"></i>&nbsp;编辑
						</button>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<jsp:include page="../include/ajax_pager.jsp" flush="true"></jsp:include>
</s:else>
