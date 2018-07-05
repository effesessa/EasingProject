<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<title>Judge Manager</title>
<!-- Favicon-->
<link rel="icon" href="favicon.ico" type="image/x-icon">
<script defer
	src="https://use.fontawesome.com/releases/v5.0.8/js/all.js"></script>

<%@ include file="includes/header.jsp" %>

<!-- Animation Css -->
<link href="resources/plugins/animate-css/animate.css" rel="stylesheet" />

<!-- Morris Chart Css-->
<link href="resources/plugins/morrisjs/morris.css" rel="stylesheet" />

<!-- Custom Css -->
<link href="resources/css/style.css" rel="stylesheet">


</head>

<body class="theme-red">
	<!-- Page Loader -->
	<div class="page-loader-wrapper">
		<div class="loader">
			<div class="preloader">
				<div class="spinner-layer pl-red">
					<div class="circle-clipper left">
						<div class="circle"></div>
					</div>
					<div class="circle-clipper right">
						<div class="circle"></div>
					</div>
				</div>
			</div>
			<p>Please wait...</p>
		</div>
	</div>
	<!-- #END# Page Loader -->

	<!-- navbar -->
	<c:if test="${user.professor == false}">
		<jsp:include page="includes/navbarStudent.jsp"></jsp:include>
	</c:if>
	<c:if test="${user.professor == true}">
		<jsp:include page="includes/navbarTeacher.jsp"></jsp:include>
	</c:if>
	<!-- end navbar -->

	<section class="content">
		<div class="container-fluid">
			<div class="block-header">
				<div class="card" style="height: auto !important;">
					<div class="header">
						<h2>Results</h2>
						<div class="panel-group" id="accordion" style="margin-top: 16px;">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion" href="#collapse1"> Users</a>
									</h4>
								</div>
								<div id="collapse1" class="panel-collapse collapse in">
									<div class="panel-body">
										<ul class="list-group">
											<c:forEach items="${UserResult}" var="user">
												<li class="list-group-item">${user.id} - ${user.surname} ${user.name}</li>
											</c:forEach>
										</ul>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion" href="#collapse2"> Subjects</a>
									</h4>
								</div>
								<div id="collapse2" class="panel-collapse collapse">
									<div class="panel-body">
										<ul class="list-group">
											<c:forEach var="subject" items="${SubjectResult}">
												<li class="list-group-item">${subject.name}
													<div style="display: inline;">
														<form:form id="password-form"
															class="navbar-form form-inline"
															action="signUpSubject?name=${subject.name}" method="POST"
															modelAttribute="subjectPasswordForm"
															style="display:inline-block;">
															<div class="form-group">
																<div class="input-group">
																	<input type="text" class="form-control" name="password"	placeholder="Password" />
																</div>
															</div>
															<button class="btn btn-primary button-add" data-toggle="tooltip">Iscriviti</button>
														</form:form>
													</div>
												</li>
											</c:forEach>
										</ul>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion" href="#collapse3"> Contests</a>
									</h4>
								</div>
								<div id="collapse3" class="panel-collapse collapse">
									<div class="panel-body">
										<ul class="list-group">
											<c:forEach var="contest" items="${ContestResult}">
												<li class="list-group-item">${contest.name}
													<div style="display: inline;">
														<form:form id="password-form"
															class="navbar-form form-inline"
															action="signUpSubject?name=${subject.name}" method="POST"
															modelAttribute="subjectPasswordForm"
															style="display:inline-block;">
															<div class="form-group">
																<div class="input-group">
																	<select class="form-control" id="team" name="team">
																		<c:forEach var="team" items="${TeamResult}">
																			<option>${team.name }</option>
																		</c:forEach>
																	</select>
																</div>
															</div>
														</form:form>
														<button class="btn btn-primary button-add" data-toggle="tooltip">Iscriviti</button>
													</div>
												</li>
											</c:forEach>
										</ul>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>


	<!-- Jquery Core Js -->
	<!-- <script src="resources/plugins/jquery/jquery.min.js"></script> -->

	<!-- Bootstrap Core Js -->
	<!-- <script src="resources/plugins/bootstrap/js/bootstrap.js"></script> -->

	<!-- Waves Effect Plugin Js -->
	<script src="resources/plugins/node-waves/waves.js"></script>
	<!-- Custom Js -->
	<script src="resources/js/admin.js"></script>
	<script src="resources/js/pages/index.js"></script>
	<script>
		var $searchButton = $('.navbar-search-submit');
		$searchButton.on('click', function() {
			$('form#navbar-search-form').submit();
		});
	</script>
</body>
</html>