$(document).ready(function () {

    $("#cart-button").on("click", function (event) {
        event.preventDefault();
        var productId = $("#productId").val();
        $.ajax({
            type: "POST",
            url: "/product/cart/add",
            data: {productId: productId},
            success: function (data) {
                if(data){
                    $("#cart-count").text(parseInt($("#cart-count").text()) + 1);
                }
            },
            error: function (error) {
                $("html").html(error);
            }
        });
    });

    $(".cart-form").on("submit",function (event) {
        event.preventDefault();
        var productId = $(this).children(".cart-input").val();
        $.ajax({
            type: "POST",
            url: "/product/cart/add",
            data: {productId: productId},
            success: function () {
            },
            error: function (error) {
                $("html").html(error);
            }
        })
    })
});