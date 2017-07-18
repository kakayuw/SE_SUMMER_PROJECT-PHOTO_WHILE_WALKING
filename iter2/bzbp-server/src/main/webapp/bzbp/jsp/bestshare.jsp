<%@ page import="java.util.ArrayList"%>
<%@ page import="model.ShareItem"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>BookStore</title>

<%
	String path = request.getContextPath();
%>
<link href="<%=path%>/bzbp/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=path%>/bzbp/css/dataTables.bootstrap.css"
	rel="stylesheet">
<link href="<%=path%>/bzbp/css/dataTables.responsive.css"
	rel="stylesheet">
<link href="<%=path%>/bzbp/css/bookstore.css" rel="stylesheet">
<link href="<%=path%>/bzbp/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
</head>

<body>
<p>2333</p>
<div id="thetopten">
</div>

</body>

<script src="<%=path%>/bzbp/js/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$.ajax({
			url:"bestshare_getTopTen",
			type:"post",
			dataType : "json",
			success: function(data){
				console.log(data);
				var html;
				html= "<table class='table table-striped table-bordered table-hover'>"+
				   "<thead><tr><th>分享ID</th><th>用户ID</th><th>用户名</th><th>分享主题</th><th>照片数量</th>"
				   +"<th>开始时间</th><th>时长</th><th>点赞数</th><th>评论数</th><th>美文或小诗</th></tr></thead><tbody>";
				var size = data.length;
				
				if(size>0){
					for (var i = 0; i < size; ++i){
						html+="<tr><td>"+data[i][0]+"</td><td>"+data[i][1]+"</td><td>"+data[i][2]+"</td><td>"+data[i][3]+"</td><td>"+data[i][4]+"</td>";
						html+="<td>"+data[i][5]+"</td><td>"+data[i][6]+"</td><td>"+data[i][7]+"</td><td>"+data[i][8]+"</td><td>"+data[i][9]+"</td></tr>";
					}
					html+="</tbody></table>";
					$('#thetopten').append(html);
				}else
					$('#thetopten').append("<p>No result.</p>");
				}
				
				//$('#thetopten').append(html);
			//}
		});
	});
	

	
</script>
</html>


