<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Iniciar sesión</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            margin: 150px auto;
        }

        h1 {
            text-align: center;
            color: #333;
        }

        form {
            max-width: 300px;
            margin: 0 auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        label {
            display: block;
            margin-bottom: 10px;
            color: #333;
        }

        input[type="text"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        button[type="submit"] {
            display: block;
            width: 100%;
            padding: 10px;
            margin-top: 10px;
            background-color: #c52323;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        button[type="submit"]:hover {
            background-color: rgba(197, 35, 35, 0.7);
        }
    </style>
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
        <p style="color: red;">${requestScope.error}</p>
    </c:if>
</div>
</body>
</html>

