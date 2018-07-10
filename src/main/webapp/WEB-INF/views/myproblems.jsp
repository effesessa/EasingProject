<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
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
					<div class="header">
						<h2>I miei Problemi</h2>
						Verranno visualizzati solo i Problemi creati da una Giuria di cui sei leader.
						<div class="panel-group" id="accordion" style="margin-top: 16px;">
							<c:forEach var="contest" items="${contests}">
								<div class="panel panel-default">
									<div class="panel-heading">
										<h4 class="panel-title">
											<a data-toggle="collapse" data-parent="#accordion" href="#${contest.name }"> ${contest.name }</a>
										</h4>
									</div>
									<div id="${contest.name }" class="panel-collapse collapse in">
										<div class="panel-body">
											<ul class="list-group">
												<c:forEach items="${problems}" var="problem">
													<c:if test="${problem.id_contest.idcontest == contest.idcontest}">
														<li class="list-group-item">
															${problem.name}
															<span class="toRight">
																<input class="btn btn-warning editProblemBtn" type="button" value="Modifica" data-id="${problem.id_problem}" data-toggle="modal" data-target="#editProblemModal" />
																<input class="btn btn-danger deleteProblemBtn" type="button" value="Elimina" data-id="${problem.id_problem}" />
															</span>
														</li>
													</c:if>
												</c:forEach>
											</ul>
										</div>
									</div>
								</div>
							</c:forEach>
							
							<%-- 
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion" href="#collapse1"> Users</a>
									</h4>
								</div>
								<div id="collapse1" class="panel-collapse collapse in">
									<div class="panel-body">
										<ul class="list-group">
											<c:forEach items="${UserResult}" var="user">
												<li class="list-group-item">${user.id} - ${user.surname} ${user.name}</li>
											</c:forEach>
										</ul>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion" href="#collapse2"> Subjects</a>
									</h4>
								</div>
								<div id="collapse2" class="panel-collapse collapse">
									<div class="panel-body">
										<ul class="list-group">
											<c:forEach var="subject" items="${SubjectResult}">
												<li class="list-group-item">${subject.name}
													<div style="display: inline;">
														<form:form id="password-form"
															class="navbar-form form-inline"
															action="signUpSubject?name=${subject.name}" method="POST"
															modelAttribute="subjectPasswordForm"
															style="display:inline-block;">
															<div class="form-group">
																<div class="input-group">
																	<input type="text" class="form-control" name="password"	placeholder="Password" />
																</div>
															</div>
															<button class="btn btn-primary button-add" data-toggle="tooltip">Iscriviti</button>
														</form:form>
													</div>
												</li>
											</c:forEach>
										</ul>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion" href="#collapse3"> Contests</a>
									</h4>
								</div>
								<div id="collapse3" class="panel-collapse collapse">
									<div class="panel-body">
										<ul class="list-group">
											<c:forEach var="contest" items="${ContestResult}">
												<li class="list-group-item">${contest.name}
													<div style="display: inline;">
														<form:form id="password-form"
															class="navbar-form form-inline"
															action="signUpSubject?name=${subject.name}" method="POST"
															modelAttribute="subjectPasswordForm"
															style="display:inline-block;">
															<div class="form-group">
																<div class="input-group">
																	<select class="form-control" id="team" name="team">
																		<c:forEach var="team" items="${TeamResult}">
																			<option>${team.name }</option>
																		</c:forEach>
																	</select>
																</div>
															</div>
														</form:form>
														<button class="btn btn-primary button-add" data-toggle="tooltip">Iscriviti</button>
													</div>
												</li>
											</c:forEach>
										</ul>
									</div>
								</div>
							</div> --%>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	
	<div id="editProblemModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h3 class="modal-title">Modifica un Problema</h3>
				</div>
				<form:form action="problem" method="post" enctype="multipart/form-data" modelAttribute="problemForm">
					<input type="hidden" name="id" id="id" value=""/> 
					<div class="form-group">
						<label for="editP_contest">Nome Contest</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-education"></i>
							</span>
							<select class="form-control" id="editP_contestName" name="contestName" required>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="editP_problemName">Nome</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-text-size"></i>
							</span>
							<input type="text" class="form-control" name="name" id="editP_problemName" placeholder="Nome del Problema" required autofocus>
						</div>
					</div>
					<div class="form-group">
				    	<label for="editP_description">Descrizione</label>
						<div class="input-group">
				    		<span class="input-group-addon">
								<i class="glyphicon glyphicon-pencil"></i>
							</span>
							<textarea class="form-control" id="editP_description" name="description" rows="5" cols="60" placeholder="Inserire una breve descrizione del Problema" style="resize: vertical;" required></textarea>		
						</div>
				  	</div>
					<div class="form-group">
				    	<label for="editP_download">Testo del Problema</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-file"></i>
							</span>
    						<input id="editP_download" name="download" type="file" class="file btn btn-default btn-file" data-show-upload="true" data-show-caption="true" accept=".txt,.pdf">
						</div>
			  		</div>
			  		<div class="form-group">
				    	<label for="editP_timeout">Timeout d'esecuzione (secondi)</label>
						<div class="input-group">
				    		<span class="input-group-addon">
								<i class="glyphicon glyphicon-time"></i>
							</span>
							<input class="form-control" id="editP_timeout" name="timeout" type="number" min=1 step=0.5 value="1" required />
						</div>
				  	</div>
				  	<br>
				  	<div class="form-check generate-group">
				  		<input class="form-check-input" type="checkbox" value=1 id="editP_show_testcase" name="show_testcase">
				  		<label class="form-check-label" for="editP_show_testcase"> Vuoi che gli Studenti possano accedere ai file di input e di output?</label>
					</div>

					<br>
					<div class="form-group">
						<label for="editP_problemTags">Tag</label><br>
						<small class="form-text text-muted">Inserisci i Tag separati da una virgola o uno spazio</small>
						<div class="input-group" id="editP_tagsDiv">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-tags"></i>
							</span>
							<input type="text" class="form-control" name="problemTags" id="editP_problemTags">
						</div>
							<small id="editP_popularTags" class="form-text text-muted">
							</small>
					</div>
					<div class="modal-footer">
						<input type="submit" class="btn btn-primary button-login" value="Edit Problem" />
					</div>
				</form:form>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
	
	
	<!-- Waves Effect Plugin Js -->
	<script src="resources/plugins/node-waves/waves.js"></script>
	<!-- Custom Js -->
	<script src="resources/js/admin.js"></script>
	<script src="resources/js/bootbox.min.js"></script>
	<script src="resources/plugins/bootstrap-tagsinput/bootstrap-tagsinput.min.js"></script>
	<script src="resources/js/typeahead.bundle.js"></script>
	<script src="resources/js/myProblemsScript.js"></script>
</body>
</html>