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
            //Obtenemos la posicion del premio
            int[] posicionPremio = posicionElementoJuego(Laberinto2.PREMIO, laberinto);

            int[] nuevaPosicionProta = posicionProta; //por defecto
            if (comprobarMovimiento2(laberinto, posicionProta, direccion, Laberinto2.PROTA)) {
                //si se puede mover, devuelve su nueva posicion
                nuevaPosicionProta = moverProta(laberinto, posicionProta, direccion, request, response);
            } else {
                movimientoNoPermitido(request, response);
            }

            // Mover al personaje malo con la nueva posición del protagonista
            int[] nuevaPosicionMalo = moverMalo(laberinto, posicionMalo, nuevaPosicionProta, request, response);

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

    private boolean comprobarMovimiento2(Laberinto2 laberinto, int[] posicion, String direccion, Character personaje) {
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
        if (personaje == Laberinto2.PROTA) {
            return elemento == Laberinto2.VACIO || elemento == Laberinto2.PREMIO || elemento == Laberinto2.MALO;
        } else if (personaje == Laberinto2.MALO) {
            return elemento == Laberinto2.VACIO || elemento == Laberinto2.PROTA;
        }

        return false;
    }


//    private boolean comprobarMovimiento(Laberinto2 laberinto, int[] posicion, String direccion, Character personaje) {
//        //comprobar si puede moverse a esa posicion
//        Character elemento;
//        switch (direccion) {
//            case "arriba":
//                //comprobar si se sale del tablero
//                if (posicion[0] - 1 < 0) {
//                    return false;
//                }
//                //compruebo qué elemento existe en esa posicion
//                elemento = laberinto.getMapa()[posicion[0] - 1][posicion[1]];
//                //en fucion del personaje que se mueva, compruebo si puede moverse a esa posicion
//                if(personaje == Laberinto2.PROTA) {
//                    return elemento == Laberinto2.VACIO || elemento == Laberinto2.PREMIO || elemento == Laberinto2.MALO;
//                } else if(personaje == Laberinto2.MALO){
//                    return elemento == Laberinto2.VACIO || elemento == Laberinto2.PROTA;
//                }
////                return elemento == Laberinto2.VACIO || elemento == Laberinto2.PREMIO;
//            case "abajo":
//                //comprobar si se sale del tablero
//                if (posicion[0] + 1 >= laberinto.getNumFilas()) {
//                    return false;
//                }
//                elemento = laberinto.getMapa()[posicion[0] + 1][posicion[1]];
//                if(personaje == Laberinto2.PROTA) {
//                    return elemento == Laberinto2.VACIO || elemento == Laberinto2.PREMIO || elemento == Laberinto2.MALO;
//                } else if(personaje == Laberinto2.MALO){
//                    return elemento == Laberinto2.VACIO || elemento == Laberinto2.PROTA;
//                }
////                return elemento == Laberinto2.VACIO || elemento == Laberinto2.PREMIO;
//            case "izquierda":
//                //comprobar si se sale del tablero
//                if (posicion[1] - 1 < 0) {
//                    return false;
//                }
//                elemento = laberinto.getMapa()[posicion[0]][posicion[1] - 1];
//                if(personaje == Laberinto2.PROTA) {
//                    return elemento == Laberinto2.VACIO || elemento == Laberinto2.PREMIO || elemento == Laberinto2.MALO;
//                } else if(personaje == Laberinto2.MALO){
//                    return elemento == Laberinto2.VACIO || elemento == Laberinto2.PROTA;
//                }
////                return elemento == Laberinto2.VACIO || elemento == Laberinto2.PREMIO;
//            case "derecha":
//                //comprobar si se sale del tablero
//                if (posicion[1] + 1 >= laberinto.getNumColumnas()) {
//                    return false;
//                }
//                elemento = laberinto.getMapa()[posicion[0]][posicion[1] + 1];
//                if(personaje == Laberinto2.PROTA) {
//                    return elemento == Laberinto2.VACIO || elemento == Laberinto2.PREMIO || elemento == Laberinto2.MALO;
//                } else if(personaje == Laberinto2.MALO){
//                    return elemento == Laberinto2.VACIO || elemento == Laberinto2.PROTA;
//                }
////                return elemento == Laberinto2.VACIO || elemento == Laberinto2.PREMIO;
//            default:
//                return false;
//        }
//    }

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

    private int[] moverMalo(Laberinto2 laberinto, int[] posicionMalo, int[] posicionProta, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean atrapado = false;
        // Calcular la dirección hacia el protagonista
        int dx = Integer.compare(posicionProta[1], posicionMalo[1]); //columna
        int dy = Integer.compare(posicionProta[0], posicionMalo[0]); //fila
        //Calculamos la direccion en la que deberia moverse el malo
        String direccion = calcularDireccion(dx, dy);

        // Inserta la posición nueva
        /*int nuevaFila = posicionMalo[0];
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
        int nuevaFila = posicionMalo[0];
        int nuevaColumna = posicionMalo[1];
        // Intentar mover al malo en la direccion calculada
        if (comprobarMovimiento2(laberinto, posicionMalo, direccion, Laberinto2.MALO)) {
            // Borra la posición actual del malo
            laberinto.getMapa()[posicionMalo[0]][posicionMalo[1]] = Laberinto2.VACIO;
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
            }
            // Muevo al malo
            laberinto.getMapa()[nuevaFila][nuevaColumna] = Laberinto2.MALO;
        } else {
            // Si no se puede mover en la dirección calculada, de momento no se mueve y se queda en la misma posición
            nuevaFila = posicionMalo[0];
            nuevaColumna = posicionMalo[1];
        }

        // Verificar si el malo ha atrapado al protagonista
        /*if (posicionMalo[0] + dy == posicionProta[0] && posicionMalo[1] + dx == posicionProta[1]) {
            // El malo ha atrapado al protagonista
            atrapado = true;
        }*/
        /*if(atrapado) {
            response.sendRedirect("consolacion.jsp");
        }*/
        return new int[]{nuevaFila, nuevaColumna};
    }

    private int[] moverProta(Laberinto2 laberinto, int[] posicion, String direccion,
                             HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        boolean hayPremio = false;

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

//        if (laberinto.getMapa()[nuevaFila][nuevaColumna] == Laberinto2.PREMIO) {
//            hayPremio = true;
//        }

        // Muevo al protagonista
        laberinto.getMapa()[nuevaFila][nuevaColumna] = Laberinto2.PROTA;
        request.setAttribute("mensaje", "Movimiento hacia " + direccion);

        //Devolvemos la nueva posicion del protagonista
        return new int[]{nuevaFila, nuevaColumna};
    }

    private void movimientoNoPermitido(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("mensaje", "Movimiento no permitido");
        //request.getRequestDispatcher("/juego.jsp").forward(request, response);
    }
}