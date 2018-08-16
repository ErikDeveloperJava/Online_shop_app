<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");%>
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
    <link rel="stylesheet" href="/resources/plugins/photoswipe/photoswipe.min.css">
    <link rel="stylesheet" href="/resources/plugins/photoswipe/photoswipe-default-skin/default-skin.min.css">
    <link rel="stylesheet" href="/resources/dist/css/style.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <title>Product</title>
</head>
<body>

<!--Header -->
<header class="navbar navbar-expand navbar-light fixed-top">

    <!-- Toggle Menu -->
    <span class="toggle-menu"><i class="fa fa-bars fa-lg"></i></span>

    <!-- Logo -->
    <a class="navbar-brand" href="index.html"><img src="/resources/img/logo.svg" alt="Mimity">Online Shop</a>

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
        <c:if test="${user != null}">
            <li>
                <a href="grid.html#" class="nav-link" data-toggle="modal" data-target="#cartModal">
                    balance: $${user.balance}
                </a>
            </li>
            <li class="nav-item dropdown ml-1 ml-sm-3">
                <a href="grid.html#" class="nav-link" data-toggle="modal" data-target="#cartModal">
                    <i class="fa fa-shopping-cart fa-lg"></i>
                    <span id="cart-count" class="badge badge-pink badge-count">${cartCount}</span>
                </a>
            </li>
        </c:if>
    </ul>
    <!-- /navbar-nav -->

    <!-- User Dropdown -->
    <c:if test="${user != null}">
        <div class="dropdown dropdown-user">
            <a class="dropdown-toggle" style="cursor: pointer;" role="button" id="userDropdown" data-toggle="dropdown"
               aria-haspopup="true" aria-expanded="false">
                <img src="/resources/images/${user.imgUrl}" width="40px" alt="User">
            </a>
            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
                <a class="dropdown-item has-icon" href="/user/orders"><i class="fa fa-shopping-bag fa-fw"></i> My Orders
                    (${ordersCount})</a>
                    <%--<a class="dropdown-item has-icon" href="account-profile.html"><i class="fa fa-cog fa-fw"></i> Account Setting</a>--%>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item has-icon" href="/logout"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
            </div>
        </div>
    </c:if>
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
                <div id="cat-blog">
                </div>
                <a class="list-group-item list-group-item-action"><i
                        class="fa fa-list fa-lg fa-fw"></i> Other</a>
                <c:if test="${user == null}">
                    <a href="/login-register" class="list-group-item list-group-item-action sub">Login/Register</a>
                </c:if>
                <c:if test="${user != null}">
                    <a href="/product/add/1" class="list-group-item list-group-item-action sub">Add product</a>
                </c:if>
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

            <!-- Breadcrumb -->
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="/" class="text-info">Home</a></li>
                    <c:forEach items="${productCategories}" var="category">
                        <li class="breadcrumb-item"><a href="/category/${category.id}"
                                                       class="text-info">${category.name} </a></li>
                    </c:forEach>
                </ol>
            </nav>
            <!-- /Breadcrumb -->

            <div class="row">
                <div class="col-md-7">
                    <div class="img-detail-wrapper">
                        <img src="/resources/images/${product.imgUrl}" class="img-fluid px-5" id="img-detail"
                             alt="Responsive image" data-index="0">
                        <div id="image-blog" title="<%=((List)request.getAttribute("images")).size()%>" class="img-detail-list">
                            <a href="/resources/images/${product.imgUrl}" class="active"><img
                                    src="/resources/images/${product.imgUrl}"
                                    data-large-src="/resources/images/${product.imgUrl}" alt="Product"
                                    data-index="0"></a>
                            <c:forEach items="${images}" var="img" varStatus="status">
                                <c:if test="${img.image != product.imgUrl}">
                                    <a href="/resources/images/${img.image}">
                                        <img src="/resources/images/${img.image}"
                                             data-large-src="/resources/images/${img.image}" alt="Product"
                                             data-index="${status.index + 1}">
                                    </a>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <div class="col-md-5">
                    <div class="detail-header">
                        <h3>${product.title}</h3>
                        <a class="dropdown-toggle" style="cursor: pointer;" role="button" data-toggle="dropdown"
                           aria-haspopup="true" aria-expanded="false">
                            <img src="/resources/images/${product.user.imgUrl}" width="50px" height="50px"
                                 alt="User"> ${product.user.name} ${product.user.surname}
                        </a>
                        <h6><span class="rating" data-value="4.5"></span> <a class="ml-1"
                                                                             style="cursor: pointer">${reviewsCount}
                            reviews</a></h6>
                        <h3 class="price">$ ${product.price}</h3>
                    </div>
                    <div class="form-group">
                        <label for="quantity">Quantity</label>
                        <div class="input-spinner">
                            <input type="number" class="form-control" id="quantity" value="1" min="1"
                                   max="${product.quantity}">
                            <div class="btn-group-vertical">
                                <button type="button" class="btn btn-light"><i class="fa fa-chevron-up"></i></button>
                                <button type="button" class="btn btn-light"><i class="fa fa-chevron-down"></i></button>
                            </div>
                        </div>
                    </div>
                    <c:forEach items="${attributes}" var="attr">
                        <div class="form-group">
                            <label class="d-block">${attr.categoryAttribute.name}</label>
                            <div class="btn-group btn-group-sm btn-group-toggle" data-toggle="buttons">
                                <label class="btn btn-outline-success active">
                                    <input type="radio" name="ram" checked>${attr.value}
                                </label>
                            </div>
                        </div>
                    </c:forEach>
                    <c:if test="${user != null && user.id != product.user.id}">
                        <form action="/product/cart/add" class="cart-form"  method="post">
                            <input type="hidden" class="productId" value="${product.id}">
                            <div class="form-group">
                                <button id="cart-button" type="submit" class="btn btn-info btn-block">ADD TO CART</button>
                            </div>
                        </form>
                        <form action="/product/order/add"  method="post">
                            <input type="hidden" name="productId" value="${product.id}">
                            <div class="form-group">
                                <button type="submit" class="btn btn-info btn-block">MAKE A ORDER</button>
                            </div>
                        </form>
                    </c:if>
                    <c:if test="${user != null && user.id == product.user.id}">
                        <form action="/product/delete" method="post">
                            <input type="hidden" name="productId" value="${product.id}">
                            <div class="form-group">
                                <button type="submit" class="btn btn-info btn-block">DELETE PRODUCT</button>
                            </div>
                        </form>
                        <form id="image-upload" action="/product/image/upload" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="productId" value="${product.id}">
                            <div class="form-group">
                                <input type="file" name="image" id="file" class="inputfile"/>
                                <label for="file">Choose a photo</label>
                            </div>
                            <div class="form-group">
                                <button type="submit" class="btn btn-info btn-block">UPLOAD</button>
                            </div>
                        </form>
                    </c:if>
                </div>
            </div>
            <hr>
            <div class="row mt-4">
                <div class="col">
                    <h3>Description</h3>
                    <p>${product.description}</p>
                    <hr>

                    <!-- Similar Items -->
                    <div class="content-slider">
                        <div class="swiper-container" id="popular-slider">
                            <div class="swiper-wrapper">

                                <div class="swiper-slide">
                                    <div class="row no-gutters gutters-2">
                                        <c:forEach items="${likeProducts}" var="product" varStatus="status">
                                            <c:if test="${status.index < 4}">
                                                <div class="col-6 col-md-3 mb-2">
                                                    <div class="card card-product">
                                                        <div class="ribbon"><span class="bg-pink text-white"></span>
                                                        </div>
                                                        <a href="/product/one/${product.id}"><img
                                                                src="/resources/images/${product.imgUrl}"
                                                                alt="Nikon D7200 DX-format DSLR Body (Black)"
                                                                class="card-img-top"></a>
                                                        <div class="card-body">
                                                            <span class="price">$${product.price}</span>
                                                            <a href="/product/one/${product.id}"
                                                               class="card-title h6">${product.title}</a>
                                                            <c:if test="${user == null || user.id != product.user.id}">
                                                                <form class="cart-form" action="/product/cart/add" method="post">
                                                                    <input type="hidden" class="productId"
                                                                           value="${product.id}">
                                                                    <div class="d-flex justify-content-between align-items-center">
                                                                        <button type="submit"
                                                                                class="btn btn-outline-info btn-sm">Add
                                                                            to cart
                                                                        </button>
                                                                    </div>
                                                                </form>
                                                            </c:if>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </div>

                                <div class="swiper-slide">
                                    <div class="row no-gutters gutters-2">
                                        <c:forEach items="${likeProducts}" var="product" varStatus="status">
                                            <c:if test="${status.index >= 4}">
                                                <div class="col-6 col-md-3 mb-2">
                                                    <div class="card card-product">
                                                        <div class="ribbon"><span class="bg-pink text-white"></span>
                                                        </div>
                                                        <a href="/product/one/${product.id}"><img
                                                                src="/resources/images/${product.imgUrl}"
                                                                alt="Nikon D7200 DX-format DSLR Body (Black)"
                                                                class="card-img-top"></a>
                                                        <div class="card-body">
                                                            <span class="price">$${product.price}</span>
                                                            <a href="/product/one/${product.id}"
                                                               class="card-title h6">${product.title}</a>
                                                            <c:if test="${user == null || user.id != product.user.id}">
                                                                <form action="/product/cart/add" class="cart-form" method="post">
                                                                    <input type="hidden" class="productId"
                                                                           value="${product.id}">
                                                                    <div class="d-flex justify-content-between align-items-center">
                                                                        <button type="submit"
                                                                                class="btn btn-outline-info btn-sm">Add
                                                                            to cart
                                                                        </button>
                                                                    </div>
                                                                </form>
                                                            </c:if>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <a href="detail.html#" role="button" class="carousel-control-prev" id="popular-slider-prev"><i
                                class="fa fa-angle-left fa-lg"></i></a>
                        <a href="detail.html#" role="button" class="carousel-control-next" id="popular-slider-next"><i
                                class="fa fa-angle-right fa-lg"></i></a>
                    </div>
                    <!-- /Similar Items -->

                    <hr>
                    <h3 id="reviews">Reviews</h3>
                    <div class="media align-items-center mb-3">
                        <div class="media-body ml-2">
                            <div class="rating" data-value="4.5"></div>
                            <div>(${reviewsCount} reviews)</div>
                        </div>
                    </div>
                    <!--reviews-->
                    <div id="review-blog">
                        <c:forEach items="${reviewsList}" var="reviews">
                            <div class="media">
                                <img src="/resources/images/${reviews.user.imgUrl}" width="50" height="50" alt="John Thor"
                                     class="rounded-circle">
                                <div class="media-body ml-3">
                                    <h5 class="mb-0">${reviews.user.name} ${reviews.user.surname}</h5>
                                    <span class="rating text-secondary" data-value="${reviews.rating}"></span>
                                    <small class="ml-2">${dateFormat.format(reviews.sendDate)}</small>
                                    <p>${reviews.reviewText}</p>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <c:if test="${user == null || user.id != product.user.id}">
                        <div class="text-center">
                            <button type="button" class="btn btn-warning" data-toggle="modal"
                                    data-target="#reviewFormModal">Write a review
                            </button>
                        </div>
                    </c:if>
                </div>
            </div>

            <!-- Footer -->
            <div class="navbar navbar-expand navbar-light navbar-footer">
                <ul class="navbar-nav ml-auto">
                </ul>
            </div>
            <!-- /Footer -->

        </div>
    </div>
