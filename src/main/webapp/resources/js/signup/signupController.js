var signupController = function() {

	var validationDelay = 500;
	var emailTimeout;
	var usernameTimeout;

	/*
	 * Processes the response of the server after the sendEmailRequest method.
	 */
	var processEmailResponse = function(data) {
		var $emailContainer = $(".js-email-container");

		if (data.taken) {
			setValidationError($emailContainer, "taken");
		} else {
			setValidationSuccess($emailContainer);
		}
	};

	var processUsernameResponse = function(data) {
		var $usernameContainer = $(".js-username-container");

		if (data.taken) {
			setValidationError($usernameContainer, "taken");
		} else {
			setValidationSuccess($usernameContainer);
		}
	};

	/*
	 * Sets the validation state of a form field to "error". Takes as an
	 * argument the class of the message to show.
	 */
	var setValidationError = function($object, messageClass) {
		var $formGroup = $object.find(".form-group");
		$formGroup.removeClass("has-success");
		$formGroup.addClass("has-error");

		var $glyphicon = $object.find(".glyphicon");
		$glyphicon.removeClass("glyphicon-ok");
		$glyphicon.addClass("glyphicon-remove");

		var $currentMessage = $object.find(".message.visible");
		var $newMessage = $object.find(".message." + messageClass);

		$currentMessage.removeClass("visible");
		$newMessage.addClass("visible");

		$object.find(".loader").hide();
		checkButton();
	};

	/*
	 * Sets the validation state of a form field to "success".
	 */
	var setValidationSuccess = function($object) {
		var $formGroup = $object.find(".form-group");
		$formGroup.removeClass("has-error");
		$formGroup.addClass("has-success");

		var $glyphicon = $object.find(".glyphicon");
		$glyphicon.removeClass("glyphicon-remove");
		$glyphicon.addClass("glyphicon-ok");

		var $validMessage = $object.find(".message.valid");
		var $currentMessage = $object.find(".message.visible");
		$currentMessage.removeClass("visible");
		$validMessage.addClass("visible");

		$object.find(".loader").hide();
		checkButton();
	};

	/*
	 * Clears the validation state of a form field. It will show the loading
	 * icon if showLoader is true, or hide it if showLoader is false.
	 */
	var clearValidationState = function($object, showLoader) {
		var $formGroup = $object.find(".form-group");
		$formGroup.removeClass("has-success");
		$formGroup.removeClass("has-error");

		var $glyphicon = $object.find(".glyphicon");
		$glyphicon.removeClass("glyphicon-ok");
		$glyphicon.removeClass("glyphicon-remove");

		var $currentMessage = $object.find(".message.visible");
		$currentMessage.removeClass("visible");

		/*
		 * If the loader is shown, validation is occurring, so the right message
		 * must be shown as well.
		 */
		if (showLoader) {
			$object.find(".loader").show();
			$object.find(".message.validating").addClass("visible");
		} else {
			$object.find(".loader").hide();
			$object.find(".message.hint").addClass("visible");
		}

		checkButton();
	}

	/**
	 * Checks if the Sign Up button must be enabled or not, and enabled (or
	 * disables) it if necessary.
	 */
	var checkButton = function() {
		var $formGroups = $('#sign-up-form .form-group');

		for (var i = 0; i < $formGroups.length; i++) {
			if (!$formGroups.eq(i).hasClass('has-success')) {
				$('#sign-up-form .button-submit').attr('disabled', 'disabled');
				return false;
			}
		}

		$('#sign-up-form .button-submit').removeAttr('disabled');

		return true;
	};

	return {
		/*
		 * Each time the user types a character into the email input, check for
		 * its validity. Then, wait for the user to type in something else. If
		 * the user doesn't type anything for some time (validation delay) fire
		 * a request to validate the email again server-side and to check if the
		 * email has already been taken.
		 */
		onEmailChanged : function(email) {
			clearTimeout(emailTimeout);
			var $emailContainer = $(".js-email-container");

			if (email.length == 0) {
				clearValidationState($emailContainer, false);
			} else if (signupValidator.emailValidate(email)) {
				clearValidationState($emailContainer, true);
				console.log("Email ok, sending request.")
				emailTimeout = setTimeout(function() {
					signupValidator.emailTaken(email, processEmailResponse);
				}, validationDelay);
			} else {
				setValidationError($emailContainer, "error");
			}
		},

		onUsernameChanged : function(username) {
			clearTimeout(usernameTimeout);
			var $usernameContainer = $(".js-username-container");

			if (username.length == 0) {
				clearValidationState($usernameContainer, false);
			} else if (signupValidator.usernameValidate(username)) {
				clearValidationState($usernameContainer, true);
				console.log("Username ok, sending request.")
				usernameTimeout = setTimeout(function() {
					signupValidator.usernameTaken(username, processUsernameResponse);
				}, validationDelay);
			} else {
				setValidationError($usernameContainer, "error");
			}
		},

		/**
		 * Generic callback for every field which does not require server side
		 * checks.
		 */
		onSimpleFieldChanged : function(fieldName, value) {

			var $container = $('.js-' + fieldName + '-container');

			if (value.length == 0) {
				clearValidationState($container, false);
			} else if (signupValidator.validate(fieldName, value)) {
				setValidationSuccess($container);
			} else {
				setValidationError($container, 'error');
			}
		},

		onPlaceChanged : function(address, location) {
			$('#sign-up-form .js-street-input').val(address.street);
			$('#sign-up-form .js-number-input').val(address.number);
			$('#sign-up-form .js-city-input').val(address.city);
			$('#sign-up-form .js-latitude-input').val(location.latitude);
			$('#sign-up-form .js-longitude-input').val(location.longitude);

			var $container = $('#sign-up-form .js-location-container');
			setValidationSuccess($container);
		},

		onLocationInput : function() {
			var $container = $('#sign-up-form .js-location-container');

			if ($container.find('#sign-up-form .js-location-form').hasClass('has-success')) {
				clearValidationState($container);
			}
		},

		onPillClicked : function($li) {
			$li.addClass('active');
			$li.siblings('li').removeClass('active');
			
			var $form = $('#sign-up-form');
			var accountType = $li.data('additional');

			/*
			 * Update the action attribute of the form to point to the right
			 * controller.
			 */
			$form.attr('action', accountType + 'Signup');

			/*
			 * Detach the additional which is currently inside the form, append
			 * it to the body, and hide it.
			 */
			$('#sign-up-form .additional').detach().appendTo('body').hide();

			/*
			 * Detach the additional that must be added to the form, append it
			 * to the form, and make it visible.
			 */
			$('.additional.additional-' + accountType).detach().appendTo('#sign-up-form').show();
			
			/* Initialize maps autocomplete. */
			mapsAutocomplete.initMaps('maps-autocomplete-' + accountType);
			mapsAutocomplete.setOnPlaceChangedListener(function(latitude, longitude) {
				signupController.onPlaceChanged(latitude, longitude);
			});

			/* Check the sign up button at the end. */
			checkButton();
		}
	};
}();