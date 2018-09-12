<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="global.jsp" %>
<title>位置和多边形区域设置</title>

<style type="text/css">
	div.contain{
		position: relative;
		float:left;
		height: 42px;
		line-height: 42px;
	    background: #fff;
		-webkit-border-radius: 23px;
	    -moz-border-radius: 23px;
	    -o-border-radius: 23px;
	    border-radius: 23px;
		box-shadow: 0 6px 6px rgba(0,0,0,.15);
		padding: 0 20px;
		font-size: 14px;
	}
	.next{
		margin-left: 15px;
	}
	.sepcbtn{
		background: #0068FF !important;
		color: #fff !important;
	}
	img.locate{
		width: 20px;
		height: 20px;
		margin-bottom: -5px;
	}
	.contain input{
		border: none;
		outline: none;
		width: 260px;
	}
	.contain #search{
		width: 20px;
		height: 20px;
		cursor: pointer;
		margin-bottom: -5px;
	}
	#searchresult{
		background: #FFF;
		width: 357px;
		font-size: 14px;
		margin-top: 10px;
		padding: 10px 0px;
		-webkit-border-radius: 12px;
	    -moz-border-radius: 12px;
	    -o-border-radius: 12px;
	    border-radius: 12px;
		box-shadow: 0 6px 6px rgba(0,0,0,.15);
		overflow-x: hidden;
		overflow-y: auto;
		display: none;
	}
	#searchresult div.searchresult-item{
		margin: 3px 0px;
		padding: 5px 20px;
		cursor: pointer;
	}
	#searchresult div.searchresult-item.active{
		background: #F1F1F1;
	}
	#searchresult div.searchresult-item div.searchresult-item-contain{
		white-space:nowrap; 
		overflow: hidden;
	}
	#searchresult div.searchresult-item span.item-addr{
		color: #999;
	}
</style>

</head>

<body style="font-size: 14px;font-family: PingFangSC-Regular">
    <div id="container" class="container" tabindex="0"></div>
    <div id="lefttop">
    	<div id="buttonsDiv">
    		<div class="contain">
	    		<img class="locate" src="images/locate.png">
    			<input type="text" id="searchText" placeholder="地址、小区名称、建筑名称"/>
	    		<img id="search" src="images/search.png" onclick="search()">
	    	</div>
	    	<div id="buttons" style="float:left"></div>
	    	<div id="cancelbuttons" style="float:left;display:none;">
	    		<span id="tip" style="color:red;">请在地图上点击选择位置</span>
	    	</div>
    	</div>
    	<div id="searchresult">
    	<!-- 
    		<div class="searchresult-item" index="0">
    			<div class="searchresult-item-contain">
	    		<img class="locate" src="images/locate1.png">
    			<span class="item-name">上海火车站</span>
    			<span class="item-addr">静安区秣陵路303号</span>
    			</div>
    		</div>
    		<div class="searchresult-item active" index="1">
    			<div class="searchresult-item-contain">
	    		<img class="locate" src="images/locate.png">
    			<span class="item-name">昆明翠湖公司公共厕所</span>
    			<span class="item-addr">昆明市西山区秣陵路303号</span>
    			</div>
    		</div>
    	 -->
    	</div>
    </div>
</body>

<script language="JavaScript">
var placeSearch = null;
var g_markers=[];
var g_ptMarker = null;
var g_selPoint = false;
var g_polygon = null;
var g_polygonEditor = null;
var g_editInfo = '${editInfo}';
$(function(){
	WWUI.Init();
	<c:if test="${edit}">
    new WWUI.button({
    	id: "selPositon",
    	class: "next mapbtn sepcbtn",
        parent: $('#buttons'),
        title: "选择位置",
    	fontSize: 14,
        clickFun: selPosition
    });
	<c:if test="${editType==1}">
    new WWUI.button({
    	id: "drawPolygon",
    	class: "next mapbtn sepcbtn",
        parent: $('#buttons'),
        title: "绘制区域",
    	fontSize: 14,
        clickFun: drawPolygon
    });
    new WWUI.button({
    	id: "editPolygon",
    	class: "next mapbtn",
        parent: $('#buttons'),
        title: "编辑区域",
    	fontSize: 14,
        clickFun: editPolygon
    });
	</c:if>
    new WWUI.button({
        parent: $('#buttons'),
    	class: "next mapbtn",
        title: "保存",
    	fontSize: 14,
        clickFun: save
    });
    new WWUI.button({
    	id:'cancel',
        parent: $('#cancelbuttons'),
    	class: "next mapbtn",
        title: "取消",
    	fontSize: 14,
        clickFun: cancel
    });
	</c:if>
	
	
	amapinit({
		type:"2D",
		show:"depart",
		zoom: 28,
		domIDName:"container",
		clickfun:mapClick,
		complete:mapLoaded
	});
	
	$('#searchresult').on('click','div.searchresult-item',function(){
		$('#searchresult div.searchresult-item').each(function(){
			if ($(this).hasClass('active')){
				$(this).find('img').attr('src','images/locate1.png');
				$(this).removeClass('active');
				var index = parseInt($(this).attr('index'));
				if (isNaN(index) || g_markers.length <= index)
					return;
				g_markers[index].setTop(false);
				g_markers[index].setIcon("images/locatex2.png");
				return false;
			}
		});
		$(this).addClass('active');
		$(this).find('img').attr('src','images/locate.png');
		var index = parseInt($(this).attr('index'));
		if (isNaN(index) || g_markers.length <= index)
			return;
		g_markers[index].setTop(true);
		g_markers[index].setIcon("images/locate2x2.png");
		g_map.setCenter(g_markers[index].getPosition());
	});
});

