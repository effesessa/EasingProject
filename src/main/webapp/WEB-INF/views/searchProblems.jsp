<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<title>Judge Manager</title>
	<%@ include file="includes/header.jsp" %>
	<!-- Favicon-->
	<link rel="icon" href="favicon.ico" type="image/x-icon">
	<!-- <script defer src="https://use.fontawesome.com/releases/v5.0.8/js/all.js"></script> -->
	<!-- Custom Css -->
	<link href="resources/css/style.css" rel="stylesheet">


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
	<!-- navbar -->
	<!-- TEACHER -->
		<jsp:include page="includes/navbarTeacher.jsp"></jsp:include>
	<!-- end navbar -->
		<section class="content">
			<div class="container-fluid">
				<div class="block-header">
					<div class="card" style="height: auto !important;">
						<div class="header">
							<h2>Search results</h2>
							<div class="panel-group" id="accordion" style="margin-top: 16px;">
								<div class="panel panel-default">
									<div class="panel-heading">
										<h4 class="panel-title">
											<a data-toggle="collapse" data-parent="#accordion" href="#results"> Problems</a>
										</h4>
									</div>
									<div id="results" class="panel-collapse collapse in">
										<div class="panel-body">
											<ul class="list-group">
												<c:forEach items="${problems}" var="problem">
													<li class="list-group-item">
														<a href="viewSubmits?id=${problem.id_problem}">${problem.name}</a>
													</li>
												</c:forEach>
											</ul>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					
					
<%-- 					<div class="card">
					<c:forEach items="${submits}" var="mapElement">
     					<div> Contest: <h4> ${mapElement.key}:</h4></div><br>
         				<c:forEach items="${mapElement.value}" var="listElement" >
             				<div>Team :${listElement.team.name} <div>Score: ${listElement.score}</div></div><br>
          				</c:forEach>
   					</c:forEach>
					</div> --%>
				</div>
			</div>
		</section>
	

	


	<!-- Waves Effect Plugin Js -->
	<script src="resources/plugins/node-waves/waves.js"></script>
	<!-- Custom Js -->
	<script src="resources/js/admin.js"></script>
	<!-- <script src="resources/js/pages/index.js"></script> -->
<!-- 	<script>
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
	</script> -->
</body>

</html>