<%@ page import="java.text.SimpleDateFormat" %>
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
    <title>product/add</title>

</head>
<div>
    !--Header -->
    <header class="navbar navbar-expand navbar-light fixed-top">

        <!-- Toggle Menu -->
        <span class="toggle-menu"><i class="fa fa-bars fa-lg"></i></span>

        <!-- Logo -->
        <a class="navbar-brand" href="/"><img src="/resources/img/logo.svg" alt="Mimity">Online Shop</a>

        <!-- /Search Form -->

        <!-- navbar-nav -->
        <ul class="navbar-nav ml-auto">


            <!-- /Language Dropdown -->

            <!-- Search Toggle -->
            <li class="nav-item d-sm-none">
                <a href="grid.html#" class="nav-link" id="search-toggle"><i class="fa fa-search fa-lg"></i></a>
            </li>
            <!-- /Search Toggle -->

            <!-- Shopping Cart Toggle -->
        </ul>
        <!-- /navbar-nav -->

        <!-- User Dropdown -->
        <div class="dropdown dropdown-user">
            <a class="dropdown-toggle" style="cursor: pointer;" role="button" id="userDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <img src="/resources/images/${user.imgUrl}" width="40px" alt="User">
            </a>
            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
                <div class="dropdown-divider"></div>
                <a class="dropdown-item has-icon" href="/logout"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
            </div>
        </div>
        <!-- /User Dropdown -->

    </header>
    <!-- /Header -->

<div class="container-fluid" id="main-container">
    <div class="row">

        <!-- Sidebar -->
        <div class="col" id="main-sidebar">
            <div class="list-group list-group-flush">
                <a href="/" class="list-group-item list-group-item-action"><i class="fa fa-home fa-lg fa-fw"></i>
                    Home</a>
                <a style="cursor: pointer" class="list-group-item list-group-item-action"><i></i></a>
                <a  class="list-group-item list-group-item-action"><i
                        class="fa fa-list fa-lg fa-fw"></i> Other</a>
                <a href="/admin/category/add" class="list-group-item list-group-item-action sub">Add category</a>
                <a href="/admin/category/attr/1" class="list-group-item list-group-item-action sub">Add category attribute</a>
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
                                   id="login-tab" role="tab" aria-controls="login" aria-selected="true">category add</a>
                            </div>
                        </nav>
                        <div class="tab-content">
                            <div class="tab-pane fade show active" id="register" role="tabpanel" aria-labelledby="register-tab">

                                <div class="card shadow-sm">
                                    <div class="card-body">
                                        <form action="/admin/category/add" method="post" >
                                            <div class="form-row">
                                                <div class="form-group col-sm-6">
                                                    <label for="registerFirstName">Name</label>
                                                    <input type="text" name="name" class="form-control" id="registerFirstName">
                                                    <c:if test="${nameError != null}">
                                                        <span style="color: red">${nameError}</span>
                                                    </c:if>
                                                </div>
                                                <div class="form-group col-12">
                                                    <button type="submit" class="btn btn-success btn-block">Add</button>
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