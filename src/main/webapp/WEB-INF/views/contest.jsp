<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page session="false"%>
<html>
<head>
	<title>Judge Manager</title>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<!-- Favicon-->
	<link rel="icon" href="favicon.ico" type="image/x-icon">
	<script defer src="https://use.fontawesome.com/releases/v5.0.8/js/all.js"></script>
	<!-- Animation Css -->
	<link href="resources/plugins/animate-css/animate.css" rel="stylesheet" />
	<!-- Morris Chart Css-->
	<link href="resources/plugins/morrisjs/morris.css" rel="stylesheet" />
	
	<%@ include file="includes/header.jsp" %>
	<link href="resources/css/style.css" rel="stylesheet">
	<link href="resources/css/contest.css" rel="stylesheet">
</head>
<body>
	<jsp:include page="includes/navbarStudent.jsp"></jsp:include>
	<section class="content">
		<div class="container-fluid">
			<div class="block-header">
				<div class="card modify-card">
					<div class="pn-ProductNav_Wrapper">
						<nav id="pnProductNav" class="pn-ProductNav">
							<div id="pnProductNavContents" class="pn-ProductNav_Contents">
								<c:forEach items="${problems}" var="problem" varStatus="status">
									<a href="#" data-nav="problem-${problem.id_problem}" class="pn-ProductNav_Link" ${status.first ? 'aria-selected="true"' : ''}>${problem.name }</a>
								</c:forEach>
								<c:forEach items="${quizzes}" var="quiz" varStatus="status">
									<a href="#" data-nav="quiz-${quiz.id}" class="pn-ProductNav_Link">${quiz.name }</a>
								</c:forEach>
								<span id="pnIndicator" class="pn-ProductNav_Indicator"></span>
							</div>
						</nav>
						<button id="pnAdvancerLeft" class="pn-Advancer pn-Advancer_Left" type="button">
							<svg class="pn-Advancer_Icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 551 1024">
								<path d="M445.44 38.183L-2.53 512l447.97 473.817 85.857-81.173-409.6-433.23v81.172l409.6-433.23L445.44 38.18z" />
							</svg>
						</button>
						<button id="pnAdvancerRight" class="pn-Advancer pn-Advancer_Right" type="button">
							<svg class="pn-Advancer_Icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 551 1024">
								<path d="M105.56 985.817L553.53 512 105.56 38.183l-85.857 81.173 409.6 433.23v-81.172l-409.6 433.23 85.856 81.174z" />
							</svg>
						</button>
					</div>
					<div class="body">
						<c:forEach items="${problems}" var="problem" varStatus="status">
							<div id="problem-${problem.id_problem}" ${status.first ? '' : 'style="display:none"'}>
								<small class="form-text text-muted">Clicca sul problema per vedere la traccia</small>
								<h3>Problema <span class="label label-primary"><a target="_blank" href="${pageContext.servletContext.contextPath }/files/${problem.id_problem}">${problem.name}</a></span></h3><br>
								<c:if test="${problem.show_testcase}">
									<c:choose>
										<c:when test="${(problem.type == 'zip')}">
											<h4>Test-Case</h4>
											<a href="${pageContext.servletContext.contextPath }/testCase/input/${problem.id_problem}" class="btn btn-info">File di input</a><br><br>
										</c:when>
										<c:when test="${(problem.type == 'txt') || (problem.type == 'dlv') || (problem.type == 'dat')}">
											<h4>Test-Case</h4>
											<a href="${pageContext.servletContext.contextPath }/testCase/input/${problem.id_problem}" class="btn btn-info">File di input</a>
											<a href="${pageContext.servletContext.contextPath }/testCase/output/${problem.id_problem}" class="btn btn-info">File di output</a><br><br>
										</c:when>
									</c:choose>
								</c:if>
								<div class="bubble">
									<h4><span class="label label-warning">Descrizione</span></h4>
									<div class="well well-sm">${problem.description}</div>
									<br>
								</div><br>
								<c:if test="${not empty submits}">
									<div class="panel-group" id="prob${problem.id_problem }accordion" style="margin-top: 16px;">
										<c:forEach var="team" items="${teams}" varStatus="index">
											<div class="panel panel-default">
												<div class="panel-heading">
													<h4 class="panel-title">
														<a data-toggle="collapse" data-parent="#prob${problem.id_problem }accordion" href="#prob${problem.id_problem }team${team.id }"> ${team.name }</a>
													</h4>
												</div>
												<div id="prob${problem.id_problem }team${team.id }" class="panel-collapse collapse ${index.first ? 'in' : ''}">
													<table class="table table-hover table-responsive">
														<thead class="headTable">
													  		<tr>
															    <th>Info</th>
															    <th class="hidden-xs">Score</th>
															    <th>Problema</th>
															    <th class="hidden-xs">Linguaggio</th>
													  		</tr>
														</thead>
														<tbody id="table_prob${problem.id_problem }_team${team.id }">
															<c:forEach items="${submits}" var="submit">
																<c:if test="${team.id == submit.team.id && submit.problem.id_problem == problem.id_problem }">
																	<tr id="table_prob${problem.id_problem }_team${team.id }_sub${submit.id }">
																		<td>
																			<a href="viewSubmit?submitId=${submit.id }">
																				<c:choose>
																					<c:when test="${submit.info=='CORRECT'}">
																						<span class="label label-success">CORRECT</span>
																					</c:when>
																					<c:when test="${submit.info=='WRONG_ANSWER'}">
																						<span class="label label-danger">WRONG_ANSWER</span>
																					</c:when>
																					<c:when test="${submit.info=='COMPILE_ERROR'}">
																						<span class="label label-danger">COMPILE_ERROR</span>
																					</c:when>
																					<c:when test="${submit.info=='TIME_LIMIT_EXIT'}">
																						<span class="label label-warning">TIME_LIMIT_EXIT</span>
																					</c:when>
																					<c:when test="${submit.info=='RUN_TIME_ERROR'}">
																						<span class="label label-default">RUN_TIME_ERROR</span>
																					</c:when>
																					<c:when test="${submit.info=='EXECUTION_ERROR'}">
																						<span class="label label-default">EXECUTION_ERROR</span>
																					</c:when>
																					<c:otherwise>
																						<span class="label label-info">UNKNOWN_ERROR</span>
																					</c:otherwise>
																				</c:choose>
																			</a>
																		</td>
																		<td class="hidden-xs">
																			${submit.score }
																		</td>
																		<td>
																			${submit.problem.name }
																		</td>
																		<td class="hidden-xs">
																			${submit.type }
																		</td>
																	</tr>
																</c:if>
															</c:forEach>
														</tbody>
													</table>
												</div>
											</div>
										</c:forEach>
									</div>
								</c:if>
								<form:form action="submit" method="post" enctype="multipart/form-data" modelAttribute="submitForm">
									<input type="hidden" name="idProblem" id="idProblem" value="${problem.id_problem}"/> 
									<div class="form-group">
										<label for="team">Nome del Team</label>
										<div class="input-group">
											<span class="input-group-addon">
												<i class="glyphicon glyphicon-education"></i>
											</span>
											<select class="form-control" id="team" name="team" required>
												<c:forEach items="${teams}" var="team">
													<option value="${team.name}">${team.name}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="form-group">
								    	<label for="solution">Soluzione</label>
										<div class="input-group">
											<span class="input-group-addon">
												<i class="glyphicon glyphicon-open-file"></i>
											</span>
				    						<input id="solution" name="solution" type="file" class="file btn btn-default btn-file" data-show-upload="true" data-show-caption="true" accept=".dlv,.java,.cpp,.c,.py" required>
										</div>
							  		</div>
									<div>
										<input type="submit" class="btn btn-primary btn-lg button-login" value="Invia" />
									</div>
								</form:form>
							</div>
						</c:forEach>
						<c:forEach items="${quizzes}" var="quiz" varStatus="status">
							<div id="quiz-${quiz.id}" style="display:none">
								<h3><span class="label label-primary">${quiz.name}</span></h3><br>
								<form action="submitQuiz" method="post" modelAttribute="submitQuizForm">
									<input type="hidden" name="quizID" id="quizID" value="${quiz.id}"/> 
									<div class="form-group">
										<label for="team">Nome del Team</label>
										<div class="input-group">
											<span class="input-group-addon">
												<i class="glyphicon glyphicon-education"></i>
											</span>
											<select class="form-control" id="quizTeam" name="teamName" required>
												<c:forEach items="${teams}" var="team">
													<option value="${team.name}">${team.name}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<c:forEach items="${quiz.questions}" var="question">
										<c:choose>
											<c:when test="${question.type == 'OPEN' }">
												<div class="form-group">
											  		<label class="col-sm-1 control-label">Domanda</label>
											  		<div class="col-sm-11">
										    			<p class="form-control-static">${question.text }</p>
											  		</div>
												</div>
												<div class="form-group">
													<label class="col-sm-1 control-label" for="questionID-${question.id}">Risposta</label>
													<div class="col-sm-11">
														<input type="text" class="form-control" name="question_answer['${question.id}']" id="questionID-${question.id}" placeholder="Risposta" required autofocus>
													</div>
												</div>
											</c:when>
											<c:otherwise>
												<div class="form-group">
											  		<label class="col-sm-1 control-label">Domanda</label>
											  		<div class="col-sm-11">
										    			<p class="form-control-static">${question.text }</p>
											  		</div>
												</div>
												<div class="answers">
													<c:forEach items="${question.answers}" var="answer">
														<div class="form-check">
															<input class="form-check-input" type="radio" name="question_answer['${question.id}']" id="answerID-${answer.id }" value="${answer.id}" required>
														  	<label class="form-check-label" for="answerID-${answer.id }">${answer.text }</label>
														</div>
													</c:forEach>
												</div>
											</c:otherwise>
										</c:choose>
										<br/>
										<br/>
									</c:forEach>
									<input type="submit" class="btn btn-primary btn-lg" value="Invia" />
								</form>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</section>

	<script src="resources/js/contest.js" type="text/javascript"></script>
	<script src="resources/plugins/node-waves/waves.js"></script>
	<script src="resources/js/admin.js"></script>
	<!-- ?? -->
	<script type="text/javascript">
		$(function() {
			$('#fileUpload').change( function(event) {
			    var tmppath = URL.createObjectURL(event.target.files[0]);
			    console.log(tmppath);
			});
		});
	</script>
</body>
</html>
