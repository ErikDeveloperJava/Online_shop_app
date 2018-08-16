$(document).ready(function () {

    $("#login-form").on("submit",function (event) {
        var inputTags = [$("#loginUsername"),$("#loginPassword")];
        $.each(inputTags,function (i, input) {
            var name = $(input).attr("name");
            var value = $(input).val();
            isValidData(name,value,event);
        })
    });

    $(".form-control").on("input",function () {
        var name = $(this).attr("name");
        var value = $(this).val();

        isValidData(name,value,null);
    });

    $("#file").on("change",function () {
        var name = $(this).attr("name");
        var value = $(this).val();
        isValidData(name,value,null);
    });

    $("#register-form").on("submit",function () {
        var inputTags = [$("#name"),$("#surname"),$("#username"),$("#password"),$("#file")]
        $.each(inputTags,function (i, input) {
            var name = $(input).attr("name");
            var value = $(input).val();
            isValidData(name,value,event);
        });
    })
});

function isValidData(name, value, event) {
    switch (name){
        case "username":
            if(value == null || value.length < 4 || value.length > 255){
                if(event != null){
                    event.preventDefault();
                }
                $("#usernameError").text("in field username wrong data")
            }else {
                $("#usernameError").text("")
            };
            break;
        case "password":
            if(value == null || value.length < 4 || value.length > 255){
                if(event != null){
                    event.preventDefault();
                }
                $("#passwordError").text("in field password wrong data")
            }else {
                $("#passwordError").text("")
            };
            break;
        case "name":
            if(value == null || value.length < 4 || value.length > 255){
                if(event != null){
                    event.preventDefault();
                }
                $("#nameError").text("in field name wrong data")
            }else {
                $("#nameError").text("")
            };
            break;
        case "surname":
            if(value == null || value.length < 4 || value.length > 255){
                if(event != null){
                    event.preventDefault();
                }
                $("#surnameError").text("in field surname wrong data")
            }else {
                $("#surnameError").text("")
            };
            break;
        case "image":
            if(value == null || value.length < 4 || value.length > 255){
                if(event != null){
                    event.preventDefault();
                }
                $("#imageError").text("in field image wrong data")
            }else {
                $("#imageError").text("")
            };
            break;
    }
}