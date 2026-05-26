package controller.bus;

import controller.dao.BusDAO;
import controller.db.ConnectionBBDD;
import model.Bus;
import view.bus.AddBusView;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
/**
 * Clase AddBusController
 * Controlador del formulario de alta de buses.
 * Gestiona la validación de los datos introducidos por el usuario
 * y su inserción en la base de datos a través de BusDAO.
 * * @author Marta
 */
public class AddBusController {

    // ==================== ATRIBUTOS ====================

    private final AddBusView view;
    private final BusController busController;

    // ==================== CONSTRUCTOR ====================

    /*
     * Constructor de AddBusController.
     * Asocia la vista y configura los listeners de los botones del formulario.
     * * @param view Vista del formulario de añadir bus que contiene los campos de entrada.
     * @param busController Controlador principal para gestionar la actualización de la lista de buses.
     */
    public AddBusController(AddBusView view, BusController busController) {
        this.view = view;
        this.busController = busController;

        // Asocia el botón Cancelar para cerrar la ventana sin guardar
        view.getBtnCancelar().addActionListener(e -> view.dispose());

        // Asocia el botón Añadir con el proceso de validación y guardado
        view.getBtnAdd().addActionListener(e -> confirmarGuardado());
    }

    // ==================== MÉTODOS ====================

    /*
     * Valida los campos del formulario, solicita confirmación al usuario
     * e inserta el nuevo bus en la base de datos.
     * Comprueba que los campos matrícula, tipo y licencia no estén vacíos antes de la persistencia.
     * * @throws SQLException Si ocurre una anomalía en la conexión o durante la ejecución de la sentencia SQL de inserción.
     */
    private void confirmarGuardado() {

        // Recoge y limpia los datos introducidos en el formulario
        String matricula = view.getMatricula().getText().trim();
        String tipo = view.getTipo().getText().trim();
        String licencia = view.getLicencia().getText().trim();
        String imagen = null;
        // Valida que ningún campo esté vacío
        if (matricula.isEmpty() || tipo.isEmpty() || licencia.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Deben estar todos los campos completos.",
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Solicita confirmación al usuario antes de guardar
        int opcion = JOptionPane.showConfirmDialog(view,
                "¿Estás seguro de que quieres guardar el bus?",
                "Confirmar guardado", JOptionPane.YES_NO_OPTION);

        // Si el usuario cancela, cierra la ventana sin guardar
        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        // Construye el objeto Bus con los datos ya validados
        Bus bus = new Bus(matricula, tipo, licencia, imagen);

        // Intenta insertar el bus en la base de datos
        boolean addSuccess = false;
        try (Connection con = ConnectionBBDD.getConexion()) {
            addSuccess = BusDAO.añadirBus(con, bus);
        } catch (SQLException e) {
            // Imprime información de error si ocurre una excepción SQL
            e.printStackTrace();
        }

        // Muestra el resultado de la operación al usuario
        if (addSuccess) {
            JOptionPane.showMessageDialog(view, "Bus guardado correctamente",
                    "Información", JOptionPane.INFORMATION_MESSAGE);
            busController.cargarBuses();
        } else {
            JOptionPane.showMessageDialog(view, "Error al guardar el bus.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Cierra la ventana tras completar la operación
        view.dispose();
    }
}