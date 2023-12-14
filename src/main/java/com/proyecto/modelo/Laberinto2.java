package com.proyecto.modelo;

import java.util.Random;

public class Laberinto2 {
    private final int numFilas;
    private final int numColumnas;
    private Character[][] mapa;

    // Constantes
    public static char VACIO = ' ';
    public static char PROTA = 'P';
    public static char MALO = 'M';
    public static char OBSTACULO = 'O';
    public static char PREMIO = 'R';
    public static char VACIO_VISIBLE = 'v';
    public static char PROTA_VISIBLE = 'p';
    public static char MALO_VISIBLE = 'm';
    public static char OBSTACULO_VISIBLE = 'o';
    public static char PREMIO_VISIBLE = 'r';

    public Laberinto2() {
        this(20, 20);
        for (int longitud = 4; longitud < 8; longitud++) {
            insertarObstaculoAleatorio(longitud);
            if (longitud < 6) {
                insertarObstaculoAleatorio(longitud);
                insertarObstaculoAleatorio(longitud);
            }
        }
        //insertar protagonista en posicion aleatoria
        insertarElemento(PROTA);
        // insertar malo en posicion aleatoria
        insertarElemento(MALO);
        // insertar premio en posicion aleatoria
        insertarElemento(PREMIO);
    }

    public Laberinto2(int numFilas, int numColumnas) {
        this.numFilas = numFilas;
        this.numColumnas = numColumnas;
        mapa = new Character[numFilas][numColumnas];
        for (int f = 0; f < numFilas; f++) {
            for (int c = 0; c < numColumnas; c++) {
                mapa[f][c] = ' ';
            }
        }
    }

    public int getNumFilas() {
        return numFilas;
    }

    public int getNumColumnas() {
        return numColumnas;
    }

    public Character[][] getMapa() {
        return mapa;
    }

    public void insertarElemento(char elemento) {
        Random rand = new Random(System.nanoTime());
        int fila, columna;
        do {
            fila = rand.nextInt(numFilas);
            columna = rand.nextInt(numColumnas);
        } while (!mapa[fila][columna].equals(VACIO));
        mapa[fila][columna] = elemento;
    }

    public boolean insertarObstaculo(int fila, int columna, int longitud, char direccion) {
        boolean sePuede = true;

        if (direccion == 'h') {
            if (columna + longitud > numColumnas || fila >= numFilas) {
                sePuede = false;
            } else {
                for (int c = columna; c < columna + longitud; c++) {
                    if (mapa[fila][c] != VACIO && mapa[fila][c] != VACIO_VISIBLE) {
                        sePuede = false;
                        break;
                    }
                }
            }
        } else if (direccion == 'v') {
            if (fila + longitud > numFilas || columna >= numColumnas) {
                sePuede = false;
            } else {
                for (int f = fila; f < fila + longitud && sePuede; f++) {
                    if (mapa[f][columna] != VACIO && mapa[f][columna] != VACIO_VISIBLE) {
                        sePuede = false;
                    }
                }
            }
        } else {
            sePuede = false; // Dirección no válida
        }

        if (sePuede) {
            if (direccion == 'h') {
                for (int c = columna; c < columna + longitud; c++) {
                    mapa[fila][c] = OBSTACULO;
                }
            } else {
                for (int f = fila; f < fila + longitud; f++) {
                    mapa[f][columna] = OBSTACULO;
                }
            }
        }

        return sePuede;
    }


    public boolean insertarObstaculoAleatorio(int longitud) {
        boolean insertado = false;
        Random rand = new Random(System.nanoTime());
        for (int intentos = 0; intentos < 1000 && !insertado; intentos++) {
            int fila = rand.nextInt(numFilas);
            int columna = rand.nextInt(numColumnas - 2) + 1;
            char direccion = rand.nextBoolean() ? 'h' : 'v';
            if (insertarObstaculo(fila, columna, longitud, direccion)) {
                insertado = true;
            }
        }
        return insertado;
    }
}

