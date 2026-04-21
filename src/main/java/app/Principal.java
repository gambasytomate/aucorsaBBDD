package app;

import connection.ConnectionBBBDD;
import dao.BDPDAO;
import dao.BusDAO;
import dao.DriverDAO;
import dao.RuteDAO;
import model.Driver;
import model.Rute;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        do {
            System.out.println("Bienvenido al sistema AUCORSA \n" +
                    "¿Qué desea hacer?\n" +
                    "1. Consultar conductores\n" +
                    "2. Insertar datos\n" +
                    "3. Actualizar rutas\n" +
                    "4. Eliminar rutas\n" +
                    "5. Consultar dia por ciudad\n" +
                    "6. Salir");
            System.out.println("Introduce una opción: ");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1:
                    System.out.println("Introduce el ID del conductor: ");
                    int numDriver = sc.nextInt();
                    Driver conductor = DriverDAO.consultarConductor(numDriver);
                    if (conductor != null) {
                        System.out.println("Conductor encontrado: " + conductor.toString());
                    } else {
                        System.out.println("No se encontró el conductor con ID: " + numDriver);
                    }
                    break;
                    case 2:
                        int opcMenu2;
                        do {
                            System.out.println("Menú de inserción:");
                            System.out.println("0. Salir");
                            System.out.println("1. Conductores");
                            System.out.println("2. Rutas");
                            System.out.println("3. Autobuses");
                            System.out.println("Introduce una opción: ");
                            opcMenu2 = sc.nextInt();
                            switch (opcMenu2) {
                                case 1:
                                    System.out.println("Introduce el número de conductores que vas a añadir: ");
                                    int contador = sc.nextInt();
                                    DriverDAO.insertarConductores(contador, sc);
                                        break;
                                    case 2:
                                        RuteDAO.insertarRuta(sc);
                                        break;
                                    case 3:
                                        BusDAO.insertarBus(sc);
                                        break;
                                    case 0:
                                        System.out.println("Volviendo al menú principal...");
                                        break;
                                    default:
                                        System.out.println("Opción no válida");
                                        break;
                            }
                            } while (opcMenu2 != 0);
                            break;

                        case 3:
                            RuteDAO.actualizarRutas(sc);
                            break;

                        case 4:
                            RuteDAO.eliminarRuta(sc);
                            break;
                        case 5:
                            System.out.println("Qué ciudad desea consultar?");
                            String city=sc.next();
                            BDPDAO.consultarDiaPorCiudad(city);
                            break;
                        case 6:
                            System.out.println("Saliendo del sistema AUCORSA. ¡Hasta pronto!");
                            break;

                        default:
                            System.out.println("Opción no válida. Por favor, elige una opción del 1 al 5.");
                            break;
                    }
                } while (opcion != 5);

                sc.close();
    }
}
