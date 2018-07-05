var homeUser = function() {

	var center;

	var showPizzeriasMarkers = function(pizzerias) {
		for (var i = 0; i < pizzerias.length; i++) {
			var marker = maps.createAndShowMarker({
				latitude : pizzerias[i].latitude,
				longitude : pizzerias[i].longitude
			}, 'green');

			maps.attachInfoWindowToMarker(marker, pizzerias[i]);
		}
	};

	var showCircle = function(center, radius) {
		maps.createAndShowCircle(center, radius)
	}

	var requestPizzerias = function(center, radius) {
		$.ajax({
			method : 'post',
			url : '/pizzeria/nearby',
			dataType : 'json',
			data : {
				latitude : center.latitude,
				longitude : center.longitude,
				radius : radius
			},

			success : function(response) {
				if (response.success) {
					showPizzeriasMarkers(response.pizzerias);
					showCircle(center, response.radius);
				}
			}
		});
	};

	var initListeners = function() {
		$('.button-radius').on('click', function() {
			maps.clearMap();
			requestPizzerias(center, $('.form-radius').val());
		});
	}

	return {
		init : function() {
			initListeners();

			maps.locate(function(location) {
				// onSuccess
				center = location;

				maps.initMap('map', center, 'red', 12);
				requestPizzerias(center, $('.form-radius').val());
			}, function() {
				// onFailure
				maps.initMapWithError('map');
			});
		}
	};
}();

$(function() {
	homeUser.init();
});