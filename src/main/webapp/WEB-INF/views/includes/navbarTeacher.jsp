<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<!-- <script src="resources/plugins/jquery/jquery.min.js"></script> -->
	<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/css/bootstrap-datepicker.css" rel="stylesheet"/>
	
<!-- 	<link href="resources/plugins/bootstrap/css/bootstrap.css" rel="stylesheet">
	<script src="resources/plugins/bootstrap/js/bootstrap.js"></script> -->
	
	<link rel="stylesheet" href="resources/plugins/bootstrap-tagsinput/bootstrap-tagsinput.css" type="text/css">
	<link rel="stylesheet" href="resources/css/navbarTeacherStyle.css" type="text/css">
	<!-- <script type="text/javascript" src="resources/js/moment.js"></script> -->
	<script defer src="https://use.fontawesome.com/releases/v5.0.8/js/all.js"></script>
	
	
</head>
<body>
	<!-- Overlay For Sidebars -->
	<div class="overlay"></div>
	<div class="button">
		<a href="javascript:void(0);" class="bars" style="display: block;"></a>
	</div>
	<!-- #END# Overlay For Sidebars -->
	<!-- Search Bar -->
	<div class="search-bar">
		<div class="search-icon">
			<i class="material-icons">search</i>
		</div>
		<form:form id="navbar-search-form" class="navbar-form form-inline"
			action="search" method="POST" modelAttribute="searchForm"
			style="margin: 0px !important;padding: 0px !important;">
			<input type="text" name="word" placeholder="START TYPING...">
		</form:form>
		<div class="close-search">
			<i class="material-icons">close</i>
		</div>
	</div>
	<!-- #Search Bar -->

	<section> <!-- Left Sidebar -->
		<aside id="leftsidebar" class="sidebar">
			<!-- User Info -->
			<div class="user-info">
				<div class="info-container">
					<div class="name" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">${user.name}${user.surname}</div>
					<div class="id_number">${user.id}</div>
					<a href="javascript:void(0);" class="js-search" data-close="true"><i class="material-icons">search</i></a>
				</div>
			</div>
			<!-- #User Info -->
			<!-- Menu -->
			<div class="menu">
				<ul class="list">
					<li>
						<div id="custom-search-input" style="margin: 16px">
							<div class="input-group col-md-12">
								<form:form id="navbar-search-form" class="navbar-form form-inline" action="searchProblem" method="POST" modelAttribute="searchForm" style="margin: 0px !important;padding: 0px !important;">
									<input type="text" class="form-control input-lg" placeholder="Solutions" name="word" />
									<span class="input-group-btn">
										<button class="btn btn-info btn-lg" type="button">
											<i class="glyphicon glyphicon-search"></i>
										</button>
									</span>
								</form:form>
							</div>
						</div>
					</li>
					<li class="active"><a href="/"> <i class="material-icons">home</i>
							<span>Home</span>
					</a></li>
					<li><a href="#" data-toggle="modal" data-target="#myModal1">
							<i class="material-icons">view_list</i> <span>Add Subject</span>
					</a></li>
					<li><a href="#" data-toggle="modal" data-target="#myModal2" onclick="getSubjectsAndJuries()">
							<i class="material-icons">content_copy</i> <span>Add Contest</span>
					</a></li>
					<li>
					<li><a href="#" data-toggle="modal" data-target="#myModal3" onclick="getContests()">
							<i class="material-icons">update</i> <span>Add Problem</span>
					</a></li>
					<li><a href="logout"> <i class="fas fa-sign-out-alt"
							style="font-size: 1.5em;"></i> <span
							style="position: relative; bottom: 5px">Logout</span>
					</a></li>
		
				</ul>
			</div>
			<!-- #Menu -->
			<!-- Footer -->
			<div class="legal">
				<div class="copyright">&copy; 2017 - 2018 Judge Manager.</div>
				<div class="version">
					<b>Version: </b> 1.0.0
				</div>
			</div>
			<!-- #Footer -->
		</aside>
		<!-- #END# Left Sidebar -->
	</section>
	<div id="myModal1" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h3 class="modal-title">Crea Subject</h3>
				</div>
				<form:form action="addSubject" method="post" modelAttribute="addSubjectForm">
					<div class="form-group">
						<label for="newSub_subjectName">Nome Corso</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-text-size"></i>
							</span>
							<input type="text" class="form-control" name="name" id="newSub_subjectName" placeholder="Nome del Corso" required autofocus>
						</div>
					</div>
					<div class="form-group">
						<label for="newSub_year">Anno</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-calendar"></i>
							</span>
							<input type="text" class="form-control" name="year" id="newSub_year" placeholder="Year" required>
						</div>
					</div>
					<div class="form-group">
						<label for="newSub_passwordSubject">Password</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-lock"></i>
							</span>
							<input type="text" class="form-control" name="password" id="newSub_passwordSubject" placeholder="Password richiesta per iscriversi" required>
						</div>
					</div>
					<div class="form-group">
						<label for="newSub_subjectURL">Pagina del Corso</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-link"></i>
							</span>
							<input type="text" class="form-control" name="url" id="newSub_subjectURL" placeholder="Incolla l'URL della pagine del Corso" required>
						</div>
					</div>
					<div class="modal-footer">
						<input type="submit" class="btn btn-primary button-login" value="Add Subject" />
					</div>
				</form:form>
			</div>
		</div>
	</div>
	<div id="myModal2" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h3 class="modal-title">Crea Contest</h3>
				</div>
				<form:form action="addContest" method="post" modelAttribute="addContestForm">
					<div class="form-group">
						<label for="newContest_contestName">Nome Contest</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-text-size"></i>
							</span>
							<input type="text" class="form-control" name="name" id="newContest_contestName" placeholder="Nome del Contest" required autofocus>
						</div>
						<!-- <div>Nome del Contest:</div>
						<input type="text" class="form-control" name="name"	placeholder="Name"> -->
					</div>
					<div class="form-group">
						<label for="newContest_subjectName">Nome Corso</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-education"></i>
							</span>
							<select class="form-control" id="newContest_subjectName" name="subjectId" required>
							</select>
						</div>
						<!-- <div>Corso:</div>
						<input type="text" name="subjectId" placeholder="Id Subject"> -->
					</div>
					<div class="form-group">
						<label for="newContest_jury">ID Giuria</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-user"></i>
							</span>
							<select class="form-control" id="newContest_jury" name="jury" required>
							</select>
						</div>
						<!-- <div>Corso:</div>
						<input type="text" name="jury" placeholder="Id Jury"> -->
					</div>
					<div class="form-group">
						<label for="newContest_deadline">Data di scadenza</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-calendar"></i>
							</span>
							<input type="text" class="form-control" name="deadline" id="newContest_deadline" placeholder="Scadenza" required>
						</div>
						<!-- <div>Data Scadenza:</div>
						<input type="text" id="datepicker"> -->
					</div>
					<div class="modal-footer">
						<input type="submit" class="btn btn-primary button-login" value="Add Contest" />
					</div>
				</form:form>
			</div>
		</div>
	</div>
	<div id="myModal3" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h3 class="modal-title">Crea un Problema</h3>
				</div>
				<form:form action="addProblem" method="post" enctype="multipart/form-data" modelAttribute="problemForm">
					<input type="hidden" name="type" id="type" value="1"/> 
					<div class="form-group">
						<label for="contest">Nome Contest</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-education"></i>
							</span>
							<select class="form-control" id="contestName" name="contestName" required>
							   <!--  <option disabled selected value="">Contest</option> -->
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="problemName">Nome</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-text-size"></i>
							</span>
							<input type="text" class="form-control" name="name" id="problemName" placeholder="Nome del Problema" required autofocus>
						</div>
					</div>
					<div class="form-group">
				    	<label for="description">Descrizione</label>
						<div class="input-group">
				    		<span class="input-group-addon">
								<i class="glyphicon glyphicon-pencil"></i>
							</span>
							<textarea class="form-control" id="description" name="description" rows="5" cols="60" placeholder="Inserire una breve descrizione del Problema" style="resize: vertical;" required></textarea>		
						</div>
				  	</div>
					<div class="form-group">
				    	<label for="download">Testo del Problema</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-file"></i>
							</span>
    						<input id="download" name="download" type="file" class="file btn btn-default btn-file" data-show-upload="true" data-show-caption="true" accept=".txt,.pdf" required>
						</div>
			  		</div>
			  		<div class="form-group">
				    	<label for="timeout">Timeout d'esecuzione (secondi)</label>
						<div class="input-group">
				    		<span class="input-group-addon">
								<i class="glyphicon glyphicon-time"></i>
							</span>
							<input class="form-control" id="timeout" name="timeout" type="number" min=1 step=0.5 value="1" required />
						</div>
				  	</div>
				  	<br>
				  	<div class="form-check generate-group">
				  		<input class="form-check-input" type="checkbox" value=1 id="show_testcase" name="show_testcase">
				  		<label class="form-check-label" for="show_testcase"> Vuoi che gli Studenti possano accedere ai file di input e di output?</label>
					</div>
					<div class="form-group">
				    	<label for="testcase">File di input o Algoritmo</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-open-file"></i>
							</span>
    						<input id="testcase" name="testcase" type="file" class="file btn btn-default btn-file" data-show-upload="true" data-show-caption="true" accept=".txt,.java,.cpp,.c,.zip,.rar,.7z,.tar" required>
						</div>
			  		</div>
					<div class="form-group output-group hidden">
				    	<label for="output">Output atteso</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-open-file"></i>
							</span>
    						<input id="output" name="output" type="file" class="file btn btn-default btn-file" data-show-upload="true" data-show-caption="true" accept=".txt">
						</div>
			  		</div>
			  		<div class="form-check generate-group hidden">
					  <input class="form-check-input" type="checkbox" value="" id="generateInput">
					  <label class="form-check-label" for="generateInput"> Vuoi che vengano generati degli input?</label>
					</div>
					<div class="form-group hidden">
						<label class="form-check-label" for="domain"> Dominio:</label>
						<div class="form-check">
							<label class="form-check-label">
								<input class="form-check-input" type="radio" name="domain" id="intDomain" value="Array Integer" checked> Array Integer
							</label>
						</div>
						<div class="form-check">
							<label class="form-check-label">
								<input class="form-check-input" type="radio" name="domain" id="stringDomain" value="String" > String
							</label>
						</div>
					</div>
					<br>
					<div class="form-group">
						<label for="problemTags">Tag</label>
						<div class="input-group" id="tagsDiv">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-tags"></i>
							</span>
							<input type="text" class="form-control" name="problemTags" id="problemTags" placeholder="Inserisci i tags separati da una virgola">
						</div>
					</div>
					<div class="modal-footer">
						<input type="submit" class="btn btn-primary button-login" value="Add Problem" />
					</div>
				</form:form>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
	<%@ include file="footer.jsp" %>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/js/bootstrap-datepicker.min.js"></script>
	<script src="resources/plugins/bootstrap-tagsinput/bootstrap-tagsinput.min.js"></script>
	<script src="resources/js/typeahead.bundle.js"></script>
	<script src="resources/js/navbarTeacherScript.js"></script>
	<!-- <script src="resources/plugins/jquery/jquery.min.js"></script>
	<script src="resources/plugins/bootstrap/js/bootstrap.js"></script> -->
	
</body>
</html>