<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="fonts/font-awesome.min.css">
    <link rel="stylesheet" href="fonts/Cabin.css">
    <link rel="stylesheet" href="fonts/Lora.css">
    <link rel="stylesheet" href="css/Login-Form-Basic-icons.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.12.0/css/all.css">
    <link rel="stylesheet" href="fonts/fontawesome5-overrides.min.css">
    <link rel="stylesheet" href="css/Responsive-UI-Card.css">
    <title>Login</title>
</head>
<body id="page-top" data-bs-spy="scroll" data-bs-target="#mainNav" data-bs-offset="77">
<header class="masthead" style="background-image: url('img/downloads-bg.jpg');">
    <div class="intro-body" style="margin-top: 16px;padding-bottom: 0px;margin-bottom: 1px;">
        <div class="container" style="display: flex; align-items: center;">
            <c:if test="${currentPage > 1}">
                <div>
                    <a aria-label="Previous"
                       href="${pageContext.request.contextPath}/eventListServlet?page=${currentPage - 1}">
                        <span style="font-size: 50px; padding-right: 10px;">«</span>
                    </a>
                </div>
            </c:if>
            <div class="row" style="width: 100%;">
                <c:forEach var="event" items="${eventList}">
                    <c:set var="id-event" value="${event.id}" scope="request"/>
                    <div class="col-md-4">
                        <div class="blog-card blog-card-blog">
                            <div class="blog-table">
                                <h6 class="blog-category blog-text-success"><i
                                        class="far fa-newspaper"></i> ${event.address.country}</h6>
                                <h4 class="blog-card-caption">
                                    <a href="#">${event.name}</a>
                                </h4>
                                <p class="blog-card-description">${event.address}</p>
                                <p class="blog-card-description">
                                    Date: ${event.date.dayOfMonth}-${event.date.monthValue}-${event.date.year}
                                </p>
                                <p class="blog-card-description">
                                    <c:choose>
                                        <c:when test="${event.date.minute} == 0">
                                            <c:out value="Time: ${event.date.hour}:${event.date.minute}0"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="Time: ${event.date.hour}:${event.date.minute}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </p>
                                <div class="ftr">
                                    <div class="btn-register">
                                        <a class="btn btn-primary" role="button" href="registerForEvent.jsp">Choose
                                            event</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <c:if test="${maxPage > currentPage}">
                <div>
                    <a aria-label="Next"
                       href="${pageContext.request.contextPath}/eventListServlet?page=${currentPage + 1}">
                        <span style="font-size: 50px; padding-right: 10px;">»</span>
                    </a>
                </div>
            </c:if>
        </div>
        <nav class="font-monospace d-lg-flex justify-content-center justify-content-lg-center align-items-lg-center"
             style="padding-bottom: 0;margin-bottom: -119px;margin-top: 93px;">
            <ul class="pagination">
                <c:if test="${currentPage > 1}">
                    <li class="page-item"><a class="page-link" style="background: transparent; border: none;"
                                             class="page-link"
                                             aria-label="Previous"
                                             href="${pageContext.request.contextPath}/eventListServlet?page=${currentPage - 1}"><span
                            aria-hidden="true">«</span></a></li>
                </c:if>
                <c:forEach var="i" begin="1" end="${maxPage}">
                    <c:if test="${currentPage == i}">
                        <c:choose>
                            <c:when test="${i == 1}">
                                <li class="page-item"><a class="page-link"
                                                         style="background: transparent; border: none;"
                                                         href="${pageContext.request.contextPath}/eventListServlet?page=${i}">${i}</a>
                                </li>
                                <c:if test="${currentPage != maxPage}">
                                    <li class="page-item"><a class="page-link"
                                                             style="background: transparent; border: none;"
                                                             href="${pageContext.request.contextPath}/eventListServlet?page=${i + 1}">${i + 1}</a>
                                    </li>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item"><a class="page-link"
                                                         style="background: transparent; border: none;"
                                                         href="${pageContext.request.contextPath}/eventListServlet?page=${i - 1}">${i - 1}</a>
                                </li>
                                <li class="page-item"><a class="page-link"
                                                         style="background: transparent; border: none;"
                                                         href="${pageContext.request.contextPath}/eventListServlet?page=${i}">${i}</a>
                                </li>
                                <c:if test="${currentPage != maxPage}">
                                    <li class="page-item"><a class="page-link"
                                                             style="background: transparent; border: none;"
                                                             href="${pageContext.request.contextPath}/eventListServlet?page=${i + 1}">${i + 1}</a>
                                    </li>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </c:forEach>
                <c:if test="${maxPage > currentPage}">
                    <li class="page-item fs-2 text-start"><a class="page-link"
                                                             style="background: transparent; border: none;"
                                                             aria-label="Next"
                                                             href="${pageContext.request.contextPath}/eventListServlet?page=${currentPage + 1}"><span
                            aria-hidden="true">»</span></a></li>
                </c:if>
            </ul>
        </nav>
    </div>
</header>
<%@include file="footer.jsp" %>
</body>
</html>