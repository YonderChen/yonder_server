<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=10; IE=9; IE=8; IE=7; IE=EDGE">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
		<meta http-equiv="expires" content="0">
		<title>逗趣验证码</title>
		<meta name="keywords" content="生成验证码效果,逗趣验证码,12306,12306验证码,验证码,图片验证码">
		<meta name="description" content="生成验证码效果,逗趣验证码,12306,12306验证码,验证码,图片验证码。试着玩咯">
		<link rel="shortcut icon" type="image/x-icon" href="/images/favicon.ico" media="screen">

		<link href="css/default.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="js/base.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>

	<script type="text/javascript">
	function submit() {
		if($("#desc").val().trim() == ""){
			alert("请输入文字描述");
			return;
		}
		for(var i = 1; i < 9; i++){
			if($("#img_" + i).val().trim() == ""){
				alert("请选择图片");
				return;
			}
		}
		$("#pay_form").submit();
	}
</script>
	</head>
	<body style="text-align: center;margin-top: 70px;">
		<div id="mainDiv">
			<form id="pay_form" action="toPage" onkeydown="if(event.keyCode==13) return false;" method="post" enctype="multipart/form-data">
			
				<div class="item clearfix">
					<label class="tit">
						文字描述（点击下图所有的**）：
					</label>
					<input type="text" id="desc" name="desc" />
				</div>
				<div class="item clearfix">
					<label class="tit">
						图1：
					</label>
					<input type="file" id="img_1" name="imgFile" class="input_file">
				</div>
				<div class="item clearfix">
					<label class="tit">
						图2：
					</label>
					<input type="file" id="img_2" name="imgFile" class="input_file">
				</div>
				<div class="item clearfix">
					<label class="tit">
						图3：
					</label>
					<input type="file" id="img_3" name="imgFile" class="input_file">
				</div>
				<div class="item clearfix">
					<label class="tit">
						图4：
					</label>
					<input type="file" id="img_4" name="imgFile" class="input_file">
				</div>
				<div class="item clearfix">
					<label class="tit">
						图5：
					</label>
					<input type="file" id="img_5" name="imgFile" class="input_file">
				</div>
				<div class="item clearfix">
					<label class="tit">
						图6：
					</label>
					<input type="file" id="img_6" name="imgFile" class="input_file">
				</div>
				<div class="item clearfix">
					<label class="tit">
						图7：
					</label>
					<input type="file" id="img_7" name="imgFile" class="input_file">
				</div>
				<div class="item clearfix">
					<label class="tit">
						图8：
					</label>
					<input type="file" id="img_8" name="imgFile" class="input_file">
				</div>
			</form>
			<input type="button" onclick="javascript:submit();" id="submit_button" name="submit_button" value="生成验证码页面">
		</div>
		<div style="padding-top: 200px">
			<a href="about.jsp">联系我</a>
		</div>
		<div id="copyright">
			© 2015-2017 lazyfoal.com 版权所有 ICP证：闽ICP备15008824号-1
			<br />
		</div>
	</body>
</html>