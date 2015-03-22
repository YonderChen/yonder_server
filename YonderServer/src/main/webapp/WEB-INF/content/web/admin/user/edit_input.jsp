<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<input type="hidden" id="userId" name="userId" value="${user.userId }"/>
<input type="hidden" id="roleIds" name="roleIds" value="${roleIds }"/>
<table width="100%" border="1">
  <tr>
    <td align="right">用户名<em class="text-error"  style="font-size:16px">*</em>：</td>
    <td><input type="text" class="span2" readonly value="${user.username }" placeholder="用户名"/></td>
  </tr>
  <tr>
    <td align="right">姓名<em class="text-error"  style="font-size:16px">*</em>：</td>
    <td><input name="name_e" id="name_e" type="text" class="span2" maxlength="50" value="${user.name }" placeholder="姓名"/></td>
  </tr>
  <tr>
    <td align="right">手机号码<em class="text-error"  style="font-size:16px">*</em>：</td>
    <td><input name="phone_e" id="phone_e" type="text" class="span2" maxlength="20" value="${user.phone }" placeholder="手机号码"/></td>
  </tr>
  <tr>
    <td align="right" style="padding-top:5px;">所属角色<em class="text-error"  style="font-size:16px">*</em>：</td>
    <td id="roleTd_e">
    <s:iterator value="#request.roleList" id="role">
		<label class="checkbox inline" title="${role.description }"><input type="checkbox" id="${role.roleId }"/>${role.name }</label>
	</s:iterator>
    </td>
  </tr>
</table>