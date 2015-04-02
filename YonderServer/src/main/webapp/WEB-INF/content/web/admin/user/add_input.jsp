<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<table width="100%" border="1">
  <tr>
    <td align="right">用户名<em class="text-error"  style="font-size:16px">*</em>：</td>
    <td><input name="username_a" id="username_a" type="text" class="span2" value="" placeholder="用户名" maxlength="50"/></td>
  </tr>
  <tr>
    <td align="right">姓名<em class="text-error"  style="font-size:16px">*</em>：</td>
    <td><input name="name_a" id="name_a" type="text" class="span2" maxlength="50" value="" placeholder="姓名"/></td>
  </tr>
  <tr>
    <td align="right" style="padding-top:5px;">所属角色<em class="text-error"  style="font-size:16px">*</em>：</td>
    <td id="roleTd_a">
    <s:iterator value="#request.roleList" id="role">
		<label class="checkbox inline" title="${role.description }"><input type="checkbox" id="${role.roleId }"/>${role.name }</label>
	</s:iterator>
    </td>
  </tr>
</table>