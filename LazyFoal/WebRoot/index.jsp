<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.io.File"%>
<%@page import="java.io.FileWriter"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String visitLogDirPath = request.getSession().getServletContext().getRealPath("/") + "visitlog/";
SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
String visitLogName = "visit" + dayFormat.format(new Date()) + ".log";
FileWriter fw = null;   
try {  
	String remoteAddr = request.getRemoteAddr();
	File visitLogFile = new File(visitLogDirPath + visitLogName);
	if(!visitLogFile.isFile()) {
		File visitLogDirFile = new File(visitLogDirPath);
		visitLogDirFile.setWritable(true, false);
		visitLogFile.createNewFile();
	}
	visitLogFile.setWritable(true, false);
	fw = new FileWriter(visitLogFile, true);
	fw.append(timeFormat.format(new Date()) + " 有人访问, ip: " + remoteAddr + "\r\n");
} catch (Exception e) {   
    e.printStackTrace();   
}   
finally {   
    try {   
        fw.close();   
    } catch (Exception e) {   
        e.printStackTrace();   
    }   
}  
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
    
	<title>牛儿与逗哥</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="牛儿,表白,我爱你">
	<meta http-equiv="description" content="i love you">

	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
	<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon" />
	<link rel="icon" href="images/favicon.ico" type="image/x-icon" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

	<link href="css/default.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/garden.js"></script>
	<script type="text/javascript" src="js/functions.js"></script>
	<script type="text/javascript" src="js/pic.js"></script>

	<!--
		<script type="text/javascript" src="js/excanvas.js"></script>
	-->
	<!--[if IE]><script type="text/javascript" src="js/html5.js"></script><![endif]-->
	<style type="text/css">
		@font-face {
			font-family: digit;
		}
	</style>
</head>

<body>

