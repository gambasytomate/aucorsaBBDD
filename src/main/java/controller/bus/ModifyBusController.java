package controller.bus;

import controller.dao.BusDAO;
import controller.db.ConnectionBBDD;
import model.Bus;
import view.bus.ModifyBusView;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
/**
 * Clase ModifyBusController
 * Controlador del formulario de modificación de buses.
 * Gestiona la validación de los datos introducidos por el usuario
 * y su actualización en la base de datos a través de BusDAO.
 * * @author Marta
 */
public class ModifyBusController {

    // ==================== ATRIBUTOS ====================

    private final ModifyBusView view;
    private final BusController busController;

    // ==================== CONSTRUCTOR ====================

    /*
     * Constructor de ModifyBusController.
     * Asocia la vista y configura los listeners de los botones del formulario.
     * * @param view Vista del formulario de modificar bus que contiene los campos de texto.
     * @param busController Controlador de buses para recargar la tabla tras la modificación.
     */
    public ModifyBusController(ModifyBusView view, BusController busController) {
        this.view = view;
        this.busController = busController;

        // Asocia el botón Cancelar para cerrar la ventana sin guardar
        view.getBtnCancelar().addActionListener(e -> view.dispose());

        // Asocia el botón Guardar con el proceso de validación y actualización
        view.getBtnGuardar().addActionListener(e -> confirmarModificacion());
    }

    // ==================== MÉTODOS ====================

    /*
     * Valida los campos del formulario, solicita confirmación al usuario
     * y actualiza el bus en la base de datos.
     * Recupera la matrícula de la vista y genera un objeto Bus para su persistencia.
     * * @throws SQLException Si ocurre un error al intentar establecer conexión o ejecutar la sentencia en la base de datos.
     */
    private void confirmarModificacion() {

        // Recoge y limpia los datos introducidos en el formulario
        String tipo = view.getTipo().getText().trim();
        String licencia = view.getLicencia().getText().trim();

        // Valida que ningún campo esté vacío
        if (tipo.isEmpty() || licencia.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Deben estar todos los campos completos.",
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Solicita confirmación al usuario antes de guardar
        int opcion = JOptionPane.showConfirmDialog(view,
                "¿Estás seguro de que quieres guardar los cambios?",
                "Confirmar modificación", JOptionPane.YES_NO_OPTION);

        // Si el usuario cancela, cierra la ventana sin guardar
        if (opcion != JOptionPane.YES_OPTION) {
            view.dispose();
            return;
        }

        // Construye el objeto Bus con los datos ya validados
        Bus bus = new Bus(view.getMatricula(), tipo, licencia);

        // Intenta actualizar el bus en la base de datos
        boolean updateSuccess = false;
        try (Connection con = ConnectionBBDD.getConexion()) {
            updateSuccess = BusDAO.modificarBus(con, bus);
        } catch (SQLException e) {
            // Imprime información de error si ocurre una excepción SQL
            e.printStackTrace();
        }

        // Muestra el resultado de la operación al usuario
        if (updateSuccess) {
            JOptionPane.showMessageDialog(view, "Bus modificado correctamente.",
                    "Información", JOptionPane.INFORMATION_MESSAGE);

            // Recarga la tabla para reflejar los cambios
            busController.cargarBuses();
        } else {
            JOptionPane.showMessageDialog(view, "Error al modificar el bus.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Cierra la ventana tras completar la operación
        view.dispose();
    }
}