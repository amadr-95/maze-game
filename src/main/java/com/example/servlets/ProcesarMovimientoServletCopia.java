package com.example.servlets;

import com.example.modelo.Laberinto2;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "ProcesarMovimientoServlet", value = "/ProcesarMovimientoServlet")
public class ProcesarMovimientoServletCopia extends HttpServlet {

    public ProcesarMovimientoServletCopia() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        //Controller
        //Obtener la direccion de la peticion
        String direccion = request.getParameter("direccion");
        //Obetener el laberinto de la sesion
        Laberinto2 laberinto = (Laberinto2) request.getSession().getAttribute("laberinto");
        if (laberinto == null) {
            response.sendRedirect("index.jsp");
        }
        if (direccion != null) {
            switch (direccion) {
                case "arriba":

                    moverArriba(request, response);
                    break;
                case "abajo":
                    moverAbajo(laberinto, direccion, request, response);
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
    private int[] posicionProtagonista(Laberinto2 laberinto) {
        //buscar la posicion del protagonista en el mapa
        int[] posicion = new int[2];
        for (int f = 0; f < laberinto.getNumFilas(); f++) {
            for (int c = 0; c < laberinto.getNumColumnas(); c++) {
                if (laberinto.getMapa()[f][c].equals(Laberinto2.PROTA)) {
                    posicion[0] = f;
                    posicion[1] = c;
                    break;
                }
            }
        }
        return posicion;
    }

    private boolean comprobarMovimiento(Laberinto2 laberinto, int[] posicion, String direccion) {
        //comprobar si puede moverse a esa posicion
        switch (direccion) {
            case "arriba":
            case "abajo":
                Character elemento = laberinto.getMapa()[posicion[0] + 1][posicion[1]];
                if (elemento == Laberinto2.VACIO) {
                    return true;
                } else if (elemento == Laberinto2.MALO) {
                    return false;
                } else if (elemento == Laberinto2.PREMIO) {
                    return true;
                } else {
                    return false;

                }
            case "izquierda":
            case "derecha":
            default:
                return false;
        }
    }

    private void moverArriba(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("mensaje", "Movimiento hacia arriba");
        request.getRequestDispatcher("/juego.jsp").forward(request, response);
    }

    private void moverAbajo(Laberinto2 laberinto, String direccion, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //posicion del personaje en el mapa
        int[] posicion = posicionProtagonista(laberinto);
        //mover hacia abajo el personaje
        int f = posicion[0];
        int c = posicion[1];
        //comprobar si puede moverse a esa posicion
        if (comprobarMovimiento(laberinto, posicion, direccion)) {
            //borrar la posicion actual
            laberinto.getMapa()[f][c] = Laberinto2.VACIO;
            //insertar la posicion nueva
            laberinto.getMapa()[f + 1][c] = Laberinto2.PROTA;
            request.setAttribute("mensaje", "Movimiento hacia abajo");
        } else {
            //no se puede mover
            request.setAttribute("mensaje", "Movimiento no permitido");
        }
        request.getRequestDispatcher("/juego.jsp").forward(request, response);

        /*//borrar la posicion actual
        laberinto.getMapa()[f][c] = Laberinto2.VACIO;

        //insertar la posicion nueva
        laberinto.getMapa()[f + 1][c] = Laberinto2.PROTA;

        //enviar el mensaje
        request.setAttribute("mensaje", "Movimiento hacia abajo");
        request.getRequestDispatcher("/juego.jsp").forward(request, response);*/
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