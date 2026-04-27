package controller.conductor;

import controller.dao.ConductorDAO;
import controller.db.ConnectionBBDD;
import model.Conductor;
import view.conductor.AddConductorView;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
public class AddConductorController {

    /**
     * Clase AddConductorController
     * @author Marta
     */
    // ==================== ATRIBUTOS ====================

    private final AddConductorView view;
    private final ConductorController conductorController;
    private final ConductorDAO conductorDAO;


    // ==================== CONSTRUCTOR ====================

    /*
     * Inicializa el controlador vinculando la vista con la lógica de negocio y los DAOs.
     * Establece los escuchadores de eventos para la gestión de la interfaz.
     * * @param view Objeto de la interfaz gráfica que captura los datos del conductor.
     * @param conductorController Controlador de referencia para actualizar la lista tras la inserción.
     * @param conductorDAO Objeto de acceso a datos encargado de la persistencia en la base de datos.
     */
    public AddConductorController(AddConductorView view, ConductorController conductorController, ConductorDAO conductorDAO) {
        this.view = view;
        this.conductorController = conductorController;
        this.conductorDAO = conductorDAO;

        view.getBtnCancelar().addActionListener(e -> view.dispose());
        view.getBtnAdd().addActionListener(e -> confirmarGuardado());
    }

    // ==================== MÉTODOS ====================

    /*
     * Gestiona el proceso de validación y almacenamiento de un nuevo conductor.
     * Recupera los datos de la vista, verifica su integridad y procede a su
     * inserción mediante el uso de una conexión a la base de datos.
     * * @throws NumberFormatException Si el formato del número de conductor introducido no es un entero válido.
     * @throws SQLException Si ocurre un error de conectividad o integridad durante la ejecución de la consulta SQL.
     */
    private void confirmarGuardado() {

        String sNum = view.getNumConductor().getText().trim();
        String nombre = view.getNombre().getText().trim();
        String apellido = view.getApellidos().getText().trim();

        String nombreImagen = null;

        if (sNum.isEmpty() || nombre.isEmpty() || apellido.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Deben estar todos los campos completos.",
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int numDriver;
        try {
            numDriver = Integer.parseInt(sNum);
            if (numDriver <= 0) {
                JOptionPane.showMessageDialog(view, "Debe ser un número de conductor positivo.",
                        "Error de validación", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Número debe ser en formato numérico válido.",
                    "Error de formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(view,
                "¿Estás seguro de que quieres guardar el conductor?",
                "Confirmar guardado", JOptionPane.YES_NO_OPTION);

        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        Conductor conductor = new Conductor(numDriver, nombre, apellido, nombreImagen);

        boolean addSuccess = false;
        try (Connection con = ConnectionBBDD.getConexion()) {
            addSuccess = conductorDAO.insertarConductor(con, conductor);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (addSuccess) {
            JOptionPane.showMessageDialog(view, "Conductor guardado correctamente",
                    "Información", JOptionPane.INFORMATION_MESSAGE);
            conductorController.cargarConductores();
            view.dispose();
        } else {
            JOptionPane.showMessageDialog(view, "Error al guardar el conductor (ID duplicado o error de BD).",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}