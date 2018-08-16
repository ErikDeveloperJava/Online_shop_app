$(document).ready(function () {
    loadProducts();
    setInterval(function () {
        loadProducts();
    },2000);
});
function loadProducts() {
    $.ajax({
        type: "POST",
        url: "/products",
        success: function (data) {
            $("#product-blog").html(data);
        },
        error: function (error) {
            $("html").html(error);
        }
    })
}