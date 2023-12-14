<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session.getAttribute("nombreUsuario") == null) {
        response.sendRedirect("index.jsp");
    }
%>
<html>
<head>
    <title>Felicitacion</title>
    <link rel="stylesheet" href="styles/fin-juego.css">
</head>
<body>
<div class="card">
    <h1>Enhorabuena <c:out value="${sessionScope.nombreUsuario}"/>, has ganado!</h1>
    <p>Que la magia de la Navidad llene tu hogar de alegría y amor.</p>
    <img src="images/png/victoria.png" alt="imagen victoria">
</div>
<div class="center">
    <a href="ReiniciarLaberintoServlet"><button>Volver a jugar</button></a>
</div>
</body>
</html>

