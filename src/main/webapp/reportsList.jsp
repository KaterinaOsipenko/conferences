<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/jquery.tablesorter/2.31.2/css/theme.bootstrap_4.min.css">
    <link rel="stylesheet" href="css/Responsive-UI-Card.css">
    <title>Event</title>
</head>
<body id="page-top" data-bs-spy="scroll" data-bs-target="#mainNav" data-bs-offset="77">
<header class="masthead" style="background-image: url('/img/downloads-bg.jpg');">
    <div class="intro-body">
        <section class="py-4 py-xl-5">
            <div class="container">
                <div class="text-white bg-dark border rounded border-0 p-4 p-md-5">
                    <div class="table-responsive">
                        <table class="table" style="color: white;">
                            <thead>
                            <tr>
                                <th>Topic</th>
                                <th>Speaker</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="report" items="${reports}">
                                <tr>
                                    <td>${report.topic.name}</td>
                                    <td>${report.speaker.firstName} ${report.speaker.lastName}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="my-3">
                        <a class="btn btn-primary btn-lg me-2" role="button"
                           href="${pageContext.request.contextPath}/eventCard?id=${id}">Go
                            to
                            Event</a>
                    </div>
                </div>
            </div>
        </section>
    </div>
</header>
</body>
<%@include file="include/footer.jsp" %>
</html>