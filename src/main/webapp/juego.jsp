<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.modelo.Laberinto2" %>
<%--Controller--%>
<jsp:useBean id="laberinto" class="com.example.modelo.Laberinto2" scope="session"/>
<%
    if (session.getAttribute("nombreUsuario") == null) {
        response.sendRedirect("index.jsp");
    }
%>
<html>
<head>
    <title>Juego</title>
    <link rel="stylesheet" href="styles/style.css">
</head>
<body>
<h1>Jugando como <%=session.getAttribute("nombreUsuario")%>
</h1>
<%-- pintar mapa del laberinto --%>
<main>
    <div class="panel-juego">
        <table>
            <c:forEach items="${laberinto.mapa}" var="fila">
                <tr>
                    <c:forEach items="${fila}" var="celda">
                        <c:choose>
                            <c:when test="${celda == Laberinto2.MALO}">
                                <td><img src="images/png/badSanta.png"></td>
                            </c:when>
                            <c:when test="${celda == Laberinto2.PROTA}">
                                <td><img src="images/png/santa.png"></td>
                            </c:when>
                            <c:when test="${celda == Laberinto2.OBSTACULO}">
                                <td><img src="images/png/wall.png"></td>
                            </c:when>
                            <c:when test="${celda == Laberinto2.PREMIO}">
                                <td><img src="images/png/prize.png"></td>
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