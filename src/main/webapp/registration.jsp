<%--
  Created by IntelliJ IDEA.
  User: Дом
  Date: 24.09.2022
  Time: 15:02
  To change this template use File | Settings | File Templates.
--%>
<jsp:include page="header.jsp"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<p>Registration</p>
<c:out value="${ex}"/>
<form action="registration" method="post" accept-charset="UTF-8">
    <label for="firstname">Name: </label>
    <input type="text" name="firstname" id="firstname" pattern="^[a-zA-Z].{1,45}$" minlength="1" maxlength="45"
    />
    <label for="lastname">lastname</label>
    <input type="text" name="lastname" id="lastname" pattern="^[a-zA-Z].{1,45}$" minlength="1" maxlength="45" required/>
    <label for="email">email</label>
    <input type="email" name="email" id="email" required/>
    <label for="password">Password</label>
    <input type="password" name="password" id="password" pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{5,12}$"
           minlength="5" maxlength="12" required/>
    <label for="role">role</label>
    <select name="role" id="role" required>
        <option value="MODERATOR">Moderator</option>
        <option value="SPEAKER">Speaker</option>
    </select>
    <button type="submit" value="registration">Registration</button>
</form>
<p><a href="index.jsp">Home</a></p>
</body>
</html>
