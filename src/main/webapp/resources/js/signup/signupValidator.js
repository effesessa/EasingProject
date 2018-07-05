var signupValidator = function() {

	var sendEmailRequest = function(email, onResponse) {
		$.ajax({
			type : "get",
			url : "signup/emailTaken",
			dataType : "json",
			data : {
				email : email
			},
			success : function(data) {
				onResponse(data);
			}
		});
	};

	var sendUsernameRequest = function(username, onResponse) {
		console.log("Sending request for username " + username);

		$.ajax({
			type : "get",
			url : "signup/usernameTaken",
			dataType : "json",
			data : {
				username : username
			},
			success : function(data) {
				onResponse(data);
			}
		});
	};

	return {
		validate : function(fieldName, value) {
			/* Execute a function by its name. */
			return window['signupValidator'][fieldName + 'Validate'](value);
		},

		/*
		 * Provides a simple regex to validate email syntax. It doesn't try to
		 * be overly smart and it just checks for syntax.
		 */
		emailValidate : function(email) {
			if (email.length < 5) {
				return false;
			}

			var regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
			return regex.test(email);
		},

		emailTaken : function(email, onResponse) {
			sendEmailRequest(email, onResponse);
		},

		passwordValidate : function(password) {
			return password.length >= 8;
		},

		usernameValidate : function(username) {
			var regex = /^[A-Za-z0-9_-]{4,}$/;
			return regex.test(username);
		},

		usernameTaken : function(username, onResponse) {
			sendUsernameRequest(username, onResponse);
		},

		firstNameValidate : function(firstName) {
			var regex = /^[A-Za-z]+$/;
			return regex.test(firstName);
		},

		lastNameValidate : function(lastName) {
			var regex = /^[A-Za-z]+$/;
			return regex.test(lastName);
		},

		nameValidate : function(name) {
			var regex = /^[A-Za-z0-9 -]+$/;
			return regex.test(name);
		},

		phoneNumberValidate : function(phoneNumber) {
			var regex = /^[0-9]+([- ][0-9]+)*$/;
			return regex.test(phoneNumber);
		},

		locationValidate : function(locationString) {
			return locationString.length > 0;
		}
	};
}();