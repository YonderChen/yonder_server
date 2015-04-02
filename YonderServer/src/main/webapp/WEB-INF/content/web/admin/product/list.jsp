<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:if test="#request.pageBean.allRow == 0">
<div class='no-found'>无结果.</div>
</s:if>
<s:else>
			
				<ul class="seachform">
				<s:iterator value="#request.pageBean.list" id="product">
				<li>
					<s:if test="picture == null || picture == ''">
							<div class="picture_big" style="background-image: url('${ctx}/images/no_picture.jpg');"></div>
							</s:if>
							<s:else>
							<div class="picture_big" style="background-image: url('${product.picture}');" ></div>
							</s:else>
					<div>
					<label>${product.name}</label><label style="color:#e84538;">¥${product.price}</label>
					<label><input type="checkbox" name="checkboxs" id="${product.productId}" onclick="selectOne(this);"/>&nbsp;我要吃</label>
					</div>
				</li>
				<li>
				&nbsp;
				</li>
				</s:iterator>
			</ul>
			<table class="tablelist"></table>
			<jsp:include page="../include/ajax_pager.jsp" flush="true"></jsp:include>
</s:else>
