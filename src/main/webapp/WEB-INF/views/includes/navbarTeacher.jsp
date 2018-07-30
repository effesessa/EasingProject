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
					<div class="name" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">${user.name}<br>${user.surname}</div>
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
									<input type="text" class="form-control input-lg" placeholder="Search Problems" name="word" />
									<span class="input-group-btn">
										<button class="btn btn-info btn-lg" type="submit">
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
				<!-- <li><a href="#" data-toggle="modal" data-target="#myModal1">
						<i class="material-icons">school</i> <span>Add Subject</span>
					</a></li>
					<li><a href="#" data-toggle="modal" data-target="#myModal2" onclick="getSubjectsAndJuries()">
						<i class="material-icons">add_to_queue</i> <span>Add Contest</span>
					</a></li>
					<li>
					<li><a href="#" data-toggle="modal" data-target="#myModal3" onclick="getContests()">
						<i class="material-icons">note_add</i> <span>Add Problem</span>
					</a></li>
					<li><a href="#" data-toggle="modal" data-target="#addQuestionModal" onclick="getContests()">
						<i class="material-icons">help_outline</i> <span>Add Question</span>
					</a></li>
					<li><a href="createQuiz">
						<i class="material-icons">list_alt</i> <span>Add Quiz</span>
					</a></li> -->
					<!-- <li><a href="myProblems">
						<i class="material-icons">view_list</i> <span>My Problems</span>
					</a></li>
					<li><a href="myQuizzes">
						<i class="material-icons">list</i> <span>My Quizzes</span>
					</a></li> -->
					<li class="submenu"><a href="#"><i class="material-icons">add_circle</i><span>Add...</span></a>
						<ul>
							<li class="submenu"><a href="#" data-toggle="modal" data-target="#myModal1">
								<i class="material-icons">school</i> <span>Add Subject</span>
							</a></li>
							<li class="submenu"><a href="#" data-toggle="modal" data-target="#myModal2" onclick="getSubjectsAndJuries()">
								<i class="material-icons">add_to_queue</i> <span>Add Contest</span>
							</a></li>
							<li>
							<li class="submenu"><a href="#" data-toggle="modal" data-target="#myModal3" onclick="getContests()">
								<i class="material-icons">note_add</i> <span>Add Problem</span>
							</a></li>
							<li class="submenu"><a href="#" data-toggle="modal" data-target="#addQuestionModal" onclick="getContests()">
								<i class="material-icons">help_outline</i> <span>Add Question</span>
							</a></li>
							<li class="submenu"><a href="createQuiz">
								<i class="material-icons">list_alt</i> <span>Add Quiz</span>
							</a></li>
						</ul>
					</li>
					<li class="submenu"><a href="#"><i class="material-icons">account_box</i><span>My...</span></a>
						<ul>
					   		<li class="submenu"><a href="myContests">
								<i class="material-icons">view_list</i> <span>My Contests</span>
							</a></li>
					   		<li class="submenu"><a href="myProblems">
								<i class="material-icons">view_list</i> <span>My Problems</span>
							</a></li>
						   	<li class="submenu"><a href="myQuizzes">
								<i class="material-icons">list</i> <span>My Quizzes</span>
							</a></li>
				  		</ul>
				  	</li>
					<hr>
					<li><a href="logout">
						<i class="material-icons">exit_to_app</i> <span>Logout</span>
					</a></li>
		
				</ul>
			</div>
			<!-- #Menu -->
			<!-- Footer -->
			<!-- <div class="legal">
				<div class="copyright">&copy; 2017 - 2018 Judge Manager.</div>
				<div class="version">
					<b>Version: </b> 1.0.0
				</div>
			</div> -->
			<!-- #Footer -->
		</aside>
		<!-- #END# Left Sidebar -->
	</section>
	<div id="myModal1" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h3 class="modal-title">Create Subject</h3>
				</div>
				<form:form action="addSubject" method="post" modelAttribute="addSubjectForm">
					<div class="form-group">
						<label for="newSub_subjectName">Subject Name</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-text-size"></i>
							</span>
							<input type="text" class="form-control" name="name" id="newSub_subjectName" placeholder="Subject Name" required autofocus>
						</div>
					</div>
					<div class="form-group">
						<label for="newSub_year">Year</label>
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
							<input type="text" class="form-control" name="password" id="newSub_passwordSubject" placeholder="Subject password" required>
						</div>
					</div>
					<div class="form-group">
						<label for="newSub_subjectURL">Subject page</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-link"></i>
							</span>
							<input type="text" class="form-control" name="url" id="newSub_subjectURL" placeholder="Paste Subject page URL" required>
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
					<h3 class="modal-title">Create Contest</h3>
				</div>
				<form:form action="addContest" method="post" modelAttribute="addContestForm">
					<div class="form-group">
						<label for="newContest_contestName">Contest Name</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-text-size"></i>
							</span>
							<input type="text" class="form-control" name="name" id="newContest_contestName" placeholder="Contest Name" required autofocus>
						</div>
					</div>
					<div class="form-group">
						<label for="newContest_subjectName">Subject Name</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-education"></i>
							</span>
							<select class="form-control" id="newContest_subjectName" name="subjectId" required>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="newContest_jury">Jury ID</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-user"></i>
							</span>
							<select class="form-control" id="newContest_jury" name="jury" required>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="newContest_deadline">Deadline</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-calendar"></i>
							</span>
							<input type="text" class="form-control" name="deadline" id="newContest_deadline" placeholder="Deadline" required>
						</div>
					</div>
					
					<div class="form-check generate-group">
				  		<input class="form-check-input" type="checkbox" value=1 id="isExam" name="exam">
				  		<label class="form-check-label" for="isExam"> Is this an Exam?</label>
					</div>
					<div class="form-group hidden">
			    		<label for="newContest_password">Password</label>
						<div class="input-group">
				    		<span class="input-group-addon">
								<i class="glyphicon glyphicon-lock"></i>
							</span>
			  				<input class="form-control" id="newContest_password" name="password" type="password" placeholder="Choose a password" />
				  		</div>
			  		</div>
					<div class="form-group hidden">
			    		<label for="newContest_confPassword">Confirm password</label>
						<div class="input-group">
				    		<span class="input-group-addon">
								<i class="glyphicon glyphicon-lock"></i>
							</span>
			    			<input class="form-control" id="newContest_confPassword" name="confPassword" type="password" placeholder="Confirm password" />
				  		</div>
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
					<h3 class="modal-title">Create Problem</h3>
				</div>
				<form:form action="addProblem" method="post" enctype="multipart/form-data" modelAttribute="problemForm">
					<input type="hidden" name="type" id="type" value="1"/> 
					<div class="form-group">
						<label for="contest">Contest Name</label>
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
						<label for="problemName">Name</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-text-size"></i>
							</span>
							<input type="text" class="form-control" name="name" id="problemName" placeholder="Problem Name" required autofocus>
						</div>
					</div>
					<div class="form-group">
				    	<label for="description">Description</label>
						<div class="input-group">
				    		<span class="input-group-addon">
								<i class="glyphicon glyphicon-pencil"></i>
							</span>
							<textarea class="form-control" id="description" name="description" rows="5" cols="60" placeholder="Insert a Problem description" style="resize: vertical;" required></textarea>		
						</div>
				  	</div>
					<div class="form-group">
				    	<label for="download">Problem essay</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-file"></i>
							</span>
    						<input id="download" name="download" type="file" class="file btn btn-default btn-file" data-show-upload="true" data-show-caption="true" accept=".txt,.pdf" required>
						</div>
			  		</div>
			  		<div class="form-group">
				    	<label for="timeout">Execution timeout (seconds)</label>
						<div class="input-group">
				    		<span class="input-group-addon">
								<i class="glyphicon glyphicon-time"></i>
							</span>
							<input class="form-control" id="timeout" name="timeout" type="number" min=1 step=1 value="5" required />
						</div>
				  	</div>
				  	<br>
				  	<div class="form-check generate-group">
				  		<input class="form-check-input" type="checkbox" value=1 id="show_testcase" name="show_testcase">
				  		<label class="form-check-label" for="show_testcase"> Do you want share input/output files with the students?</label>
					</div>
					<div class="form-group">
				    	<label for="testcase">Input file or Algorithm</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-open-file"></i>
							</span>
    						<input id="testcase" name="testcase" type="file" class="file btn btn-default btn-file" data-show-upload="true" data-show-caption="true" accept=".txt,.dat,.dlv,.java,.cpp,.c,.zip" required>
						</div>
			  		</div>
					<div class="form-group output-group hidden">
				    	<label for="output">Expected output</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-open-file"></i>
							</span>
    						<input id="output" name="output" type="file" class="file btn btn-default btn-file" data-show-upload="true" data-show-caption="true" accept=".txt,.dlv">
						</div>
			  		</div>
			  		<div class="form-check generate-group hidden">
					  <input class="form-check-input" type="checkbox" value="" id="generateInput">
					  <label class="form-check-label" for="generateInput"> Do you want generate inputs?</label>
					</div>
					<div class="form-group hidden">
						<label class="form-check-label" for="domain"> Domain:</label>
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
						<label for="problemTags">Tag</label><br>
						<small class="form-text text-muted">Insert Tags divided by comma or space</small>
						<div class="input-group" id="tagsDiv">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-tags"></i>
							</span>
							<input type="text" class="form-control" name="problemTags" id="problemTags">
						</div>
							<small id="popularTags" class="form-text text-muted">
							</small>
					</div>
					<div class="modal-footer">
						<input type="submit" class="btn btn-primary button-login" value="Add Problem" />
					</div>
				</form:form>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
	<div id="addQuestionModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h3 class="modal-title">Create free Question</h3>
				</div>
				<form:form action="addQuestions" method="post" modelAttribute="addQuestionsForm">
					<div class="form-group">
						<label for="newQuestion_contestName">Contest Name</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-education"></i>
							</span>
							<select class="form-control" id="newQuestion_contestName" name="contestName" required>
							</select>
						</div>
					</div>
					<div class="form-check">
						<input class="form-check-input nav-questionType" type="radio" name="question_types[question1]" id="newQuestion_open" value="open" checked>
					  	<label class="form-check-label" for="newQuestion_open">Open question</label>
					</div>
					<div class="form-check">
			  			<input class="form-check-input nav-questionType" type="radio" name="question_types[question1]" id="newQuestion_closed" value="closed">
				  		<label class="form-check-label" for="newQuestion_closed">Closed question</label>
					</div>
					<div class="row">
						<div class="col-md-9">
							<div class="form-group">
								<label for="newQuestion_name">Question</label>
								<div class="input-group">
									<span class="input-group-addon">
										<i class="glyphicon glyphicon-question-sign"></i>
									</span>
									<input type="text" class="form-control input-sm questionName" name="questions" id="newQuestion_name" placeholder="Insert question" required autofocus>
								</div>
							</div>
						</div>
						<div class="col-md-3">
							<div class="form-group">
						    	<label for="newQuestion_points">Points</label>
								<div class="input-group">
						    		<span class="input-group-addon">
										<i class="glyphicon glyphicon-time"></i>
									</span>
									<input class="form-control input-sm questionPoints" id="newQuestion_points" name="question_points[question1]" type="number" min=1 step=1 value="5" required />
								</div>
						  	</div>
						</div>
					</div>
					<div class="form-group">
						<label for="newQuestion_tags">Tags</label><br>
						<small class="form-text text-muted">Insert Tags divided by comma or space</small>
						<div class="input-group questionTags">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-tags"></i>
							</span>
							<input type="text" class="form-control" name="question_tags[question1]" id="newQuestion_tags">
						</div>
						<small class="form-text text-muted popularQuestionsTags">
						</small>
					</div>
					<div class="modal-footer">
						<input type="submit" class="btn btn-primary" value="Add Question" />
					</div>
				</form:form>
			</div>
		</div>
	</div>
	
	<%@ include file="footer.jsp" %>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/js/bootstrap-datepicker.min.js"></script>
	<script src="resources/plugins/bootstrap-tagsinput/bootstrap-tagsinput.min.js"></script>
	<script src="resources/js/typeahead.bundle.js"></script>
	<script src="resources/js/navbarTeacherScript.js"></script>
	
</body>
</html>