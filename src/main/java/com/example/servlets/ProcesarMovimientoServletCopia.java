package com.example.servlets;

import com.example.modelo.Laberinto2;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Deprecated
@WebServlet(name = "ProcesarMovimientoServlet", value = "/ProcesarMovimientoServlet")
public class ProcesarMovimientoServletCopia extends HttpServlet {

    public ProcesarMovimientoServletCopia() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        //Controlador
        //Obtener la direccion de la peticion
        String direccion = request.getParameter("direccion");
        //Obtener el laberinto de la sesion
        Laberinto2 laberinto = (Laberinto2) request.getSession().getAttribute("laberinto");
        if (direccion != null) {
            //posicion del personaje en el mapa
            int[] posicion = posicionProtagonista(laberinto);
            switch (direccion) {
                case "arriba":
                    moverArriba(laberinto, posicion, comprobarMovimiento(laberinto, posicion, direccion), request, response);
                    break;
                case "abajo":
                    moverAbajo(laberinto, posicion, comprobarMovimiento(laberinto, posicion, direccion), request, response);
                    break;
                case "izquierda":
                    moverIzquierda(laberinto, posicion, comprobarMovimiento(laberinto, posicion, direccion), request, response);
                    break;
                case "derecha":
                    moverDerecha(laberinto, posicion, comprobarMovimiento(laberinto, posicion, direccion), request, response);
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

    private void moverArriba(Laberinto2 laberinto, int[] posicion, boolean sePuede, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean hayPremio = false;
        if (sePuede) {
            //borrar la posicion actual
            laberinto.getMapa()[posicion[0]][posicion[1]] = Laberinto2.VACIO;
            //insertar la posicion nueva
            if (laberinto.getMapa()[posicion[0] - 1][posicion[1]] == laberinto.PREMIO) {
                laberinto.getMapa()[posicion[0] - 1][posicion[1]] = Laberinto2.PROTA;
                hayPremio = true;
            } else {
                laberinto.getMapa()[posicion[0] - 1][posicion[1]] = Laberinto2.PROTA;
            }
            request.setAttribute("mensaje", "Movimiento hacia arriba");
        } else {
            //no se puede mover
            request.setAttribute("mensaje", "Movimiento no permitido");
        }
        if(hayPremio)
            response.sendRedirect("felicitacion.jsp");
        else
            request.getRequestDispatcher("/juego.jsp").forward(request, response);
    }

    private void moverAbajo(Laberinto2 laberinto, int[] posicion, boolean sePuede, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (sePuede) {
            //borrar la posicion actual
            laberinto.getMapa()[posicion[0]][posicion[1]] = Laberinto2.VACIO;
            //insertar la posicion nueva
            laberinto.getMapa()[posicion[0] + 1][posicion[1]] = Laberinto2.PROTA;
            request.setAttribute("mensaje", "Movimiento hacia abajo");
        } else {
            //no se puede mover
            request.setAttribute("mensaje", "Movimiento no permitido");
        }
        request.getRequestDispatcher("/juego.jsp").forward(request, response);
    }

    private void moverIzquierda(Laberinto2 laberinto, int[] posicion, boolean sePuede, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (sePuede) {
            //borrar la posicion actual
            laberinto.getMapa()[posicion[0]][posicion[1]] = Laberinto2.VACIO;
            //insertar la posicion nueva
            laberinto.getMapa()[posicion[0]][posicion[1] - 1] = Laberinto2.PROTA;
            request.setAttribute("mensaje", "Movimiento hacia izquierda");
        } else {
            //no se puede mover
            request.setAttribute("mensaje", "Movimiento no permitido");
        }
        request.getRequestDispatcher("/juego.jsp").forward(request, response);
    }

    private void moverDerecha(Laberinto2 laberinto, int[] posicion, boolean sePuede, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (sePuede) {
            //borrar la posicion actual
            laberinto.getMapa()[posicion[0]][posicion[1]] = Laberinto2.VACIO;
            //insertar la posicion nueva
            laberinto.getMapa()[posicion[0]][posicion[1] + 1] = Laberinto2.PROTA;
            request.setAttribute("mensaje", "Movimiento hacia derecha");
        } else {
            //no se puede mover
            request.setAttribute("mensaje", "Movimiento no permitido");
        }
        request.getRequestDispatcher("/juego.jsp").forward(request, response);
    }

    private void movimientoNoPermitido(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("mensaje", "Movimiento no permitido");
        request.getRequestDispatcher("/juego.jsp").forward(request, response);
    }
}