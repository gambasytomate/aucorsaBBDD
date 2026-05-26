package controller.dao;

import model.Bus;
import model.Routes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RuteDAO {

    /*
     * Inserta un nuevo Bus en la base de datos.
     * @param con Conexión activa a la base de datos
     * @param bus Objeto Bus con los datos a insertar
     * @return true si se insertó correctamente una fila, false en caso de error
     */
    public static boolean añadirRuta(Connection con, Routes rute) {

        // Sentencia SQL parametrizada para evitar SQL Injection
        String sql = "INSERT INTO BDP (register, numDriver, idlace) VALUES (?, ?, ?)";

        // Prepara la sentencia SQL
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            // Asigna los datos del objeto Bus a los parámetros de la sentencia
            ps.setString(1, rute.getRegistro());
            ps.setString(2, String.valueOf(rute.getNumConductor()));
            ps.setString(3, String.valueOf(rute.getIdLugar()));
            ps.setString(3, String.valueOf(rute.getDiaSemana()));


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
     * @param register Matrícula del bus a buscar
     * @return un objeto Bus con los datos encontrados, o null si no existe o ocurre un error
     */
    public static Routes buscarRuta(Connection con, String register) {

        // Sentencia SQL parametrizada para buscar un bus por su matrícula
        String sql = "SELECT * FROM BDP WHERE register = ?;";

        // Prepara la sentencia SQL
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, register);

            // Ejecuta la consulta y obtiene el resultado
            try (ResultSet rs = ps.executeQuery()) {

                // Si se encuentra un registro, crea y devuelve el objeto Bus
                if (rs.next()) {
                    String register1 = rs.getString("register");
                    int numdriver = rs.getInt("numdriver");
                    int idplace = rs.getInt("license");
                    String dayOfWeek = rs.getString("day_of_week");
                    String imagen = rs.getString("imagen");

                    return new Routes(register1, numdriver, idplace, dayOfWeek, imagen);
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
    public static List<Routes> mostrarTodasRutas(Connection con) {

        // Sentencia SQL para seleccionar todos los buses
        String sql = "SELECT * FROM BDP";

        // Prepara y ejecuta la sentencia SQL y obtiene el resultado
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Lista donde se almacenarán todos los buses
            List<Routes> rutes = new ArrayList<>();

            // Recorre los resultados y crea objetos Bus
            while (rs.next()) {
                String register = rs.getString("register");
                int numDriver = rs.getInt("numdriver");
                int idPlace = rs.getInt("idplace");
                String dayOfWeek = rs.getString("day_of_week");
                String imagen = rs.getString("imagen");
                rutes.add(new Routes(register, numDriver, idPlace, dayOfWeek,imagen));
            }
            // Devuelve la lista completa de buses
            return rutes;
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
    public static boolean eliminarRuta(Connection con, String register) {

        // Sentencia SQL parametrizada para eliminar un bus por su matrícula
        String sql = "DELETE FROM BDP WHERE register = ?";

        // Prepara la sentencia SQL
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            // Asigna la matrícula al parámetro de la sentencia
            ps.setString(1, register);

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
    public static boolean modificarRuta(Connection con, Routes routes) {

        // Sentencia SQL parametrizada para actualizar los datos de un bus por su matrícula
        String sql = "UPDATE BDP SET numdriver = ?, idplace = ? WHERE register = ?";

        // Prepara la sentencia SQL
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            // Asigna los datos actualizados del objeto Bus a los parámetros de la sentencia
            ps.setInt(1, routes.getNumConductor());
            ps.setInt(2, routes.getIdLugar());
            ps.setString(3, routes.getRegistro());

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
