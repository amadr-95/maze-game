package com.example.servlets;

import com.example.modelo.Laberinto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Prueba", value = "/Prueba")
public class Prueba extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Laberinto laberinto = new Laberinto();
        System.out.println(laberinto);
        /*
            Laberinto laberinto = new Laberinto(20, 20);
            for (int i = 2; i< 11; i++) {
                laberinto.insertarObstaculoAleatorio(i);
                laberinto.insertarObstaculoAleatorio(i);
            }
            */
        //vista
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Laberinto</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Laberinto</h1>");
        out.println("<table border='1'>");
        for (int f = 0; f < laberinto.getNumFilas(); f++) {
            out.println("<tr>");
            for (int c = 0; c < laberinto.getNumColumnas(); c++) {
                out.println("<td>");
                out.println(laberinto.getMapa()[f][c]);
                out.println("</td>");
            }
            out.println("</tr>");
        }
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }
}
