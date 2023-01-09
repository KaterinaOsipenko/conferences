<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="ct" uri="/WEB-INF/customTag" %>
<%@ include file="adminHeader.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/fonts/Cabin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/fonts/Lora.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Login-Form-Basic-icons.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.12.0/css/all.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/fonts/fontawesome5-overrides.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Responsive-UI-Card.css">
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous">
    </script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/js/bootstrap.min.js"
            integrity="sha384-oesi62hOLfzrys4LxRF63OJCXdXDipiYWBnvTl9Y9/TRlw5xlKIEHpNyvvDShgf/"
            crossorigin="anonymous">
    </script>
    <title>Topics</title>
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
                                <th>Topic name</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="topic" items="${topics}">
                                <tr>
                                    <td>${topic.name}</td>
                                    <td>
                                        <button type="button" class="btn btn-primary"
                                                data-toggle="modal" data-target="#deleteModal">
                                            Delete
                                        </button>
                                    </td>
                                    <div class="modal fade" id="deleteModal">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" style="color:black;">
                                                        Delete topic
                                                    </h5>
                                                    <button type="button" class="close"
                                                            data-dismiss="modal" aria-label="Close">
                                                        <span aria-hidden="true">×</span>
                                                    </button>
                                                </div>
                                                <div class="modal-body" style="color: black">
                                                    Are you sure that you want to delete topic ${topic.name}?
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary"
                                                            data-dismiss="modal">
                                                        Cancel
                                                    </button>
                                                    <form action="${pageContext.request.contextPath}/admin/deleteTopic"
                                                          method="post">
                                                        <input type="hidden" name="topicId" value="${topic.id}"/>
                                                        <button class="btn btn-primary" type="submit">Delete</button>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </section>
    </div>
</header>
<%@include file="/include/footer.jsp" %>
</body>
</html>
