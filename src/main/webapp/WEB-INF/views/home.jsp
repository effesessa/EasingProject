<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<script type="text/javascript" src="resources/js/jquery.js"></script>
<script type="text/javascript" src="resources/js/bootstrap.js"></script>

<script type="text/javascript" src="resources/js/user/homeUser.js"></script>
<style>
.city {display:none}
</style>

<link rel="stylesheet" type="text/css" href="resources/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="resources/css/homeUser.css" />

<title>Home User</title>

<meta name="viewport" content="width=device-width" />

<style type="text/css">
</style>
</head>
<body>
<c:choose>
	<c:when test="${user.professor == false}">
	<jsp:include page="includes/navbarAccount.jsp" />

	<div class="container">
		<div class="row">
			<div class="col-xs-4">
				<div class="bubble">
					<div class="profile-image-container">
						<img src="resources/images/no-image.png" class="img-circle">
					</div>
					<div class="name-container">${user.name}<span> </span>${user.surname}</div>
					<div class="username-container">${user.id}</div>
				</div>
				<div class="bubble info-bubble">
					<div class="bubble-title">Your latest activity</div>
					<c:choose>
						<c:when test="${teams.size() > 0}">
							<div class="feedbacks-header">
								<a href="createteam">View all Teams</a>
							</div>
							<div class="teams">
								<c:forEach items="${teams}" var="team">
									<div class="feedback">
										<div class="pizzeria-name">
											<a href="createteam">${team.name}</a>
										</div>
									</div>
								</c:forEach>
							</div>
						</c:when>
						<c:otherwise>
							<div class="no-team">You have no team yet.</div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="col-xs-8 wrapper">
				<div class="bubble">
					<div class="bubble-title">Your submissions</div>
					<h4>Latest:</h4>
					<c:forEach items="${submits}" var="submit">
									<div class="submit">
										<div class="submit">
											<a href="/contest?name=">${submit.problem.name} (qua ci metto la data) ${problem.score} </a>
										</div>
									</div>
								</c:forEach>
				</div>
				<div class="bubble">
					<div class="bubble-title">Corsi che segui</div>
					<c:forEach items="${subjects}" var="subject">
									<div class="subject">
										<div class="subject">
											<a href="<c:url value="/subject?name=${subject}" />"> ${subject}</a>
											<a>  </a>
											<a href="https://www.mat.unical.it/informatica"> Sito corso </a>
										</div>
									</div>
						</c:forEach>
				</div>
				<div class="bubble">
					<div class="bubble-title">Contest a cui sei iscritto</div>
					<c:forEach items="${contests}" var="contest">
									<div class="contest">
										<div class="contest">
											<a href="contest?name=${contest.contest.name}"> ${contest.contest.name} ${contest.contest.idcontest}</a>
											
										</div>
									</div>
						</c:forEach>
				</div>
			</div>
		</div>
	</div>
</c:when>

