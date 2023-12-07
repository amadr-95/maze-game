<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Inicio</title>
</head>
<body>
<h1>Bienvenido al juego del laberinto</h1>
<h3>Introduce tu nombre de usuario para jugar</h3>
<%-- Formulario para introducir el nombre de usuario --%>
<form action="SesionServlet" method="post">
    <input type="text" name="nombre" placeholder="Nombre de usuario">
    <input type="submit" value="Jugar">
</form>
<c:if test="${not empty requestScope.error}">
    <p style="color: red;">${requestScope.error}</p>
</c:if>
</body>
</html>
