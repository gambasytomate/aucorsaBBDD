package dao;


import connection.ConnectionBBBDD;
import model.BDP;
import model.Driver;
import model.Rute;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class BDPDAO {
    public static void consultarDiaPorCiudad(String city) {
        String query = "SELECT p.idplace, p.city, p.ubicacion, b.day_of_week, b.register, b.numdriver " +
                "FROM BDP b " +
                "INNER JOIN Place p ON b.idplace = p.idplace " +
                "WHERE p.city = ?";

        try (Connection conn = ConnectionBBBDD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, city);
            ResultSet rs = pstmt.executeQuery();

            boolean encontrado = false;
            System.out.println("RUTAS EN " + city.toUpperCase());

            while (rs.next()) {
                encontrado = true;
                int idplace = rs.getInt("idplace");
                String ubicacion = rs.getString("ubicacion");
                String dayOfWeek = rs.getString("day_of_week");
                String register = rs.getString("register");
                int numdriver = rs.getInt("numdriver");

                System.out.println("  Lugar ID: " + idplace);
                System.out.println("  Ubicación: " + ubicacion);
                System.out.println("  Día: " + dayOfWeek);
                System.out.println("  Bus (matrícula): " + register);
                System.out.println("  Conductor: " + numdriver);
                System.out.println("  ────────────────────────────────────────");
            }

            if (!encontrado) {
                System.out.println("  ✗ No se encontraron rutas para la city: " + city);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar rutas por city: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public static void actualizarDiaSemana(Scanner sc) {
        sc.nextLine(); // Limpiar buffer

        System.out.print("Matrícula del autobús: ");
        String register = sc.nextLine();

        System.out.print("ID del conductor: ");
        int numdriver = sc.nextInt();

        System.out.print("ID del lugar: ");
        int idplace = sc.nextInt();
        sc.nextLine(); // Limpiar buffer

        System.out.print("Nuevo día de la semana: ");
        String newDay = sc.nextLine();

        String query = "UPDATE BDP SET day_of_week = ? WHERE register = ? AND numdriver = ? AND idplace = ?";

        try (Connection conn = ConnectionBBBDD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, newDay);
            pstmt.setString(2, register);
            pstmt.setInt(3, numdriver);
            pstmt.setInt(4, idplace);

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("✓ Día de la semana actualizado correctamente.");
            } else {
                System.out.println("✗ No se encontró la asignación especificada.");
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar día de la semana: " + e.getMessage());
            e.printStackTrace();
        }
    }



}
