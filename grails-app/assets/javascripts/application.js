// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better
// to create separate JavaScript files as needed.
//
//= require jquery-2.2.0.min
//= require bootstrap
//= require jquery-ui
//= require_tree .
//= require_self


if (typeof jQuery !== 'undefined') {
    (function ($) {
        $('#spinner').ajaxStart(function () {
            $(this).fadeIn();
        }).ajaxStop(function () {
            $(this).fadeOut();
        });
    })(jQuery);
}


$( 'form.ajax' )
    .submit( function( e ) {
        $.ajax( {
            url: $(this).attr('action'),
            data: new FormData( this ),
            cache: false,
            contentType: false,
            processData: false,
            type: 'POST'
        } ).done(function(data) {
          //  alert(data);
            data1 = data;
                $("#infoModal").modal("show").find(".infoContainer").html($(data))
        });
        e.preventDefault();
    }
    );

var map;
var newform;
var buttonOpenNewForm = '<a data-toggle="modal" data-target="#createPOIModal">Ajouter un POI + </a>';


$(document).ready(function () {
    $('form[name="gEdition"]').find("input").prop("disabled", true);
    $('form[name="gEdition"]').find("select[name=\"selectGroupe\"]").change(function () {
        $('form[name="gEdition"]').find("input").prop("disabled", false);
    });

    $('form[name="uEdition"]').find("input,select:not(.mainSelect)").prop("disabled", true);
    $('form[name="uEdition"]').find("select[name=\"selectGroupe\"]").change(function () {
        $('form[name="gEdition"]').find("input,select").prop("disabled", false);
    });


    $(".listSupress").on("change","input",function () {
        if($(this).prop("checked")){
            $(this).parent().addClass("alert alert-danger")
        }
        else
        {
            $(this).parent().removeClass("alert alert-danger")
        }
    });

    if (map = $("#map")[0]) {
        map = new google.maps.Map(map, {
            center: {lat: 46.856614, lng: 2.3522219000000177},
            zoom: 6
        });


        google.maps.event.addListener(map, 'click', function (event) {
            if (markerClick) {
                markerClick.setMap(null);
            }
            markerClick = createMarkerObject(event.latLng);

            newForm.find("[name='latitude']").val(event.latLng.lat());
            newForm.find("[name='longitude']").val(event.latLng.lng());


            var infowindow = new google.maps.InfoWindow({
                content: buttonOpenNewForm
            });
            infowindow.open(map, markerClick);
            markerClick.addListener('click', function () {
                infowindow.open(map, markerClick);
            });
            map.setCenter(event.latLng);
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



