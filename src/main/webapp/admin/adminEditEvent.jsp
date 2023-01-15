<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="ct" uri="/WEB-INF/customTag" %>
<%@ include file="adminHeader.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <link rel="stylesheet" href="../fonts/font-awesome.min.css">
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
    <title>Event Card</title>
</head>
<body id="page-top" data-bs-spy="scroll" data-bs-target="#mainNav" data-bs-offset="77">
<header class="masthead" style="background-image: url('/img/downloads-bg.jpg');">
    <div class="intro-body">
        <section class="py-4 py-xl-5">
            <div class="container h-100">
                <div class="text-white bg-light border rounded border-0 p-4"
                     style="display: flex; flex-direction: column;">
                    <p style="text-align: left">
                        <a style="color: black;" href="${pageContext.request.contextPath}/admin/viewEvents">Go Back</a>
                    </p>
                    <p class="text-danger">${ex}</p>
                    <div class="row h-100">
                        <div class="col-md-10 col-xl-8 text-center d-flex d-sm-flex d-md-flex justify-content-center align-items-center mx-auto justify-content-md-start align-items-md-center justify-content-xl-center">
                            <div class="table-responsive">
                                <form method="post" action="${pageContext.request.contextPath}/admin/editEvent">
                                    <table class="table">
                                        <tbody>
                                        <tr>
                                            <td>
                                                <label for="name">Name</label>
                                            </td>
                                            <td>
                                                <input class="form-control form-control-user" type="text" id="name"
                                                       name="name" maxlength="45"
                                                       value="${event.name}"
                                                       pattern="^[a-zA-Z].{1,45}$"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <label for="about">About</label>
                                            </td>
                                            <td>
                                                <input class="form-control form-control-user" type="text" id="about"
                                                       name="about" maxlength="65"
                                                       pattern="^[a-zA-Z].{1,65}$"
                                                       value="${event.description}"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Address</td>
                                            <td>${event.address.country}, ${event.address}
                                                <button type="button" class="btn btn-primary"
                                                        data-toggle="modal" data-target="#changeAddressModal">
                                                    Edit
                                                </button>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><label for="date">Date</label></td>
                                            <td>
                                                <input class="form-control form-control-user" type="date" name="date"
                                                       id="date"
                                                       value="${event.date.toLocalDate()}"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <label for="time">Time</label>
                                            </td>
                                            <td>
                                                <input class="form-control form-control-user" type="time" name="time"
                                                       id="time"
                                                       value="<ct:localTimeTag time="${event.date}"/>"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Category</td>
                                            <td>
                                                <div style="display: flex; gap: .5rem; height: 3.5rem;">
                                                    <c:forEach var="category" items="${event.categories}">
                                                        <div style="display: flex;">
                                                            <p class="mb-4 text-body text-bg-light">${category.name}</p>
                                                            <form method="post"
                                                                  action="${pageContext.request.contextPath}/admin/deleteCategoryFromEvent">
                                                                <input hidden name="id_category" value="${category.id}">
                                                                <input hidden name="id_event" value="${event.id}">
                                                                <button type="submit" class="close" style="border: none"
                                                                        aria-label="Close">
                                                                    <span aria-hidden="true">×</span>
                                                                </button>
                                                            </form>
                                                        </div>
                                                    </c:forEach>
                                                    <button type="button" class="btn btn-light"
                                                            style="--bs-btn-hover-bg: none; color: rgba(66, 220, 163); height: 2rem;"
                                                            data-toggle="modal"
                                                            data-target="#addCategoryToEvent">
                                                        + add category
                                                    </button>
                                                </div>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <input type="hidden" name="id_event" value="${event.id}"/>
                                    <button type="submit" class="btn btn-primary">Edit</button>
                                </form>
                            </div>
                            <div class="modal fade" id="addCategoryToEvent">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" style="color:black;">
                                                Add category to ${event.name}
                                            </h5>
                                            <button type="button" class="close"
                                                    data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">×</span>
                                            </button>
                                        </div>
                                        <div class="modal-body" style="color: black">
                                            <form method="post"
                                                  action="${pageContext.request.contextPath}/admin/addCategoryToEvent">
                                                <input hidden name="id_event"
                                                       value="${event.id}">
                                                <select class="form-select" name="id_category">
                                                    <c:forEach var="category"
                                                               items="${categories}">
                                                        <option value="${category.id}">${category.name}</option>
                                                    </c:forEach>
                                                </select>
                                                <div style="display: flex; justify-content: center; gap: 2rem; margin-top: 1rem;">
                                                    <button type="submit"
                                                            class="btn btn-primary">
                                                        Add
                                                    </button>
                                                    <button type="button"
                                                            class="btn btn-secondary"
                                                            data-dismiss="modal">
                                                        Cancel
                                                    </button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="modal fade" id="changeAddressModal">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" style="color:black;">
                                                Change address
                                            </h5>
                                            <button type="button" class="close"
                                                    data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">×</span>
                                            </button>
                                        </div>
                                        <div class="modal-body" style="color: black">
                                            <form method="post"
                                                  action="${pageContext.request.contextPath}/admin/changeAddress"
                                                  style="display: flex; flex-direction: column; gap: 1rem;">
                                                <input id="country" class="form-control form-control-user"
                                                       type="text" name="country" value="${event.address.country}"
                                                       maxlength="45" pattern="^[a-zA-Z].{1,45}$">
                                                <input id="city" class="form-control form-control-user" type="text"
                                                       name="city" value="${event.address.city}"
                                                       maxlength="45" pattern="^[a-zA-Z].{1,45}$">
                                                <input id="street" class="form-control form-control-user"
                                                       type="text" name="street" value="${event.address.street}"
                                                       maxlength="45" pattern="^[a-zA-Z].{1,45}$">
                                                <input id="house" class="form-control form-control-user"
                                                       type="number" name="house" value="${event.address.house}">
                                                <input type="hidden" name="eventId" value="${event.id}">
                                                <input type="hidden" name="addressId" value="${event.address.id}">
                                                <div style="display: flex; justify-content: center; gap: 2rem;">
                                                    <button type="submit" class="btn btn-primary">
                                                        Change Address
                                                    </button>
                                                    <button type="button" class="btn btn-secondary"
                                                            data-dismiss="modal"> Cancel
                                                    </button>
                                                </div>
                                            </form>
                                        </div>
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