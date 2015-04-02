<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<input type="hidden" id="roleId" name="roleId" value="${role.roleId }"/>
<table width="100%" border="1">
  <tr>
    <td align="right">角色名称<em class="text-error"  style="font-size:16px">*</em>：</td>
    <td><input name="name_e" id="name_e" type="text" class="span2" value="${role.name }" placeholder="角色名称" maxlength="50"/></td>
  </tr>
  <tr>
    <td align="right">角色描述：</td>
    <td>
    <textarea class="span3" name="description_e" id="description_e" placeholder="角色描述(长度不能超过100个字符)">${role.description }</textarea>
    </td>
  </tr>
  <tr>
    <td align="right">菜单<em class="text-error"  style="font-size:16px">*</em>：</td>
    <td>
    <dl class="leftmenu leftmenu_e" style="padding-top: 10px;">
				${roleMenuHtml }
			</dl>
		</td>
  </tr>
</table>