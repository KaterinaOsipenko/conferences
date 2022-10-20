<%--
  Created by IntelliJ IDEA.
  User: Дом
  Date: 24.09.2022
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="header.jsp" %>
<%@include file="footer.jsp" %>
<html>
<head>
    <meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <title>Profile</title>
</head>
<body>
<p>Requset: ${user.firstName}</p>
<c:out value="${user.email}">Username</c:out>
<p><a href="${pageContext.request.contextPath}/logout">LogOut</a></p>
</body>
</html>
