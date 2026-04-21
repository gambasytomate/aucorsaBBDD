package connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBBBDD {

        static Conf.ConfigDB configDB = new Conf.ConfigDB();

    /**Obtiene la conexion de la base de datos
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(configDB.getUrl(), configDB.getUSER(), configDB.getPass());
        }

}
