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
    <title>Create Event</title>
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
                            <h4 class="text-white mb-4">Create an Event</h4>
                        </div>
                        <form class="text-white text-bg-dark user"
                              action="${pageContext.request.contextPath}/admin/createEvent" method="post"
                              enctype="application/x-www-form-urlencoded">
                            <div style="display:flex; align-items: center; justify-content: center; gap: 2rem;">
                                <div>
                                    <input class="form-control form-control-user" type="text"
                                           placeholder="Name" required="" name="name"
                                           autocomplete="on" pattern="^[a-zA-Z].{1,45}$" minlength="1"
                                           maxlength="45" style="margin: 17px 0px 0px;">
                                    <input class="form-control form-control-user" type="text"
                                           placeholder="About" required="" name="about"
                                           autocomplete="on" pattern="^[a-zA-Z].{1,45}$" minlength="1"
                                           maxlength="45" style="margin: 17px 0px 0px;">
                                    <input class="form-control form-control-user" type="date"
                                           id="date" style="margin: 17px 0px 0px;padding: 6px 12px;" name="date"
                                           required>
                                    <input class="form-control form-control-user" type="time"
                                           id="time" style="margin: 17px 0px 0px;padding: 6px 12px;" name="time"
                                           required>
                                </div>
                                <div>
                                    <input class="form-control form-control-user" type="text" placeholder="Country"
                                           required="" name="country" Country="on" minlength="1" maxlength="45"
                                           pattern="^[a-zA-Z].{1,45}$" style="margin: 17px 0px 0px;padding: 6px 12px;">
                                    <input class="form-control form-control-user" type="text" id="city"
                                           pattern="^[a-zA-Z].{1,45}$"
                                           placeholder="City" required="" style="margin-top: 17px;" minlength="1"
                                           maxlength="45"
                                           name="city" autocomplete="on">
                                    <input class="form-control form-control-user" type="text"
                                           id="street" placeholder="Street" required=""
                                           style="margin: 17px 0px 0px;padding: 6px 12px;" name="street"
                                           minlength="1" maxlength="45"
                                           pattern="^[a-zA-Z].{1,45}$">
                                    <input class="form-control form-control-user" type="number"
                                           id="house" placeholder="House" required=""
                                           style="margin: 17px 0 0;padding: 6px 12px;" name="house">
                                </div>
                            </div>
                            <button class="btn btn-primary d-block btn-user w-100" style="margin-top: 2rem; width: 80%;"
                                    id="submitBtn" type="submit">
                                Create Event
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>
<%@include file="/include/footer.jsp" %>
</body>
</html>