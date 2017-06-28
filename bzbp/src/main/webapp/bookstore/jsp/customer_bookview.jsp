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
		ArrayList<Book> bookList = new ArrayList<Book>();
			if (request.getAttribute("books") != null) {
		bookList = (ArrayList<Book>) request.getAttribute("books");
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
							class="fa fa-book fa-fw"></i> User profile</a></li>
					<li><a href="customerBookview"><i class="fa fa-user fa-fw" class="active"></i>
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
					<h1 class="page-header">All the Books</h1>
				</div>
				<div class="col-lg-12">
				<label>Amount:</label> <input class="form-control"
											 type="number" step="1" name="amount">
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
									<thead>
										<tr>
										    <th>ID</th>
											<th>Title</th>
											<th>Author</th>
											<th>Price</th>
											<th>Publisher</th>
											<th>Date</th>
											<th></th>
										</tr>
									</thead>
									<tbody>
										<%
											for (int i = 0; i < bookList.size(); i++) {
														Book book = bookList.get(i);
										%>
										<tr>
										    <td><%=book.getId()%></td>
											<td><%=book.getTitle()%></td>
											<td><%=book.getAuthor()%></td>
											<td><%=book.getPrice()/100%></td>
											<td><%=book.getPublisher()%></td>
											<td><%=book.getDate()%></td>
											<td>
												<button class="toclick" type="button"  id="buybook"
													data-id="<%=book.getId()%>"
													data-title="<%=book.getTitle()%>"
													data-author="<%=book.getAuthor()%>"
													data-price="<%=book.getPrice()%>"
													data-publisher="<%=book.getPublisher()%>"
													data-date="<%=book.getDate()%>">
													Add to shopping cart
												</button>
											</td>
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

	<script src="<%=path%>/bookstore/js/jquery.min.js"></script>
	<script src="<%=path%>/bookstore/js/bootstrap.min.js"></script>
	<script src="<%=path%>/bookstore/js/jquery.dataTables.min.js"></script>
	<script src="<%=path%>/bookstore/js/dataTables.bootstrap.min.js"></script>
	<script src="<%=path%>/bookstore/js/bookstore.js"></script>
	<script src="<%=path%>/bookstore/js/bootbox.min.js"></script>

	<script src="<%=path%>/bookstore/js/customer_book.js"></script>

	<script>
		$(document).ready(function() {
			$('#dataTables').DataTable({
				responsive : true
			});
		});
	</script>

</body>

</html>

