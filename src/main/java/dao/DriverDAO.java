package dao;

import connection.ConnectionBBBDD;
import model.Driver;

import java.sql.*;
import java.util.Scanner;

public class DriverDAO {

    /**
     * Consulta un conductor por su ID
     * @param id ID del conductor a buscar
     * @return Driver encontrado o null si no existe
     */
    public static Driver consultarConductor(int id) {
        Driver driver = null;
        String query = "SELECT * FROM conductores WHERE id_C = ?";

        try (Connection con = ConnectionBBBDD.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int id_C = rs.getInt("id_C");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                driver = new Driver(id_C, nombre, apellido);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar conductor: " + e.getMessage());
            e.printStackTrace();
        }

        return driver;
    }

    /**
     * Inserta múltiples conductores en la base de datos
     * @param cantidad Número de conductores a insertar
     * @param sc Scanner para leer los datos del usuario
     */
    public static void insertarConductores(int cantidad, Scanner sc) {
        String query = "INSERT INTO conductores (nombre, apellido) VALUES (?, ?)";

        try (Connection conn = ConnectionBBBDD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            sc.nextLine(); // Limpiar buffer

            for (int i = 0; i < cantidad; i++) {
                System.out.println("\n--- Conductor " + (i + 1) + " ---");
                System.out.print("Nombre: ");
                String nombre = sc.nextLine();

                System.out.print("Apellido: ");
                String apellido = sc.nextLine();

                pstmt.setString(1, nombre);
                pstmt.setString(2, apellido);

                int filasAfectadas = pstmt.executeUpdate();

                if (filasAfectadas > 0) {
                    System.out.println("Conductor " + nombre + " " + apellido + " insertado correctamente.");
                } else {
                    System.out.println("Error al insertar conductor.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar conductores: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Actualiza los datos de un conductor existente
     * @param id ID del conductor a actualizar
     * @param nombre Nuevo nombre
     * @param apellido Nuevo apellido
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public static boolean actualizarConductor(int id, String nombre, String apellido) {
        String query = "UPDATE conductores SET nombre = ?, apellido = ? WHERE id_C = ?";

        try (Connection conn = ConnectionBBBDD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido);
            pstmt.setInt(3, id);

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar conductor: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un conductor de la base de datos
     * @param id ID del conductor a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public static boolean eliminarConductor(int id) {
        String query = "DELETE FROM conductores WHERE id_C = ?";

        try (Connection conn = ConnectionBBBDD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar conductor: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
