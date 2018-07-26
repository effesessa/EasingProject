<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<!-- 	<script type="text/javascript" src="resources/js/jquery.js"></script>
	<script type="text/javascript" src="resources/js/bootstrap.js"></script>
	<link rel="stylesheet" type="text/css" href="resources/css/bootstrap.css" />
	<link rel="stylesheet" type="text/css" href="resources/css/common.css" />
	<link rel="stylesheet" type="text/css" href="resources/css/userMainView.css" />
	<link rel="stylesheet" type="text/css" href="resources/css/pizza.css" /> -->
	<%@ include file="includes/header.jsp" %>
	<link href="resources/css/style.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="resources/css/subjectViewStyle.css" />
	
	<title>Subject Page</title>
	
	<meta name="viewport" content="width=device-width" />

</head>
<body>
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
	
	<jsp:include page="includes/navbarStudent.jsp"/>

	<section class="content">
		<div class="container-fluid">
			<div class="block-header">
				<div class="card">
					<div class="header">
						<h2>${name}</h2>
					</div>
					<div class="body">
						<c:choose>
							<c:when test="${contests.size() > 0}">
								<div class="table-responsive">
									<table class="table table-hover dashboard-task-infos">
										<thead>
											<tr>
												<th>Contest Name</th>
												<th>Year</th>
												<th>Deadline</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${contests}" var="contest">
												<tr>
													<td><a href="contest?name=${contest.name}">${contest.name}</a></td>
													<td>${contest.subject.subjectId.year}</td>
													<td>${contest.deadline}</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</c:when>
							<c:otherwise>
								<h4>No Contests available now.</h4>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</div>
	</section>
	
	<!-- Waves Effect Plugin Js -->
	<script src="resources/plugins/node-waves/waves.js"></script>
	<!-- Custom Js -->
	<script src="resources/js/admin.js"></script>
</body>
<script>
	var $searchButton = $('.button-add');
	$searchButton.on('click', function() {
		$('form#password-form').submit();
	});
</script>