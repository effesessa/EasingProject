<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Easing - Crea un Quiz</title>
	<%@ include file="includes/header.jsp" %>
	<link href="resources/css/style.css" rel="stylesheet">
	<link href="resources/css/createQuizStyle.css" rel="stylesheet">
</head>
<body>
	<jsp:include page="includes/navbarTeacher.jsp"></jsp:include>
	
	<section class="content">
		<div class="container-fluid">
			<div class="block-header">
				<div class="card" style="height: auto !important;">
					<div class="header">
						<h2>Crea un Quiz</h2>
					</div>
					<form:form id="quizForm" class="form-horizontal" action="addQuiz" method="post" modelAttribute="addQuizForm">
						<div class="border">
							<div class="form-group">
								<label for="nQuiz_contest">Nome Contest</label>
								<div class="input-group text-center">
									<span class="input-group-addon">
										<i class="glyphicon glyphicon-education"></i>
									</span>
									<select class="form-control" id="nQuiz_contest" name="contestName">
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="nQuiz_name">Nome Quiz</label>
								<div class="input-group">
									<span class="input-group-addon">
										<i class="glyphicon glyphicon-list-alt"></i>
									</span>
									<input type="text" class="form-control" name="name" id="nQuiz_name" placeholder="Nome del Quiz" required autofocus>
								</div>
							</div>
						</div>
						<br>
						<div class="quizQuestions">
							<div class="quizQuestion border" id="question1">
								<div class="form-check">
									<input class="form-check-input questionType" type="radio" name="question1_type" id="question1_open" value="open" checked>
								  	<label class="form-check-label" for="question1_open">Domanda aperta</label>
								</div>
								<div class="form-check">
						  			<input class="form-check-input questionType" type="radio" name="question1_type" id="question1_closed" value="closed">
							  		<label class="form-check-label" for="question1_closed">Domanda chiusa</label>
								</div>
								<div class="row">
									<div class="col-md-10">
										<div class="form-group">
											<label for="question1_name">Domanda</label>
											<div class="input-group">
												<span class="input-group-addon">
													<i class="glyphicon glyphicon-question-sign"></i>
												</span>
												<input type="text" class="form-control input-sm questionName" name="name" id="question1_name" placeholder="Inserisci la domanda" required autofocus>
											</div>
										</div>
									</div>
									<div class="col-md-2">
										<div class="form-group">
									    	<label for="question1_points">Punti domanda</label>
											<div class="input-group">
									    		<span class="input-group-addon">
													<i class="glyphicon glyphicon-time"></i>
												</span>
												<input class="form-control input-sm questionPoints" id="question1_points" name="points" type="number" min=1 step=1 value="5" required />
											</div>
									  	</div>
									</div>
								</div>
							</div>
						</div>
						<i id="newQuestionBtn" class="material-icons md-36">add</i>
						<button class="btn">Crea Quiz</button>
						
					</form:form>
				</div>
			</div>
		</div>
	</section>
	
	<!-- Waves Effect Plugin Js -->
	<script src="resources/plugins/node-waves/waves.js"></script>
	<!-- Custom Js -->
	<script src="resources/js/admin.js"></script>
	<script src="resources/js/createQuizScript.js"></script>
</body>
</html>