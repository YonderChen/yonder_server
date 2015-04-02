<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<table width="95%" class="tablelist">
				<thead>
					<tr>
						<th width="30%">
							图片
						</th>
						<th width="30%">
							菜名
						</th>
						<th width="20%">
							价格
						</th>
						<th width="20%">
							数量
						</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="#request.productList" id="product">
					<tr id="${product.productId}_tr1">
						<td>
							<s:if test="picture == null || picture == ''">
							<div class="picture" style="background-image: url('${ctx}/images/no_picture.jpg');"></div>
							</s:if>
							<s:else>
							<div class="picture" style="background-image: url('${product.picture}');" ></div>
							</s:else>
						</td>
						<td>
							${product.name}<br/>
							(所属商家：${product.shop.name})
						</td>
						<td>
							¥${product.price}
						</td>
						<td>
							<div class="input-prepend input-append">
							<button class="btn" type="button" onclick="minusOne('${product.productId}')">-</button>
							<input name="${product.productId}_count" id="${product.productId}_count" type="text" class="span1" style="width:20px;" value="1" onkeyup="vertifyCount1(this);" onblur="vertifyCount2(this);"/>
							<button class="btn" type="button" onclick="plusOne('${product.productId}')">+</button>
							</div>
						</td>
					</tr>
					<tr id="${product.productId}_tr2">
						<td colspan="4" style="padding-top:10px;">
							<input name="${product.productId}_remark" id="${product.productId}_remark" type="text" class="span6" placeholder="${product.name}备注" maxlength="100"/>
						</td>
					</tr>
					</s:iterator>
				</tbody>
			</table>
