<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:if test="#request.pageBean.allRow == 0">
<div class='no-found'>无结果.</div>
</s:if>
<s:else>

                        <table width="95%" class="tablelist">
				<thead>
					<tr>
						<th width="15%">
							用户名
						</th>
						<th width="10%">
							姓名
						</th>
						<th width="10%">
							联系电话
						</th>
						<th width="15%">
							创建时间
						</th>
						<th width="15%">
							最近登录时间
						</th>
						<th width="15%">
							所属角色
						</th>
						<th width="10%">
							状态
						</th>
						<th width="10%">
							操作
						</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="#request.pageBean.list" id="user">
					<tr>
						<td>
							${user.username}
						</td>
						<td>
							${user.name}
						</td>
						<td>
							${user.phone}
						</td>
						<td>
							<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							<s:date name="lastLoginTime" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							${user.roleName}
						</td>
						<td>
							<s:if test="status == 1">在职</s:if>
							<s:else>离职</s:else>
						</td>
						<td>
							<button class="btn btn-link" type="button" onclick="editInput('${user.userId }');" data-toggle="button" data-loading-text="<i class='icon-pencil'></i>&nbsp;编辑"><i class="icon-pencil"></i>&nbsp;编辑</button>
						</td>
					</tr>
					</s:iterator>
				</tbody>
			</table>
			<jsp:include page="../include/ajax_pager.jsp" flush="true"></jsp:include>
</s:else>
