package controller.bus;

import controller.dao.BusDAO;
import controller.db.ConnectionBBDD;
import model.Bus;
import view.bus.AddBusView;
import view.bus.BusPanel;
import view.bus.ModifyBusView;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase BusController
 * Controlador de la sección de buses.
 * Gestiona la carga de datos desde la base de datos y las acciones
 * disponibles sobre los buses en la vista.
 * * @author Marta
 */
public class BusController {

    // ==================== ATRIBUTOS ====================

    BusDAO busDAO;
    BusPanel busPanel;

    // Lista local que almacena los buses cargados desde la base de datos
    ArrayList<Bus> buses = new ArrayList<>();

    // ==================== CONSTRUCTOR ====================

    /*
     * Constructor de BusController.
     * Inicializa el DAO, asocia el panel y carga los datos de la base de datos.
     * * @param panel Panel de la vista donde se mostrarán los buses.
     */
    public BusController(BusPanel panel) {
        this.busPanel = panel;
        busDAO = new BusDAO();

        // Carga los buses al inicializar el controlador
        cargarBuses();
    }

    // ==================== MÉTODOS ====================

    /*
     * Carga todos los buses de la base de datos y los muestra en la tabla del panel.
     * Actualiza el modelo de la tabla limpiando las filas previas e insertando las nuevas .
     * * @throws SQLException Si ocurre un error al intentar obtener la conexión o consultar la base de datos.
     */
    public void cargarBuses() {
        try (Connection con = ConnectionBBDD.getConexion()) {
            buses = (ArrayList<Bus>) BusDAO.mostrarTodosLosBuses(con);

            // Limpia la tabla antes de recargar los datos
            busPanel.getModeloTabla().setRowCount(0);

            // Añade cada bus como una nueva fila en la tabla
            for (Bus b : buses) {
                busPanel.getModeloTabla().addRow(new Object[]{
                        b.getMatricula(), b.getTipo(), b.getLicencia()
                });
            }
        } catch (SQLException e) {
            // Imprime información de error si ocurre una excepción SQL
            e.printStackTrace();
        }
    }

    /*
     * Abre la ventana para añadir un nuevo Bus.
     * Crea una nueva instancia de la vista y su controlador correspondiente.
     */
    public void añadirBus() {
        // Crea la vista del formulario de añadir bus
        AddBusView addPanel = new AddBusView();

        // Crea el controlador del formulario y le pasa la vista
        new AddBusController(addPanel, this);
    }

    /*
     * Elimina el bus seleccionado en la tabla tras pedir confirmación al usuario.
     * Identifica el registro mediante la matrícula obtenida de la fila seleccionada.
     * * @throws SQLException Si se produce un error durante la ejecución de la baja en la base de datos.
     */
    public void eliminarBus() {

        // Obtiene la fila seleccionada en la tabla
        int filaSeleccionada = busPanel.getTablaVista().getSelectedRow();

        // Comprueba que haya una fila seleccionada
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(busPanel, "Debes seleccionar un bus para eliminar.",
                    "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtiene la matrícula del bus seleccionado
        String matricula = (String) busPanel.getModeloTabla().getValueAt(filaSeleccionada, 0);

        // Solicita confirmación al usuario antes de eliminar
        int opcion = JOptionPane.showConfirmDialog(busPanel,
                "¿Estás seguro de que quieres eliminar el bus " + matricula + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        // Si el usuario cancela, no hace nada
        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        // Intenta eliminar el bus de la base de datos
        boolean deleteSuccess = false;
        try (Connection con = ConnectionBBDD.getConexion()) {
            deleteSuccess = BusDAO.eliminarBus(con, matricula);
        } catch (SQLException e) {
            // Imprime información de error si ocurre una excepción SQL
            e.printStackTrace();
        }

        // Muestra el resultado de la operación al usuario
        if (deleteSuccess) {
            JOptionPane.showMessageDialog(busPanel, "Bus eliminado correctamente.",
                    "Información", JOptionPane.INFORMATION_MESSAGE);

            // Recarga la tabla para reflejar los cambios
            cargarBuses();
        } else {
            JOptionPane.showMessageDialog(busPanel, "Error al eliminar el bus.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /*
     * Abre la ventana para modificar el bus seleccionado en la tabla.
     * Extrae los datos de la fila seleccionada y los transfiere a la vista de modificación.
     */
    public void modificarBus() {

        // Obtiene la fila seleccionada en la tabla
        int filaSeleccionada = busPanel.getTablaVista().getSelectedRow();

        // Comprueba que haya una fila seleccionada
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(busPanel, "Debes seleccionar un bus para modificar.",
                    "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtiene los datos del bus seleccionado de la tabla
        String matricula = (String) busPanel.getModeloTabla().getValueAt(filaSeleccionada, 0);
        String tipo = (String) busPanel.getModeloTabla().getValueAt(filaSeleccionada, 1);
        String licencia = (String) busPanel.getModeloTabla().getValueAt(filaSeleccionada, 2);

        // Crea la vista del formulario pre-rellenada con los datos actuales
        ModifyBusView modifyPanel = new ModifyBusView(matricula, tipo, licencia);

        // Crea el controlador del formulario y le pasa la vista
        new ModifyBusController(modifyPanel, this);
    }

}