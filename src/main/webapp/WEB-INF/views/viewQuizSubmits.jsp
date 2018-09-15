<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Easing</title>
	<%@ include file="includes/header.jsp" %>
	<link href="resources/css/style.css" rel="stylesheet">
	<link href="resources/css/viewQuizSubmitsStyle.css" rel="stylesheet">
</head>
<body>
	<jsp:include page="includes/navbarTeacher.jsp"></jsp:include>
	
	<section class="content">
		<div class="container-fluid">
			<div class="block-header">
				<div class="card" style="height: auto !important;">
					<div class="header">
						<h2>Submits</h2>
						<div class="panel-group" id="accordion" style="margin-top: 16px;">
							<c:forEach var="teamsSubmit" items="${submitQuizSubmitAnswersMap}" varStatus="index">
								<div class="panel panel-default">
									<div class="panel-heading">
										<h4 class="panel-title">
											<a data-toggle="collapse" data-parent="#accordion" href="#${teamsSubmit.key.team.id }"> ${teamsSubmit.key.team.name } ${teamsSubmit.key.correction ? "<div class='text-danger'> PENDING FOR REVIEW </div>" : "" }</a>
										</h4>
									</div>
									<div id="${teamsSubmit.key.team.id}" class="panel-collapse collapse ${index.first ? 'in' : ''}">
										<div class="panel-body">
											<c:forEach items="${teamsSubmit.value}" var="submit">
												<br>
												<div class="panel panel-default">
													<div class="panel-heading">
														QUESTION POINTS: ${submit.question.points }
													</div>
												</div>
											<div class="border">
												<c:choose>
													<c:when test="${submit.question.type == 'OPEN' }">
													<div class="row">
														<div class="form-group">
													  		<label class="col-sm-1 control-label">Question</label>
													  		<div class="col-sm-11">
												    			<p class="form-control-static">${submit.question.text }</p>
													  		</div>
														</div>
														<div class="form-group">
															<label class="col-sm-1 control-label">Answer</label>
															<div class="col-sm-11">
																<p class="form-control-static">${submit.openAnswer }</p>
															</div>
														</div>
													</div>
													</c:when>
													<c:otherwise>
														<div class="form-group">
													  		<label class="col-sm-1 control-label">Question</label>
													  		<div class="col-sm-11">
												    			<p class="form-control-static">${submit.question.text }</p>
													  		</div>
														</div>
														<div class="answers">
															<c:forEach items="${submit.question.answers}" var="answer">
																<div class="form-check">
																	<label>
																		<input type="radio" name="team${teamsSubmit.key.team.id}question_answer['${submit.question.id}']" ${answer.id == submit.answer.id ? "checked" : "" } disabled>
																		${answer.text }
																	</label>
																</div>
															</c:forEach>
														</div>
													</c:otherwise>
												</c:choose>
											</div>
											</c:forEach>
										</div>
										<div class="panel-footer">
											TOTAL POINTS: ${teamsSubmit.key.totalScore }/${teamsSubmit.key.quiz.points }
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
	
	<!-- Waves Effect Plugin Js -->
	<script src="resources/plugins/node-waves/waves.js"></script>
	<!-- Custom Js -->
	<script src="resources/js/admin.js"></script>
</body>
</html>