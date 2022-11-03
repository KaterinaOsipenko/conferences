<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="adminHeader.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/fonts/Cabin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/fonts/Lora.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Login-Form-Basic-icons.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.12.0/css/all.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/fonts/fontawesome5-overrides.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Responsive-UI-Card.css">
    <title>Event</title>
</head>
<body id="page-top" data-bs-spy="scroll" data-bs-target="#mainNav" data-bs-offset="77">
<header class="masthead" style="background-image: url('/img/downloads-bg.jpg');">
    <div class="intro-body">
        <section class="py-4 py-xl-5">
            <div class="container">
                <div class="text-white bg-dark border rounded border-0 p-4 p-md-5">
                    <div style="display: flex; padding-bottom: 2rem; gap: 2rem; justify-content: center;">
                        <div>
                            <a role="button" href="${pageContext.request.contextPath}/admin/viewEvents?sort=past">View
                                past
                                events</a>
                        </div>
                        <div>
                            <a role="button" href="${pageContext.request.contextPath}/admin/viewEvents?sort=users">Sort
                                events
                                by Registered Users</a>
                        </div>
                        <div>
                            <a role="button" href="${pageContext.request.contextPath}/admin/viewEvents?sort=reports">Sort
                                events
                                by count of reports</a>
                        </div>
                        <div>
                            <a role="button" href="${pageContext.request.contextPath}/admin/viewEvents?sort=all">View
                                all
                                events</a>
                        </div>
                        <div>
                            <a role="button" href="${pageContext.request.contextPath}/admin/viewEvents?sort=future">View
                                future
                                events</a>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table class="table" style="color: white;">
                            <thead>
                            <tr>
                                <th>name</th>
                                <th>address</th>
                                <th>date</th>
                                <th>time</th>
                                <th>count reports</th>
                                <th>count registered users</th>
                                <c:if test="${not empty past}">
                                    <th>count guests</th>
                                </c:if>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="event" items="${eventList}">
                                <tr>
                                    <td><a role="button"
                                           href="${pageContext.request.contextPath}/admin/cardEvent?id=${event.id}">${event.name}</a>
                                    </td>
                                    <td>${event.address}</td>
                                    <td>${event.date.dayOfMonth}-${event.date.monthValue}-${event.date.year}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${event.date.minute == 0}">
                                                <c:out value="${event.date.hour}:${event.date.minute}0"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${event.date.hour}:${event.date.minute}"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${event.reports}</td>
                                    <td>${event.registerUsers}</td>
                                    <c:if test="${not empty past}">
                                        <td>${event.registerUsers}</td>
                                    </c:if>
                                    <c:if test="${empty past}">
                                        <td>
                                            <a role="button" class="btn btn-primary btn-sm me-2"
                                               href="${pageContext.request.contextPath}/admin/editEvent?id=${event.id}">Edit</a>
                                        </td>
                                    </c:if>
                                    <td>
                                        <a role="button" class="btn btn-light btn-sm me-2"
                                           href="${pageContext.request.contextPath}/admin/getReports?id=${event.id}">See
                                            Reports</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <nav class="font-monospace d-lg-flex justify-content-center justify-content-lg-center align-items-lg-center"
                 style="padding-bottom: 0;margin-bottom: -119px;margin-top: 93px;">
                <ul class="pagination">
                    <c:if test="${currentPage > 1}">
                        <li class="page-item">
                            <a class="page-link" style="background: transparent; border: none;"
                               aria-label="Previous"
                               href="${pageContext.request.contextPath}/admin/viewEvents?page=${currentPage - 1}">
                                <span aria-hidden="true">«</span>
                            </a>

                        </li>
                    </c:if>
                    <c:forEach var="i" begin="1" end="${maxPage}">
                        <c:if test="${currentPage == i}">
                            <c:choose>
                                <c:when test="${i == 1}">
                                    <li class="page-item"><a class="page-link"
                                                             style="background: transparent; border: none;"
                                                             href="${pageContext.request.contextPath}/admin/viewEvents?page=${i}">${i}</a>
                                    </li>
                                    <c:if test="${currentPage != maxPage}">
                                        <li class="page-item"><a class="page-link"
                                                                 style="background: transparent; border: none;"
                                                                 href="${pageContext.request.contextPath}/admin/viewEvents?page=${i + 1}">${i + 1}</a>
                                        </li>
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item"><a class="page-link"
                                                             style="background: transparent; border: none;"
                                                             href="${pageContext.request.contextPath}/admin/viewEvents?page=${i - 1}">${i - 1}</a>
                                    </li>
                                    <li class="page-item"><a class="page-link"
                                                             style="background: transparent; border: none;"
                                                             href="${pageContext.request.contextPath}/admin/viewEvents?page=${i}">${i}</a>
                                    </li>
                                    <c:if test="${currentPage != maxPage}">
                                        <li class="page-item"><a class="page-link"
                                                                 style="background: transparent; border: none;"
                                                                 href="${pageContext.request.contextPath}/admin/viewEvents?page=${i + 1}">${i + 1}</a>
                                        </li>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </c:forEach>
                    <c:if test="${maxPage > currentPage}">
                        <li class="page-item fs-2 text-start">
                            <a class="page-link" style="background: transparent; border: none;"
                               aria-label="Next"
                               href="${pageContext.request.contextPath}/admin/viewEvents?page=${currentPage + 1}">
                                <span aria-hidden="true">»</span>
                            </a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </section>
    </div>
</header>

<%@include file="/include/footer.jsp" %>
</body>
</html>
