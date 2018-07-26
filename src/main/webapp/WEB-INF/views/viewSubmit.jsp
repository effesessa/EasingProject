<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Easing</title>
	<%@ include file="includes/header.jsp" %>
	<link href="resources/css/style.css" rel="stylesheet">
	<link href="resources/css/prism.css" rel="stylesheet" />
	<link href="resources/css/viewSubmitStyle.css" rel="stylesheet" />
</head>
<body>
	<!-- navbar -->
	<c:if test="${user.professor == false}">
		<jsp:include page="includes/navbarStudent.jsp"></jsp:include>
	</c:if>
	<c:if test="${user.professor == true}">
		<jsp:include page="includes/navbarTeacher.jsp"></jsp:include>
	</c:if>
	<!-- end navbar -->
	
	<section class="content">
		<div class="container-fluid">
			<div class="block-header">
				<div class="card" style="height: auto !important;">
					<div class="header">
						<h2>
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
						</h2>
						<br>
						<h2>
							<c:if test="${not empty submit.score }">
								Tempo d'esecuzione: ${submit.score}s
							</c:if>
							<a id="downloadBtn" href="${pageContext.servletContext.contextPath }/downloadSubmit/${submit.id}" class="btn btn-info">Scarica Sottomissione</a>
							<c:if test="${submit.info!='CORRECT' && user.professor}">
								<form:form action="" method="post">
									<input type="hidden" >
									<button type="submit" class="btn btn-primary">OEEE</button>
								</form:form>
							</c:if>
						</h2>
					</div>
					<pre>
						<code class="language-${language}">${submitFile }</code>
					</pre>
					
				</div>
			</div>
		</div>
	</section>
	
	<!-- Waves Effect Plugin Js -->
	<script src="resources/plugins/node-waves/waves.js"></script>
	<!-- Custom Js -->
	<script src="resources/js/admin.js"></script>
	<script src="resources/js/prism.js"></script>
</body>
</html>