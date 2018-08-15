<%--
  Created by IntelliJ IDEA.
  User: Erik
  Date: 10.08.2018
  Time: 10:44
  To change this template use File | Settings | File Templates.
--%>
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

    <title>404</title>
</head>
<body>

<!--Header -->
<header class="navbar navbar-expand navbar-light fixed-top">

    <!-- Toggle Menu -->
    <span class="toggle-menu"><i class="fa fa-bars fa-lg"></i></span>
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

            <!-- 404 Content -->
            <div class="text-center">
                <div class="display-2"><i class="fa fa-compass"></i></div>
                <h1>ERROR 404 PAGE NOT FOUND</h1>
                <h4>The page you were looking for doesn't exist.</h4>
                <div class="btn-group btn-group-sm mt-3 mb-5" role="group">
                    <a class="btn btn-outline-info" href="/" role="button"><i class="fa fa-arrow-left"></i> Go Back</a>
                    <a class="btn btn-info" href="/" role="button"><i class="fa fa-home"></i> Home</a>
                </div>
                <p>Think this is an error? Please <a href="contact.html"><u>let us know.</u></a></p>
            </div>
            <!-- /404 Content -->

<script src="/resources/plugins/jquery/jquery.min.js"></script>
<script src="/resources/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="/resources/plugins/perfect-scrollbar/js/perfect-scrollbar.min.js"></script>
<script src="/resources/dist/js/script.js"></script>
</body>
</html>
