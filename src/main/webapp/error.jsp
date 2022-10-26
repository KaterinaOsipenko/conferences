<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <title>Error</title>
</head>
<body>
<p>${ex}</p>
<a class="btn btn-primary fs-5 border-1 shadow-lg py-2 px-4" role="button"
   href="${pageContext.request.contextPath}/${address}">Go back to list</a>
</body>
</html>
