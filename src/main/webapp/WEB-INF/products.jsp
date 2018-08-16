<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row no-gutters gutters-2">
    <c:forEach items="${products}" var="product">
        <div class="col-6 col-md-3 mb-2">
            <div class="card card-product">
                <div class="ribbon"><span class="bg-info text-white"></span></div>
                <a href="/product/one/${product.id}"><img src="/resources/images/${product.imgUrl}" alt="NEW Microsoft Surface Go" class="card-img-top"></a>
                <div class="card-body">
                    <span class="price">$${product.price}</span>
                    <a href="/product/one/${product.id}" class="card-title h6">${product.title}</a>
                    <c:if test="${user != null && product.user.id != user.id} ">
                        <form action="/product/cart/add" method="post">
                            <input type="hidden" name="productId" value="${product.id}">
                            <div class="d-flex justify-content-between align-items-center">
                                <button type="submit" class="btn btn-outline-info btn-sm btn-block">Add to cart</button>
                            </div>
                        </form>
                    </c:if>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
<!-- /Grid -->

<!-- Pagination -->
<nav aria-label="Page navigation Shop Grid">
    <ul class="pagination justify-content-center">
        <c:if test="${pageNumber == 0}">
            <li class="page-item disabled"><a class="page-link" style="cursor: pointer" tabindex="-1">Previous</a></li>
        </c:if>
        <c:if test="${pageNumber > 0}">
            <li class="page-item"><a class="page-link" href="/?page=${pageNumber -1}" tabindex="-1">Previous</a></li>
        </c:if>
        <c:forEach begin="0" end="${length - 1}" varStatus="status">
            <c:if test="${status.index == pageNumber}">
                <li class="page-item active"><a class="page-link" style="cursor: pointer">${pageNumber + 1}</a></li>
            </c:if>
            <c:if test="${status.index != pageNumber}">
                <li class="page-item"><a class="page-link" href="/?page=${status.index}">${status.index+1}</a></li>
            </c:if>
        </c:forEach>
        <c:if test="${pageNumber == (length-1)}">
            <li class="page-item  disabled">
                <a class="page-link" style="cursor: pointer">Next</a>
            </li>
        </c:if>
        <c:if test="${pageNumber < (length-1)}">
            <li class="page-item">
                <a class="page-link" href="/?page=${pageNumber + 1}">Next</a>
            </li>
        </c:if>
    </ul>
</nav>

