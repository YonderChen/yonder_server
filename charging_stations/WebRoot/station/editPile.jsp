<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<div class="account_layer">
	    <div class="common_list_out_layer">
	        <div class="account_content">
				<div class="operate_layer">
					<ul class="left">
					    <li><a onclick="back()" class="back_btn pngFix">返回</a></li>
				    </ul>
				</div>
	            <div class="inf_modular">
	                <div class="titleDiv clearfix">
	                    <div class="title_L">
	                        充电桩信息
	                    </div>
	                    <div class="title_R">
	                        <a class="drop_down_icon pngFix "></a>
	                    </div>
	                </div>
	                <div class="content">
	                    <div class="content_main">
	                        <div class="add_content">
	                            <div class="add_option">
	                                <table cellpadding="0" cellspacing="0" class="add_site_tb" style="margin: 17px 0 11px 0;">
	                                   <tr>
	                                       <th>
	                                           充电桩ID:
	                                       </th>
	                                       <td class="add_site_padding td_height">
	                                           <input type="text" id="pile_id" readonly="readonly" name="pile_id" class="txtCss_site1" />
	                                       </td>
	                                   </tr>
	                                   <tr>
	                                       <th>
	                                       </th>
	                                       <td>
	                                       </td>
	                                   </tr>
	                                   <tr>
	                                       <th>
	                                           充电桩别名:
	                                       </th>
	                                       <td class="add_site_padding td_height">
	                                           <input type="text" id="pile_name" name="pile_name" class="txtCss_site2" />
	                                       </td>
	                                   </tr>
	                                   <tr>
	                                       <th>
	                                       </th>
	                                       <td>
	                                       </td>
	                                   </tr>
	                               </table>
	                           </div>
	                       </div>
	                   </div>
	               </div>
	           </div>
	           
	           <div class="button_site">
	               <input id="applicationBtn" type="button" value="" onclick="update()" class="saveBtn" />&nbsp;
	               <input id="resetBtn" type="button" value="" onclick="reset()" class="resetBtn" />
	           	</div>
	        </div>
	    </div>
	</div>
<script type="text/javascript">
	$(function () {
        $(".drop_down_icon").click(function () {
        	if ($(this).parent().parent().next().is(":hidden"))
           		$(this).removeClass("contraction_icon");
          	else
             	$(this).addClass("contraction_icon");
            $(this).parent().parent().next().slideToggle();
        });
        //框架高度调整
	 	if (window.parent.adjustHeiht) {
	        var height = $(document).height() + 20;
	       	window.parent.adjustHeiht(height);
	    }
          
        document.onkeydown=function(event){
	        var e = event || window.event || arguments.callee.caller.arguments[0];
	        if(e && e.keyCode==27){ // 按 Esc 
	        	reset();
	       	}            
	       	if(e && e.keyCode==13){ // enter 键
	         	update();
	     	}
		}; 
	});
      
      
   
	function update(){
       	$.ajax({
       		type: "POST",
			url: "<%=basePath%>station/updatePile.action",
			data: {pile_id : $("#pile_id").val(), pile_name : $("#pile_name").val()},
			async: false,
			dataType: "json",
			success: function(jsonResult){
	      		//var jsonResult=eval("(" + result + ")");
	      		alert(jsonResult.message);
	      		if(jsonResult.isSuccess=="true"){
	      			location.href = "<%=basePath%>station/myStation.jsp";
	         	}
			}
		});
	}
       
	function check(ip){
		//alert("IP地址输入非法!");
        var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
        var flag_ip=pattern.test(ip);
        if(!flag_ip){
         	return false;
        }
        return true;
	}
       
	function reset(){
 		$("#pile_name").val("");
 	}
	
	function back(){
		loadPageContent("station", "chargingPileList");
	}
   </script>
</html>
