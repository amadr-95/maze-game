package com.proyecto.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "SesionServlet", value = "/SesionServlet")
public class SesionServlet extends HttpServlet {

    public SesionServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener el nombre de usuario del formulario
        String nombreUsuario = request.getParameter("nombre");

        if (nombreUsuario != null && !nombreUsuario.trim().isEmpty()) {
            // almacenar el nombre de usuario en la sesión
            request.getSession().setAttribute("nombreUsuario", nombreUsuario);
            response.sendRedirect("juego.jsp");
        } else {
            request.setAttribute("error", "El nombre de usuario no puede estar vacío");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}