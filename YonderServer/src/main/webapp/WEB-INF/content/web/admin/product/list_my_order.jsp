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
							图片
						</th>
						<th width="25%">
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
						<th width="10%">
							点餐时间
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
					<s:iterator value="#request.pageBean.list" id="order">
					<tr>
						<td>
							<s:if test="picture == null || picture == ''">
							<div class="picture" style="background-image: url('${ctx}/images/no_picture.jpg');"></div>
							</s:if>
							<s:else>
							<div class="picture" style="background-image: url('${product.picture}');" ></div>
							</s:else>
						</td>
						<td>
							${order.name}<br/>
							所属商家：${order.product.shop.name}
							<br/>
							<s:if test="product.shop.telephone != null && product.shop.telephone != ''">
							商家电话：${order.product.shop.telephone }<br/>
							</s:if>
							<s:if test="remark != null && remark != ''">
							备注：${order.remark }
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
							<s:date name="parent.submitTime" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td><s:if test="parent.isDelete == 0">有效</s:if>
							<s:else>已取消</s:else>
						</td>
						<td>
							<s:if test="parent.isDelete == 0">
							<button class="btn btn-link" type="button" onclick="cancel('${order.parent.orderId }');" data-toggle="button" data-loading-text="<i class='icon-remove'></i>&nbsp;取消订单"><i class="icon-remove"></i>&nbsp;取消订单</button>
							</s:if>
							<s:if test="product.isDelete == 0 && product.shop.isDelete == 0">
								<button class="btn btn-link" type="button" onclick="eat('${order.product.productId}')" data-toggle="button" data-loading-text="<i class='icon-shopping-cart'></i>&nbsp;我还要吃"><i class="icon-shopping-cart"></i>&nbsp;我还要吃</button>
							</s:if>
						</td>
					</tr>
					</s:iterator>
				</tbody>
			</table>
			<jsp:include page="../include/ajax_pager.jsp" flush="true"></jsp:include>
</s:else>
