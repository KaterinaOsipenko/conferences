<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="adminHeader.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="../fonts/Cabin.css">
    <link rel="stylesheet" href="../fonts/Lora.css">
    <link rel="stylesheet" href="../css/Login-Form-Basic-icons.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.12.0/css/all.css">
    <link rel="stylesheet" href="../fonts/fontawesome5-overrides.min.css">
    <link rel="stylesheet" href="../css/Responsive-UI-Card.css">
    <title>Admin Home</title>
</head>
<body id="page-top" data-bs-spy="scroll" data-bs-target="#mainNav" data-bs-offset="77">
<header class="masthead" style="background-image: url('../img/downloads-bg.jpg');">
    <div class="intro-body">
        <section class="py-4 py-xl-5">
            <div class="container">
                <div class="text-center p-4 p-lg-5">
                    <p class="fw-bold text-primary mb-2">Hello, ${user.firstName}</p>
                    <a class="btn btn-primary fs-5 me-2 py-2 px-4" role="button" type="button" href="/admin/profile">Visit
                        Profile</a>
                    <a class="btn btn-light fs-5 py-2 px-4" role="button" type="button"
                       href="${pageContext.request.contextPath}/admin/viewEvents">Get all
                        events</a>
                    <a class="btn btn-primary fs-5 py-2 px-4" role="button" type="button"
                       href="${pageContext.request.contextPath}/admin/createEvent">Create
                        Event</a>
                    <a class="btn btn-light fs-5 py-2 px-4" role="button" type="button"
                       href="${pageContext.request.contextPath}/admin/addresses">Get all addresses</a>
                    <a class="btn btn-primary fs-5 py-2 px-4" role="button" type="button"
                       href="${pageContext.request.contextPath}/admin/topics">Get all topics</a>
                </div>
            </div>
        </section>
    </div>
</header>
<%@include file="/include/footer.jsp" %>
</body>
</html>
