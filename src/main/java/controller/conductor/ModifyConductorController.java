package controller.conductor;

import controller.dao.ConductorDAO;
import controller.db.ConnectionBBDD;
import model.Conductor;
import view.conductor.ModifyConductorView;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
/**
 * Controlador para la modificación de datos de un conductor existente.
 * Gestiona la interacción entre la vista de edición y la base de datos.
 * * @author Marta
 */
public class ModifyConductorController {

    // ==================== ATRIBUTOS ====================

    private final ModifyConductorView view;
    private final ConductorController conductorController;
    private final ConductorDAO conductorDAO;


    // ==================== CONSTRUCTOR ====================

    /**
     * Constructor de ModifyConductorController.
     * Vincula la vista con el DAO y configura los eventos de los botones Guardar y Cancelar.
     * * @param view Instancia de la interfaz gráfica de modificación.
     * @param conductorController Controlador principal para refrescar la lista tras los cambios.
     * @param conductorDAO Objeto para realizar la actualización en la persistencia.
     */
    public ModifyConductorController(ModifyConductorView view, ConductorController conductorController, ConductorDAO conductorDAO) {
        this.view = view;
        this.conductorController = conductorController;
        this.conductorDAO = conductorDAO;

        view.getBtnCancelar().addActionListener(e -> view.dispose());

        view.getBtnGuardar().addActionListener(e -> confirmarModificacion());
    }

    // ==================== MÉTODOS ====================

    /**
     * Valida la información del formulario y aplica los cambios en la base de datos.
     * Verifica que los campos de nombre y apellido no estén vacíos antes de proceder
     * con la actualización del registro.
     * * @throws SQLException Si ocurre un error de acceso a datos durante la ejecución de la sentencia SQL.
     */
    private void confirmarModificacion() {

        String nombre = view.getNombre().getText().trim();
        String apellido = view.getApellido().getText().trim();

        String nombreImagen = null;

        if (nombre.isEmpty() || apellido.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Deben estar todos los campos completos.",
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(view,
                "¿Estás seguro de que quieres guardar los cambios?",
                "Confirmar modificación", JOptionPane.YES_NO_OPTION);

        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        Conductor conductor = new Conductor(view.getNumConductor(), nombre, apellido, nombreImagen);

        boolean updateSuccess = false;
        try (Connection con = ConnectionBBDD.getConexion()) {
            updateSuccess = conductorDAO.modificarConductor(con, conductor);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (updateSuccess) {
            JOptionPane.showMessageDialog(view, "Conductor modificado correctamente.",
                    "Información", JOptionPane.INFORMATION_MESSAGE);
            conductorController.cargarConductores();
            view.dispose();
        } else {
            JOptionPane.showMessageDialog(view, "Error al modificar el conductor en la base de datos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}