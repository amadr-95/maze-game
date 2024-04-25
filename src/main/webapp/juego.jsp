<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.proyecto.modelo.Laberinto" %>
<%--Controller--%>
<jsp:useBean id="laberinto" class="com.proyecto.modelo.Laberinto" scope="session"/>
<%
    if (session.getAttribute("nombreUsuario") == null) {
        response.sendRedirect("index.jsp");
    }
%>
<html>
<head>
    <title>Juego</title>
    <link rel="stylesheet" href="styles/juego.css">
</head>
<body>
<h1>Playing as <%=session.getAttribute("nombreUsuario")%>
</h1>
<%-- pintar mapa del laberinto --%>
<main>
    <div class="panel-juego">
        <table>
            <c:forEach items="${laberinto.mapa}" var="fila">
                <tr>
                    <c:forEach items="${fila}" var="celda">
                        <c:choose>
                            <c:when test="${celda == Laberinto.MALO}">
                                <td><img src="images/png/badSanta.png" alt="bad santa"></td>
                            </c:when>
                            <c:when test="${celda == Laberinto.PROTA}">
                                <td><img src="images/png/santa.png" alt="santa"></td>
                            </c:when>
                            <c:when test="${celda == Laberinto.OBSTACULO}">
                                <td><img src="images/png/wall.png" alt="wall"></td>
                            </c:when>
                            <c:when test="${celda == Laberinto.PREMIO}">
                                <td><img src="images/png/prize.png" alt="prize"></td>
                            </c:when>
                            <c:otherwise>
                                <td>${celda}</td>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
    </div>
    <%--include de los controles del personaje--%>
    <jsp:include page="controles.jsp"/>

    <c:if test="${not empty requestScope.error}">
        <p class="error">${requestScope.error}</p>
    </c:if>
    <div class="mensajes">
        <c:if test="${not empty requestScope.infoProta}">
            <p class="info">${requestScope.infoProta}</p>
        </c:if>
        <c:if test="${not empty requestScope.infoMalo}">
            <p class="info">${requestScope.infoMalo}</p>
        </c:if>
    </div>

</main>
</body>
</html>