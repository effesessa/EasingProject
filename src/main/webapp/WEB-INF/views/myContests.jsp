<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Easing - My Contests</title>
	<%@ include file="includes/header.jsp" %>
	<link href="resources/css/style.css" rel="stylesheet">
	<link href="resources/css/myContestsStyle.css" rel="stylesheet">
</head>
<body>
	<jsp:include page="includes/navbarTeacher.jsp"></jsp:include>
	
	<section class="content">
		<div class="container-fluid">
			<div class="block-header">
				<div class="card" style="height: auto !important;">
					<div class="header">
						<h2>My Contests</h2>
						Only Contests created by a Jury which you are leader will be shown.
						<div class="panel-group" id="accordion" style="margin-top: 16px;">
							<c:forEach var="subjectMap" items="${subjectsMap}" varStatus="index">
								<div class="panel panel-default">
									<div class="panel-heading">
										<h4 class="panel-title">
											<a data-toggle="collapse" data-parent="#accordion" href="#${subjectMap.key.subjectId.id_subject }"> ${subjectMap.key.name }</a>
										</h4>
									</div>
									<div id="${subjectMap.key.subjectId.id_subject }" class="panel-collapse collapse ${index.first ? 'in' : ''}">
										<div class="panel-body">
											<ul class="list-group">
												<c:forEach items="${subjectMap.value}" var="contest">
													<li class="list-group-item">
														${contest.name}
														<span class="toRight">
															<c:if test="${contest.exam}">
																<input class="btn btn-${contest.visible ? 'danger' : 'success' } visibleExamBtn" type="button" value="${contest.visible ? 'Hide' : 'Show' } Exam" data-id="${contest.idcontest}" />
																<a href="manageExam?contestID=${contest.idcontest }" class="btn btn-warning manageContestBtn">Manage Exam</a>
															</c:if>
															<%-- <input class="btn btn-danger deleteContestBtn" type="button" value="Delete" data-id="${contest.idcontest}" /> --%>
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
	
	<!-- Waves Effect Plugin Js -->
	<script src="resources/plugins/node-waves/waves.js"></script>
	<!-- Custom Js -->
	<script src="resources/js/admin.js"></script>
	<script src="resources/js/bootbox.min.js"></script>
	<script src="resources/js/myContestsScript.js"></script>
</body>
</html>