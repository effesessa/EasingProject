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
							<c:if test="${submit.showTcf }">
								<a id="downloadFTCBtn" href="${pageContext.servletContext.contextPath }/downloadTestCaseFailed/${submit.id}" class="btn btn-info">Download Failed TestCase</a>
							</c:if>
							<a id="downloadBtn" href="${pageContext.servletContext.contextPath }/downloadSubmit/${submit.id}" class="btn btn-info">Download Submit</a>
							<c:if test="${user.professor && submit.info!='CORRECT' && submit.info!='COMPILE_ERROR'}">
								<c:choose>
									<c:when test="${not submit.showTcf}">
										<form:form action="toggleTestCase" method="post">
											<input type="hidden" name="idSubmit" value="${submit.id}">
											<button id="testCaseBtn" type="submit" class="btn btn-warning">Show failed TestCase</button>
										</form:form>
									</c:when>
									<c:otherwise>
										<form:form action="toggleTestCase" method="post">
											<input type="hidden" name="idSubmit" value="${submit.id}">
											<button id="testCaseBtn" type="submit" class="btn btn-warning">Hide failed TestCase</button>
										</form:form>
									</c:otherwise>
								</c:choose>
							</c:if>
						</h2>
					</div>
					<pre>
						<code class="language-${language}">${submitFile }</code>
					</pre>
					<c:if test="${submit.info!='CORRECT' && submit.info!='WRONG_ANSWER'}">
						<%-- <pre>
							<code class="language-bash">${submit.error}</code>
						</pre>
						<pre>
							<samp>${submit.error}</samp>
						</pre> --%>
						<samp>${submit.error}</samp>
					</c:if>
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