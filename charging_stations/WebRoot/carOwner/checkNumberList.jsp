<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE div PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<div class="common_list_out_layer">
		<div class="operate_layer">
			<ul class="left">
			    <li><a onclick="reloadCheckNumbersShowList()" class="reload_btn pngFix">刷新</a></li>
		    </ul>
		</div>
        <table cellpadding="0" cellspacing="0" class="common_list">
             <tr>
                 <th style="width: 20%;">
                     记录ID
                 </th>
                 <th style="width: 20%;">
                     充电桩ID
                 </th>
                 <th style="width: 15%;">
                     到期时间
                 </th>
                 <th style="width: 20%;">
                     验证码
                 </th>
                 <th style="width: 5%;">
                    状态
                 </th>
             </tr>
      		<!-- 数据填充 -->
       		<tbody id="checkNumberList">
             
            </tbody>
        </table>
        <!-- 分页-->
        <div class="paginator pageOuterDiv" style="display: none">
            <table cellpadding="0" cellspacing="0" class="pageTab">
                <tr>
                    <td class="customInfo" nowrap="true" style="width: 40%;">
                        共12条记录，当前第1/1页，每页12条记录
                    </td>
                    <td class="paginator" nowrap="true" style="width: 60%;">
                        <a class="link_class" disabled="disabled">首页</a><a class="link_class" disabled="disabled">前一页</a><span
                            class="link_acted">1</span><a href="#" class="list_ye">2</a><a href="#" class="list_ye">3</a><a
                                href="#" class="list_ye">4</a><a href="#" class="list_ye">5</a><a class="link_class"
                                    href="#">后一页</a><a class="link_class" href="#">末页</a> <span>转到 <span>
                                        <input id="txtPageCouter" type="text" class="txtPageCouter" />
                                    </span>页</span> <a class="link_class" href="#">确定</a>
                    </td>
                </tr>
            </table>
        </div>
        <!-- 分页结束-->
	</div>
	
    <script type="text/javascript">
        $(function () {
        	reloadCheckNumbersShowList();
        });
        
        function reloadCheckNumbersShowList(){
        	$.ajax({
				url: "<%=basePath%>carOwner/getCheckNumbersShowList.action",
			  	data: "",
			  	async: false,
			  	dataType: "json",
			  	success: function(jsonResult){
	            	$("#checkNumberList").append("");
	           		//var jsonResult=eval("(" + result + ")");
		           	for(var i=0; i<jsonResult.length; i++){
		            	var tr_html="<tr>";
		            	tr_html+="<td>";
		            	tr_html+=jsonResult[i].ordersId;
		            	tr_html+="</td>";
		            	tr_html+="<td>";
		            	tr_html+=jsonResult[i].pileId;
		            	tr_html+="</td>";
		            	tr_html+="<td>";
		            	tr_html+=jsonResult[i].expirationTime;
		            	tr_html+="</td>";
		            	tr_html+="<td>";
		            	tr_html+=jsonResult[i].checkNumber;
		            	tr_html+="</td>";
		            	tr_html+="<td>";
		            	tr_html+=jsonResult[i].status;
		            	tr_html+="</td>";
		            	tr_html+="</tr>";
		            	$("#checkNumberList").append(tr_html);
	            	}
			  	}
			});
			
			initUI();
        }
    </script>
</html>