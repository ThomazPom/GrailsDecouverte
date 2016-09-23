// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better
// to create separate JavaScript files as needed.
//
//= require jquery-2.2.0.min
//= require bootstrap
//= require_tree .
//= require_self

if (typeof jQuery !== 'undefined') {
    (function($) {
        $('#spinner').ajaxStart(function() {
            $(this).fadeIn();
        }).ajaxStop(function() {
            $(this).fadeOut();
        });
    })(jQuery);
}

var map;
var newform;


$(document).ready(function () {

    if(map = $("#map")[0]){
        map = new google.maps.Map(document.getElementById('map'), {
            center: {lat: 46.856614, lng: 2.3522219000000177},
            zoom: 6
        });


        google.maps.event.addListener(map, 'click', function(event) {
            if(markerClick)
            {
                markerClick.setMap(null);
            }
            markerClick =  createMarkerObject(event.latLng);

            var content =  newForm.clone();

            content.find("input[name='latitude']").attr("value",event.latLng.lat);

            content.find("input[name='longitude']").attr("value",event.latLng.lng);
;

            var infowindow = new google.maps.InfoWindow({
                content: content.html()
            });
                infowindow.open(map, markerClick);
            markerClick.addListener('click', function() {
                infowindow.open(map, markerClick);
            });
            map.setCenter({lat: event.latLng.lat()+3, lng: event.latLng.lng()})
        });


        var newForm = $("form[name='addPOI']");
    }
    else console.log("Info  : Map non trouvée ");


})

var markerClick;

function createMarker(lat, lng) {
   createMarkerObject({lat: lat, lng: lng})
}
function createMarkerObject(location) {
   return new google.maps.Marker({
        position: location,
        map: map,
        title: ''
    });
}


