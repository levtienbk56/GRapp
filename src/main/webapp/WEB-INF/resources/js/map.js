var modulColor=(function(){
     var colors = [ '#ff0000', '#008000', '#4B0082', '#0000FF', '#FFA500' ];
     return {
            getColor:function(i) {
                return colors[i % 5];
            }
      }
}());

var centerMap = {
	lat : 21.006071,
	lng : 105.8416976
};

function initDefaultMap() {
	var myLatLng = {
		lat : 21.006071,
		lng : 105.8416976
	};

	// Create a map object and specify the DOM element for display.
	var map = new google.maps.Map(document.getElementById('map'), {
		center : myLatLng,
		scrollwheel : false,
		zoom : 15
	});

	// Create a marker and set its position.
	var marker = new google.maps.Marker({
		map : map,
		position : myLatLng,
		title : 'Trường Đại học Bách khoa Hà Nội!'
	});
}

function initMap() {
	// Create a map object and specify the DOM element for display.
	var map = new google.maps.Map(document.getElementById('map'), {
		center : centerMap,
		scrollwheel : false,
		zoom : 15
	});

	for (var i = 0; i < staypoints.length; i++) {
		var sp = staypoints[i];
		var points = sp.arr;

		/** view centroid of staypoint */
		var centroid = {
			lat : sp.latlng.lat,
			lng : sp.latlng.lng
		};
		// Create a marker and set its position.
		var marker = new google.maps.Marker({
			map : map,
			position : centroid,
			title : sp.time
		});

		/** view near point of staypoint */
		for (var j = 0; j < points.length; j++) {
			var point = points[j];

			var myLatLng = {
				lat : point.latlng.lat,
				lng : point.latlng.lng
			};

			/** Create a marker and set its position. */
			var marker2 = new google.maps.Marker({
				map : map,
				position : myLatLng,
				title : point.time,
				icon : {
					path : google.maps.SymbolPath.CIRCLE,
					scale : 3,
					fillColor : modulColor.getColor(i),
					fillOpacity : 1,
					strokeWeight : 0.8
				}
			});
		}

	}

}