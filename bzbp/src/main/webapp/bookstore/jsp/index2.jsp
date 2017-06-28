<%@ page import="java.util.ArrayList"%>
<%@ page import="model.Book"%>
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
<link href="<%=path%>/bookstore/css/homepage.css"
	rel="stylesheet">
</head>

<body>
	 <%
	 	String session_username=(String)session.getAttribute("username");
	 	String session_role=(String)session.getAttribute("role"); 
	 	if (session_username == null || !session_role.equals("Customer")){
	 		response.sendRedirect("login_register");
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
					<li><a href="homePage" class="active"><i class="fa fa-home fa-fw"></i>
							Homepage</a></li>
					<li><a href="Userprofile" ><i
							class="fa fa-book fa-fw"></i> User profile</a></li>
					<li><a href="customerBookview"><i class="fa fa-user fa-fw"></i>
							View books</a></li>
					<li><a href="Shoppingcart" ><i
							class="fa fa-book fa-fw"></i> My Shopping cart</a></li>
				</ul>
			</div>
			<!-- /.sidebar-collapse -->
		</div>  </nav>

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h5>Welcome to my bookstore</h5>
				</div>
			</div>
			<div class="row">
			<div>
				<h6>Today's Special:</h6>
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">

						
	<table>
	<tr>
		<td><img src="<%=path%>/bookstore/pictures/ICS.jpg" alt="" width="500" height="500"/></td>
		<td><img src="<%=path%>/bookstore/pictures/java.jpg" alt="" width="350" height="500"/></td>
	</tr>
	<tr>
		<td id = "price">Price:88RMB</td>
		<td id = "price">Price:20RMB</td>
	</tr>
	</table>					

				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /#page-wrapper -->
		
	</div>
	<!-- /#wrapper -->
	



	<script src="<%=path%>/bookstore/js/jquery.min.js"></script>
	<script src="<%=path%>/bookstore/js/bootstrap.min.js"></script>
	<script src="<%=path%>/bookstore/js/jquery.dataTables.min.js"></script>
	<script src="<%=path%>/bookstore/js/dataTables.bootstrap.min.js"></script>
	<script src="<%=path%>/bookstore/js/bookstore.js"></script>
	<script src="<%=path%>/bookstore/js/bootbox.min.js"></script>

	<script src="<%=path%>/bookstore/js/book.js"></script>

	<script>
		$(document).ready(function() {
			$('#dataTables').DataTable({
				responsive : true
			});
		});
	</script>

</body>

</html>

