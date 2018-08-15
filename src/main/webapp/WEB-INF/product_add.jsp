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

<!--Header -->
    <header class="navbar navbar-expand navbar-light fixed-top">

        <!-- Toggle Menu -->
        <span class="toggle-menu"><i class="fa fa-bars fa-lg"></i></span>

        <!-- Logo -->
        <a class="navbar-brand" href="/"><img src="/resources/img/logo.svg" alt="Mimity">Online Shop</a>

        <!-- Search Form -->
        <form action="<c:url value="/product/search" />" method="post" class="form-inline form-search d-none d-sm-inline">
            <div class="input-group">
                <button class="btn btn-light btn-search-back" type="button"><i class="fa fa-arrow-left"></i></button>
                <input type="text" class="form-control" name="title" placeholder="Search ..." aria-label="Search ...">
                <button class="btn btn-light" type="submit"><i class="fa fa-search"></i></button>
            </div>
        </form>
        <!-- /Search Form -->

        <!-- navbar-nav -->
        <ul class="navbar-nav ml-auto">


            <!-- /Language Dropdown -->

            <!-- Search Toggle -->
            <li class="nav-item d-sm-none">
                <a href="grid.html#" class="nav-link" id="search-toggle"><i class="fa fa-search fa-lg"></i></a>
            </li>
        </ul>
        <!-- /navbar-nav -->
        <li>
            <a href="grid.html#" class="nav-link" data-toggle="modal" data-target="#cartModal">
                balance: $${user.balance}
            </a>
        </li>
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
                <a style="cursor: pointer" class="list-group-item list-group-item-action"><i
                        class="fa fa-th fa-lg fa-fw"></i> Categories</a>
                <c:forEach items="${categories}" var="category" varStatus="cat">
                    <a href="/category/${category.id}" class="list-group-item list-group-item-action sub">${category.name}</a>
                </c:forEach>
                    <a  class="list-group-item list-group-item-action"><i
                            class="fa fa-list fa-lg fa-fw"></i> Other</a>
                    <a href="/product/add/1" class="list-group-item list-group-item-action sub">Add product</a>

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
                                   id="login-tab" role="tab" aria-controls="login" aria-selected="true">product add</a>
                            </div>
                        </nav>
                        <div class="tab-content">
                            <div class="tab-pane fade show active" id="register" role="tabpanel" aria-labelledby="register-tab">
                                <c:choose>
                                    <c:when test="${pageNumber == 1}">
                                        <div class="card shadow-sm">
                                            <div class="card-body">
                                                <form action="/product/add/1" method="post" >
                                                    <div class="form-row">
                                                        <div class="form-group col-sm-6">
                                                            <label for="registerFirstName">Title</label>
                                                            <input type="text" name="title" class="form-control" id="registerFirstName">
                                                            <c:if test="${titleError != null}">
                                                                <span style="color: red">${titleError}</span>
                                                            </c:if>
                                                        </div>
                                                        <div class="form-group col-sm-6">
                                                            <label for="registerLastName">Price</label>
                                                            $<input type="text" name="price" class="form-control" id="registerLastName">
                                                            <c:if test="${priceError != null}">
                                                                <span style="color: red">${priceError}</span>
                                                            </c:if>
                                                        </div>
                                                        <div class="form-group col-sm-6">
                                                            <label for="registerEmail">Description</label>
                                                            <textarea type="text" name="description" class="form-control" id="registerEmail"></textarea>
                                                            <c:if test="${descriptionError != null}">
                                                                <span style="color: red">${descriptionError}</span>
                                                            </c:if>
                                                        </div>
                                                        <div class="form-group col-sm-6">
                                                            <label for="registerPhone">Quantity</label>
                                                            <input type="text" name="quantity" class="form-control" id="registerPhone">
                                                            <c:if test="${quantityError != null}">
                                                                <span style="color: red">${quantityError}</span>
                                                            </c:if>
                                                        </div>
                                                        <div class="form-group col-sm-6">
                                                            <span>Categories</span>
                                                            <c:if test="${categoryError != null}">
                                                                <span style="color: red">${categoryError}</span>
                                                            </c:if>
                                                            <c:forEach items="${categories}" var="category">
                                                                <div class="custom-control custom-checkbox">
                                                                    <input type="checkbox" class="custom-control-input"
                                                                           id="${category.id}" name="categories" value="${category.id}">
                                                                    <label class="custom-control-label" for="${category.id}">${category.name}</label>
                                                                </div>
                                                            </c:forEach>
                                                        </div>
                                                        <div class="form-group col-12">
                                                            <button type="submit" class="btn btn-success btn-block">Add</button>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="card shadow-sm">
                                            <div class="card-body">
                                                <form action="/product/add/2" method="post" enctype="multipart/form-data">
                                                    <c:if test="${attrError != null}">
                                                        <h4 style="color: red">${attrError}</h4>
                                                    </c:if>
                                                    <div class="form-row">
                                                        <c:forEach items="${attributes}" var="attr">
                                                            <div class="form-group col-sm-6">
                                                                <label for="${attr.id}">${attr.name}</label>
                                                                <input type="text" name="${attr.id}" class="form-control" id="${attr.id}">
                                                            </div>
                                                        </c:forEach>
                                                        <br/>
                                                        <div class="form-group col-sm-6">
                                                            <input  type="file" name="image" id="file" class="inputfile" />
                                                            <label for="file">Choose a photo</label>
                                                            <br/>
                                                            <c:if test="${imageError != null}">
                                                                <span style="color: red">${imageError}</span>
                                                            </c:if>
                                                        </div>
                                                        <div class="form-group col-12">
                                                            <button type="submit" class="btn btn-success btn-block">Add
                                                            </button>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
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