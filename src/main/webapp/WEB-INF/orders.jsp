<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Erik
  Date: 09.08.2018
  Time: 22:32
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
    <link rel="stylesheet" href="/resources/plugins/swiper/swiper.min.css">
    <link rel="stylesheet" href="/resources/dist/css/style.css">

    <title>Order</title>
</head>
<body>

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
        <!-- /Search Toggle -->

        <!-- Shopping Cart Toggle -->
        <li>
            <a href="grid.html#" class="nav-link" data-toggle="modal" data-target="#cartModal">
                balance: $${user.balance}
            </a>
        </li>
        <li class="nav-item dropdown ml-1 ml-sm-3">
            <a href="grid.html#" class="nav-link" data-toggle="modal" data-target="#cartModal">
                <i class="fa fa-shopping-cart fa-lg"></i>
                <span class="badge badge-pink badge-count">${cartCount}</span>
            </a>
        </li>
    </ul>
    <!-- /navbar-nav -->

    <!-- User Dropdown -->
    <div class="dropdown dropdown-user">
        <a class="dropdown-toggle" style="cursor: pointer;" role="button" id="userDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            <img src="/resources/images/${user.imgUrl}" width="40px" alt="User">
        </a>
        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
            <a class="dropdown-item has-icon" href="/user/orders"><i class="fa fa-shopping-bag fa-fw"></i> My Orders (${ordersCount})</a>
            <%--<a class="dropdown-item has-icon" href="account-profile.html"><i class="fa fa-cog fa-fw"></i> Account Setting</a>--%>
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

            <h3 class="title mb-3">you ordered ${ordersCount} products </h3>

            <!-- Shopping Cart Table -->
            <table class="table table-cart">
                <tbody>
                <c:forEach items="${orders}" var="order">
                    <tr>
                        <td>
                            <form action="/product/order/delete" method="post">
                                <input type="hidden" name="productId" value="${order.product.id}">
                                <button type="submit" class="btn btn-sm btn-outline-warning rounded-circle" title="Remove"><i class="fa fa-close"></i></button>
                            </form>
                        </td>
                        <td>
                            <a href="/product/one/${order.product.id}"><img src="/resources/images/${order.product.imgUrl}" width="50" height="50" alt="NEW Microsoft Surface Go"></a>
                            <button class="btn btn-sm btn-outline-warning rounded"></button>
                        </td>
                        <td>
                            <h6><a href="/product/one/${order.product.id}" class="text-body">${order.product.title}</a></h6>
                            <h6 class="text-muted">$${order.product.price}</h6>
                            <c:forEach items="${order.product.values}" var="attr">
                                <span class="badge badge-success font-weight-light">${attr.categoryAttribute.name}: ${attr.value}</span>
                            </c:forEach>

                        </td>
                        <td>
                            <div class="input-spinner">
                                <input type="number" class="form-control" value="${order.count}">
                            </div>
                            <span class="price">$${order.product.price * order.count}</span>
                        </td>
                    </tr>

                </c:forEach>

                </tbody>
            </table>
            <!-- /Shopping Cart Table -->

            <!-- Recently viewed-->

            <!-- /Recently viewed-->

            <!-- Footer -->
            <!-- /Footer -->

        </div>
    </div>
</div>

<!-- Modal Cart -->
<div class="modal fade modal-cart" id="cartModal" tabindex="-1" role="dialog" aria-labelledby="cartModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="cartModalLabel">You have ${cartCount} items in your bag</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <c:forEach items="${cartProducts}" var="product">
                    <div class="media">
                        <a href="/product/one/${product.id}"><img src="/resources/images/${product.imgUrl}" width="50" height="50" alt="NEW Microsoft Surface Go"></a>
                        <div class="media-body">
                            <a href="/product/one/${product.id}" title="NEW Microsoft Surface Go">${product.title}</a>
                            <div class="input-spinner input-spinner-sm">
                                <div class="btn-group-vertical">
                                    <button type="button" class="btn btn-light"></button>
                                    <button type="button" class="btn btn-light"></button>
                                </div>
                            </div>
                            <span class="price">$${product.price}</span>
                            <form action="/product/cart/delete" method="post">
                                <input type="hidden" name="productId" value="${product.id}">
                                <button type="submit" class="close" aria-label="Close"><i class="fa fa-trash-o"></i></button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="modal-footer">
                <div class="box-total">
                    <h4>Subotal: <span class="price">$${sum}</span></h4>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="/resources/plugins/jquery/jquery.min.js"></script>
<script src="/resources/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="/resources/plugins/perfect-scrollbar/js/perfect-scrollbar.min.js"></script>
<script src="/resources/plugins/swiper/swiper.min.js"></script>
<script src="/resources/dist/js/script.js"></script>
</body>
</html>