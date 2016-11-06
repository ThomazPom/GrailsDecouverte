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


if (typeof jQuery !== 'undefined') {
    (function ($) {
        $('#spinner').ajaxStart(function () {
            $(this).fadeIn();
        }).ajaxStop(function () {
            $(this).fadeOut();
        });
    })(jQuery);
}


var map;
var buttonOpenNewForm = '<a data-toggle="modal" data-target="#createPOIModal">Ajouter un POI + </a>';


$(document).ready(function () {

    $(this).on('click', 'a[data-target="#createPOIModal"]', function () {
        var form = $("form[name='addPOI']");
        var select = form.find("[name='selectGroupe']");


        JSONGet('/A_Groupe/getGroupes', null, function (data) {
            select.empty();
            $(data.data).each(function () {
                select.append($('<option>', {
                    value: this.id,
                    text: this.nom
                }));
            });
        })
    });


    markerDelete = "MarkerDelete";
    openmarkerview = function (idView) {

        var form = $("div[name='viewPOI']");
        var imgContainer = form.find(".imgContainer").empty();
        var ul = form.find("[name='selectGroupe']");
        var id = Number.isInteger(idView) ? idView : this.get("id");
        if (!Number.isInteger(idView)) {
            markerDelete = this;
        }
        var poi;

        JSONGet('/A_POI/getOne/' + id, null, function (data) {
            poi = data.data;
            form.find("[name='nom']").text(data.data.nom);
            form.find("[name='latitude']").text(data.data.latitude);
            form.find("[name='longitude']").text(data.data.longitude);
            form.find("[name='description']").text(data.data.description);

            var buttonEdit = form.find("#editPOIButton");
            buttonEdit.hide();
            if (data.data2) {
                buttonEdit.show();
                buttonEdit.data("id", data.data.id)
            }

            ul.empty();
            JSONGet('/A_Groupe/getGroupsPOI/' + data.data.id, null, function (data) {
                $(data.data).each(function () {
                    ul.append($('<li>', {
                        value: this.id,
                        text: this.nom
                    }));
                });
            });
        });

        $("#viewPOIModal").modal().show();


        JSONGet('/A_POI/getOnePhotos/' + id, null, function (data) {

            $(data.data).each(function () {

                imgContainer.prepend(
                    $('<div>', {
                        class: 'imgItem'
                    }).prepend($('<img>', {
                        src: this.url,
                        class: 'img-responsive'
                    }))
                )
            })
        })
    };

    openmarkerdelete = function (idDel, nom) {
        var id = Number.isInteger(idDel) ? idDel : $(this).data("id");
        var form = $("form[name='delPOI']");
        form.find(".paramId").val(id);
        console.log((typeof nom === 'string' || nom instanceof String) ? nom : $(this).data("nom"));

        $("#delPOIModal").modal("show").find("#confirmDeletePOIName").html((typeof nom === 'string' || nom instanceof String) ? nom : $(this).data("nom"));
    }

    openmarkeredit = function (idEdit) {

        var form = $("form[name='majPOI']");
        var imgContainer = form.find(".imgContainer").empty();
        var select = form.find("[name='selectGroupe']");
        var id = Number.isInteger(idEdit) ? idEdit : $(this).data("id");
        var poi;

        JSONGet('/A_POI/getOne/' + id, null, function (data) {
            poi = data.data;
            form.find(".paramId").val(data.data.id);
            form.find("[name='nom']").val(data.data.nom);
            form.find("[name='latitude']").val(data.data.latitude);
            form.find("[name='longitude']").val(data.data.longitude);
            form.find("[name='description']").val(data.data.description);


            var buttonDel = $("#delPOIButton");
            buttonDel.data("id", data.data.id);
            buttonDel.data("nom", data.data.nom);


            JSONGet('/A_Groupe/getGroupes', null, function (dataGet) {
                select.empty();
                $(dataGet.data).each(function () {

                    select.append($('<option>', {
                        value: this.id,
                        text: this.nom
                    }));
                });
                $(data.data.groupes).each(function () {
                    select.find("option[value='" + this.id + "']").prop("selected", true);
                })
            });
        });


        $("#viewPOIModal").modal("hide");
        $("#majPOIModal").modal("show");


        JSONGet('/A_POI/getOnePhotos/' + id, null, function (data) {

            $(data.data).each(function () {

                imgContainer.prepend(
                    $('<div>', {
                        class: 'imgItem'
                    }).prepend($('<input>', {
                        type: 'checkbox',
                        name: 'imgid',
                        val: this.id,
                        class: 'hidden'
                    }).prop('checked', true))
                        .prepend($('<img>', {
                            src: this.url,
                            class: 'img-responsive'
                        })).append($("<div>", {class: 'removeimg', text: '✘'}))
                )
            })
        })
    };

    $("#editPOIButton").click(openmarkeredit);

    $("#delPOIButton").click(openmarkerdelete);
    JSONGet('/A_POI/getAll', null, function (data) {

        $(data.data).each(function () {
                var markerClick = createMarker(this.latitude, this.longitude, this.id);

                markerClick.addListener('click', openmarkerview);

            }
        );
    });

    $('form[name="gEdition"]').find("input").prop("disabled", true);


    $('form[name="gEdition"]').find("select[name=\"selectGroupe\"]").change(function () {

        var select = $(this);


        $('form[name="gEdition"]').find("input").prop("disabled",
            true
        );
        if (this.value) {
            $('form[name="gEdition"]').find("input").prop("disabled",
                false
            );
            select.closest('form').find('input[name="nom"]').val(select.find(":selected").text());

            JSONGet("/A_Groupe/getLogoGroup/" + select.val(), null, function (data) {
                $('form[name="gEdition"]').find("img[name='apercu']").attr('src', data.data.url)
            })


        }
    });


    $('form.ajax')
        .submit(function (e) {
                e.preventDefault();
                var paramId = "";
                var form = $(this);
                var paramIdDom = form.find(".paramId");


                if (paramIdDom.length > 0) {
                    paramId = "/" + paramIdDom.val();
                }
                $.ajax({
                    url: form.attr('action') + paramId,
                    data: new FormData(this),

                    cache: false,
                    contentType: false,
                    processData: false,
                    type: 'POST'
                }).done(function (data) {
                    console.log(form);

                    if (form[0].hasAttribute("callback")) {
                        form.trigger(form.attr("callback"), data)
                    }
                    if (form.hasClass("reset")) {
                        form[0].reset();
                    }

                }).always(function (data) {
                    if (data.responseJSON) {
                        data = data.responseJSON
                    }

                    if (!form.hasClass("nomodal")) {

                        var infocontainer = $("#infoModal").modal("show").find(".infoContainer").html($('<div>', {
                            class: "alert alert-" + data.status||"",
                            html: data.message?data.message.replace(new RegExp("\n", 'g'), "<br>"):""
                        }))
                    }

                    function fillContainer(data) {

                        if (Array.isArray(data)) {
                            $(data).each(function () {
                                infocontainer.append($('<div>', {
                                    class: "alert alert-" + this.status||"",
                                    html: this.message?this.message.replace(new RegExp("\n", 'g'), "<br>"):""
                                }));
                                fillContainer(this.data);
                            })

                        }
                    }

                    fillContainer(data.data)

                });
                ;
            }
        );

    $('form[name="uEdition"]').find("input,select:not(.mainSelect)").prop("disabled", true);

    $('form[name="uEdition"]').find("select[name=\"selectUser\"]").change(function () {

            var select = $(this);


            $('form[name="uEdition"]').find("input,select").prop("disabled",
                true
            );
            if (this.value) {
                $('form[name="uEdition"]').find("input,select").prop("disabled",
                    false
                );
                var form = select.closest('form')


                var selectRoles = form.find("select[name='selectRole']");

                JSONGet("/A_User/getUser/" + select.val(), null, function (data) {
                    select.find("option[value='" + select.val() + "']").text(data.data.username);
                    form.find("[name='username']").val(data.data.username);
                    selectRoles.val(data.data.role);
                })
            }
        }
    );


    $("[data-target='#gestionUserModal'],a[href='#uEdition']").click(function () {

        var form = $('form[name="uEdition"]');
        var select = form.find("select[name='selectUser']");

        var selectRoles = form.find("select[name='selectRole']");
        select.empty();
        selectRoles.empty();
        JSONGet("/A_User/getUsers/", null, function (data) {

            $(data.data).each(function () {
                select.append($('<option>', {
                    value: this.id,
                    text: this.id + " - " + this.username
                }));
            });
        });
        JSONGet("/A_User/getRoles/", null, function (data) {

            $(data.data).each(function () {
                selectRoles.append($('<option>', {
                    value: this.id,
                    text: this.id + " - " + this.authority
                }));
            });
        });

    });


    $("#addPOISend").click(function (e) {
        $("form[name='addPOI']").submit();
    });

    $("#majPOISend").click(function (e) {
        $("form[name='majPOI']").submit();
    });

    $(".listSupress").on("click", "li", function () {
        var chkbox = $(this).find("input");
        if (chkbox.prop("checked")) {
            chkbox.prop("checked", false);
            $(this).removeClass("alert alert-danger")
        }
        else {
            chkbox.prop("checked", true);

            $(this).addClass("alert alert-danger")
        }
    });
    $(".listSupress").on("change", "input", function () {
        if ($(this).prop("checked")) {
            $(this).parent().addClass("alert alert-danger")
        }
        else {
            $(this).parent().removeClass("alert alert-danger")
        }
    });

    $('body').on('click', '.removeimg', function () {
        $(this).parent().remove();
    });

    $('form[name="delPOI"]').on("delPOICallback", function (event, data) {
        $("#delPOIModal").modal("hide");

        $("#majPOIModal").modal("hide");
        markerDelete.setMap(null);
    });

    $('form[name="poiPhoto"]').on("poiPhotoCallBack", function (event, data) {
        var form = $("form[name='addPOI']");
        var imgContainer = form.find(".imgContainer");

        imgContainer.prepend(
            $('<div>', {
                class: 'imgItem'
            }).prepend($('<input>', {
                type: 'checkbox',
                name: 'imgid',
                val: data.data.id,
                class: 'hidden'
            }).prop('checked', true))
                .prepend($('<img>', {
                    src: data.data.url,
                    class: 'img-responsive'
                })).append($("<div>", {class: 'removeimg', text: '✘'}))
        )

    });

    $('form[name="addPOI"]').on("addPOICallBack", function (event, data) {
        markerClick.setMap(null);
        $("#createPOIModal").modal("hide");
        createMarker(data.data.latitude, data.data.longitude, data.data.id).addListener('click', openmarkerview);
        openmarkerview(data.data.id);
        $(this).find(".imgContainer").empty();

    });

    $('form[name="majPoiPhoto"]').on("majPoiPhotoCallBack", function (event, data) {

        var form = $("form[name='majPOI']");
        var imgContainer = form.find(".imgContainer");

        imgContainer.prepend(
            $('<div>', {
                class: 'imgItem'
            }).prepend($('<input>', {
                type: 'checkbox',
                name: 'imgid',
                val: data.data.id,
                class: 'hidden'
            }).prop('checked', true))
                .prepend($('<img>', {
                    src: data.data.url,
                    class: 'img-responsive'
                })).append($("<div>", {class: 'removeimg', text: '✘'}))
        )

    });


    $('form[name="uEdition"]').on("uEditionCallBack", function (event, data) {
        var form = $(this);
        var select = form.find("select[name='selectUser']");
        select.trigger('change');
    });

    $('form[name="gCreation"]').on("gCreationCallBack", function (event, data) {
        var form = $('form[name="gEdition"]');
        $("a[href='#gEdition']").tab('show').trigger("click", data);
        //   form.find("select[name='selectGroupe']").val(data.data.id).trigger("change");
        //   console.log(data.data.id);
    });

    $('form[name="gEdition"]').on("gEditionCallBack", function (event, data) {
        var form = $(this);
        //select.find(":selected").text()
        form.find('input[name="nom"]').val(data.data.nom);
        form.find("select[name='selectGroupe'] option[value='" + data.data.id + "']").text(data.data.nom);
        JSONGet("/A_Groupe/getLogoGroup/" + data.data.id, null, function (data) {
            form.find("img[name='apercu']").attr('src', data.data.url)
        })

    });


    $('form[name="poiPhoto"], form[name="majPoiPhoto"]').on("change", "input", function (event) {
        $(this).closest('form').submit();
    });


    $('form[name="uSupression"]').on("uSupressionCallback", function (event, data) {
        var form = $(this);
        $(data.data).each(function () {
                form.find("[name='deleteUser'][value='" + this.data + "']").closest("li").remove();

            }
        )
    });
    $('form[name="gSupression"]').on("gSupressionCallBack", function (event, data) {
        var form = $(this);
        $(data.data).each(function () {
                form.find("[name='deleteGroup'][value='" + this.data.id + "']").closest("li").remove();

            }
        )
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
            //    map.setCenter(event.latLng);
        });


        var newForm = $("form[name='addPOI']");
    }
    else console.log("Info  : Map non trouvée ");


});

