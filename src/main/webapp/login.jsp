<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="include/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="fonts/Cabin.css">
    <link rel="stylesheet" href="fonts/Lora.css">
    <link rel="stylesheet" href="css/Login-Form-Basic-icons.css">
    <title>Login</title>
</head>
<body id="page-top" data-bs-spy="scroll" data-bs-target="#mainNav" data-bs-offset="77">
<header class="masthead" style="background-image: url('img/downloads-bg.jpg');">
    <div class="intro-body">
        <section class="position-relative py-4 py-xl-5">
            <div class="container">
                <div class="row d-flex justify-content-center">
                    <div class="col-md-6 col-xl-4">
                        <div class="card border-0 mb-5">
                            <div class="card-body text-white text-bg-dark border-0 d-flex flex-column align-items-center"
                                 style="border-style: none;margin-right: -53px;padding-bottom: 37px;box-shadow: 15px 10px 12px 2px var(--bs-gray-800);">
                                <div class="bs-icon-xl bs-icon-circle bs-icon-primary bs-icon my-4">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor"
                                         viewBox="0 0 16 16" class="bi bi-person">
                                        <path d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0zm4 8c0 1-1 1-1 1H3s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C11.516 10.68 10.289 10 8 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10z"></path>
                                    </svg>
                                </div>
                                <c:if test="${sessionScope.ex != null}">
                                    <p class="text-danger fs-6" role="alert">
                                        <c:out value="${ex}"/>
                                    </p>
                                </c:if>
                                <form class="text-center" method="post" action="login">
                                    <div class="mb-3"><input class="form-control" type="email" name="email"
                                                             placeholder="Email" autocomplete="on" required
                                                             inputmode="email"></div>
                                    <div class="mb-3"><input class="form-control" type="password" name="password"
                                                             placeholder="Password" required></div>
                                    <div class="mb-3">
                                        <button class="btn btn-primary d-block w-100" type="submit">Login</button>
                                    </div>
                                </form>
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
