<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:if test="#request.areaList.size == 0">
<div class='no-found'>无结果.</div>
</s:if>
<s:else>

                        <table width="95%" class="tablelist">
				<thead>
					<tr>
						<th width="5%">
							区服编号
						</th>
						<th width="10%">
							区服名称
						</th>
						<th width="10%">
							版本
						</th>
						<th width="7%">
							5分钟在线人数
						</th>
						<th width="7%">
							15分钟在线人数
						</th>
						<th width="7%">
							30分钟在线人数
						</th>
						<th width="10%">
							服务器时间
						</th>
						<th width="10%">
							开服时间
						</th>
						<th width="5%">
							版本号
						</th>
						<th width="5%">
							状态
						</th>
						<th width="5%">
							合服编号
						</th>
						<th width="5%">
							是否QA
						</th>
						<th width="9%">
							区服描述
						</th>
						<th width="10%">
							操作
						</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="#request.areaList" id="area">
					<tr>
						<td class="area_id">
							${area.areaId }
						</td>
						<td>
							${area.areaName }
						</td>
						<td id="version_${area.areaId }">
							
						</td>
						<td id="onlinePlayer5MinCount_${area.areaId }">
							
						</td>
						<td id="onlinePlayerCount_${area.areaId }">
							
						</td>
						<td id="onlinePlayer30MinCount_${area.areaId }">
							
						</td>
						<td id="serverTime_${area.areaId }">
							
						</td>
						<td>
							${area.publishTime }
						</td>
						<td>
							${area.version }
						</td>
						<td>
							<s:if test="status == 0">逻辑删除</s:if>
							<s:elseif test="status == 1">推荐</s:elseif>
							<s:elseif test="status == 2">拥挤</s:elseif>
							<s:elseif test="status == 3">已满</s:elseif>
							<s:elseif test="status == 4">维护中</s:elseif>
						</td>
						<td>
							${area.groupBy }
						</td>
						<td>
							<s:if test="isQa == 0">否</s:if><s:else>是</s:else>
						</td>
						<td>
							${area.areaDesc }
						</td>
						<td>
							<button class="btn btn-link" type="button" onclick="editInput(${area.areaId });" data-toggle="button" data-loading-text="<i class='icon-pencil'></i>&nbsp;编辑"><i class="icon-pencil"></i>&nbsp;编辑</button>
						</td>
					</tr>
					</s:iterator>
				</tbody>
			</table>
</s:else>