function mapLoaded(){
	AMap.plugin('AMap.Autocomplete',function(){
		var autoOptions = {
		    city: "昆明",
		    input:"searchText"
		};
		autocomplete= new AMap.Autocomplete(autoOptions);
	});
	AMap.service('AMap.PlaceSearch',function(){
	    placeSearch= new AMap.PlaceSearch({
	    	city:"昆明",
	    	citylimit: true,
	    	type:"汽车服务|汽车销售|汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施",
	    	pageSize: 20,
	    	pageIndex: 1
	    });
	});	
	AMap.plugin("AMap.PolyEditor");
	
	//读取已有的位置信息和区域信息
	readAlreadyLocation();
}

function readAlreadyLocation(){
	$.ajax({
		type : "post",
		url : '${urlhead}/ajax.html?ajax=getLocation',
		dataType : 'json',
		data: {'editinfo': g_editInfo},
		timeout : 10000,
		success : function(msg, status) {
			if (msg.status == 200) {
				if (null != msg.data){
					if (null != msg.data.FW){
						var pts = eval('('+msg.data.FW+')');
						var polygon = new AMap.Polygon({
					        path: pts,			//设置多边形边界路径
					        strokeColor: '#00F', //线颜色
					        strokeOpacity: 1, 	//线透明度
					        strokeWeight: 2,    	//线宽
					        fillColor: '#00F', 	//填充色
					        fillOpacity: 0.3,		//填充透明度
					        bubble: false
					    });
						polygon.setMap(g_map);
						drawOver(polygon);
					}
					if (null != msg.data.ZB){
						var pt = msg.data.ZB.split(',');
						if (pt.length == 2){
							pt[0]=  parseFloat(pt[0]);
							pt[1]=  parseFloat(pt[1]);
							if (!isNaN(pt[0]) && !isNaN(pt[1])){
								showPtMarker(pt);
								g_map.setCenter(new AMap.LngLat(pt[0],pt[1]));
							}
						}
					}
				}
			}else{
				WWUI.fun.showError(msg.msg);
			}
		},
		error: function(){
			WWUI.fun.showError('网络访问失败！');
		}
	});	
}

function mapClick(pt,formatAddr){
	if (true != g_selPoint)
		return;
	if (null != g_ptMarker){
		g_map.remove(g_ptMarker);
		g_ptMarker = null;
	}
	showPtMarker(pt,formatAddr);
	g_selPoint = false;
	$('#buttons').show();
	$('#cancelbuttons').hide();
}
function showPtMarker(pt,formatAddr){
	g_ptMarker = new AMap.Marker({
		position: pt
	});
	g_ptMarker.setTop(true);
	g_ptMarker.setMap(g_map);
	if (null != formatAddr){
		g_ptMarker.setTitle(formatAddr);
	}
	
	g_ptMarker.on('click',function(){
		var title = this.getTitle();
		var curPt = this.getPosition();
		var html=[];
		if (null != title && '' != title) {
			html.push('<div class="infoline">地址：'+ title + '</div>');
		}
		html.push('<div class="infoline">坐标：');
		html.push(curPt.getLng()+' , '+curPt.getLat());
		html.push('</div>');
		showInfoWindow(curPt,"位置信息",html.join(''),new AMap.Pixel(0,-30));
	});	
	var html=[];
	if (null != formatAddr && '' != formatAddr) {
		if (formatAddr.substring(0,3)=="云南省")
			formatAddr = formatAddr.substring(3);
		html.push('<div class="infoline">地址：'+ formatAddr + '</div>');
	}
	html.push('<div class="infoline">坐标：');
	html.push(pt[0]+' , '+pt[1]);
	html.push('</div>');
	showInfoWindow(pt,"当前位置",html.join(''),new AMap.Pixel(0,-30));
}

