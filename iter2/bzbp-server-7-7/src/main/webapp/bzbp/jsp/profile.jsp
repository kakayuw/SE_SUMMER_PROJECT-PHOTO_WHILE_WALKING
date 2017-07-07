<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="model.Profile, java.util.List,java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Profile</title>
</head>
<body>
<%
		List<Profile> pList = new ArrayList<Profile>();
		if (request.getAttribute("profiles") != null) {
			pList = (List<Profile>) request.getAttribute("profiles");
		}
		System.out.print(pList.size());
	%>

<div class="col-lg-12">
					<div class="panel panel-default">
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover"
									id="dataTables">
									<thead>
										<tr>
										    <th>UID</th>
											<th>Intro</th>
											<th>Autograph</th>
										</tr>
									</thead>
									<tbody>
										<%
											for (int i = 0; i < pList.size(); i++) {
													Profile p = pList.get(i);
										%>
										<tr>
										    <td><%=p.getUid()%></td>
											<td><%=p.getIntro()%></td>
											<td><%=p.getAutograph()%></td>
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

</body>
</html>