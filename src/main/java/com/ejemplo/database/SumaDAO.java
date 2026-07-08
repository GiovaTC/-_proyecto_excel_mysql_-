package com.ejemplo.database;

import com.ejemplo.model.Resultado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SumaDAO {

    public void guardar(Resultado resultado) {

        String sql =
                "INSERT INTO suma_filas(fila,suma) VALUES(?,?)";

        try (
                Connection conexion = Conexion.obtenerConexion();

                PreparedStatement ps =
                        conexion.prepareStatement(sql)

        ) {
            ps.setInt(1, resultado.getFila());
            ps.setDouble(2, resultado.getSuma());

            ps.executeUpdate();

            System.out.println("registro Guardado!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listar() {

        String sql =
                "SELECT * FROM suma_filas";

        try (
                Connection conexion = Conexion.obtenerConexion();
                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery(sql)
        ) {
            System.out.println();
            System.out.println("===== DATOS EN MYSQL =====");

            while (rs.next()) {
                System.out.println(
                        "ID: " + rs.getInt("id")
                                + "  Fila: " + rs.getInt("fila")
                                + "  Suma: " + rs.getDouble("suma")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