<c:otherwise>
		<jsp:include page="includes/navbarAccount.jsp" />

	<div class="container">
		<div class="row">
			<div class="col-xs-4">
				<div class="bubble">
					<div class="profile-image-container">
						<img src="resources/images/no-image.png" class="img-circle">
					</div>
					<div class="name-container">${user.name}<span> </span>${user.surname}</div>
					<div class="username-container">${user.id}</div>
				</div>
				<form:form id="navbar-search-form" class="navbar-form form-inline" action="searchProblem"
						method="POST" modelAttribute="searchForm">
						<div class="form-group">
							<div class="input-group">
								<input type="text" class="form-control" name="word" placeholder="Search Problem" />
								<div class="input-group-addon navbar-search-submit">
									<span class="glyphicon glyphicon-search"></span>
								</div>
							</div>
						</div>
					</form:form>
				<div class="col-md-5">
				
				<a href="#" data-toggle="modal" data-target="#myModal1" class="btn btn-primary button-add" data-toggle="tooltip" data-placement="bottom">
					<span class="glyphicon glyphicon-plus"></span> Add Subject
				</a><div></div><br>
				<a href="#" data-toggle="modal" data-target="#myModal2" class="btn btn-success button-update" data-toggle="tooltip" data-placement="bottom">
					<span class="glyphicon glyphicon-plus"></span> Add Contest
				</a><div></div><br>
				<a href="#" data-toggle="modal" data-target="#myModal3" class="btn btn-danger button-delete" data-toggle="tooltip" data-placement="bottom">
					<span class="glyphicon glyphicon-plus"></span> Add Problem
				</a><div></div><br>
			</div>
			</div>
			<div class="col-xs-8 wrapper">
				<div class="bubble">
					<div class="bubble-title">Corsi Attivi</div>
					<h4>Latest:</h4>
					<c:forEach items="${subjects}" var="subject">
									<div class="subject">
										<div class="subject">
											<a href="/contest?name=">${subject.name}</a>
										</div>
									</div>
								</c:forEach>
				</div>
				<div class="bubble">
					<div class="bubble-title">Contes Creati:</div>
					<c:forEach items="${contests}" var="contest">
									<div class="subject">
										<div class="subject">
											<a href="<c:url value="/subject?name=${contest.name}" />"> ${contest.name}</a>
											<a>  </a>
											<a href="https://www.mat.unical.it/informatica"> Sito corso </a>
										</div>
									</div>
						</c:forEach>
				</div>
				<div class="bubble">
					<div class="bubble-title">Giuria di cui sei membro:</div>
					<c:forEach items="${contestjuries}" var="contestjury">
									<div class="jury">
										<div class="jury">
											<div> ${contestjury.jury.id_jury}  <a href="https://www.mat.unical.it/informatica"/>(${contestjury.name})</a>
											
											</div>
										</div>
									</div>
						</c:forEach>
				</div>
			</div>
			</div>
			</div>
			<div id="myModal1" class="modal fade">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4 class="modal-title">Crea Nuovo Subject</h4>
							</div>
							<form:form class="navbar-form form-inline" action="addSubject" method="post"
						modelAttribute="addSubjectForm">
						<div class="form-group">
							<div>Id del corso:</div>
							<input type="text" class="form-control" name="id" placeholder="Id">
						</div><br>
							
						<div class="form-group">
							<div>Year:</div>
							<input type="text" class="form-control" name="year" placeholder="Year">
						</div><br>
						<div class="form-group">
							<div>Nome:</div>
							<input type="text" class="form-control" name="name" placeholder="Name">
						</div><br>
						<div class="form-group">
							<div>Password:</div>
							<input type="text" class="form-control" name="password" placeholder="Password">
						</div><br>
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
								<h4 class="modal-title">Crea Contest:</h4>
							</div>
							<form:form class="navbar-form form-inline" action="addContest" method="post"
						modelAttribute="addContestForm">
						<div class="form-group">
							<div>Nome del Contest:</div>
							<input type="text" class="form-control" name="name" placeholder="Name">
						</div><br>							
						<div class="form-group">
							<div>Corso:</div>
							<input type="text" name="subjectId" placeholder="Id Subject">
						</div><br>
						<div class="form-group">
							<div>Giuria:</div>
							<input type="text" name="jury" placeholder="Id Jury">
						</div><br>
						<div class="form-group">
							<div>Iscrizione:</div>
						<input type="radio" name="restriction" value="1" checked>Solo Iscritti<br>
						<input type="radio" name="restriction" value="0" >Tutti<br>
						</div><br>						
						<div class="form-group">
							<div>Data Scadenza:</div>
							<input type="text" maxlength="2" class="form-control" name="day" placeholder="dd">
							<input type="text" maxlength="2" class="form-control" name="month" placeholder="mm">
							<input type="text" maxlength="4" class="form-control" name="year" placeholder="yyyy">
						</div><br>
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
								<h4 class="modal-title">Inserisci Problema</h4>
							</div>
							<div class="w3-bar w3-border-bottom">
   <button class="tablink w3-bar-item w3-button" onclick="openCity(event, '1')">Problem 1</button>
   <button class="tablink w3-bar-item w3-button" onclick="openCity(event, '2')">Problem 2</button>
   <button class="tablink w3-bar-item w3-button" onclick="openCity(event, '3')">Problem 3</button>
   <button class="tablink w3-bar-item w3-button" onclick="openCity(event, '4')">Problem 4</button>
  </div>
  							<div id="1" class="w3-container city">
							<form:form class="navbar-form form-inline" action="addProblem" method="post"
						modelAttribute="problemForm">
						<div class="form-group">
						<input type="hidden" name="type" value="1"/>
						</div>
						<div class="form-group">
							<div>Id del contest:</div>
							<input type="text" class="form-control" name="id" placeholder="Id Subject">
						</div><br>
						<div class="form-group">
							<div>Nome problema:</div>
							<input type="text" class="form-control" name="name" placeholder="Name">
						</div><br>
						<div>Insert TestCaseFile and SolutionFile</div>
						<div class="form-group">
							<div>Path TestCase:</div>
							<input type="text" class="form-control" name="pathTest" placeholder="Path TestCase">
						</div><br>
						<div class="form-group">
							<div>Path Soluzioni:</div>
							<input type="text" class="form-control" name="pathSol" placeholder="Path Solutions">
						</div><br>
						<div class="modal-footer">
						<input type="submit" class="btn btn-primary button-login" value="Add Problem" />
						</div>
						</form:form>
						</div>
						<div id="2" class="w3-container city">
						<form:form class="navbar-form form-inline" action="addProblem" method="post"
						modelAttribute="problemForm">
						<div class="form-group">
						<input type="hidden" name="type" value="2"/>
						</div>
						<div class="form-group">
							<div>Id del contest:</div>
							<input type="text" class="form-control" name="id" placeholder="Id Subject">
						</div><br>
						<div class="form-group">
							<div>Nome problema:</div>
							<input type="text" class="form-control" name="name" placeholder="Name">
						</div><br>
						<div class="form-group">
							<div>Path Zip:</div>
							<input type="text" class="form-control" name="pathZip" placeholder="Path Zip">
						</div><br>
						<div class="modal-footer">
						<input type="submit" class="btn btn-primary button-login" value="Add Problem" />
						</div>
						</form:form>
						</div>
						<div id="3" class="w3-container city">
						<form:form class="navbar-form form-inline" action="addProblem" method="post"
						modelAttribute="problemForm">
						<div class="form-group">
						<input type="hidden" name="type" value="3"/>
						</div>
						<div class="form-group">
							<div>Id del contest:</div>
							<input type="text" class="form-control" name="id" placeholder="Id Subject">
						</div><br>
						<div class="form-group">
							<div>Nome problema:</div>
							<input type="text" class="form-control" name="name" placeholder="Name">
						</div><br>
						<div class="form-group">
							<div>Path Algorithm:</div>
							<input type="text" class="form-control" name="pathAlgorithm" placeholder="Path Algorithm">
						</div><br>
						<div class="modal-footer">
						<input type="submit" class="btn btn-primary button-login" value="Add Problem" />
						</div>
						</form:form>
						</div>
						<div id="4" class="w3-container city">
						<form:form id="4" class="navbar-form form-inline" action="addProblem" method="post"
						modelAttribute="problemForm">
						<div class="form-group">
						<input type="hidden" name="type" value="4"/>
						</div>
						<div class="form-group">
							<div>Id del contest:</div>
							<input type="text" class="form-control" name="id" placeholder="Id Subject">
						</div><br>
						<div class="form-group">
							<div>Nome problema:</div>
							<input type="text" class="form-control" name="name" placeholder="Name">
						</div><br>
						<div class="form-group">
							<div>Path Algorithm:</div>
							<input type="text" class="form-control" name="pathAlgorithm" placeholder="Path Algorithm">
						</div><br>
						<div class="form-group">
							<div>Domain:</div>
						<input type="radio" name="domain" value="Array Integer" checked> Array Integer<br>
						</div><br>
						<div class="modal-footer">
						<input type="submit" class="btn btn-primary button-login" value="Add Problem" />
						</div>
					</form:form>
					</div>
						</div>
					</div>
				</div>
				
				<div id="myModal4" class="modal fade">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4 class="modal-title">Inserisci Problema</h4>
							</div>
							<form:form class="navbar-form form-inline" action="addProblem2" method="post"
						modelAttribute="problemForm">
						<div class="form-group">
							<div>Id del contest:</div>
							<input type="text" class="form-control" name="id" placeholder="Id Contest">
						</div><br>
						<div class="form-group">
							<div>Nome problema:</div>
							<input type="text" class="form-control" name="name" placeholder="Name">
						</div><br>
						<div class="form-group">
							<div>Dominio Applicativo:</div>
							<input type="text" class="form-control" name="description" placeholder="Dominio Applicativo">
						</div><br>
						
						<div class="modal-footer">
						<input type="submit" class="btn btn-primary button-login" value="Add Problem" />
						</div>
					</form:form>
						</div>
					</div>
				</div>
</c:otherwise>
</c:choose>
</body>

<script>
document.getElementsByClassName("tablink")[0].click();

function openCity(evt, cityName) {
  var i, x, tablinks;
  x = document.getElementsByClassName("city");
  for (i = 0; i < x.length; i++) {
    x[i].style.display = "none";
  }
  tablinks = document.getElementsByClassName("tablink");
  for (i = 0; i < x.length; i++) {
    tablinks[i].classList.remove("w3-light-grey");
  }
  document.getElementById(cityName).style.display = "block";
  evt.currentTarget.classList.add("w3-light-grey");
}
</script>

</html>