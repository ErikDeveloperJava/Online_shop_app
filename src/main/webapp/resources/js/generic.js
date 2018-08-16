$(document).ready(function () {
    loadCategories();
});
var isLoad = $("#isLoadCatBlog2").attr("content")

function loadCategories() {
    $.ajax({
        type: "POST",
        url: "/categories",
        success: function (data) {
            $.each(data,function (i, category) {
                $("#cat-blog")
                    .append("<a href='/category/" + category.id + "' class='list-group-item list-group-item-action sub'>" + category.name + "</a>");
                if(isLoad){
                    $("#cat-blog2").append("<a href='/category/" + category.id + "' class='btn btn-light btn-sm active'>" + category.name + "</a>")
                }

            })
        },
        error: function (error) {
            $("html").html(error);
        }
    })
}