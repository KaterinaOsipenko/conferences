<jsp:include page="header.jsp"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
<p>Login</p>
<c:out value="${ex}"/>
<form method="post" action="login">
    <label for="email">email</label>
    <input type="email" name="email" id="email" required/>
    <label for="password">Password</label>
    <input type="password" name="password" id="password" required/>
    <button type="submit">Login</button>
</form>
<p><a href="index.jsp">Home</a></p>
</body>
</html>