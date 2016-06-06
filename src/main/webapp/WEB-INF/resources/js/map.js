var colors = [ '#ff0000', '#008000', '#4B0082', '#0000FF', '#FFA500' ];

// class of point
function point(id, time, lat, lng) {
	this.id = id;
	this.time = time;
	this.lat = lat;
	this.lng = lng;
}

/*
 * send request checkout order to server
 */
function requestCheckoutOrder() {
	// disable checkout button
	disableCheckoutButton(true);
	getListCupLength();

	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "order",
		data : JSON.stringify(listCup),
		dataType : 'json',
		timeout : 100000,
		// data is list of Point object
		success : function(data) {
			
		},
		error : function(e) {
			console.log("ERROR " + e);
		},
		done : function(e) {
			console.log("DONE " + e);
		}
	});
}

function initMap() {
	var myLatLng = {
		lat : 21.0054055,
		lng : 105.8442154
	};
	var myLatLng2 = {
		lat : 21.0052525,
		lng : 105.842126
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
		title : 'Hello World!'
	});

	// Create a marker and set its position.
	var marker2 = new google.maps.Marker({
		map : map,
		position : myLatLng2,
		title : 'Hello World!',
		icon : {
			path : google.maps.SymbolPath.CIRCLE,
			scale : 3,
			fillColor : "#F00",
			fillOpacity : 0.4,
			strokeWeight : 0.4
		}
	});
}