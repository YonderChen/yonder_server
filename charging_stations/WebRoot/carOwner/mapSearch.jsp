<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<br>
     搜索半径<input type="text" id="radius" value="5" size="5" />公里
     <div id="map_canvas" style="width: 486px; height: 400px"></div>
    <script type="text/javascript">
		var map;
	    var geocoder;
		var address;
        var myIcon = new GIcon(G_DEFAULT_ICON,"App_Themes/Global/img/carOwner/36x40_pub.png");
        myIcon.iconSize = new GSize(36, 40);
        var marker;
        var localMarker;
        
        $(function () {
			initialize(); 
        });
        
        function loadStation(latitude, longitude, radius){
        	$.ajax({
    			url: "<%=basePath%>carOwner/getStationByPosition.action",
    			data : {latitude : latitude, longitude : longitude, radius : radius},
    			async : false,
    			dataType : "json",
    			success : function(jsonResult) {
    				for(var i=0; i<jsonResult.length; i++){
    					var latlan = new GLatLng(jsonResult[i].latitude,jsonResult[i].longitude);
    		            marker = new GMarker(latlan, { icon: myIcon, draggable: false, bouncy: true });
    		            map.addOverlay(marker);
    		            loadInfo(marker, jsonResult[i]); 
    				}
    			}
        	});
        }
        
        function loadInfo(marker,info){
			GEvent.addListener(marker, "click", function(){
		        marker.openInfoWindowHtml(
		    	        '<div style="font-size:13px;">' +
		    	        '<b>充电站名称:</b><a href=\"javascript:void(0)\" onclick=\"loadPileList(\''+info.id+'\')\" style=\"color: blue;\">' + info.stationName + '</a><br/>' +
		    	        '<b>充电站地址:</b>' + info.address + '<br/>' +
		    	        '<b>充电桩数量:</b>' + info.pileCount + '<br/>' +
		    	        '<b>空闲充电桩数量:</b> ' + info.idlePileCount + '<br/>' +
		    	        '<b>坐标:</b>' + info.latitude+","+info.longitude +
		    	        '</div>');
			}); //添加删除地标事件，添加到createMarker(point)函数中
        }
        
	    function initialize() {
	      if (GBrowserIsCompatible()) {
		  var options = {
	       listingTypes : "kmlonly"
	      };
	        map = new GMap2(document.getElementById("map_canvas"),{ size: new GSize(800,400) } );
	        map.setCenter(new GLatLng(24.490898,118.115966), 11);
	        drawCircle(24.490898,118.115966,$("#radius").val());
        	loadStation(24.490898,118.115966,$("#radius").val());
			//这里点击显示地址
			// map.addControl(new GSmallMapControl());
	       GEvent.addListener(map, "click", getAddress);
	       geocoder = new GClientGeocoder();
			var customUI = map.getDefaultUI();
	        customUI.maptypes.hybrid = true;
	        map.setUI(customUI);
			map.enableGoogleBar();
	      }
	    }
		
	function getAddress(overlay, latlng) {
	      if (latlng != null) {
	  		if(localMarker){
				map.removeOverlay(localMarker);
			}
	        address = latlng;
	        geocoder.getLocations(latlng, showAddress);
	      }
	    }

	    function showAddress(response) {
	      if (!response || response.Status.code != 200) {
	        alert("嗯，你点击的这个地方还没有准确地址！"+"状态码（Status Code）:" + response.Status.code);
	      } else {
	        place = response.Placemark[0];
	        var glatlng = new GLatLng(place.Point.coordinates[1],
	                            place.Point.coordinates[0]);
	        localMarker = new GMarker(glatlng);
		    reSetCenter(glatlng)
	      }
	    }

	    function reSetCenter(glatlng){
	        map.clearOverlays();
	        drawCircle(glatlng.lat(),glatlng.lng(),$("#radius").val());
       		loadStation(glatlng.lat(),glatlng.lng(),$("#radius").val());
	        map.addOverlay(localMarker);
		    GEvent.addListener(localMarker, "click", function(){
				map.removeOverlay(localMarker);
		    });
	        localMarker.openInfoWindowHtml(
	        '<div style="font-size:13px;">' +
	        '<b>你现在所点击的地址:</b><br/>' + place.address + '<br/><br/>' +
	        '<b>准确度:</b>' + place.AddressDetails.Accuracy + '&nbsp;&nbsp;&nbsp;&nbsp;' +
	        '<b>国家代码:</b> ' + place.AddressDetails.Country.CountryNameCode +
	        '<b>坐标</b>' + place.Point.coordinates[1]+","+place.Point.coordinates[0] +
	        '</div>');
	    }

	    function loadPileList(stationId){
			$.ajax({
				url: "<%=basePath%>carOwner/chargingPileList.jsp",
			  	data: "",
			  	async: false,
			  	dataType: "html",
			  	success: function(htmlPage){
			  		$("#pageContent").html(htmlPage);
			  		$("#stationId").val(stationId);
			  		reloadPileList();
			  	}
			});
	    }
	    
		//地理位置解析***********************************************************************************************
	    function addAddressToMap(response) {
	      //map.clearOverlays();
	  		if(localMarker){
				map.removeOverlay(localMarker);
			}
	      if (!response || response.Status.code != 200) {
	        alert("不能解析这个地址");
	      } else {
	        var place = response.Placemark[0];
	        var glatlng = new GLatLng(place.Point.coordinates[1],
	                            place.Point.coordinates[0]);
	        localMarker = new GMarker(glatlng);
	        map.addOverlay(localMarker);
	        localMarker.openInfoWindowHtml(getGeocodeHtml(response));
	      }
	    }
	    function getGeocodeHtml(response){
	      var html = "<div style='font-size:13px'>";
	      html += "<b>搜索目标：</b>"+response.name;
	     // html += "<br/><b>结果状态：</b>"+response.Status.code;
	      if(response.Placemark){
	        for (var i = 0, place; place = response.Placemark[i]; i++) {
	          html += "<br/><b>地址：</b>"+place.address;
	         // if(place.AddressDetails.Country)html += "<br/><b>国家代码：</b>"+place.AddressDetails.Country.CountryNameCode;
	         // html += "<br/><b>准确度：</b>"+place.AddressDetails.Accuracy;
	         // html += "<br/><b>坐标：</b>"+place.Point.coordinates[1]+place.Point.coordinates[0];
	        }
	      }
	      html += "</div>";
	      return html;
	    }
	    
		//地理位置解析***********************************************************************************************
		//画圆*********************
		function drawCircle(lat, lng, radius, strokeColor, strokeWidth, strokeOpacity, fillColor, fillOpacity) {
		    var d2r = Math.PI / 180;
		    var r2d = 180 / Math.PI;
		    var Clat = radius / ( 40007.86 / 360 );  // Convert statute miles into degrees latitude
		    var Clng = radius / ( 40075.13 / 360 );
		    var Cpoints = [];
		
		    // 计算圆周上33个点的经纬度，若需要圆滑些，可以增加圆周的点数
		    for (var i = 0; i < 721; i++) {
		        var theta = Math.PI * (i / 360);
		        Cy = lat + (Clat * Math.sin(theta));
		        Cx = lng + (Clng * Math.cos(theta));
		        var P = new GPoint(Cx, Cy);
		        Cpoints.push(P);
		    }
		
		    strokeColor = strokeColor || "#0055ff";   // 边框颜色，默认"#0055ff"
		    strokeWidth = strokeWidth || 1;           // 边框宽度，默认1px
		    strokeOpacity = strokeOpacity || 1;       // 边框透明度，默认不透明
		    fillColor = fillColor || strokeColor;     // 填充颜色，默认同边框颜色
		    fillOpacity = fillOpacity || 0.1;         // 填充透明度，默认0.1
		
		    var polygon = new GPolygon(Cpoints, strokeColor, strokeWidth, strokeOpacity, fillColor, fillOpacity);
		    map.addOverlay(polygon);
		    GEvent.addListener(polygon, "click", function(){
				map.removeOverlay(polygon);
		    });
		}
		//**************
    </script>
</html>
