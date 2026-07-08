package com.ejemplo.database;

import com.ejemplo.model.Resultado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

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

    public List<Resultado> obtenerTodos() {

        List<Resultado> lista = new ArrayList<>();

        String sql = "SELECT fila, suma FROM suma_filas ORDER BY fila";

        try (
                Connection conexion = Conexion.obtenerConexion();
                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery(sql)
        ) {

            while (rs.next()) {

                Resultado resultado = new Resultado();

                resultado.setFila(rs.getInt("fila"));
                resultado.setSuma(rs.getDouble("suma"));

                lista.add(resultado);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return lista;

    }
}
