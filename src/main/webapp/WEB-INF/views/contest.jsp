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
	<!-- Google Fonts -->
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" type="text/css">
	<!-- Bootstrap Core Css -->
	<link href="resources/plugins/bootstrap/css/bootstrap.css" rel="stylesheet">
	<!-- Animation Css -->
	<link href="resources/plugins/animate-css/animate.css" rel="stylesheet" />
	<!-- Morris Chart Css-->
	<link href="resources/plugins/morrisjs/morris.css" rel="stylesheet" />
	<!-- Custom Css -->
	<link href="resources/css/style.css" rel="stylesheet">
	<link href="resources/css/contest.css" rel="stylesheet">
	<!-- Jquery Core Js -->
	<script src="resources/plugins/jquery/jquery.min.js"></script>
	<!-- Bootstrap Core Js -->
	<script src="resources/plugins/bootstrap/js/bootstrap.js"></script>
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
									<a href="#" class="pn-ProductNav_Link"
										${status.first ? 'aria-selected="true"' : ''}>${problem.name }</a>
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
							<c:set var="bodyID" value="${fn:replace(problem.name,' ', '')}" />
							<div id="body-${bodyID }" ${status.first ? '' : 'style="display:none"'}>
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
									<div class="bubble">
										<h4><span class="label label-info">Submit</span></h4>
										<div class="bubble-title">Submit:</div>
										<c:forEach items="${submits}" var="submit">
											<div class="submit">
												<div class="submit">
													<div>Team: ${submit.team.name}</div>
													<div>Score: ${submit.score}</div>
												</div>
												<br>
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
					</div>
				</div>
			</div>
		</div>
	</section>

	<script src="resources/js/contest.js" type="text/javascript"></script>
	<!-- Jquery Core Js -->
	<script src="resources/plugins/jquery/jquery.min.js"></script>

	<!-- Bootstrap Core Js -->
	<script src="resources/plugins/bootstrap/js/bootstrap.js"></script>
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
