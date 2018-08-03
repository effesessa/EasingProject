<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Easing</title>
	<%@ include file="includes/header.jsp" %>
	<link href="resources/css/style.css" rel="stylesheet">
	<link href="resources/css/manageExamStyle.css" rel="stylesheet">
</head>
<body>
	<jsp:include page="includes/navbarTeacher.jsp"></jsp:include>
	
	<section class="content">
		<div class="container-fluid">
			<div class="block-header">
				<div class="card" style="height: auto !important;">
					<div class="header">
						<h2>Prepare exam - ${contest.name}</h2>
					</div>
					<form:form id="manageExamForm" action="manageExam" method="POST">
						<input type="hidden" name="contestID" value="${contest.idcontest}" />
						<table class="table table-hover table-responsive">
							<thead class="headTable">
						  		<tr>
								    <th>#</th>
								    <th>Problem / Quiz</th>
								    <th>Minimum resolved problems</th>
								    <th>Minimum quizzes points</th>
						  		</tr>
							</thead>
							<tbody id="problemsQuizzesList">
								<c:forEach var="problem" items="${problems}" varStatus="index">
									<tr id="p${problem.id_problem}">
										<td scope="row">${index.count}</td>
										<td>${problem.name}</td>
										<td>
											<div class="form-group">
												<div class="input-group">
													<input class="form-control input-sm" id="p${problem.id_problem}-mPr" name="minProblems[p${problem.id_problem}]" type="number" min=0 step=1 value=0 required />
												</div>
										  	</div>
										</td>
										<td>
											<div class="form-group">
												<div class="input-group">
													<input class="form-control input-sm" id="p${problem.id_problem}-mPo" name="minPoints[p${problem.id_problem}]" type="number" min=0 step=1 value=0 required />
												</div>
										  	</div>
										</td>
									</tr>
								</c:forEach>
								<c:forEach var="quiz" items="${quizzes}" varStatus="index">
									<tr id="q${quiz.id}">
										<td scope="row">${fn:length(problems)+index.count}</td>
										<td>${quiz.name}</td>
										<td>
											<div class="form-group">
												<div class="input-group">
													<input class="form-control input-sm" id="q${problem.id_problem}-mPr" name="minProblems[q${quiz.id}]" type="number" min=0 step=1 value=0 required />
												</div>
										  	</div>
										</td>
										<td>
											<div class="form-group">
												<div class="input-group">
													<input class="form-control input-sm" id="q${problem.id_problem}-mPo" name="minPoints[q${quiz.id}]" type="number" min=0 step=1 value=0 required />
												</div>
										  	</div>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<input type="submit" class="btn btn-lg btn-primary" value="Apply">
					</form:form>
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