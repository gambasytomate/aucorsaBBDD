package controller.dao;
import model.Routes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
    /**
     * Clase RutaDAO
     * @author Marta
     */
    public class RuteDAO {

        public boolean insertarRuta(Connection con, Routes ruta) throws SQLException {
            String sql = "INSERT INTO rutas (register, numdriver, idplace, day_of_week, imagen) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, ruta.getMatricula());
                ps.setInt(2, ruta.getNumConductor());
                ps.setInt(3, ruta.getIdLugar());
                ps.setString(4, ruta.getDiaSemana());
                ps.setString(5, ruta.getImagen());
                return ps.executeUpdate() > 0;
            }
        }

        public boolean modificarRuta(Connection con, Routes ruta) throws SQLException {
            String sql = "UPDATE rutas SET day_of_week = ?, imagen = ? WHERE register = ? AND numdriver = ? AND idplace = ?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, ruta.getDiaSemana());
                ps.setString(2, ruta.getImagen());
                ps.setString(3, ruta.getMatricula());
                ps.setInt(4, ruta.getNumConductor());
                ps.setInt(5, ruta.getIdLugar());
                return ps.executeUpdate() > 0;
            }
        }

        public boolean eliminarRuta(Connection con, String matricula, int numConductor, int idLugar) throws SQLException {
            String sql = "DELETE FROM rutas WHERE register = ? AND numdriver = ? AND idplace = ?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, matricula);
                ps.setInt(2, numConductor);
                ps.setInt(3, idLugar);
                return ps.executeUpdate() > 0;
            }
        }

        public List<Routes> mostrarTodasRutas(Connection con) throws SQLException {
            List<Routes> listaRutas = new ArrayList<>();
            String sql = "SELECT register, numdriver, idplace, day_of_week, imagen FROM rutas";
            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String matricula = rs.getString("register");
                    int numConductor = rs.getInt("numdriver");
                    int idLugar = rs.getInt("idplace");
                    String diaSemana = rs.getString("day_of_week");
                    String imagen = rs.getString("imagen");

                    listaRutas.add(new Routes(matricula, numConductor, idLugar, diaSemana, imagen));
                }
            }
            return listaRutas;
        }
    }