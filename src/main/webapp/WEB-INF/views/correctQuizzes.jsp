<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Easing - ${team.name}</title>
	<%@ include file="includes/header.jsp" %>
	<link href="resources/css/style.css" rel="stylesheet">
</head>
<body>
	<jsp:include page="includes/navbarTeacher.jsp"></jsp:include>

	<section class="content">
		<div class="container-fluid">
			<div class="block-header">
				<div class="card" style="height: auto !important;">
					<div class="header">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h2>Correct Quizzes</h2>
							</div>
						</div>
					</div>
					<h3>Submit</h3>
					<div class="panel panel-default">
						<div class="panel-body">
							<div class="panel-group" id="accordion" style="margin-top: 16px;">
								<c:forEach var="contest" items="${contestQuizzesMap}" varStatus="index">
									<c:if test="${not empty contest.value}">
										<div class="panel panel-default">
											<div class="panel-heading">
												<h4 class="panel-title">
													<a data-toggle="collapse" data-parent="#accordion" href="#con${contest.key.idcontest}"> ${contest.key.name }</a>
												</h4>
											</div>
											<div id="con${contest.key.idcontest}" class="panel-collapse collapse ${index.first ? 'in' : ''}">
												<div class="panel-group" id="accordion2" style="margin-top: 16px;">
													<c:forEach var="quiz" items="${contest.value}" varStatus="quizIndex">
														<c:if test="${not empty quizSubmitQuizzesMap[quiz]}">
															<div class="panel panel-default">
																<div class="panel-heading">
																	<h4 class="panel-title">
																		<a data-toggle="collapse" data-parent="#accordion2" href="#quiz${quiz.id}"> ${quiz.name}</a>
																	</h4>
																</div>
																<div id="quiz${quiz.id}" class="panel-collapse collapse">
																	
																	<div class="panel-group" id="accordion3" style="margin-top: 16px;">
																		<c:forEach var="submit" items="${quizSubmitQuizzesMap[quiz]}">
																			<div class="panel panel-default">
																				<div class="panel-heading">
																					<h4 class="panel-title">
																						<a data-toggle="collapse" data-parent="#accordion3" href="#sub${submit.id}"> ${submit.team.name}</a>
																					</h4>
																				</div>
																				<div id="sub${submit.id}" class="panel-collapse collapse">
																					
																					<c:forEach var="answer" items="${submitQuizSubmitAnswersMap[submit]}" varStatus="subIndex">
																							<div class="panel-body">
																								<c:if test="${answer.question.type == 'OPEN'}">
																									<br>
																									<div class="panel panel-default">
																										<div class="panel-heading">
																											QUESTION POINTS: ${answer.question.points }
																										</div>
																									</div>
																									<div class="border">
																										<div class="row">
																											<div class="form-group">
																										  		<label class="col-sm-1 control-label">Question</label>
																										  		<div class="col-sm-11">
																									    			<p class="form-control-static">${answer.question.text }</p>
																										  		</div>
																											</div>
																											<div class="form-group">
																												<label class="col-sm-1 control-label">Answer</label>
																												<div class="col-sm-11">
																													<p class="form-control-static">${answer.openAnswer }</p>
																												</div>
																											</div>
																											<div class="form-group">
																												<label class="col-sm-1 control-label" for="ans${answer.id}">Rating /${answer.question.points}</label>
																												<div class="col-sm-11 input-group">
																													<input class="form-control input-sm" id="ans${answer.id}" name="points[${answer.id}]" type="number" min=0 max="${answer.question.points}" step=1 value=0 required />
																												</div>
																										  	</div>
																										</div>
																									</div>
																								</c:if>
																							</div>
																					</c:forEach>

																				</div>
																			</div>
																		</c:forEach>
																	</div>	
																	
																</div>
															</div>
														</c:if>
													</c:forEach>
													
												</div>
											</div>
										</div>
									</c:if>
								</c:forEach>
							</div>
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