$(document).ready(function () {

    $(".cart-form").on("click", function (event) {
        event.preventDefault();
        var productId = $(this).children(".productId").val();
        $.ajax({
            type: "POST",
            url: "/product/cart/add",
            data: {productId: productId},
            success: function (data) {
                if(data.id > 0){
                    $("#cart-count").text(parseInt($("#cart-count").text()) + 1);
                    $("#cartModalLabel").text("you have " + (parseInt($("#cartModalLabel").text().split(" ")[2]) + 1) + " items in your bag");
                    var productDiv =
                        "<div class='media'>" +
                        "<a href='/product/one/" + data.id + "'><img src='/resources/images/" + data.imgUrl + "'" +
                        " width='50' height='50'" +
                        " alt='NEW Microsoft Surface Go'></a>" +
                        "<div class='media-body'>" +
                        "<a href='/product/one/" + data.id + "'" +
                        " title='NEW Microsoft Surface Go'>" + data.title +"</a>" +
                        "<div class='input-spinner input-spinner-sm'>" +
                        "<div class='btn-group-vertical'>" +
                        "<button type='button' class='btn btn-light'></button>" +
                        "<button type='button' class='btn btn-light'></button>" +
                        "</div>" +
                        "</div>" +
                        "<span class='price' id='product-price'>$" + data.price + "</span>" +
                        "<form class='cart-delete-form' action='/product/cart/delete' method='post'>" +
                        "<input type='hidden' class='productId' name='productId' value='" + data.id + "'>" +
                        "<button type='submit' class='close' aria-label='Close'><i class='fa fa-trash-o'></i></button>" +
                        "</form>" +
                        "</div>" +
                        "</div>";
                    $("#cart-blog").append(productDiv);
                    var subotal = $("#subotal").text().split("$")[1];
                    if(subotal == null){
                        $("#subotal").text("$" + parseInt(data.price));
                    }else {
                        $("#subotal").text("$" + (parseInt(subotal) + parseInt(data.price)))
                    }
                }
            },
            error: function (error) {
                $("html").html(error);
            }
        });
    });

    $(document).on("submit",".cart-delete-form",function (event) {
        event.preventDefault();
        var productId = $(this).children(".productId").val();
        var parent = $(this).parent().parent();
        var price = $(this).parent().children("#product-price").text().split("$")[1];
        $.ajax({
            type: "POST",
            url: "/product/cart/delete",
            data: {productId : productId},
            success :function (data) {
                if(data){
                    parent.remove();
                    $("#cart-count").text(parseInt($("#cart-count").text()) - 1);
                    $("#cartModalLabel").text("you have " + (parseInt($("#cartModalLabel").text().split(" ")[2]) - 1) + " items in your bag");
                    $("#subotal").text("$" + (parseInt($("#subotal").text().split("$")[1]) - parseInt(price)));

                }
            },
            error: function (error) {
                $("html").html(error);
            }
        })
    });

    $(".delete-order-form").on("submit",function (event) {
        event.preventDefault();
        var productId = $(this).children(".productId").val();
        var parent = $(this).parent().parent();
        var orderCount = $("#order-count").text().split(" ")[2];
        orderCount = orderCount.split("(")[1];
        orderCount = orderCount.split(")")[0];
        $.ajax({
            type: "POST",
            url: "/product/order/delete",
            data: {productId : productId},
            success: function (data) {
                if(data){
                    parent.remove();
                    $("#order-count").text("My orders (" + (parseInt(orderCount) - 1) + ")");
                    $("#order-title").text("you ordered " + (parseInt($("#order-title").text().split(" ")[2]) - 1) + " products");
                }
            },
            error : function (error) {
                $("html").html(error);
            }
        })
    })
});