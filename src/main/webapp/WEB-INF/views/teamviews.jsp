<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
	<title>I miei Team</title>
	<!-- <script type="text/javascript" src="resources/js/jquery.js"></script>
	<script type="text/javascript" src="resources/js/bootstrap.js"></script> -->
	<%@ include file="includes/header.jsp" %>
	<link rel="stylesheet" type="text/css" href="resources/css/teamviews.css" />
	<meta name="viewport" content="width=device-width" />
	<!-- 
	<script type="text/javascript" src="resources/js/jquery.js"></script>
	<script type="text/javascript" src="resources/js/bootstrap.js"></script>
	<script type="text/javascript" src="resources/js/moment.js"></script>
	<script type="text/javascript" src="resources/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js"></script>
	<script type="text/javascript" src="resources/js/maps.js"></script>
	<script type="text/javascript" src="resources/js/pizzeria/pizzeriaMainView.js"></script>
	<style>
	h2 {
		margin-top: 0;
		margin-bottom: 20px;
	}
	</style>
	<link rel="stylesheet" type="text/css" href="resources/css/userMainView.css" />
	<link rel="stylesheet" type="text/css" href="resources/css/pizza.css" />-->
	<!-- Google Fonts -->
	<link href="resources/css/style.css" rel="stylesheet">
</head>

<body style="padding-top: 0px">
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
	
	
	<jsp:include page="includes/navbarStudent.jsp"></jsp:include>

	<section class="content">
		<div class="container-fluid">
			<div class="block-header">
				<div class="card">
					<div class="header">
						<h2>My Teams</h2>
					</div>
					<div class="body">
						<c:choose>
							<c:when test="${teams.size() > 0}">
								<div class="table-responsive">
									<table class="table table-hover dashboard-task-infos">
										<thead>
											<tr>
												<th>Team Name</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${teams}" var="team">
												<tr>
													<td><a href="viewTeam?name=${team.name}">${team.name}</a></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</c:when>
							<c:otherwise>
								<h4>You are not in any Team now. Create one!</h4>
							</c:otherwise>
						</c:choose>
						<br>
						<a href="#" data-toggle="modal" data-target="#newTeamModal" class="btn btn-lg btn-primary button-bookatable createTeam">Create Team</a>
					</div>
				</div>
			</div>
		</div>
	</section>


	<div id="newTeamModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Create Team</h4>
				</div>
				<form:form action="addTeam" method="post" modelAttribute="addTeamForm" id="newTeamForm">
					<div class="form-group">
						<label for="newTeam_name">Team Name</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-education"></i>
							</span>
							<input type="text" class="form-control" name="name" id="newTeam_name" placeholder="Team Name" required autofocus>
						</div>
					</div>
						
					<div class="form-group">
						<label for="newTeam_member2">Second Member</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-user"></i>
							</span>
							<input type="text" class="form-control" name="member2" id="newTeam_member2" placeholder="Student ID" required>
						</div>
					</div>

					<div class="form-group">
						<label for="newTeam_member3">Third Member</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-user"></i>
							</span>
							<input type="text" class="form-control" name="member3" id="newTeam_member3" placeholder="Student ID" required>
						</div>
					</div>
					
					<input type="submit" class="btn btn-lg btn-primary button-login" value="Add Team" />
				</form:form>
			</div>
		</div>
	</div>
	
	<!-- Waves Effect Plugin Js -->
	<script src="resources/plugins/node-waves/waves.js"></script>
	<!-- Custom Js -->
	<script src="resources/js/admin.js"></script>
	<script src="resources/js/teamviewsScript.js"></script>
</body>

