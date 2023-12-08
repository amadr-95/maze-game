package com.example.servlets;

import com.example.modelo.Laberinto2;
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
        Laberinto2 laberinto = (Laberinto2) request.getSession().getAttribute("laberinto");

        if (direccion != null) {
            //obtenemos la posicion del protagonista
            int[] posicion = posicionProtagonista(laberinto);

            if (comprobarMovimiento(laberinto, posicion, direccion)) {
                mover(laberinto, posicion, direccion, request, response);
            } else {
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
        Character elemento;
        switch (direccion) {
            case "arriba":
                //comprobar si se sale del tablero
                if (posicion[0] - 1 < 0) {
                    return false;
                }
                elemento = laberinto.getMapa()[posicion[0] - 1][posicion[1]];
                return elemento == Laberinto2.VACIO || elemento == Laberinto2.MALO || elemento == Laberinto2.PREMIO;
            case "abajo":
                //comprobar si se sale del tablero
                if (posicion[0] + 1 >= laberinto.getNumFilas()) {
                    return false;
                }
                elemento = laberinto.getMapa()[posicion[0] + 1][posicion[1]];
                return elemento == Laberinto2.VACIO || elemento == Laberinto2.MALO || elemento == Laberinto2.PREMIO;
            case "izquierda":
                //comprobar si se sale del tablero
                if (posicion[1] - 1 < 0) {
                    return false;
                }
                elemento = laberinto.getMapa()[posicion[0]][posicion[1] - 1];
                return elemento == Laberinto2.VACIO || elemento == Laberinto2.MALO || elemento == Laberinto2.PREMIO;
            case "derecha":
                //comprobar si se sale del tablero
                if (posicion[1] + 1 >= laberinto.getNumColumnas()) {
                    return false;
                }
                elemento = laberinto.getMapa()[posicion[0]][posicion[1] + 1];
                return elemento == Laberinto2.VACIO || elemento == Laberinto2.MALO || elemento == Laberinto2.PREMIO;
            default:
                return false;
        }
    }

    private void mover(Laberinto2 laberinto, int[] posicion, String direccion,
                       HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean hayPremio = false;

        // Borra la posición actual del protagonista
        laberinto.getMapa()[posicion[0]][posicion[1]] = Laberinto2.VACIO;

        // Inserta la posición nueva
        int nuevaFila = posicion[0];
        int nuevaColumna = posicion[1];

        switch (direccion) {
            case "arriba":
                nuevaFila--;
                break;
            case "abajo":
                nuevaFila++;
                break;
            case "izquierda":
                nuevaColumna--;
                break;
            case "derecha":
                nuevaColumna++;
                break;
        }

        if (laberinto.getMapa()[nuevaFila][nuevaColumna] == laberinto.PREMIO) {
            hayPremio = true;
        }

        // Muevo al protagonista
        laberinto.getMapa()[nuevaFila][nuevaColumna] = Laberinto2.PROTA;
        request.setAttribute("mensaje", "Movimiento hacia " + direccion);

        if (hayPremio) {
            response.sendRedirect("felicitacion.jsp");
        } else {
            request.getRequestDispatcher("/juego.jsp").forward(request, response);
        }
    }

    private void movimientoNoPermitido(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("mensaje", "Movimiento no permitido");
        request.getRequestDispatcher("/juego.jsp").forward(request, response);
    }
}