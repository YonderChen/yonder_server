<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<table width="100%" border="1">
  <tr>
    <td align="right">区服编号<em class="text-error"  style="font-size:16px">*</em>：</td>
    <td><input name="areaId_e" id="areaId_e" type="text" class="span2" readonly value="${area.areaId }" placeholder="区服编号"/></td>
  </tr>
  <tr>
    <td align="right">区服名称<em class="text-error"  style="font-size:16px">*</em>：</td>
    <td><input name="areaName_e" id="areaName_e" type="text" class="span2" maxlength="200" value="${area.areaName }" placeholder="区服名称"/></td>
  </tr>
  <tr>
    <td align="right">Mina(IP地址:端口)<em class="text-error"  style="font-size:16px">*</em> ：</td>
    <td><input name="host_e" id="host_e" type="text" class="span2" maxlength="100" value="${area.host }" placeholder="IP地址"/>&nbsp;:&nbsp;
    	<input name="port_e" id="port_e" type="text" class="span1" maxlength="10" value="${area.port }" placeholder="端口"/>
    </td>
  </tr>
  <tr>
    <td align="right">Akka(IP地址:端口) <em class="text-error"  style="font-size:16px">*</em>：</td>
    <td><input name="hostLan_e" id="hostLan_e" type="text" class="span2" maxlength="100" value="${area.hostLan }" placeholder="IP地址"/>&nbsp;:&nbsp;
    	<input name="portLan_e" id="portLan_e" type="text" class="span1" maxlength="10" value="${area.portLan }" placeholder="端口"/>
    </td>
  </tr>
  <tr>
    <td align="right">开服时间<em class="text-error"  style="font-size:16px">*</em>：</td>
    <td>
    <input type="text" class="span2" placeholder="开服时间" id="publishTime_e" name="publishTime_e" value="${area.publishTime }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'});"/>
    </td>
  </tr>
  <tr>
    <td align="right">版本号<em class="text-error"  style="font-size:16px">*</em>：</td>
    <td><input name="version_e" id="version_e" type="text" class="span2" value="${area.version }" placeholder="版本号"/></td>
  </tr>
  <tr>
    <td align="right">状态<em class="text-error"  style="font-size:16px">*</em>：</td>
    <td>
    <select name="status_e" id="status_e" class="span2">
		<option value="0" <s:if test="#request.area.status == 0">selected</s:if>>逻辑删除</option>
	    <option value="1" <s:if test="#request.area.status == 1">selected</s:if>>推荐</option>
	    <option value="2" <s:if test="#request.area.status == 2">selected</s:if>>拥挤</option>
	    <option value="3" <s:if test="#request.area.status == 3">selected</s:if>>已满</option>
	    <option value="4" <s:if test="#request.area.status == 4">selected</s:if>>维护中</option>
	</select>
    </td>
  </tr>
  <tr>
    <td align="right">合服编号<em class="text-error"  style="font-size:16px">*</em>：</td>
    <td><input name="groupBy_e" id="groupBy_e" type="text" class="span2" value="${area.groupBy }" placeholder="合服编号"/></td>
  </tr>
  <tr>
    <td align="right" style="padding-top:5px;">是否QA<em class="text-error"  style="font-size:16px">*</em>：</td>
    <td><label class="radio inline"><input type="radio" value="0" name="isQa_e" <s:if test="#request.area.isQa == 0">checked</s:if>/>否</label>
    	<label class="radio inline"><input type="radio" value="1" name="isQa_e" <s:if test="#request.area.isQa == 1">checked</s:if>/>是</label>
    </td>
  </tr>
  <tr>
    <td align="right">区服描述：</td>
    <td>
    <textarea class="span3" name="areaDesc_e" id="areaDesc_e" placeholder="区服描述">${area.areaDesc }</textarea>
    </td>
  </tr>
</table>