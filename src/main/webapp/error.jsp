<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="include/header.jsp" %>
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
    <title>Choose Event</title>
</head>
<body id="page-top" data-bs-spy="scroll" data-bs-target="#mainNav" data-bs-offset="77">
<header class="masthead" style="background-image: url('img/downloads-bg.jpg');">
    <div class="intro-body">
        <section class="py-4 py-xl-5">
            <div class="container">
                <div class="text-center p-4 p-lg-5">
                    <h1 class="fw-bold mb-4">${ex}</h1>
                    <c:choose>
                        <c:when test="${empty id}">
                            <a class="btn btn-primary fs-5 border-1 shadow-lg py-2 px-4" role="button"
                               href="${pageContext.request.contextPath}/${address}">Go back</a>
                        </c:when>
                        <c:otherwise>
                            <a class="btn btn-primary fs-5 border-1 shadow-lg py-2 px-4" role="button"
                               href="${pageContext.request.contextPath}/${address}?id=${id}">Go back</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </section>
    </div>
</header>

<%@include file="include/footer.jsp" %>
</body>
</html>