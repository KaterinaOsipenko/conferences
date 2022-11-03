<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="ct" uri="/WEB-INF/customTag" %>
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
    <title>Event Card</title>
</head>
<body id="page-top" data-bs-spy="scroll" data-bs-target="#mainNav" data-bs-offset="77">
<header class="masthead" style="background-image: url('/img/downloads-bg.jpg');">
    <div class="intro-body">
        <section class="py-4 py-xl-5">
            <div class="container h-100">
                <div class="text-white bg-light border rounded border-0 p-4">
                    <p style="text-align: left">
                        <a style="color: black;" href="${pageContext.request.contextPath}/admin/viewEvents">Go Back</a>
                    </p>
                    <div class="row h-100">
                        <div class="col-md-10 col-xl-8 text-center d-flex d-sm-flex d-md-flex justify-content-center align-items-center mx-auto justify-content-md-start align-items-md-center justify-content-xl-center">
                            <div class="table-responsive">
                                <table class="table">
                                    <tbody>
                                    <tr>
                                        <td>Name</td>
                                        <td>${event.name}</td>
                                    </tr>
                                    <tr>
                                        <td>About</td>
                                        <td>${event.description}</td>
                                    </tr>
                                    <tr>
                                        <td>Address</td>
                                        <td>${event.address.country}, ${event.address}</td>
                                    </tr>
                                    <tr>
                                        <td>Date</td>
                                        <td>${event.date.dayOfMonth}-${event.date.monthValue}-${event.date.year}</td>
                                    </tr>
                                    <tr>
                                        <td>Time</td>
                                        <td>
                                            <ct:localTimeTag time="${event.date}"/>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                                <div class="ftr">
                                    <div class="btn-register">
                                        <a class="btn btn-primary"
                                           href="${pageContext.request.contextPath}/admin/getReports?id=${event.id}"
                                           role="button">See reports</a>
                                        <a class="btn btn-dark"
                                           href="${pageContext.request.contextPath}/admin/editEvent?id=${event.id}"
                                           role="button">Edit event</a>
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
<%@include file="/include/footer.jsp" %>
</body>
</html>