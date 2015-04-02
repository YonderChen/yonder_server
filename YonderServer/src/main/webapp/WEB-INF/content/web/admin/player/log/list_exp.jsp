<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:if test="#request.pageBean.allRow == 0">
<div class='no-found'>无结果.</div>
</s:if>
<s:else>

                        <table width="95%" class="tablelist">
				<thead>
					<tr>
						<th width="5%">
							玩家编号
						</th>
						<th width="10%">
							玩家名称
						</th>
						<th width="15%">
							操作模块
						</th>
						<th width="10%">
							变动前经验
						</th>
						<th width="10%">
							变动经验
						</th>
						<th width="10%">
							变动后经验
						</th>
						<th width="30%">
							备注
						</th>
						<th width="10%">
							操作时间
						</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="#request.pageBean.list" id="log">
					<tr>
						<td>
							${log.gameProfileId }
						</td>
						<td>
							${log.name }
						</td>
						<td>
							${log.moduleDesc }
						</td>
						<td>
							${log.preExp }
						</td>
						<td>
							${log.deltaExp }
						</td>
						<td>
							${log.curExp }
						</td>
						<td>
							${log.remark }
						</td>
						<td>
							<s:date name="opTime" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
						
					</tr>
					</s:iterator>
				</tbody>
			</table>
			<jsp:include page="../../include/ajax_pager.jsp" flush="true"></jsp:include>
</s:else>
