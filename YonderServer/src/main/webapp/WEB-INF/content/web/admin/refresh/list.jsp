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
							操作者
						</th>
						<th width="65%">
							操作内容
						</th>
						<th width="10%">
							操作时间
						</th>
						<th width="10%">
							操作IP
						</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="#request.pageBean.list" id="log">
					<tr>
						<td>
							${log.operator.username }(${log.operator.name })
						</td>
						<td>
							${log.operateContent }
						</td>
						<td>
							<s:date name="operateTime" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							${log.logIp }
						</td>
					</tr>
					</s:iterator>
				</tbody>
			</table>
			<jsp:include page="../include/ajax_pager.jsp" flush="true"></jsp:include>
</s:else>
