<%@ page import="java.util.List" %>
<%@ page import="onlineShop.model.Category" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Fira+Sans">
    <link rel="stylesheet" href="/resources/plugins/_mod/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="/resources/plugins/perfect-scrollbar/css/perfect-scrollbar.min.css">
    <link rel="stylesheet" href="/resources/plugins/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="/resources/dist/css/style.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <title>Login / Register </title>
    <script>

        $(document).ready(function () {
            $("#login-tab").on("click",function () {
                $("#register").attr("class","tab-pane fade");
                $("#login").attr("class","tab-pane fade show active");;
            })
            $("#register-tab").on("click",function () {
                $("#login").attr("class","tab-pane fade");
                $("#register").attr("class","tab-pane fade show active");
            })
        })
    </script>
</head>
<div>

<!--Header -->
<header class="navbar navbar-expand navbar-light fixed-top">

    <!-- Toggle Menu -->
    <span class="toggle-menu"><i class="fa fa-bars fa-lg"></i></span>

    <!-- Logo -->
    <a class="navbar-brand" href="/">
        <img src="/resources/img/logo.svg" alt="Mimity">Online Shop
    </a>

    <!-- Search Form -->
    <form action="<c:url value="/product/search" />" method="post" class="form-inline form-search d-none d-sm-inline">
        <div class="input-group">
            <button class="btn btn-light btn-search-back" type="button"><i class="fa fa-arrow-left"></i></button>
            <input type="text" class="form-control" name="title" placeholder="Search ..." aria-label="Search ...">
            <button class="btn btn-light" type="submit"><i class="fa fa-search"></i></button>
        </div>
    </form>

</header>
<!-- /Header -->

