package controller.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase ConnectionBBDD
 * Permite la conexión con la base de datos donde se iniciará sesión
 * con su URL, el USER y la PASSWORD
 * @author Marta
 */

public class ConnectionBBDD {

    // ==================== CONSTANTES DE CONEXIÓN ====================

    public static final String URL = "jdbc:mysql://127.0.0.1:3306/Aucorsa";
    public static final String USER = "root";
    public static final String PASSWORD = "Daniel2204+";

    // ==================== MÉTODOS ====================

    /*
     * Crea y devuelve una nueva conexión activa a la base de datos.
     * @return una conexión activa a la base de datos
     * @throws SQLException si ocurre un error al establecer la conexión
     */
    public static Connection getConexion() throws SQLException {
        // Establece y devuelve la conexión usando las credenciales definidas
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}