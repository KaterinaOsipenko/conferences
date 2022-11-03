<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="ct" uri="/WEB-INF/customTag" %>
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
    <title>Event</title>
</head>
<body id="page-top" data-bs-spy="scroll" data-bs-target="#mainNav" data-bs-offset="77">
<header class="masthead" style="background-image: url('/img/downloads-bg.jpg');">
    <div class="intro-body">
        <section class="py-4 py-xl-5">
            <div class="container h-100">
                <div class="text-white bg-primary border rounded border-0 p-4">
                    <p style="text-align: left">
                        <a style="color: black;" href="${pageContext.request.contextPath}/eventList">Go Back</a>
                    </p>
                    <div class="row h-100">
                        <div class="col-md-10 col-xl-8 text-center d-flex d-sm-flex d-md-flex justify-content-center align-items-center mx-auto justify-content-md-start align-items-md-center justify-content-xl-center">
                            <div>
                                <h1 class="text-uppercase fw-bold text-white mb-3 mt-0">${event.name}</h1>
                                <p class="mb-4">${event.description}</p>
                                <p class="mb-4">${event.address.country}, ${event.address}</p>
                                <p class="mb-4">
                                    Date: ${event.date.dayOfMonth}-${event.date.monthValue}-${event.date.year}
                                </p>
                                <p class="mb-4">
                                    <ct:localTimeTag time="${event.date}"/>
                                </p>
                                <div class="ftr">
                                    <div class="btn-register">
                                        <a class="btn btn-dark"
                                           href="${pageContext.request.contextPath}/reports?id=${event.id}"
                                           role="button">See reports</a>
                                        <a class="btn btn-light"
                                           href="${pageContext.request.contextPath}/chooseEvent?id=${event.id}"
                                           role="button">Choose event</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</header>
<%@include file="include/footer.jsp" %>
</body>
</html>
