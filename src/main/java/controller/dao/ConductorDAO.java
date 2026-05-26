package controller.dao;

import model.Conductor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase ConductorDAO
 * Es el DAO del modelo Conductor que se conecta con la base de datos
 * Permite crear, modificar , buscar y eliminar usuarios.
 * @author Marta
 */
public class ConductorDAO {

    /*
     * Inserta un nuevo Conductor incluyendo el nombre de su imagen.
     * @param con
     * @param conductor
     * @return
     */
    public boolean insertarConductor(Connection con, Conductor conductor) {
        String sql = "INSERT INTO Driver (numdriver, name, surname, imagen) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, conductor.getNumConductor());
            ps.setString(2, conductor.getNombre());
            ps.setString(3, conductor.getApellido());
            ps.setString(4, conductor.getImagen());

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
     * Busca un Conductor y recupera el nombre de su imagen.
     * @param con
     * @param numConductor
     * @return
     */
    public Conductor buscarConductor(Connection con, int numConductor) {
        String sql = "SELECT * FROM Driver WHERE numdriver = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, numConductor);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Conductor(
                            rs.getInt("numdriver"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getString("imagen")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * Obtiene todos los conductores con sus respectivas imágenes.
     * @param con
     * @return
     */
    public List<Conductor> mostrarTodosConductores(Connection con) {
        String sql = "SELECT * FROM Driver";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Conductor> conductores = new ArrayList<>();

            while (rs.next()) {
                conductores.add(new Conductor(
                        rs.getInt("numDriver"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("imagen")
                ));
            }
            return conductores;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * Elimina un Conductor de la base de datos.
     * @param con
     * @param numConductor
     * @return
     */
    public boolean eliminarConductor(Connection con, int numConductor) {
        String sql = "DELETE FROM Driver WHERE numdriver = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, numConductor);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
     * Modifica un Conductor incluyendo la actualización de su imagen.
     * @param con
     * @param conductor
     * @return
     */
    public boolean modificarConductor(Connection con, Conductor conductor) {
        String sql = "UPDATE Driver SET name = ?, surname = ?, imagen = ? WHERE numdriver = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, conductor.getNombre());
            ps.setString(2, conductor.getApellido());
            ps.setString(3, conductor.getImagen());
            ps.setInt(4, conductor.getNumConductor());

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
