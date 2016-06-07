var colors = [ '#ff0000', '#008000', '#4B0082', '#0000FF', '#FFA500' ];

function getcolor(i) {
	return colors[i % 5];
}

function Coordinate(lat, lng) {
	this.lat = lat;
	this.lng = lng;
}

// class of point gps
function GPSPoint(id, time, lat, lng) {
	this.id = id;
	this.time = time;
	this.lat = lat;
	this.lng = lng;
}

// Staypoint
function Staypoint(id, time, lat, lng) {
	this.id = id;
	this.time = time;
	this.avgCoordinate = new Coordinate(lat, lng);
	this.arr = [];
}

var centerMap = {
	lat : 21.006071,
	lng : 105.8416976
};

var staypoints = [];

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
			lat : sp.avgCoordinate.lat,
			lng : sp.avgCoordinate.lng
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
				lat : point.lat,
				lng : point.lng
			};

			/** Create a marker and set its position. */
			var marker2 = new google.maps.Marker({
				map : map,
				position : myLatLng,
				title : point.time,
				icon : {
					path : google.maps.SymbolPath.CIRCLE,
					scale : 3,
					fillColor : getcolor(i),
					fillOpacity : 1,
					strokeWeight : 0.8
				}
			});
		}

	}

}

/*
 * send request get staypoint list. @callback: then draw MAP
 */
function requestExtractStaypoint() {
	$
			.ajax({
				type : "POST",
				url : "/GRapp/staypoint",
				timeout : 100000,
				// data is list of Point object
				success : function(data) {
					// var p1 = new GPSPoint(1, "2008-02-02", 21.005786,
					// 105.8397683);
					// var p2 = new GPSPoint(1, "2008-02-03", 21.004935,
					// 105.8396183);
					// var p3 = new GPSPoint(2, "2008-02-04", 21.005115,
					// 105.8446613);
					// var p4 = new GPSPoint(2, "2008-02-05", 21.004294,
					// 105.8444683);
					// var sp1 = new Staypoint(1, "2008-02-02", 21.005356,
					// 105.8401763);
					// var sp2 = new Staypoint(2, "2008-02-04", 21.0053159,
					// 105.8452832);
					// sp1.arr.push(p1);
					// sp1.arr.push(p2);
					// sp2.arr.push(p3);
					// sp2.arr.push(p4);
					// staypoints.push(sp1);
					// staypoints.push(sp2);
					// centerMap = {
					// lat : (sp1.lat + sp2.lat) / 2,
					// lng : (sp1.lng + sp2.lng) / 2
					// };
					// console.log(sp1);

					staypoints = data;
					console.log(data);
					$
							.getScript(
									"https://maps.googleapis.com/maps/api/js?key=AIzaSyCJjR5v4RZQBNl1CJJitVFskGzNupL8GiA&callback=initMap",
									function() {
									});
				},
				error : function(e) {
					console.log("ERROR " + e);
				},
				done : function(e) {
					console.log("DONE " + e);
				}
			});
}

$(document).ready(function() {
	requestExtractStaypoint();
});