function selPosition(){
	g_selPoint = true;
	$('#tip').html('请在地图上点击选择位置');
	$('#buttons').hide();
	$('#cancelbuttons').show();
	
	closeInfoWindow();
}
function drawPolygon(){
	$('#tip').html('请在地图上绘制多边形区域，双击或右击结束');
	$('#buttons').hide();
	$('#cancelbuttons').show();
	closeInfoWindow();
	if (null != g_polygon){
		g_map.remove(g_polygon);
	}
	startDraw("polygon",drawOver);
}
function editPolygon(){
	if (null != g_polygonEditor){
		g_polygonEditor.close();
		g_polygonEditor = null;
	}
	if (null == g_polygon){
		return;
	}
	$('#tip').html('拖动多边形点进行编辑');
	$('#buttons').hide();
	$('#cancelbuttons').show();
	closeInfoWindow();
	
	$('#cancel').html("结束");
	g_polygonEditor= new AMap.PolyEditor(g_map, g_polygon);
	g_polygonEditor.open();
}

function drawOver(polygon){
	if(null != polygon){
		g_polygon = polygon;
	}else if (null != g_polygon){
		g_map.add(g_polygon);
	}
	$('#buttons').show();
	$('#cancelbuttons').hide();
}

function search(){
	$('#searchresult').html('');
	$('#searchresult').hide();
	var text = $('#searchText').val().trim();
	if (text == '')
		return;
	if (null == placeSearch)
		return;
	g_map.remove(g_markers);
	g_markers=[];
	placeSearch.search(text,function(status,result){
		var resultDom = $('#searchresult');
		if (status != 'complete' || result.info != 'OK'){
			resultDom.html('没有搜索到匹配结果');
		}else{
			for(var i=0;i<result.poiList.pois.length;i++){
				var poi = result.poiList.pois[i];
				var item=[];
	    		item.push('<div class="searchresult-item" index="'+i+'">');
	    		item.push('<div class="searchresult-item-contain">');
	    		item.push('<img class="locate" src="images/locate1.png">');
	    		item.push('<span class="item-name">'+poi.name+'</span>');
	    		item.push('<span class="item-addr">'+poi.address+'</span>');
	    		item.push('</div>');
	    		resultDom.append(item.join(''));
	    		
	    		var marker = new AMap.Marker({
	    			icon: "images/locatex2.png",
	    			bubble: true,
	    			position: poi.location
	    		});
	    		marker.setMap(g_map);
	    		marker.setTitle(poi.name);
	    		g_markers.push(marker);
	    		marker.on('mouseover',function(){
	    			var title = this.getTitle();
	    			this.setLabel({//label默认蓝框白底左上角显示，样式className为：amap-marker-label
	    		        offset: new AMap.Pixel(20, 20),//label相对于maker的位置
	    		        content: title
	    		    });
	    		});
	    		marker.on('mouseout',function(){
	    			this.setLabel({});
	    		});
			}
			g_map.setFitView();
		}
		resultDom.show();
		
		var top = resultDom.offset().top;
		var fullHeight = $(window).height();
		resultDom.height(fullHeight - top - 30);
	});
}
function cancel(){
	g_selPoint = false;
	if (null != g_polygonEditor){
		g_polygonEditor.close();
		g_polygonEditor = null;
		$('#cancel').html("取消");
	}else{
		endDraw();
		if (null != g_polygon){
			g_map.add(g_polygon);
		}
	}
	$('#buttons').show();
	$('#cancelbuttons').hide();
}

function save(){
	var params={'editinfo': g_editInfo};
	if (null != g_ptMarker){
		var pt =g_ptMarker.getPosition();
		params.zb = pt.lng+','+pt.lat;
	}
	if (null != g_polygon){
		var pts = g_polygon.getPath();
		var out=[];
		out.push('[');
		for(var i=0;i<pts.length;i++){
			if (i>0) out.push(',');
			out.push('[');
			out.push(pts[i].getLng());
			out.push(',');
			out.push(pts[i].getLat());
			out.push(']');
		}
		out.push(']');
		params.fw = out.join('');
	}
	$.ajax({
		type : "post",
		url : '${urlhead}/ajax.html?ajax=editLocation',
		dataType : 'json',
		data: params,
		timeout : 10000,
		success : function(msg, status) {
			if (msg.status == 200) {
				WWUI.fun.showMessage("保存成功！");
			}else{
				WWUI.fun.showError(msg.msg);
			}
		},
		error: function(){
			WWUI.fun.showError('网络访问失败！');
		}
	});
}
</script>

</html>
