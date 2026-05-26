package controller.conductor;


import controller.DetailsDriverController;
import controller.dao.ConductorDAO;
import controller.db.ConnectionBBDD;
import model.Conductor;
import view.conductor.AddConductorView;
import view.conductor.ConductorPanel;
import view.conductor.DetailsBusView;
import view.conductor.ModifyConductorView;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase ConductorController
 * Permite añadir, eliminar y cargar conductores mediante botones
 * @author Marta
 */
public class ConductorController {

    // ==================== ATRIBUTOS ====================

    private final ConductorPanel conductorPanel;
    private final ConductorController self = this; /* referencia limpia ya que al probar la primera vez hubo confusión
                                                        con la instancia de mouseclicked y el controller */
    private final ConductorDAO conductorDAO;
    private List<Conductor> conductores = new ArrayList<>();

    // ==================== CONSTRUCTOR ====================

    /*
     *
     * @param panel
     * @param conductorDAO
     */
    public ConductorController(ConductorPanel panel, ConductorDAO conductorDAO) {
        this.conductorPanel = panel;
        this.conductorDAO = conductorDAO;

        conductorPanel.getTablaVista().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && conductorPanel.getTablaVista().getSelectedRow() != -1) {
                    DetailsBusView vista = new DetailsBusView();
                    new DetailsDriverController(vista, self, conductorPanel, conductores, conductorDAO);
                }
            }
        });

        cargarConductores();
    }

    // ==================== MÉTODOS ====================

    /*
     * Carga todos los conductores y refresca la tabla. Devuelve la lista actualizada.
     */
    public List<Conductor> cargarConductores() {
        try (Connection con = ConnectionBBDD.getConexion()) {
            List<Conductor> lista = conductorDAO.mostrarTodosConductores(con);

            if (lista == null) lista = new ArrayList<>();
            conductores = lista;

            conductorPanel.getModeloTabla().setRowCount(0);
            for (Conductor c : conductores) {
                conductorPanel.getModeloTabla().addRow(new Object[]{
                        c.getNumConductor(),
                        c.getNombre(),
                        c.getApellido()
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(conductorPanel,
                    "Error al conectar con la base de datos: " + e.getMessage(),
                    "Error de conexión", JOptionPane.ERROR_MESSAGE);
        }
        return conductores;
    }

    /*
     * Abre la ventana de añadir conductor.
     */
    public void añadirConductor() {
        new AddConductorController(new AddConductorView(), this, conductorDAO);
    }

    /*
     * Elimina el conductor seleccionado en la tabla.
     */
    public void eliminarConductor() {
        int fila = conductorPanel.getTablaVista().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(conductorPanel,
                    "Debes seleccionar un conductor para eliminar.",
                    "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int numConductor = (int) conductorPanel.getModeloTabla().getValueAt(fila, 0);

        int opcion = JOptionPane.showConfirmDialog(conductorPanel,
                "¿Eliminar el conductor " + numConductor + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (opcion != JOptionPane.YES_OPTION) return;

        try (Connection con = ConnectionBBDD.getConexion()) {
            if (conductorDAO.eliminarConductor(con, numConductor)) {
                JOptionPane.showMessageDialog(conductorPanel,
                        "Conductor eliminado correctamente.");
                cargarConductores();
            } else {
                JOptionPane.showMessageDialog(conductorPanel,
                        "Error al eliminar el conductor.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * Abre la ventana de modificación para el conductor seleccionado.
     */
    public void modificarConductor() {
        int fila = conductorPanel.getTablaVista().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(conductorPanel,
                    "Debes seleccionar un conductor para modificar.",
                    "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int num = (int) conductorPanel.getModeloTabla().getValueAt(fila, 0);
        String nombre = (String) conductorPanel.getModeloTabla().getValueAt(fila, 1);
        String apellido = (String) conductorPanel.getModeloTabla().getValueAt(fila, 2);

        new ModifyConductorController(new ModifyConductorView(num, nombre, apellido), this, conductorDAO);
    }
}