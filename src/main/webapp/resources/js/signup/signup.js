$(function() {
	$('.js-email-input').on('input propertychange', function() {
		signupController.onEmailChanged($(this).val());
	});

	$('.js-username-input').on('input propertychange', function() {
		signupController.onUsernameChanged($(this).val());
	});

	$('.js-simple-input').on('input propertychange', function() {
		var fieldName = $(this).attr('name');
		signupController.onSimpleFieldChanged(fieldName, $(this).val());
	});

	$('#signup-additional li').on('click', function(event) {
		signupController.onPillClicked($(this));
		event.preventDefault();
	});

	$('.js-location-input').on('input', function() {
		signupController.onLocationInput();
	});
});