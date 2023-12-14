<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session.getAttribute("nombreUsuario") == null) {
        response.sendRedirect("index.jsp");
    }
%>
<html>
<head>
    <title>Derrota</title>
    <link rel="stylesheet" href="styles/fin-juego.css">
</head>
<body>
<div class="card">
    <h1>¡Has sido atrapado, <c:out value="${sessionScope.nombreUsuario}"/>!</h1>
    <p>Otra vez será...</p>
    <img src="images/png/derrota.png" alt="imagen derrota">
</div>
<div class="center">
    <a href="ReiniciarLaberintoServlet"><button>Volver a jugar</button></a>
</div>
</body>
</html>