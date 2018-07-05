<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<script type="text/javascript" src="resources/js/jquery.js"></script>
<script type="text/javascript" src="resources/js/bootstrap.js"></script>
<link rel="stylesheet" type="text/css" href="resources/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="resources/css/userMainView.css" />
<link rel="stylesheet" type="text/css" href="resources/css/pizza.css" />

<title>Contest Page</title>

<meta name="viewport" content="width=device-width" />
<style>
h2 {
	margin-top: 0;
	margin-bottom: 20px;
}
</style>

</head>
<body>
	<jsp:include page="includes/navbar${typeSession}.jsp"/>

	<div class="container">

		<div class="row">
		<div class="col-xs-2"></div>
		<div class="col-xs-8 wrapper">
				<div class="bubble bubble-feedbacks">
				<h4>Problems:</h4>
			<ul class="nav navbar-nav navbar-right">
				<c:if test="${userLogged}">
							<a href="#"
								data-toggle="modal" data-target="#myModal"
								class="btn btn-primary button-bookatable createTeam">Iscriviti</a>
						</c:if>
			</ul>
					<c:forEach items="${problems}" var="problem">
									<div class="problem">
										<div class="problem">
											<a href="problem?id=${problem.id_problem}">${problem.name}</a>
										</div>
									</div>
								</c:forEach>
		</div>
		</div>
		</div>
	</div>
	<div id="myModal" class="modal fade">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4 class="modal-title">Con quale Team vuoi iscriverti?</h4>
							</div>
	
						<input type="hidden" name="contestrequested" value="${contest}">Contest Id: ${contest}</input>
							<form:form class="navbar-form form-inline" action="subscribe" method="post"
						modelAttribute="subscribeForm">
						<div class="form-group">
							<div>Nome del Team:</div>
							<input type="text" class="form-control" name="team" placeholder="Nome Team">
							<div>Id contest:</div>
							<input class="form-control" name="contest" value="${contest}" readonly>
						</div><br>
						
						<div class="modal-footer">
						<input type="submit" class="btn btn-primary button-login" value="Iscrivi" />
						</div>
					</form:form>
						</div>
										</div>
									</div>
								
						
				
</body>