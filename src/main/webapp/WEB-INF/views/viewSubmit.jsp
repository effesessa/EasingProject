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
</head>
<body>
	<jsp:include page="includes/navbarTeacher.jsp"></jsp:include>
	
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
						<c:if test="${not empty submit.score }">
							<h2>Tempo d'esecuzione: ${submit.score}s</h2>
						</c:if>
						<br>
						<a href="${pageContext.servletContext.contextPath }/downloadSubmit/${submit.id}" class="btn btn-info">Scarica Sottomissione</a>
					</div>
					<pre>
						<code class="language-${language}">${submitFile }</code>
					</pre>
					
				</div>
			</div>
		</div>
	</section>
	
	
	<script src="resources/js/prism.js"></script>
</body>
</html>