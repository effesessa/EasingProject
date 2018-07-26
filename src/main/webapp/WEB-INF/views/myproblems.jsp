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
					<div class="header">
						<h2>My Problems</h2>
						Only Problems created by a Jury which you are leader will be shown.
						<div class="panel-group" id="accordion" style="margin-top: 16px;">
							<c:forEach var="contest" items="${contests}" varStatus="index">
								<div class="panel panel-default">
									<div class="panel-heading">
										<h4 class="panel-title">
											<a data-toggle="collapse" data-parent="#accordion" href="#${contest.idcontest }"> ${contest.name }</a>
										</h4>
									</div>
									<div id="${contest.idcontest }" class="panel-collapse collapse ${index.first ? 'in' : ''}">
										<div class="panel-body">
											<ul class="list-group">
												<c:forEach items="${problems}" var="problem">
													<c:if test="${problem.id_contest.idcontest == contest.idcontest}">
														<li class="list-group-item">
															<a href="viewSubmits?id=${problem.id_problem}">${problem.name}</a>
															<span class="toRight">
																<input class="btn btn-success cloneProblemBtn" type="button" value="Clona" data-id="${problem.id_problem}" data-toggle="modal" data-target="#cloneProblemModal" />
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
					<h3 class="modal-title">Edit Problem</h3>
				</div>
				<form:form action="problem" method="post" enctype="multipart/form-data" modelAttribute="problemForm">
					<input type="hidden" name="op" value="editProblem"/> 
					<input type="hidden" name="id" id="id" value=""/> 
					<div class="form-group">
						<label for="editP_contest">Contest Name</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-education"></i>
							</span>
							<select class="form-control" id="editP_contestName" name="contestName" required>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="editP_problemName">Name</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-text-size"></i>
							</span>
							<input type="text" class="form-control" name="name" id="editP_problemName" placeholder="Problem Name" required autofocus>
						</div>
					</div>
					<div class="form-group">
				    	<label for="editP_description">Description</label>
						<div class="input-group">
				    		<span class="input-group-addon">
								<i class="glyphicon glyphicon-pencil"></i>
							</span>
							<textarea class="form-control" id="editP_description" name="description" rows="5" cols="60" placeholder="Insert a Problem description" style="resize: vertical;" required></textarea>		
						</div>
				  	</div>
					<div class="form-group">
				    	<label for="editP_download">Essay</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-file"></i>
							</span>
    						<input id="editP_download" name="download" type="file" class="file btn btn-default btn-file" data-show-upload="true" data-show-caption="true" accept=".txt,.pdf">
						</div>
			  		</div>
			  		<div class="form-group">
				    	<label for="editP_timeout">Execution timeout (seconds)</label>
						<div class="input-group">
				    		<span class="input-group-addon">
								<i class="glyphicon glyphicon-time"></i>
							</span>
							<input class="form-control" id="editP_timeout" name="timeout" type="number" min=1 step=1 value="5" required />
						</div>
				  	</div>
				  	<br>
				  	<div class="form-check generate-group">
				  		<input class="form-check-input" type="checkbox" value=1 id="editP_show_testcase" name="show_testcase">
				  		<label class="form-check-label" for="editP_show_testcase"> Do you want share input/output files with the students?</label>
					</div>

					<br>
					<div class="form-group">
						<label for="editP_problemTags">Tag</label><br>
						<small class="form-text text-muted">Insert Tags divided by comma or space</small>
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
						<input type="submit" class="btn btn-primary button-login" value="Modifica Problema" />
					</div>
				</form:form>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
	
	<div id="cloneProblemModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h3 class="modal-title">Clone Problem</h3>
				</div>
				<form:form action="problem" method="post" enctype="multipart/form-data">
					<input type="hidden" name="op" value="cloneProblem"/>
					<input type="hidden" name="id" id="cloneP_id" value=""/> 
					<div class="form-group">
						<label for="cloneP_contestName">Select Contest</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-education"></i>
							</span>
							<select class="form-control" id="cloneP_contestName" name="contestName" required>
							</select>
						</div>
					</div>
					<div class="modal-footer">
						<input type="submit" class="btn btn-primary button-login" value="Clona Problema" />
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