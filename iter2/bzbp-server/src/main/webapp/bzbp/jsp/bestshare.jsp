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

	<%
		ShareItem shareItem = new ShareItem();
			if (request.getAttribute("bestShareItem") != null) {
				shareItem = (ShareItem) request.getAttribute("bestShareItem");
			}
	%>

<div id="wrapper">
		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top" role="navigation"
			style="margin-bottom: 0">

		<div class="navbar-header">
			<a class="navbar-brand" href="#">边走边拍后台管理系统</a>
			<a class="navbar-brand" href="admin_logout">登出</a>
		</div>

		<div class="navbar-default sidebar" role="navigation">
			<div class="sidebar-nav navbar-collapse">
				<ul class="nav" id="side-menu">
			  		<li><a href="user_getAll"><i
							class="fa fa-user fa-fw"></i> 用户管理</a></li>
					<li><a href="#"><i class="fa fa-book fa-fw"></i>
							查看统计分析</a></li>
					<li><a href="bestshare_getBest" class="active"><i class="fa fa-reorder fa-fw"></i>
							最佳分享管理</a></li>
				</ul>
			</div>
			<!-- /.sidebar-collapse -->
		</div>
		<!-- /.navbar-static-side --> </nav>

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">管理最佳的分享</h1>
				</div>
			</div>
			
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							当前的最佳分享				
						</div>
							<table class="table table-striped table-bordered table-hover"
									id="dataTables">
									<thead>
										<tr>
										<th>分享ID</th>
										<th>用户ID</th>
										<th>用户名</th>
										<th>分享主题</th>
										<th>照片数量</th>
				   						<th>开始时间</th>
				   						<th>结束时间</th>
				   						<th>点赞数</th>
				   						<th>评论数</th>
				   						<th>美文或小诗</th>
				   						
									</thead>
									<tbody>
										<tr>
											<td><%=shareItem.getSid()%></td>
										    <td><%=shareItem.getUid()%></td>
											<td><%=shareItem.getUsername()%></td>
											<td><%=shareItem.getTitle()%></td>
											<td><%=shareItem.getPicnum()%></td>
											<td><%=shareItem.getStarttime()%></td>
											<td><%=shareItem.getEndtime()%></td>
											<td><%=shareItem.getUpvote()%></td>
											<td><%=shareItem.getComment()%></td>
											<td><%=shareItem.getPoem()%></td>
											
										</tr>
									
									</tbody>
								</table>	
					</div>
				</div>
			</div>
			
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							点赞数排名前三的分享				
						</div>	
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper" id="thetopten">
								
							</div>
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /#page-wrapper -->
	</div>
	<!-- /#wrapper -->
	


</body>

<script src="<%=path%>/bzbp/js/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$.ajax({
			url:"bestshare_getTopTen",
			type:"post",
			dataType : "json",
			success: function(data){
				console.log(data[0][5]);
				var html;
				html= "<table class='table table-striped table-bordered table-hover'>"+
				   "<thead><tr><th>分享ID</th><th>用户ID</th><th>用户名</th><th>分享主题</th><th>照片数量</th>"
				   +"<th>开始时间</th><th>结束时间</th><th>点赞数</th><th>评论数</th><th>美文或小诗</th><th></th></tr></thead><tbody>";
				var size = data.length;
				//var c = data[0][5]
				//var d = eval("("+data[0][5]+")");
				//console.log(11,d);
				//console.log("get",d["bookdetail"]);
				
				if(size>0){
					for (var i = 0; i < size; ++i){
						html+="<tr><td>"+data[i][0]+"</td><td>"+data[i][1]+"</td><td>"+data[i][2]+"</td><td>"+data[i][3]+"</td><td>"+data[i][4]+"</td>";
						html+="<td>"+getTime(data[i][5])+"</td><td>"+getTime(data[i][6])+"</td><td>"+data[i][7]+"</td><td>"+data[i][8]+"</td><td>"+data[i][9]+"</td>";
						html+="<td><button onclick='test("+data[i][0]+")'>设置它为最佳的分享</button></td></tr>";
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
	
	function getTime(date) {
		var year, month, day, hour, minute, second;
		year = 1900+date["year"];
		month = 1+ date["month"];
		day = date["day"];
		hour = date["hours"];
		minute = date["minutes"];
		second = date["seconds"];
		if ( month<10 ) month = "0" + month;
		if ( day<10 ) day = "0" + day;
		if ( hour<10 ) hour = "0" + hour;
		if ( minute<10 ) minute = "0" + minute;
		if ( second<10 ) second = "0" + second;
		return year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
	}

	
	function test(sid){
		
		bootbox.confirm({
			buttons : {
				confirm : {
					label : '确认'
				},
				cancel : {
					label : '取消'
				}
			},
			message : '确定要将分享ID为'+sid+"的分享设置为最佳分享吗？",
			callback : function(result) {
				if (result) {					
					jQuery.ajax({
						url : 'bestshare_changeBest',
						processData : true,
						dataType : "text",
						data : {
							sid : sid
						},
						success : function(data) {
							bootbox.alert({
								message : '设置成功!',
								callback : function() {
									location.reload();
								}
							});
						}
					});

				}
			}
		});
	}
	
</script>

	<script src="<%=path%>/bzbp/js/jquery.min.js"></script>
	<script src="<%=path%>/bzbp/js/bootstrap.min.js"></script>
	<script src="<%=path%>/bzbp/js/jquery.dataTables.min.js"></script>
	<script src="<%=path%>/bzbp/js/dataTables.bootstrap.min.js"></script>
	<script src="<%=path%>/bzbp/js/bootbox.min.js"></script>

</html>


