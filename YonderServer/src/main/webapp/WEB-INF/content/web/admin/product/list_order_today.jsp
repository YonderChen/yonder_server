<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<input type="hidden" id="status" value="${status }"/>
<input type="hidden" id="msg" value="${msg }"/>
<s:if test="#request.orderList.size== 0">
<div class='no-found'>无结果.</div>
</s:if>
<s:else>
<div class="alert alert-error">
				<div>${totalMsg }</div>
			</div>
                       <table width="95%" class="tablelist">
				<thead>
					<tr>
						<th width="15%">
							图片
						</th>
						<th width="20%">
							菜名
						</th>
						<th width="10%">
							单价
						</th>
						<th width="10%">
							数量
						</th>
						<th width="10%">
							总价
						</th>
						<th width="20%">
							所属商家
						</th>
						<th width="15%">
							商家电话
						</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="#request.orderList" id="order">
					<tr>
						<td>
							<s:if test="picture == null || picture == ''">
							<div class="picture" style="background-image: url('${ctx}/images/no_picture.jpg');"></div>
							</s:if>
							<s:else>
							<div class="picture" style="background-image: url('${order.picture}');" ></div>
							</s:else>
						</td>
						<td>
							${order.productName}
							<s:if test="remark != null">
							<br/>
							${order.remark }
							</s:if>
						</td>
						<td>
							¥${order.price}
						</td>
						<td>
							${order.quantity}
						</td>
						<td>
							¥${order.total}
						</td>
						<td>
							${order.shopName}
						</td>
						<td>
							${order.shopTelephone}
						</td>
					</tr>
					</s:iterator>
				</tbody>
			</table>
			
</s:else>
