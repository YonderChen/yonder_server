<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title></title>
	<link type="text/css" href="${ctx}/style/style.css" rel="stylesheet"/>
  	<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
	<script type="text/javascript">
	$('document').ready(function() {
		//导航切换
		$(".menuson li").click(function(){
			$(".menuson li.active").removeClass("active")
			$(this).addClass("active");
		});
	
		$('.title').click(function(){
			var $ul = $(this).next('ul');
			$('dd').find('ul').slideUp();
			if($ul.is(':visible')){
				$(this).next('ul').slideUp();
			}else{
				$(this).next('ul').slideDown();
			}
		});
	});
	
	function changeTabfunction(url){
	$(".menuson li.active").removeClass("active");
	$(".menuson li").each(function(e){
	     var u=$(this).find("a").attr("href");
	     if(u == url){
	      $(this).addClass("active");
	     }
	     
	});
	
	
	}
	</script>
  </head>
  <body style="background:#f0f9fd;width:187px; height:500px; overflow-x:hidden;">
	<div class="lefttop"><span></span>功能目录</div>
    
    <dl class="leftmenu">
        
   ${menuHtml}
    
    </dl>
    

</body>
</html>