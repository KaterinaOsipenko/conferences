<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="header.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Conference</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="fonts/font-awesome.min.css">
    <link rel="stylesheet" href="fonts/Cabin.css">
    <link rel="stylesheet" href="css/Login-Form-Basic-icons.css">
    <link rel="stylesheet" href="css/Responsive-UI-Card.css">
    <link rel="stylesheet" href="fonts/Lora.css">
</head>
<body id="page-top" data-bs-spy="scroll" data-bs-target="#mainNav" data-bs-offset="77">
<header class="masthead" style="background-image:url('img/intro-bg.jpg');">
    <div class="intro-body">
        <div class="container">
            <div class="row">
                <div class="col-lg-8 mx-auto">
                    <h1 class="brand-heading">Conference</h1>
                    <p class="intro-text">Choose one for your free time</p>
                </div>
            </div>
        </div>
        <a class="btn btn-primary" role="button" href="${pageContext.request.contextPath}/eventListServlet">Choose
            event</a>
    </div>
</header>
</body>
<%@include file="footer.jsp" %>
</html>
