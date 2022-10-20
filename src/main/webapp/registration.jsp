<%--
  Created by IntelliJ IDEA.
  User: Дом
  Date: 24.09.2022
  Time: 15:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="header.jsp" %>
<html>
<head>
    <meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="fonts/font-awesome.min.css">
    <link rel="stylesheet" href="fonts/Cabin.css">
    <link rel="stylesheet" href="fonts/Lora.css">
    <link rel="stylesheet" href="css/Login-Form-Basic-icons.css">
    <title>Registration</title>
</head>
<body id="page-top" data-bs-spy="scroll" data-bs-target="#mainNav" data-bs-offset="77">
<header class="masthead" style="background-image: url('img/downloads-bg.jpg');">
    <div class="intro-body">
        <div class="container text-white"
             style="position: absolute;left: 0;right: 0;top: 50%;transform: translateY(-50%);-ms-transform: translateY(-50%);-moz-transform: translateY(-50%);-webkit-transform: translateY(-50%);-o-transform: translateY(-50%);padding-left: 112px;padding-right: 79px;padding-bottom: 74px;padding-top: 94px;">
            <div class="row text-white d-flex d-xl-flex justify-content-center justify-content-xl-center"
                 style="margin-left: -38px;margin-right: 61px;">
                <div class="col-sm-12 col-lg-10 col-xl-9 col-xxl-7 text-bg-dark"
                     style="border-radius: 5px;background: var(--bs-border-color-translucent);border-style: none;border-top-color: var(--bs-border-color-translucent);--bs-body-bg: var(--bs-pink);box-shadow: 15px 10px 20px 4px var(--bs-gray-800);">
                    <div class="text-white text-bg-dark border-0 border-light p-5" style="border-style: none;">
                        <div class="text-center">
                            <h4 class="text-white mb-4">Create an Account!</h4>
                        </div>
                        <c:if test="${sessionScope.ex != null}">
                            <p class="text-danger fs-6" role="alert">
                                <c:out value="${ex}"/>
                            </p>
                        </c:if>
                        <form class="text-white text-bg-dark user" action="registration" method="post"
                              enctype="application/x-www-form-urlencoded">
                            <div class="mb-3"><input class="form-control form-control-user" type="text"
                                                     placeholder="First Name" required="" name="firstname"
                                                     autocomplete="on" pattern="^[a-zA-Z].{1,45}$" minlength="1"
                                                     maxlength="45" style="margin: 17px 0px 0px;"></div>
                            <input class="form-control form-control-user" type="text" placeholder="Last Name"
                                   required="" name="lastname" autocomplete="on" minlength="1" maxlength="45"
                                   pattern="^[a-zA-Z].{1,45}$" style="margin: 17px 0px 0px;padding: 6px 12px;">
                            <div class="mb-3"><input class="form-control form-control-user" type="email" id="email"
                                                     placeholder="Email Address" required="" style="margin-top: 17px;"
                                                     name="email" inputmode="email" autocomplete="on"></div>
                            <div class="mb-3"><input class="form-control form-control-user" type="password"
                                                     id="password" placeholder="Password" required=""
                                                     style="margin: 17px 0px 0px;padding: 6px 12px;" name="password"
                                                     minlength="5" maxlength="12"
                                                     pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{5,12}$"></div>
                            <div class="row mb-3">
                                <p id="emailErrorMsg" class="text-danger" style="display:none;">Paragraph</p>
                                <p id="passwordErrorMsg" class="text-danger" style="display:none;">Paragraph</p>
                            </div>
                            <div class="row"
                                 style="height: 37px;margin: 0px -12px 16px;padding-bottom: 4px;margin-bottom: 63px;margin-left: 0px;margin-right: 2px;">
                                <div class="col" style="padding-left: 0px;padding-right: 0px;"><select
                                        class="form-select" name="role" required="" value="Role"
                                        style="margin-top: -14px;">
                                    <option value="SPEAKER">Speaker</option>
                                    <option value="13">Moderator</option>
                                </select></div>
                            </div>
                            <button class="btn btn-primary d-block btn-user w-100" id="submitBtn" type="submit"
                                    style="padding-top: 10px;margin-bottom: 3px;margin-top: -1px;">Register Account
                            </button>
                            <hr>
                        </form>
                        <div class="text-center"></div>
                        <div class="text-center"><a class="small" href="login.jsp">Already have an account? Login!</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>
<%--<p>Registration</p>--%>
<%--<c:out value="${ex}"/>--%>
<%--<form action="registration" method="post" accept-charset="UTF-8">--%>
<%--    <label for="firstname">Name: </label>--%>
<%--    <input type="text" name="firstname" id="firstname" pattern="^[a-zA-Z].{1,45}$" minlength="1" maxlength="45"--%>
<%--    />--%>
<%--    <label for="lastname">lastname</label>--%>
<%--    <input type="text" name="lastname" id="lastname" pattern="^[a-zA-Z].{1,45}$" minlength="1" maxlength="45" required/>--%>
<%--    <label for="email">email</label>--%>
<%--    <input type="email" name="email" id="email" required/>--%>
<%--    <label for="password">Password</label>--%>
<%--    <input type="password" name="password" id="password" pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{5,12}$"--%>
<%--           minlength="5" maxlength="12" required/>--%>
<%--    <label for="role">role</label>--%>
<%--    <select name="role" id="role" required>--%>
<%--        <option value="MODERATOR">Moderator</option>--%>
<%--        <option value="SPEAKER">Speaker</option>--%>
<%--    </select>--%>
<%--    <button type="submit" value="registration">Registration</button>--%>
<%--</form>--%>
</body>
<%@include file="footer.jsp" %>
</html>
