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
	<link href="resources/css/correctQuizzesStyle.css" rel="stylesheet">
</head>
<body>
	<jsp:include page="includes/navbarTeacher.jsp"></jsp:include>

	<section class="content">
		<div class="container-fluid">
			<div class="block-header">
				<div class="card" style="height: auto !important;">
					<h3>Corrections</h3>
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
												<div class="panel-group" id="accordion_c${contest.key.idcontest}" style="margin-top: 16px;">
													<c:forEach var="quiz" items="${contest.value}" varStatus="quizIndex">
														<c:if test="${not empty quizSubmitQuizzesMap[quiz]}">
															<div class="panel panel-default subPanel">
																<div class="panel-heading">
																	<h4 class="panel-title">
																		<a data-toggle="collapse" data-parent="#accordion_c${contest.key.idcontest}" href="#quiz${quiz.id}"> ${quiz.name}</a>
																	</h4>
																</div>
																<div id="quiz${quiz.id}" class="panel-collapse collapse">
																	
																	<div class="panel-group" id="accordion_q${quiz.id}" style="margin-top: 16px;">
																		<c:forEach var="submit" items="${quizSubmitQuizzesMap[quiz]}">
																			<div class="panel panel-default subPanel">
																				<div class="panel-heading">
																					<h4 class="panel-title">
																						<a data-toggle="collapse" data-parent="#accordion_q${quiz.id}" href="#sub${submit.id}"> ${submit.team.name}</a>
																					</h4>
																				</div>
																				<div id="sub${submit.id}" class="panel-collapse collapse">
																					
																					<form:form class="correctionForm">
																						<c:forEach var="answer" items="${submitQuizSubmitAnswersMap[submit]}" varStatus="subIndex">
																								<c:if test="${answer.question.type == 'OPEN'}">
																									<div class="panel-body">
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
																									</div>
																								</c:if>
																						</c:forEach>
																						<input type="submit" class="btn btn-lg btn-primary" value="Submit" />
																					</form:form>
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