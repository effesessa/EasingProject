<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
				data-target="#id-navbar-collapse">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
			<span class="navbar-brand"> <img alt="Brand" src="resources/images/images.png">
			</span> <a class="navbar-brand" href="/">JudgeManager</a>
		</div>

		<div class="collapse navbar-collapse" id="id-navbar-collapse">
			<ul class="nav navbar-nav">
				<!-- <li><a href="#">Link1</a></li>-->
				<%--if session user condition --%>
				<!--<li><a href="#">Cart</a></li>-->
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><form:form id="navbar-search-form" class="navbar-form form-inline" action="search"
						method="POST" modelAttribute="searchForm">
						<div class="form-group">
							<div class="input-group">
								<input type="text" class="form-control" name="word" placeholder="Search" />
								<div class="input-group-addon navbar-search-submit">
									<span class="glyphicon glyphicon-search"></span>
								</div>
							</div>
						</div>
					</form:form></li>
				<!-- <li><a href="#">Adv.Search</a></li> -->
				<c:if test="${user != null}">
					<li><a href="/index2">Settings</a></li>
				</c:if>
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"> <span
						class="glyphicon glyphicon-user button-glyphicon"></span> <!-- One will be empty and won't be printed. -->
						${user.name} ${pizzeria.name} <span class="caret"></span>
				</a>
					<ul class="dropdown-menu">
						<li><a href="/logout">Log out</a></li>
					</ul></li>
			</ul>
		</div>
	</div>
</nav>
<script>
	var $searchButton = $('.navbar-search-submit');
	$searchButton.on('click', function() {
		$('form#navbar-search-form').submit();
	});
</script>