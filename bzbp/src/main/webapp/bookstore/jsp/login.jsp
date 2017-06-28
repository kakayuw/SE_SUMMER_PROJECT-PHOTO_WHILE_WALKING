<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<%
	String path = request.getContextPath();
	%>
	

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
	<link href="<%=path%>/bookstore/css/bootstrap.min.css" rel="stylesheet">
	<link href="<%=path%>/bookstore/css/dataTables.bootstrap.css"
		rel="stylesheet">
	<link href="<%=path%>/bookstore/css/dataTables.responsive.css"
		rel="stylesheet">
	<link href="<%=path%>/bookstore/css/bookstore.css" rel="stylesheet">
	<link href="<%=path%>/bookstore/css/font-awesome.min.css"
		rel="stylesheet" type="text/css">
    <title>Signin  for Bookstore</title>

    <!-- Bootstrap core CSS -->
    <link href="<%=path%>/bookstore/css/bootstrap.min.css" rel="stylesheet">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="<%=path%>/bookstore/css/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="<%=path%>/bookstore/css/signin.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="<%=path%>/bookstore/css/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    	<link href="<%=path%>/bookstore/css/login.css"
	rel="stylesheet">
  </head>

  <body background="<%=path%>/bookstore/pictures/background.jpg"
  style=" background-repeat:no-repeat ;
background-size:100% 100%;
background-attachment: fixed;">

	<h5>Welcome to my bookstore</h5>
    <div class="container">


      <form class="form-signin">	
    <table>
	<tr>
      	<td><h2 class="form-signin-heading">Please login</h2> </td>
			<td><div class="panel-heading">
							<button class="btn btn-default" type="button" id="add">
								Register
							</button></div></td>
							<button class="btn btn-default" type="button" id="test">
								test mongodb
							</button></div></td>
      	<!--  <td><button class="btn btn-primary btn-lg" id="add" data-toggle="modal" data-target="#myModal">
		Register
		</button></td>  -->
	</tr>
	</table>
        <label for="inputUsername" class="sr-only">Username</label>
        <input class="form-control" name="username" placeholder="Username">
        <label for="inputPassword" class="sr-only">Password</label>
        <input class="form-control" name="password" placeholder="Password">

        <button class="btn btn-lg btn-primary btn-block" type="button" id="login">Sign in</button>
        
      </form>

    </div> <!-- /container -->

	<div class="modal fade" id="modal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h6>Register!</h6>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-lg-12">
							<form role="form">	
								<p6>Please input the following information:</p6>
								<div class="form-group">
									<label>Username</label> <input class="form-control" name="reg_username">
								</div>
								<div class="form-group">
									<label>Password</label> <input class="form-control"
										name="reg_password">
								</div>
								<div class="form-group">
									<label>Role</label> <input class="form-control" name="role">
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


    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="<%=path%>/bookstore/css/ie10-viewport-bug-workaround.js"></script>
    <script src="<%=path%>/bookstore/js/jquery.min.js"></script>
	<script src="<%=path%>/bookstore/js/bootstrap.min.js"></script>
	<script src="<%=path%>/bookstore/js/jquery.dataTables.min.js"></script>
	<script src="<%=path%>/bookstore/js/dataTables.bootstrap.min.js"></script>
	<script src="<%=path%>/bookstore/js/bookstore.js"></script>
	<script src="<%=path%>/bookstore/js/bootbox.min.js"></script>

	<script src="<%=path%>/bookstore/js/login.js"></script>
	
		<script>
		$(document).ready(function() {
			$('#dataTables').DataTable({
				responsive : true
			});
		});
	</script>
  </body>
