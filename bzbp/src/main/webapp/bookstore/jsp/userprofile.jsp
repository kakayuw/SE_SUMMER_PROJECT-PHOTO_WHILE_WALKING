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
<link href="<%=path%>/bookstore/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=path%>/bookstore/css/dataTables.bootstrap.css"
	rel="stylesheet">
<link href="<%=path%>/bookstore/css/dataTables.responsive.css"
	rel="stylesheet">
<link href="<%=path%>/bookstore/css/bookstore.css" rel="stylesheet">
<link href="<%=path%>/bookstore/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

</head>

<body>
	 <%
	 	String session_username=(String)session.getAttribute("username");
	 	String session_role=(String)session.getAttribute("role"); 
	 	if (session_username == null || !session_role.equals("Customer")){
	 		response.sendRedirect("login_register");
	 	}
	 %>
	<%
		ArrayList<User> userList = new ArrayList<User>();
			if (request.getAttribute("users") != null) {
		userList = (ArrayList<User>) request.getAttribute("users");
			}
	%>
	<div id="wrapper">
		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top" role="navigation"
			style="margin-bottom: 0">

		<div class="navbar-header">
			<a class="navbar-brand" href="#">BookStore</a>
			<a class="navbar-brand" href="login_register">Login</a>
			<a class="navbar-brand" href="#">Hello,<%=session_username%>!</a>
			<a class="navbar-brand" href="Logout">Logout</a>
		</div>

		<div class="navbar-default sidebar" role="navigation">
			<div class="sidebar-nav navbar-collapse">
				<ul class="nav" id="side-menu">
					<li><a href="homePage"><i class="fa fa-home fa-fw"></i>
							Homepage</a></li>
					<li><a href="Userprofile" ><i
							class="fa fa-book fa-fw"  class="active"></i> User profile</a></li>
					<li><a href="customerBookview"><i class="fa fa-user fa-fw"></i>
							View books</a></li>
					<li><a href="Shoppingcart" ><i
							class="fa fa-book fa-fw"></i> My Shopping cart</a></li>
				</ul>
			</div>
			<!-- /.sidebar-collapse -->
		</div>
		<!-- /.navbar-static-side --> </nav>



		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">User profile</h1>
				</div>
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">	
								<table class="table table-striped table-bordered table-hover"
									id="dataTables">
									<tbody>
										<%
											for (int i = 0; i < userList.size(); i++) {
												User user = userList.get(i);
												if (user.getUsername().equals(session_username)){
													
										%>
										<tr>
											<td>					
												<button class="btn btn-default edit" type="button"
													data-id="<%=user.getId()%>"
													data-username="<%=user.getUsername()%>"
													data-password="<%=user.getPassword()%>"
													data-role="<%=user.getRole()%>"
													data-name="<%=user.getName()%>"
													data-gender="<%=user.getGender()%>"
													data-phonenumber="<%=user.getPhonenumber()%>"
													data-address="<%=user.getAddress()%>">
													修改个人信息
													<i class="fa fa-edit"></i>
												</button>
											</td>
											<td></td>
										</tr>
										<tr>
										<td>ID</td>
										<td><%=user.getId()%></td>
										</tr>
										<tr>
										<td>Username</td>
										<td><%=user.getUsername()%></td>
										</tr>
										<tr>
										<td>Password</td>
										<td><%=user.getPassword()%></td>
										</tr>
										<tr>
										<td>Role</td>
										<td><%=user.getRole()%></td>
										</tr>
										<tr>
										<td>Name</td>
										<td><%=user.getName()%></td>
										</tr>
										<tr>
										<td>Gender</td>
										<td><%=user.getGender()%></td>
										</tr>
										<tr>
										<td>Phonenumber</td>
										<td><%=user.getPhonenumber()%></td>
										</tr>
										<tr>
										<td>Address</td>
										<td><%=user.getAddress()%></td>
										</tr>
										<%
												}
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
		aria-labelledby="myModalLabel" aria-hidden="true">
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
							<form role="form">
								<div class="form-group">
									<label>Username</label> <input class="form-control" name="username" readOnly="true">
								</div>
								<div class="form-group">
									<label>Password</label> <input class="form-control"
										name="password">
								</div>
								<div class="form-group">
									<label>Role</label> <input class="form-control" name="role"  readOnly="true">
								</div>
								<div class="form-group">
									<label>Name</label> <input class="form-control" name="name">
								</div>
								<div class="form-group">
									<label>Gender</label> <input class="form-control" name="gender">
								</div>
									<div class="form-group">
									<label>Phonenumber</label> <input class="form-control" name="phonenumber">
								</div>
								<div class="form-group">
									<label>Address</label> <input class="form-control" name="address">
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

	<script src="<%=path%>/bookstore/js/jquery.min.js"></script>
	<script src="<%=path%>/bookstore/js/bootstrap.min.js"></script>
	<script src="<%=path%>/bookstore/js/jquery.dataTables.min.js"></script>
	<script src="<%=path%>/bookstore/js/dataTables.bootstrap.min.js"></script>
	<script src="<%=path%>/bookstore/js/bookstore.js"></script>
	<script src="<%=path%>/bookstore/js/bootbox.min.js"></script>

	<script src="<%=path%>/bookstore/js/user.js"></script>

	<script>
		$(document).ready(function() {
			$('#dataTables').DataTable({
				responsive : true
			});
		});
	</script>

</body>

</html>

