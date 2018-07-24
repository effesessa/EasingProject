<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Easing - I miei Quiz</title>
	<%@ include file="includes/header.jsp" %>
	<link href="resources/css/style.css" rel="stylesheet">
	<link href="resources/css/myQuizzesStyle.css" rel="stylesheet">
</head>
<body>
	<jsp:include page="includes/navbarTeacher.jsp"></jsp:include>
	
	<section class="content">
		<div class="container-fluid">
			<div class="block-header">
				<div class="card" style="height: auto !important;">
					<div class="header">
						<h2>I miei Quiz</h2>
						Verranno visualizzati solo i Quiz creati da una Giuria di cui sei leader.
						<div class="panel-group" id="accordion" style="margin-top: 16px;">
							<c:forEach var="contestMap" items="${contestQuizzesMap}" varStatus="index">
								<div class="panel panel-default">
									<div class="panel-heading">
										<h4 class="panel-title">
											<a data-toggle="collapse" data-parent="#accordion" href="#${contestMap.key.idcontest }"> ${contestMap.key.name }</a>
										</h4>
									</div>
									<div id="${contestMap.key.idcontest }" class="panel-collapse collapse ${index.first ? 'in' : ''}">
										<div class="panel-body">
											<ul class="list-group">
												<c:forEach items="${contestMap.value}" var="quiz">
													<li class="list-group-item">
														<a href="quizSubmits?idQuiz=${quiz.id}">${quiz.name}</a>
														<span class="toRight">
															<input class="btn btn-success cloneQuizBtn" type="button" value="Clona" data-id="${quiz.id}" data-toggle="modal" data-target="#cloneQuizModal" />
															<input class="btn btn-danger deleteQuizBtn" type="button" value="Elimina" data-id="${quiz.id}" />
														</span>
													</li>
												</c:forEach>
											</ul>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	
	<div id="cloneQuizModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h3 class="modal-title">Clona un Quiz</h3>
				</div>
				<form:form action="quiz" method="post" enctype="multipart/form-data">
					<input type="hidden" name="op" value="cloneQuiz"/>
					<input type="hidden" name="id" id="cloneQ_id" value=""/> 
					<div class="form-group">
						<label for="cloneQ_contestName">Seleziona un Contest di destinazione</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-education"></i>
							</span>
							<select class="form-control" id="cloneQ_contestName" name="contestName" required>
							</select>
						</div>
					</div>
					<div class="modal-footer">
						<input type="submit" class="btn btn-primary button-login" value="Clona Quiz" />
					</div>
				</form:form>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
	
	
	<!-- Waves Effect Plugin Js -->
	<script src="resources/plugins/node-waves/waves.js"></script>
	<!-- Custom Js -->
	<script src="resources/js/admin.js"></script>
	<script src="resources/js/bootbox.min.js"></script>
	<script src="resources/js/myQuizzesScript.js"></script>
</body>
</html>