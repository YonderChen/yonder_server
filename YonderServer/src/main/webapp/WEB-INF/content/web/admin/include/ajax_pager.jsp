<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<div class="pagin">
		<input type="hidden" name="currentPage" id="currentPage" value="${pageBean.currentPage}"/>
   	  <div class="message">共找到<i class="blue">${pageBean.allRow }</i>条记录, 每页显示
	                        	<s:set name="pageSizes" value="#request.pageBean.getPageSizes()"/>
								<select id="pageSize" class="span1" onchange="ajaxSearch(1);">
									<s:iterator value="#pageSizes" id="sp">
	                               	<option <s:if test="#sp == #request.pageBean.pageSize">selected</s:if> value="${sp }">${sp }</option>
	                            	</s:iterator>
								</select>
	                        	条记录, 当前显示第
	                        	<s:set name="pages" value="#request.pageBean.getPages()"/>
								<select  id="pageSize_1" class="span1" onchange="ajaxSearch(this.value);">
									<s:iterator value="#pages" id="sp">
	                               	<option <s:if test="#sp == #request.pageBean.currentPage">selected</s:if> value="${sp }">${sp }</option>
	                            	</s:iterator>
								</select>
	                        	页, 共<i class="blue">${pageBean.totalPage }</i>页
   	  
   	  </div>
        <ul class="paginList">
       						 	<s:if test="#request.pageBean.currentPage > 1">
        						<li class="paginItem"><a href="javascript:ajaxSearch(1);" title="首页">首&nbsp;&nbsp;页</a></li>
        						<li class="paginItem"><a href="javascript:ajaxSearch(${pageBean.currentPage - 1 });" title="上一页">上一页</a></li>
	                            </s:if>
	                            <s:else>
	                            <li class="paginItem current"><a href="javascript:;" title="首页">首&nbsp;&nbsp;页</a></li>
	                            <li class="paginItem current"><a href="javascript:;" title="上一页">上一页</a></li>
	                            </s:else>
	                            <s:if test="#request.pageBean.currentPage < #request.pageBean.totalPage">
	                            <li class="paginItem"><a href="javascript:ajaxSearch(${pageBean.currentPage + 1 });" title="下一页">下一页</a></li>
        						<li class="paginItem"><a href="javascript:ajaxSearch(${pageBean.totalPage });" title="尾页">尾&nbsp;&nbsp;页</a></li>
								</s:if>
								<s:else>
								<li class="paginItem current"><a href="javascript:;" title="下一页">下一页</a></li>
	                            <li class="paginItem current"><a href="javascript:;" title="尾页">尾&nbsp;&nbsp;页</a></li>
								</s:else>
        
        </ul>
    </div>
    