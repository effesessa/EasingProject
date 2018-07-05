<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="resources/js/jquery.js"></script>
<script type="text/javascript" src="resources/js/bootstrap.js"></script>
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?libraries=places"></script>
<script type="text/javascript" src="resources/js/maps.js"></script>
<script type="text/javascript" src="resources/js/mapsAutocomplete.js"></script>
<script type="text/javascript" src="resources/js/signup/signup.js"></script>
<script type="text/javascript" src="resources/js/signup/signupValidator.js"></script>
<script type="text/javascript" src="resources/js/signup/signupController.js"></script>

<link rel="stylesheet" type="text/css" href="resources/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="resources/css/signup.css" />

<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Sign Up to Pizza Manager</title>
</head>
<body>
	<jsp:include page="includes/navbarLogin.jsp" />

	<div class="container">
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-7">
				<div class="bubble">
					<h2>Sign up to Pizza Manager!</h2>
					<c:if test="${signUpError}">
						<div class="sign-up-error">There was an error in your request. Please repeat the sign up
							process.</div>
					</c:if>
					<form id="sign-up-form" action="signup" method="post">
						<div class="row js-email-container">
							<div class="col-md-5 input-container">
								<div class="form-group has-feedback js-email-form">
									<input type="text" name="email" class="form-control js-email-input" placeholder="Email" />
									<span class="glyphicon form-control-feedback"></span> <img
										class="loader form-control-feedback" src="resources/gifs/loader.gif" />
								</div>
							</div>
							<div class="col-md-7">
								<div class="message hint visible">Insert your email.</div>
								<div class="message valid">Email seems ok.</div>
								<div class="message validating">Validating...</div>
								<div class="message taken">An user with this email already exists.</div>
								<div class="message error">Invalid email.</div>
							</div>
						</div>
						<div class="row js-password-container js-simple-container">
							<div class="col-md-5 input-container">
								<div class="form-group has-feedback js-password-form">
									<input type="text" name="password" class="form-control js-password-input js-simple-input"
										placeholder="Password" /> <span class="glyphicon form-control-feedback"></span>
								</div>
							</div>
							<div class="col-md-7">
								<div class="message hint visible">Your password must be at least 8 characters long.</div>
								<div class="message valid">Password is ok.</div>
								<div class="message error">Your password must be at least 8 characters long.</div>
							</div>
						</div>
						<div id="signup-additional">
							<div>You are creating an account for...</div>
							<div>
								<ul class="nav nav-pills nav-justified">
									<li data-additional="user"><a href="#">User</a></li>
									<li data-additional="pizzeria"><a href="#">Pizzeria</a></li>
								</ul>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="additional-user additional">
		<div class="row js-username-container">
			<div class="col-md-5 input-container">
				<div class="form-group has-feedback js-username-form">
					<input type="text" name="username" class="form-control js-username-input"
						placeholder="Username" /> <span class="glyphicon form-control-feedback"></span><img
						class="loader form-control-feedback" src="resources/img/loader.gif" />
				</div>
			</div>
			<div class="col-md-7">
				<div class="message hint visible">Choose a username.</div>
				<div class="message valid">Username is ok.</div>
				<div class="message validating">Validating...</div>
				<div class="message taken">An user with this username already exists.</div>
				<div class="message message-multiline error">Username must contain only letters, numbers,
					_ and - and must be at least 4 characters long.</div>
			</div>
		</div>
		<div class="row js-firstName-container js-simple-container">
			<div class="col-md-5 input-container">
				<div class="form-group has-feedback js-firstName-form">
					<input type="text" name="firstName" class="form-control js-first-name-input js-simple-input"
						placeholder="First name" /> <span class="glyphicon form-control-feedback"></span>
				</div>
			</div>
			<div class="col-md-7">
				<div class="message hint visible">Insert your first name.</div>
				<div class="message valid">First name is ok.</div>
				<div class="message error">First name must contain only letters.</div>
			</div>
		</div>
		<div class="row js-lastName-container js-simple-container">
			<div class="col-md-5 input-container">
				<div class="form-group has-feedback js-lastName-form">
					<input type="text" name="lastName" class="form-control js-last-name-input js-simple-input"
						placeholder="Last name" /> <span class="glyphicon form-control-feedback"></span>
				</div>
			</div>
			<div class="col-md-7">
				<div class="message hint visible">Insert your last name.</div>
				<div class="message valid">Last name is ok.</div>
				<div class="message error">Last name must contain only letters.</div>
			</div>
		</div>
		<div class="row js-location-container">
			<div class="col-md-8 input-container">
				<div class="form-group has-feedback js-location-form">
					<input type="text" name="location" id="maps-autocomplete-user"
						class="form-control js-location-input" placeholder="Search for your address" /><span
						class="glyphicon form-control-feedback"></span>
				</div>
			</div>
			<div class="col-md-4">
				<div class="message hint visible">Select your address.</div>
				<div class="message valid">Address selected.</div>
			</div>
		</div>
		<div class="hidden-fields-container">
			<input class="js-street-input" type="hidden" name="street" /> <input class="js-number-input"
				type="hidden" name="number" /> <input class="js-city-input" type="hidden" name="city" /> <input
				class="js-latitude-input" type="hidden" name="latitude" /> <input class="js-longitude-input"
				type="hidden" name="longitude" />
		</div>
		<div class="button-submit-container">
			<button type="submit" class="btn btn-primary button-submit" disabled>Sign up</button>
		</div>
	</div>
	<div class="additional-pizzeria additional">
		<div class="row js-name-container js-simple-container">
			<div class="col-md-5 input-container">
				<div class="form-group has-feedback js-name-form">
					<input type="text" name="name" class="form-control js-name-input js-simple-input"
						placeholder="Name" /> <span class="glyphicon form-control-feedback"></span>
				</div>
			</div>
			<div class="col-md-7">
				<div class="message hint visible">Insert the name of your pizzeria.</div>
				<div class="message valid">Name is ok.</div>
				<div class="message error">Name must contain only letters, numbers or -.</div>
			</div>
		</div>
		<div class="row js-phoneNumber-container js-simple-container">
			<div class="col-md-5 input-container">
				<div class="form-group has-feedback js-phoneNumber-form">
					<input type="text" name="phoneNumber" class="form-control js-phoneNumber-input js-simple-input"
						placeholder="Phone number" /> <span class="glyphicon form-control-feedback"></span>
				</div>
			</div>
			<div class="col-md-7">
				<div class="message hint visible">Insert the phone number.</div>
				<div class="message valid">Phone number is ok.</div>
				<div class="message error">Insert a valid phone number.</div>
			</div>
		</div>
		<div class="row js-location-container">
			<div class="col-md-8 input-container">
				<div class="form-group has-feedback js-location-form">
					<input type="text" name="location" id="maps-autocomplete-pizzeria"
						class="form-control js-location-input" placeholder="Search for your address" /><span
						class="glyphicon form-control-feedback"></span>
				</div>
			</div>
			<div class="col-md-4">
				<div class="message hint visible">Select your address.</div>
				<div class="message valid">Address selected.</div>
			</div>
		</div>
		<div class="hidden-fields-container">
			<input class="js-street-input" type="hidden" name="street" /> <input class="js-number-input"
				type="hidden" name="number" /> <input class="js-city-input" type="hidden" name="city" /> <input
				class="js-latitude-input" type="hidden" name="latitude" /> <input class="js-longitude-input"
				type="hidden" name="longitude" />
		</div>
		<div class="button-submit-container">
			<button type="submit" class="btn btn-primary button-submit" disabled>Sign up</button>
		</div>
	</div>
	<div id="emptyDiv"></div>
</body>
</html>