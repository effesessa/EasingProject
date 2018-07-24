<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Easing - I miei Problemi</title>
	<%@ include file="includes/header.jsp" %>
	<link href="resources/css/style.css" rel="stylesheet">
	<link href="resources/css/myProblemsStyle.css" rel="stylesheet">
</head>
<body>
	<jsp:include page="includes/navbarTeacher.jsp"></jsp:include>

	<section class="content">
		<div class="container-fluid">
			<div class="block-header">
				<div class="card" style="height: auto !important;">
				
				<div class="panel panel-default">
					<div class="panel-body">
						<div class="panel-group" id="accordion" style="margin-top: 16px;">
							<c:forEach var="contest" items="${contestQuizzesMap}" varStatus="index">
								<div class="panel panel-default">
									<div class="panel-heading">
										<h4 class="panel-title">
											<a data-toggle="collapse" data-parent="#accordion" href="#con${contest.key.idcontest }"> ${contest.key.name }</a>
										</h4>
									</div>
									<div id="con${contest.key.idcontest }" class="panel-collapse collapse ${index.first ? 'in' : ''}">
										<div class="panel-group" id="accordion2" style="margin-top: 16px;">
											<c:forEach var="quiz" items="${contest.value}" varStatus="subIndex">
												
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


	<!-- Waves Effect Plugin Js -->
	<script src="resources/plugins/node-waves/waves.js"></script>
	<!-- Custom Js -->
	<script src="resources/js/admin.js"></script>
	<script src="resources/js/bootbox.min.js"></script>
</body>
</html>