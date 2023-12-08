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
            int[] posicionProta = posicionElementoJuego(Laberinto2.PROTA, laberinto);
            //Obtenemos la posicion del malo
            int[] posicionMalo = posicionElementoJuego(Laberinto2.MALO, laberinto);

            if (comprobarMovimiento(laberinto, posicionProta, direccion)) {
                mover(laberinto, posicionProta, direccion, request, response);
            } else {
                movimientoNoPermitido(request, response);
            }

            // Mover al personaje malo hacia el protagonista
            moverMalo(laberinto, posicionMalo, posicionProta, request, response);
        } else {
            movimientoNoPermitido(request, response);
        }
    }

    //Model
    private int[] posicionElementoJuego(Character elemento, Laberinto2 laberinto) {
        //buscar la posicion del protagonista en el mapa
        int[] posicion = new int[2];
        for (int f = 0; f < laberinto.getNumFilas(); f++) {
            for (int c = 0; c < laberinto.getNumColumnas(); c++) {
                if (laberinto.getMapa()[f][c] == elemento) {
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
                return elemento == Laberinto2.VACIO || elemento == Laberinto2.PROTA || elemento == Laberinto2.PREMIO;
            case "abajo":
                //comprobar si se sale del tablero
                if (posicion[0] + 1 >= laberinto.getNumFilas()) {
                    return false;
                }
                elemento = laberinto.getMapa()[posicion[0] + 1][posicion[1]];
                return elemento == Laberinto2.VACIO || elemento == Laberinto2.PROTA || elemento == Laberinto2.PREMIO;
            case "izquierda":
                //comprobar si se sale del tablero
                if (posicion[1] - 1 < 0) {
                    return false;
                }
                elemento = laberinto.getMapa()[posicion[0]][posicion[1] - 1];
                return elemento == Laberinto2.VACIO || elemento == Laberinto2.PROTA || elemento == Laberinto2.PREMIO;
            case "derecha":
                //comprobar si se sale del tablero
                if (posicion[1] + 1 >= laberinto.getNumColumnas()) {
                    return false;
                }
                elemento = laberinto.getMapa()[posicion[0]][posicion[1] + 1];
                return elemento == Laberinto2.VACIO || elemento == Laberinto2.PROTA || elemento == Laberinto2.PREMIO;
            default:
                return false;
        }
    }

    private String calcularDireccion(int dx, int dy) {
        if (dx == 0 && dy == -1) {
            return "arriba";
        } else if (dx == 0 && dy == 1) {
            return "abajo";
        } else if (dx == -1 && dy == 0) {
            return "izquierda";
        } else if (dx == 1 && dy == 0) {
            return "derecha";
        } else {
            // En caso de que las diferencias no representen una dirección válida (por defecto)
            return "derecha";
        }
    }

    private void moverMalo(Laberinto2 laberinto, int[] posicionMalo, int[] posicionProta, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean atrapado = false;
        // Calcular la dirección hacia el protagonista
        int dx = Integer.compare(posicionProta[1], posicionMalo[1]); //columna
        int dy = Integer.compare(posicionProta[0], posicionMalo[0]); //fila
        //Calculamos la direccion en la que deberia moverse el malo
        String direccion = calcularDireccion(dx, dy);

        // Inserta la posición nueva
       /* int nuevaFila = posicionMalo[0];
        int nuevaColumna = posicionMalo[1];

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
        }*/

        // Intentar mover al malo
        if (comprobarMovimiento(laberinto, posicionMalo, direccion)) {
            laberinto.getMapa()[posicionMalo[0]][posicionMalo[1]] = Laberinto2.VACIO;
            laberinto.getMapa()[posicionMalo[0] + dy][posicionMalo[1] + dx] = Laberinto2.MALO; //se mueve en diagonal pero
            //se come los obstaculos
        }

        // Verificar si el malo ha atrapado al protagonista
        if (posicionMalo[0] + dy == posicionProta[0] && posicionMalo[1] + dx == posicionProta[1]) {
            // El malo ha atrapado al protagonista
            atrapado = true;
        }
        if(atrapado) {
            response.sendRedirect("consolacion.jsp");
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

        if (laberinto.getMapa()[nuevaFila][nuevaColumna] == Laberinto2.PREMIO) {
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