mapsAutocomplete = function() {
	var autocomplete;

	var addressFromPlace = function(place) {
		var address = new Object();

		var addressComponents = place.address_components;

		for (var i = 0; i < addressComponents.length; i++) {
			var component = addressComponents[i];
			var types = component.types;

			for (var j = 0; j < types.length; j++) {
				if (types[j] == 'street_number') {
					address.number = component.long_name;
				} else if (types[j] == 'route') {
					address.street = component.long_name;
				} else if (types[j] == 'administrative_area_level_3') {
					address.city = component.long_name;
				}
			}
		}

		return address;
	};

	return {
		initMaps : function(inputId) {
			autocomplete = new google.maps.places.Autocomplete((document.getElementById(inputId)),
					{
						types : [ 'geocode' ],
						componentRestrictions : {
							country : "it"
						}
					});
		},

		setOnPlaceChangedListener : function(onPlaceChanged) {
			autocomplete.addListener('place_changed', function() {
				var place = autocomplete.getPlace();
				var address = addressFromPlace(place);
				var latitude = place.geometry.location.lat();
				var longitude = place.geometry.location.lng();

				onPlaceChanged(address, {
					latitude : latitude,
					longitude : longitude
				});
			});
		}
	};
}();