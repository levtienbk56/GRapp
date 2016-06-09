var types = [ "amusement_park", "aquarium", "art_gallery", "bar", "book_store",
		"bowling_alley", "cafe", "campground", "casino", "library",
		"movie_rental", "movie_theater", "museum", "night_club", "park",
		"rv_park", "spa", "stadium", "zoo", "food", "restaurant",
		"beauty_salon", "dentist", "doctor", "gym", "hair_care", "health",
		"hospital", "hindu_temple", "airport", "atm", "bank", "bus_station",
		"car_repair", "car_wash", "cemetery", "church", "courthouse",
		"electrician", "embassy", "establishment", "fire_station",
		"funeral_home", "gas_station", "general_contractor",
		"insurance_agency", "jewelry_store", "laundry", "lawyer",
		"liquor_store", "meal_delivery", "meal_takeaway", "mosque",
		"moving_company", "painter", "parking", "pet_store", "pharmacy",
		"physiotherapist", "place_of_worship", "plumber", "police",
		"real_estate_agency", "roofing_contractor", "storage",
		"subway_station", "synagogue", "taxi_stand", "train_station",
		"travel_agency", "veterinary_care", "local_government_office",
		"locksmith", "lodging", "bakery", "bicycle_store", "car_dealer",
		"car_rental", "clothing_store", "convenience_store",
		"department_store", "electronics_store", "florist", "furniture_store",
		"grocery_or_supermarket", "hardware_store", "home_goods_store",
		"shoe_store", "shopping_mall", "store", "accounting", "city_hall",
		"finance", "post_office", "school", "university" ];

function Coordinate(lat, lng) {
	this.lat = lat;
	this.lng = lng;
}

// class of point gps
function GPSPoint(id, time, lat, lng) {
	this.id = id;
	this.time = time;
	this.latlng = new Coordinate(lat, lng);
}

// Staypoint
function Staypoint(id, time, lat, lng) {
	this.id = id;
	this.time = time;
	this.latlng = new Coordinate(lat, lng);
	this.arr = []; // near points
}

// staypoint with tag
function StaypointTag(id, time, lat, lng) {
	this.id = id;
	this.time = time;
	this.latlng = new Coordinate(lat, lng);
	this.tags = []; // tags
}

var staypoints = [];
var staypointTags = [];

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
					staypoints = data;
					console.log(data);
					$
							.getScript(
									"https://maps.googleapis.com/maps/api/js?key=AIzaSyCJjR5v4RZQBNl1CJJitVFskGzNupL8GiA&callback=initMap",
									function() {
									});
					// request geotag
					// requestGeotag();

				},
				error : function(e) {
					console.log("ERROR " + e);
				},
				done : function(e) {
					console.log("DONE " + e);
				}
			});
}

function requestGeotag() {
	$.ajax({
		type : "POST",
		url : "/GRapp/geotag",
		timeout : 100000,
		// data is list of Point object
		success : function(data) {
			console.log(data);
			var spt;
			for (var j = 0; j < data.length; j++) {
				spt = data[j];
				var str = "<tr>";
				str += "<td>" + spt.time + "</td>";
				str += "<td>" + spt.latlng.lat + ", " + spt.latlng.lng
						+ "</td>";
				str += "<td>";
				for (var i = 0; i < spt.tags.length; i++) {
					str += "<span class='label label-success'>" + spt.tags[i]
							+ "</span> ";
				}
				str += "<td>";
				str += "</tr>";

				// append html as a cup
				$("#label-of-staypoint tr.anchor").before(str);
			}

		},
		error : function(e) {
			console.log("ERROR " + e);
		},
		done : function(e) {
			console.log("DONE " + e);
		}
	});
}

function requestCreateSequence() {
	$.ajax({
		type : "POST",
		url : "/GRapp/sequence",
		timeout : 100000,
		// data is list of Point object
		success : function(data) {
			console.log(data);
			var spt;
			for (var j = 0; j < data.length; j++) {
				spt = data[j];
				var str = "<tr>";
				str += "<td>" + spt.time + "</td>";
				str += "<td>" + spt.latlng.lat + ", " + spt.latlng.lng
						+ "</td>";
				str += "<td>";
				for (var i = 0; i < spt.tags.length; i++) {
					str += "<span class='label label-success'>" + spt.tags[i]
							+ "</span> ";
				}
				str += "<td>";
				str += "</tr>";

				// append html as a cup
				$("#label-of-staypoint tr.anchor").before(str);
			}

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
