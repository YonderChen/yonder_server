<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<table width="100%" border="1">
  <tr>
    <td align="right">区服编号<em class="text-error"  style="font-size:16px">*</em>：</td>
    <td><input name="areaId_a" id="areaId_a" type="text" class="span2"  placeholder="区服编号"/></td>
  </tr>
  <tr>
    <td align="right">区服名称<em class="text-error"  style="font-size:16px">*</em>：</td>
    <td><input name="areaName_a" id="areaName_a" type="text" class="span2" maxlength="200"  placeholder="区服名称"/></td>
  </tr>
  <tr>
    <td align="right">Mina(IP地址:端口) <em class="text-error"  style="font-size:16px">*</em>：</td>
    <td><input name="host_a" id="host_a" type="text" class="span2" maxlength="100" placeholder="IP地址"/>&nbsp;:&nbsp;
    	<input name="port_a" id="port_a" type="text" class="span1" maxlength="10"  placeholder="端口"/>
    </td>
  </tr>
  <tr>
    <td align="right">Akka(IP地址:端口) <em class="text-error"  style="font-size:16px">*</em>：</td>
    <td><input name="hostLan_a" id="hostLan_a" type="text" class="span2" maxlength="100"  placeholder="IP地址"/>&nbsp;:&nbsp;
    	<input name="portLan_a" id="portLan_a" type="text" class="span1" maxlength="10"  placeholder="端口"/>
    </td>
  </tr>
  <tr>
    <td align="right">开服时间<em class="text-error"  style="font-size:16px">*</em>：</td>
    <td>
    <input type="text" class="span2" placeholder="开服时间" id="publishTime_a" name="publishTime_a" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'});"/>
    </td>
  </tr>
  <tr>
    <td align="right">版本号<em class="text-error"  style="font-size:16px">*</em>：</td>
    <td><input name="version_a" id="version_a" type="text" class="span2" value="${area.version }" placeholder="版本号"/></td>
  </tr>
  <tr>
    <td align="right">状态<em class="text-error"  style="font-size:16px">*</em>：</td>
    <td>
    <select name="status_a" id="status_a" class="span2">
		<option value="0" >逻辑删除</option>
	    <option value="1" >推荐</option>
	    <option value="2" >拥挤</option>
	    <option value="3" >已满</option>
	    <option value="4" >维护中</option>
	</select>
    </td>
  </tr>
  <tr>
    <td align="right">合服编号<em class="text-error"  style="font-size:16px">*</em>：</td>
    <td><input name="groupBy_a" id="groupBy_a" type="text" class="span2" placeholder="合服编号"/></td>
  </tr>
  <tr>
    <td align="right" style="padding-top:5px;">是否QA<em class="text-error"  style="font-size:16px">*</em>：</td>
    <td><label class="radio inline"><input type="radio" value="0" name="isQa_a"  checked/>否</label>
    	<label class="radio inline"><input type="radio" value="1" name="isQa_a" />是</label>
    </td>
  </tr>
  <tr>
    <td align="right">区服描述：</td>
    <td>
    <textarea class="span3" name="areaDesc_a" id="areaDesc_a" placeholder="区服描述"></textarea>
    </td>
  </tr>
</table>