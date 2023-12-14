<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Iniciar sesión</title>
    <link rel="stylesheet" href="styles/inicio-juego.css">
</head>
<body>
<h1>Bienvenido al juego del laberinto</h1>
<form action="SesionServlet">
    <label for="nombre">Nombre de usuario:</label>
    <input type="text" id="nombre" name="nombre">
    <br>
    <button type="submit">Comenzar juego</button>
</form>
<div>
    <c:if test="${not empty requestScope.error}">
        <p class="error">${requestScope.error}</p>
    </c:if>
</div>
</body>
</html>

