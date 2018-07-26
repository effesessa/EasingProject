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
						<h2>Create Quiz</h2>
					</div>
					<form:form id="quizForm" class="form-horizontal" action="addQuizFake" method="post" modelAttribute="addQuizForm">
						<input type="hidden" id="quizPoints" name="quizPoints" />
						<a type="button" class="btn btn-default btn-sm toRight" href="#" data-toggle="modal" data-target="#generateModal">Generate Quiz</a><br><br>
						<div class="border">
							<div class="form-group">
								<label for="nQuiz_contest">Contest Name</label>
								<div class="input-group text-center">
									<span class="input-group-addon">
										<i class="glyphicon glyphicon-education"></i>
									</span>
									<select class="form-control" id="nQuiz_contest" name="contestName">
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="nQuiz_name">Quiz Name</label>
								<div class="input-group">
									<span class="input-group-addon">
										<i class="glyphicon glyphicon-list-alt"></i>
									</span>
									<input type="text" class="form-control" name="quizName" id="nQuiz_name" placeholder="Quiz name" required autofocus>
								</div>
							</div>
						</div>
						<br>
						<div class="quizQuestions">
							<div class="quizQuestion border" id="question1">
								<div class="form-check">
									<input class="form-check-input questionType" type="radio" name="question_types[question1]" id="question1_open" value="open" checked>
								  	<label class="form-check-label" for="question1_open">Open question</label>
								</div>
								<div class="form-check">
						  			<input class="form-check-input questionType" type="radio" name="question_types[question1]" id="question1_closed" value="closed">
							  		<label class="form-check-label" for="question1_closed">Closed question</label>
								</div>
								<div class="row">
									<div class="col-md-10">
										<div class="form-group">
											<label for="question1_name">Question</label>
											<div class="input-group">
												<span class="input-group-addon">
													<i class="glyphicon glyphicon-question-sign"></i>
												</span>
												<input type="text" class="form-control input-sm questionName" name="questions" id="question1_name" placeholder="Insert question" required autofocus>
											</div>
										</div>
									</div>
									<div class="col-md-2">
										<div class="form-group">
									    	<label for="question1_points">Points</label>
											<div class="input-group">
									    		<span class="input-group-addon">
													<i class="glyphicon glyphicon-time"></i>
												</span>
												<input class="form-control input-sm questionPoints" id="question1_points" name="points" type="number" min=1 step=1 value="5" required />
											</div>
									  	</div>
									</div>
								</div>
								<div class="form-group">
									<label for="question1_tags">Tags</label><br>
									<small class="form-text text-muted">Insert Tags divided by comma or space</small>
									<div class="input-group questionTags">
										<span class="input-group-addon">
											<i class="glyphicon glyphicon-tags"></i>
										</span>
										<input type="text" class="form-control" name="questions_tags[question1]" id="question1_tags">
									</div>
										<small class="form-text text-muted popularQuestionsTags">
										</small>
								</div>
							</div>
						</div>
						<i id="newQuestionBtn" class="material-icons md-36">add</i><br>
						<div class="text-center">
							<button id="createQuizBtn" class="btn btn-lg btn-primary">Create Quiz</button>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
	
	
	<div id="generateModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h3 class="modal-title">Generate Quiz</h3>
				</div>
				<form:form id="generate_form">
			    	<label>How many Questions?</label>
				  	<div class="questionArgument">
					  	<div class="row">
							<div class="col-md-2">
								<div class="form-group">
							    	<label for="generate_nArg1">#</label>
									<div class="input-group">
										<input class="form-control nArgs" id="generate_nArg1" name="generate_nArg" type="number" min=1 step=1 value="1" required />
									</div>
							  	</div>
							</div>
							<div class="col-md-7">
								<div class="form-group">
									<label for="generate_arg1">Tag</label>
									<div class="input-group">
										<span class="input-group-addon">
											<i class="glyphicon glyphicon-tags"></i>
										</span>
										<input type="text" class="form-control" name="generate_arg" id="generate_arg1" placeholder="Insert one Tag" required autofocus>
									</div>
								</div>
							</div>
						</div>
				  	</div>
				  	<hr/>
				  	<p class="form-control-static">TOTALE</p>
				  	<p id="totalArgsNumber" class="form-control-static">1</p><br>
					<i id="generate_newArg" class="material-icons md-24">add</i><br>
					<div class="modal-footer">
						<button id="generateQuizBtn" class="btn btn-lg btn-primary">Generate Quiz</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
	
	<!-- Waves Effect Plugin Js -->
	<script src="resources/plugins/node-waves/waves.js"></script>
	<!-- Custom Js -->
	<script src="resources/js/admin.js"></script>
	<script src="resources/plugins/bootstrap-tagsinput/bootstrap-tagsinput.min.js"></script>
	<script src="resources/js/typeahead.bundle.js"></script>
	<script src="resources/js/createQuizScript.js"></script>
</body>
</html>