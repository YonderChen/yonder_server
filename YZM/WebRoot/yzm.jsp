<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=10; IE=9; IE=8; IE=7; IE=EDGE">
		<title>逗趣验证码</title>

	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
	<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon" />
	<link rel="icon" href="images/favicon.ico" type="image/x-icon" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

	<link href="css/default.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="js/jquery.js"></script>
	<style type="text/css">
		@font-face {
			font-family: digit;
		}
	</style>
</head>

<body>

	<div id="mainDiv">
		<div id="content" style="width:720px; height:494px; margin-left:auto; margin-right:auto; padding-top: 50px; background:url(images/bgimg.jpg) no-repeat 0 0;">
			<div style="padding-left: 100px; padding-top: 70px;">
				<h2 style="display: inline; padding-left: 130px;">${desc}</h2>
				<table style="padding-top: 10px;">
					<tr>
						<td>
							<img style="width: 72px; height: 71px;" alt="" src="${pics[0]}"/>
						</td>
						<td>
							<img style="width: 72px; height: 71px;" alt="" src="${pics[1]}"/>
						</td>
						<td>
							<img style="width: 72px; height: 71px;" alt="" src="${pics[2]}"/>
						</td>
						<td>
							<img style="width: 72px; height: 71px;" alt="" src="${pics[3]}"/>
						</td>
					</tr>
					<tr>
						<td>
							<img style="width: 72px; height: 71px;" alt="" src="${pics[4]}"/>
						</td>
						<td>
							<img style="width: 72px; height: 71px;" alt="" src="${pics[5]}"/>
						</td>
						<td>
							<img style="width: 72px; height: 71px;" alt="" src="${pics[6]}"/>
						</td>
						<td>
							<img style="width: 72px; height: 71px;" alt="" src="${pics[7]}"/>
						</td>
					</tr>
				</table>
			</div>
			<div style="padding-top: 100px">
				<a href="about.jsp">联系我</a>
			</div>
		</div>
		
		<div id="copyright">
			© 2015-2017 lazyfoal.com 版权所有 ICP证：闽ICP备15008824号-1
		</div>
	</div>
</body>
</html>