　　<embed src="voice/wulai.mp3" autostart="true" loop="true" hidden="true"></embed>

	<div id="mainDiv">
		<div id="content">
			<div id="code">
				我说你是牛<br />
				你说我是豆<br />
				既然这样<br />
				那么<br />
				我来负责<br />
				工作挣钱养牛儿<br />
				你就负责<br />
				吃饭睡觉打豆豆<br />
				<br />
				<br />
				<br />
				其实我想说的是:<br />
				<br />
				曾经我犯过很多错<br />
				也因为我的不珍惜 错过很多东西<br />
				我也曾经差点错过你<br />
				还好<br />
				小伙伴们的到来<br />
				让我又有机会接触你<br />
				这一次我会好好珍惜<br />
				不再错过这美好的缘分<br />
				<br />
				<br />
				和你在一起<br />
				我会尽我所能的给你幸福<br />
				你开心<br />
				我陪你开心<br />
				你不开心<br />
				我逗你开心<br />
				你还是不开心<br />
				我陪着你一起不开心<br />
				希望我们能这样相守到老
			</div>
			<div id="loveHeart">
				<canvas id="garden"></canvas>
				<div id="words">
					<div id="messages">
						牛儿，这是我们相识的时光。
						<div id="elapseClock"></div>
					</div>
					<div id="loveu">
						爱你直到永永远远。<br/>
						<div class="signature">- 爱你的逗哥</div>
					</div>
				</div>
			</div>
			<div id="top">
				<div class="small">
			    	<ul>
			        	<li style="opacity: 0.96; " class="hove"><img alt="" width="65" height="55" src="images/1.jpg" /></li>
			            <li style="opacity: 0.4; " class=""><img alt="" style="margin-left: 5px;" width="50" height="65" src="images/2.jpg" /></li>
			            <li style="opacity: 0.4; " class=""><img alt="" width="65" height="65" src="images/3.jpg" /></li>
			            <li style="opacity: 0.4; " class=""><img alt="" width="65" height="65" src="images/4.jpg" /></li>
			            <li style="opacity: 0.4; " class=""><img alt="" width="50" style="margin-left: 5px;" height="65" src="images/5.jpg" /></li>
			            <li style="opacity: 0.4; " class=""><img alt="" width="50" style="margin-left: 5px;" height="65" src="images/6.jpg" /></li>
			            <li style="opacity: 0.4; " class=""><img alt="" width="65" height="55" src="images/7.jpg" /></li>
			            <li style="opacity: 0.4; " class=""><img alt="" width="65" height="55" src="images/8.jpg" /></li>
			            <li style="opacity: 0.4; " class=""><img alt="" width="50" style="margin-left: 5px;" height="65" src="images/9.jpg" /></li>
			        </ul>
			    </div>
			</div>
			<div id="box">
				<div class="bg"></div>
			    <div id="list">
			        <ul>
			            <li style="width: 344px; height: 440px; top: 0px; left: 352px; z-index: 10; " class="hove">
			                <img src="images/1.jpg">
			                <div style="opacity: 0; "></div>
			                <p class="b_tit" style="bottom: -120px; ">
			                    <span class="opacity"></span>
			                    <span class="tit">
			                        <span><em>打台球的牛</em>&nbsp;&nbsp;&nbsp;&nbsp;有模有样形象气质佳</span>
			                        <em>第一次教你打台球，学的有模有样，虽然还是打不过我</em>
			                    </span>
			                </p>
			            </li>
			            <li style="width: 260px; height: 332px; top: 56px; left: 640px; z-index: 8; " class="">
			                <img src="images/2.jpg">
			                <div style="opacity: 0.71; "></div>
			                <p class="b_tit">
			                    <span class="opacity"></span>
			                    <span class="tit">
			                        <span><em>炫耀牛蹄的牛</em>&nbsp;&nbsp;&nbsp;&nbsp;涂满了不明物体</span>
			                        <em>涂了回来臭美，问每个人好不好看，结果表面上得到一致差评，但是其实大家心里还是觉得很美的</em>
			                    </span>
			                </p>
			            </li>
			            <li style="width: 204px; height: 260px; top: 92px; left: 844px; z-index: 6; " class="">
			                <img src="images/3.jpg">
			                <div style="opacity: 0.71; "></div>
			                <p class="b_tit" style="bottom: -120px; ">
			                    <span class="opacity"></span>
			                    <span class="tit">
			                        <span><em>端庄贤淑的牛</em>&nbsp;&nbsp;&nbsp;&nbsp;安静的做个美女子</span>
			                        <em>我们在点菜，你就这样端庄的坐在那边等着林妹妹拍你</em>
			                    </span>
			                </p>
			            </li>
			            <li style="width: 140px; height: 180px; top: 132px; left: 148px; z-index: 4; " class="">
			                <img src="images/4.jpg">
			                <div style="opacity: 0.71; "></div>
			                <p class="b_tit">
			                    <span class="opacity"></span>
			                    <span class="tit">
			                        <span><em>通话中的牛</em>&nbsp;&nbsp;&nbsp;&nbsp;轻声细语的接着电话</span>
			                        <em>百分之八十以上是那个可恶老板或者老板娘的电话</em>
			                    </span>
			                </p>
			            </li>
			            <li style="width: 110px; height: 140px; top: 172px; left: 232px; z-index: 2; " class="">
			                <img src="images/5.jpg">
			                <div style="opacity: 0.71; "></div>
			                <p class="b_tit" style="bottom: -120px; ">
			                    <span class="opacity"></span>
			                    <span class="tit">
			                        <span><em>托着下巴的牛</em>&nbsp;&nbsp;&nbsp;&nbsp;发呆的时候就这样托着下巴</span>
			                        <em>去星巴克你什么都不点，就这样在里面托着牛下巴坐在我身边发呆</em>
			                    </span>
			                </p>
			            </li>
			            <li style="width: 110px; height: 140px; top: 172px; left: 232px; z-index: 2; " class="">
			                <img src="images/6.jpg">
			                <div style="opacity: 0.71; "></div>
			                <p class="b_tit">
			                    <span class="opacity"></span>
			                    <span class="tit">
			                        <span><em>过生日的牛</em>&nbsp;&nbsp;&nbsp;&nbsp;脸上被涂满了幸福蛋糕</span>
			                        <em>因为一些事情，没能陪你过生日，心里默默伤心</em>
			                    </span>
			                </p>
			            </li>
			            <li style="width: 140px; height: 180px; top: 132px; left: 148px; z-index: 4; " class="">
			                <img src="images/7.jpg">
			                <div style="opacity: 0.71; "></div>
			                <p class="b_tit">
			                    <span class="opacity"></span>
			                    <span class="tit">
			                        <span><em>一群小伙伴中的牛</em>&nbsp;&nbsp;&nbsp;&nbsp;5十K作案团伙</span>
			                        <em>身边有这么一群小伙伴，牛儿总是这么开心快乐</em>
			                    </span>
			                </p>
			            </li>
			            <li style="width: 204px; height: 260px; top: 92px; left: 0px; z-index: 6; " class="">
			                <img src="images/8.jpg">
			                <div style="opacity: 0.71; "></div>
			                <p class="b_tit">
			                    <span class="opacity"></span>
			                    <span class="tit">
			                        <span><em>新小伙伴中的牛</em>&nbsp;&nbsp;&nbsp;&nbsp;为什么有个大叔乱入</span>
			                        <em>这一排的小眼神在看什么呢，是在看我么</em>
			                    </span>
			                </p>
			            </li>
			            <li style="width: 260px; height: 332px; top: 56px; left: 148px; z-index: 8; " class="">
			                <img src="images/9.jpg">
			                <div style="opacity: 0.71; "></div>
			                <p class="b_tit">
			                    <span class="opacity"></span>
			                    <span class="tit">
			                        <span><em>和我一起看菜单的牛</em>&nbsp;&nbsp;&nbsp;&nbsp;牛奶巧克力豆</span>
			                        <em>喝什么？牛奶巧克力豆！</em>
			                    </span>
			                </p>
			            </li>
			        </ul>
			        <a href="javascript:;" class="prev"></a>
			        <a href="javascript:;" class="next"></a>
			    </div>
			</div>
		</div>
		<div id="copyright">
			© 2015-2017 liulili520.com 版权所有 ICP证：闽ICP备15008824号-1<br />
		</div>
	</div>

<script type="text/javascript">
var offsetX = $("#loveHeart").width() / 2;
var offsetY = $("#loveHeart").height() / 2 - 55;
var together = new Date();
together.setFullYear(2013, 7, 19);
together.setHours(0);
together.setMinutes(0);
together.setSeconds(0);
together.setMilliseconds(0);

if (document.createElement('canvas').getContext && (!isIE || safariVersion > DEFAULT_VERSION)) {
	setTimeout(function () {
		startHeartAnimation();
	}, 5000);
} else {
	$("#loveHeart").css("background-image","url(images/loveHeartBg.png)");
	setTimeout(function () {
		showMessages();
	}, 5000);
}
	
timeElapse(together);
setInterval(function () {
	timeElapse(together);
}, 500);

adjustCodePosition();
$("#code").typewriter();
</script>


</body>
</html>