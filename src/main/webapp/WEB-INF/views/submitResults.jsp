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
	
	<!-- Favicon-->
	<link rel="icon" href="favicon.ico" type="image/x-icon">
	<script defer src="https://use.fontawesome.com/releases/v5.0.8/js/all.js"></script>
	
	<!-- Google Fonts -->
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" type="text/css">
	
	<!-- Bootstrap Core Css -->
	<link href="resources/plugins/bootstrap/css/bootstrap.css" rel="stylesheet">
	
	<!-- Animation Css -->
	<!--
	<link href="resources/plugins/animate-css/animate.css" rel="stylesheet" />
	-->
	
	<!-- Morris Chart Css-->
	<!--
	<link href="resources/plugins/morrisjs/morris.css" rel="stylesheet" />
	-->
	
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
	<!-- TEACHER -->
		<jsp:include page="includes/navbarTeacher.jsp"></jsp:include>
	<!-- end navbar -->
		<section class="content">
			<div class="container-fluid">
				<div class="block-header">
					<div class="card">
					<c:forEach items="${submits}" var="mapElement">
     					<div> Contest: <h4> ${mapElement.key}:</h4></div><br>
         				<c:forEach items="${mapElement.value}" var="listElement" >
             				<div>Team :${listElement.team.name} <div>Score: ${listElement.score}</div></div><br>
          				</c:forEach>
   					</c:forEach>
					</div>
				</div>
			</div>
		</section>
	

	


	<!-- Jquery Core Js -->
	<script src="resources/plugins/jquery/jquery.min.js"></script>

	<!-- Bootstrap Core Js -->
	<script src="resources/plugins/bootstrap/js/bootstrap.js"></script>

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