<div class="container-fluid" id="main-container">
    <div class="row">

        <!-- Sidebar -->
        <div class="col" id="main-sidebar">
            <div class="list-group list-group-flush">
                <a href="/" class="list-group-item list-group-item-action"><i class="fa fa-home fa-lg fa-fw"></i>
                    Home</a>
                <a style="cursor: pointer" class="list-group-item list-group-item-action"><i
                        class="fa fa-th fa-lg fa-fw"></i> Categories</a>
                <c:forEach items="${categories}" var="category" varStatus="cat">
                    <a href="/category/${category.id}" class="list-group-item list-group-item-action sub">${category.name}</a>
                </c:forEach>
                    <a  class="list-group-item list-group-item-action"><i
                            class="fa fa-list fa-lg fa-fw"></i> Other</a>
                    <a href="/login-register" class="list-group-item list-group-item-action sub">Login/Register</a>

                    <div class="collapse" id="pages">

                        <a class="list-group-item list-group-item-action sub"></a>
                        <a class="list-group-item list-group-item-action sub"></a>
                        <a class="list-group-item list-group-item-action sub"></a>
                        <a class="list-group-item list-group-item-action sub"></a>
                    </div>

                    <a class="list-group-item list-group-item-action sub toggle"
                       data-toggle="collapse" aria-expanded="false"></a>

                    <a class="list-group-item list-group-item-action"></a>
                    <a class="list-group-item list-group-item-action"></a>
                </div>
                <div class="small p-3">Copyright © 2018 O N L I N E _ S H O P</div>
            </div>
            <!-- /Sidebar -->

            <div class="col" id="main-content">

                <div class="row justify-content-center">
                    <div class="col-xl-6 col-md-8 col-12">
                        <nav class="d-flex justify-content-center mb-4">
                            <div class="btn-group nav" role="tablist">
                                <a class="btn btn-outline-info active" data-toggle="tab" style="cursor: pointer"
                                   id="login-tab" role="tab" aria-controls="login" aria-selected="true">Login</a>
                                <a class="btn btn-outline-info" data-toggle="tab" style="cursor: pointer"
                                   id="register-tab" role="tab" aria-controls="register"
                                   aria-selected="false">Register</a>
                            </div>
                        </nav>
                        <div class="tab-content">
                            <div class="tab-pane fade show active" id="login" role="tabpanel"
                                 aria-labelledby="login-tab">
                                <div class="card shadow-sm card-login-form">
                                    <div class="card-body">
                                        <div class="row">
                                            <div class="col-sm-6">
                                                <form action="/login" method="post">
                                                    <div class="form-group text-center">Login to your account</div>
                                                    <c:if test="${loginError != null}">
                                                        <span style="color: red">${loginError}</span>
                                                    </c:if>
                                                    <div class="form-group">
                                                        <input type="text" class="form-control" name="username" id="loginUsername"
                                                               placeholder="Username">
                                                    </div>
                                                    <div class="form-group">
                                                        <input type="password" class="form-control" name="password" id="loginPassword"
                                                               placeholder="Password">
                                                    </div>
                                                    <%--
                                                    <div class="form-group">
                                                        <div class="custom-control custom-checkbox">
                                                            <input type="checkbox" class="custom-control-input"
                                                                   id="remember">
                                                            <label class="custom-control-label" for="remember">Remember
                                                                me</label>
                                                        </div>
                                                    </div>--%>
                                                    <button type="submit" class="btn btn-success btn-block">LOGIN
                                                    </button>
                                                </form>
                                            </div>
                                            <div class="col-sm-6 mt-2 mt-sm-0">
                                                <div class="form-group text-center">OR</div>
                                                <div class="btn-group w-100 mb-1">
                                                    <button class="btn btn-primary active"><i
                                                            class="fa fa-facebook fa-fw"></i></button>
                                                    <button class="btn btn-primary btn-block">Login with Facebook
                                                    </button>
                                                </div>
                                                <div class="btn-group w-100 mb-1">
                                                    <button class="btn btn-danger active"><i
                                                            class="fa fa-google-plus fa-fw"></i></button>
                                                    <button class="btn btn-danger btn-block">Login with Google+</button>
                                                </div>
                                                <div class="btn-group w-100">
                                                    <button class="btn btn-info active"><i
                                                            class="fa fa-twitter fa-fw"></i></button>
                                                    <button class="btn btn-info btn-block">Login with Twitter</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="tab-pane fade" id="register" role="tabpanel" aria-labelledby="register-tab">
                                <div class="card shadow-sm">
                                    <div class="card-body">
                                        <form action="/register" method="post" enctype="multipart/form-data">
                                            <div class="form-row">
                                                <div class="form-group col-sm-6">
                                                    <label for="registerFirstName">Name</label>
                                                    <input type="text" name="name" class="form-control" id="registerFirstName">
                                                    <c:if test="${nameError != null}">
                                                        <span style="color: red">${nameError}</span>
                                                    </c:if>
                                                </div>
                                                <div class="form-group col-sm-6">
                                                    <label for="registerLastName">Surname</label>
                                                    <input type="text" name="surname" class="form-control" id="registerLastName">
                                                    <c:if test="${surnameError != null}">
                                                        <span style="color: red">${surnameError}</span>
                                                    </c:if>
                                                </div>
                                                <div class="form-group col-sm-6">
                                                    <label for="registerEmail">Username</label>
                                                    <input type="text" name="username" class="form-control" id="registerEmail">
                                                    <c:if test="${usernameError != null}">
                                                        <span style="color: red">${usernameError}</span>
                                                    </c:if>
                                                </div>
                                                <div class="form-group col-sm-6">
                                                    <label for="registerPhone">Password</label>
                                                    <input type="password" name="password" class="form-control" id="registerPhone">
                                                    <c:if test="${passwordError != null}">
                                                        <span style="color: red">${passwordError}</span>
                                                    </c:if>
                                                </div>
                                                <div class="form-group col-sm-6">
                                                    <input  type="file" name="image" id="file" class="inputfile" />
                                                    <label for="file">Choose a photo</label>
                                                    <br/>
                                                    <c:if test="${imageError != null}">
                                                        <span style="color: red">${imageError}</span>
                                                    </c:if>
                                                </div>
                                                <div class="form-group col-12">
                                                    <button type="submit" class="btn btn-success btn-block">REGISTER
                                                    </button>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
    <script src="/resources/plugins/jquery/jquery.min.js"></script>
    <script src="/resources/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="/resources/plugins/perfect-scrollbar/js/perfect-scrollbar.min.js"></script>
    <script src="/resources/dist/js/script.js"></script>
</body>
</html>