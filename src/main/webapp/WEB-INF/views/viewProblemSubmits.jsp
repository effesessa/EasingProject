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
</head>
<body>
	<jsp:include page="includes/navbarTeacher.jsp"></jsp:include>

	<section class="content">
		<div class="container-fluid">
			<div class="block-header">
				<div class="card" style="height: auto !important;">
					<div class="header">
						<h2>Sottomissioni ${problem.name}</h2>
					</div>
					<table class="table table-hover table-responsive">
						<thead class="headTable">
					  		<tr>
							    <th>#</th>
							    <th>Nome Team</th>
							    <th>1</th>
							    <th>2</th>
							    <th>3</th>
							    <th>4</th>
							    <th>5</th>
					  		</tr>
						</thead>
						<tbody id="problemSubmits">
							<c:forEach var="team" items="${submitsPerTeam}" varStatus="teamIndex">
								<tr id=${team.key.name }>
									<th scope="row">${teamIndex.count}</th>
									<th><a href="viewTeam?name=${team.key.name }">${team.key.name}</a></th>
									<c:set var = "emptySubmits" value = "${5-fn:length(team.value)}"/>
									<c:forEach var="submit" items="${team.value}" varStatus="submitIndex">
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
									</c:forEach>
									<c:forEach begin="1" end="${emptySubmits}">
										<td><span class="label label-primary">EMPTY_SUBMIT</span></td>
									</c:forEach>
								</tr>
							</c:forEach>
						</tbody>
					</table>
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