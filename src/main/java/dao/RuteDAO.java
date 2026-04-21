package dao;

import connection.ConnectionBBBDD;
import model.Rute;

import java.sql.*;
import java.util.Scanner;


public class RuteDAO {


    /**
     * Consulta una ruta por su ID
     * @param id ID de la ruta a buscar
     * @return Rute encontrada o null si no existe
     */
    public static Rute consultarRuta(int id) {
        Rute ruta = null;
        String query = "SELECT * FROM rutas WHERE id_L = ?";

        try (Connection conn = ConnectionBBBDD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int id_L = rs.getInt("id_L");
                int cod_Post = rs.getInt("cod_Post");
                String ciudad = rs.getString("ciudad");
                String ubicacion = rs.getString("ubicacion");
                ruta = new Rute(id_L, cod_Post, ciudad, ubicacion);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar ruta: " + e.getMessage());
            e.printStackTrace();
        }

        return ruta;
    }

    /**
     * Inserta una nueva ruta en la base de datos
     * @param sc Scanner para leer los datos del usuario
     */
    public static void insertarRuta(Scanner sc) {
        String query = "INSERT INTO rutas (cod_Post, ciudad, ubicacion) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionBBBDD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            sc.nextLine(); // Limpiar buffer

            System.out.print("Código Postal: ");
            int codPost = sc.nextInt();
            sc.nextLine(); // Limpiar buffer

            System.out.print("Ciudad: ");
            String ciudad = sc.nextLine();

            System.out.print("Ubicación: ");
            String ubicacion = sc.nextLine();

            pstmt.setInt(1, codPost);
            pstmt.setString(2, ciudad);
            pstmt.setString(3, ubicacion);

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Ruta insertada correctamente.");
            } else {
                System.out.println("Error al insertar ruta.");
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar ruta: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**Actualiza el día de la semana de una ruta específica
     * @param sc
     */
    public static void actualizarRutas(Scanner sc) {
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
                System.out.println("Día de la semana actualizado correctamente.");
            } else {
                System.out.println("No se encontró la asignación especificada.");
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar día de la semana: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**Elimina una ruta específica solicitando los tres parámetros
     * @param sc Scanner para leer datos del usuario
     */
    public static void eliminarRuta(Scanner sc) {
        sc.nextLine(); // Limpiar buffer

        System.out.println("\n=== ELIMINAR RUTA ===");
        System.out.print("Matrícula del autobús (register): ");
        String register = sc.nextLine();

        System.out.print("ID del conductor (numdriver): ");
        int numdriver = sc.nextInt();

        System.out.print("ID del lugar (idplace): ");
        int idplace = sc.nextInt();
        sc.nextLine(); // Limpiar buffer

        // Mostrar la ruta a eliminar
        String querySelect = "SELECT * FROM BDP WHERE register = ? AND numdriver = ? AND idplace = ?";

        try (Connection conn = ConnectionBBBDD.getConnection();
             PreparedStatement pstmtSelect = conn.prepareStatement(querySelect)) {

            pstmtSelect.setString(1, register);
            pstmtSelect.setInt(2, numdriver);
            pstmtSelect.setInt(3, idplace);

            ResultSet rs = pstmtSelect.executeQuery();

            if (rs.next()) {
                String dayOfWeek = rs.getString("day_of_week");
                System.out.println("\nRuta encontrada:");
                System.out.println("  Bus: " + register);
                System.out.println("  Conductor: " + numdriver);
                System.out.println("  Lugar: " + idplace);
                System.out.println("  Día: " + dayOfWeek);

                System.out.print("\n¿Estás seguro de eliminar esta ruta? (S/N): ");
                String confirmacion = sc.nextLine();

                if (confirmacion.equalsIgnoreCase("S")) {
                    String queryDelete = "DELETE FROM BDP WHERE register = ? AND numdriver = ? AND idplace = ?";
                    try (PreparedStatement pstmtDelete = conn.prepareStatement(queryDelete)) {
                        pstmtDelete.setString(1, register);
                        pstmtDelete.setInt(2, numdriver);
                        pstmtDelete.setInt(3, idplace);

                        int filasAfectadas = pstmtDelete.executeUpdate();

                        if (filasAfectadas > 0) {
                            System.out.println("Ruta eliminada correctamente.");
                        } else {
                            System.out.println("Error al eliminar ruta.");
                        }
                    }
                } else {
                    System.out.println("Operación cancelada.");
                }
            } else {
                System.out.println("No se encontró la ruta especificada.");
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar ruta: " + e.getMessage());
            e.printStackTrace();
        }
    }





}
