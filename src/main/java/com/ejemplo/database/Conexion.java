package com.ejemplo.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL =
            "jdbc:mysql://localhost:3306/proyecto_excel";

    private static final String USER = "root";
    private static final String PASSWORD = "Tapiero123";

    public static Connection obtenerConexion() {

        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (SQLException e) {

            System.out.println("error de Conexion");

            e.printStackTrace();

            return null;
        }
    }
}   
