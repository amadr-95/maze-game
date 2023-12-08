<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<%--        pintar solo las celdas alrededor del protagonista --%>
        <%--<table>
            <c:forEach items="${laberinto.mapa}" var="fila" varStatus="i">
                <tr>
                    <c:forEach items="${fila}" var="celda" varStatus="j">
                        <c:if test="${i.index >= laberinto.posicionProtagonista.fila - 1 && i.index <= laberinto.posicionProtagonista.fila + 1}">
                            <c:if test="${j.index >= laberinto.posicionProtagonista.columna - 1 && j.index <= laberinto.posicionProtagonista.columna + 1}">
                                <td>${celda}</td>
                            </c:if>
                        </c:if>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>--%>
        <table>
            <c:forEach items="${laberinto.mapa}" var="fila">
                <tr>
                    <c:forEach items="${fila}" var="celda">
                        <td>${celda}</td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
    </div>
    <%--include de los controles del personaje--%>
    <jsp:include page="controles.jsp"/>
    <c:if test="${not empty requestScope.mensaje}">
        <div class="mensaje">
            ${requestScope.mensaje}
        </div>
    </c:if>
</main>
</body>
</html>