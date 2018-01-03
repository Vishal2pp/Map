<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!Doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="Libraries/bootstrap/css/bootstrap.min.css">
<title>Admin Login</title>
<link rel="icon" type="image/png" href="images/login.png">
</head>

<body>

	<div class="container" style="padding-top: 15px">
		<div class="panel panel-primary" style="height: 434px;">

			<div class="panel-heading">OLABS WORKSHOP</div>
			<div class="panel-body">

				<div class="panel panel-body">
					<center>
						<h1>Login</h1>
					</center>
					<center>
						<form name="login" method="post" action="checkLogin">
							<table>
							
								<tr>
									<td>User Name:  </td>
									<td><input type="text" class="form-control" name="u_name" align="right" /></td>
								</tr>
							<tr><td></br></td></tr>
								<tr>
									<td>Password: </td>
									
									<td><input type="password" name="u_password" class="form-control" align="right" /></td>
								</tr>
								<tr><td></br></td></tr>
								<tr>
									<td></td>
									<td>
										<center>
											<input type="submit" name="submit" Value="Login" />
										</center>
									</td>
								</tr>
							</table>
						</form>
					</center>




				</div>
			</div>
		</div>
	</div>
</body>
</html>