$(document).ready(function () {
    $("#image-upload").on("submit",function (event) {
        event.preventDefault();
        var index = $("#image-blog").attr("title");
        $.ajax({
            type: "POST",
            url: "/product/image/upload",
            data: new FormData(this),
            processData: false,
            contentType: false,
            success: function (data) {
                if(data.id > 0){
                    var imageDiv = "<a href='/resources/images/" + data.image + "'>" +
                        "<img src='/resources/images/" + data.image + "'" +
                        " data-large-src='/resources/images/" + data.image + "' alt='Product'" +
                        " data-index='" + index + "'>" +
                        "</a>";
                    $("#image-blog").append(imageDiv);
                    $("#image-blog").attr("title","" + (parseInt(index) + 1));
                    $("#file").val("");
                }
            },
            error: function (error) {
                $("html").html(error);
            }
        })
    })
})