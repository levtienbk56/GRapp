function initMap() {
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

$(document).ready(function(){
	$(".progress-bar").fadeOut(0);
});
function requestProcess() {
	$(".progress-bar").fadeIn(0);
	$(".container").fadeOut(0);
}