<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:if test="#request.pageBean.allRow == 0">
<div class='no-found'>无结果.</div>
</s:if>
<s:else>

                       <table width="95%" class="tablelist">
				<thead>
					<tr>
						<th width="20%">
							字典KEY
						</th>
						<th width="30%">
							字典值
						</th>
						<th width="20%">
							创建时间
						</th>
						<th width="20%">
							最近修改时间
						</th>
						<th width="10%">
							操作
						</th>
					</tr>
				</thead>
				<tbody>
					<tr id="newTr" style="display:none;">
						<td style="padding-top:10px;">
							<input name="index_new" id="index_new" type="text" class="span2" value="" placeholder="字典KEY" maxlength="40"/>
						</td>
						<td style="padding-top:10px;">
							<input name="value_new" id="value_new" type="text" class="span3" value="" placeholder="字典值" maxlength="90"/>
						</td>
						<td>
							
						</td>
						<td>
							
						</td>
						<td>
							<button class="btn btn-link" type="button" onclick="add();" data-toggle="button" data-loading-text="<i class='icon-ok'></i>&nbsp;保存"><i class="icon-ok"></i>&nbsp;保存</button>
							<button class="btn btn-link" type="button" onclick="cancel();" data-toggle="button" data-loading-text="<i class='icon-remove'></i>&nbsp;取消"><i class="icon-remove"></i>&nbsp;取消</button>
						</td>
					</tr>
					<s:iterator value="#request.pageBean.list" id="dictionary">
					<tr>
						<td>
								${dictionary.pk.index }
						</td>
						<td style="padding-top:10px;">
							<input name="value_${dictionary.pk.index }" id="value_${dictionary.pk.index }" type="text" class="span3" value="${dictionary.value }" placeholder="字典值" maxlength="90"/>
						</td>
						<td>
							<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							<s:date name="modifyTime" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							<button class="btn btn-link" type="button" onclick="edit('${dictionary.pk.index}');" data-toggle="button" data-loading-text="<i class='icon-ok'></i>&nbsp;保存"><i class="icon-ok"></i>&nbsp;保存</button>
						</td>
					</tr>
					</s:iterator>
				</tbody>
			</table>
			<jsp:include page="../../include/ajax_pager.jsp" flush="true"></jsp:include>
</s:else>
