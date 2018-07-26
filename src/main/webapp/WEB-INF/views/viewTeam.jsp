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
	<link href="resources/css/viewTeamStyle.css" rel="stylesheet">
</head>
<body>
	<c:choose>
		<c:when test="${user.professor == false}">
			<!-- Student View -->
			<jsp:include page="includes/navbarStudent.jsp"></jsp:include>
			
			<section class="content">
				<div class="container-fluid">
					<div class="block-header">
						<div class="card" style="height: auto !important;">
							<div class="header">
								<div class="panel panel-default">
									<div class="panel-heading">
										<h2>${team.name }</h2>
									</div>
									<div class="panel-body">
										<div class="form-group">
											<ul class="list-group">
										    	<c:forEach var="member" items="${students}">
													<li class="list-group-item">
														${member.user.id} - ${member.user.name} ${member.user.surname}
													</li>
												</c:forEach>
											</ul>
								  		</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>
		</c:when>
		
		<c:otherwise>
			<!-- Professor View -->
			<jsp:include page="includes/navbarTeacher.jsp"></jsp:include>
			
			<section class="content">
				<div class="container-fluid">
					<div class="block-header">
						<div class="card" style="height: auto !important;">
							<div class="header">
								<div class="panel panel-default">
									<div class="panel-heading">
										<h2>${team.name }</h2>
									</div>
									<div class="panel-body">
										<div class="form-group">
											<ul class="list-group">
										    	<c:forEach var="member" items="${students}">
													<li class="list-group-item">
														${member.user.id} - ${member.user.name} ${member.user.surname}
													</li>
												</c:forEach>
											</ul>
								  		</div>
									</div>
								</div>
							</div>
							<h3>Submit</h3>
							<div class="panel panel-default">
								<div class="panel-body">
									<div class="panel-group" id="accordion" style="margin-top: 16px;">
										<c:forEach var="subject" items="${subjects}" varStatus="index">
											<div class="panel panel-default">
												<div class="panel-heading">
													<h4 class="panel-title">
														<a data-toggle="collapse" data-parent="#accordion" href="#sub${subject.subjectId.id_subject }"> ${subject.name }</a>
													</h4>
												</div>
												<div id="sub${subject.subjectId.id_subject }" class="panel-collapse collapse ${index.first ? 'in' : ''}">
													<div class="panel-group" id="accordion2" style="margin-top: 16px;">
														<c:forEach var="contest" items="${contests}">
															<c:if test="${subject.subjectId.id_subject == contest.subject.subjectId.id_subject }">
																<div class="panel panel-default">
																	<div class="panel-heading">
																		<h4 class="panel-title">
																			<a data-toggle="collapse" data-parent="#accordion2" href="#con${contest.idcontest }"> ${contest.name }</a>
																		</h4>
																	</div>
																	<div id="con${contest.idcontest }" class="panel-collapse collapse">
																		<table class="table table-hover table-responsive">
																			<thead class="headTable">
																		  		<tr>
																				    <th>Info</th>
																				    <th class="hidden-xs">Score</th>
																				    <th>Problem</th>
																				    <th class="hidden-xs">Language</th>
																		  		</tr>
																			</thead>
																			<tbody id="tableSub${contest.idcontest }">
																				<c:forEach items="${submits}" var="submit">
																					<c:if test="${contest.idcontest == submit.problem.id_contest.idcontest }">
																						<tr id=${submit.id }>
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
															</c:if>
														</c:forEach>
													</div>
												</div>
											</div>
										</c:forEach>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>
		</c:otherwise>
	</c:choose>
	
	<!-- Waves Effect Plugin Js -->
	<script src="resources/plugins/node-waves/waves.js"></script>
	<!-- Custom Js -->
	<script src="resources/js/admin.js"></script>
</body>
</html>