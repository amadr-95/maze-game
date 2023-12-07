package com.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "ProcesarMovimientoServlet", value = "/ProcesarMovimientoServlet")
public class ProcesarMovimientoServlet extends HttpServlet {

    public ProcesarMovimientoServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        //Controller
        String direccion = request.getParameter("direccion");
        if(direccion != null) {
            switch (direccion) {
                case "arriba":
                    moverArriba(request, response);
                    break;
                case "abajo":
                    moverAbajo(request, response);
                    break;
                case "izquierda":
                    moverIzquierda(request, response);
                    break;
                case "derecha":
                    moverDerecha(request, response);
                    break;
                default:
                    movimientoNoPermitido(request, response);
            }
        } else {
            movimientoNoPermitido(request, response);
        }
    }

    //Model
    private void moverArriba(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("mensaje", "Movimiento hacia arriba");
        request.getRequestDispatcher("/juego.jsp").forward(request, response);
    }

    private void moverAbajo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("mensaje", "Movimiento hacia abajo");
        request.getRequestDispatcher("/juego.jsp").forward(request, response);
    }

    private void moverIzquierda(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("mensaje", "Movimiento hacia izquierda");
        request.getRequestDispatcher("/juego.jsp").forward(request, response);
    }

    private void moverDerecha(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("mensaje", "Movimiento hacia derecha");
        request.getRequestDispatcher("/juego.jsp").forward(request, response);
    }

    private void movimientoNoPermitido(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("mensaje", "Movimiento no permitido");
        request.getRequestDispatcher("/juego.jsp").forward(request, response);
    }
}