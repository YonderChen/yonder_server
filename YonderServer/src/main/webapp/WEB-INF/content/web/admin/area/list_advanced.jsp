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
							Mina(IP地址:端口)
						</th>
						<th width="10%">
							Akka(IP地址:端口)
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
						<th width="15%">
							区服描述
						</th>
						<th width="15%">
							操作
						</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="#request.areaList" id="area">
					<tr>
						<td>
							${area.areaId }
						</td>
						<td>
							${area.areaName }
						</td>
						<td>
							${area.host }:${area.port }
						</td>
						<td>
							${area.hostLan }:${area.portLan }
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
							<button class="btn btn-link" type="button" onclick="clearMac(${area.areaId });" data-toggle="button" data-loading-text="<i class='icon-repeat'></i>&nbsp;清空Mac"><i class="icon-repeat"></i>&nbsp;清空Mac</button>
						</td>
					</tr>
					</s:iterator>
				</tbody>
			</table>
</s:else>
