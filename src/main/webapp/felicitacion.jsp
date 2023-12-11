<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session.getAttribute("nombreUsuario") == null) {
        response.sendRedirect("index.jsp");
    }
%>
<html>
<head>
    <title>Felicitación</title>
</head>
<body>
    <h1>Enhorabuena <c:out value="${sessionScope.nombreUsuario}"/>, has ganado!</h1>
</body>
</html>
