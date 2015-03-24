<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<table width="100%" border="1">
	<tr>
		<td align="right">
			角色名称<em class="text-error" style="font-size: 16px">*</em>：
		</td>
		<td>
			<input name="name_a" id="name_a" type="text" class="span2" value="" placeholder="角色名称" maxlength="50" />
		</td>
	</tr>
	<tr>
		<td align="right">
			角色描述<em class="text-error" style="font-size: 16px">*</em>：
		</td>
		<td>
			<textarea class="span3" name="description_a" id="description_a" placeholder="角色描述(长度不能超过100个字符)"></textarea>
		</td>
	</tr>
	<tr>
		<td align="right">
			菜单<em class="text-error" style="font-size: 16px">*</em>：
		</td>
		<td>
			<dl class="leftmenu leftmenu_a" style="padding-top: 10px;">
				${roleMenuHtml }
			</dl>
		</td>
	</tr>
</table>