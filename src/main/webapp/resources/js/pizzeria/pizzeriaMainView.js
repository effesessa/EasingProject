var pizzeriaMainView = function() {
	var addFeedbackListeners = function() {
		$('.givefeedback-star').on('mouseenter', function() {
			if (!$(this).closest('.givefeedback-stars').hasClass('given')) {
				$(this).attr('src', 'resources/images/star.png')
				$(this).prevAll('.givefeedback-star').attr('src', 'resources/images/star.png')
				$(this).nextAll('.givefeedback-star').attr('src', 'resources/images/star_grey.png')
			}
		});

		$('.givefeedback-star').on('click', function() {
			var $closest = $(this).closest('.givefeedback-stars');

			if ($closest.hasClass('given')) {
				$closest.removeClass('given');
				$closest.find('.givefeedback-star').attr('src', 'resources/images/star_grey.png');
				$closest.find('.glyphicon-ok').hide();
				$closest.find('input').attr('value', '0');
			} else {
				$closest.addClass('given');
				$closest.find('.glyphicon-ok').show();
				$closest.find('input').attr('value', $(this).data('index'));
			}
		});

		$('.givefeedback-stars').on('mouseleave', function() {
			if (!$(this).hasClass('given')) {
				$(this).find('.givefeedback-star').attr('src', 'resources/images/star_grey.png');
			}
		});
	};

	return {
		init : function(pizzeriaLocation) {
			maps.initMap('map', pizzeriaLocation, 'green', 15);
			addFeedbackListeners();
		}
	}
}();