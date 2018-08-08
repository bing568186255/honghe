<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="global.jsp" %>
<title>测试页面</title>
</head>
<body>
	<div>
		<span>Type: </span><span>${objType}</span>
	</div>
	<div>
		<span>ID: </span><span>${objID}</span>
	</div>
	<button onclick="ajaxData()">Ajax获取数据</button>
</body>
</html>
<script language="JavaScript">
function ajaxData(){
	$.ajax({
		type : "post",
		url : '${urlhead}/ajax.html?ajax=lvxmXR',
		dataType : 'json',
		data: {},
		timeout : 10000,
		success : function(msg, status) {
			if (msg.status == 200) {
				if (null != msg.rows){
					alert("get rows:"+msg.rows.length);
					for(var i=0;i<msg.rows.length;i++){
						console.log(msg.rows[i]);
					}
				}
			}else{
				showError(msg.msg);
			}
		},
		error: function(){
			alert('网络访问失败！');
		}
	});		
}
</script>
