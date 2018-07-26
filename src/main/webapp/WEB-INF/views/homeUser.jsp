<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<%@ include file="includes/header.jsp"%>
<!-- Favicon-->
<link rel="icon" href="favicon.ico" type="image/x-icon">

<!-- Google Fonts -->
<!-- <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" type="text/css"> -->

<!-- Bootstrap Core Css -->
<!-- <link href="resources/plugins/bootstrap/css/bootstrap.css" rel="stylesheet"> -->

<!-- Animation Css -->
<!-- <link href="resources/plugins/animate-css/animate.css" rel="stylesheet" /> -->

<!-- Morris Chart Css-->
<!-- <link href="resources/plugins/morrisjs/morris.css" rel="stylesheet" />  -->

<!-- Custom Css -->
<link href="resources/css/style.css" rel="stylesheet">
<link href="resources/css/smallball.css" rel="stylesheet">

<!-- Jquery Core Js -->
<!-- <script src="resources/plugins/jquery/jquery.min.js"></script> -->

<!-- Bootstrap Core Js -->
<!-- <script src="resources/plugins/bootstrap/js/bootstrap.js"></script> -->

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
	<c:if test="${user.professor == false}">
		<section class="content">
			<div class="container-fluid">
				<div class="block-header">
					<div class="card">
						<div class="header">
							<h2>YOUR SUBMISSIONS</h2>
						</div>
						<div class="body">
							<div class="table-responsive">
								<table class="table table-hover dashboard-task-infos">
									<thead>
										<tr>
											<th>Contest</th>
											<th>Problem</th>
											<th>Team</th>
											<th>Date</th>
											<th>Language</th>
											<th>Score</th>
											<th>Status</th>
											<th>Code</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${submits}" var="submit">
											<tr>
												<td>${submit.problem.id_contest.idcontest}</td>
												<td>${submit.problem.name}</td>
												<td>${submit.team.name}</td>
												<td>${submit.date}</td>
												<td>
												<c:choose>
													<c:when test="${submit.type=='java'}">
														<span class="smallball smallball-java"></span> java
													</c:when>
													<c:when test="${submit.type=='cpp'}">
														<span class="smallball smallball-cpp"></span> c++
													</c:when>
													<c:when test="${submit.type=='py'}">
														<span class="smallball smallball-python"></span> python
													</c:when>
													<c:when test="${submit.type=='c'}">
														<span class="smallball smallball-c"></span> c
													</c:when>
													<c:when test="${submit.type=='dlv'}">
														<span class="smallball smallball-dlv"></span> dlv
													</c:when>
													<c:otherwise>
														<span class="smallball"></span>
													</c:otherwise>
												</c:choose>
												</td>
												<td>${submit.score}</td>
												<td>
												<c:choose>
													<c:when test="${submit.info=='CORRECT'}">
														<span class="label label-success">CORRECT</span>
													</c:when>
													<c:when test="${submit.info=='WRONG_ANSWER'}">
														<span class="label label-danger">WRONG_ANSWER</span>
													</c:when>
													<c:when test="${submit.info=='COMPILE_ERROR'}">
														<span class="label label-danger">COMPILE_ERROR</span>
													</c:when>
													<c:when test="${submit.info=='TIME_LIMIT_EXIT'}">
														<span class="label label-warning">TIME_LIMIT_EXIT</span>
													</c:when>
													<c:when test="${submit.info=='RUN_TIME_ERROR'}">
														<span class="label label-default">RUN_TIME_ERROR</span>
													</c:when>
													<c:when test="${submit.info=='EXECUTION_ERROR'}">
														<span class="label label-default">EXECUTION_ERROR</span>
													</c:when>
													<c:otherwise>
														<span class="label bg-info">UNKNOWN_ERROR</span>
													</c:otherwise>
												</c:choose>
												</td>
												<td>
													<a href="viewSubmit?submitId=${submit.id }">
														<i class="material-icons">get_app</i>
													</a>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-7">
							<div class="card">
								<div class="header">
									<h2>CONTEST SUBSCRIBED</h2>
								</div>
								<div class="body">
									<div class="table-responsive">
										<table class="table table-hover dashboard-task-infos">
											<thead>
												<tr>
													<th>Name</th>
													<th>Course</th>
													<th>Problems</th>
													<th>Deadline</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${contests}" var="contest">
													<tr>
														<td><a href="contest?name=${contest.contest.name}">${contest.contest.name}</a></td>
														<td>${contest.contest.subject.name}</td>
														<td>
															<button onclick="myFunction()" class="dropbtn">Dropdown</button>
															<div id="myDropdown" class="dropdown-content">
																<a href="javascript:void(0);">Home</a> <a href="#about">About</a>
																<a href="#contact">Contact</a>
															</div>
														</td>
														<td>${contest.contest.deadline}</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
						<div class="col-sm-5">
							<div class="card">
								<div class="header">
									<h2>COURSES FOLLOWED</h2>
								</div>
								<div class="body">
									<div class="table-responsive">
										<table class="table table-hover dashboard-task-infos">
											<thead>
												<tr>
													<th>Course</th>
													<th>Page</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${subjects}" var="subject">
													<tr>
														<td>${subject}</td>
														<td><a href="<c:url value='subject?name=${subject}'/>"><i class="fas fa-link" style="color: #bf0418; font-size: 1.1em;"></i></a></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>

					</div>
				</div>
			</div>
		</section>
	</c:if>

	<c:if test="${user.professor == true}">
		<!--                               PROFESSOR PAGE                         -->
		<section class="content">
			<div class="container-fluid">
				<div class="block-header">
					<div class="card">
						<div class="header">
							<h2>CONTEST CREATED</h2>
						</div>
						<div class="body">
							<div class="table-responsive">
								<table class="table table-hover dashboard-task-infos">
									<thead>
										<tr>
											<th>Name</th>
											<th>Deadline</th>
											<th>Subject</th>
											<th>Year</th>
											<th>Jury</th>
											<th>Open</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${contests}" var="contest">
											<tr>
												<td>${contest.name}</td>
												<td>${contest.deadline}</td>
												<td>${contest.subject.subjectId.id_subject}</td>
												<td>${contest.subject.subjectId.year}</td>
												<td>${contest.jury.id_jury}</td>
												<td>${contest.restriction}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-7">
							<div class="card">
								<div class="header">
									<h2>Active Subjects</h2>
								</div>
								<div class="body">
									<div class="table-responsive">
										<table class="table table-hover dashboard-task-infos">
											<thead>
												<tr>
													<th>Name</th>
													<th>Id Subject</th>
													<th>Year</th>
													<th>Password</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${subjects}" var="subject">
													<tr>
														<td>${subject.name}</td>
														<td>${subject.subjectId.id_subject}</td>
														<td>${subject.subjectId.year}</td>
														<td>
															<button onclick="myFunction()" class="dropbtn">${subject.password}</button>
															<div id="myDropdown" class="dropdown-content">
																<a href="javascript:void(0);">Reimposta</a>
															</div>
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
						<div class="col-sm-5">
							<div class="card">
								<div class="header">
									<h2>JURY MEMBER</h2>
								</div>
								<div class="body">
									<div class="table-responsive">
										<table class="table table-hover dashboard-task-infos">
											<thead>
												<tr>
													<th>Jury Id</th>
													<th>Contest</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${contestjuries}" var="jury">
													<tr>
														<td>${jury.jury.id_jury}</td>
														<td>${jury.name}</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>

					</div>
				</div>
			</div>
		</section>
	</c:if>




	<!-- <script src="resources/plugins/jquery/jquery.min.js"></script> -->

	<!-- Bootstrap Core Js -->
	<!-- 	<script src="resources/plugins/bootstrap/js/bootstrap.js"></script> -->
	<!-- Waves Effect Plugin Js -->
	<script src="resources/plugins/node-waves/waves.js"></script>
	<!-- Custom Js -->
	<script src="resources/js/admin.js"></script>

	<!--<script src="resources/js/pages/index.js"></script>-->
	<script>
		var $searchButton = $('.navbar-search-submit');
		$searchButton.on('click', function() {
			$('form#navbar-search-form').submit();
		});

		/* When the user clicks on the button, 
		toggle between hiding and showing the dropdown content */
		function myFunction() {
			document.getElementById("myDropdown").classList.toggle("show");
		}

		// Close the dropdown if the user clicks outside of it
		window.onclick = function(event) {
			if (!event.target.matches('.dropbtn')) {

				var dropdowns = document
						.getElementsByClassName("dropdown-content");
				var i;
				for (i = 0; i < dropdowns.length; i++) {
					var openDropdown = dropdowns[i];
					if (openDropdown.classList.contains('show')) {
						openDropdown.classList.remove('show');
					}
				}
			}
		}
	</script>

</body>

</html>