var markerClick;

function createMarker(lat, lng, id) {
    return createMarkerObject({lat: lat, lng: lng}, id)
}
function createMarkerObject(location, id) {
    var mk = new google.maps.Marker({
        position: location,
        map: map,
        title: '',
        animation: google.maps.Animation.DROP

    });
    mk.set("id", id);
    return mk;
}

function JSONGet(url, data, callback) {
    $.ajax({
        url: url,
        type: 'POST',
        data: data
    }).done(callback);
}

$("a[href='#gEdition']").click(function (event, eventData) {

    JSONGet('/A_Groupe/getGroupes', null, function (data) {
        var select = $('form[name="gEdition"] select[name="selectGroupe"]').empty();

        $(data.data).each(function () {
            select.append($('<option>', {
                value: this.id,
                text: this.nom
            }));
        });
        if (eventData) {
            select.val(eventData.data.id);
        }
        select.trigger('change');
    })
});


$("a[href='#gSupression']").click(function () {

    JSONGet('/A_Groupe/getGroupes', null, function (data) {
        var ul = $('form[name="gSupression"] ul.listSupress').empty();

        $(data.data).each(function () {
            ul.append('<li class="list-group-item"><label>' + this.nom + '</label><input name="deleteGroup" value="' + this.id + '" type="checkbox" class="checkbox hidden"> </li>');
        })
    });


});


$("a[href='#uSupression']").click(function () {

    JSONGet('/A_User/getUsers', null, function (data) {
        var ul = $('form[name="uSupression"] ul.listSupress').empty();

        $(data.data).each(function () {
            ul.append('<li class="list-group-item"><label>' + this.username + '</label><input name="deleteUser" value="' + this.id + '" type="checkbox" class="checkbox hidden"> </li>');
        })
    });


});