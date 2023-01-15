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
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous">
    </script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/js/bootstrap.min.js"
            integrity="sha384-oesi62hOLfzrys4LxRF63OJCXdXDipiYWBnvTl9Y9/TRlw5xlKIEHpNyvvDShgf/"
            crossorigin="anonymous">
    </script>
    <title>Registration Speaker</title>
</head>
<body id="page-top" data-bs-spy="scroll" data-bs-target="#mainNav" data-bs-offset="77">
<header class="masthead" style="background-image: url('/img/downloads-bg.jpg');">
    <div class="intro-body">
        <div class="container text-white"
             style="position: absolute;left: 0;right: 0;top: 50%;transform: translateY(-50%);-ms-transform: translateY(-50%);-moz-transform: translateY(-50%);-webkit-transform: translateY(-50%);-o-transform: translateY(-50%);padding-left: 112px;padding-right: 79px;padding-bottom: 74px;padding-top: 94px;">
            <div class="row text-white d-flex d-xl-flex justify-content-center justify-content-xl-center"
                 style="margin-left: -38px;margin-right: 61px;">
                <div class="col-sm-12 col-lg-10 col-xl-9 col-xxl-7 text-bg-dark"
                     style="border-radius: 5px;background: var(--bs-border-color-translucent);border-style: none;border-top-color: var(--bs-border-color-translucent);--bs-body-bg: var(--bs-pink);box-shadow: 15px 10px 20px 4px var(--bs-gray-800);">
                    <div class="text-white text-bg-dark border-0 border-light p-5" style="border-style: none;">
                        <div class="text-center">
                            <h4 class="text-white mb-4">Create a Speaker Account!</h4>
                        </div>
                        <div class="text-danger">${ex}</div>
                        <form class="text-white text-bg-dark user"
                              action="${pageContext.request.contextPath}/admin/registration" method="post"
                              enctype="application/x-www-form-urlencoded">
                            <div class="mb-3" style="display: flex;align-items: self-end;">
                                <input class="form-control" type="text" id="firstname"
                                       placeholder="First Name" required="" name="firstname"
                                       autocomplete="on" pattern="^[a-zA-Z].{1,45}$" minlength="1"
                                       maxlength="45"
                                       style="margin: 17px 0px 0px;transform: translate(26px);"/>
                                <button onclick="copyPast('firstname')"
                                        style="display: inline;width: 10rem;"
                                        class="btn btn-light border rounded-pill border-1" type="button">
                                    Copy
                                </button>
                            </div>
                            <div class="mb-3" style="display: flex;align-items: self-end;">
                                <input class="form-control form-control-user" type="text" placeholder="Last Name"
                                       required="" id="lastname" name="lastname" autocomplete="on" minlength="1"
                                       maxlength="45"
                                       pattern="^[a-zA-Z].{1,45}$"
                                       style="margin: 17px 0px 0px;transform: translate(26px);">
                                <button onclick="copyPast('lastname')"
                                        style="display: inline;width: 10rem;"
                                        class="btn btn-light border rounded-pill border-1" type="button">
                                    Copy
                                </button>
                            </div>
                            <div class="mb-3" style="display: flex;align-items: self-end;">
                                <input class="form-control form-control-user" type="email" id="email"
                                       placeholder="Email Address" required=""
                                       style="margin: 17px 0px 0px;transform: translate(26px);"
                                       name="email" inputmode="email" autocomplete="on">
                                <button onclick="copyPast('email')"
                                        style="display: inline;width: 10rem;"
                                        class="btn btn-light border rounded-pill border-1" type="button">
                                    Copy
                                </button>
                            </div>
                            <div class="mb-3" style="display: flex"><input class="form-control form-control-user"
                                                                           type="password"
                                                                           id="password" placeholder="Password"
                                                                           required=""
                                                                           style="margin: 17px 0px 0px; width: 94%; transform: translate(26px);"
                                                                           name="password"
                                                                           minlength="5" maxlength="12"
                                                                           pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{5,12}$">
                            </div>
                            <div class="row mb-3">
                                <p id="emailErrorMsg" class="text-danger" style="display:none;">Paragraph</p>
                                <p id="passwordErrorMsg" class="text-danger" style="display:none;">Paragraph</p>
                            </div>
                            <button class="btn btn-primary d-block btn-user w-100" id="submitBtn" type="submit"
                                    style="padding-top: 10px;margin-bottom: 3px;margin-top: -1px;">Register Account
                            </button>
                            <hr>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </div>
</header>
<%@include file="/include/footer.jsp" %>
<script src="${pageContext.request.contextPath}/js/copyPast.js"></script>
</body>
</html>
