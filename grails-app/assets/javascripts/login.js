
//= require jquery-2.2.0.min
//= require bootstrap


function JSONGet(url, data, callback) {
    $.ajax({
        url: url,
        type: 'POST',
        data: data
    }).done(callback);
}

$(document).ready(function () {

    JSONGet("/A_User/getPublicUsers",null,function (data) {
        var ul= $("#kusers");

        $(data.data).each(function () {

            ul.append(
                $("<li>",{
                    class:"list-group-item",
                    text:this.data[0] +" ( "+ this.data[1]+" ) ",
                    value:this.data[0]
                })
            )
        })
    })

    $("#kusers").on("click","li",function () {
       val = $(this).attr("value");
        $("#username").val(val).focus();
        $("#password").val(val.toLowerCase());
        $("#kusers li").removeClass("alert alert-success")  ;
        $(this).addClass("alert alert-success");

    })

});
