<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
	<script type="text/javascript" src="resources/js/jquery.js"></script>
	<script type="text/javascript" src="resources/js/bootstrap.js"></script>
	<link rel="stylesheet" type="text/css" href="resources/css/bootstrap.css" />
	<link rel="stylesheet" type="text/css" href="resources/css/common.css" />
	<!--
		<link rel="stylesheet" type="text/css" href="resources/css/userMainView.css" />
		<link rel="stylesheet" type="text/css" href="resources/css/pizza.css" /> 
		
		<style type="text/css">
			.wrapper {
				padding-left: 15px;
			}
			
			.bubble-feedbacks .bubble-title {
				margin-bottom: 15px;
			}
		</style>
	-->
	
	<title>Problem Page</title>
	
	<meta name="viewport" content="width=device-width" />
</head>
<body>
	<jsp:include page="includes/navbar${typeSession}.jsp" />

	<div class="container">

		<div class="row">
			<div class="col-xs-2"></div>
			<div class="col-xs-8 wrapper">
				<div class="bubble bubble-feedbacks">
					<h4>Problem: ${problem.name}</h4>
					<div class="bubble">
						<ul class="nav navbar-nav navbar-right">
							<c:if test="${userLogged}">
								<a href="#" data-toggle="modal" data-target="#myModal"
									class="btn btn-primary button-bookatable createTeam">Submit</a>
							</c:if>
						</ul>
						<div class="bubble-title">Descrizione del problema:</div>
						<div>${problem.description}</div>
						<br>
						<div>
							Contest: <a href="contest?name=${contest.name}">${contest.name}</a>
						</div>
					</div>
					<div class="bubble">
						<div class="bubble-title">Submit:</div>
						<c:forEach items="${submits}" var="submit">
							<div class="submit">
								<div class="submit">
									<div>Team: ${submit.team.name}</div>
									<div>Score: ${submit.score}</div>
								</div>
								<br>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="myModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Immetti Soluzione</h4>
				</div>
				<form:form class="navbar-form form-inline"
					action="submit?problemId=${problem.id_problem}" method="post"
					modelAttribute="submitForm">
					<div class="form-group">
						<div>Nome del Team:</div>
						<label for="team">Select Team: </label> <select
							class="form-control" id ="team" name="team">
							<c:forEach items="${memberships}" var="member">
								<option>${member.team.name}</option>
							</c:forEach>
						</select>
						<div>Soluzione:</div>
						<input type="text" class="form-control" name="path"
							placeholder="Path">
					</div>
					<br>

					<div class="modal-footer">
						<input type="submit" class="btn btn-primary button-login"
							value="Invia" />
					</div>
				</form:form>
			</div>
		</div>
	</div>

	<div id="confirm" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Sicuro di voler inserire questa soluzione?</h4>
				

					<div class="modal-footer">
						<input type="submit" class="btn btn-primary button-login"
							value="Invia" />
					</div>
				</div>
			</div>
		</div>
	</div>
<script type="text/javascript">

    window.onload = function () {
        if (window.location.hash == '#confirm') {
            alert('Your modal code here!');
        }
    };

</script>
</body>