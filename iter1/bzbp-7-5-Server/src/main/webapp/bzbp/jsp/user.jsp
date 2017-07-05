<%@ page import="java.util.ArrayList"%>
<%@ page import="model.User"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
		ArrayList<User> userList = new ArrayList<User>();
			if (request.getAttribute("user") != null) {
		userList = (ArrayList<User>) request.getAttribute("user");
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
			  		<li><a href="user_getAll" class="active"><i
							class="fa fa-user fa-fw"></i> 用户管理</a></li>
					<li><a href="#"><i class="fa fa-book fa-fw"></i>
							查看统计分析</a></li>
					<li><a href="#"><i class="fa fa-reorder fa-fw"></i>
							最佳分享管理</a></li>
				</ul>
			</div>
			<!-- /.sidebar-collapse -->
		</div>
		<!-- /.navbar-static-side --> </nav>

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">用户信息</h1>
				</div>
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							添加用户
							<button class="btn btn-default" type="button" id="add">
								<i class="fa fa-plus"></i>
							</button>
						</div>	
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover"
									id="dataTables">
									<thead>
										<tr>
										    <th>用户ID</th>
											<th>用户名</th>
											<th>密码</th>
											<th>电子邮箱</th>
											<th>联系方式</th>
											<th>修改/删除</th>
											<th>好友</th>
										</tr>
									</thead>
									<tbody>
										<%
											for (int i = 0; i < userList.size(); i++) {
												User user = userList.get(i);
										%>
										<tr>
										    <td><%=user.getUid()%></td>
											<td><%=user.getUsername()%></td>
											<td><%=user.getPassword()%></td>
											<td><%=user.getEmail()%></td>
											<td><%=user.getPhone()%></td>
											<td>
												<button class="btn btn-default delete" type="button"
													data-id="<%=user.getUid()%>">
													<i class="fa fa-trash"></i>
												</button>
												<button class="btn btn-default edit" type="button"
													data-id="<%=user.getUid()%>"
													data-username="<%=user.getUsername()%>"
													data-password="<%=user.getPassword()%>"
													data-email="<%=user.getEmail()%>"
													data-phone="<%=user.getPhone()%>">
													<i class="fa fa-edit"></i>
												</button>
											</td>
											<td><button onclick="getFriends('<%=user.getUid()%>')">
												查看好友
											</button></td>
										</tr>
										<%
											}
										%>
									</tbody>
								</table>
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

	<div class="modal fade" id="modal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true" >
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="modalTitle"></h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-lg-12">
							<form role="form" style="width: 80%; margin-left: 10%" accept-charset="utf-8" onsubmit="document.charset='utf-8';">
								<div class="form-group input-group">
									<span class="input-group-addon">用户名</span>
									<input class="form-control" name="username">
								</div>
								<div class="form-group input-group">
									<span class="input-group-addon">密码</span> <input class="form-control"
										name="password">
								</div>
								<div class="form-group input-group">
									<span class="input-group-addon">电子邮箱</span> 
									<input class="form-control" name="email">
								</div>
								<div class="form-group input-group">
									<span class="input-group-addon">联系方式</span> 
									<input class="form-control" name="phone">
								</div>								
							</form>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary" id="save">Save</button>
				</div>
			</div>
		</div>
	</div>
	
	
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×
				</button>
				<h4 class="modal-title" id="myModalLabel">
					好友信息
				</h4>
			</div>
			<div class="modal-body" id="friendsfound">
			</div>

		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

	<script src="<%=path%>/bzbp/js/jquery.min.js"></script>
	<script src="<%=path%>/bzbp/js/bootstrap.min.js"></script>
	<script src="<%=path%>/bzbp/js/jquery.dataTables.min.js"></script>
	<script src="<%=path%>/bzbp/js/dataTables.bootstrap.min.js"></script>
	<script src="<%=path%>/bzbp/js/bootbox.min.js"></script>

	<script src="<%=path%>/bzbp/js/user.js"></script>

	<script>
		$(document).ready(function() {
			$('#dataTables').DataTable({
				responsive : true
			});
		});
		function getFriends(uid) {
			jQuery.ajax({
				url : 'user_getFriends',
				processData : true,
				dataType : "json",
				data : {
					uid : uid
				},
				success : function(data) {
					$('#friendsfound').empty();
					html= "<table class='table table-striped table-bordered table-hover'>"+
						   "<thead><tr><th>好友ID</th><th>备注</th></tr></thead><tbody>";
					var size = data.length;
					if(size>0){
						for (var i = 0; i < size; ++i){
							html+="<tr><td>"+data[i].uid2+"</td><td>"+data[i].remark+"</td></tr>";
						}
						html+="</tbody></table>";
						$('#friendsfound').append(html);
					}else
						$('#friendsfound').append("这个人暂时没有好友");
				}
			});
			
			$('#myModal').modal("show");
		}
	</script>

</body>

</html>

