package controller.dao;

import model.Bus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase BusDAO
 * DAO (Data Access Object) para la entidad Bus.
 * Proporciona métodos para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * sobre la tabla "Bus" en la base de datos.
 * @author Marta
 */
public class BusDAO {

    /*
     * Inserta un nuevo Bus en la base de datos.
     * @param con Conexión activa a la base de datos
     * @param bus Objeto Bus con los datos a insertar
     * @return true si se insertó correctamente una fila, false en caso de error
     */
    public static boolean añadirBus(Connection con, Bus bus) {

        // Sentencia SQL parametrizada para evitar SQL Injection
        String sql = "INSERT INTO Bus (matricula, tipo, licencia) VALUES (?, ?, ?)";

        // Prepara la sentencia SQL
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            // Asigna los datos del objeto Bus a los parámetros de la sentencia
            ps.setString(1, bus.getMatricula());
            ps.setString(2, bus.getTipo());
            ps.setString(3, bus.getLicencia());

            // Ejecuta la inserción y devuelve true si se afectó exactamente una fila
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            // Imprime información de error si ocurre una excepción SQL
            e.printStackTrace();
        }
        // Devuelve false si hubo algún error durante la inserción
        return false;
    }

    /*
     * Busca un Bus en la base de datos según su matrícula.
     * @param con Conexión activa a la base de datos
     * @param matricula Matrícula del bus a buscar
     * @return un objeto Bus con los datos encontrados, o null si no existe o ocurre un error
     */
    public static Bus buscarBus(Connection con, String matricula) {

        // Sentencia SQL parametrizada para buscar un bus por su matrícula
        String sql = "SELECT * FROM Bus WHERE matricula = ?;";

        // Prepara la sentencia SQL
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, matricula);

            // Ejecuta la consulta y obtiene el resultado
            try (ResultSet rs = ps.executeQuery()) {

                // Si se encuentra un registro, crea y devuelve el objeto Bus
                if (rs.next()) {
                    String matriculaBus = rs.getString("matricula");
                    String tipo = rs.getString("tipo");
                    String licencia = rs.getString("licencia");
                    return new Bus(matriculaBus, tipo, licencia);
                }
            }
        } catch (SQLException e) {
            // Imprime información de error si ocurre una excepción SQL
            e.printStackTrace();
        }
        // Devuelve null si no se encontró el bus o hubo algún error
        return null;
    }

    /*
     * Obtiene todos los registros de la tabla "Bus" en la base de datos.
     * @param con Conexión activa a la base de datos
     * @return una lista de objetos Bus con todos los registros de la tabla,
     * o null si ocurre un error en la consulta
     */
    public static List<Bus> mostrarTodosLosBuses(Connection con) {

        // Sentencia SQL para seleccionar todos los buses
        String sql = "SELECT * FROM Bus";

        // Prepara y ejecuta la sentencia SQL y obtiene el resultado
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Lista donde se almacenarán todos los buses
            List<Bus> buses = new ArrayList<>();

            // Recorre los resultados y crea objetos Bus
            while (rs.next()) {
                String matricula = rs.getString("matricula");
                String tipo = rs.getString("tipo");
                String licencia = rs.getString("licencia");
                buses.add(new Bus(matricula, tipo, licencia));
            }
            // Devuelve la lista completa de buses
            return buses;
        } catch (SQLException e) {
            // Imprime información de error si ocurre una excepción SQL
            e.printStackTrace();
        }
        // Devuelve null si hubo algún error durante la consulta
        return null;
    }

    /*
     * Elimina un Bus de la base de datos según su matrícula.
     * @param con Conexión activa a la base de datos
     * @param matricula Matrícula del bus a eliminar
     * @return true si se eliminó correctamente una fila, false en caso de error
     */
    public static boolean eliminarBus(Connection con, String matricula) {

        // Sentencia SQL parametrizada para eliminar un bus por su matrícula
        String sql = "DELETE FROM Bus WHERE matricula = ?";

        // Prepara la sentencia SQL
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            // Asigna la matrícula al parámetro de la sentencia
            ps.setString(1, matricula);

            // Ejecuta la eliminación y devuelve true si se afectó exactamente una fila
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            // Imprime información de error si ocurre una excepción SQL
            e.printStackTrace();
        }
        // Devuelve false si hubo algún error durante la eliminación
        return false;
    }

    /*
     * Modifica un Bus existente en la base de datos.
     * @param con Conexión activa a la base de datos
     * @param bus Objeto Bus con los datos actualizados
     * @return true si se modificó correctamente una fila, false en caso de error
     */
    public static boolean modificarBus(Connection con, Bus bus) {

        // Sentencia SQL parametrizada para actualizar los datos de un bus por su matrícula
        String sql = "UPDATE Bus SET tipo = ?, licencia = ? WHERE matricula = ?";

        // Prepara la sentencia SQL
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            // Asigna los datos actualizados del objeto Bus a los parámetros de la sentencia
            ps.setString(1, bus.getTipo());
            ps.setString(2, bus.getLicencia());
            ps.setString(3, bus.getMatricula());

            // Ejecuta la actualización y devuelve true si se afectó exactamente una fila
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            // Imprime información de error si ocurre una excepción SQL
            e.printStackTrace();
        }
        // Devuelve false si hubo algún error durante la actualización
        return false;
    }
}