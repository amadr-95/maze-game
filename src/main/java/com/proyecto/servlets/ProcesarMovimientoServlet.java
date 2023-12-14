package com.proyecto.servlets;

import com.proyecto.modelo.Laberinto;
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
        Laberinto laberinto = (Laberinto) request.getSession().getAttribute("laberinto");

        if (direccion != null) {
            //obtenemos la posicion del protagonista
            int[] posicionProta = posicionElementoJuego(Laberinto.PROTA, laberinto);
            //Obtenemos la posicion del malo
            int[] posicionMalo = posicionElementoJuego(Laberinto.MALO, laberinto);
            //Obtenemos la posicion del premio
            int[] posicionPremio = posicionElementoJuego(Laberinto.PREMIO, laberinto);

            int[] nuevaPosicionProta = posicionProta; //por defecto
            if (comprobarMovimiento(laberinto, posicionProta, direccion, Laberinto.PROTA)) {
                //si se puede mover, devuelve su nueva posicion
                nuevaPosicionProta = moverProta(laberinto, posicionProta, direccion, request);
            } else {
                movimientoNoPermitido(request, Laberinto.PROTA);
            }

            // Mover al personaje malo con la nueva posición del protagonista
            int[] nuevaPosicionMalo = moverMalo(laberinto, posicionMalo, nuevaPosicionProta, request);

            // Verificar si el prota ha atrapado el premio
            if (nuevaPosicionProta[0] == posicionPremio[0] && nuevaPosicionProta[1] == posicionPremio[1]) {
                response.sendRedirect("felicitacion.jsp");
                // Verificar si el malo ha atrapado al prota
            } else if (nuevaPosicionMalo[0] == nuevaPosicionProta[0] && nuevaPosicionMalo[1] == nuevaPosicionProta[1]) {
                response.sendRedirect("consolacion.jsp");
            } else {
                // Sigue el juego
                request.getRequestDispatcher("/juego.jsp").forward(request, response);
            }

        } else {
            request.setAttribute("error", "No has proporcionado una dirección válida");
        }
    }

    //Model
    private int[] posicionElementoJuego(Character elemento, Laberinto laberinto) {
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

    private boolean comprobarMovimiento(Laberinto laberinto, int[] posicion, String direccion, Character personaje) {
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
            //a partir de aquí solo entrará el malo
            case "arriba_izquierda":
                nuevaFila--;
                nuevaColumna--;
                break;
            case "arriba_derecha":
                nuevaFila--;
                nuevaColumna++;
                break;
            case "abajo_izquierda":
                nuevaFila++;
                nuevaColumna--;
                break;
            case "abajo_derecha":
                nuevaFila++;
                nuevaColumna++;
                break;
            default:
                return false;
        }

        // Comprobar si se sale del tablero
        if (nuevaFila < 0 || nuevaFila >= laberinto.getNumFilas() || nuevaColumna < 0 || nuevaColumna >= laberinto.getNumColumnas()) {
            return false;
        }

        // Obtener el elemento en la nueva posición
        Character elemento = laberinto.getMapa()[nuevaFila][nuevaColumna];

        // Verificar si el personaje puede moverse a esa posición
        if (personaje == Laberinto.PROTA) {
            return elemento == Laberinto.VACIO || elemento == Laberinto.PREMIO || elemento == Laberinto.MALO;
        } else if (personaje == Laberinto.MALO) {
            return elemento == Laberinto.VACIO || elemento == Laberinto.PROTA;
        }

        return false;
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
        } else if (dx == -1 && dy == -1) {
            return "arriba_izquierda";
        } else if (dx == 1 && dy == -1) {
            return "arriba_derecha";
        } else if (dx == -1 && dy == 1) {
            return "abajo_izquierda";
        } else if (dx == 1 && dy == 1) {
            return "abajo_derecha";
        } else {
            // En caso de que las diferencias no representen una dirección válida (por defecto)
            return "derecha";
        }
    }

    private int[] moverMalo(Laberinto laberinto, int[] posicionMalo, int[] posicionProta, HttpServletRequest request) {
        // Calcular la dirección hacia el protagonista
        int dx = Integer.compare(posicionProta[1], posicionMalo[1]); //columna
        int dy = Integer.compare(posicionProta[0], posicionMalo[0]); //fila
        //Calculamos la direccion en la que deberia moverse el malo
        String direccion = calcularDireccion(dx, dy);

        int nuevaFila = posicionMalo[0];
        int nuevaColumna = posicionMalo[1];
        // Intentar mover al malo en la direccion calculada
        if (comprobarMovimiento(laberinto, posicionMalo, direccion, Laberinto.MALO)) {
            // Borra la posición actual del malo
            laberinto.getMapa()[posicionMalo[0]][posicionMalo[1]] = Laberinto.VACIO;
            // Inserta la posición nueva
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
                case "arriba_izquierda":
                    nuevaFila--;
                    nuevaColumna--;
                    break;
                case "arriba_derecha":
                    nuevaFila--;
                    nuevaColumna++;
                    break;
                case "abajo_izquierda":
                    nuevaFila++;
                    nuevaColumna--;
                    break;
                case "abajo_derecha":
                    nuevaFila++;
                    nuevaColumna++;
                    break;
            }
            // Muevo al malo
            laberinto.getMapa()[nuevaFila][nuevaColumna] = Laberinto.MALO;
            request.setAttribute("infoMalo", "Malo: Movimiento hacia " + direccion);
        } else {
            // Si no se puede mover en la dirección calculada, de momento no se mueve y se queda en la misma posición
            nuevaFila = posicionMalo[0];
            nuevaColumna = posicionMalo[1];
            movimientoNoPermitido(request, Laberinto.MALO);
            // como opcion estaria moverlo a una posicion aleatoria o incluso moverlo hacia el premio (el juego se complica bastante)
        }

        return new int[]{nuevaFila, nuevaColumna};
    }

    private int[] moverProta(Laberinto laberinto, int[] posicion, String direccion, HttpServletRequest request) {
        // Borra la posición actual del protagonista
        laberinto.getMapa()[posicion[0]][posicion[1]] = Laberinto.VACIO;

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

        // Muevo al protagonista
        laberinto.getMapa()[nuevaFila][nuevaColumna] = Laberinto.PROTA;
        request.setAttribute("infoProta", "Prota: Movimiento hacia " + direccion);

        //Devolvemos la nueva posicion del protagonista
        return new int[]{nuevaFila, nuevaColumna};
    }

    private void movimientoNoPermitido(HttpServletRequest request, Character personaje) {
        if (personaje == Laberinto.PROTA)
            request.setAttribute("infoProta", "Prota: Movimiento no permitido");
        else if (personaje == Laberinto.MALO)
            request.setAttribute("infoMalo", "Malo: Movimiento no permitido");
    }
}