</div>

<!-- Modal Cart -->
<c:if test="${user != null}">
    <div class="modal fade modal-cart" id="cartModal" tabindex="-1" role="dialog" aria-labelledby="cartModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="cartModalLabel">You have ${cartCount} items in your bag</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div id="cart-blog" class="modal-body">
                    <c:forEach items="${cartProducts}" var="product">
                        <div class="media">
                            <a href="/product/one/${product.id}"><img src="/resources/images/${product.imgUrl}"
                                                                      width="50" height="50"
                                                                      alt="NEW Microsoft Surface Go"></a>
                            <div class="media-body">
                                <a href="/product/one/${product.id}"
                                   title="NEW Microsoft Surface Go">${product.title}</a>
                                <div class="input-spinner input-spinner-sm">
                                    <div class="btn-group-vertical">
                                        <button type="button" class="btn btn-light"></button>
                                        <button type="button" class="btn btn-light"></button>
                                    </div>
                                </div>
                                <span class="price" id="product-price">$${product.price}</span>
                                <form class="cart-delete-form" action="/product/cart/delete" method="post">
                                    <input type="hidden" class="productId" name="productId" value="${product.id}">
                                    <button type="submit" class="close" aria-label="Close"><i class="fa fa-trash-o"></i></button>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="modal-footer">
                    <div class="box-total">
                        <h4>Subotal: <span class="price" id="subotal">$${sum}</span></h4>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:if>

