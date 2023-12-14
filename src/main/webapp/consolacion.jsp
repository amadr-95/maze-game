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
    <style>
        body {
            background-color: #f2f2f2;
            font-family: Arial, sans-serif;
        }

        .card {
            width: 600px;
            margin: 30px auto;
            padding: 20px;
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        h1 {
            text-align: center;
            color: #c52323;
        }

        p {
            text-align: center;
            font-size: 18px;
            color: #333333;
        }

        img {
            width: 100%;
            height: auto;
        }

        button {
            background-color: #c52323;
            border: none;
            color: white;
            padding: 15px 32px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 4px 2px;
            cursor: pointer;
        }

        .center {
            text-align: center;
        }
    </style>
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