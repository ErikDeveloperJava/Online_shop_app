$(document).ready(function () {

    $(".user-form").on("submit",function (event) {
        event.preventDefault();
        var userId = $(this).children(".userId").val();
        var parent = $(this).parent().parent();
        $.ajax({
            type: "POST",
            url: "/admin/user/delete",
            data: {userId: userId},
            success :function (data) {
                if(data){
                    parent.remove();
                }
            },
            error: function (error) {
                $("html").html(error);
            }
        })
    })
});