package com.example.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ReiniciarLaberintoServlet", value = "/ReiniciarLaberintoServlet")
public class ReiniciarLaberintoServlet extends HttpServlet {

    public ReiniciarLaberintoServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //eliminamos el laberinto de la sesion
        if(request.getSession().getAttribute("laberinto") != null) {
            request.getSession().removeAttribute("laberinto");
        }
        response.sendRedirect("juego.jsp");
    }
}