<!-- Review Form Modal -->
<c:if test="${user == null || user.id != product.user.id}">
    <div class="modal fade" id="reviewFormModal" tabindex="-1" role="dialog" aria-labelledby="reviewFormModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="reviewFormModalLabel">Write your review</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <form id="review-form" action="/review/add" method="post">
                    <input type="hidden" name="productId" value="${product.id}">
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="inputDesc">Review</label>
                            <textarea class="form-control" name="text" id="inputDesc" rows="3"
                                      placeholder="Your review"></textarea>
                        </div>
                        <div class="form-group">
                            <label>Rating</label>
                            <div id="star-rating" class="text-warning h5"></div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-primary">Send</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

</c:if>

<!-- Photoswipe container-->

<div class="pswp" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="pswp__bg"></div>
    <div class="pswp__scroll-wrap">
        <div class="pswp__container">
            <div class="pswp__item"></div>
            <div class="pswp__item"></div>
            <div class="pswp__item"></div>
        </div>
        <div class="pswp__ui pswp__ui--hidden">
            <div class="pswp__top-bar">
                <div class="pswp__counter"></div>
                <button class="pswp__button pswp__button--close" title="Close (Esc)"></button>
                <button class="pswp__button pswp__button--share" title="Share"></button>
                <button class="pswp__button pswp__button--fs" title="Toggle fullscreen"></button>
                <button class="pswp__button pswp__button--zoom" title="Zoom in/out"></button>
                <div class="pswp__preloader">
                    <div class="pswp__preloader__icn">
                        <div class="pswp__preloader__cut">
                            <div class="pswp__preloader__donut"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="pswp__share-modal pswp__share-modal--hidden pswp__single-tap">
                <div class="pswp__share-tooltip"></div>
            </div>
            <button class="pswp__button pswp__button--arrow--left" title="Previous (arrow left)"></button>
            <button class="pswp__button pswp__button--arrow--right" title="Next (arrow right)"></button>
            <div class="pswp__caption">
                <div class="pswp__caption__center"></div>
            </div>
        </div>
    </div>
</div>

<script src="/resources/plugins/jquery/jquery.min.js"></script>
<script src="/resources/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="/resources/plugins/perfect-scrollbar/js/perfect-scrollbar.min.js"></script>
<script src="/resources/plugins/swiper/swiper.min.js"></script>
<script src="/resources/plugins/raty-fa/jquery.raty-fa.min.js"></script>
<script src="/resources/plugins/photoswipe/photoswipe.min.js"></script>
<script src="/resources/plugins/photoswipe/photoswipe-ui-default.min.js"></script>
<script src="/resources/dist/js/script.js"></script>
<script src="/resources/js/generic.js"></script>
<c:if test="${user != null}">
    <script src="/resources/js/product.js"></script>
</c:if>
<c:if test="${user != null && user.id == product.user.id}">
    <script src="/resources/js/upload-image.js"></script>
</c:if>
</body>
</html>