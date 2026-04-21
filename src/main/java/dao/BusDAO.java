package dao;

import connection.ConnectionBBBDD;
import model.Bus;

import java.sql.*;
import java.util.Scanner;

public class BusDAO {

    /**
     * Consulta un autobús por su ID
     * @param id ID del autobús a buscar
     * @return Bus encontrado o null si no existe
     */
    public static Bus consultarBus(String id) {
        Bus bus = null;
        String query = "SELECT * FROM buses WHERE id_bus = ?";

        try (Connection conn = ConnectionBBBDD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String id_bus = rs.getString("id_bus");
                String tipo = rs.getString("tipo");
                String licencia = rs.getString("licencia");
                bus = new Bus(id_bus, tipo, licencia);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar autobús: " + e.getMessage());
            e.printStackTrace();
        }

        return bus;
    }

    /**
     * Inserta un nuevo autobús en la base de datos
     * @param sc Scanner para leer los datos del usuario
     * Suelo limpiar el buffer porque si no se me skipea y no pone bién los datos btw
     */
    public static void insertarBus(Scanner sc) {
        String query = "INSERT INTO buses (id_bus, tipo, licencia) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionBBBDD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            sc.nextLine();

            System.out.print("ID del Autobús: ");
            String idBus = sc.nextLine();

            System.out.print("Tipo: ");
            String tipo = sc.nextLine();

            System.out.print("Licencia: ");
            String licencia = sc.nextLine();

            pstmt.setString(1, idBus);
            pstmt.setString(2, tipo);
            pstmt.setString(3, licencia);

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Autobús insertado correctamente.");
            } else {
                System.out.println("Error al insertar autobús.");
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar autobús: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**Actualiza los datos de un autobús existente
     * @param id ID del autobús a actualizar
     * @param tipo Nuevo tipo
     * @param licencia Nueva licencia
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public static boolean actualizarBus(String id, String tipo, String licencia) {
        String query = "UPDATE buses SET tipo = ?, licencia = ? WHERE id_bus = ?";

        try (Connection conn = ConnectionBBBDD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, tipo);
            pstmt.setString(2, licencia);
            pstmt.setString(3, id);

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar autobús: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un autobús de la base de datos
     * @param id ID del autobus a eliminar
     * @return true si se elimino correctamente, false en caso contrario
     */
    public static boolean eliminarBus(String id) {
        String query = "DELETE FROM buses WHERE id_bus = ?";

        try (Connection conn = ConnectionBBBDD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, id);

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar autobús: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
