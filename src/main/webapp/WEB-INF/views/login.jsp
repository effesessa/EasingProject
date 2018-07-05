<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<!-- jQuery library -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<!-- Latest compiled JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<!-- <link rel="stylesheet" href="resources/css/style.css"> -->
	<link rel="stylesheet" href="resources/css/login_style.css">
	<title>Easing</title>
</head>
<body>
	<div class="container login-container">
		<div class="row">
			<div class="col-sm-2"/>
				<div class="col-sm-8">
					<div class="card-login-sign-in">
						<div class="card-head">
							<div class="login100-form-title" style="background-image: url(resources/images/unical.jpg);">
							</div>	
						</div>
						<div class="card-body card-login">
							<div class="row">
								<div class="col-sm-12">
									<c:if test="${not empty message}">
											<div id="message" class="alert alert-danger" role="alert">${message}</div>
									</c:if>
									<form:form action="login" method="post" modelAttribute="logInForm" class="login100-form validate-form">
										<div class="wrap-input100 validate-input m-b-26" data-validate="Username is required">
											<span class="label-input100">Username</span>
											<input class="input100 has-val" type="text" name="email" placeholder="Enter username">
											<span class="focus-input100"></span>
										</div>
										<div class="wrap-input100 validate-input m-b-18" data-validate="Password is required">
											<span class="label-input100">Password</span>
											<input class="input100 has-val" type="password" name="password" placeholder="Enter password">
											<span class="focus-input100"></span>
										</div><br>
										<input type="submit" class="login100-form-btn" value="Log in" />
										<button id="btn-sign-in" class="login100-form-btn sign-in" value="Sign in" >Sign in</button>
									</form:form>
								</div>
							</div>
						</div>
						<div class="card-body card-sign">
							<div class="row">
								<div class="col-sm-12">
									<form:form action="signin" method="post" modelAttribute="SignInForm" class="login100-form validate-form">
										<div class="wrap-input100 validate-input m-b-26" data-validate="Username is required">
											<span class="label-input100">Email</span>
											<input class="input100 has-val" type="text" name="email" placeholder="Enter username">
											<span class="focus-input100"></span>
										</div>
										<div class="wrap-input100 validate-input m-b-26" data-validate="Username is required">
											<span class="label-input100">Matricola</span>
											<input class="input100 has-val" type="text" name="id" placeholder="Enter matricola">
											<span class="focus-input100"></span>
										</div>
										<div class="wrap-input100 validate-input m-b-26" data-validate="Username is required">
											<span class="label-input100">Name</span>
											<input class="input100 has-val" type="text" name="name" placeholder="Enter name">
											<span class="focus-input100"></span>
										</div>
										<div class="wrap-input100 validate-input m-b-26" data-validate="Username is required">
											<span class="label-input100">Surname</span>
											<input class="input100 has-val" type="text" name="surname" placeholder="Enter surname">
											<span class="focus-input100"></span>
										</div>
										<div class="wrap-input100 validate-input m-b-18" data-validate="Password is required">
											<span class="label-input100">Password</span>
											<input class="input100 has-val" type="password" name="password" placeholder="Enter password">
											<span class="focus-input100"></span>
										</div>
										<div class="wrap-input100 validate-input m-b-26" data-validate="Username is required">
											<span class="label-input100">Repeat Password</span>
											<input class="input100 has-val" type="password" name="confirm" placeholder="Enter username">
											<span class="focus-input100"></span>
										</div>
										<input type="submit" class="login100-form-btn" value="Sign in" />
										<button id="btn-log-in" class="login100-form-btn sign-in" value="Sign in" >Log in</button>
									</form:form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$('#btn-sign-in').click(function(e) {
			e.preventDefault();
			$(".card-sign").css('display', 'block');
			$(".card-login").css('display', 'none');	
		});
		$('#btn-log-in').click(function(e) {
			e.preventDefault();
			 $(".card-sign").css('display', 'none');
			 $(".card-login").css('display', 'block');
		});

	</script>
</body>
</html>