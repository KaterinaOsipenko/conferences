<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="include/header.jsp" %>
<%@ taglib prefix="ct" uri="/WEB-INF/customTag" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="fonts/Cabin.css">
    <link rel="stylesheet" href="fonts/Lora.css">
    <link rel="stylesheet" href="css/Login-Form-Basic-icons.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.12.0/css/all.css">
    <link rel="stylesheet" href="fonts/fontawesome5-overrides.min.css">
    <link rel="stylesheet" href="css/Responsive-UI-Card.css">
    <title>Events</title>
</head>
<body id="page-top" data-bs-spy="scroll" data-bs-target="#mainNav" data-bs-offset="77">
<header class="masthead" style="background-image: url('img/downloads-bg.jpg');">
    <div class="intro-body" style="margin-top: 16px;padding-bottom: 0px;margin-bottom: 1px;">
        <div class="ftr" style="display:flex; gap:0.1rem; justify-content: center">
            <div class="btn-register">
                <a class="btn btn-primary"
                   href="${pageContext.request.contextPath}/eventList?sort=date"
                   role="button">Sort by Date</a>
            </div>
            <div class="btn-register">
                <a class="btn btn-primary"
                   href="${pageContext.request.contextPath}/eventList?sort=future"
                   role="button">Show future events</a>
            </div>
            <div class="btn-register">
                <a class="btn btn-primary"
                   href="${pageContext.request.contextPath}/eventList?sort=past"
                   role="button">Show past events</a>
            </div>
            <div class="btn-register">
                <a class="btn btn-primary"
                   href="${pageContext.request.contextPath}/eventList?sort=reports"
                   role="button">Sort by quantity of reports</a>
            </div>
            <div class="btn-register">
                <a class="btn btn-primary"
                   href="${pageContext.request.contextPath}/eventList?sort=users"
                   role="button">Sort by quantity of registered users</a>
            </div>
        </div>
        <div class="container" style="display: flex; align-items: center;">
            <c:if test="${currentPage > 1}">
                <div>

                    <c:choose>
                        <c:when test="${not empty sort}">
                            <a aria-label="Previous"
                               href="${pageContext.request.contextPath}/eventList?page=${currentPage - 1}&sort=${sort}">
                                <span style="font-size: 50px; padding-right: 10px;">«</span>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a aria-label="Previous"
                               href="${pageContext.request.contextPath}/eventList?page=${currentPage - 1}">
                                <span style="font-size: 50px; padding-right: 10px;">«</span>
                            </a>
                        </c:otherwise>
                    </c:choose>

                </div>
            </c:if>
            <div class="row" style="width: 100%;">
                <c:forEach var="event" items="${eventList}">
                    <div class="col-md-4">
                        <div class="blog-card blog-card-blog">
                            <div class="blog-table" style="padding: 10px 10px;">
                                <c:if test="${event.reports > 0}">
                                    <c:choose>
                                        <c:when test="${event.reports == 1}">
                                            <p class="blog-category blog-text-success"
                                               style="display: inline"> ${event.reports} report | </p>
                                        </c:when>
                                        <c:otherwise>
                                            <p class="blog-category blog-text-success"
                                               style="display: inline"> ${event.reports} reports | </p>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                                <h6 style="display: inline" class="blog-category blog-text-success"><i
                                        class="far fa-newspaper"></i> ${event.address.country}</h6>
                                <c:if test="${event.registerUsers > 0}">
                                    <c:choose>
                                        <c:when test="${event.registerUsers == 1}">
                                            <p class="blog-category blog-text-success"
                                               style="display: inline"> | ${event.registerUsers} user</p>
                                        </c:when>
                                        <c:otherwise>
                                            <p class="blog-category blog-text-success"
                                               style="display: inline"> | ${event.registerUsers} users</p>
                                        </c:otherwise>
                                    </c:choose>

                                </c:if>
                                <h4 class="blog-card-caption">
                                    <a href="${pageContext.request.contextPath}/eventCard?id=${event.id}">${event.name}</a>
                                </h4>
                                <p class="blog-card-description">${event.address}</p>
                                <p class="blog-card-description">
                                    Date: ${event.date.dayOfMonth}-${event.date.monthValue}-${event.date.year}
                                </p>
                                <p class="blog-card-description">
                                    <ct:localTimeTag time="${event.date}"/>
                                </p>
                                <div class="ftr">
                                    <div class="btn-register">
                                        <c:choose>
                                            <c:when test="${event.date gt now}">
                                                <a class="btn btn-primary"
                                                   href="${pageContext.request.contextPath}/chooseEvent?id=${event.id}"
                                                   role="button">Choose event</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a class="btn btn-primary"
                                                   href="${pageContext.request.contextPath}/eventCard?id=${event.id}"
                                                   role="button">See event</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <c:if test="${maxPage > currentPage}">
                <div>
                    <c:choose>
                        <c:when test="${not empty sort}">
                            <a aria-label="Next"
                               href="${pageContext.request.contextPath}/eventList?page=${currentPage + 1}&sort=${sort}">
                                <span style="font-size: 50px; padding-right: 10px;">»</span>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a aria-label="Next"
                               href="${pageContext.request.contextPath}/eventList?page=${currentPage + 1}">
                                <span style="font-size: 50px; padding-right: 10px;">»</span>
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:if>
        </div>
        <nav class="font-monospace d-lg-flex justify-content-center justify-content-lg-center align-items-lg-center"
             style="padding-bottom: 0;margin-bottom: -119px;margin-top: 93px;">
            <ul class="pagination">
                <c:if test="${currentPage > 1}">
                    <li class="page-item">
                        <c:choose>
                            <c:when test="${not empty sort}">
                                <a class="page-link" style="background: transparent; border: none;"
                                   aria-label="Previous"
                                   href="${pageContext.request.contextPath}/eventList?page=${currentPage - 1}&sort=${sort}">
                                    <span aria-hidden="true">«</span>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a class="page-link" style="background: transparent; border: none;"
                                   aria-label="Previous"
                                   href="${pageContext.request.contextPath}/eventList?page=${currentPage - 1}">
                                    <span aria-hidden="true">«</span>
                                </a>
                            </c:otherwise>
                        </c:choose>

                    </li>
                </c:if>
                <c:forEach var="i" begin="1" end="${maxPage}">
                    <c:if test="${currentPage == i}">
                        <c:choose>
                            <c:when test="${i == 1}">
                                <li class="page-item"><a class="page-link"
                                                         style="background: transparent; border: none;"
                                                         href="${pageContext.request.contextPath}/eventList?page=${i}">${i}</a>
                                </li>
                                <c:if test="${currentPage != maxPage}">
                                    <li class="page-item"><a class="page-link"
                                                             style="background: transparent; border: none;"
                                                             href="${pageContext.request.contextPath}/eventList?page=${i + 1}">${i + 1}</a>
                                    </li>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item"><a class="page-link"
                                                         style="background: transparent; border: none;"
                                                         href="${pageContext.request.contextPath}/eventList?page=${i - 1}">${i - 1}</a>
                                </li>
                                <li class="page-item"><a class="page-link"
                                                         style="background: transparent; border: none;"
                                                         href="${pageContext.request.contextPath}/eventList?page=${i}">${i}</a>
                                </li>
                                <c:if test="${currentPage != maxPage}">
                                    <li class="page-item"><a class="page-link"
                                                             style="background: transparent; border: none;"
                                                             href="${pageContext.request.contextPath}/eventList?page=${i + 1}">${i + 1}</a>
                                    </li>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </c:forEach>
                <c:if test="${maxPage > currentPage}">
                    <li class="page-item fs-2 text-start">
                        <c:choose>
                            <c:when test="${not empty sort}">
                                <a class="page-link" style="background: transparent; border: none;" aria-label="Next"
                                   href="${pageContext.request.contextPath}/eventList?page=${currentPage + 1}&sort=${sort}">
                                    <span aria-hidden="true">»</span>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a class="page-link" style="background: transparent; border: none;" aria-label="Next"
                                   href="${pageContext.request.contextPath}/eventList?page=${currentPage + 1}">
                                    <span aria-hidden="true">»</span>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</header>
<%@include file="include/footer.jsp" %>
</body>
</html>